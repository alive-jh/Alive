package com.wechat.controller;

import com.wechat.entity.Category;
import com.wechat.entity.Library;
import com.wechat.service.CategoryService;
import com.wechat.service.LibraryService;
import com.wechat.service.RedisService;
import com.wechat.util.JsonResult;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("api")
public class LibraryController {

	@Resource
	private LibraryService libraryService;
	
	@Resource
	private CategoryService categoryService;


	@Resource
	private RedisService redisService;
	
	/**
	 * 保存
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveLibrary")
	public void saveLibrary(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String ISBN  = request.getParameter("isbn");
			String bookName = request.getParameter("bookName");
			Library library = new Library();

			library.setISBN(ISBN);
			library.seBookName(bookName);
			libraryService.saveLibrary(library);
			
			JsonResult.JsonResultInfo(response, library);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	public String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}  
	/**
	 * 根据ISBM获取书名
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getBookNameFromISBN")
	public void getBookNameFromISBN(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String ISBN = request.getParameter("isbn");
			map.put("isbn", ISBN);
			String version = ParameterFilter.emptyFilter("1", "version", request);
			// 1为字典，2为名字列表
	        try{
	    	    String remoteIP = getIpAddr(request);
	        	JSONObject searchInfo = new JSONObject();
				String key = "BookNameFromISBN";
				searchInfo.put("ISBN", ISBN);
				searchInfo.put("version", version);
				searchInfo.put("remoteIP", remoteIP);
				this.redisService.lpush(key,searchInfo.toString());
				
	        }catch (Exception e){
	        	e.printStackTrace();
	        }

			if("1".equals(version)){
				List result = categoryService.searchBookByCode(ISBN);
				String bookName = "";
				
				if(result.size() == 0){
					bookName = libraryService.getBookName(ISBN);
				}else{
					Category category = (Category)result.get(0);
					bookName = category.getbName();
				}
				Library library = new Library();
				library.seBookName(bookName);
				library.setISBN(ISBN);
				if("".equals(bookName)||null==bookName){
					JsonResult.JsonResultError(response, 1000);
				}else{
					JsonResult.JsonResultInfo(response, library);
				}
			}else{
				JSONArray result = new JSONArray();
				
				
				JSONArray NameList = new JSONArray();
				
				List categoryList = categoryService.searchBookByCode(ISBN);
				for(int i=0;i<categoryList.size();i++){
					JSONObject temp = new JSONObject();
					temp.put("isbn", ISBN);
					Category category = (Category)categoryList.get(i);
					temp.put("bookName", category.getbName());
					result.add(temp);
					
				}
				
				JSONArray bookNameList = libraryService.getbookNameList(ISBN);
				for(int i=0;i<bookNameList.size();i++){
					JSONObject temp = new JSONObject();
					temp.put("isbn", ISBN);
					temp.put("bookName", bookNameList.getJSONObject(i).getString("bookName"));
					result.add(temp);
				}
				
				
				
	
				JsonResult.JsonResultInfo(response, result);

			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
}