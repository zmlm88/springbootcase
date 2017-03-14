package com.zm.web.util;

import java.util.List;

import org.springframework.util.StringUtils;

import com.zm.web.util.menu.ModeList;

/**
 * 自定义工具类
 * 
 * @author zhumin
 *
 */
public class CustUtils {

	/**
	 * 根据id查询权限
	 */
	public static List<ModeList> findMenuById(String paraentId, List<ModeList> subList) {
		for (ModeList modeList : subList) {
			if (modeList.gettMenu().getId().equals(paraentId)) {
				return modeList.getSubList();
			} else {
				if (!StringUtils.isEmpty(modeList.getSubList()))
					findMenuById(paraentId, modeList.getSubList());
			}
		}
		return null;
	}

}
