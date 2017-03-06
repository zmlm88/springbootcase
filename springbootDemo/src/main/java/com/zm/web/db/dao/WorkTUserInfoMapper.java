package com.zm.web.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.db.model.WorkTUserInfo;

public interface WorkTUserInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkTUserInfo record);

    int insertSelective(WorkTUserInfo record);

    WorkTUserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkTUserInfo record);

    int updateByPrimaryKeyWithBLOBs(WorkTUserInfo record);

    int updateByPrimaryKey(WorkTUserInfo record);
    
    List<WorkTUserInfo> findPageWorkTUserInfo(@Param("userId") String userId, Page<?> page); 
    
    List<WorkTUserInfo> findPageTempCase(@Param("userId") String userId,Page<?> page); 
}