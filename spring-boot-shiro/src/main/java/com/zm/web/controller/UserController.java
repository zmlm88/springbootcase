package com.zm.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.zm.web.vo.ResultVo;

@Controller
@RequestMapping("/web/sys/user")
public class UserController extends BaseController {

	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
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
		return tUserMapperMBService.selectByUserPage(userName, pageNumber, limit);
	}

	
	/**
	 * 根据角色id和数据权限 ，查询用户
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/selectUserByRoleAndCodeDataPage", method = RequestMethod.POST)
	@ResponseBody
	public Page<TUser> selectUserByRoleAndCodeDataPage(@RequestBody Map<String, Object> param, HttpSession session) {
		final String roleId = (String) param.get("roleId");
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		final String codeDate= shiroUser.getTuser().getCodeData();
		final String userName = (String) param.get("userName");
		Integer pageNumber = (Integer) param.get("pageNumber");
		Integer limit = (Integer) param.get("limit");
		this.sessionSetAttribute(session, "userListUserName", userName);
		return tUserMapperMBService.selectUserByRoleAndCodeDataPage(roleId,codeDate,userName, pageNumber, limit);		
		
	}
	
	
	
	@RequestMapping(value = "/form")
	public String form(TUser userReq, Model model, @RequestParam("userOper") String userOper) {
		String id = userReq.getId();
		if (StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(userOper)) {
			if (userOper.equals(Const.OPER_STATUS.VIEW.toString()) || userOper.equals(Const.OPER_STATUS.MODIFY.toString())) {
				TUser user = tUserMapperMBService.selectByPrimaryKey(id);
				model.addAttribute("user", user);
				//查看或修改用户的角色
				model.addAttribute("userRoles",tRoleMapperService.selectRoleListByUserId(id));
			}else if(userOper.equals(Const.OPER_STATUS.ADD.toString())){
				
			}
		}
		//当前操作用户角色
		model.addAttribute("userOperRoles",tRoleMapperService.selectRoleListByCreateUserId(getShiroUser().getTuser().getId()));
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
					//添加用户
					this.tUserMapperMBService.addUserService(user,getShiroUser().getTuser());
					this.addMessage(redirectAttributes, "添加用户成功!");
				} else if (Const.OPER_STATUS.MODIFY.equals(userOper)) {
					this.tUserMapperMBService.updateUserService(user);
					this.addMessage(redirectAttributes, "修改用户成功!");
				}
			}catch(Exception e){
				this.addMessage(redirectAttributes, "用户操作异常!");
				e.printStackTrace();
			}
		}
		return "redirect:/web/sys/user/";
	}

	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResultVo del(@PathVariable String id){
		int result = this.tUserMapperMBService.deleteByPrimaryKey(id);
		if(result == 1)
			return new ResultVo(true,"操作成功");
		return new ResultVo(false,"操作失败"); 
	}
}
