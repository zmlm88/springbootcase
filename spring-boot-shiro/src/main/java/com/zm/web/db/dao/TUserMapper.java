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
	//角色查询用户
	List<TUser> selectRoleUser(@Param("roleId") String roleId);
	
	//根据角色id和数据权限 ，查询用户
	List<TUser> selectUserByRoleAndCodeDataPage(@Param("roleId") String roleId,@Param("codeData") String codeDate,@Param("userName") String userName, Page<?> page);
	
	//根据codeData查询用户数量
	int selectCountByCodeData(@Param("codeData") String codeData);
}