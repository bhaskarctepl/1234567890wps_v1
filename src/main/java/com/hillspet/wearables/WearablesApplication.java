package com.hillspet.wearables;

import java.beans.PropertyVetoException;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.dao.configuration.DataSourceConfig;
import com.mchange.v2.c3p0.C3P0Registry;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@ComponentScan(basePackages = { Constants.WEARABLES_BASE_PACKAGE })
@SpringBootApplication
@EnableAutoConfiguration
public class WearablesApplication extends SpringBootServletInitializer {

	private static final Logger LOGGER = LogManager.getLogger(WearablesApplication.class);

	@Autowired
	DataSourceConfig dataSourceConfig;

	/**
	 * @return
	 */
	@Bean(name = Constants.WEARABLES_TRANSACTION_MANAGER)
	public PlatformTransactionManager txManager() {
	    return new DataSourceTransactionManager(createDataSource()); 
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	/**
	 * It gets the information from configuration files and creates the data source
	 * object.
	 * 
	 * This data source will be used for quartz session factory, jpa session
	 * factory.
	 * 
	 * @return
	 */

	@Bean(name = Constants.DATA_SOURCE)
	@Primary
	public DataSource createDataSource() {
		/*
		 * when creating the data-source, we're indicating not to register with the JMX
		 * register. This is because on the constructor it will use a random id
		 * generated to register. we will set the identifier separately and then
		 * register manually
		 */
		ComboPooledDataSource dataSource = new ComboPooledDataSource(false);
		try {
			dataSource.setDriverClass(dataSourceConfig.getDriverClass());
		} catch (PropertyVetoException e) {

			LOGGER.error("Exception in setting driverClass : ", e);
		}
		dataSource.setUser(dataSourceConfig.getUser());
		/*
		 * set data-source name and identity so the pool is easily identifiable in the
		 * JMX console and in logs
		 */
		dataSource.setDataSourceName(dataSourceConfig.getDataSourceName());
		dataSource.setIdentityToken("wearables");
		dataSource.setPassword(dataSourceConfig.getPassword());
		dataSource.setForceSynchronousCheckins(dataSourceConfig.isForceSynchronousCheckins());
		dataSource.setTestConnectionOnCheckin(dataSourceConfig.isTestConnectionOnCheckin());
		dataSource.setTestConnectionOnCheckout(dataSourceConfig.isTestConnectionOnCheckout());
		dataSource.setAcquireIncrement(dataSourceConfig.getAcquireIncrement());
		dataSource.setMaxStatements(dataSourceConfig.getMaxStatements());
		dataSource.setMinPoolSize(dataSourceConfig.getMinPoolSize());
		dataSource.setMaxPoolSize(dataSourceConfig.getMaxPoolSize());
		dataSource.setNumHelperThreads(dataSourceConfig.getNumHelperThreads());
		dataSource.setCheckoutTimeout(dataSourceConfig.getCheckoutTimeout());
		dataSource.setIdleConnectionTestPeriod(dataSourceConfig.getIdleConnectionTestPeriod());
		dataSource.setMaxIdleTimeExcessConnections(dataSourceConfig.getMaxIdleTimeExcessConnections());
		dataSource.setMaxIdleTime(dataSourceConfig.getMaxIdleTime());
		dataSource.setDebugUnreturnedConnectionStackTraces(dataSourceConfig.isDebugUnreturnedConnectionStackTraces());
		dataSource.setJdbcUrl(dataSourceConfig.getJdbcUrl());
		dataSource.setInitialPoolSize(dataSourceConfig.getInitialPoolSize());
		LOGGER.debug("Created a new datasource: {}", dataSource.toString());
		// register the data-source with C3P0 MBEAN manager
		C3P0Registry.reregister(dataSource);
		return dataSource;
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(1024 * 1024 * 10));
		factory.setMaxRequestSize(DataSize.ofBytes(1024 * 1024 * 1024));
		return factory.createMultipartConfig();
	}

	public static void main(String[] args) {
		new WearablesApplication().configure(new SpringApplicationBuilder(WearablesApplication.class)).run(args);
		LOGGER.info("Info level log message");
		LOGGER.debug("Debug level log message");
		LOGGER.error("Error level log message");
	}
}
