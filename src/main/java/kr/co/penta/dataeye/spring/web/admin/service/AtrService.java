package kr.co.penta.dataeye.spring.web.admin.service;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.AtrForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface AtrService {
    boolean isValidAtrId(String atrId);
    CamelMap get(String atrId);
    void regist(AtrForm atrForm, SessionInfo sessionInfo);
    void update(AtrForm atrForm, SessionInfo sessionInfo);
    void remove(String atrId, SessionInfo sessionInfo);
}
