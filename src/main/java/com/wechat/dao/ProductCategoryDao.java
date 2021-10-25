package com.wechat.dao;

import com.wechat.entity.ProductCategory;
import com.wechat.util.Page;

import java.util.HashMap;

public interface ProductCategoryDao {
	
	/**
	 * 添加商品分类
	 * @param ProductCategory
	 */
	void saveProductCategory(ProductCategory productCategory);
	
	/**
	 * 删除商品分类
	 * @param id
	 */
	void delereProductCategory(Integer id);
	
	/**
	 * 查询商品分类集合
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
	

	
	

	
	
	
}
