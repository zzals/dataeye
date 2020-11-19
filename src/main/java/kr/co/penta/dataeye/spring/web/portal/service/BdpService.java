package kr.co.penta.dataeye.spring.web.portal.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface BdpService {
	void dsetAtrImport(String dsetTypeId, String dsetId, String dsetAtrTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo);
	void dsgnTabColImport(String dsgnTabTypeId, String dsgnTabId, String dsgnTabColTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo);
	List<Map<String, Object>> dsgnTabColStdCheck(String dbmsType, List<Map<String, Object>> data);
	String dsgnTabNmStdTrans(String entityNm);
}
