package com.wechat.service;

import com.wechat.entity.Comment;
import com.wechat.entity.CommentImg;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface CommentService {

	
	/**
	 * 保存评论
	 * @param comment
	 */
	void saveComment(Comment comment);
	
	
	/**
	 * 查看评论列表
	 * @param map
	 * @return
	 */
	Page searchComment(HashMap map);
	
	/**
	 * 保存评论照片
	 * @param commentImg
	 */
	void saveCommentImg(CommentImg commentImg);
	
	/**
	 * 查询评论图片列表
	 * @param orderId
	 * @return
	 */
	List searchCommentImg(String orderId);
	
	
	/**
	 * 查询用户是否评论
	 * @param productId
	 * @param memberId
	 * @return
	 */
	String getProductComment(String orderId);
	
	
	/**
	 * 修改订单产品评论状态
	 * @param id
	 */
	void updateProductCommentStatus(String id);
}
