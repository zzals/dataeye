package kr.co.penta.dataeye.spring.mybatis.interceptor;


import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Intercepts
(
     {
          @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
     }
)
public class UpdateInterceptor implements Interceptor
{
    private Logger log = LogManager.getLogger(getClass());
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        StatementHandler handler = (StatementHandler) invocation.getTarget();
        
        String sql = handler.getBoundSql().getSql();
        String param = handler.getParameterHandler().getParameterObject() != null ? handler.getParameterHandler().getParameterObject().toString() : "";

        System.out.println("--------------------------------");
        System.out.println(sql);
        System.out.println(param);
        System.out.println("--------------------------------");
        System.out.println(invocation.getMethod().getName());
        System.out.println(invocation.getMethod().getAnnotations());
        System.out.println("--------------------------------");
        
        log.debug(sql);
        log.debug(param);
       
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target)
    {
        return Plugin.wrap(target, this);
    }


    @Override
    public void setProperties(Properties properties)
    {
    }
}