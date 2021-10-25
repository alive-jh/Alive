package com.wechat.dao.impl;

import com.wechat.dao.RoleDao;
import com.wechat.entity.Role;
import com.wechat.entity.RoleModular;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao{

	@Override
	public void deleteRole(String id) {
		
		Role role  =  new Role();
		role.setId(new Integer(id));
		this.delete(role);
	}

	@Override
	public void deleteRoleModular(String roleId) {
		
		this.executeUpdateSQL(" delete from rolemodular where roleid="+roleId);
	}

	@Override
	public void saveRole(Role role) {
		
		this.saveOrUpdate(role);
	}

	@Override
	public void saveRoleModular(RoleModular roleModular) {
		
		this.save(roleModular);
	}

	@Override
	public Page searchRole(HashMap map) {
		StringBuffer sql = new StringBuffer(" from Role where 1=1");
		if(!"".equals(map.get("name")) && map.get("name")!= null)
		{
			sql.append(" and name like '%").append(map.get("name").toString()).append("%'");
		}
		sql.append(" order by id desc");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchRoleModular(String roleId) {
		
		List list = this.executeSQL(" select a.name,a.url,a.sort,a.parentid , a.id  from modular  a ,rolemodular b "
			+" where a.id = b.modularid  and b.roleid = " + roleId
			+" order by a.sort asc");
		return list;
	}

	@Override
	public List searchModular() {
		
		return this.executeHQL(" from Modular where 1=1 order by sort asc");
	}

	@Override
	public List searchRoleList() {
		
		return this.executeHQL(" from Role where 1=1  order by id desc ");
	}

}
