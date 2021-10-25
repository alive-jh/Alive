package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.EpalSystem;
import com.wechat.service.EpalSystemService;
import com.wechat.util.JsonResult;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("app")
public class EpalSystemController {

	
	@Resource
	private EpalSystemService epalStstemService;

	public EpalSystemService getEpalStstemService() {
		return epalStstemService;
	}

	public void setEpalStstemService(EpalSystemService epalStstemService) {
		this.epalStstemService = epalStstemService;
	}
	
	@RequestMapping("getEpalSystem")
	public void getEpalSystem(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		JSONObject jsonObj = new JSONObject();
		try {
				
				String epalId = request.getParameter("epalId");
				
				if(!"".equals(epalId) && epalId != null)
				{
					EpalSystem epalSystem= this.epalStstemService.getEpalSystem(epalId);
					
					JsonResult.JsonResultInfo(response,epalSystem);
					
				}
				else
				{
					jsonObj.put("success", 0);
					jsonObj.put("error", "参数缺失:epalId");
					JsonResult.JsonResultInfo(response,jsonObj);
				}
	
			} catch (Exception e) 
			{
				e.printStackTrace();
				JsonResult.JsonResultError(response, 1000);
			}
		
	}	

	@RequestMapping("saveEpalSystem")
	public void saveEpalSystem(HttpServletRequest request,HttpServletResponse response,QueryDto queryDto){
		
		JSONObject jsonObj = new JSONObject();
		try {
				
				EpalSystem epalSystem = new EpalSystem();
				String epalId = request.getParameter("epalId");
				
				if(!"".equals(epalId) && epalId != null)
				{
					epalSystem = this.epalStstemService.getEpalSystem(epalId);
					
					if(epalSystem.getId()!= null)
					{
						
						epalSystem.setDistinguish(new Integer(request.getParameter("distinguish")));
						epalSystem.setRecommend( new Integer(request.getParameter("recommendnew")));
						epalSystem.setSchedule( new Integer(request.getParameter("schedule")));
						epalSystem.setTesting( new Integer(request.getParameter("testing")));
						epalSystem.setChat(new Integer(ParameterFilter.emptyFilter("1", "chat", request)));
					}
					else
					{
						epalSystem.setEpalId(epalId);
					}
					
					this.epalStstemService.saveEpalSystem(epalSystem);
					
					JsonResult.JsonResultInfo(response,"ok");
					
				}
				else
				{
					JsonResult.JsonResultError(response, 1000);
					
				}
	
			} catch (Exception e) 
			{
				JsonResult.JsonResultError(response, 1000);
			}
		
	}	
	
	
	
}
