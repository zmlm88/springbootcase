package com.zm.web.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.db.model.TUser;

@Controller
@RequestMapping("/web/sys/user")
public class UserController extends BaseController {
	
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String index(){
		return "/sys/userList";
	}
	
	@RequestMapping(value="/list", method = RequestMethod.POST)
	@ResponseBody
	public Page<TUser> list(@RequestBody Map<String,Object> param){
		final String userName = (String)param.get("userName");
		Integer pageNumber = (Integer)param.get("pageNumber");
		Integer limit = (Integer)param.get("limit");
		return tUserMapperService.selectByUserPage(userName, pageNumber, limit);
	}

}
