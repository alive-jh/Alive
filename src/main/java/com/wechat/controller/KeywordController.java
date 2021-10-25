package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Keyword;
import com.wechat.service.KeywordService;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@RequestMapping("/keyword")
public class KeywordController {

	@Resource
	private KeywordService KeywordService;

	public KeywordService getKeywordService() {
		return KeywordService;
	}

	public void setKeywordService(KeywordService KeywordService) {
		this.KeywordService = KeywordService;
	}
	

	@RequestMapping(value="/keywordManager")
	public String keywordManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
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
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("keyword", queryDto.getKeyword());
		map.put("type", queryDto.getType());
		map.put("matchingrules", queryDto.getMatchingrules());
		map.put("status", queryDto.getStatus());
		
		
		//map.put("accountId", Keys.ACCOUNT_ID);
		
		
		Page resultPage = this.KeywordService.searchKeyword(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("contentType", request.getParameter("contentType"));
		return "keyword/keywordManager";
		 
		 
	}

	@RequestMapping(value="/toKeyword")
	public String toSaveUser(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
				
		
	
		String keywordId = request.getParameter("keywordId");
		Keyword keyword = new Keyword();
		if(keywordId!= null && !"".equals(keywordId))
		{
			
			keyword.setId(new Integer(keywordId));
			keyword = this.KeywordService.getKeyword(keyword);
			
		}
		request.setAttribute("Keyword", keyword);
		
		if("0".equals(request.getParameter("contentType")))
		{
			return "keyword/keywordModify";
		}
		else
		{
			JSONObject jobj = new JSONObject();
			
			jobj.put("id", keyword.getId());
			jobj.put("keyword", keyword.getKeyword());
			jobj.put("contentType", keyword.getContentType());
			jobj.put("atchingRules", keyword.getMatchingRules());
			jobj.put("status", keyword.getStatus());
			response.setContentType("application/json;charset=UTF-8");
			String s = "{\"data\":["+jobj.toString()+"]}";
			String jsonStr="{\"data\":{\"status\":\"修改成功!\"}}";
			//System.out.println("jsonStr = "+s.toString());
			response.getWriter().println(jsonStr);
			return null;
			
		}
		
		 
		 
		 
	}
	/** 
	 * 计算采用utf-8编码方式时字符串所占字节数 
	 *  
	 * @param content 
	 * @return 
	 */  
	public static int getByteSize(String content) {  
	    int size = 0;  
	    if (null != content) {  
	        try {  
	            // 汉字采用utf-8编码时占3个字节  
	            size = content.getBytes("utf-8").length;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    return size;  
	} 
	
	@RequestMapping(value="/saveKeyword")
	public String saveKeyword(HttpServletRequest request,HttpServletResponse response,Keyword keyword)throws Exception{
		
		
	
		if(request.getParameter("id")!= null && !"".equals(request.getParameter("id")))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			keyword.setId(new Integer(request.getParameter("id")));
			Keyword tempKeyword = this.KeywordService.getKeyword(keyword);
			keyword.setCreateDate(sdf.parse(tempKeyword.getCreateDate().toString()));
			keyword.setAccountId(new Integer(Keys.ACCOUNT_ID));
			keyword.setMaterialId(tempKeyword.getMaterialId());

			if(keyword.getStatus() ==null)
			{
				keyword.setStatus(1);
				
			}
		}
		else
		{
			keyword.setCreateDate(new Date());
		}
		keyword.setContentType(new Integer(0));
		keyword.setAccountId(new Integer(Keys.ACCOUNT_ID));
		
		
		this.KeywordService.saveKeyword(keyword);
		return "redirect:keywordManager";
		 
	}
	
	
	
	@RequestMapping(value="/materialManager")
	public String materialManager(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		request.setAttribute("addType", request.getParameter("addType"));
		HashMap map = new HashMap();
		
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		request.setAttribute("title", "关键词图文管理");
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("keyword", queryDto.getKeyword());
		map.put("account", queryDto.getAccount());
		map.put("status", queryDto.getStatus());
		map.put("contentType", request.getParameter("contentType"));
		map.put("email", queryDto.getEmail());
		map.put("mobile", queryDto.getMobile());
		
		
		//map.put("accountId", Keys.ACCOUNT_ID);
		
		
//		Page resultPage = this.MaterialService.searchMaterial(map);
//		for (int i = 0; i < resultPage.getItems().size(); i++) {
//			
//			if(((Material)resultPage.getItems().get(i)).getParentId() ==0 && ((Material)resultPage.getItems().get(i)).getType() ==1)
//			{
//				((Material)resultPage.getItems().get(i)).setMaterialList(this.MaterialService.searchMaterialByParentId(((Material)resultPage.getItems().get(i)).getId().toString()));
//			}
//		}
//		
//		HashMap keywordMap = this.KeywordService.searchAllKeywordMap(Account.getId().toString());
//		Material Material = new Material();
//		for (int i = 0; i < resultPage.getItems().size(); i++) {
//
//			Material = (Material)resultPage.getItems().get(i);
//			if(keywordMap.get(Material.getId().toString())!= null)
//			{
//				Material.setKeyword((Keyword)keywordMap.get(Material.getId().toString()));
//			}
//			
//		}
//		request.setAttribute("resultPage", resultPage);
//		request.getSession().setAttribute("queryDto", queryDto);
//		request.getSession().setAttribute("contentType", request.getParameter("contentType"));
		return "material/materialManager";
		 
		 
	}

	@RequestMapping(value="/keywordManagerView")
	public String keywordManagerView(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		HashMap map = new HashMap();
		map.put("keywordId", request.getParameter("keywordId"));
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
	
		
		map.put("accountId", Keys.ACCOUNT_ID);
		Page resultPage = this.KeywordService.searchKeyword(map);
		List<Object[]> infoList  = resultPage.getItems();
		List jsonList = new ArrayList();
		
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			
			for(Object[] obj : infoList) {
				JSONObject jobj = new JSONObject();
					
				jobj.put("id", obj[0]);
				jobj.put("keyword", obj[1]);
				jobj.put("content", obj[2]);
				jobj.put("createDate", obj[3]);
				jobj.put("matchingRules",obj[4]);
				jobj.put("status", obj[6]);
				if("0".equals(obj[4].toString()))
				{
					jobj.put("matchingRulesName", "精准匹配");
				}
				if("1".equals(obj[4].toString()))
				{
					jobj.put("matchingRulesName", "模糊匹配");
				}
				
				if("0".equals(obj[6].toString()))
				{
					jobj.put("statusName", "启用");
				}
				if("1".equals(obj[6].toString()))
				{
					jobj.put("statusName", "关闭");
				}
				//jobj.put("status", obj[5]);
				
				
			
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
		 
		 
	}
	
	
	
	@RequestMapping(value="/removeKeyword")
	public String removeKeyword(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto)throws Exception{
		

		
		this.KeywordService.removeKeyword(request.getParameter("keywordId"));

		return "redirect:keywordManager";
		 
		 
	}
	
	
	@RequestMapping(value="/searchKeyword")
	public String getUserPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{

		String keywordName = request.getParameter("keywordName");
		Keyword keyword = new Keyword();
		Object[] obj = this.KeywordService.searchKeyworObject(keywordName);
		String jsonObj = "";
		if(obj!= null)
		{
			
			jsonObj="{\"data\":{\"status\":\"ok\",\"keywordId\":\""+obj[5]+"\"}}";
		}
		else
		{
			jsonObj="{\"data\":{\"status\":\"error\"}}";
		}
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().println(jsonObj.toString());
		return  null;
 
	}
	
}
