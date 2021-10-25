package com.wechat.controller;

import com.wechat.entity.GroupInfo;
import com.wechat.service.GroupInfoService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class GroupInfoController {

	@Resource
	private GroupInfoService groupInfoService;
	

	/**
	 * 上传好友关系到服务器
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveGroupInfo")
	public void saveGroupInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String groupId = request.getParameter("groupId");
			String userId = request.getParameter("userId");
			String noteName = ParameterFilter.emptyFilter("", "noteName", request);
			HashMap map = new HashMap();
			map.put("userId", userId);
			map.put("groupId", groupId);
			GroupInfo groupInfo = new GroupInfo();
			groupInfo = groupInfoService.getId(map); // 通过userID和friendid组合，查找到ID
			groupInfo.setGroupId(groupId);
			groupInfo.setNoteName(noteName);
			groupInfo.setUserId(userId);
			groupInfoService.saveGroupInfo(groupInfo);
			JsonResult.JsonResultInfo(response, groupInfo);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	@RequestMapping("getGroupInfo")
	public void getGroupInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String groupId = request.getParameter("groupId");
			HashMap map = new HashMap();
			map.put("groupId", groupId);
			Page result = groupInfoService.searchGroupInfo(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	@RequestMapping("deleteGroupInfo")
	public void deleteGroupInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String groupId = request.getParameter("groupId");
			String userId = request.getParameter("userId");
			map.put("groupId", groupId);
			map.put("userId", userId);
			GroupInfo groupInfo = groupInfoService.getId(map); // 通过userID和friendid组合，查找到ID
			int id  = groupInfo.getId();
			groupInfoService.deleteGroupInfo(id);  //删除
			JsonResult.JsonResultInfo(response, groupInfo);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	

}
