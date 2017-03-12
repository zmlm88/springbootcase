package com.zm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.zm.web.service.TMenuMapperService;

public class BaseController {
	@Autowired
	TMenuMapperService tMenuMapperService;

}
