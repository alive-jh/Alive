package com.wechat.dao.impl;

import com.wechat.dao.MaterialDao;
import com.wechat.entity.Material;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class MaterialDaoImpl extends BaseDaoImpl implements MaterialDao{

	@Override
	public Material getMaterial(Material Material)
			throws Exception {
		
		return (Material)this.getEntity(Material.class, Material.getId());
	}

	@Override
	public void removeMaterial(String id) throws Exception {
		
		this.executeUpdateSQL(" delete from material where id = "+id);
	}

	@Override
	public void saveMaterial(Material Material)
			throws Exception {
		
		this.saveOrUpdate(Material);
	}

	@Override
	public Page searchMaterial(HashMap map) throws Exception {
		
		StringBuffer sql = new StringBuffer(" from Material where 1=1 and parentid = 0");
		if(!"".equals(map.get("accountId")) && map.get("accountId")!= null)
		{
			sql.append(" and accountId = "+map.get("accountId").toString());
		}
		if(!"".equals(map.get("materialId")) && map.get("materialId")!= null)
		{
			sql.append(" and id = "+map.get("materialId").toString());
		}
		sql.append(" order by createDate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public List searchMaterialByParentId(String parentId) throws Exception {
		
		StringBuffer sql = new StringBuffer(" from Material where 1=1  and parentId = "+parentId);
		
		return this.executeHQL(sql.toString());
	}
	
	@Override
	public HashMap searchMaterialMap() {
		
		StringBuffer sql = new StringBuffer(" from Material where 1=1  and parentId !=0 ");
		
		List<Material> list = this.executeHQL(sql.toString());
		HashMap  map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			
			map.put(list.get(i).getId().toString(), list.get(i));
		}
		
		return map;
	}
}
