package kr.co.penta.dataeye.spring.web.system.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.SysLogForm;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface SystemLogService {

   
	//List<Map<String,Object>> getLogMenu() ;
	Map<String, Object> getLogMenu(String fromData);
    
	Map<String, Object> getLogUser(String fromData);
	
	Map<String, Object> getLogObj(String fromData);
}
