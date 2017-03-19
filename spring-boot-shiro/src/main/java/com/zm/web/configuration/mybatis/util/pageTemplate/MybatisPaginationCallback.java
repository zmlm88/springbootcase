package com.zm.web.configuration.mybatis.util.pageTemplate;

import java.util.List;

import com.zm.web.configuration.mybatis.Page;

/**
 * mybatis template 钩子程序
 * 
 * @author zmlm88@126.com
 *
 */
public interface MybatisPaginationCallback {
	<T> List<T> doPagination(Page<?> page);
}
