package com.zm.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationCallback;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationTemplate;
import com.zm.web.db.dao.WorkTUserInfoMapper;
import com.zm.web.db.model.WorkTUserInfo;

@Service
public class TestService {
	@Autowired
	WorkTUserInfoMapper workTUserInfoMapper;
	

	@Autowired
	MybatisPaginationTemplate mybatisPaginationTemplate;
	
	
	public WorkTUserInfo selectByPrimaryKey(String id){
		return workTUserInfoMapper.selectByPrimaryKey(id);
	}
	
 
	/**
	 * 查询例子
	 * @param userId
	 * @return
	 */
	public Page<WorkTUserInfo> findPageTemp(String userId){
		@SuppressWarnings("unchecked")
		Page<WorkTUserInfo> page = (Page<WorkTUserInfo>) mybatisPaginationTemplate.setStartAndLength(1, 3).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) workTUserInfoMapper.findPageWorkTUserInfo(userId, page);
			}
		});
		return page;
	}
	
	public Page<WorkTUserInfo> findPageTempCase(final String userId){
		@SuppressWarnings("unchecked")
		Page<WorkTUserInfo> page = (Page<WorkTUserInfo>) mybatisPaginationTemplate.setStartAndLength(1, 3).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) workTUserInfoMapper.findPageTempCase(userId, page);
			}
		});
		return page;
	}
	
	
}
