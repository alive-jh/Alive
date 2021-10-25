package com.wechat.controller;

import com.wechat.entity.Device;
import com.wechat.service.DeviceService;
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

@Controller
@RequestMapping("server")
public class SystemApiController {
	
	
	@Resource
	private DeviceService deviceService;
	
	
	
	@Resource
	private RedisService redisServer;
	
	/*
	 * 获取服务器时间
	 * */
	
	@RequestMapping("getTime")
	public void soundCollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("code", 200);
		obj.put("msg", "ok");
		obj.put("currentTime", System.currentTimeMillis());
		//System.out.println(System.currentTimeMillis());
		try {
		    Thread.sleep(5000);                 //1000 毫秒，也就是1秒.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		JsonResult.JsonResultInfo(response, obj);
	}
	

	
	/*
	 *获取静态文件访问域名列表
	 * */
	@RequestMapping("getStaticHostList")
	public void getStaticHostList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray hostList = new JSONArray();
		
		JSONObject hostObj1 = new JSONObject();
		hostObj1.put("host", "wechat.fandoutech.com.cn");
		hostObj1.put("sort",1);
		hostList.add(hostObj1);
		
		
		JSONObject hostObj2 = new JSONObject();
		hostObj2.put("host", "aliyun.fandoutech.com.cn");
		hostObj2.put("sort",2);
		hostList.add(hostObj2);
		
		JsonResult.JsonResultInfo(response, hostList);
	}
	

	/*
	 *获取app升级配置文件
	 * */
	@RequestMapping("getUpgradeConfig")
	public void getUpgradeConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String clientType = ParameterFilter.emptyFilter("", "clientType", request);
		String environment = ParameterFilter.emptyFilter("", "environment", request);
		String userId = ParameterFilter.emptyFilter("", "userId", request);
		JSONObject result = new JSONObject();
		if("".equals(clientType)||"".equals(environment)){
			result.put("code", 406);
			result.put("msg", "parater error!");
			
		}else{
			HashMap map = new HashMap();
			map.put("clientType", clientType);
			map.put("environment", environment);
			
			JSONObject config = new JSONObject();
			
			config.put("environment", "test");
			config.put("clientType","IOS");
			
			config.put("url", "http://www.fandoutech.com/FandouPalDownload");
			config.put("content","哈哈");

			
			config.put("version", "3.3.8");
			config.put("insertDate","2017-06-14 10:35:35");
			

			result.put("config", config);
		}

		JsonResult.JsonResultInfo(response, result);
	}
	
	/*
	 * 通过机器人设备序列号获取机器人换机后账号
	 * */
	@RequestMapping("brushDevice")
	public void brushDevice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String deviceNo = ParameterFilter.emptyFilter("", "deviceNo", request);
		String epalId = ParameterFilter.emptyFilter("", "epalId", request);
		Device device = deviceService.searchDeviceByDeviceNo(deviceNo);
		
		String newEpalId = deviceService.getNewEpalId(epalId);
		if (!"".equals(newEpalId)&&null!=newEpalId){
			device.setEpalId(newEpalId);
		}
		JsonResult.JsonResultInfo(response, device);
	}
	
	
	/*
	 * 通过机器人设备序列号获取机器人换机后账号
	 * */
	@RequestMapping("lookup")
	public void lookup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String id = ParameterFilter.emptyFilter("", "id", request);
		String environment = ParameterFilter.emptyFilter("normal", "environment", request);
		String key = "";
		if("test".equals(environment)){
			key = "lookup_id_" + id + "_" + environment ;
		}else{
			key = "lookup_id_" + id ;
		}
		String data = redisServer.get(key);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print(data);
	}
	
}


 
