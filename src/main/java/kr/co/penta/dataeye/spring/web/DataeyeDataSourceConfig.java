package kr.co.penta.dataeye.spring.web;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.co.penta.dataeye.spring.config.properties.DataEyeMybatisSettings;
import kr.co.penta.dataeye.spring.mybatis.interceptor.QueryInterceptor;
import kr.co.penta.dataeye.spring.mybatis.interceptor.UpdateInterceptor;

@Configuration
@EnableTransactionManagement
public class DataeyeDataSourceConfig {
    
	@Autowired
    private DataEyeMybatisSettings dataEyeMybatisSettings;
    
    @Autowired
	private DataeyePropertySource dataeyePropertySource;
    
    @Bean(name="dataeyeDataSource")
    //@ConfigurationProperties(prefix = "spring.datasource.dataeye-ds")
    @Primary
    public DataSource dataSource() {

        //return DataSourceBuilder.create().build();
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(dataeyePropertySource.getDriverClassName());
		dataSource.setUrl(dataeyePropertySource.getUrl());
		dataSource.setUsername(dataeyePropertySource.getUsername());
		dataSource.setPassword(dataeyePropertySource.getPassword());
		return dataSource;
    }

    @Bean(name="dataeyePlatformTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    @Bean(name="dataeyeSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataeyeDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(dataEyeMybatisSettings.getMapperLocations()));
        sqlSessionFactoryBean.setConfiguration(dataEyeMybatisSettings.getConfiguration());
        sqlSessionFactoryBean.setTypeHandlersPackage(dataEyeMybatisSettings.getTypeHandlersPackage());
        
        Interceptor[] plugins = {new QueryInterceptor(), new UpdateInterceptor()};
//        sqlSessionFactoryBean.setPlugins(plugins);
//        if ("oracle".equals(databaseType)) {
//	        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{
//	        	new TimestampTypeHandler(), new DateTypeHandler()
//	        });
//        }
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name="dataeyeSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dataeyeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
