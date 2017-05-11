package com.zm.web.configuration.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

/**
 * Hbase 配置
 * @author zhumin
 *
 */
public class HbaseConfig {
	
	@Bean
	public org.apache.hadoop.conf.Configuration configuration() {
	    return  HBaseConfiguration.create();
	}
	
	@Bean(name = "hbaseTemplate")
	public HbaseTemplate hbaseTemplate() {
	    return new HbaseTemplate(configuration());
	}
}
