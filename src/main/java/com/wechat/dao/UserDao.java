package com.wechat.dao;

import com.wechat.entity.MemberCard;
import com.wechat.entity.User;
import com.wechat.util.Page;

import java.util.HashMap;

public interface UserDao {
	
	/**
	 * 添加用户
	 * @param id
	 * @return
	 */
	public User getUser(Integer id);

	/**
	 * 查询用户列表
	 * @return
	 */
	public Page searchUser(HashMap map);

	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUser(User user);

	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public void deleteUser(String id);

	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public void updateUser(User user);
	
	/**
	 * 根据map里的条件查询用户
	 * @param map
	 * @return
	 * @throws Exception
	 */
	User getUser(HashMap map) throws Exception;
	
	/**
	 * 启用,锁定用户
	 * @param userId
	 * @param status
	 * @throws Exception
	 */
	void updateUserStatus(String userId, String status)throws Exception;
	
	
	
	/**
	 * 修改用户密码
	 * @param userId
	 * @param password
	 * @throws Exception
	 */
	 void updateUserPwd(String userId, String password)throws Exception;
	 
	 /**
	  * 用户登录
	  * @param account
	  * @param password
	  * @return
	  * @throws Exception
	  */
	 User login(String account, String password)throws Exception;

	 /**
	  *  查询操作人map
	  * @return
	  */
	 HashMap getOperatorNameMap();
	 
	 
	 /**
	  * 生成实体借书卡序列号
	  * @param memberCard
	  */
	 public void saveMemberCard(MemberCard memberCard);
}
