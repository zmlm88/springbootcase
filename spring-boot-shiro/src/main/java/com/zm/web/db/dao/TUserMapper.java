package com.zm.web.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.db.model.TUser;

public interface TUserMapper {
	int deleteByPrimaryKey(String id);

	int insert(TUser record);

	int insertSelective(TUser record);

	TUser selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(TUser record);

	int updateByPrimaryKey(TUser record);

	List<TUser> selectByUserName(@Param("userName") String userName);

	
	List<TUser> selectByUserPage(@Param("userName") String userId, Page<?> page); 
}