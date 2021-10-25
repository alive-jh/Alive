package com.wechat.service.impl;

import com.wechat.dao.CategoryDao;
import com.wechat.entity.BookKeyword;
import com.wechat.entity.Special;
import com.wechat.entity.SpecialInfo;
import com.wechat.service.CategoryService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryDao categoryDao;
	
	
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}


	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}


	
	@Override
	public void deleteSpecial(String id) {
		
		this.categoryDao.deleteSpecial(id);
	}


	
	@Override
	public void deleteSpecialInfo(String specialId) {
		
		this.categoryDao.deleteSpecialInfo(specialId);
	}


	
	@Override
	public void saveSpecial(Special special) {
		
		this.categoryDao.saveSpecial(special);
	}


	
	@Override
	public void saveSpecialInfo(SpecialInfo specialInfo) {
		
		this.categoryDao.saveSpecialInfo(specialInfo);
	}


	
	@Override
	public Page searchSpecial(HashMap map) {
		
		return this.categoryDao.searchSpecial(map);
	}


	
	@Override
	public List searchSpecialIndex() {
		
		return this.categoryDao.searchSpecialIndex();
	}


	
	@Override
	public List searchSpecialInfo(String specialId) {
		
		return this.categoryDao.searchSpecialInfo(specialId);
	}


	
	@Override
	public String searchMaxSort() {
		
		return this.categoryDao.searchMaxSort();
	}


	
	@Override
	public void updateSpecialSort(String id, String sort) {
		
		this.categoryDao.updateSpecialSort(id, sort);
	}


	
	@Override
	public void updateSpecialStatus(String id, String status) {
		
		this.categoryDao.updateSpecialStatus(id, status);
	}


	
	@Override
	public List searchBooksBySpecial(String id) {
		
		
		return this.categoryDao.searchBooksBySpecial(id);
	}


	
	@Override
	public Page searchBooksByLabel(HashMap map) {
		
		return this.categoryDao.searchBooksByLabel(map);
	}


	
	@Override
	public void deleteBookKeyword(String cateId) {
		
		this.categoryDao.deleteBookKeyword(cateId);
	}


	
	@Override
	public void saveBookKeyword(BookKeyword bookKeyword) {
		
		this.categoryDao.saveBookKeyword(bookKeyword);
	}


	
	@Override
	public List searchBookKeyword(String cateId) {
		
		return this.categoryDao.searchBookKeyword(cateId);
	}


	
	@Override
	public List searchBookByCode(String code) {
		
		return this.categoryDao.searchBookByCode(code);
	}


	@Override
	public Page searchBooksByLabelIdAndKeyWord(HashMap map) {
		
		return this.categoryDao.searchBooksByLabelIdAndKeyWord(map);
	}
	
	




}
