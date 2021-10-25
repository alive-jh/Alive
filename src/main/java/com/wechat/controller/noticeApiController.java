package com.wechat.controller;

import com.wechat.util.JsonResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("api")
public class noticeApiController {

	/**
	 * 
	 * 获取公告
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getNotice")
	public void getNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
//			JSONObject parameter = new JSONObject();
//			parameter.put("userId", userId);
//			MongoHandle mongo1 = new MongoHandle("fandou","noticeRecord");
//			JSONArray dataList1 = mongo1.getDocument(parameter);
//			
//			JSONArray record = new JSONArray();
//			for(int i=0;i<dataList1.size();i++){
//				if(record.contains(dataList1.getJSONObject(i).getString("noticeId"))){
//					
//				}else{
//					record.add(dataList1.getJSONObject(i).getString("noticeId"));
//				}
//				
//			}
//			
//			JSONObject parameter2 = new JSONObject();
//			MongoHandle mongo2 = new MongoHandle("fandou","notice");
//			JSONArray dataList2 = mongo2.getDocument(parameter2);
//			
			JSONArray result = new JSONArray();
//			for(int i=0;i<dataList2.size();i++){
//				if(record.contains(dataList2.getJSONObject(i).getString("id"))){
//					dataList2.remove(i);
//				}else{
//					result.add(dataList2.getJSONObject(i));
//				}
//			}
			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/**
	 * 
	 * 上传阅读记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadNoticeRecord")
	public void uploadNoticeRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
			String noticeId = request.getParameter("noticeId");
			JSONObject parameter = new JSONObject();
			parameter.put("userId", userId);
			parameter.put("noticeId", noticeId);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parameter.put("readTime", df.format(new Date()));
//			MongoHandle mongo = new MongoHandle("fandou","noticeRecord");
//			mongo.saveDocument(parameter);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
}
