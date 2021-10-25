package com.wechat.service.impl;

import com.wechat.dao.CommentDao;
import com.wechat.entity.Comment;
import com.wechat.entity.CommentImg;
import com.wechat.service.CommentService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService  {

	@Resource
	private CommentDao commentDao;
	
	
	public CommentDao getCommentDao() {
		return commentDao;
	}


	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}


	@Override
	public void saveComment(Comment comment) {
		
		this.commentDao.saveComment(comment);
	}

	
	@Override
	public void saveCommentImg(CommentImg commentImg) {
		
		this.commentDao.saveCommentImg(commentImg);
	}

	
	@Override
	public Page searchComment(HashMap map) {
		
		return this.commentDao.searchComment(map);
	}

	
	@Override
	public List searchCommentImg(String orderId) {
		
		return this.searchCommentImg(orderId);
	}


	
	@Override
	public String getProductComment(String orderId) {
		
		return this.commentDao.getProductComment(orderId);
	}


	
	@Override
	public void updateProductCommentStatus(String id) {
		
		this.commentDao.updateProductCommentStatus(id);
		
	}

}
