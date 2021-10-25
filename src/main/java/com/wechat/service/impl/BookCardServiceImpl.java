package com.wechat.service.impl;

import com.wechat.dao.BookCardDao;
import com.wechat.entity.BookCard;
import com.wechat.service.BookCardService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class BookCardServiceImpl implements BookCardService{

	@Resource
	private BookCardDao bookCardDao;
	
	
	
	public BookCardDao getBookCardDao() {
		return bookCardDao;
	}

	public void setBookCardDao(BookCardDao bookCardDao) {
		this.bookCardDao = bookCardDao;
	}

	@Override
	public void saveBookCard(BookCard bookCard) {
		
		this.bookCardDao.saveBookCard(bookCard);
	}

	@Override
	public Page searchBookCard(HashMap map) {
		
		return this.bookCardDao.searchBookCard(map);
	}

	@Override
	public BookCard getBookCard(String id) {
		
		return this.bookCardDao.getBookCard(id);
	}

	@Override
	public List searchBookCardByMemberId(String memberId) {
		
		return this.bookCardDao.searchBookCardByMemberId(memberId);
	}

	@Override
	public void updateBookCard(BookCard bookCard) {
		
		this.bookCardDao.updateBookCard(bookCard);
	}

}
