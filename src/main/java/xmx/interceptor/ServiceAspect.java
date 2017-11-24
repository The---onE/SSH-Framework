package xmx.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import xmx.dao.LogHome;
import xmx.model.Admin;
import xmx.model.Log;
import xmx.model.User;
import xmx.util.IPUtil;

/**
 * 业务层切面
 * 
 * @author The_onE
 *
 */
@Component
@Aspect
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class ServiceAspect {

	@Resource
	private LogHome logDao;

	@Pointcut("execution (* xmx.service..*.*(..))")
	private void point() {
	}

	@After("point()")
	public void doAfter(JoinPoint joinPoint) {
		// 当前请求
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String ip = IPUtil.getIP(request); // 请求IP
		String target = joinPoint.getTarget().getClass().getSimpleName(); // 所在类类名
		String method = joinPoint.getSignature().toString(); // 方法名
		Object[] params = joinPoint.getArgs(); // 参数
		StringBuffer param = new StringBuffer();
		for (Object o : params) {
			if (o == null) {
				param.append("?_?");
			} else if (o instanceof String) {
				param.append(o);
			} else if (o.getClass().isPrimitive()) {
				param.append(o);
			} else {
				param.append(o.getClass().getSimpleName());
			}
			param.append(" ");
		}
		HttpSession session = request.getSession();
		String user = ""; // 用户名
		String userType = ""; // 用户类型
		Object o;
		o = session.getAttribute("admin");
		if (o != null && o instanceof Admin) {
			user = ((Admin) o).getUsername();
			userType = "Admin";
		} else {
			o = session.getAttribute("user");
			if (o != null && o instanceof User) {
				user = ((User) o).getUsername();
				userType = "User";
			}
		}

		// 生成日志
		Log log = new Log();
		log.setType("service");
		log.setTarget(target);
		log.setMethod(method);
		log.setParam(param.toString());
		log.setUser(user);
		log.setUserType(userType);
		log.setIp(ip);
		// 记录日志
		//logDao.attachDirty(log);
	}

	@Around("point()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		Object object = pjp.proceed();
		return object;
	}
}
