package com.wechat.dao;

import com.wechat.entity.Article;
import com.wechat.entity.ArticleLog;
import com.wechat.util.Page;

import java.util.HashMap;

public interface ArticleDao {
	
	
	/**
	 * 保存/更新文章
	 * @param article
	 */
	void saveArticle(Article article);
	/**
	 * 查询单个文章
	 * @param id
	 * @return
	 */
	Article getArticle(Integer id);
	/**
	 * 查询文章列表
	 * @param map
	 * @return
	 */
	Page searchArticle(HashMap map);
	/**
	 * 删除文章
	 * @param id
	 */
	void deleteArticle(Integer id);

	
	/**
	 * 保存访问or转发记录
	 * @param articleLog
	 */
	void saveArticleLog(ArticleLog articleLog);
	
	/**
	 * 查询记录列表
	 * @param map
	 * @return
	 */
	Page searchArticleLog(HashMap map);
	
	/**
	 * 查看访问记录map
	 * @return
	 */
	HashMap searchArticleLog(String accountIds);
}
