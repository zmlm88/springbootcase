package com.zm.web.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationCallback;
import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.cst.Const;
import com.zm.web.db.model.TRole;
import com.zm.web.db.model.TRoleMenu;
import com.zm.web.db.model.TUser;
import com.zm.web.util.CustUtils;

/**
 * 角色查询
 * 
 * @author zhumin
 *
 */
@Service
public class TRoleMapperService extends BaseService {

	/**
	 * 根据用户查询角色
	 * 
	 * @param codeData
	 * @return
	 */
	public List<TRole> selectRoleListByUserCodeData(final String codeData) {
		return tRoleMapper.selectRoleListByUserCodeData(codeData);

	}
	
	/**
	 * 查询分页信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TUser> selectByUserPage(final String userId,int pageNo,int pageCount){
		Page<TUser> page = (Page<TUser>)mybatisPaginationTemplate.setStartAndLength(pageNo, pageCount).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) tUserMapper.selectByUserPage(userId, page);
			}
		});
		return page;
	}

	/**
	 * 根据id查询角色信息
	 * @param id
	 * @return
	 */
	public TRole selectRoleById(String id){
		return tRoleMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 角色操作
	 * @param rv
	 * @param oper
	 * @param menuTreeIds
	 * @param shiroUser
	 */
	public void roleFormPOST(TRole rv,String oper,String menuTreeIds,ShiroUser shiroUser){
		if(oper.equals(Const.OPER_STATUS.ADD.toString())){
			roleFormPostAdd(rv,menuTreeIds,shiroUser);
		}else if(oper.equals(Const.OPER_STATUS.MODIFY.toString())){
			roleFormPostModify(rv,menuTreeIds,shiroUser);
		}
		
	}
	
	/**
	 * 修改角色
	 * @param rv
	 * @param menuTreeIds
	 * @param shiroUser
	 */
	@Transactional
	public void roleFormPostModify(TRole rv,String menuTreeIds,ShiroUser shiroUser){
		String  roleId =rv.getId();
		//删除角色和资源关系
		tRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
		//保存资源和角色关系
		String[] menus = StringUtils.split(menuTreeIds,",");
		for(String menu:menus){
			TRoleMenu tRoleMenu = new TRoleMenu();
			tRoleMenu.setMenuId(menu);
			tRoleMenu.setRoleId(roleId);
			tRoleMenuMapper.insert(tRoleMenu);
		}
		//更新角色信息
		
	}
	
	
	/**
	 * 新建角色
	 * @param rv
	 */
	@Transactional
	public void roleFormPostAdd(TRole rv,String menuTreeIds,ShiroUser shiroUser){
		String roleId = CustUtils.genReqID();
		String userId = shiroUser.getTuser().getId();
		rv.setId(roleId);
		rv.setCreateId(userId);
		rv.setStatus(Const.STATUS.VAILDATE.getIndex()+"");
		//保存角色信息
		tRoleMapper.insertSelective(rv);
		//保存用户的角色关系
//		TUserRole tUserRole = new TUserRole();
//		tUserRole.setRoleId(roleId);
//		tUserRole.setUserId(userId);
//		tUserRole.setStatus(Const.STATUS.VAILDATE.getIndex()+"");
//		tUserRoleMapper.insertSelective(tUserRole);
		//保存资源和角色关系
		String[] menus = StringUtils.split(menuTreeIds,",");
		for(String menu:menus){
			TRoleMenu tRoleMenu = new TRoleMenu();
			tRoleMenu.setMenuId(menu);
			tRoleMenu.setRoleId(roleId);
			tRoleMenuMapper.insert(tRoleMenu);
		}
		
	}
	
	/**
	 * 删除角色
	 * @param id
	 */
	@Transactional
	public void deleteRole(String roleId){
		//删除角色
		tRoleMapper.deleteByPrimaryKey(roleId);
		//删除用户和角色关系
		tUserRoleMapper.deleteUserRoleByRoleId(roleId);
		//删除角色和菜单关系
		tRoleMenuMapper.deleteRoleMenuByRoleId(roleId);
	}
	
	
}
