package com.wechat.dao.impl;

import com.wechat.dao.MenuDao;
import com.wechat.entity.Menu;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	
	@Override
	public void deleteMenu(Menu menu) {
		
		this.removeEntity(menu);
	}

	
	@Override
	public Menu getMenu(Menu menu) {
		
	
		return 	(Menu)this.getEntity(Menu.class, menu.getId());
	}

	
	@Override
	public void saveMenu(Menu menu) {
		
		this.saveOrUpdate(menu);
	}

	
	@Override
	public Page searchMenu(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Menu where 1=1 ");
		if(!"".equals(map.get("menuId")) && map.get("menuId") != null)
		{
			sql.append(" and id = ").append(map.get("menuId").toString());
		}
		if(!"".equals(map.get("accountId")) && map.get("accountId") != null)
		{
			sql.append(" and accountId = ").append(map.get("accountId").toString());
		}
		if(!"".equals(map.get("parentId")) && map.get("parentId") != null)
		{
			sql.append(" and parentId = ").append(map.get("parentId").toString());
		}
		sql.append(" order by id asc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}
	
	
	
	@Override
	public List searchMenuByParentId(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Menu where 1=1 ");
		if(!"".equals(map.get("menuId")) && map.get("menuId") != null)
		{
			sql.append(" and id = ").append(map.get("menuId").toString());
		}
		if(!"".equals(map.get("accountId")) && map.get("accountId") != null)
		{
			sql.append(" and accountId = ").append(map.get("accountId").toString());
		}
		if(!"".equals(map.get("parentId")) && map.get("parentId") != null)
		{
			sql.append(" and parentId = ").append(map.get("parentId").toString());
		}
		sql.append(" order by id asc ");
		return this.executeHQL(sql.toString());
	}

	
	@Override
	public void deleteMenuByParentId(String parentId) {
		
		this.executeUpdateSQL("delete from menu where parentid = "+parentId);
	}
}
