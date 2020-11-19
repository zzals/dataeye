package kr.co.penta.dataeye.spring.web.admin.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface ObjUIService {
    CamelMap get(String uiId);
    void regist(UIForm uiForm, SessionInfo sessionInfo);
    void update(UIForm uiForm, SessionInfo sessionInfo);
    void remove(String uiId, SessionInfo sessionInfo);
    void saveMapping(String objTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo);
    void copy(String uiId, String newUiId, String uiNm, SessionInfo sessionInfo);
}
