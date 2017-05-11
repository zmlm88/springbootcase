package com.zm.web;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zm.web.configuration.mybatis.PageInterceptor;
import com.zm.web.configuration.mybatis.dialect.MySQLDialet;
import com.zm.web.cst.Const;

/**
 * spring boot batch
 * http://www.codingpedia.org/ama/spring-batch-tutorial-with-spring	
 * -boot-and-java-configuration/
 * http://blog.csdn.net/xiaoyu411502/article/details/48164311/
 * http://www.jianshu.com/p/e09d2370b796
 * 
 * 
 * http://www.jb51.net/article/60965.htm --bootstrap page
 * 
 * http://www.open-open.com/lib/view/open1454042809855.html
 * 
 * @author zmlm88@126.com
 *
 */
@EnableTransactionManagement(proxyTargetClass=true)//启动事务
@SpringBootApplication
@MapperScan(value="com.zm.web.db.dao",sqlSessionFactoryRef="mybatisSqlSessionFactory")
public class Application extends SpringBootServletInitializer {


	@Autowired
	DataSource dataSource;
	
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setFailFast(true);
		// 添加XML目录
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			sessionFactory.setMapperLocations(resolver.getResources(Const.MAPPER_LOCATIONS));
			PageInterceptor pageInterceptor = new PageInterceptor();
			// 设置配置文件方言
			pageInterceptor.setProperties(PropertiesLoaderUtils.loadProperties(new ClassPathResource(Const.CONFIG_PROPERTIES)), new MySQLDialet());
			sessionFactory.setPlugins(new Interceptor[] { pageInterceptor });
			// 设置setting文件
			sessionFactory.setConfigLocation(new ClassPathResource(Const.CONFIG_MYBATIS_LOCATION));
			return sessionFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
    static Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
