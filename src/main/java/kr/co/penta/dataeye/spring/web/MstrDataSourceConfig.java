package kr.co.penta.dataeye.spring.web;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import kr.co.penta.dataeye.spring.config.properties.MstrMybatisSettings;
import kr.co.penta.dataeye.spring.mybatis.interceptor.QueryInterceptor;
import kr.co.penta.dataeye.spring.mybatis.interceptor.UpdateInterceptor;

@Configuration
@EnableTransactionManagement
public class MstrDataSourceConfig {
	
	@Autowired
	private MstrMybatisSettings mstrMybatisSettings;
	
	@Autowired
	private MstrPropertySource mstrPropertySource;
	
	@Bean(name = "mstrDataSource")
	//@ConfigurationProperties(prefix = "spring.datasource.mstr-ds")
	public DataSource dataSource() {
		//return DataSourceBuilder.create().build();
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(mstrPropertySource.getDriverClassName());
		dataSource.setUrl(mstrPropertySource.getUrl());
		dataSource.setUsername(mstrPropertySource.getUsername());
		dataSource.setPassword(mstrPropertySource.getPassword());
		return dataSource;
	}

	@Bean(name = "mstrSqlSessionFactory")
	public SqlSessionFactory mstrSqlSessionFactory(@Qualifier("mstrDataSource") DataSource mstrDataSource,
			ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mstrDataSource);
		sqlSessionFactoryBean
				.setMapperLocations(applicationContext.getResources(mstrMybatisSettings.getMapperLocations()));
		sqlSessionFactoryBean.setConfiguration(mstrMybatisSettings.getConfiguration());
		sqlSessionFactoryBean.setTypeHandlersPackage(mstrMybatisSettings.getTypeHandlersPackage());

        Interceptor[] plugins = {new QueryInterceptor(), new UpdateInterceptor()};
//        sqlSessionFactoryBean.setPlugins(plugins);
//        if ("oracle".equals(databaseType)) {
//	        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{
//	        	new TimestampTypeHandler(), new DateTypeHandler()
//	        });
//        }
		return sqlSessionFactoryBean.getObject();
	}

	@Bean(name = "mstrSqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier("mstrSqlSessionFactory") SqlSessionFactory mstrSqlSessionTemplate) throws Exception {
		return new SqlSessionTemplate(mstrSqlSessionTemplate);
	}
	
	@Bean(name = "mstrPlatformTransactionManager")
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource());
		transactionManager.setGlobalRollbackOnParticipationFailure(false);
		return transactionManager;
	}
}
