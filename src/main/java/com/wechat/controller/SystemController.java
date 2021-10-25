package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.service.DeviceService;
import com.wechat.service.SoundService;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("server")
public class SystemController {
	
	
	@Resource
	private DeviceService deviceService;
	
	@Resource
	private SoundService soundService;
	
	/*
	 * 升级配置文件
	 * */
	


	@RequestMapping("UpgradeConfigManage")
	public String UpgradeConfigManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
//		Page channels = soundService.getUpgradeConfigList(map); 
//		List channel = channels.getItems();
//		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
//		request.setAttribute("queryDto", queryDto);
//		request.setAttribute("resultPage", channels);
		return "server/UpgradeConfigManager";
	}
}


 
