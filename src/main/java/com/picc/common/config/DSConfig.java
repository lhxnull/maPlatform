//package com.picc.common.config;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//@Configuration
//@MapperScan(basePackages = "com.picc.*.mapper", sqlSessionFactoryRef = "sqlSessionFactory")
//public class DSConfig {
//
//	//获取dataSourcec
//		@Autowired
//		@Qualifier("dataSource")
//		private DataSource dataSource;
//
//		//获取application.propertis配置文件中的mybatis.mapper-locations
//		@Value("${mybatis-plus.mapper-locations}")
//		private String mapperLocations;
//
//
//
//		@Bean
//		public SqlSessionFactory sqlSessionFactory() throws Exception {
//			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//			factoryBean.setDataSource(dataSource);
//			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//
//			factoryBean.setMapperLocations(resolver.getResources(mapperLocations));
//			return factoryBean.getObject();
//		}
//
//	        @Bean
//		public DataSourceTransactionManager transactionManager() throws Exception {
//			return new DataSourceTransactionManager(dataSource);
//		}
//
//		@Bean
//		public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//			return new SqlSessionTemplate(sqlSessionFactory());
//		}
//
//
//
//
//}
