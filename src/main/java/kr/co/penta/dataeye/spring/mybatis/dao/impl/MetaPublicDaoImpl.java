package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaPublicDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class MetaPublicDaoImpl extends DataEyeDaoSupport implements MetaPublicDao {
    private static final Logger LOG = LoggerFactory.getLogger(MetaPublicDaoImpl.class);
  
    @Override
    public List<Map<String, Object>> getObjTypeTree() {
        return sqlSession.selectList("metapublic.getObjTypeTree");
    }
    
    @Override
    public CamelMap getAtr(String objTypeId, Integer atrIdSeq) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("atrIdSeq", atrIdSeq);
        return sqlSession.selectOne("metapublic.getAtr", daoParam);
    }
    
    @Override
    public List<Map<String, Object>> getOrgTree() {
        return sqlSession.selectList("bdp_custom.getOrgTree");
    }
    
    @Override
    public List<Map<String, Object>> getObjUIInfo(String objTypeId) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId);
        return sqlSession.selectList("metapublic.getObjUIInfo", daoParam);
    }

	@Override
	public List<PenObjD> getObjAtrVal(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq) {
		DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("atrIdSeq", atrIdSeq).put("atrValSeq", atrValSeq);
        return sqlSession.selectList("metapublic.getObjAtrVal", daoParam);
	}
}
