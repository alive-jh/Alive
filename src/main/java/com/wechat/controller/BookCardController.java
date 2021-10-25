package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.service.BookCardService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("card")
public class BookCardController {

	
	private BookCardService bookCardService;

	public BookCardService getBookCardService() {
		return bookCardService;
	}

	public void setBookCardService(BookCardService bookCardService) {
		this.bookCardService = bookCardService;
	}


	
	@RequestMapping("/cardManager")
	public String specialManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());
		map.put("topicType", queryDto.getTopicType());
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());

		Page resultPage = this.bookCardService.searchBookCard(map);
		request.setAttribute("resultPage", resultPage);
		List jsonList = new ArrayList();
		List<Object[]> infoList = resultPage.getItems();

		// 封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList != null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for (Object[] bookSpecial : infoList) {
				JSONObject jobj = new JSONObject();
				jobj.put("id", bookSpecial[0]);
				jobj.put("title", bookSpecial[2]);
				jobj.put("logo", bookSpecial[1]);
				jobj.put("sort", bookSpecial[4]);
				jobj.put("status", bookSpecial[3]);
				jobj.put("topicType", bookSpecial[6]);
				jobj.put("books", bookSpecial[8]);

				if ("0".equals(bookSpecial[3].toString())) {
					jobj.put("statusName", "显示");
				} else {
					jobj.put("statusName", "隐藏");
				}

				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);

		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		return "special/specialManager";
	}

	
	

	
	public static void main(String[] args) {
		
		String state = "1@2@3@";
		//System.out.println(state.split("@").length);
		if(state.split("@").length==4)
    	{
    		//System.out.println("WWWWWWWWWWWWWW");
    	}
		else
		{
			//System.out.println("aaaaaaaaaaaaaa");
		}
		
	}
	
	

}
