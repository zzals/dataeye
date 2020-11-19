package kr.co.penta.dataeye.spring.web.admin.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.CdGrpForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface CodeService {
    CamelMap getCdGrp(String cdGrpId);
    boolean isValidCdGrpId(String cdGrpId);
    boolean isValidCdId(String cdId);
    void registCdGrp(CdGrpForm cdGrpForm, SessionInfo sessionInfo);
    void updateCdGrp(CdGrpForm cdGrpForm, SessionInfo sessionInfo);
    void removeCdGrp(String cdGrpId, SessionInfo sessionInfo);
    void saveCd(String cdGrpId, List<Map<String, Object>> data, SessionInfo sessionInfo);
}
