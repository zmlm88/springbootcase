package com.zm.web.util;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.FileResourceLoader;
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

	public static String getBeetlMap(Map<String, Object> sharedVars) throws Exception {
		String filePath = CustUtils.class.getClassLoader().getResource("").getPath();
		FileResourceLoader resourceLoader = new FileResourceLoader(filePath, "utf-8");
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		gt.setSharedVars(sharedVars);
		Template t = gt.getTemplate("/bootstarpTable.bbl");
		String str = t.render();
		return str;
	}

	public static String genReqID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	public static String genPassword(String password){
		DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
				.setSource(ByteSource.Util.bytes(password)).setIterations(2)
				.build();
		String hex = hashService.computeHash(request).toHex();
		
		return hex;
	}
	

}
