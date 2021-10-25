package com.wechat.service.impl;

import com.wechat.dao.UserDao;
import com.wechat.entity.MemberCard;
import com.wechat.entity.User;
import com.wechat.service.UserService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public void deleteUser(String id) {
		 userDao.deleteUser(id);
	}

	@Override
	public Page searchUser(HashMap map) {
		return userDao.searchUser(map);
	}

	@Override
	public void updateUser(User user) {
		 userDao.updateUser(user);
	}

	@Override
	public User getUser(Integer id) {
		return userDao.getUser(id);
	}

	
	@Override
	public User getUser(HashMap map) throws Exception {
		
		return userDao.getUser(map);
	}

	
	@Override
	public void updateUserPwd(String userId, String password) throws Exception {
		this.userDao.updateUserPwd(userId, password);
		
	}

	
	@Override
	public void updateUserStatus(String userId, String status) throws Exception {
		
		this.userDao.updateUserStatus(userId, status);
	}

	
	@Override
	public User login(String account, String password) throws Exception {
		
		return this.userDao.login(account, password);
	}

	
	@Override
	public HashMap getOperatorNameMap() {
		
		
		return this.userDao.getOperatorNameMap();
	}

	@Override
	public void saveMemberCard(MemberCard memberCard) {
		
		this.userDao.saveMemberCard(memberCard);
		
	}
}
