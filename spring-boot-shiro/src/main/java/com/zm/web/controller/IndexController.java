package com.zm.web.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.service.TMenuMapperService;

@Controller
public class IndexController {

	@Autowired
	TMenuMapperService tMenuMapperService;

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "login";
	}

	/**
	 * 用户退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if (!StringUtils.isEmpty(shiroUser)) {
			SecurityUtils.getSubject().logout();
		}
		return "redirect:/sys/login";
	}

	/**
	 * 403无权限页面
	 * 
	 * @return
	 */
	@RequestMapping("/403")
	public String unauthorizedRole() {
		return "/error/403";
	}
}
