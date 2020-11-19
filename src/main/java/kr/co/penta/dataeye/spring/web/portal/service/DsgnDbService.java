package kr.co.penta.dataeye.spring.web.portal.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface DsgnDbService {   
	Map<String, Object> uploadDsgnTab(File f, String databaseId);
	Map<String, Object> importDsgnTab(Map<String, Object> reqParam, SessionInfo sessionInfo);
	
	Map<String, Object> uploadDsgnCol(File f, String databaseId);
	Map<String, Object> importDsgnCol(Map<String, Object> reqParam, SessionInfo sessionInfo);
	String genDDL(List<Map<String, Object>> datas);
}
