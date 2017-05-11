package com.zm.web.configuration.filter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * shiro 配置
 * 
 * @author zhumin
 *
 */
@Configuration
public class ShiroFilterConfig extends WebMvcConfigurerAdapter {

	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean fitler = new FilterRegistrationBean();
		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
		delegatingFilterProxy.setTargetFilterLifecycle(true);
		fitler.setFilter(delegatingFilterProxy);
		Set<String> urlPatterns = new HashSet<String>();
		urlPatterns.add("/*");
		fitler.setUrlPatterns(urlPatterns);
		return fitler;
	}

}
