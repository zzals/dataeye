package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import kr.co.penta.dataeye.spring.mybatis.dao.AprvDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "aprvDao")
public class AprvDaoImpl extends DataEyeDaoSupport implements AprvDao {

    @Override
    public <T> T selectOne(DaoParam daoParam) {
        final String queryId = (String) daoParam.get("queryId");
        return sqlSession.selectOne(queryId, daoParam);
    }

    @Override
    public <E> List<E> selectList(DaoParam daoParam) {
        final String queryId = (String) daoParam.get("queryId");
        return sqlSession.selectList(queryId, daoParam);
    }

    @Override
    public void insert(DaoParam daoParam) {
        final String queryId = (String) daoParam.get("queryId");
        sqlSession.insert(queryId, daoParam);
    }

    @Override
    public void update(DaoParam daoParam) {
        final String queryId = (String) daoParam.get("queryId");
        sqlSession.update(queryId, daoParam);
    }

    @Override
    public void delete(DaoParam daoParam) {
        final String queryId = (String) daoParam.get("queryId");
        sqlSession.delete(queryId, daoParam);
    }
}
