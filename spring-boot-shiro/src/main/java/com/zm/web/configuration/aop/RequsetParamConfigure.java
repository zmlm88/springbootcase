package com.zm.web.configuration.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequsetParamConfigure {

    // 定义切点Pointcut
    @Pointcut("execution(* com.zm.web.controller.*Controller.list*(..))")
    public void webReq() {
    }

    @Before("webReq()")
    public void doAround(JoinPoint  pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        Map<String,String[]> params = request.getParameterMap();
        for(String key:params.keySet()){
        	String[] values=params.get(key);
        	for (int i = 0;i<values.length;i++){
        		String value=values[i];
        		System.out.println(key +"  value==="+value);
        	}
        	
        }
        
//        request.getSession().setAttribute(name, value);
 
    }
    
    @AfterReturning(returning = "ret", pointcut = "webReq()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
       //logger.info("RESPONSE : " + ret);
    }
	
}
