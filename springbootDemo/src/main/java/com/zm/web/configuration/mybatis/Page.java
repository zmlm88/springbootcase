package com.zm.web.configuration.mybatis;

import java.util.List;

/**
 * page 实体
 * @author zmlm88@126.com
 *
 */
public class Page<T>{
	/** 页码 */
	protected int pageNo;
	/** 每页记录条数 */
	protected int pageCount;
	/** 总页数 */
	protected int totalPage;
	/** 总记录条数 */
	protected int totalCount = -1;

	/** 用于存放查询结果 */
	protected List<T> resultList;

	public Page(int pageNo, int pageCount) {
		if (pageNo <= 0) {
			throw new IllegalArgumentException("pageNo must be greater than 0.");
		}
		if (pageCount <= 0) {
			throw new IllegalArgumentException("pageCount must be greater than 0.");
		}
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		if (totalCount < 0) { // 如果总数为负数, 表未设置
			totalPage = 0;
		} else { // 计算总页数
			totalPage = (totalCount / pageCount) + (totalCount % pageCount == 0 ? 0 : 1);
		}
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
