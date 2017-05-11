package com.zm.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色管理
 * 
 * @author zhumin
 *
 */
@Service
public class TUserRoleMapperService extends BaseService {
	
 
	
	/**
	 * 根据角色id 删除用户角色关系
	 * 
	 * @param roleId
	 * @return
	 */
	@Transactional
	public int deleteUserRoleByRoleId(String roleId) {
		return tUserRoleMapper.deleteUserRoleByRoleId(roleId);
	}

}
