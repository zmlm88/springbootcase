package com.zm.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.cst.Const;
import com.zm.web.db.model.TMenu;
import com.zm.web.db.model.TRole;

/**
 * 角色管理
 * @author zhumin
 *
 */
@Controller
@RequestMapping("/web/sys/role")
public class RoleController extends BaseController {
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(RedirectAttributes attr) {
		return "/sys/roleList";
	}
	
	@RequestMapping(value = "/tree")
	public String tree(Model model) {
		ShiroUser userInfo = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		 List<TRole> listRole = tRoleMapperService.selectRoleListByUserCodeData(userInfo.getTuser().getCodeData());
		model.addAttribute("roleList", listRole);
		return "sys/roleTree";
	}	
	
	
	@RequestMapping(value = "none")
	public String none() {
		return "sys/roleNone";
	}
	
	/**
	 * 角色查询用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/roleUserList/{id}", method = RequestMethod.GET)
	public String roleUser(@PathVariable String id,Model model){
		model.addAttribute("roleId", id);
		return "sys/roleUserList";
	}
	/**
	 * 角色管理
	 * @return
	 */
	@RequestMapping(value = "/roleForm", method = RequestMethod.GET)
	public String roleForm(Model model,HttpServletRequest request){
		String oper = request.getParameter("oper");
		if(Const.OPER_STATUS.MODIFY.toString().equals(oper)){
			String roleId = request.getParameter("roleId");
			List<TMenu>  listTMenu =  tMenuMapperService.selectMenuByRoleId(roleId);
			model.addAttribute("roleMenu", listTMenu);
			//查询角色
			TRole tRole = tRoleMapperService.selectRoleById(roleId);
			model.addAttribute("userRole", tRole);
		}
		List<TMenu> listMenu =  tMenuMapperService.selectMenuByUserIdAndRole(getShiroUser().tuser.getId());
		model.addAttribute("userRoleList", listMenu);
		model.addAttribute("roleOper",oper);
		return "sys/roleForm";
	}
	/**
	 * 角色管理
	 * @param model
	 * @param rv
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/roleForm", method = RequestMethod.POST)
	public String roleFormPOST(Model model,TRole role,HttpServletRequest request,RedirectAttributes redirectAttributes){
		String menuTreeIds = request.getParameter("menuTreeIds");
		String roleOper = request.getParameter("roleOper");
		String roleOperName = Const.OPER_STATUS.getEnumName(roleOper);
		try{
			tRoleMapperService.roleFormPOST(role, roleOper, menuTreeIds, getShiroUser());
			redirectAttributes.addFlashAttribute("roleFormMessage", "角色"+roleOperName+"成功");
		}catch(Exception e){
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("roleFormMessage", "角色"+roleOperName+"失败");
		}

		return "redirect:/web/sys/role/roleForm";
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@RequestMapping(value = "/deleteRole/{roleId}", method = RequestMethod.GET)
	public String deleteRole(@PathVariable String roleId,RedirectAttributes redirectAttributes){
		try{
			tRoleMapperService.deleteRole(roleId);
			redirectAttributes.addFlashAttribute("roleFormMessage", "角色删除成功");
		}catch(Exception e){
			redirectAttributes.addFlashAttribute("roleFormMessage", "角色删除失败");
		}
		return "redirect:/web/sys/role/roleForm";
	}
	
}
