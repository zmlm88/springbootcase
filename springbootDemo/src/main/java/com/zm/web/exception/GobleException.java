package com.zm.web.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.zm.web.vo.ResultVo;

/**
 * 
 * @author zmlm88@126.com
 *  捕获全局异常
 */
@RestControllerAdvice
@EnableWebMvc
public class GobleException {

	Logger log = LoggerFactory.getLogger("error");
	
	@ExceptionHandler(value = { Exception.class })
	public ResultVo handleConflict(HttpServletRequest req, Exception e) throws IOException {
		ResultVo rv = new ResultVo();
		if (e instanceof AsyncRequestTimeoutException) {
			rv.renderResultError("正在处理中");
		} else {
			rv.renderResultError(ExceptionUtils.getRootCause(e.getCause()));
		}
		log.info(String.format("错误信息==请求资源:【%s】,请求方式【%s】,请求参数【%s】", req.getRequestURI(),req.getMethod(),getParam(req)),e);
		e.printStackTrace();
		return rv;
	}
	/**
	 * 组装参数
	 * @param request
	 * @return
	 */
	private String getParam(HttpServletRequest request){
		  Map<String, String[]> params = request.getParameterMap(); 
		  StringBuffer queryString = new StringBuffer("");  
	        for (String key : params.keySet()) {  
	            String[] values = params.get(key);  
	            for (int i = 0; i < values.length; i++) {  
	                String value = values[i];  
	                queryString = queryString.append( key + "=" + value + "&");
	            }  
	        }  
	        // 去掉最后一个空格  
	     return queryString.toString().substring(0, queryString.length() - 1); 
	}
}
