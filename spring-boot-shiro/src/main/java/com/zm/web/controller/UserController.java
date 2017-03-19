package com.zm.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.cst.Const;
import com.zm.web.db.model.TUser;
import com.zm.web.util.CustUtils;

@Controller
@RequestMapping("/web/sys/user")
public class UserController extends BaseController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(RedirectAttributes attr) {
		return "/sys/userList";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Page<TUser> list(@RequestBody Map<String, Object> param, HttpSession session) {
		final String userName = (String) param.get("userName");
		Integer pageNumber = (Integer) param.get("pageNumber");
		Integer limit = (Integer) param.get("limit");

		this.sessionSetAttribute(session, "userListUserName", userName);
		return tUserMapperService.selectByUserPage(userName, pageNumber, limit);
	}

	@RequestMapping(value = "/form")
	public String form(TUser userReq, Model model, @RequestParam("userOper") String userOper) {
		String id = userReq.getId();
		if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(userOper)) {
			if (userOper.equals(Const.OPER_STATUS.VIEW.toString()) || userOper.equals(Const.OPER_STATUS.MODIFY.toString())) {
				TUser user = tUserMapperService.selectByPrimaryKey(id);
				model.addAttribute("user", user);
			}
		}
		model.addAttribute("userOper", userOper);
		return "sys/userForm";
	}

	/*
	 * 用户操作
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(TUser user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String userOper = request.getParameter("userOper");
		if (StringUtils.isNotEmpty(userOper)) {
			try{
				if (Const.OPER_STATUS.ADD.toString().equals(userOper)) {
					user.setId(CustUtils.genReqID());
					user.setPassword(CustUtils.genPassword(user.getPassword()));
					this.tUserMapperService.insertSelective(user);
					this.addMessage(redirectAttributes, "添加用户成功!");
				} else if (Const.OPER_STATUS.MODIFY.equals(userOper)) {
					String password = user.getPassword();
					if(StringUtils.isNotEmpty(password)){
						String md5Passwd = CustUtils.genPassword(password);
						ShiroUser userInfo = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
						userInfo.decrypt();
						TUser userT = userInfo.getTuser();
						if(!md5Passwd.equals(userT.getPassword())){
							user.setPassword(md5Passwd);
						}
						userInfo.encrypt();
					}
					this.tUserMapperService.updateByPrimaryKeySelective(user);
					this.addMessage(redirectAttributes, "修改用户成功!");
				}
			}catch(Exception e){
				this.addMessage(redirectAttributes, "用户操作异常!");
				e.printStackTrace();
			}
		}
		return "redirect:/web/sys/user/index";
	}

}
