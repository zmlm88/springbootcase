package com.zm.web.cst;

/**
 * 	常量定义类
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
	
	
	/**
	 * 新增变量系统表
	 * @author zhumin
	 *
	 */
	public enum TMENU {
		ROOOT,TOP,CHILDREN
	}
	
	
	
//	
//	//新增变量
//	public enum TMENU {
//		RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);
//		// 成员变量
//		private String name;
//		private int index;
//		// 构造方法
//		private TMENU(String name, int index) {
//			this.name = name;
//			this.index = index;
//		}
//		// 普通方法
//		public static String getName(int index) {
//			for (TMENU c : TMENU.values()) {
//				if (c.getIndex() == index) {
//					return c.name;
//				}
//			}
//			return null;
//		}
//		// get set 方法
//		public String getName() {
//			return name;
//		}
//		public void setName(String name) {
//			this.name = name;
//		}
//		public int getIndex() {
//			return index;
//		}
//		public void setIndex(int index) {
//			this.index = index;
//		}
//	}
//	
}
