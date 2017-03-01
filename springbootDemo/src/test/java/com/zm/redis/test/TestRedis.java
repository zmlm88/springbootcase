package com.zm.redis.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zm.web.Application;

/**
 * redis 并发测试
 * 
 * @author zhumin
 *
 */
@RunWith(SpringRunner.class)
//@WebMvcTest(RedisController.class)
@ContextConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestRedis {

  @Autowired
  WebApplicationContext webApplicationConnect;  
  
  MockMvc mvc1,mvc2; 
  
   @Before  
    public void setUp() throws JsonProcessingException {  
	   mvc1 = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();  
	   mvc2= MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();  
    }  

	
	@Test
	public void redisTest() throws Exception {
		this.mvc1.perform(get("/redis/redisOper1"));
	}
	
	@Test
	public void redisTest2() throws Exception {
		final MockMvc mvc11 =  mvc1;
		final MockMvc mvc22 =  mvc2;
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				try {
					mvc11.perform(get("/redis/redisOper2"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	
		new Thread( new Runnable() {
			
			@Override
			public void run() {
				try {
					mvc22.perform(get("/redis/redisOper3"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();		
		
		
	}

}
