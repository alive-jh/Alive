package com.wechat.service.impl;

import com.wechat.dao.ProductCategoryDao;
import com.wechat.entity.ProductCategory;
import com.wechat.service.ProductCategoryService;
import com.wechat.util.CategoryUtil;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Resource
	private ProductCategoryDao productCategoryDao;

	public void setProductCategoryDao(ProductCategoryDao productCategoryDao) {
		this.productCategoryDao = productCategoryDao;
	}

	public ProductCategoryDao getProductCategoryDao() {
		return productCategoryDao;
	}

	@Override
	public void delereProductCategory(Integer id) {
		productCategoryDao.delereProductCategory(id);
	}

	

	@Override
	public ProductCategory getProductCategory(Integer id) {
		return productCategoryDao.getProductCategory(id);
	}

	@Override
	public ProductCategory getProductCategoryById(String id) throws Exception {
		return productCategoryDao.getProductCategory(Integer.parseInt(id));
	}

	@Override
	public void saveProductCategory(ProductCategory productCategory) {
		productCategoryDao.saveProductCategory(productCategory);
	}

	@Override
	public Page searchProductCategory(HashMap map) {
		
		return productCategoryDao.searchProductCategory(map);
	}

	@Override
	public ArrayList getProductCategoryNolevel(String table, int parentId, int level,
			int currentId, ArrayList cats) {
		HashMap map=new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "1000000");
		Page bData=productCategoryDao.searchProductCategory(map);
		CategoryUtil categoryUtil = new CategoryUtil();
		return categoryUtil.getCategoryNolevel(table, bData, parentId, level, currentId, cats);
	}
	
	
	

	
    


}
