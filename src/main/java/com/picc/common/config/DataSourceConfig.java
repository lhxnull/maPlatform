//package com.picc.common.config;
//
//import java.util.Properties;
//
//import javax.naming.Context;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import ins.framework.jndi.JndiObjectFindFactoryBean;
//
//
//@Configuration
//public class DataSourceConfig {
//	/**
//	  * 配置weilogic数据源
//	 * @author: 李永强
//	 * @Desc: 描述
//	 * @return
//	 * @throws IllegalArgumentException
//	 * @throws NamingException
//	 * @createTime: 2019-11-26 10:13:37
//	 */
//	@Bean(name="dataSource")
//	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
//
//		JndiObjectFindFactoryBean bean = new JndiObjectFindFactoryBean();
//		Properties properties = new Properties();
//
//		properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
//		properties.put(Context.PROVIDER_URL, "t3://localhost:7003");
//		//weblogic账号
//		properties.put(Context.SECURITY_PRINCIPAL, "weblogic");
//		//weblogic密码
//		properties.put(Context.SECURITY_CREDENTIALS, "piccgis2014");
//
//
//		bean.setJndiEnvironment(properties);
//
//		bean.setResourceRef(true);
//		bean.setJndiName("maPlatformDatabases");
//		bean.setProxyInterface(DataSource.class);
//		bean.setLookupOnStartup(false);
//		bean.afterPropertiesSet();
//
//		return (DataSource) bean.getObject();
//	}
//}
