package com.wechat.service.impl;

import com.wechat.dao.ProductDao;
import com.wechat.entity.Product;
import com.wechat.entity.ProductInfo;
import com.wechat.service.ProductService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("productService")
public class ProductServiceImpl implements ProductService{

	@Resource
	private ProductDao productDao;
	
	public ProductDao getProductDao() {
		return productDao;
	}


	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}


	@Override
	public void delereProduct(String id) {
		
		this.productDao.delereProduct(id);
	}

	
	@Override
	public void delereProductInfo(Integer id) {
		
		this.productDao.delereProductInfo(id);
	}

	
	@Override
	public Product getProduct(Integer id) {
		
		return this.productDao.getProduct(id);
	}

	
	@Override
	public ProductInfo getProductInfo(Integer id) {
		
		return this.productDao.getProductInfo(id);
	}

	
	@Override
	public void saveProduct(Product product) {
		
		this.productDao.saveProduct(product);
	}

	
	@Override
	public void saveProductInfo(ProductInfo productInfo) {
		
		this.productDao.saveProductInfo(productInfo);
	}

	
	@Override
	public Page searchProduct(HashMap map) {
		
		return this.productDao.searchProduct(map);
	}

	
	@Override
	public Page searchProductInfo(HashMap map) {
		
		return this.productDao.searchProductInfo(map);
	}


	
	@Override
	public Object[] getProductInfoById(String id) throws Exception {
		
		return this.productDao.getProductInfoById(id);
	}


	
	@Override
	public void updateProductByMemberId(String productId, String memberId,
			String type) throws Exception {
		
		this.productDao.updateProductByMemberId(productId, memberId, type);
	}


	
	@Override
	public Page searchProductByMember(HashMap map) throws Exception {
		
		return this.productDao.searchProductByMember(map);
	}

}
