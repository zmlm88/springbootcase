package com.zm.web.db.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zm.web.db.model.TMenu;

public interface TMenuMapper {
	int deleteByPrimaryKey(String id);

	int insert(TMenu record);

	int insertSelective(TMenu record);

	TMenu selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TMenu record);

	int updateByPrimaryKey(TMenu record);

	public List<TMenu> selectMenuByUserIdAndRole(@Param("userId") String userId);

	public List<TMenu> selectMenuByRoleId(@Param("roleId") String roleId);

	// 查询根节点为0的记录
	public List<TMenu> selectMenuByParentId(Map<String,Object> param);
	
}