package kr.co.penta.dataeye.spring.web.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.security.core.User;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.system.service.SystemUserService;
import kr.co.penta.dataeye.spring.web.validate.SysUserGrpMForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;
import kr.co.penta.dataeye.spring.web.validate.SysUserMForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {

	@Autowired 
	private CommonDao commonDao;

	@Override
    public void userInitPwd(String userId, String userPassword, SessionInfo sessionInfo) {
	    String hashpw = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));
        
            updatePwd(userId, hashpw, sessionInfo);
        
	}
	
    @Override
    public void copyUser(String userId, String userPassword, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        daoParam.put("userPassword", userPassword);
        daoParam.put("sessionInfo", sessionInfo);
        
        commonDao.update("PEN_USER_M.copyInsert", daoParam);
    }
	
	@Override
    public void updatePwd(String userId, String userPassword, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        daoParam.put("userPassword", userPassword);
        daoParam.put("sessionInfo", sessionInfo);
        
        commonDao.update("PEN_USER_M.updatePwd", daoParam);
    }
    
    private boolean isExistUser(String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        
        int cnt = commonDao.selectOne("system.user.isExistUser", daoParam);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public boolean isExistUserGrp(String userGrpId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userGrpId", userGrpId);
        Integer cnt = commonDao.selectOne("system.user.getUserGrpDupCnt", daoParam);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void registUserGrp(SysUserGrpMForm sysUserGrpMForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(sysUserGrpMForm), sessionInfo);
        commonDao.insert("PEN_SYS_USER_GRP_M.insert", daoParam);
    }

    @Override
    public void updateUserGrp(SysUserGrpMForm sysUserGrpMForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(sysUserGrpMForm), sessionInfo);        
        commonDao.update("PEN_SYS_USER_GRP_M.update", daoParam);
    }

    @Override
    public void removeUserGrp(String userGrpId, SessionInfo sessionInfo) {
        Map<String, Object> map = getUserGrp(userGrpId);
        String userGrpType = CastUtils.toString(map.get("USER_GRP_TYPE"));
        
        DaoParam daoParam = new DaoParam(sessionInfo);
        int cnt = 0;
        if ("ROLE".equals(userGrpType)) {
            daoParam.put("relUserGrpId", userGrpId);
            cnt = commonDao.selectOne("system.user.getSysGrpRCnt", daoParam);
        } else {
            /*daoParam.put("userGrpId", userGrpId);
            cnt = commonDao.selectOne("system.user.getSysGrpRCnt", daoParam);*/
        }
        if (cnt > 0) {
            throw new ServiceRuntimeException("참조하는 그룹관계가 존재 합니다.<br />삭제할 수 없습니다.");
        }
        
        daoParam.clear();
        daoParam.put("userGrpId", userGrpId);
        cnt = commonDao.selectOne("system.user.getSysGrpUserRCnt", daoParam);
        if (cnt > 0) {
            throw new ServiceRuntimeException("참조하는 사용자가 존재 합니다.<br />삭제할 수 없습니다.");
        }
        
        daoParam.clear();
        daoParam.putSessionInfo(sessionInfo).put("userGrpId", userGrpId);
        commonDao.delete("PEN_SYS_USER_GRP_M.delete", daoParam);
    }

    @Override
    public Map<String, Object> getUserGrp(String userGrpId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userGrpId", userGrpId);
        
        return commonDao.selectOne("system.user.getSysUserGrpM", daoParam);
    }

    @Override
    @Transactional
    public void registGroupUserR(String userGrpId, List<String> userIds, SessionInfo sessionInfo) {
        for(String userId : userIds) {
            DaoParam daoParam = new DaoParam(sessionInfo);
            daoParam.put("USER_GRP_ID", userGrpId).put("USER_ID", userId).put("DEL_YN", "N");
            
            commonDao.insert("PEN_SYS_GRP_USER_R.insert", daoParam);
        }
    }

    @Override
    public void removeGroupUserR(String userGrpId, List<String> userIds, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userGrpId", userGrpId).put("userIds", userIds);
        
        commonDao.delete("PEN_SYS_GRP_USER_R.delete", daoParam);
    }
    
    @Override
    @Transactional
    public void registGroupR(String userGrpId, List<String> relUserGrpIds, SessionInfo sessionInfo) {
        for(String relUserGrpId : relUserGrpIds) {
            DaoParam daoParam = new DaoParam(sessionInfo);
            daoParam.put("USER_GRP_ID", userGrpId).put("REL_USER_GRP_ID", relUserGrpId).put("DEL_YN", "N");
            
            commonDao.insert("PEN_SYS_GRP_R.insert", daoParam);
        }
    }
    
    @Override
    @Transactional
    public void addObjAuth(String objTypeId, String objId,  List<String> addInfo,SessionInfo sessionInfo){
    	
        for(String info : addInfo) {
        	if(info!=null) {
        		String[] info_a = info.split("#");
        		
        		DaoParam daoParam = new DaoParam(sessionInfo);
                daoParam.put("OBJ_TYPE_ID", objTypeId);
                daoParam.put("OBJ_ID", objId);
                daoParam.put("PRIV_RCVR_ID", info_a[0]);
                daoParam.put("PRIV_RCVR_GBN", info_a[1]);
                commonDao.insert("system.user.addObjAuth", daoParam);	
        	}
            
        }
    }
    
    @Override
    @Transactional
    public void delObjAuth(String objTypeId, String objId,  List<String> delInfo,SessionInfo sessionInfo){
    	
        for(String info : delInfo) {
        	if(info!=null) {
        		DaoParam daoParam = new DaoParam(sessionInfo);
                daoParam.put("OBJ_TYPE_ID", objTypeId);
                daoParam.put("OBJ_ID", objId);
                daoParam.put("PRIV_RCVR_ID", info);                
                commonDao.insert("system.user.delObjAuth", daoParam);	
        	}
            
        }
    }

    @Override
    public void removeGroupR(String userGrpId, List<String> relUserGrpIds, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userGrpId", userGrpId).put("relUserGrpIds", relUserGrpIds);
        
        commonDao.delete("PEN_SYS_GRP_R.delete", daoParam);
    }
    
    private static final String strInitUserPwd="penta1234"; /* 초기비밀번호 */
    @Override
    public void registUserM(SysUserMForm sysUserMForm, SessionInfo sessionInfo) {
        DaoParam daoParam =  new DaoParam(CastUtils.toMap(sysUserMForm), sessionInfo);
        
        String userPassword = strInitUserPwd;
        String hashpw = BCrypt.hashpw(userPassword, BCrypt.gensalt(12));
        
        daoParam.put("userInitPassword", hashpw);
        commonDao.insert("PEN_USER_M.insertUserM", daoParam);

    }
    
    @Override
    public void updateUserM(SysUserMForm sysUserMForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(sysUserMForm), sessionInfo);        
        commonDao.update("PEN_USER_M.updateUserM", daoParam);
    }
    
    
    @Override
    public void removeUserM(String userId, SessionInfo sessionInfo) {
    	DaoParam daoParam = new DaoParam();
    	daoParam.put("userId", userId);
        commonDao.delete("PEN_USER_M.deleteUserM", daoParam);
    }
    
    @Override
    public Map<String, Object> getUser(String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        
        return commonDao.selectOne("system.user.getUser", daoParam);
    }
    
    @Override
    public boolean isExistUserId(String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        Integer cnt = commonDao.selectOne("system.user.isExistUser", daoParam);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public List<CamelMap> getOrgNm() {
        DaoParam daoParam = new DaoParam();
        return commonDao.selectList("system.user.getOrgNm", daoParam);
    }   
    
    @Override
    public CamelMap get(String userId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId);
        
        return commonDao.selectOne("system.user.getUserId", daoParam);
    }

	@Override
	public boolean isPTA(String userId) {
		DaoParam daoParam = new DaoParam();
        daoParam.put("userId", userId).put("userGrpId", "PTA");
        int cnt = commonDao.selectOne("system.user.checkSysGrpUserRCnt", daoParam);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
	}
	
}
