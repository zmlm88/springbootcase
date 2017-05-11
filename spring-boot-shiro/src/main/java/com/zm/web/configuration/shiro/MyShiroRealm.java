package com.zm.web.configuration.shiro;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.google.common.base.Objects;
import com.zm.web.db.dao.TUserMapper;
import com.zm.web.db.model.TMenu;
import com.zm.web.db.model.TUser;
import com.zm.web.util.AesUtil;
import com.zm.web.util.menu.ModeList;

/**
 * shiro 认证和回权
 * 
 * @author zhumin
 *
 */
public class MyShiroRealm extends AuthorizingRealm {

	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	private static final String encrypt_key = "mzhuisking";

 
	@Autowired
	TUserMapper tUserMapper;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		List<TMenu> listTMenu = shiroUser.gettMenuList();
		if (!listTMenu.isEmpty()) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (TMenu tmenu : listTMenu) {
				System.out.println(tmenu.getPermission());
				info.addStringPermission(tmenu.getPermission());
			}
			return info;
		}
		return null;
		// List<Map<String, Object>> listMaps =
		// userService.findRoleByLoginName(loginName);
		// if(!listMaps.isEmpty()){
		// SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		// for(Map<String,Object> map:listMaps){
		// String roleCode = (String)map.get("roleCode");
		// if(!StringUtils.isEmpty(roleCode)){
		// info.addRole(roleCode);
		// }
		//
		// }
		// //permission
		// List<Map<String, Object>> listPermisssion =
		// userService.findWorkTFunInfoByLoginName(loginName);
		// for(Map<String, Object> param:listPermisssion){
		// if(!StringUtils.isEmpty(param.get("permission"))){
		// info.addStringPermission((String)param.get("permission"));
		// }
		// }
		// // info.addStringPermission(permission);
		// return info;
		// }
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken paramAuthenticationToken) throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) paramAuthenticationToken;

		List<TUser> listTuser = tUserMapper.selectByUserName(token.getUsername());
		if (!StringUtils.isEmpty(listTuser)) {
			if (listTuser.size() > 1) {
				throw new AuthenticationException("用戶名：" + token.getUsername() + "重复");
			}
			if (listTuser.size() == 1) {
				TUser tUser = listTuser.get(0);
				ShiroUser shiro = new ShiroUser(tUser.getId(), tUser.getUserName(), tUser);
				return new SimpleAuthenticationInfo(shiro, tUser.getPassword(), getName());
			}
		}
		return null;
		// userService.checkLogin(token.getUsername());
		// Map<String, Object> param =
		// userService.checkLogin(token.getUsername());
		// if (param != null && !param.isEmpty()) {
		// //return new SimpleAuthenticationInfo(token.getUsername(), (String)
		// param.get("loginPassword"), getName());
		// return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
		// user.getUsername()), (String) param.get("loginPassword"), getName());
		// }

	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public String id;
		public String loginName;

		public List<TMenu> tMenuList = new LinkedList<TMenu>();

		public ModeList modeList = new ModeList();

		public TUser tuser;

		public ShiroUser(String id, String loginName, TUser tuser) {
			this.id = id;
			this.loginName = loginName;
			this.tuser = tuser;
		}

		public TUser getTuser() {
			return tuser;
		}

		public void setTuser(TUser tuser) {
			this.tuser = tuser;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getLoginName() {
			return loginName;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		public List<TMenu> gettMenuList() {
			return tMenuList;
		}

		public void settMenuList(List<TMenu> tMenuList) {
			this.tMenuList = tMenuList;
		}

		public ModeList getModeList() {
			return modeList;
		}

		public void setModeList(ModeList modeList) {
			this.modeList = modeList;
		}

		/**
		 * 加密
		 * 
		 * @return
		 */
		public ShiroUser encrypt() {
			synchronized (this) {
				if (!StringUtils.isEmpty(tuser)) {
					String userPassword = tuser.getPassword();
					byte[] encryptResult = AesUtil.encrypt(userPassword, encrypt_key);
					String encryptResultStr = AesUtil.parseByte2HexStr(encryptResult);
					tuser.setPassword(encryptResultStr);
				}
				return this;
			}

		}

		public ShiroUser decrypt() throws UnsupportedEncodingException {
			synchronized (this) {
				if (!StringUtils.isEmpty(tuser)) {
					String userPassword = tuser.getPassword();
					// 解密
					byte[] decryptFrom = AesUtil.parseHexStr2Byte(userPassword);
					byte[] decryptResult = AesUtil.decrypt(decryptFrom, encrypt_key);
					// 解密内容进行解码
					String result = new String(decryptResult, AesUtil.UTF8);
					tuser.setPassword(result);
				}

				return this;
			}
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

	}

}
