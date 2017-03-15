package com.zm.web.configuration.mybatis.dialect;

import com.zm.web.configuration.mybatis.Page;

/**
 * mybatis 方言
 * 
 * @author zmlm88@126.com
 *
 */
public class MySQLDialet implements IDialect {

	public String dialect(Page<?> page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
		int offset = (page.getPageNo() - 1) * page.getPageCount();
		sqlBuffer.append(" limit ").append(page.getPageNo()).append(",").append(page.getPageCount());
		return sqlBuffer.toString();
	}

}
