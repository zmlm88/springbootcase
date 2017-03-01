package com.zm.web.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 结果返回类
 * @author zmlm88@126.com
 *
 */
public class ResultVo  implements Serializable {
	private static final long serialVersionUID = -6546528328109400942L;

	public Map<String, Object> result = new HashMap<String, Object>();

	/**
	 * 空构造函数用于jackjson反序列化
	 */
	public ResultVo() {
		super();
	}

	public ResultVo(boolean isSuccess) {
		if (isSuccess)
			renderResultSuccess("操作成功!");
		else
			renderResultError("操作失败!");
	}

	public ResultVo(boolean isSuccess, Object message) {
		if (isSuccess)
			renderResultSuccess(message);
		else
			renderResultError(message);
	}

	public void renderResultSuccess(Object message) {
		result.put("success", true);
		result.put("message", message);
	}

	public void renderResultError(Object message) {
		result.put("error", true);
		result.put("message", message);
	}

	public void putValue(String key, Object value) {
		result.put(key, value);
	}
	
	public Object getValue(String key){
		return result.get(key);
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "";
		}
	}
	
	public static boolean isSuccess(final ResultVo result) {
		if(result == null)
			return false;
		
		if (StringUtils.isEmpty(result.result.get("success"))) {
			return false;
		}
		return (boolean) result.result.get("success");
	}
}