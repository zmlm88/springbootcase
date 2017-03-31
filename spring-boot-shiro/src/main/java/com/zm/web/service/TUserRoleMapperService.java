package com.zm.web.service;

/**
 * 用户角色管理
 * 
 * @author zhumin
 *
 */
public class TUserRoleMapperService extends BaseService {
	/**
	 * 根据角色id 删除用户角色关系
	 * 
	 * @param roleId
	 * @return
	 */
	public int deleteUserRoleByRoleId(String roleId) {
		return tUserRoleMapper.deleteUserRoleByRoleId(roleId);
	}

}
