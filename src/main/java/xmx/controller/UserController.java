package xmx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xmx.model.User;
import xmx.service.IUserService;
import xmx.util.ObjectResult;
import xmx.util.PromptResult;

/**
 * 前台控制器
 * 
 * @author The_onE
 *
 */
@Controller
@RequestMapping("/User")
public class UserController {
	/**
	 * 业务层
	 */
	@Resource
	private IUserService userService;

	/**
	 * 前台首页
	 * 
	 * @return 前台首页页面
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView("user/index");

		Object o = session.getAttribute("user");
		if (o != null && o instanceof User) {
			mav.addObject("user", (User) o);
		}
		return mav;
	}

	/**
	 * 前台登录页
	 * 
	 * @return 前台登录页面
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "user/login";
	}

	/**
	 * 登录请求
	 * 
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param session
	 *            当前Session
	 * @return 登录信息
	 */
	@RequestMapping(value = "/login.action", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String loginRequest(String name, String pwd, HttpSession session) {
		ObjectResult<User> result = new ObjectResult<>();
		try {
			if (name != null && name.trim().length() > 0 && pwd != null && pwd.trim().length() > 0) {
				User user = userService.login(name, pwd);
				if (user != null) {
					result.setStatus(1).setPrompt("登录成功").setObject(user);

					session.setAttribute("user", user);
				} else {
					result.setStatus(-3).setPrompt("用户名或密码错误");
				}
			} else {
				result.setStatus(-2).setPrompt("用户名或密码不能为空");
			}
			return result.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-1).setPrompt("登录失败").toJson();
		}
	}

	/**
	 * 前台注册页
	 * 
	 * @return 前台注册页面
	 */
	@RequestMapping(value = "/register")
	public String register() {
		return "user/register";
	}

	/**
	 * 注册请求
	 * 
	 * @param user
	 *            用户信息
	 * @param session
	 *            当前Session
	 * @return 注册信息
	 */
	@RequestMapping(value = "/register.action", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String registerRequest(User user, HttpSession session) {
		ObjectResult<User> result = new ObjectResult<>();
		try {
			String name = user.getUsername();
			String pwd = user.getPassword();
			if (name != null && name.trim().length() > 0 && pwd != null && pwd.trim().length() > 0) {
				int re = userService.register(user);
				if (re > 0) {
					result.setStatus(1).setPrompt("注册成功");

					session.setAttribute("user", userService.login(name, pwd));
				} else if (re == 0) {
					result.setStatus(-3).setPrompt("该用户名已被注册");
				} else {
					result.setStatus(-4).setPrompt("注册失败");
				}
			} else {
				result.setStatus(-2).setPrompt("用户名或密码不能为空");
			}
			return result.toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-1).setPrompt("注册失败").toJson();
		}
	}

	/**
	 * 注销请求
	 * 
	 * @param session
	 *            当前Session
	 * @return 注销信息
	 */
	@RequestMapping(value = "/logout.action", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String logoutRequest(HttpSession session) {
		PromptResult result = new PromptResult();
		try {
			session.removeAttribute("user");

			return result.setStatus(1).setPrompt("注销成功").toJson();
		} catch (Exception e) {
			e.printStackTrace();
			return result.setStatus(-1).setPrompt("注销失败").toJson();
		}
	}
}
