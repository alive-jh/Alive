package com.wechat.service.impl;

import com.wechat.dao.BookOrderDao;
import com.wechat.entity.*;
import com.wechat.service.BookOrderService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("bookOrderService")
public class BookOrderServiceImpl implements BookOrderService{

	@Resource
	private BookOrderDao bookOrderDao;
	
	
	public BookOrderDao getBookOrderDao() {
		return bookOrderDao;
	}

	public void setBookOrderDao(BookOrderDao bookOrderDao) {
		this.bookOrderDao = bookOrderDao;
	}

	
	public void saveBookOrder(BookOrder bookOrder) {
		
		this.bookOrderDao.saveBookOrder(bookOrder);
	}

	
	public Page searchBookOrder(HashMap map) {
		
		return this.bookOrderDao.searchBookOrder(map);
	}

	
	public void updateBookOrderStatus(String orderId, String status) {
		
		this.bookOrderDao.updateBookOrderStatus(orderId, status);
	}

	
	public BookOrder getBookOrder(String id) {
		
		return this.bookOrderDao.getBookOrder(id);
	}

	
	public void saveBookOrderInfo(BookOrderInfo bookOrderInfo) {
		
		this.bookOrderDao.saveBookOrderInfo(bookOrderInfo);
	}

	
	public List searchBookOrderInfo(String orderId) {
		
		return this.bookOrderDao.searchBookOrderInfo(orderId);
	}

	
	public void updateBookOrderInfoStatus(String id, String status) {
		
		this.bookOrderDao.updateBookOrderInfoStatus(id, status);
	}

	
	public void saveBookExpress(BookExpress BookExpress) {
		
		this.bookOrderDao.saveBookExpress(BookExpress);
	}

	
	public void updateBookExpress(String id, String status) {
		
		this.bookOrderDao.updateBookExpress(id, status);
	}

	
	public Page searchOrderInfo(HashMap map) {
		
		return this.bookOrderDao.searchOrderInfo(map);
	}

	
	public void updateBookOrderInfo(String orderId, String express,
			String expressNumber, String operatorId) {
		
		this.bookOrderDao.updateBookOrderInfo(orderId, express, expressNumber, operatorId);
	}

	
	public Page searchMemberBook(HashMap map) {
		
		return this.bookOrderDao.searchMemberBook(map);
	}

	
	public void updateBookStatus(String bookCode, String status) {
		
		this.bookOrderDao.updateBookStatus(bookCode, status);
	}

	
	public void saveMemberBookInfo(MemberBookInfo memberBookInfo) {
		
		this.bookOrderDao.saveMemberBookInfo(memberBookInfo);
	}

	
	public void updateMemberBookInfoStatus(String id) {
		
		this.bookOrderDao.updateMemberBookInfoStatus(id);
	}

	
	public Page searchBookExpress(HashMap map) {
		
		return this.bookOrderDao.searchBookExpress(map);
	}

	
	public Page searchMemberBookInfo(HashMap map) {
		
		
		return this.bookOrderDao.searchMemberBookInfo(map);
	}

	
	public void deleteBookvehicle(String cateId, String memberId) {
		
		this.bookOrderDao.deleteBookvehicle(cateId, memberId);
		
	}

	
	public void updateBookOrderExpess(String id, String status, String express,
			String expressNumber) {
		
		this.bookOrderDao.updateBookOrderExpess(id, status, express, expressNumber);
		
	}

	
	public Page searchMemberExpress(HashMap map) {
		
		return this.bookOrderDao.searchMemberExpress(map);
	}

	
	public void updateMemberExpress(String id, String status) {
		
		this.bookOrderDao.updateBookExpress(id, status);
	}

	
	public void updateBooksStatus(String bookCode) {
	
		this.bookOrderDao.updateBooksStatus(bookCode);
	}

	
	public void saveShopOrder(ShopOrder shopOrder) {
		
		this.bookOrderDao.saveShopOrder(shopOrder);
	}

	
	public void saveShopOrderInfo(ShopOrderInfo shopOrderInfo) {
		
		this.bookOrderDao.saveShopOrderInfo(shopOrderInfo);
	}

	
	public Page searchShopOrder(HashMap map) {
		
		return this.bookOrderDao.searchShopOrder(map); 
	}

	
	public void updateShopOrderStatus(String id, String status) {
		
		this.bookOrderDao.updateShopOrderStatus(id, status);
		
	}

	
	public Page searchShopBook(HashMap map) {
		
		return this.bookOrderDao.searchShopBook(map);
	}

	
	public Object[] searchBookShopInfo(String shopId,String bookName) {
		
		return this.bookOrderDao.searchBookShopInfo(shopId,bookName);
	}

	
	public Page searchOrderBook(HashMap map) {
		
		return this.bookOrderDao.searchOrderBook(map);
	}

	
	public void updateShopOrderInfoStatus(String code) {
		
		this.bookOrderDao.updateShopOrderInfoStatus(code);
		
	}

	
	public List searchShopOrderInfo(String orderId) {
		
		return this.bookOrderDao.searchShopOrderInfo(orderId);
	}

	@Override
	public boolean checkShopOrder(String shopId, String memberId) {
		// TODO Auto-generated method stub
		return this.bookOrderDao.checkShopOrder(shopId,memberId);
	}

}
