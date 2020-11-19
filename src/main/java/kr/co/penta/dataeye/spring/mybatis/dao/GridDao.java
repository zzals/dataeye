package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;

public interface GridDao {
    List<Map<String, Object>> getJqGridPagingList(String queryId, JqGridDaoParam daoParam);
    List<Map<String, Object>> getJqGridNonPagingList(String queryId, JqGridDaoParam daoParam);
    List<Map<String, Object>> getJqGridList(String queryId, JqGridDaoParam daoParam);
    Integer totalCount(String queryId, JqGridDaoParam daoParam);
}
