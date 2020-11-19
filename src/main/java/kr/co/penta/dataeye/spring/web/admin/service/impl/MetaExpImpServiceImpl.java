package kr.co.penta.dataeye.spring.web.admin.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.consts.META_EXP_CONST;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.admin.service.MetaExpImpService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class MetaExpImpServiceImpl implements MetaExpImpService {

	@Autowired
	private CommonDao commonDao;

    @Override
    public Map<String, Object> exp(Map<String, Object> reqParam, SessionInfo sessionInfo) {
    	Map<String, Object> resultMap = new HashMap<>();
    	
    	for(META_EXP_CONST.KEY reqKey : META_EXP_CONST.KEY.values()) {
    		String key = reqKey.name();
    		List<String> value = reqKey.value();
    		
    		if (reqKey.equals(META_EXP_CONST.KEY.OBJ)) {
    			List<String> objTypeIds = (List<String>)reqParam.get(reqKey.name());
    			Map<String, Object> m1 = new HashMap<>();
				for(String objTypeId : objTypeIds) {
					Map<String, Object> m2 = new HashMap<>();
					for(String tableName : value) {
						m2.put(tableName, export(tableName, objTypeId));
					}
					m1.put(objTypeId, m2);
    			}
				resultMap.put(key, m1);
    		} else {
    			Boolean b = CastUtils.toBoolean(reqParam.get(reqKey.name()));
    			if (b) {
    				Map<String, Object> m1 = new HashMap<>();
    				for(String tableName : value) {
    					m1.put(tableName, export(tableName));
    				}
    				resultMap.put(key, m1);
    			}
    		}
    	}
    	
    	return resultMap;
    }

    @Override
    public void imp(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        
    }
    
    private List<Map<String, Object>> export(String tableName) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(tableName);
    	
    	return commonDao.dynamicSqlList(new DaoParam(), sb.toString());
    }
    
    private List<Map<String, Object>> export(String tableName, String objTypeId) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("SELECT * FROM ").append(tableName).append("\n");
    	sb.append("WHERE OBJ_TYPE_ID = '").append(objTypeId).append("'");
    	
    	return commonDao.dynamicSqlList(new DaoParam(), sb.toString());
    }
}
