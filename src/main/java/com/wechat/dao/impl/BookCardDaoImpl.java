package com.wechat.dao.impl;

import com.wechat.dao.BookCardDao;
import com.wechat.entity.BookCard;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class BookCardDaoImpl extends BaseDaoImpl implements BookCardDao{

	@Override
	public void saveBookCard(BookCard bookCard) {
		
		this.saveEntity(bookCard);
	}

	@Override
	public Page searchBookCard(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from BookCard where 1=1 ");
		return null;
	}

	@Override
	public BookCard getBookCard(String id) {
		
		List<BookCard> list = this.executeHQL(" from BookCard where id = "+id);
		BookCard bookCard  = new BookCard();
		if(list.size()>0)
		{
			bookCard = list.get(0);
		}
		return bookCard;
	}

	@Override
	public List searchBookCardByMemberId(String memberId) {
		
		return this.executeHQL(" from BookCard where memberId = "+memberId);
	}

	@Override
	public void updateBookCard(BookCard bookCard) {
		
		this.saveOrUpdate(bookCard);
	}

	
	
}
