package com.zm.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zm.web.vo.ResultVo;

/**
 * 
 * @author zhumin
 *  捕获全局异常
 */
@RestControllerAdvice
@EnableWebMvc
public class GobleException {
	
	@ExceptionHandler(value = {Exception.class})
    public ResultVo handleConflict(HttpServletRequest req, Exception e) {
		ResultVo rv = new ResultVo();
		if(e instanceof AsyncRequestTimeoutException){
			rv.renderResultError("正在处理中");
		}else{
			rv.renderResultError(ExceptionUtils.getRootCause(e.getCause()));
		}
		System.out.println("异常捕获"+e.getMessage());
		e.printStackTrace();
        return rv;
    }
}
