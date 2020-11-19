package kr.co.penta.dataeye.spring.mybatis.dao.support;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DataEyeDaoSupport {
    @Autowired
    @Qualifier("dataeyeSqlSessionTemplate")
    protected SqlSession sqlSession;
    
    public String getOriginalSql(String queryId, Map<String, Object> daoParam) {
        BoundSql boundSql = sqlSession.getConfiguration().getMappedStatement(queryId).getSqlSource().getBoundSql(daoParam);
        String sql = boundSql.getSql();
        Object paramObj = boundSql.getParameterObject();
        if(paramObj != null) {
            List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
            for(ParameterMapping mapping : paramMapping){
                String propValue = mapping.getProperty();       
                sql=sql.replaceFirst("\\?", "#{"+propValue+"}");
            }
        }
        return sql;
    }
}