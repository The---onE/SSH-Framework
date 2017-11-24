package xmx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xmx.dao.AdminHome;
import xmx.model.Admin;
import xmx.service.IAdminService;

/**
 * 管理员服务实现
 * 
 * @author The_onE
 *
 */
@Service("adminService")
@Transactional(readOnly=true, propagation=Propagation.SUPPORTS)
public class AdminService implements IAdminService {

	@Resource
	private AdminHome adminDao;

	@Override
	public Admin login(String username, String password) {
		// 根据用户名密码查询管理员
		Admin example = new Admin();
		example.setUsername(username);
		example.setPassword(password);
		List<Admin> admins = adminDao.findByExample(example);
		if (admins != null && admins.size() > 0) {
			return admins.get(0);
		}
		return null;
	}

}
