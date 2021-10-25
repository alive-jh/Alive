package com.wechat.service.impl;

import com.wechat.dao.MenuDao;
import com.wechat.entity.Menu;
import com.wechat.service.MenuService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Service("menuService")
public class MenuServiceImpl implements MenuService{

	
	@Resource
	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	
	@Override
	public void deleteMenu(Menu menu) {
		
		this.menuDao.deleteMenu(menu);
	}

	
	@Override
	public Menu getMenu(Menu menu) {
		
		return this.menuDao.getMenu(menu);
	}

	
	@Override
	public void saveMenu(Menu menu) {
		
		this.menuDao.saveMenu(menu);
	}

	
	@Override
	public Page searchMenu(HashMap map) {
		
		return this.menuDao.searchMenu(map);
	}

	
	@Override
	public List searchMenuByParentId(HashMap map) {
		
		return this.menuDao.searchMenuByParentId(map);
	}

	
	@Override
	public void deleteMenuByParentId(String parentId) {
		
		this.menuDao.deleteMenuByParentId(parentId);
		
	}
	
}
