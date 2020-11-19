package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

public interface CommonDao {
    <E> List<E> selectList(String queryId, DaoParam daoParam);
    <T> T selectOne(String queryId, DaoParam daoParam);
    void insert(String queryId, DaoParam daoParam);
    void update(String queryId, DaoParam daoParam);
    void delete(String queryId, DaoParam daoParam);
    List<Map<String, Object>> dynamicSqlList(DaoParam daoParam, String SQL);
    Map<String, Object> dynamicSqlOne(DaoParam daoParam, String SQL);
    Map<String, Object> getJdbcConnectInfo(String jdbcId);
    int execute(final String queryId, final Map<String, Object> parameter);
}
