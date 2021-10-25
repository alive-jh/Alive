package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.AppIndex;
import com.wechat.service.BookService;
import com.wechat.service.MallProductService;
import com.wechat.service.RedisService;
import com.wechat.util.Page;
import com.wechat.util.RedisKeys;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("appIndex")
public class AppIndexController {

	
	@Resource
	private RedisService redisService;
	
	
	public RedisService getRedisService() {
		return redisService;
	}

	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	
	
	@Resource
	private MallProductService mallProductServier;
	
	
	
	public MallProductService getMallProductServier() {
		return mallProductServier;
	}



	public void setMallProductServier(MallProductService mallProductServier) {
		this.mallProductServier = mallProductServier;
	}

	
	@Resource
	private BookService bookService;
	

	public BookService getBookService() {
		return bookService;
	}



	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	

	@RequestMapping("appIndexManager")
	public String getDevicesInfo(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("title", queryDto.getTitle());
		map.put("type", queryDto.getType());
		map.put("status", queryDto.getStatus());
		Page resultPage = mallProductServier.searchAppIndex(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		
		

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("infoList", resultPage.getItems());	//集合是实体类型,不需要转换
		request.setAttribute("jsonStr", jsonObj.toString());
		
		List<Object[]> labelList = this.mallProductServier.searchLabeiList();
		List categoryList = this.mallProductServier.searchCateGory();
		
		
		JSONObject object = new JSONObject();
		List tempList = new ArrayList();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < labelList.size(); i++) {//集合是数据,需要吧转矩转换成json格式
			
			obj = new JSONObject();
			obj.put("id", labelList.get(i)[0].toString());
			obj.put("name", labelList.get(i)[1].toString());
			tempList.add(obj);
		}
		object.put("labelData", tempList);
		request.setAttribute("labelData", object.toString());
		object = new JSONObject();
		object.put("categoryData", categoryList);//集合是实体类型,不需要转换
		request.setAttribute("categoryData", object.toString());
		
		
		return "appIndex/appIndexManager";

	}
	
	
	


	@RequestMapping("saveAppIndex")
	public String saveAppIndex(HttpServletRequest request,
			HttpServletResponse response,AppIndex appIndex) {

		this.mallProductServier.saveAppIndex(appIndex);
		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		return "redirect:appIndexManager";

	}

	
	@RequestMapping("deleteAppIndex")
	public String deleteAppIndex(HttpServletRequest request,
			HttpServletResponse response) {

		this.mallProductServier.deleteAppIndex(request.getParameter("appIndexId"));
		this.redisService.del(RedisKeys.APP_CURRICULUM_INDEX);
		return "redirect:appIndexManager";

	}
	
	
	
	
	@RequestMapping("bookIndexManager")
	public String bookIndexManager(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("title", queryDto.getTitle());
		map.put("type", queryDto.getType());
		map.put("status", queryDto.getStatus());
		Page resultPage = mallProductServier.searchAppIndex(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		
		
		

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("infoList", resultPage.getItems());	//集合是实体类型,不需要转换
		request.setAttribute("jsonStr", jsonObj.toString());
		
		List<Object[]> labelList = this.bookService.searchBookLabel();
		
		List categoryList = bookService.getBookCategoryNolevel("book_category", 0, 0, -1, new ArrayList());
		
		JSONObject object = new JSONObject();
		List tempList = new ArrayList();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < labelList.size(); i++) {//集合是数据,需要吧转矩转换成json格式
			
			obj = new JSONObject();
			obj.put("id", labelList.get(i)[0].toString());
			obj.put("name", labelList.get(i)[1].toString());
			tempList.add(obj);
		}
		object.put("labelData", tempList);
		request.setAttribute("labelData", object.toString());
		object = new JSONObject();
		object.put("categoryData", categoryList);//集合是实体类型,不需要转换
		request.setAttribute("categoryData", object.toString());
		
		request.setAttribute("labelList", tempList);
		request.setAttribute("categoryList", categoryList);
		
		
		
		return "appIndex/bookIndexManager";

	}
}
