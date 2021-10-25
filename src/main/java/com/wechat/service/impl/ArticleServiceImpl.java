package com.wechat.service.impl;

import com.wechat.dao.ArticleDao;
import com.wechat.entity.Article;
import com.wechat.entity.ArticleLog;
import com.wechat.service.ArticleService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService{

	@Resource
	private ArticleDao articleDao;
	
	
	public ArticleDao getArticleDao() {
		return articleDao;
	}


	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}


	@Override
	public void deleteArticle(Integer id) {
		
		this.articleDao.deleteArticle(id);
	}

	
	@Override
	public Article getArticle(Integer id) {
		
		return this.articleDao.getArticle(id);
	}

	
	@Override
	public void saveArticle(Article article) {
		
		this.articleDao.saveArticle(article);
	}

	
	@Override
	public Page searchArticle(HashMap map) {
		
		return this.articleDao.searchArticle(map);
	}


	
	@Override
	public void saveArticleLog(ArticleLog articleLog) {
		
		this.articleDao.saveArticleLog(articleLog);
	}


	
	@Override
	public Page searchArticleLog(HashMap map) {
		
		return this.articleDao.searchArticleLog(map);
	}


	
	@Override
	public HashMap searchArticleLog(String accountIds) {
		
		return this.articleDao.searchArticleLog(accountIds);
	}

}
