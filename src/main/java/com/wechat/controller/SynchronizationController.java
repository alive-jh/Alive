package com.wechat.controller;

import com.wechat.service.DeviceRelationService;
import com.wechat.service.DeviceService;
import com.wechat.service.DeviceShareService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.HashMap;

/*
 * 
 * 
 * 不同机器人之间的数据同步
 * 
 * */
@Controller
@RequestMapping("api")
public class SynchronizationController {

	@Resource
	private DeviceService deviceService;
	

	@Resource
	private DeviceShareService deviceShareService;

	
	@Resource
	private DeviceRelationService deviceRelationService;
	
	/**
	 * 同步数据
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("synchronization")
	public void synchronization(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String from = request.getParameter("from"); 
			String to = request.getParameter("to");
			String userId = request.getParameter("userId");
			String time = request.getParameter("time");
			String token = request.getParameter("token");
			
			HashMap map = new HashMap();
			map.put("time", time);
			map.put("userId", userId);
			map.put("epalId", from);
			map.put("token", token);
			JSONObject result = new JSONObject();
			if(checkTime(map) == true){
				if (checkBindRelation(map) == true){
					Page resultPage;
					if(true == checkToken(map)){
						resultPage = deviceShareService.searchDeviceShares(map);  //同步小网盘文件
						
						
						resultPage = deviceService.searchDeviceRelations(map); //同步好友信息
						result.put("code", 200);
						result.put("msg", "Already synchronized");
						JsonResult.JsonResultInfo(response, result.toString());
						
					}else{
						//System.out.println("token error!");
						result.put("code", 403);
						result.put("msg", "token error!");
						JsonResult.JsonResultToStr(result.toString());
					}
				}else{
					//System.out.println("no binding");
					result.put("code", 403);
					result.put("msg", "no binding");
					JsonResult.JsonResultInfo(response,result.toString());
				}
			}else{
				//System.out.println("Time out!");
				result.put("code", 408);
				result.put("msg", "Time out!");
				JsonResult.JsonResultInfo(response,result.toString());
			}

				
		} catch (Exception e) {
			
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	//生成MD5加密字符
	
	private String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/*
	 * 
	 * 	确定绑定关系
	 * 
	 * 
	 * */
	
	private boolean checkBindRelation(HashMap paramter){  
		HashMap map = new HashMap();
		map.put("friend_id", paramter.get("userId").toString());
		deviceService.searchDevicebBindList(map);
		return false;
		
	}
	
	
	/*
	 * 
	 * 	验证时间，从获取服务器时间到请求copy时间间隔不超过五分钟
	 * 
	 * 
	 * */
	
	private boolean checkTime(HashMap map){  // 验证时间

		String time = map.get("time").toString();
		long currentTime=System.currentTimeMillis(); 
		long uploadTime = Long.parseLong(time);
		if (currentTime -uploadTime > 30000 ){
			return false;
		}else{
			return true;
		}
		
	}
	/*
	 * 	计算并且判断token是否有效
	 * 
	 * 
	 * */
	
	private boolean checkToken(HashMap map){  
		String token = map.get("token").toString();
		String time = map.get("time").toString();
		String userId = map.get("userId").toString();
		String tokenStr = userId + "@fandou:" + time;
		
		String serverToken = MD5(tokenStr);
		if (serverToken.equals(token)){
			return true;
		}else{
			//System.out.println("token error!"+"uploadToken:" + token + "  serviceToken:" + serverToken);
			return false;
		}
		
	}
	
	/**
	 * 获取服务器时间
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getTime")
	public void getTime(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject result = new JSONObject();
			long currentTime=System.currentTimeMillis(); 
			result.put("code", 200);
			result.put("msg", "ok");
			result.put("time", currentTime);
			
			JsonResult.JsonResultInfo(response,result.toString());
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

}
