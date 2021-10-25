package com.wechat.service;

import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface BookOrderService {

	

	/**
	 * 提交借书订单
	 * @param bookOrder
	 */
	void saveBookOrder(BookOrder bookOrder);
	
	/**
	 * 查询订单列表
	 * @param map
	 * @return
	 */
	Page searchBookOrder(HashMap map);
	
	/*修改订单状态
	 *
	 */
	void updateBookOrderStatus(String orderId, String status);
	
	/*查询单个订单信息
	 * 
	 */
	BookOrder getBookOrder(String id);
	
	
	/**
	 * 保存借书订单详情
	 * @param bookOrderInfo
	 */
	void saveBookOrderInfo(BookOrderInfo bookOrderInfo);
	
	/**
	 * 查询订单详情
	 * @param orderId
	 * @return
	 */
	List searchBookOrderInfo(String orderId);
	
	/**
	 * 更新订单还书状态
	 * @param id
	 * @param status
	 */
	void updateBookOrderInfoStatus(String id, String status);
	
	
	/**
	 * 保存还书快递信息
	 * @param BookExpress
	 */
	void saveBookExpress(BookExpress BookExpress);
	
	/**
	 * 更新还书快递信息
	 * @param id
	 * @param status
	 */
	void updateBookExpress(String id, String status);
	
	
	
	/**
	 * 查询订单信息
	 * @param map
	 * @return
	 */
	Page searchOrderInfo(HashMap map);
	
	
	/**
	 * 修改借书订单快递信息
	 * @param orderId
	 * @param express
	 * @param expressNumber
	 * @param operatorId
	 */
	void updateBookOrderInfo(String orderId, String express, String expressNumber, String operatorId);
	
	
	
	/**
	 * 我的书籍列表
	 * @param map
	 * @return
	 */
	Page searchMemberBook(HashMap map);
	
	
	/**
	 * 修改书籍列表
	 * @param bookCode
	 * @param status
	 */
	void updateBookStatus(String bookCode, String status);
	

	/**
	 * 保存还书记录表
	 * @param memberBookInfo
	 */
	void saveMemberBookInfo(MemberBookInfo memberBookInfo);
	
	/**
	 * 更新还书订单状态
	 * @param id
	 */
	void updateMemberBookInfoStatus(String id);
	
	/**
	 * 查询还书记录表
	 * @param map
	 * @return
	 */
	Page searchBookExpress(HashMap map);
	
	
	/**
	 * 查询会员拥有的书籍
	 * @param map
	 * @return
	 */
	Page searchMemberBookInfo(HashMap map);
	
	
	/**
	 * 提价欧订单删除借书车记录
	 * @param cateId
	 * @param memberId
	 */
	void deleteBookvehicle(String cateId, String memberId);
	
	
	/**
	 * 书院订单发货
	 * @param id
	 * @param status
	 * @param express
	 * @param expressNumber
	 */
	public void updateBookOrderExpess(String id, String status, String express, String expressNumber);
	
	
	/**
	 * 查询用户还书信息
	 * @param map
	 * @return
	 */
	public Page searchMemberExpress(HashMap map);
	
	
	/*
	 *修改还书状态
	 */
	public void updateMemberExpress(String id, String status);
	
	
	/**
	 * 修改书籍库存状态
	 * @param bookCode
	 */
	public void updateBooksStatus(String bookCode);
	
	
	
	/**
	 * 图书馆添加订单
	 * @param shopOrder
	 */
	public void saveShopOrder(ShopOrder shopOrder);
	
	/**
	 * 图书馆添加订单详情
	 * @param shopOrderInfo
	 */
	public void saveShopOrderInfo(ShopOrderInfo shopOrderInfo);
	
	/**
	 * 查询图书馆订单
	 * @param map
	 * @return
	 */
	public Page searchShopOrder(HashMap map);
	
	/**
	 * 修改订单状态
	 * @param id
	 * @param status
	 */
	public void updateShopOrderStatus(String id, String status);
	
	
	/**
	 * 查询图书馆书籍库存
	 * @param map
	 * @return
	 */
	public Page searchShopBook(HashMap map);
	
	
	
	/**
	 * 查询店铺书籍汇总信息
	 * @param shopId
	 * @return
	 */
	public Object[] searchBookShopInfo(String shopId, String bookName);
	
	/**
	 * 查询店铺订单
	 * @param map
	 * @return
	 */
	public Page searchOrderBook(HashMap map);
	
	
	
	/**
	 * 还书更新状态
	 * @param code
	 */
	public void updateShopOrderInfoStatus(String code);
	
	
	
	/**
	 * 查询订单待还书籍
	 * @param orderId
	 * @return
	 */
	public List searchShopOrderInfo(String orderId);

	boolean checkShopOrder(String shopId, String memberId);
	
	
}
