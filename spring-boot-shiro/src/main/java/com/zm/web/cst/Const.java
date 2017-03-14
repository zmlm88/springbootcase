package com.zm.web.cst;

/**
 * 常量定义类
 * 
 * @author zmlm88@126.com
 *
 */
public class Const {

	public static final String CONFIG_PROPERTIES = "application.properties";

	public static final String CONFIG_MYBATIS_LOCATION = "mybatis-setting.xml";

	public static final String MAPPER_LOCATIONS = "classpath*:mybatis/sqlMap/**/*Mapper.xml";
	//
	public static final String PACKAGE_CONFIG_MYBATIS = "com.zm.web.db.dao";

	public static final String Prefix = "/WEB-INF/views/";

	public static final String Suffix = ".jsp";

	public static final String ROOT = "0";
	/** shiro 权限缓存 */
	public static final String USER_ALL_MENU_SHIRO = "USER_ALL_MENU_SHIRO";

	/**
	 * 返回状态号码: 1 - 成功, 2 - 失败, 3 - 未知, 4 - 没有数据
	 * 
	 * @author zhumin
	 *
	 */
	public enum RETURN_STATUS {
		SUCCESS("成功", 1), ERROR("失败", 2), UNKONW("未知", 3), NODATA("没有数据", 4);
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private RETURN_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (RETURN_STATUS c : RETURN_STATUS.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		// get set 方法
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

}