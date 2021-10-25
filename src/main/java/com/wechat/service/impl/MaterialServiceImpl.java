package com.wechat.service.impl;

import com.wechat.dao.MaterialDao;
import com.wechat.entity.Material;
import com.wechat.service.MaterialService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService{

	@Resource
	private MaterialDao materialDao;
	
	public MaterialDao getMaterialDao() {
		return materialDao;
	}


	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}


	@Override
	public Material getMaterial(Material material) throws Exception {
		
		return this.materialDao.getMaterial(material);
	}

	
	@Override
	public void removeMaterial(String id) throws Exception {
		
		this.materialDao.removeMaterial(id);
	}

	
	@Override
	public void saveMaterial(Material material) throws Exception {
		
		this.materialDao.saveMaterial(material);
	}

	
	@Override
	public Page searchMaterial(HashMap map) throws Exception {
		
		return this.materialDao.searchMaterial(map);
	}

	
	@Override
	public List searchMaterialByParentId(String parentId) throws Exception {
		
		return this.materialDao.searchMaterialByParentId(parentId);
	}


	
	@Override
	public HashMap searchMaterialMap() {
		
		
		return this.materialDao.searchMaterialMap();
	}

}
