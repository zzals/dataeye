package kr.co.penta.dataeye.spring.web.common.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.TreeBuilder;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaPublicDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.common.service.MetaPublicService;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class MetaPublicServiceImpl implements MetaPublicService {
    private static final Logger LOG = LoggerFactory.getLogger(MetaPublicServiceImpl.class);

    @Autowired private MetaPublicDao metaPublicDao;
    @Autowired private CommonDao commonDao;
    
    @Override
    public List<Map<String, Object>> getObjTypeTree() {
        List<Map<String, Object>> rsp = metaPublicDao.getObjTypeTree();
        
        TreeBuilder treeBuilder = new TreeBuilder("objTypeId", "upObjTypeId", rsp);
        
        return treeBuilder.build();
    }
    
    @Override
    public List<Map<String, Object>> getAtrSbstRslt(String objTypeId, Integer atrIdSeq) {
        CamelMap r = metaPublicDao.getAtr(objTypeId, atrIdSeq);
        
        String sqlSbst = r.getString("sqlSbst");
        
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        return commonDao.dynamicSqlList(daoParam, sqlSbst);
    }
    
    @Override
    public List<Map<String, Object>> getList(Map<String,Object> reqParam) {
    	String queryId = CastUtils.toString(reqParam.get("queryId"));
    	 
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam);        
        return commonDao.selectList(queryId, daoParam);
    }
    
    @Override
    public Map<String, Object> getMap(Map<String,Object> reqParam) {
        String queryId = CastUtils.toString(reqParam.get("queryId"));
         
        if ("".equals(queryId)) {
            throw new ServiceRuntimeException("요청이 올바르지 않습니다.");
        }
        
        DaoParam daoParam = new DaoParam(reqParam);        
        return commonDao.selectOne(queryId, daoParam);
    }
    
    @Override
    public List<Map<String, Object>> getOrgTree() {
        List<Map<String, Object>> rsp = metaPublicDao.getOrgTree();
        
        TreeBuilder treeBuilder = new TreeBuilder("orgId", "upOrgId", rsp);
        
        return treeBuilder.build();
    }
    
    @Override
    public List<Map<String, Object>> getObjUIInfo(String objTypeId) {
        return metaPublicDao.getObjUIInfo(objTypeId);
    }
    
	@Override
	public List<PenObjD> getObjAtrVal(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq) {
		return metaPublicDao.getObjAtrVal(objTypeId, objId, atrIdSeq, atrValSeq);
	}
}
