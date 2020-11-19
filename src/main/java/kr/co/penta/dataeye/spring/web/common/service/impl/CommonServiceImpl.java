package kr.co.penta.dataeye.spring.web.common.service.impl;

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.web.common.service.CommonService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);
    
    @Autowired
    private CommonDao commonDao;
    
    @Override
    public void insert(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String queryId = CastUtils.toString(reqParam.get("queryId"));
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.insert(queryId, daoParam);
    }

    @Override
    public void insert(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo) {
        queryId = CastUtils.toString(queryId);
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.insert(queryId, daoParam);
    }
    
    @Override
    public void update(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String queryId = CastUtils.toString(reqParam.get("queryId"));
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.update(queryId, daoParam);
    }

    @Override
    public void update(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo) {
        queryId = CastUtils.toString(queryId);
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.update(queryId, daoParam);
    }
    
    @Override
    public void delete(Map<String, Object> reqParam, SessionInfo sessionInfo) {
        String queryId = CastUtils.toString(reqParam.get("queryId"));
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.delete(queryId, daoParam);
    }
    
    @Override
    public void delete(String queryId, Map<String, Object> reqParam, SessionInfo sessionInfo) {
        queryId = CastUtils.toString(queryId);
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam, sessionInfo);
        commonDao.delete(queryId, daoParam);
    }

    @Override
    public Map<String, Object> get(String queryId, Map<String, Object> reqParam) {
        DaoParam daoParam = new DaoParam(reqParam);
        return commonDao.selectOne(queryId, daoParam);
    }

    @Override
    public List<Map<String, Object>> find(String queryId, Map<String, Object> reqParam) {
        DaoParam daoParam = new DaoParam(reqParam);
        return commonDao.selectList(queryId, daoParam);
    }
}
