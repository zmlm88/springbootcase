package com.zm.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zm.web.util.menu.ModeList;


@Controller
@RequestMapping("/web/menu")
public class MenuController extends BaseController {
	
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public String tree( String parentId,Model model){
		List<ModeList> listMode = tMenuMapperService.findTMenuByParentId("1", "1");
		
		model.addAttribute("", parentId).addAttribute("menuTree", listMode);
		return "sys/menuTree";
	}

}
