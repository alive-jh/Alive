package com.wechat.dao.impl;

import com.wechat.dao.ArticleDao;
import com.wechat.entity.Article;
import com.wechat.entity.ArticleLog;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao
{

	
	@Override
	public void deleteArticle(Integer id) {
		
		Article article = new Article();
		article.setId( new Integer(id));
		this.delete(article);
	}

	
	@Override
	public Article getArticle(Integer id) {
		
		return (Article)this.getEntity(Article.class, id);
	}

	
	@Override
	public void saveArticle(Article article) {
		
		this.saveOrUpdate(article);
	}

	
	@Override
	public Page searchArticle(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from Article where 1=1 ");
		
		if(!"".equals(map.get("articleId")) && map.get("articleId")!= null)
		{
			sql.append("  and id = ").append(map.get("articleId").toString());
		}
		if(!"".equals(map.get("parentId")) && map.get("parentId")!= null)
		{
			sql.append("  and parentId = ").append(map.get("parentId").toString());
		}
		if(!"".equals(map.get("title")) && map.get("title")!= null)
		{
			sql.append("  and title like '%").append(map.get("title").toString()).append("%'");
		}
		if(!"".equals(map.get("content")) && map.get("content")!= null)
		{
			sql.append("  and content like '%").append(map.get("content").toString()).append("%'");
		}
		sql.append(" order by createDate desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}


	
	@Override
	public void saveArticleLog(ArticleLog articleLog) {
		
		this.save(articleLog);
	}


	
	@Override
	public Page searchArticleLog(HashMap map) {
		
		StringBuffer sql = new StringBuffer(" from ArticleLog where 1=1 ");
		sql.append(" order by createDate desc ");
		
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
		
	}


	
	@Override
	public HashMap searchArticleLog(String accountIds) {
		
		StringBuffer sql = new StringBuffer(" select accountid,sum(if(type=0,0,1)) as fangwen,sum(if(type=1,0,1)) as zhuanfa from article_log group by accountid ");
		sql.append(" order by fangwen desc ");
		List<Object[]> list = this.executeHQL(sql.toString());
		
		HashMap map = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			
			map.put(list.get(i)[0].toString(), list.get(i));
		}
		return map;
	} 

	
}
