package com.zm.web.configuration.mybatis.dialect;

import com.zm.web.configuration.mybatis.Page;

/**
 * oracle 方言
 * 
 * @author zmlm88@126.com
 *
 */
public class OracleDialet implements IDialect {

	public String dialect(Page<?> page, StringBuffer sqlBuffer) {
		// 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
		int offset = (page.getPageNo() - 1) * page.getPageCount() + 1;
		sqlBuffer.insert(0, "select u.*, rownum _rownum from (").append(") u where rownum < ").append(offset + page.getPageCount());
		sqlBuffer.insert(0, "select * from (").append(") where _rownum >= ").append(offset);
		// 上面的Sql语句拼接之后大概是这个样子：
		// select * from (select u.*, rownum r from (select * from t_user) u
		// where rownum < 31) where r >= 16
		return sqlBuffer.toString();
	}

}
