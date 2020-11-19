package kr.co.penta.dataeye.spring.web.system.service.impl;


import kr.co.penta.dataeye.common.util.QueryUtil;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.admin.service.AdminService;
import kr.co.penta.dataeye.spring.web.system.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private CommonDao commonDao;

	@Override
	public void delMenuGrp(Map<String, Object> reqParam) {
		System.out.println("reqParam = " + reqParam);
		DaoParam daoParam = new DaoParam();
		daoParam.put("menuId", reqParam.get("menuId"));		
		daoParam.put("privRcvrGbn", reqParam.get("privRcvrGbn"));
		System.out.println("privRcvrId=" + ((String)reqParam.get("privRcvrId")).split("#"));
		daoParam.put("privRcvrId", ((String)reqParam.get("privRcvrId")).split("#"));
		
    	commonDao.delete("system_user.delMenuGrp", daoParam);
	}
	
	@Override
	public List<Map<String,Object>> getGrpList() { 
		DaoParam daoParam = new DaoParam();		
    	return commonDao.selectList("system.user.getGrpList", daoParam);
	}
	
	@Override
	public void addMenuGrp(Map<String, Object> reqParam) {
	
		DaoParam daoParam = new DaoParam();
		daoParam.put("appId", reqParam.get("appId"));
		daoParam.put("menuId", reqParam.get("menuId"));		
		daoParam.put("privRcvrGbn", reqParam.get("privRcvrGbn"));
		daoParam.put("privRcvrId", reqParam.get("privRcvrId"));
		daoParam.put("privOpenGbn", reqParam.get("privOpenGbn"));
				
    	commonDao.insert("system_user.addMenuGrp", daoParam);
	}
		
	@Override
	public void updMenuGrp(Map<String, Object> reqParam) {
	
		DaoParam daoParam = new DaoParam();
		daoParam.put("appId", reqParam.get("appId"));
		daoParam.put("menuId", reqParam.get("menuId"));		
		daoParam.put("privRcvrGbn", reqParam.get("privRcvrGbn"));
		daoParam.put("privRcvrId", reqParam.get("privRcvrId"));
		daoParam.put("privOpenGbn", reqParam.get("privOpenGbn"));			
    	commonDao.insert("system_user.updMenuGrp", daoParam);
	}
	
	
	@Override
	public void addMenuUser(Map<String, Object> reqParam) {
	

		String userIdList = (String)reqParam.get("userIdList");
		
		String[] userIdArray  = userIdList.split(",");
				
		DaoParam daoParam = new DaoParam();
		for(int i = 0;i < userIdArray.length; i++) {
			daoParam.put("appId", reqParam.get("appId"));
			daoParam.put("menuId", reqParam.get("menuId"));		
			daoParam.put("privRcvrGbn", reqParam.get("privRcvrGbn"));
			daoParam.put("privRcvrId", userIdArray[i]);
			daoParam.put("privOpenGbn", reqParam.get("privOpenGbn"));					
	    	commonDao.insert("system_user.addMenuUser", daoParam);	
		}
	}
	
	@Override
	public void updMenuUser(Map<String, Object> reqParam) {
				
		DaoParam daoParam = new DaoParam();
		daoParam.put("appId", reqParam.get("appId"));
		daoParam.put("menuId", reqParam.get("menuId"));		
		daoParam.put("privRcvrGbn", reqParam.get("privRcvrGbn"));
		daoParam.put("privRcvrId", reqParam.get("userId"));
		daoParam.put("privOpenGbn", reqParam.get("privOpenGbn"));						
    	commonDao.insert("system_user.updMenuUser", daoParam);

	}
	
	

}
