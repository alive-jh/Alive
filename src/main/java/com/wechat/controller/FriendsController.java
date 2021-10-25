package com.wechat.controller;

import com.wechat.entity.Friends;
import com.wechat.service.FriendsService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class FriendsController {

	@Resource
	private FriendsService friendsService;
	

	/**
	 * 上传好友关系到服务器
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveFriends")
	public void saveFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String friendId = request.getParameter("friendId");
			String userId = request.getParameter("userId");
			String noteName = ParameterFilter.emptyFilter(null, "noteName", request);
			HashMap map = new HashMap();
			map.put("userId", userId);
			map.put("friendId", friendId);
			Friends friends = new Friends();
			friends = friendsService.getId(map); // 通过userID和friendid组合，查找到ID
			friends.setFriendId(friendId);
			friends.setNoteName(noteName);
			friends.setUserId(userId);
			friends.setFriendType("1");
			friends.setGroupId("1");
			friendsService.saveFriends(friends);
			JsonResult.JsonResultInfo(response, friends);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 
	 * 	APP批量保存好友列表
	 * 
	 * */
	@RequestMapping("batchSaveFriends")
	public void batchSaveFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String data = request.getParameter("data");
			JSONObject info = new JSONObject(data);
			String userId = info.getString("userId");
			JSONArray friendIds = info.getJSONArray("friendIds");
			friendsService.batchSaveFriends(userId,friendIds);
//			for(int i=0;i<friendIds.length();i++){
//				String friendId = friendIds.get(i).toString();
//				HashMap map = new HashMap();
//				map.put("userId", userId);
//				map.put("friendId", friendId);
//				Friends friends = new Friends();
//				friends = friendsService.getId(map);
//				friends.setFriendId(friendId);
//				friends.setUserId(userId);
//				friends.setFriendType("1");
//				friends.setGroupId("1");
//				friendsService.saveFriends(friends);
//			}
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/*
	 * 
	 * 
	 * 获取好友列表，app
	 * 
	 * */
	@RequestMapping("getFriends")
	public void getFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = ParameterFilter.emptyFilter("", "userId", request);
			if("".equals(userId)||null==userId){ //如果不带用户ID，返回505错误
				JSONObject result = new JSONObject();
				result.put("code", 505);
				result.put("msg", "no paramter");
				JsonResult.JsonResultInfo(response, result);
				
			}else{
				HashMap map = new HashMap();
				map.put("userId", userId);
				Page result = friendsService.searchFriends(map);
				JsonResult.JsonResultInfo(response, result);
			}
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/*
	 * 
	 * 	通过上传好友ID和用户ID删除好友关系
	 * 
	 * 
	 * */
	@RequestMapping("deleteFriends")
	public void deleteFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String friendId = request.getParameter("friendId");
			String userId = request.getParameter("userId");
			map.put("friendId", friendId);
			map.put("userId", userId);
			Friends friends = friendsService.getId(map); // 通过userID和friendid组合，查找到ID
			int id  = friends.getId();
			friendsService.deleteFriends(id);  //删除
			JsonResult.JsonResultInfo(response, friends);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1002);
		}
	}
}
