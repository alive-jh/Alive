package com.wechat.dao;

import com.wechat.entity.BookCard;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface BookCardDao {

	
	
		/**
		 * 新增会员卡
		 * @param bookCard
		 */
		void saveBookCard(BookCard bookCard);
		
		/**
		 * 查询会员卡列表
		 * @param map
		 * @return
		 */
		Page searchBookCard(HashMap map);
		
		/**
		 * 查询单个会员卡
		 * @param id
		 * @return
		 */
		BookCard getBookCard(String id);
		
		/**
		 * 查询会员的会员卡信息
		 * @param memberId
		 * @return
		 */
		List searchBookCardByMemberId(String memberId);
		
		/**
		 * 会员卡激活
		 * @param bookCard
		 */
		void updateBookCard(BookCard bookCard);
		
		
		
}
