package kr.co.penta.dataeye.spring.web.system.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.ApplicationForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface SystemAppService {
    boolean isValidAppId(String appId);
    Map<String, Object> get(String appId);
    List<Map<String, Object>> getSimpleApp();
    void regist(ApplicationForm applicationForm, SessionInfo sessionInfo);
    void update(ApplicationForm applicationForm, SessionInfo sessionInfo);
    void remove(String appId, SessionInfo sessionInfo);
}
