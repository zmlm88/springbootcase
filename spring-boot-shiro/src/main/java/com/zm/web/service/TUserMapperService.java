package com.zm.web.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.zm.web.configuration.mybatis.Page;
import com.zm.web.configuration.mybatis.util.pageTemplate.MybatisPaginationCallback;
import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.cst.Const;
import com.zm.web.db.model.TMenu;
import com.zm.web.db.model.TUser;
import com.zm.web.db.model.TUserRole;
import com.zm.web.util.CustUtils;
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
				List<TMenu> tMenuList = tMenuMapper.selectMenuByUserIdAndRole(shiroUser.getId());
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
	 * 根据角色id和数据权限 ，查询用户
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<TUser> selectUserByRoleAndCodeDataPage(final String roleId,final String codeDate,final String userName,int pageNo,int pageCount){
		Page<TUser> page = (Page<TUser>)mybatisPaginationTemplate.setStartAndLength(pageNo, pageCount).execute(new MybatisPaginationCallback() {
			public <T> List<T> doPagination(Page<?> page) {
				return (List<T>) tUserMapper.selectUserByRoleAndCodeDataPage(roleId,codeDate,userName, page);
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
	 * 添加用户Service
	 * @param tuser
	 */
	@Transactional
	public void addUserService(TUser user,TUser operUser){
		user.setId(CustUtils.genReqID());
		user.setPassword(CustUtils.genPassword(user.getPassword()));
		//设置用户的读取权限
		user.setCodeData(genCodeData(operUser.getCodeData()));
		//设置状态
		user.setStatus(Const.STATUS.VAILDATE.getIndex()+"");
		insertSelective(user);
		//添加用户和角色的关系
		TUserRole tUserRole = new TUserRole();
		tUserRole.setUserId(user.getId());
		tUserRole.setRoleId(Const.INIT_USER_ROLE_ID);
		tUserRole.setStatus(Const.STATUS.VAILDATE.getIndex()+"");
		tUserRoleMapper.insert(tUserRole);
		for(String role :user.getRoleIds()){
			if(role.equals(Const.INIT_USER_ROLE_ID))
				continue;
			tUserRole = new TUserRole();
			tUserRole.setUserId(user.getId());
			tUserRole.setRoleId(role);
			tUserRole.setStatus(Const.STATUS.VAILDATE.getIndex()+"");
			tUserRoleMapper.insert(tUserRole);	
		}
		
	}
	/**
	 * 生成读取权限
	 * @param operUserCodeData
	 * @return
	 */
	public String genCodeData(String operUserCodeData){
		String codeData = operUserCodeData+CustUtils.randCodeData();
		int count = tUserMapper.selectCountByCodeData(codeData);
		if(count >0)
			genCodeData(operUserCodeData);
		return codeData;
	}
	
	
	/**
	 * 更新用户Service
	 * @param tuser
	 * @throws UnsupportedEncodingException 
	 */	
	@Transactional
	public void updateUserService(TUser user) throws UnsupportedEncodingException{
		String password = user.getPassword();
		if(StringUtils.isEmpty(password)){
			String md5Passwd = CustUtils.genPassword(password);
			TUser tUser = this.tUserMapper.selectByPrimaryKey(user.getId());
			if(!md5Passwd.equals(tUser.getPassword())){
				user.setPassword(md5Passwd);
			}
		}
		updateByPrimaryKeySelective(user);
		
		
	}
	
	
	/**
	 * 更新用户
	 * @param tuser
	 * @return
	 */
	public int updateByPrimaryKeySelective(TUser tuser){
		return this.tUserMapper.updateByPrimaryKeySelective(tuser);
	}
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	@Transactional
	public int deleteByPrimaryKey(String id){
		return this.tUserMapper.deleteByPrimaryKey(id);
	}
}
