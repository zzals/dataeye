package kr.co.penta.dataeye.spring.web.system.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.system.service.SystemAppService;
import kr.co.penta.dataeye.spring.web.validate.ApplicationForm;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class SystemAppServiceImpl implements SystemAppService {

	@Autowired
	private CommonDao commonDao;

	@Override
    public boolean isValidAppId(String appId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("appId", appId);
        Integer cnt = commonDao.selectOne("system.app.getAppDuplCnt", daoParam);
        if (cnt == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Map<String, Object> get(String appId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("appId", appId);
        
        return commonDao.selectOne("system.app.getApp", daoParam);
    }

    @Override
    public List<Map<String, Object>> getSimpleApp() {
        return commonDao.selectList("system.app.getSimpleApp", null);
    }
    
    @Override
    public void regist(ApplicationForm applicationForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(applicationForm), sessionInfo);
        
        commonDao.insert("PEN_APP_M.insert", daoParam);
    }

    @Override
    public void update(ApplicationForm applicationForm, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(CastUtils.toMap(applicationForm), sessionInfo);
        
        commonDao.insert("PEN_APP_M.update", daoParam);
    }

    @Override
    public void remove(String appId, SessionInfo sessionInfo) {
        DaoParam daoParam = new DaoParam(sessionInfo);
        daoParam.put("appId", appId);
        
        commonDao.insert("PEN_APP_M.delete", daoParam);
    }
}
