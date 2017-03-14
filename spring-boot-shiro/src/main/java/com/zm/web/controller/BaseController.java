package com.zm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.zm.web.service.TMenuMapperService;
import com.zm.web.service.TUserMapperService;
/**
 * 
 * @author zhumin
 *
 */
public class BaseController {
	@Autowired
	TMenuMapperService tMenuMapperService;

	@Autowired
	TUserMapperService tUserMapperService;

}
