package com.zm.web.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationCallback;
import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.cst.Const;
import com.zm.web.db.model.TMenu;
import com.zm.web.db.model.TUser;
import com.zm.web.util.menu.ModeList;

@Service
public class TUserMapperService extends BaseService {

	/**
	 * 根据登录名查询用户
	 * 
	 * @param userName
	 * @return
	 */
	public List<TUser> selectByUserName(String userName) {
		return this.tUserMapper.selectByUserName(userName);
	}

	/**
	 * 添加全局缓存
	 * 
	 * @param userId
	 * @return
	 */
	public int addCacheShirUser(ShiroUser shiroUser) {
		try {
			if (!StringUtils.isEmpty(shiroUser)) {
				// 获取权限
				List<TMenu> tMenuList = tMenuMapper.selectMenuByUser(shiroUser.getId());
				if (!CollectionUtils.isEmpty(tMenuList)) {
					shiroUser.settMenuList(tMenuList);
					// 设置权限树
					shiroUser.setModeList(ModeList.createTree2(tMenuList));
					// 特殊属性加密
					if (!StringUtils.isEmpty(shiroUser.getTuser())) {
						shiroUser.encrypt();
					}
				}
				return Const.RETURN_STATUS.SUCCESS.getIndex();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Const.RETURN_STATUS.ERROR.getIndex();
		}
		
		return Const.RETURN_STATUS.NODATA.getIndex();
	}

	/**
	 * 查询分页信息
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TUser> selectByUserPage(final String userId,int pageNo,int pageCount){
		Page<TUser> page = (Page<TUser>)mybatisPaginationTemplate.setStartAndLength(pageNo, pageCount).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) tUserMapper.selectByUserPage(userId, page);
			}
		});
		return page;
	}
	/**
	 * 根据用户
	 * @param id
	 * @return
	 */
	public TUser selectByPrimaryKey(String id){
		return tUserMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 添加用户
	 * @param tuser
	 * @return
	 */
	public int insertSelective(TUser tuser){
		return this.tUserMapper.insertSelective(tuser);
	}
	
	/**
	 * 更新用户
	 * @param tuser
	 * @return
	 */
	public int updateByPrimaryKeySelective(TUser tuser){
		return this.tUserMapper.updateByPrimaryKeySelective(tuser);
	}
}
