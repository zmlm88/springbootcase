package com.zm.web.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.util.menu.ModeList;

@Controller
@RequestMapping("/web/sys/menu")
public class MenuController extends BaseController {

	
	/**
	 * 菜单登录首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {
		return "sys/menuList";
	}

	/**
	 * 左边的树菜单
	 * 
	 * @param parentId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree(@RequestParam("parentId") final String parentId, Model model) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		List<ModeList> listMode = tMenuMapperService.findTMenuByParentId(parentId, shiroUser);
		model.addAttribute("menuTree", listMode);
		return "sys/menuTree";
	}
	/**
	 * 首页blank
	 * @return
	 */
	@RequestMapping(value = "/blank", method = RequestMethod.GET)
	public String blank() {
		return "sys/blank";
	}
}
