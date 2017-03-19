package com.zm.web.configuration.filter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.opensymphony.sitemesh.webapp.SiteMeshFilter;

@Configuration
public class SiteMeshFilterConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FilterRegistrationBean siteMeshFilter() {
		FilterRegistrationBean fitler = new FilterRegistrationBean();
		SiteMeshFilter siteMeshFilter = new SiteMeshFilter();
		fitler.setFilter(siteMeshFilter);
		Set<String> urlPatterns = new HashSet<String>();
		urlPatterns.add("/web/*");
		urlPatterns.add("/sys/*");
		fitler.setUrlPatterns(urlPatterns);
		return fitler;
	}
}