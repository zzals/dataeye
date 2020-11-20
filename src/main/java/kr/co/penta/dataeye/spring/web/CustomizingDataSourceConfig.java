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

import kr.co.penta.dataeye.spring.config.properties.CustomizingMybatisSettings;
import kr.co.penta.dataeye.spring.mybatis.interceptor.QueryInterceptor;
import kr.co.penta.dataeye.spring.mybatis.interceptor.UpdateInterceptor;

@Configuration
@EnableTransactionManagement
public class CustomizingDataSourceConfig {
    
	@Autowired
    private CustomizingMybatisSettings customizingMybatisSettings;
    
    @Autowired
	private CustomizingPropertySource customizingPropertySource;
    
    @Bean(name="customizingDataSource")
    //@ConfigurationProperties(prefix = "spring.datasource.dataeye-ds")
    public DataSource dataSource() {
        //return DataSourceBuilder.create().build();
    	
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName(customizingPropertySource.getDriverClassName());
		dataSource.setUrl(customizingPropertySource.getUrl());
		dataSource.setUsername(customizingPropertySource.getUsername());
		dataSource.setPassword(customizingPropertySource.getPassword());
		return dataSource;
    }

    @Bean(name="customizingSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("customizingDataSource") DataSource customizingDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(customizingDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(customizingMybatisSettings.getMapperLocations()));
        sqlSessionFactoryBean.setConfiguration(customizingMybatisSettings.getConfiguration());
        sqlSessionFactoryBean.setTypeHandlersPackage(customizingMybatisSettings.getTypeHandlersPackage());
        
        Interceptor[] plugins = {new QueryInterceptor(), new UpdateInterceptor()};
//        sqlSessionFactoryBean.setPlugins(plugins);
//        if ("oracle".equals(databaseType)) {
//	        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{
//	        	new TimestampTypeHandler(), new DateTypeHandler()
//	        });
//        }
        return sqlSessionFactoryBean.getObject();
    }
    
    @Bean(name = "customizingSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("customizingSqlSessionFactory") SqlSessionFactory customizingSqlSessionTemplate) throws Exception {
		return new SqlSessionTemplate(customizingSqlSessionTemplate);
	}
    
    @Bean(name="customizingPlatformTransactionManager")
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }

    
}
