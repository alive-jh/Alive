package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.service.RedisService;
import com.wechat.service.SoundService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("sound")
public class SoundController {

	@Resource
	private SoundService soundService;
	
	@Resource
	private RedisService redisService;
	
	/*
	 * 音频推荐管理
	 * */
	


	@RequestMapping("DailyRecommendManage")
	public String channelManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page channels = soundService.searchDailyRecommentDateList(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "channel/dailyRecommendManager";
	}
}
