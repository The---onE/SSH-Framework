package xmx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xmx.dao.UserHome;
import xmx.model.User;
import xmx.service.IUserService;

/**
 * 用户服务实现
 * 
 * @author The_onE
 *
 */
@Service("userService")
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class UserService implements IUserService {

	@Resource
	private UserHome userDao;

	@Override
	public User login(String name, String pwd) {
		// 根据用户名密码查询用户
		User example = new User();
		example.setUsername(name);
		example.setPassword(pwd);
		List<User> users = userDao.findByExample(example);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public int register(User user) {
		// 根据用户名密码查询用户
		User example = new User();
		example.setUsername(user.getUsername());
		List<User> users = userDao.findByExample(example);
		if (users != null && users.size() > 0) {
			// 该用户名已被注册
			return 0;
		}
		// 添加用户
		userDao.attachDirty(user);
		return 1;
	}
}
