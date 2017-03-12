package com.zm.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zm.web.db.model.TMenu;
import com.zm.web.util.menu.ModeList;
/**
 * 
 * @author zhumin
 *
 */
@Service
public class TMenuMapperService extends BaseService {
	
	
	/**
	 * 用户组织权限
	 * @param userId
	 * @return
	 */
	public ModeList selectModeListByUser(String userId){
		List<TMenu> list = selectMenuByUser(userId);
		if(!list.isEmpty()){
			return ModeList.getInstance().createTree2(list);
		}
		return null;
	}

	/**
	 * 用户查询权限
	 * @param userId
	 * @return
	 */
	public List<TMenu>  selectMenuByUser(String userId){
		return tMenuMapper.selectMenuByUser(userId);
	}
	
	/**
	 * 测试例子 后期结合 缓存
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public List<ModeList>  findTMenuByParentId(String parentId,String userId){
		List<TMenu> list = selectMenuByUser(userId);
		ModeList subList = ModeList.getInstance().createTree2(list);
		if(!list.isEmpty()){
			return  ModeList.getInstance().findMenuById("1", subList.getSubList());
		}
		return null;
	}
	
}
