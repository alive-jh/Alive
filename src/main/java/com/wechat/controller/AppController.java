package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.AppFucShow;
import com.wechat.entity.AppTime;
import com.wechat.service.AppService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@RequestMapping("/app")
public class AppController {

	@Resource
	private AppService appService;

	
	Logger logger=LogManager.getLogger("log4j");

	@RequestMapping("/activityManager")
	public String activityManager(HttpServletRequest request, QueryDto queryDto) {

		HashMap map = new HashMap();
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}

		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());

		Page resultPage = this.appService.searchAppTime(map);
		request.getSession().setAttribute("resultPage", resultPage);
		// List jsonList = new ArrayList();
		// List<Object[]> infoList = resultPage.getItems();
		// List tempList = new ArrayList();
		// //封装成JSON显示对象
		JSONObject jsonObj = new JSONObject();
		// if (infoList!=null) {
		// JSONArray AgentKeyWordInfo = new JSONArray();
		//
		// for(Object[] bookSpecial : infoList) {
		// JSONObject jobj = new JSONObject();
		//
		// jobj.put("id", bookSpecial[0]);
		// jobj.put("name", bookSpecial[1]);
		// jobj.put("type", bookSpecial[2]);
		// jobj.put("count", bookSpecial[3]);
		// jobj.put("status", bookSpecial[4]);
		// jobj.put("createDate", bookSpecial[5].toString().substring(0,10));
		// jobj.put("endDate", bookSpecial[6].toString().substring(0,10));
		//
		// if("0".equals(bookSpecial[4].toString()))
		// {
		// jobj.put("statusName","正常");
		// }
		// else
		// {
		// jobj.put("statusName","关闭");
		// }
		// if(bookSpecial[7]!= null)
		// {
		// jobj.put("prizeList",bookSpecial[7]);
		// }
		//
		// jsonList.add(jobj);
		// }
		// jsonObj.put("infoList", jsonList);
		//
		// }
		jsonObj.put("infoList", resultPage.getItems());
		request.setAttribute("jsonStr", jsonObj.toString());
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		return "app/appManager";
	}
	
	
	@RequestMapping("/saveApp")
	public void saveApp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (!"".equals(request.getParameter("data"))
				&& request.getParameter("data") != null) {
			try {
				JSONArray tempList = new JSONArray();
				String dataStr = request.getParameter("data");
				JSONObject jsonObject = JSONObject.fromObject(dataStr);
				tempList = jsonObject.getJSONArray("appTimeList");
				
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm");
				for (int i = 0; i < tempList.size(); i++) {
					JSONObject temp=tempList.getJSONObject(i);
					AppTime appTime = new AppTime();
					appTime.setCreateDate(sd.parse(temp.get("createDate").toString()));
					appTime.setStartDate(sd.parse(temp.get("startDate").toString()));
					appTime.setEndDate(sd.parse(temp.get("endDate").toString()));
					appTime.setDeviceNo(temp.get("deviceNo").toString());
					appTime.setEpalId(temp.get("epalId").toString());
					appTime.setTime(temp.get("time").toString());
					this.appService.saveAppTime(appTime);
				}
				JsonResult.JsonResultInfo(response, tempList);
			} catch (Exception e) {
				JsonResult.JsonResultError(response, 1000);
			}
		}else{
			JsonResult.JsonResultError(response, 1001);
		}
	}

	@RequestMapping("getDeviceOnlineTimes")
	public String getDeviceOnlineTimes(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) {
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("deviceNo", queryDto.getSearchStr());
		Page resultPage = appService.searchDeviceOnLineTotalTime(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList epalOnlineTimes = (ArrayList) resultPage.getItems();
		request.setAttribute("jsonData",
				JsonResult.JsonResultToStr(epalOnlineTimes));
		// //System.out.println(JsonResult.JsonResultToStr(deviceTests));
		return "device/deviceOnlineManager";
	}
	
	
	
	
	/**
	 * app显示/隐藏功能开启关闭接口
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("appFucShow")
	public void appFucShow(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Integer id=Integer.parseInt(ParameterFilter.emptyFilter("-1", "id", request));
		String epalId=ParameterFilter.emptyFilter("", "epalId", request);
		String fucName=ParameterFilter.emptyFilter("", "fucName", request);
		Integer show=Integer.parseInt(ParameterFilter.emptyFilter("0", "show", request));
		try {
			AppFucShow appFucShow=new AppFucShow();
			if(id!=-1){
				appFucShow.setId(id);
			}
			appFucShow.setEpalId(epalId);
			appFucShow.setFucName(fucName);
			appFucShow.setShow(show);
			if(!"".equals(epalId)&&!"".equals(fucName)){
				appService.saveAppFucShow(appFucShow);
				JsonResult.JsonResultInfo(response, "OK");
			}else{
				JsonResult.JsonResultInfo(response, "Fail");
			}
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	/**
	 * app获取相关功能显示/隐藏列表
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("findAppFucShow")
	public void findAppFucShow(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String epalId=ParameterFilter.emptyFilter("", "epalId", request);
		String fucName=ParameterFilter.emptyFilter("", "fucName", request);
		Integer show=Integer.parseInt(ParameterFilter.emptyFilter("-1", "show", request));
		logger.error("show:"+show);
		try {
			AppFucShow appFucShow=new AppFucShow();
			appFucShow.setEpalId(epalId);
			appFucShow.setFucName(fucName);
			appFucShow.setShow(show);
			if(!"".equals(epalId)){
				List<AppFucShow> appFucShows=appService.findAppFucShow(appFucShow);
				JsonResult.JsonResultInfo(response, appFucShows);
			}else{
				JsonResult.JsonResultInfo(response, "Fail");
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * 微信测试推送
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("sendMp3")
	public void sendMp3(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			EasemobUtil easemobUtil = new EasemobUtil();
			

			String[] users = new String []{"o82t2o2oon3","o8212w2oow3","8881tto735","epal170303"};

			String mp3Name = request.getParameter("mp3Name");
			String url = request.getParameter("url");
			String mp3Id = request.getParameter("mp3Id");
			
			if(!"".equals(mp3Id) && mp3Id != null)
			{
				url = "LESSON_ONLINE";
			}
			if("".equals(mp3Name) || mp3Name == null)
			{
				mp3Name = "网络音频";
			}
			String action = "epal_play:{\"id\":\""+mp3Id+"\",\"name\":\""+mp3Name+"\",\"url\":\""+url+"\",\"action\":1,\"times\":1,\"start\":1}";

			easemobUtil.sendMessage(users,action);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	
	
	/**
	 * 机器人返回状态0成功,1失败
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("getResult")
	public void getResult(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String epalId=ParameterFilter.emptyFilter("", "epalId", request);
		String fucName=ParameterFilter.emptyFilter("", "fucName", request);
		Integer show=Integer.parseInt(ParameterFilter.emptyFilter("-1", "show", request));
		logger.error("show:"+show);
		try {
			
			if("0".equals(request.getParameter("status"))){
				JsonResult.JsonResultInfo(response, "0");
			}else{
				JsonResult.JsonResultInfo(response, "1");
			}
		} catch (Exception e) {
			JsonResult.JsonResultInfo(response, "1");
		}
		
	}
	
	public static void main(String[] args) {
		
		//System.out.println();
	}
	
	
	

}
