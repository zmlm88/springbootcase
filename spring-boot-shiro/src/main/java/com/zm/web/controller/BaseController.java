package com.zm.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.service.TMenuMapperService;
import com.zm.web.service.TRoleMapperService;
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
	
	@Autowired
	TRoleMapperService tRoleMapperService;
	
 
	
	public BaseController sessionSetAttribute(HttpSession session,String key,Object obj){
		session.setAttribute(key, obj);
		return this;
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 返回用户信息 
	 * @return
	 */
	public ShiroUser getShiroUser(){
		ShiroUser userInfo = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return userInfo;
	}

}
