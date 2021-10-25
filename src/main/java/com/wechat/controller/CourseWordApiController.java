package com.wechat.controller;

import com.wechat.service.CourseWordService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class CourseWordApiController {

	@Resource
	private CourseWordService courseWordService;
	
	/**
	 * Epal添加上传音视频文件接口
	 * 
	 * @param upFile
	 * @param request
	 * @param response
	 */

	@RequestMapping("getWordGroupList")
	public void getWordGroupList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String type = ParameterFilter.emptyFilter("", "type", request);
			HashMap map = new HashMap();
			map.put("type", type);
			Page info = courseWordService.getWordGroupList(map);
			JsonResult.JsonResultInfo(response, info);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	@RequestMapping("getWordSourceList")
	public void getWordSourceList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String groupId = request.getParameter("groupId");
			HashMap map = new HashMap();
			map.put("groupId", groupId);
			JSONArray info = courseWordService.getWordSourceList(map);
			JsonResult.JsonResultInfo(response, info);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	
	@RequestMapping("searchWords")
	public void searchWords(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String key = request.getParameter("key");
			HashMap map = new HashMap();
			map.put("key", key);
			JSONArray info = courseWordService.searchWords(map);
			JsonResult.JsonResultInfo(response, info);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
}
