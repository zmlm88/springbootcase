package com.zm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
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
