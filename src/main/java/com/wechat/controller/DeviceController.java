package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.service.DeviceService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("device")
public class DeviceController {
	
	@Resource
	private
	DeviceService deviceService;
	
	
	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	
	
	@RequestMapping("getDeviceTestsInfo")
	public String getDeviceTestsInfo(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("deviceNo", queryDto.getSearchStr());
		map.put("status", request.getParameter("status"));
		Page resultPage = deviceService.searchDeviceTests(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList deviceTests=(ArrayList) resultPage.getItems();
		request.setAttribute("jsonData", JsonResult.JsonResultToStr(deviceTests));
		////System.out.println(JsonResult.JsonResultToStr(deviceTests));
		return "device/deviceTestManager";
	}
	
	@RequestMapping("getDeviceList")
	public String getDeviceList(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("epalId", queryDto.getSearchStr());
		map.put("status", request.getParameter("status"));
		Page resultPage = deviceService.searchDeviceList(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList deviceList=(ArrayList) resultPage.getItems();
		request.setAttribute("jsonData", JsonResult.JsonResultToStr(deviceList));
		return "device/deviceListManager";
	}
	
	@RequestMapping("replacementManager")
	public String replacementManager(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		HashMap map = new HashMap();

		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page replacements = deviceService.getReplacement(map);
		List replacement = replacements.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(replacement));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", replacements);
		return "device/replacementManager";
	}
	@RequestMapping("categoryManager")
	public String categoryManager(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		HashMap map = new HashMap();

		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page replacements = deviceService.getCategoryList(map);
		List replacement = replacements.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(replacement));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", replacements);
		return "device/categoryManager";
	}
}
