package com.zm.web.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationTemplate;
import com.zm.web.db.dao.TMenuMapper;
import com.zm.web.db.dao.TRoleMapper;
import com.zm.web.db.dao.TRoleMenuMapper;
import com.zm.web.db.dao.TUserMapper;
import com.zm.web.db.dao.TUserRoleMapper;


public class BaseService {

	@Autowired
	MybatisPaginationTemplate mybatisPaginationTemplate;
	@Autowired
	TMenuMapper tMenuMapper;
	@Autowired
	TUserMapper tUserMapper;
	@Autowired
	TUserRoleMapper tUserRoleMapper;
	@Autowired
	TRoleMapper tRoleMapper;
	@Autowired
	TRoleMenuMapper tRoleMenuMapper;
}
