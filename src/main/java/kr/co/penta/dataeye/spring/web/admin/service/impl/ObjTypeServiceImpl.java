package kr.co.penta.dataeye.spring.web.admin.service.impl;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.admin.service.ObjTypeService;
import kr.co.penta.dataeye.spring.web.admin.service.ObjUIService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.validate.ObjTypeForm;
import kr.co.penta.dataeye.spring.web.validate.UIForm;
import net.minidev.json.parser.JSONParser;


/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class ObjTypeServiceImpl implements ObjTypeService {

	@Autowired
	private CommonDao commonDao;
	
   @Override
   public boolean isValidObjTypeId(String objTypeId) {
	    DaoParam daoParam = new DaoParam();
	    daoParam.put("objTypeId", objTypeId);
	    Integer cnt = commonDao.selectOne("objtype.getDuplCntByObjTypeId", daoParam);
	    if (cnt == 0) {
	        return true;
	    } else {
	        return false;
	    }
   }

    

    @Override
    public CamelMap get(String objTypeId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        
        return commonDao.selectOne("objtype.getObjTypeM", daoParam);
    }

    @Override
    public void regist(ObjTypeForm objTypeForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(objTypeForm), sessionInfo);
        
        commonDao.insert("PEN_OBJ_TYPE_M.insert", daoParam);
    }

    @Override
    public void update(ObjTypeForm objTypeForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(objTypeForm), sessionInfo);
        
        commonDao.insert("PEN_OBJ_TYPE_M.update", daoParam);
    }

    @Override
    public void remove(String objTypeId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("OBJ_TYPE_ID", objTypeId);
        
        commonDao.insert("PEN_OBJ_TYPE_M.delete", daoParam);
    }
    
    @Override
    public List getTobObjType() {
    	 DaoParam daoParam = new DaoParam();
                 
        List topObjType = commonDao.selectList("objtype.getTobObjType", daoParam);
        return topObjType;
    }

    @Override
    @Transactional
    public void atrMgr(Map<String, Object> paramMap, SessionInfo sessionInfo) {
    	
    	
        String dataList = (String)paramMap.get("dataList");      
        
     
        DaoParam deleteParam = new DaoParam(paramMap, sessionInfo);        
        commonDao.insert("PEN_OBJ_TYPE_ATR_D.delete", deleteParam);
        
        JSONParser parser = new JSONParser();
        Object obj = null;
		try {
			obj = parser.parse( dataList );
		} catch (Exception e) {
			e.printStackTrace();
		}
		net.minidev.json.JSONArray jsonObjList = ( net.minidev.json.JSONArray) obj;
        
        for(Object jsonObj : jsonObjList){
        	net.minidev.json.JSONObject mapObj = (net.minidev.json.JSONObject)(((net.minidev.json.JSONObject)jsonObj).get("map"));        	
        	HashMap<String,Object> resultMap = null;
        	try {
        		resultMap = new ObjectMapper().readValue(mapObj.toString(), HashMap.class);
			} catch (Exception e) {
				e.printStackTrace();
			} 
        	        	
    	  DaoParam daoParam = new DaoParam(resultMap, sessionInfo);
    	  commonDao.insert("PEN_OBJ_TYPE_ATR_D.insert", daoParam);
        	  
        }
   }
}