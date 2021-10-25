package com.wechat.controller;

import com.wechat.service.HotKeysService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class HotKeysApiController {

	@Resource
	private HotKeysService hotKeysService;
	

	/**
	 * 根据分类获取热词列表
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getHotKeysList")
	public void getHotKeysList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String keyType = request.getParameter("keyType");
	
			HashMap map = new HashMap();
			map.put("keyType", keyType);

			Page result = hotKeysService.getHotKeysList(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
}
