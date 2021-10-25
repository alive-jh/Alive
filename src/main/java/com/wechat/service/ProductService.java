package com.wechat.service;

import com.wechat.entity.Product;
import com.wechat.entity.ProductInfo;
import com.wechat.util.Page;

import java.util.HashMap;

public interface ProductService {
	
	/**
	 * 添加产品参数
	 * @param productInfo
	 */
	void saveProductInfo(ProductInfo productInfo);
	
	/**
	 * 删除产品参数
	 * @param id
	 */
	void delereProductInfo(Integer id);
	
	/**
	 * 查询产品参数集合
	 * @param map
	 * @return
	 */
	Page searchProductInfo(HashMap map);
	
	/**
	 * 查询单个产品参数
	 * @param id
	 * @return
	 */
	ProductInfo getProductInfo(Integer id);
	
	
	
	/**
	 * 添加产品
	 * @param product
	 */
	void saveProduct(Product product);
	
	/**
	 * 删除产品
	 * @param id
	 */
	void delereProduct(String id);
	/**
	 * 查询产品集合
	 * @param map
	 * @return
	 */
	Page searchProduct(HashMap map);
	
	/**
	 * 查询单个产品
	 * @param id
	 * @return
	 */
	Product getProduct(Integer id);
	
	/**
	 * 查询二维码产品信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Object[] getProductInfoById(String id)throws Exception;
	
	
	/**
	 * 扫描二维码绑定灯泡
	 * @param productId
	 * @param memberId
	 * @param type
	 * @throws Exception
	 */
	void updateProductByMemberId(String productId, String memberId, String type)throws Exception;
	
	
	/**
	 * 查询用户二维码列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Page searchProductByMember(HashMap map) throws Exception;
	
	


}
