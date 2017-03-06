package com.zm.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.db.dao.WorkTUserInfoMapper;
import com.zm.web.db.model.WorkTUserInfo;

@Service
public class TestService {
	@Autowired
	WorkTUserInfoMapper workTUserInfoMapper;
	
	public WorkTUserInfo selectByPrimaryKey(String id){
		return workTUserInfoMapper.selectByPrimaryKey(id);
	}
	
	public Page findPage(String userId){
		Page<WorkTUserInfo>  page = new Page(1,3);
		workTUserInfoMapper.findPageWorkTUserInfo("1", page);
		System.out.println(page);
		return page;
	}
}
