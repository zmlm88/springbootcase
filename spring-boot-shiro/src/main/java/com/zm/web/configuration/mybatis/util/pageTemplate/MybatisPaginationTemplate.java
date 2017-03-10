package com.zm.web.configuration.mybatis.util.pageTemplate;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zm.web.configuration.mybatis.Page;
/**
 * 模板模式和钩子程序封装分页
 * @author zmlm88@126.com
 *
 */
@Service
public class MybatisPaginationTemplate {

	/** 页码 */
	protected int pageNo;
	/** 每页记录条数 */
	protected int pageCount;
	
	public MybatisPaginationTemplate setStartAndLength(int pageNo,int pageCount){
		this.pageNo = pageNo;
		this.pageCount = pageCount;
		return this;
	}
	
	public <T> Page<?> execute(MybatisPaginationCallback action){
		Page<T> page = new Page<T>(pageNo,pageCount);
		List<T> resultList = action.doPagination(page);
		page.setResultList(resultList);
		return page;
	}
	
}
