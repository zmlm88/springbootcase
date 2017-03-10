package com.zm.web.configuration.mybatis.dialect;

import com.zm.web.configuration.mybatis.Page;
/**
 * mybatis 方言
 * @author zmlm88@126.com
 *
 */
public interface IDialect {
	public String dialect(Page<?> page,StringBuffer sqlBuffer);
}
