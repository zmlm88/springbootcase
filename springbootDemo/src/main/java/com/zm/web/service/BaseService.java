package com.zm.web.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationTemplate;
import com.zm.web.db.dao.WorkTUserInfoMapper;

public class BaseService {
	
	@Autowired
	WorkTUserInfoMapper workTUserInfoMapper;
	@Autowired
	MybatisPaginationTemplate mybatisPaginationTemplate;
	
}
