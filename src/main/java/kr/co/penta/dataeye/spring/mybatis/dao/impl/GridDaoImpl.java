package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.GridDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class GridDaoImpl extends DataEyeDaoSupport implements GridDao {
    private static final Logger LOG = LoggerFactory.getLogger(GridDaoImpl.class);
    
	@Override
    public List<Map<String, Object>> getJqGridPagingList(String queryId, JqGridDaoParam jqGridDaoParam) {
	    String mainSql = getOriginalSql(queryId, jqGridDaoParam);
	    LOG.debug("main-sql : {}", mainSql);
	    if (jqGridDaoParam.isFilters()) {
	        String filter = jqGridDaoParam.getJgGridFilters().getFilterWhere();
	        LOG.debug("where filter : {}", filter);
	        
	        StringBuilder sb = new StringBuilder();
            sb.append("SELECT T101.* ").append("\n");
            sb.append("FROM (").append("\n");
            sb.append(mainSql).append("\n");
            sb.append(") T101").append("\n");
            sb.append("WHERE ").append(filter).append("\n");
            
            mainSql = sb.toString();
	    }
	    jqGridDaoParam.put("MAIN_SQL", mainSql);
        return sqlSession.selectList("common.pagingList", jqGridDaoParam);
    }
	
	@Override
    public List<Map<String, Object>> getJqGridNonPagingList(String queryId, JqGridDaoParam jqGridDaoParam) {
	    String mainSql = getOriginalSql(queryId, jqGridDaoParam);
        LOG.debug("main-sql : {}", mainSql);
        
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT T101.* ").append("\n");
        sb.append("FROM (").append("\n");
        sb.append(mainSql).append("\n");
        sb.append(") T101").append("\n");
        mainSql = sb.toString();
        
        if (jqGridDaoParam.isFilters()) {
            String filter = jqGridDaoParam.getJgGridFilters().getFilterWhere();
            LOG.debug("where filter : {}", filter);
            sb.append("WHERE ").append(filter).append("\n");
        }
        String sidx = jqGridDaoParam.getString("sidx");
        String sord = jqGridDaoParam.getString("sord");
        if (!"".equals(sidx)) {
            sb.setLength(0);
            if (NumberUtils.isParsable(sidx)) {
                sb.append("\nORDER BY ").append(sidx).append(" ").append(sord);
            } else {
                sb.append("\nORDER BY T101.").append(sidx).append(" ").append(sord);
            }
            mainSql += sb.toString();
        }
        
        jqGridDaoParam.put("SQL", mainSql);
        return sqlSession.selectList("common.dynamicSql", jqGridDaoParam);
    }
    
    @Override
    public List<Map<String, Object>> getJqGridList(String queryId, JqGridDaoParam jqGridDaoParam) {
        String mainSql = getOriginalSql(queryId, jqGridDaoParam);
        LOG.debug("main-sql : {}", mainSql);
                
        jqGridDaoParam.put("SQL", mainSql);
        return sqlSession.selectList("common.dynamicSql", jqGridDaoParam);
    }
	
    @Override
    public Integer totalCount(String queryId, JqGridDaoParam jqGridDaoParam) {
        String mainSql = getOriginalSql(queryId, jqGridDaoParam);
        LOG.debug("main-sql : {}", mainSql);
        if (jqGridDaoParam.isFilters()) {
            String filter = jqGridDaoParam.getJgGridFilters().getFilterWhere();
            LOG.debug("where filter : {}", filter);
            
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT T101.* ").append("\n");
            sb.append("FROM (").append("\n");
            sb.append(mainSql).append("\n");
            sb.append(") T101").append("\n");
            sb.append("WHERE ").append(filter).append("\n");
            
            mainSql = sb.toString();
        }
        
        jqGridDaoParam.put("MAIN_SQL", mainSql);
        return sqlSession.selectOne("common.totalCount", jqGridDaoParam);
    }
}
