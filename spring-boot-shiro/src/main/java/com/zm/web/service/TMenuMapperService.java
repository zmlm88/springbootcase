package com.zm.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.db.model.TMenu;
import com.zm.web.util.CustUtils;
import com.zm.web.util.menu.ModeList;

/**
 * 
 * @author zhumin
 *
 */
@Service
public class TMenuMapperService extends BaseService {

	/**
	 * 用户查询权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<TMenu> selectMenuByUser(ShiroUser shiroUser) {
		List<TMenu> listTMenu = shiroUser.gettMenuList();
		return listTMenu;
	}

	/**
	 * 创建权限树结构
	 * 
	 * @param list
	 * @return
	 */
	public ModeList createModeList(ShiroUser shiroUser) {
		return shiroUser.getModeList();
	}

	/**
	 * 根据用户 id 和 父节点获取数据
	 * 
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public List<ModeList> findTMenuByParentId(String parentId, ShiroUser shiroUser) {
		if (!StringUtils.isEmpty(shiroUser.getModeList())) {
			return CustUtils.findMenuById(parentId, shiroUser.getModeList().getSubList());
		}
		return null;
	}
	
	/**
	 * 根据用户ID角色查询menu信息
	 * @param userId
	 * @return
	 */
	public List<TMenu> selectMenuByUserIdAndRole(String userId){
		
		return this.tMenuMapper.selectMenuByUserIdAndRole(userId);
	}
	
	/**
	 * 根据角色查出菜单
	 * @param roleId
	 * @return
	 */
	public List<TMenu> selectMenuByRoleId(String roleId){
		return this.tMenuMapper.selectMenuByRoleId(roleId);
	}
	/**
	 * 根据parentid 查询menu
	 * @param parentId
	 * @return
	 */
	public List<TMenu> selectMenuByParentId(String parentId){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("parentId", parentId);
		return this.tMenuMapper.selectMenuByParentId(param);
	}
	
	/**
	 * 根据parentid 查询menu
	 * @param parentId
	 * @return
	 */
	public List<TMenu> selectAllMenu(){
		Map<String,Object> param = new HashMap<String,Object>();
		return this.tMenuMapper.selectMenuByParentId(param);
	}	
	/**
	 *  根据id查询菜单信息
	 * @param id
	 * @return
	 */
	public TMenu findOneById(String id){
		return this.tMenuMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 新增资源信息
	 * @param tmenu
	 */
	@Transactional
	public void saveTMenu(TMenu tmenu){
		this.tMenuMapper.insertSelective(tmenu);
	}
	
	
	/**
	 * 修改资源信息
	 * @param tmenu
	 */
	@Transactional
	public void updateTMenu(TMenu tmenu){
		this.tMenuMapper.updateByPrimaryKeySelective(tmenu);
	}
	
	/**
	 * 删除资源
	 * @param id
	 */
	@Transactional
	public void deleteTMenu(String id){
		this.tMenuMapper.deleteByPrimaryKey(id);
	}
}
