package xmx.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan(basePackages = "xmx")
@EnableTransactionManagement
public class SpringHibernateConfiguration {

	@Bean
	public ComboPooledDataSource dataSource() throws IOException {
		ComboPooledDataSource source = new ComboPooledDataSource();
		
		Resource resource = new ClassPathResource("jdbc.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		
		try {
			source.setDriverClass(props.getProperty("jdbc.driver"));
			source.setJdbcUrl(props.getProperty("jdbc.url"));
			source.setUser(props.getProperty("jdbc.username"));
			source.setPassword(props.getProperty("jdbc.password"));
			source.setInitialPoolSize(Integer.parseInt(props.getProperty("jdbc.initialPoolSize")));
			source.setMaxPoolSize(Integer.parseInt(props.getProperty("jdbc.maxPoolSize")));
			source.setMinPoolSize(Integer.parseInt(props.getProperty("jdbc.minPoolSize")));
			source.setMaxIdleTime(Integer.parseInt(props.getProperty("jdbc.maxIdleTime")));
			source.setIdleConnectionTestPeriod(Integer.parseInt(props.getProperty("jdbc.idleConnectionTestPeriod")));

			return source;
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws IOException {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(dataSource());
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.format_sql", "true");
		properties.setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		bean.setHibernateProperties(properties);
		
		bean.setPackagesToScan("xmx");
		bean.setMappingLocations(new PathMatchingResourcePatternResolver().getResources("classpath:xmx/mapping/*.hbm.xml"));
		return bean;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() throws IOException {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource());
		return manager;
	}
}
