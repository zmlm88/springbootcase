package com.zm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zm.web.util.menu.ModeList;

@Controller
@RequestMapping("/web/sys")
public class LoginController extends BaseController{
	/**
	 * 登錄
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model){
		ModeList modeList = this.tMenuMapperService.selectModeListByUser("1");
		if(!StringUtils.isEmpty(modeList)){
			model.addAttribute("modeList", modeList);
		}else{
			return "sys/noPression";
		}
		return "sys/sysIndex";
	}
}
