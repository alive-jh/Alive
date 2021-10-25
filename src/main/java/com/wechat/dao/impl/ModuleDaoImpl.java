package com.wechat.dao.impl;

import com.wechat.dao.ModuleDao;
import com.wechat.entity.Module;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class ModuleDaoImpl extends BaseDaoImpl implements ModuleDao {

	
	@Override
	public void deleteModule(String id) {
		Module module = new Module();
		module.setId( new Integer(id));
		this.delete(module);
		
	}

	
	@Override
	public Module getModule(Integer id) {
		
		return (Module) this.getEntity(Module.class,id);
	}

	
	@Override
	public void saveModule(Module module) {
		this.saveOrUpdate(module);
	}

	
	@Override
	public List searchModule(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Module where 1=1");
		
		if(!"".equals(map.get("memName"))&& map.get("memName")!= null)
		{
			sql.append(" and b.memname like '%").append(map.get("memName").toString()).append("%'");
		}
		if(!"".equals(map.get("memMobile"))&& map.get("memMobile")!= null)
		{
			sql.append(" and b.memphone = '").append(map.get("memMobile").toString()).append("'");
		}
		if(!"".equals(map.get("addName"))&& map.get("addName")!= null)
		{
			sql.append(" and a.name like '%").append(map.get("addName").toString()).append("%'");
		}
		if(!"".equals(map.get("addMobile"))&& map.get("addMobile")!= null)
		{
			sql.append(" and a.mobile = '").append(map.get("addMobile").toString()).append("'");
		}
		if(!"".equals(map.get("type"))&& map.get("type")!= null)
		{
			sql.append(" and a.type = ").append(map.get("type").toString());
		}
		if(!"".equals(map.get("status"))&& map.get("status")!= null)
		{
			sql.append(" and a.status ").append(map.get("status").toString());
		}
		if(!"".equals(map.get("memShopId"))&& map.get("memShopId")!= null)
		{
			sql.append(" and b.memshopid = ").append(map.get("memShopId").toString());
		}
		if(!"".equals(map.get("operatorId"))&& map.get("operatorId")!= null)
		{
			sql.append(" and a.operatorid = ").append(map.get("operatorId").toString());
		}
		if(!"".equals(map.get("startDate"))&& map.get("startDate")!= null)
		{
			sql.append(" and a.startDate  >='").append(map.get("startDate").toString()).append("'");
		}
		if(!"".equals(map.get("endDate"))&& map.get("endDate")!= null)
		{
			sql.append(" and a.startDate  <='").append(map.get("endDate").toString()).append("'");
		}
		
		
		return this.executeHQL(sql.toString());
	}

}
