package com.wechat.dao;

import com.wechat.entity.Role;
import com.wechat.entity.RoleModular;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;


public interface RoleDao {

	/**
	 * 保存角色
	 * @param role
	 */
	void saveRole(Role role);
	/**
	 * 删除角色
	 * @param id
	 */
	void deleteRole(String id);
	/**
	 * 查询角色列表
	 * @param map
	 * @return
	 */
	Page searchRole(HashMap map);
	/**
	 * 保存角色模块
	 * @param roleModular
	 */
	void saveRoleModular(RoleModular roleModular);
	/**
	 * 删除角色模块
	 * @param roleId
	 */
	void deleteRoleModular(String roleId);
	/***
	 * 查询角色模块
	 * @param roleId
	 * @return
	 */
	List searchRoleModular(String roleId);
	
	/***
	 * 查询模块
	 * @param roleId
	 * @return
	 */
	List searchModular();
	
	/**
	 * 查询角色列表
	 * @return
	 */
	List searchRoleList();
	
	
	
}
