package kr.co.penta.dataeye.spring.web.system.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface SystemService {
	void delMenuGrp(Map<String, Object> reqParam) ;
	List<Map<String,Object>> getGrpList() ;
	void addMenuGrp(Map<String, Object> reqParam) ;
	void updMenuGrp(Map<String, Object> reqParam) ;
	
	void addMenuUser(Map<String, Object> reqParam) ;	
	void updMenuUser(Map<String, Object> reqParam) ;
}
