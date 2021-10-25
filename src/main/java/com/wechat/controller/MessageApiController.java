package com.wechat.controller;

import com.wechat.entity.Message;
import com.wechat.service.MessageService;
import com.wechat.util.JsonResult;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class MessageApiController {
	
	@Resource
	private MessageService messageService;

	/**
	 * 保存消息
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveMessage")
	public void saveMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String to = ParameterFilter.emptyFilter("", "to", request);
			String from = ParameterFilter.emptyFilter("", "from", request);
			String type = ParameterFilter.emptyFilter("", "type", request);
			String content = ParameterFilter.emptyFilter("", "message", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String currentTime = df.format(new Date());

			Message message = new Message();
			message.setContent(content);
			message.setFromUser(from);
			message.setCreateDate(currentTime);
			message.setToUser(to);
			message.setType(type);
			message.setUpdateDate(currentTime);
			if(!"".equals(id)&&null!=id){
				message.setId(Integer.parseInt(id));
			}
			messageService.saveMessage(message);
			
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/**
	 * 获取消息
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("getMessage")
	public void getMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String to = ParameterFilter.emptyFilter("", "to", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			HashMap map = new HashMap();
			map.put("to", to);
			map.put("id", id);
			JSONArray dataList = messageService.getMessageList(map);
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 修改消息
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateMessage")
	public void updateMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String to = ParameterFilter.emptyFilter("", "to", request);
			String from = ParameterFilter.emptyFilter("", "from", request);
			HashMap map = new HashMap();
			map.put("to", "");
			map.put("id", id);
			JSONArray dataList = messageService.getMessageList(map);
			if(dataList.size() > 0){
				JSONObject temp = dataList.getJSONObject(0);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				String currentTime = df.format(new Date());
				Message message = new Message();
				message.setContent(temp.get("message").toString());
				message.setCreateDate(temp.get("createDate").toString());
				message.setFromUser(from);
				message.setId(Integer.parseInt(id));
				message.setToUser(to);
				message.setType(temp.get("type").toString());
				message.setUpdateDate(currentTime);
				messageService.saveMessage(message);
			}
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/**
	 * 删除消息
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteMessage")
	public void deleteMessage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			messageService.deleteMessage(id);
			
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
}
