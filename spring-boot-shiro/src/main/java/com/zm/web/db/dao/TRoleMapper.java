package com.zm.web.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zm.web.db.model.TRole;

public interface TRoleMapper {
	
    
    /**
     * 根据角色id删除角色菜单
     * @param roleId
     * @return
     */
    int deleteByPrimaryKey(String id);

    int insert(TRole record);

    int insertSelective(TRole record);

    TRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TRole record);

    int updateByPrimaryKey(TRole record);
    
    /**
     * 根据用户查询角色
     * @param codeData
     * @return
     */
    List<TRole> selectRoleListByUserCodeData(@Param("codeData")String codeData);
    
    
}