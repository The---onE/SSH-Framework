package xmx.service;

import xmx.model.User;

/**
 * 用户服务接口
 * 
 * @author The_onE
 *
 */
public interface IUserService {
	/**
	 * 用户登录
	 * 
	 * @param name
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 登录成功返回用户信息，登录失败返回null
	 */
	public User login(String name, String pwd);

	/**
	 * 注册
	 * 
	 * @param user
	 *            用户信息
	 * @return 注册成功返回1，用户名已被注册返回0，注册失败返回-1
	 */
	public int register(User user);
}
