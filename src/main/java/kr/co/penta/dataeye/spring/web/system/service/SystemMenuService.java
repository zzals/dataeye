package kr.co.penta.dataeye.spring.web.system.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.MenuForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface SystemMenuService {
    boolean isExistChildMenu(String  menuId);
    CamelMap get(String menuId);
    void regist(MenuForm menuForm, SessionInfo sessionInfo);
    void update(MenuForm menuForm, SessionInfo sessionInfo);
    void remove(String  menuId, SessionInfo sessionInfo);
    List<CamelMap> getFolderMenu(String appId);
    
    void registUserGrp(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void updateUserGrp(Map<String, Object> reqParam, SessionInfo sessionInfo);
    void removeUserGrp(List<Map<String, Object>> datas, SessionInfo sessionInfo);
    void registUser();
    void updateUser();
}
