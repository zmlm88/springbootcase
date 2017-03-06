package com.zm.web.configuration.db;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.zm.web.configuration.mybatis.PageInterceptor;
import com.zm.web.configuration.mybatis.dialect.MySQLDialet;
import com.zm.web.cst.Const;

/**
 * 数据库配置
 * @author zmlm88@126.com
 *
 *@MapperScan(basePackages=Const.PACKAGE_CONFIG_MYBATIS）必须修改
 */

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages=Const.PACKAGE_CONFIG_MYBATIS,sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfiguration implements TransactionManagementConfigurer {
	
	 	@Value("${jdbc.driver}")
	    private String driver;
	    @Value("${jdbc.url}")
	    private String url;
	    @Value("${jdbc.username}")
	    private String username;
	    @Value("${jdbc.password}")
	    private String password;
	    @Value("${jdbc.maxActive}")
	    private int maxActive;
	    @Value("${jdbc.maxIdel}")
	    private int maxIdel;
	    @Value("${jdbc.maxWait}")
	    private long maxWait;
	    @Value("${jdbc.validationQuery}")
	    private String validationQuery;
	    
	    
	    @Bean(destroyMethod = "close", initMethod = "init")
	    public DruidDataSource  dataSource(){
	    	DruidDataSource dataSource = new DruidDataSource();
	        dataSource.setDriverClassName(driver);
	        dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
	        dataSource.setMaxActive(maxActive);
	        dataSource.setMaxWait(maxWait);
	        dataSource.setValidationQuery(validationQuery);
	        dataSource.setTestOnBorrow(true);
	        return dataSource;
	    }
	    
	    @Bean
	    public SqlSessionFactory sqlSessionFactory() throws Exception {
	        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setFailFast(true);
	        //添加XML目录
	        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	        try {
	        	sessionFactory.setMapperLocations(resolver.getResources(Const.MAPPER_LOCATIONS));
		        PageInterceptor pageInterceptor = new PageInterceptor();
		        //设置配置文件方言
		        pageInterceptor.setProperties(PropertiesLoaderUtils.loadProperties(new ClassPathResource(Const.CONFIG_PROPERTIES)),new MySQLDialet());
		        sessionFactory.setPlugins(new Interceptor[]{pageInterceptor});
		        return sessionFactory.getObject();
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new RuntimeException(e);
	        }
	    }

		public PlatformTransactionManager annotationDrivenTransactionManager() {
			 return new DataSourceTransactionManager(dataSource());
		}
		
		
		
}
