package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.service.DeviceHeartBeatService;
import com.wechat.service.RedisService;
import com.wechat.util.JsonResult;
import com.wechat.util.ParameterFilter;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("api")
public class DeviceHeartBeatController {
	
	@Resource
	private RedisService redisService;
	
	@Resource
	private DeviceHeartBeatService deviceHeartBeatService;
	
	@RequestMapping("SaveDeviceHeartBeatStatus")
	public void SaveDeviceHeartBeatStatus(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String currentStatus = ParameterFilter.emptyFilter("", "currentStatus", request);
			String key = "DeviceHeartBeat_" + epalId;
			this.redisService.set(key, "1", 130);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	@RequestMapping("GetDeviceHeartBeatStatus")
	public void GetDeviceHeartBeatStatus(HttpServletRequest request, HttpServletResponse response,QueryDto queryDto){
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String key = "DeviceHeartBeat_" + epalId;
			JSONObject obj = new JSONObject();
			if(this.redisService.get(key) == null || "".equals(this.redisService.get(key))){
				obj.put("msg", "离线");
				obj.put("status", 0);
				
			}else{
				obj.put("msg", "在线");
				obj.put("status", 1);
			}
			JsonResult.JsonResultInfo(response, obj.toString());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
}
