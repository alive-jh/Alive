package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Activity;
import com.wechat.entity.ActivityPrize;
import com.wechat.service.ActivityService;
import com.wechat.service.StatisticalService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Component
@RequestMapping("/activity")
public class ActivityController {
	
	@Resource
	private StatisticalService statisticalService;
	
	@Resource
	private ActivityService activityService;
	
	
	public ActivityService getActivityService() {
		return activityService;
	}


	public void setActivityService(ActivityService activityService) {
		this.activityService = activityService;
	}

	@RequestMapping("/activityManager")
	public String activityManager(HttpServletRequest request,QueryDto queryDto) {
		
		HashMap map = new HashMap();
		if(!"".equals(request.getParameter("page"))  && request.getParameter("page")!= null)
		{
			queryDto.setPage(request.getParameter("page"));
		}
		if(!"".equals(request.getParameter("pageSize"))  && request.getParameter("pageSize")!= null)
		{
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		
		map.put("name", queryDto.getName());
		map.put("status", queryDto.getStatus());
		

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		
		
		Page resultPage = this.activityService.searchActivity(map);
		request.getSession().setAttribute("resultPage", resultPage);
		List jsonList = new ArrayList();
		List<Object[]> infoList = resultPage.getItems();
		List tempList = new ArrayList();
		//封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		if (infoList!=null) {
			JSONArray AgentKeyWordInfo = new JSONArray();

			for(Object[] bookSpecial : infoList) {
				JSONObject jobj = new JSONObject();
				
				jobj.put("id", bookSpecial[0]);
				jobj.put("name", bookSpecial[1]);
				jobj.put("type", bookSpecial[2]);
				jobj.put("count", bookSpecial[3]);
				jobj.put("status", bookSpecial[4]);
				jobj.put("createDate", bookSpecial[5].toString().substring(0,10));
				jobj.put("endDate", bookSpecial[6].toString().substring(0,10));
				
				if("0".equals(bookSpecial[4].toString()))
				{
					jobj.put("statusName","正常");
				}
				else
				{
					jobj.put("statusName","关闭");
				}
				if(bookSpecial[7]!= null)
				{
					jobj.put("prizeList",bookSpecial[7]);
//					String[] tempPrize = bookSpecial[7].toString().split(",");
//					ActivityPrize activityPrize = new ActivityPrize();
//					String[] prize = null;
//					for (int j = 0; j < tempPrize.length; j++) {
//						
//						tempPrize = tempPrize[j].split("<");
//						if(prize!= null)
//						{
//							activityPrize = new ActivityPrize();
//							activityPrize.setActivityId(new Integer(bookSpecial[0].toString()));
//							activityPrize.setCount(new Integer(prize[3]));
//							activityPrize.setId(new Integer(prize[0]));
//							activityPrize.setName(prize[1]);
//							activityPrize.setPercentage(new Integer(prize[2]));
//							
//							tempList.add(activityPrize);
//							
//							
//						}
//						
//						
//					}
				
					
//					jobj.put("prizeList",tempList);
					
				}
				
				jsonList.add(jobj);
			}
			jsonObj.put("infoList", jsonList);
			
		}
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);	
		
		
		
		
		return "activity/activityManager";
	}
	
	
	
	


	
	@RequestMapping("/saveActivity")
	public String saveActivity(HttpServletRequest request,Activity activity) throws Exception{
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		activity.setCreateDate(sdf.parse(request.getParameter("date1")));
		activity.setEndDate(sdf.parse(request.getParameter("date2")));
		
		this.activityService.saveActivity(activity);
		
		return "redirect:activityManager";
	}
	
	
	@RequestMapping("/saveActivityPrize")
	public String saveActivityPrize(HttpServletRequest request,ActivityPrize activityPrize) throws Exception{
		
		
		activityPrize.setName(request.getParameter("jiangpin"));
		
		this.activityService.saveActivityPrize(activityPrize);
		
		return "redirect:activityManager";
	}
	
	
	
	
	@RequestMapping("/removeSpecial")
	public String removeActivity( HttpServletRequest request) 
	{
		
		this.activityService.deleteActivity(request.getParameter("id"));
		
		return "redirect:activityManager";
	}
	
	
	@RequestMapping("/luckDraw")
	public String luckDraw(HttpServletRequest request,Activity activity) throws Exception{
		
		return "activity/luckDraw";
	}
}
