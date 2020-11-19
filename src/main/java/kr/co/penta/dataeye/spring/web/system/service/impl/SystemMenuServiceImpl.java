package kr.co.penta.dataeye.spring.web.system.service.impl;


import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;
import kr.co.penta.dataeye.spring.web.system.service.SystemMenuService;
import kr.co.penta.dataeye.spring.web.validate.MenuForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

	@Autowired
	private CommonDao commonDao;

    @Override
    public boolean isExistChildMenu(String menuId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("menuId", menuId);
        Integer cnt = commonDao.selectOne("system.menu.getChildMenuCnt", daoParam);
        if (cnt == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public List<CamelMap> getFolderMenu(String appId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("appId", appId);
        return commonDao.selectList("system.menu.getFolderMenu", daoParam);
    }    

    @Override
    public CamelMap get(String menuId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("menuId", menuId);
        
        return commonDao.selectOne("system.menu.getMenu", daoParam);
    }

    @Override
    public void regist(MenuForm menuForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(menuForm), sessionInfo);
        
        commonDao.insert("PEN_MENU_M.insert", daoParam);
    }

    @Override
    public void update(MenuForm menuForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(menuForm), sessionInfo);
        
        commonDao.insert("PEN_MENU_M.update", daoParam);
    }

    @Override
    public void remove(String menuId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("menuId", menuId);
        
        commonDao.insert("PEN_MENU_M.delete", daoParam);
    }

    @Override
    @Transactional
    public void registUserGrp(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        Object privRcvrId = reqParam.get("PRIV_RCVR_ID");
        if (privRcvrId instanceof Collection<?>){
            Collection<String> userIds = (Collection<String>)privRcvrId;
            for(String userId : userIds) {
                DaoParam daoParam = new DaoParam(sessionInfo);
                daoParam.putAll(reqParam);
                daoParam.put("PRIV_RCVR_ID", userId);
                
                commonDao.insert("PEN_SYS_PRIV_MENU_R.insert", daoParam);
            }
        } else {
            DaoParam daoParam = new DaoParam(sessionInfo);
            daoParam.putAll(reqParam);
            
            commonDao.insert("PEN_SYS_PRIV_MENU_R.insert", daoParam);
        }
    }

    @Override
    public void updateUserGrp(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.putAll(reqParam);
        
        commonDao.update("PEN_SYS_PRIV_MENU_R.update", daoParam);
    }

    @Override
    @Transactional
    public void removeUserGrp(List<Map<String, Object>> datas, SessionInfo sessionInfo) {
        for(Map<String, Object> map : datas) {
            DaoParam daoParam = new DaoParam(map);            
            commonDao.delete("PEN_SYS_PRIV_MENU_R.delete", daoParam);
        }
    }
    
    
    @Override
    public void registUser() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateUser() {
        // TODO Auto-generated method stub
        
    }
}
