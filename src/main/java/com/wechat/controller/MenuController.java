package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.AccessToken;
import com.wechat.entity.Menu;
import com.wechat.service.ArticleService;
import com.wechat.service.MaterialService;
import com.wechat.service.MenuService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import com.wechat.util.WeChatUtil;
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
@RequestMapping("menu")
public class MenuController {

	@Resource
	private MenuService menuService;

	@Resource
	private ArticleService articleService;
	
	@Resource
	private MaterialService materialService;
	
	
	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	
	@RequestMapping("/menuManager")
	public String menuManager(HttpServletRequest request,QueryDto queryDto) throws Exception{
		

		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		map.put("accountId", Keys.ACCOUNT_ID);
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", "50");
		Page resultPage = this.menuService.searchMenu(map);
		request.setAttribute("resultPage", resultPage);
		
		map.put("rowsPerPage", "100");
		map.put("parentId", "0");
		
		List<Menu> parentList = this.menuService.searchMenuByParentId(map);
		request.setAttribute("parentList",parentList);//查询一级栏目
		request.setAttribute("queryDto", queryDto);
		
		
		map = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "100");
		resultPage = this.articleService.searchArticle(map);
		request.setAttribute("articleList", resultPage.getItems());//查询内容集合
		
		
		resultPage = this.materialService.searchMaterial(map);//查询图文集合
		request.setAttribute("materialList", resultPage.getItems());
		return "menu/menuManager";
	}
	
	
	@RequestMapping(value="/menuManagerView")
	public void menuManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		



		HashMap map = new HashMap();
//		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
//		{
//			queryDto.setPage(request.getParameter("page"));
//		}
//		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
//		{
//			queryDto.setPageSize(request.getParameter("pageSize"));
//		}
//
//		map.put("page", queryDto.getPage());
//		map.put("rowsPerPage", "20");
//		Page resultPage = this.menuService.searchMenu(map);
//		List<Menu> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		HashMap seaechMap = new HashMap();
		map.put("page", "1");
		map.put("rowsPerPage", "100");
		map.put("parentId", "0");
		
		List<Menu> parentList = this.menuService.searchMenuByParentId(map);
		
		List<Menu> tempList  = new ArrayList();
		List<Menu> resultList = new ArrayList();
		for (int i = 0; i < parentList.size(); i++) {
			
			resultList.add(parentList.get(i));
			map.put(parentList.get(i).getId().toString(),parentList.get(i).getName());
			seaechMap = new HashMap();
			seaechMap.put("parentId", parentList.get(i).getId().toString());
			tempList = this.menuService.searchMenuByParentId(seaechMap);
			if(tempList.size()>0)
			{
				
				for (int j = 0; j < tempList.size(); j++) {
					resultList.add(tempList.get(j));
				}
			}
		}
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (resultList!=null) {
			for(Menu menu : resultList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", menu.getId());
				jobj.put("name", menu.getName());
				jobj.put("type", menu.getType());
				jobj.put("url", menu.getUrl());
				jobj.put("createDate", sdf.format(menu.getCreateDate()).toString());
				jobj.put("parentId", menu.getParentId());
				
				if(menu.getParentId()==0)
				{
					
						seaechMap = new HashMap();
						seaechMap.put("parentId", menu.getId().toString());
						tempList = this.menuService.searchMenuByParentId(seaechMap);
						if(tempList.size()>0)
						{
							jobj.put("isParentId", "1");
						}
					
					jobj.put("typeName", "<font color='red'>一级菜单</font>");
					jobj.put("parentName", "<font color='red'>主菜单</font>");
				}
				if(menu.getParentId()!=0)
				{
					jobj.put("typeName", "二级菜单");
					if(map.get(menu.getParentId().toString())!= null)
					{
						jobj.put("parentName", map.get(menu.getParentId().toString()).toString());
					}
					jobj.put("isParentId", "0");
				}
				
				
				jsonList.add(jobj);
			} 
			jsonObj.put("infoList", jsonList);
			
		}
		
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		 
		 
	}
	
	
	@RequestMapping(value="/menuManagerViewById")
	public void menuManagerViewById(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		



		

		List jsonList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		HashMap seaechMap = new HashMap();
	
		seaechMap.put("menuId", request.getParameter("menuId"));
		List<Menu> resultList = this.menuService.searchMenuByParentId(seaechMap);
		
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (resultList!=null) {
			for(Menu menu : resultList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", menu.getId());
				jobj.put("name", menu.getName());
				jobj.put("type", menu.getType());
				jobj.put("url", menu.getUrl());
				jobj.put("createDate", sdf.format(menu.getCreateDate()).toString());
				jobj.put("parentId", menu.getParentId());
				
				if(menu.getParentId()==0)
				{
					jobj.put("typeName", "<font color='red'>一级菜单</font>");
					jobj.put("parentName", "<font color='red'>主菜单</font>");
				}
				if(menu.getParentId()!=0)
				{
					jobj.put("typeName", "二级菜单");
					
				}
				
				
				jsonList.add(jobj);
			} 
			jsonObj.put("infoList", jsonList);
			
		}
		
		
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		
		 
		 
	}
	
	
	@RequestMapping(value="/saveMenu")
	public String saveMenu(HttpServletRequest request,HttpServletResponse response,Menu menu)throws Exception{
		
		if(!"".equals(menu.getId())  &&  menu.getId()!= null )
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			menu.setCreateDate(sdf.parse(request.getParameter("tempDate")));
		}
		else
		{
			menu.setCreateDate(new Date());
		}
		if(menu.getType() ==0)
		{
			menu.setParentId(new Integer(0));
		}
		this.menuService.saveMenu(menu);
		return "redirect:menuManager";
		 
		 
	}
	
	
	@RequestMapping(value="/updateMenu")
	public String updateMenu(HttpServletRequest request,HttpServletResponse response,Menu menu)throws Exception{
		

		
		AccessToken accessToken  = WeChatUtil.getAccessToKen(Keys.APP_ID,Keys.APP_SECRET,"");
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+accessToken.getAccessToken();
		JSONObject jobj = WeChatUtil.httpRequest(url,"");//先清空以前的数据
		String jsonStr =  "";
		if("ok".equals(jobj.get("errmsg").toString()))
		{
			url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken.getAccessToken();
			
			
			HashMap map = new HashMap();
			List<Menu> tempList = new ArrayList();
			map.put("parentId", "0");
			map.put("accountId", Keys.ACCOUNT_ID);
			List<Menu> parentList = this.menuService.searchMenuByParentId(map);
			
			if(parentList.size()>0)
			{
				StringBuffer buttonStr = new StringBuffer("{\"button\":[");
				for (int i = 0; i < parentList.size(); i++) {
					
					 map = new HashMap();
					 map.put("parentId", parentList.get(i).getId());
					 map.put("accountId", Keys.ACCOUNT_ID);
					 tempList = this.menuService.searchMenuByParentId(map);
					if(tempList.size()>0)
					{
						buttonStr.append("{\"name\":\""+parentList.get(i).getName()+"\",\"sub_button\":[");
						for (int j = 0; j < tempList.size(); j++) {
							
							//buttonStr.append("{\"type\":\"view\",\"name\":\""+tempList.get(j).getName()+"\",\"url\":\""+tempList.get(j).getUrl()+"\"}");
							if((tempList.get(j).getUrl().lastIndexOf("materialId")!= -1))
							{
								buttonStr.append("{\"type\":\"click\",\"name\":\""+tempList.get(j).getName()+"\",\"key\":\""+tempList.get(j).getUrl()+"\"}");
								
							}
							else
							{
								buttonStr.append("{\"type\":\"view\",\"name\":\""+tempList.get(j).getName()+"\",\"url\":\""+tempList.get(j).getUrl()+"\"}");
								
							}
							
							if(j+1!= tempList.size())
							{
								buttonStr.append(",");
							}
						}
						buttonStr.append("]}");
					}
					else
					{
						if((parentList.get(i).getUrl().lastIndexOf("materialId")!= -1))
						{
							buttonStr.append("{\"type\":\"click\",\"name\":\""+parentList.get(i).getName()+"\",\"key\":\""+parentList.get(i).getUrl()+"\"}");
							
						}
						else
						{
							buttonStr.append("{\"type\":\"view\",\"name\":\""+parentList.get(i).getName()+"\",\"url\":\""+parentList.get(i).getUrl()+"\"}");
							
						}
						
						
					}
					if(i+1!= parentList.size())
					{
						buttonStr.append(",");
					}
				}
				buttonStr.append("]}");
				//System.out.println(buttonStr.toString());
				jobj = WeChatUtil.httpRequest(url,buttonStr.toString());//更新自定义菜单
				//System.out.println(jobj.toString());
				if("ok".equals(jobj.get("errmsg").toString()))
				{
					jsonStr="{\"data\":{\"status\":\"同步成功!\"}}";
				}
				else
				{
					jsonStr="{\"data\":{\"status\":\"同步失败!\"}}";
				}
				
				
			}
			
			
		}
		else
		{
			jsonStr="{\"data\":{\"status\":\"同步失败!\"}}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonStr);
		
		return null;
		 
		 
	}
	
	
	@RequestMapping(value = "/deleteMenu")
	public String deleteMenu(HttpServletRequest request,HttpServletResponse response) {

		Menu menu = new Menu();
		menu.setId(new Integer(request.getParameter("menuId")));
		this.menuService.deleteMenu(menu);
		if("1".equals(request.getParameter("type")))
		{
			this.menuService.deleteMenuByParentId(request.getParameter("menuId"));
		}
		
		return "redirect:menuManager";
	}
	
	public static void main(String[] args) {
		
		String s = "materialId=13";
		//System.out.println(s.substring(11,s.length()));
	}
}
