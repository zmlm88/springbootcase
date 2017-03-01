package com.zm.web.configuration.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
/**
 * redis config
 * @author zhumin
 *
 */
@Configuration
public class RedisConfig {
	
    @Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
    	
		return new StringRedisTemplate(connectionFactory);
	}
    
    @Bean
    RedissonClient  redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("192.16.150.103:6379").setDatabase(1);
    	return Redisson.create(config);
    }
    
	  
}
