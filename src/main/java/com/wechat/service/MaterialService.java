package com.wechat.service;

import com.wechat.entity.Material;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface MaterialService {

	/**
	 * 添加素材
	 * @param Material
	 * @throws Exception
	 */
	void saveMaterial(Material material) throws Exception;
	/**
	 * 删除素材
	 * @param id
	 * @throws Exception
	 */
	void removeMaterial(String id)throws Exception;
	/**
	 * 查询素材列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Page searchMaterial(HashMap map)throws Exception;
	/**
	 * 查询单个素材
	 * @param Material
	 * @return
	 * @throws Exception
	 */
	Material getMaterial(Material material)throws Exception;
	
	
	/**
	 * 查询子节点
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List searchMaterialByParentId(String parentId) throws Exception;
	
	
	/**
	 * 查询子节点Map
	 * @return
	 */
	public HashMap searchMaterialMap();
	
	
}
