package com.zm.web.db.dao;

import org.apache.ibatis.annotations.Param;

import com.zm.web.db.model.TUserRole;

public interface TUserRoleMapper {
    int insert(TUserRole record);

    int insertSelective(TUserRole record);
    
    
    /**
     * 根据角色id删除角色菜单
     * @param roleId
     * @return
     */
    int deleteUserRoleByRoleId(@Param("roleId") String roleId);
}