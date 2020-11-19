package kr.co.penta.dataeye.spring.web.portal.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface BizCommcdService {
    List<Map<String,Object>> findCommcd(String objTypeId, String objId);
    void deleteCommcdGrp(String objTypeId, String objId, SessionInfo sessionInfo);
    void saveCommcd(String cdGrpTypeId, String cdGrpId, List<Map<String, Object>> data, SessionInfo sessionInfo);
}
