package com.zm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/web/sys")
public class IndexController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "sys/sysLogin";
	}
}
