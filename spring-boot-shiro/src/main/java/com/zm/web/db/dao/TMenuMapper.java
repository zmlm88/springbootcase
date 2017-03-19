package com.zm.web.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zm.web.db.model.TMenu;

public interface TMenuMapper {
	int deleteByPrimaryKey(String id);

	int insert(TMenu record);

	int insertSelective(TMenu record);

	TMenu selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TMenu record);

	int updateByPrimaryKey(TMenu record);

	public List<TMenu> selectMenuByUser(@Param("userId") String userId);
}