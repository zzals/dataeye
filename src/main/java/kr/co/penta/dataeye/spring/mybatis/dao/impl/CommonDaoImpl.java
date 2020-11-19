package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class CommonDaoImpl extends DataEyeDaoSupport implements CommonDao {
    @Override
	public <E> List<E> selectList(String queryId, DaoParam daoParam) {
	    return sqlSession.selectList(queryId, daoParam);
	}
	
	@Override
    public <T> T selectOne(String queryId, DaoParam daoParam) {
        return sqlSession.selectOne(queryId, daoParam);
    }
    
    @Override
    public void insert(String queryId, DaoParam daoParam) {
        sqlSession.insert(queryId, daoParam);
    }
    
    @Override
    public void update(String queryId, DaoParam daoParam) {
        sqlSession.update(queryId, daoParam);
    }
    
    @Override
    public void delete(String queryId, DaoParam daoParam) {
        sqlSession.delete(queryId, daoParam);
    }
    
    @Override
    public List<Map<String, Object>> dynamicSqlList(DaoParam daoParam, String SQL) {
        daoParam.put("SQL", SQL);
        return sqlSession.selectList("common.dynamicSql", daoParam);
    }
    
    @Override
    public Map<String, Object> dynamicSqlOne(DaoParam daoParam, String SQL) {
        daoParam.put("SQL", SQL);
        return sqlSession.selectOne("common.dynamicSql", daoParam);
    }
    
    @Override
	public Map<String, Object> getJdbcConnectInfo(String jdbcId) {
		return sqlSession.selectOne("common.getJdbcConnectInfo", jdbcId);
	}
    
    @Override
	public int execute(final String queryId, final Map<String, Object> parameter) {		
		return sqlSession.insert(queryId, parameter);
	}
}
