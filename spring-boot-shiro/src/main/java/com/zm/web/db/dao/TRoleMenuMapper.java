package com.zm.web.db.dao;

import org.apache.ibatis.annotations.Param;

import com.zm.web.db.model.TRoleMenu;

public interface TRoleMenuMapper {
    int insert(TRoleMenu record);

    int insertSelective(TRoleMenu record);
    
    /**
     * 根据角色id删除角色菜单
     * @param roleId
     * @return
     */
   int deleteRoleMenuByRoleId(@Param("roleId") String roleId);
}