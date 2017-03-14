package com.zm.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zm.web.configuration.shiro.MyShiroRealm.ShiroUser;
import com.zm.web.db.model.TUser;
import com.zm.web.util.menu.ModeList;

@Controller
@RequestMapping("/sys")
public class LoginController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	/**
	 * 登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String index(RedirectAttributes attr) {
		return "sys/sysLogin";
	}

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping("/web/home")
	public String home(Model model) {
		boolean hasLogged = SecurityUtils.getSubject().isAuthenticated();
		if (hasLogged) {
			ShiroUser userInfo = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			ModeList modeList = userInfo.getModeList();
			if (!StringUtils.isEmpty(modeList)) {
				model.addAttribute("modeList", modeList);
			}
			return "/sys/sysIndex";
		}
		return "/sys/sysLogin";
	}

	/**
	 * 用户登录验证
	 * 
	 * @param user
	 * @param session
	 * @param bindingResult
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid TUser user, HttpSession session, RedirectAttributes redirectAttributes) {
		String username = user.getUserName();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("验证开始");
			currentUser.login(token);

			// 设置缓存信息
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
			tUserMapperService.addCacheShirUser(shiroUser);

			logger.info("验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("loginErrorMessage", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.info("账户或者密码错误");
			redirectAttributes.addFlashAttribute("loginErrorMessage", "密码不正确");
		} catch (LockedAccountException lae) {
			logger.info("验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("loginErrorMessage", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.info("验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("loginErrorMessage", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			// logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("loginErrorMessage", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			return "redirect:/sys/web/home";
		} else {
			token.clear();
			return "redirect:/sys/login";
		}
	}

}
