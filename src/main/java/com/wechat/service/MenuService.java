package com.wechat.service;

import com.wechat.entity.Menu;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface MenuService {
	
	/**
	 * 保存菜单
	 * @param menu
	 */
	void saveMenu(Menu menu);
	
	/**
	 * 删除菜单
	 * @param menu
	 */
	void deleteMenu(Menu menu);
	
	/**
	 * 查询菜单集合
	 * @param map
	 * @return
	 */
	Page searchMenu(HashMap map);
	
	/**
	 * 查询单个菜单
	 * @param menu
	 * @return
	 */
	Menu getMenu(Menu menu);
	
	/**
	 * 查询子节点
	 * @param map
	 * @return
	 */
	public List searchMenuByParentId(HashMap map);
	
	/**
	 * 删除子菜单
	 * @param parentId
	 */
	public void deleteMenuByParentId(String parentId);

}
