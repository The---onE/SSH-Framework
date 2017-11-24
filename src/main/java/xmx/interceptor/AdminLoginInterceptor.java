package xmx.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import xmx.model.Admin;

/**
 * 管理员登录拦截器
 * 
 * @author The_onE
 *
 */
public class AdminLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		HttpSession session = request.getSession();
		// 获取当前登录管理员
		Object admin = session.getAttribute("admin");
		if (admin != null && admin instanceof Admin) {
			// 若已登录则放行
			return true;
		} else {
			// 若未登录重定向至登录页
			response.sendRedirect("index");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mav)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e)
			throws Exception {
	}

}
