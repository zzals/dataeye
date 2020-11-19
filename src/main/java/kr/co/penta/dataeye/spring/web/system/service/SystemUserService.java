package kr.co.penta.dataeye.spring.web.system.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.SysUserGrpMForm;
import kr.co.penta.dataeye.spring.web.validate.SysUserMForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface SystemUserService {
    /**
     * 사용자 패스워드 초기화
     **/
    void userInitPwd(String userId, String userPassword, SessionInfo sessionInfo);
    
    void copyUser(String userId, String userPassword, SessionInfo sessionInfo);
	void updatePwd(String userId, String userPassword, SessionInfo sessionInfo);
	

    boolean isExistUserGrp(String  userGrpId);
    void registUserGrp(SysUserGrpMForm sysUserGrpMForm, SessionInfo sessionInfo);
    void updateUserGrp(SysUserGrpMForm sysUserGrpMForm, SessionInfo sessionInfo);
    void removeUserGrp(String userGrpId, SessionInfo sessionInfo);
    Map<String, Object> getUserGrp(String userGrpId);
    
    void registGroupUserR(String userGrpId, List<String> userIds, SessionInfo sessionInfo);
    void removeGroupUserR(String userGrpId, List<String> userIds, SessionInfo sessionInfo);
    
    void registGroupR(String userGrpId, List<String> relUserGrpIds, SessionInfo sessionInfo);
    void removeGroupR(String userGrpId, List<String> relUserGrpIds, SessionInfo sessionInfo);
    
    void addObjAuth(String objTypeId, String objId,  List<String> addInfo,SessionInfo sessionInfo);
    void delObjAuth(String objTypeId, String objId,  List<String> delInfo,SessionInfo sessionInfo);
   
    CamelMap get(String userId);
    List<CamelMap> getOrgNm();
    void registUserM(SysUserMForm sysUserMForm, SessionInfo sessionInfo);
    void updateUserM(SysUserMForm sysUserMForm, SessionInfo sessionInfo);
    void removeUserM(String userId, SessionInfo sessionInfo);
  
    
    Map<String, Object> getUser(String userId);
    boolean isExistUserId(String  userId);

	boolean isPTA(String userId);

}
