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

	/** 初始化用户角色 */
	public static final String INIT_USER_ROLE_ID = "1";

	public static final String BASE_PACKAGE_KEY="com.zm.web.db.dao";
	
	/**
	 * 新增操作变量
	 * 
	 * @author zhumin
	 *
	 */
	public enum OPER_STATUS {
		ADD("新增", 1), MODIFY("修改", 2), VIEW("查看", 3);
		// 成员变量
		private String name;
		private int index;

		// 构造方法
		private OPER_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (OPER_STATUS c : OPER_STATUS.values()) {
				if (c.getIndex() == index) {
					return c.name;
				}
			}
			return null;
		}

		public static String getEnumName(String enumName) {
			String str = "";
			switch (enumName) {
			case "ADD":
				str = OPER_STATUS.ADD.name;
				break;
			case "MODIFY":
				str = OPER_STATUS.MODIFY.name;
				break;
			case "VIEW":
				str = OPER_STATUS.VIEW.name;
				break;
			default:
				str = "";
				break;
			}
			return str;
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

	/**
	 * 表状态 操作状态 有效-1,无效-2
	 * 
	 * @author zhumin
	 *
	 */
	public enum STATUS {
		VAILDATE("有效", 1), INVAILDATE("无效", 2);
		private String name;
		private int index;

		// 构造方法
		private STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (STATUS c : STATUS.values()) {
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
