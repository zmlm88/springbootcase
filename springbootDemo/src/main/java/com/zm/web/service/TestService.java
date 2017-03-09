package com.zm.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationCallback;
import com.zm.web.db.model.WorkTUserInfo;

@Service
public class TestService extends BaseService {
	
	
	public WorkTUserInfo selectByPrimaryKey(String id){
		return workTUserInfoMapper.selectByPrimaryKey(id);
	}
	
	public Page<WorkTUserInfo> findPageTempCase(final String userId){
		@SuppressWarnings("unchecked")
		Page<WorkTUserInfo> page = (Page<WorkTUserInfo>) mybatisPaginationTemplate.setStartAndLength(1, 3).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) workTUserInfoMapper.findPageTempCase(userId, page);
			}
		});
		return page;
	}
	
	@Transactional
	public int add(){
		WorkTUserInfo workTUserInfo = new WorkTUserInfo();
		workTUserInfo.setId("110101010101010");
		workTUserInfo.setCreateDate(new Date());
		workTUserInfo.setLoginName("zhumin");
		return workTUserInfoMapper.insertSelective(workTUserInfo);
	}
	
	public List<Map<String, Object>> selectMapByOper(){
		return workTUserInfoMapper.selectMapByOper();
	}
}
