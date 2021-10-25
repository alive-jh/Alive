package com.wechat.service.impl;

import com.wechat.dao.RoleDao;
import com.wechat.entity.Role;
import com.wechat.entity.RoleModular;
import com.wechat.service.RoleService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleDao roleDao;
	
	
	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void deleteRole(String id) {
		
		this.roleDao.deleteRole(id);
	}

	@Override
	public void deleteRoleModular(String roleId) {
		
		this.roleDao.deleteRoleModular(roleId);
	}

	@Override
	public void saveRole(Role role) {
		
		this.roleDao.saveRole(role);
	}

	@Override
	public void saveRoleModular(RoleModular roleModular) {
		
		this.roleDao.saveRoleModular(roleModular);
	}

	@Override
	public Page searchRole(HashMap map) {
		
		return this.roleDao.searchRole(map);
	}

	@Override
	public List searchRoleModular(String roleId) {
		
		return this.roleDao.searchRoleModular(roleId);
	}

	@Override
	public List searchModular() {
		
		return this.roleDao.searchModular();
	}

	@Override
	public List searchRoleList() {
		
		return this.roleDao.searchRoleList();
	}

}
