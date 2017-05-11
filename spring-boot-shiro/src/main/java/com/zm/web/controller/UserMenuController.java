package com.zm.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zm.web.cst.Const;
import com.zm.web.db.model.TMenu;
import com.zm.web.util.CustUtils;
import com.zm.web.util.menu.ModeList;

@Controller
@RequestMapping("/web/sys/user/menu")
public class UserMenuController  extends BaseController {
	
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(RedirectAttributes attr) {
		return "/sys/userMenuIndex";
	}

	/**
	 * 查询资源信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/tree")
	public String tree(Model model) {
		 List<TMenu> listTMenu = this.tMenuMapperService.selectMenuByParentId(Const.ROOT);
		model.addAttribute("userMenuList", listTMenu);
		return "sys/userMenuTree";
	}	
	
	/**
	 * 查询资源权限列表
	 * @return
	 */
	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public String list(@PathVariable String id,Model model){
		 List<TMenu> listTMenu = this.tMenuMapperService.selectAllMenu();
		 ModeList userMenuListRight = ModeList.createTree2(listTMenu, id);
		 model.addAttribute("userMenuListRight", userMenuListRight);
		 model.addAttribute("modelId", id);
		return "sys/userMenuList";
	}
	/**
	 * 菜单跳转
	 * @return
	 */
	@RequestMapping(value = "/form",method = RequestMethod.GET)
	public String form(HttpServletRequest request,Model model){
		String userMenuOper = request.getParameter("userMenuOper");
		String userMenuParentid = request.getParameter("parentId");
		String modelId = request.getParameter("modelId");
		TMenu parentTMenu = this.tMenuMapperService.findOneById(userMenuParentid);
		// 新增
		if(userMenuOper.equals(Const.OPER_STATUS.MODIFY.toString())){
			String id = request.getParameter("id");
			TMenu tMenu = this.tMenuMapperService.findOneById(id);
			model.addAttribute("tMenu", tMenu);
			//修改
		}
		model.addAttribute("parentTMenu",parentTMenu);
		model.addAttribute("userMenuParentid",userMenuParentid);
		model.addAttribute("userMenuOper",userMenuOper);
		model.addAttribute("modelId",modelId);
		return "sys/userMenuForm";
	}
	/**
	 * 菜单操作 （添加，修改）
	 * @param user
	 * @param request
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/form",method = RequestMethod.POST)
	public String form(TMenu menu, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String userMenuOper = request.getParameter("userMenuOper");
		String modelId = request.getParameter("modelId");
		try{
			// 新增
			if(userMenuOper.equals(Const.OPER_STATUS.ADD.toString())){
				menu.setId(CustUtils.genReqID());
				this.tMenuMapperService.saveTMenu(menu);
			}else{
				this.tMenuMapperService.updateTMenu(menu);
			}
			redirectAttributes.addFlashAttribute("userMenuFormMessage", "资源"+Const.OPER_STATUS.getEnumName(userMenuOper)+"成功");
		}catch(Exception e){
			redirectAttributes.addFlashAttribute("userMenuFormMessage", "资源"+Const.OPER_STATUS.getEnumName(userMenuOper)+"成功");
		}

		return "redirect:/web/sys/user/menu/list/"+modelId;
	}
	
	/**
	 * 根节点添加
	 * @return
	 */
	@RequestMapping(value = "/rootMenu",method = RequestMethod.GET)
	public String rootMenu(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String rootMenuOper = request.getParameter("rootMenuOper");
		String rootMenuId = request.getParameter("rootMenuId");
		try{
			if(rootMenuOper.equals(Const.OPER_STATUS.MODIFY.toString())){
				TMenu tMenu = this.tMenuMapperService.findOneById(rootMenuId);
				model.addAttribute("rootMenu", tMenu);
			}
		}catch(Exception e){
			
		}
		model.addAttribute("rootMenuOper",rootMenuOper);
		return "sys/rootMenu";
	}
	/**
	 * 根节点添加 POST
	 * @return
	 */	
	@RequestMapping(value = "/rootMenu",method = RequestMethod.POST)
	public String rootMenu(TMenu menu,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		String userMenuOper = request.getParameter("rootMenuOper");
		try{
			// 新增
			if(userMenuOper.equals(Const.OPER_STATUS.ADD.toString())){
				menu.setId(CustUtils.genReqID());
				this.tMenuMapperService.saveTMenu(menu);
			}else{
				this.tMenuMapperService.updateTMenu(menu);
			}
			redirectAttributes.addFlashAttribute("userMenuFormMessage", "资源"+Const.OPER_STATUS.getEnumName(userMenuOper)+"成功");
		}catch(Exception e){
			redirectAttributes.addFlashAttribute("userMenuFormMessage", "资源"+Const.OPER_STATUS.getEnumName(userMenuOper)+"成功");
		}
		return "redirect:/web/sys/user/menu/index";
	}
	/**
	 * 删除资源
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteRootMenu/{id}",method = RequestMethod.GET)
	public String deleteRootMenu(@PathVariable String id,HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try{
			this.tMenuMapperService.deleteTMenu(id);
		}catch(Exception e){
		}
		redirectAttributes.addFlashAttribute("userMenuFormMessage", "资源删除成功");
		return "redirect:/web/sys/user/menu/index";
	}
	
}
