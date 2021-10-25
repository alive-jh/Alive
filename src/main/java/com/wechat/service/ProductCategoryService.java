package com.wechat.service;

import com.wechat.entity.ProductCategory;
import com.wechat.util.Page;

import java.util.ArrayList;
import java.util.HashMap;

public interface ProductCategoryService {
	
	/**
	 * 添加商品分类
	 * @param productCategory
	 */
	void saveProductCategory(ProductCategory productCategory);
	
	/**
	 * 删除商品分类
	 * @param id
	 */
	void delereProductCategory(Integer id);
	
	/**
	 * 查询所有商品分类
	 * @param map
	 * @return
	 */
	Page searchProductCategory(HashMap map);
	
	/**
	 * 查询单个商品分类
	 * @param id
	 * @return
	 */
	ProductCategory getProductCategory(Integer id);
	
	
	


	
	/**
	 * 查询商品分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ProductCategory getProductCategoryById(String id)throws Exception;
	

	
	/**
	 * 获取无限分类数组
	 * 
	 * 
	 */
	ArrayList getProductCategoryNolevel(String table, int parentId, int level, int currentId, ArrayList cats);
	
	
	


}
