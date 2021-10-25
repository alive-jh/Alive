package com.wechat.controller;

import com.wechat.service.PublicRoomService;
import com.wechat.util.JsonResult;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class PublicClassApiController {

	@Resource
	private PublicRoomService publicRoomService;
	

	/**
	 * 通过Fid卡号，获取学生ID
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getStudentIdByFid")
	public void getStudentIdByFid(HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		String fid = request.getParameter("fid");
		HashMap map = new HashMap();
		map.put("fid", fid);
		JSONObject result = publicRoomService.getStudentIdByFid(map);
		JsonResult.JsonResultInfo(response, result);

	}
}
