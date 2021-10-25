package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Article;
import com.wechat.entity.Material;
import com.wechat.service.ArticleService;
import com.wechat.service.MaterialService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Controller
@RequestMapping("article")
public class ArticleController {

	@Resource
	private ArticleService articleService;
	
	
	public ArticleService getArticleService() {
		return articleService;
	}


	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
	@Resource
	private MaterialService materialService;

	public MaterialService getMaterialService() {
		return materialService;
	}


	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}


	@RequestMapping("/articleManager")
	public String articleManager(HttpServletRequest request,QueryDto queryDto) {
		

		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("title", queryDto.getTitle());
		map.put("content", queryDto.getContent());
		map.put("parentId", queryDto.getParentId());
		Page resultPage = this.articleService.searchArticle(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		return "article/articleManager";
	}
	
	@RequestMapping("/toArticle")
	public String toArticle(HttpServletRequest request,String articleId) throws Exception{
		

		
		Article article   = new Article();
		if(articleId != null)
		{
			article = this.articleService.getArticle(new Integer(articleId));
		}
		if(request.getParameter("materialId")!= null)
		{
			Material material = new Material();
			material.setId(new Integer(request.getParameter("materialId")));
			material = this.materialService.getMaterial(material);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			article.setReleaseDate(sdf.format(material.getCreateDate()));
			article.setTitle(material.getTitle());
			article.setContent(material.getContent());
			article.setSource(Keys.PROJECT_NAME);

		}
		
		request.setAttribute("article", article);
		return "article/articleView";
	}
	
	@RequestMapping(value="/articleManagerView")
	public void articleManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		map.put("title", queryDto.getTitle());
		map.put("content", queryDto.getContent());
		map.put("parentId", queryDto.getParentId());
		
		map.put("articleId", request.getParameter("articleId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		Page resultPage = this.articleService.searchArticle(map);
		List<Article> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();
			
			
			
			for(Article article : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", article.getId());
				jobj.put("title", article.getTitle());
				jobj.put("content", article.getContent());
				jobj.put("source", article.getSource());
				jobj.put("createDate", sdf.format(article.getCreateDate()).toString());
				jobj.put("releaseDate", article.getReleaseDate().toString());
				jobj.put("parentId", article.getParentId());
				
				if(article.getParentId()==1)
				{
					jobj.put("parentName", "微官网");
				}
				if(article.getParentId()==2)
				{
					jobj.put("parentName", "最新动态");
				}
				if(article.getParentId()==3)
				{
					jobj.put("parentName", "产品介绍");
				}
				if(article.getParentId()==4)
				{
					jobj.put("parentName", "业务分类");
				}
				if(article.getParentId()==5)
				{
					jobj.put("parentName", "服务流程");
				}
				
				
				
				
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		 
		 
	}
	@RequestMapping("/articleInfo")
	public String articleInfo(HttpServletRequest request,QueryDto queryDto) {
		

		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "20");
		map.put("title", queryDto.getTitle());
		map.put("content", queryDto.getContent());
		map.put("parentId", queryDto.getParentId());
		Page resultPage = this.articleService.searchArticle(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		return "article/articleInfo";
	}
	@RequestMapping(value="/saveArticle")
	public String saveArticle(HttpServletRequest request,HttpServletResponse response,Article article)throws Exception{
		
		if(!"".equals(article.getId())  &&  article.getId()!= null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			article.setCreateDate(sdf.parse(request.getParameter("tempDate")));
		}
		else
		{
			article.setCreateDate(new Date());
		}
		this.articleService.saveArticle(article);
		return "redirect:articleManager";
		 
		 
	}
	
	
	@RequestMapping(value = "/deleteArticle")
	public String delArticle(HttpServletRequest request,HttpServletResponse response) {

		
		this.articleService.deleteArticle(new Integer(request.getParameter("articleId")));
		
		return "redirect:articleManager";
	}
}
