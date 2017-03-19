package com.zm.web.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationTemplate;
import com.zm.web.db.dao.TMenuMapper;
import com.zm.web.db.dao.TUserMapper;

public class BaseService {

	@Autowired
	MybatisPaginationTemplate mybatisPaginationTemplate;
	@Autowired
	TMenuMapper tMenuMapper;
	@Autowired
	TUserMapper tUserMapper;

}
