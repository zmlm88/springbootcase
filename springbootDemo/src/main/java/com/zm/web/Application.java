package com.zm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * spring boot batch
 * http://www.codingpedia.org/ama/spring-batch-tutorial-with-spring-boot-and-java-configuration/
 * http://blog.csdn.net/xiaoyu411502/article/details/48164311/
 * http://www.jianshu.com/p/e09d2370b796 
 * @author zmlm88@126.com
 *
 */
@EnableAsync
@SpringBootApplication
@RestController
public class Application {

	 @RequestMapping("/")
	    public @ResponseBody String greeting() {
	        return "index";
	    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    
}
