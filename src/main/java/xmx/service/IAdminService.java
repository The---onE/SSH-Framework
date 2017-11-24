package xmx.service;

import xmx.model.Admin;

/**
 * 管理员服务接口
 * 
 * @author The_onE
 *
 */
public interface IAdminService {
	/**
	 * 管理员登录
	 * 
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 登录成功返回管理员信息，登录失败返回null
	 */
	public Admin login(String name, String pwd);
}
