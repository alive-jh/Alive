package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Label;
import com.wechat.entity.XMLYAlbum;
import com.wechat.service.ChannelService;
import com.wechat.service.QuestionService;
import com.wechat.service.RedisService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import com.wechat.util.RedisKeys;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("channel")
public class ChannelController {

	@Resource
	private ChannelService channelService;
	
	@Resource
	private QuestionService questionService;
	
	@Resource
	private RedisService redisService;
	

	
	/*
	 * 频道管理
	 * */
	


	@RequestMapping("channelManage")
	public String channelManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page channels = channelService.searchChannels(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "channel/channelManager";
	}
	
	/*
	 * 频道管理
	 * */
	


	@RequestMapping("albumManage")
	public String albumManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page albums = channelService.searchAlbums(map); 
		List album = albums.getItems();
		
		ArrayList queArr = new ArrayList();
		for (int i = 0; i < album.size(); i++) {
			XMLYAlbum xmlyAlbum=(XMLYAlbum) album.get(i);
			HashMap channelMap = new HashMap();
			channelMap.put("id", xmlyAlbum.getId());
			channelMap.put("name", xmlyAlbum.getName());
			channelMap.put("intro", xmlyAlbum.getIntro());
			channelMap.put("channel_id", xmlyAlbum.getChannelId());
			channelMap.put("image", xmlyAlbum.getImage());
			channelMap.put("album_id", xmlyAlbum.getAlbumId());
			channelMap.put("status", xmlyAlbum.getStatus());
			channelMap.put("sort_id", xmlyAlbum.getSortId());
			queArr.add(channelMap);
		}
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(queArr));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", albums);
		List labelList = getLabelList();
		request.setAttribute("labelList", labelList);
		return "channel/albumManager";
	}
	
	
	
	public List getLabelList()
	{
		List labelList = new ArrayList();
		if(this.redisService.exists(RedisKeys.REDIS_SOUND_LABEL)){
			labelList =  this.redisService.getList(RedisKeys.REDIS_SOUND_LABEL, Label.class);
		}else{
			HashMap map = new HashMap();
			map = new HashMap();
			map.put("page", "1");
			map.put("rowsPerPage", "1000");
			map.put("type", "1");
			Page tempResult = this.channelService.searchLabel(map);
			labelList = tempResult.getItems();
			
			//System.out.println(labelList);
			this.redisService.setList(RedisKeys.REDIS_SOUND_LABEL, labelList, RedisKeys.ADMIN_TIME);
		}
		return labelList;
	}
	/*
	 * 频道管理
	 * */
	


	@RequestMapping("soundManage")
	public String soundManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("sortStr", ParameterFilter.emptyFilter("", "sortStr", request));
		map.put("sortValue", ParameterFilter.emptyFilter("", "sortValue", request));
		Page sounds = channelService.searchSounds(map); 
		List sound = sounds.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(sound));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", sounds);
		request.setAttribute("sortList", sounds);
		return "channel/soundManager";
	}
	
	/*
	 * 频道管理
	 * */
	@RequestMapping("recommendManage")
	public String recommendManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("tag", ParameterFilter.emptyFilter("", "tag", request));
		Page channels = channelService.searchSoundRecommend(map); 
		List channel = channels.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(channel));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", channels);
		return "channel/recommendManager";
	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * */
	@RequestMapping("soundSearchManage")
	public String soundSearchManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));		
		Page soundSearchs = channelService.searchSoundSearch(map); 
		List soundSearch = soundSearchs.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(soundSearch));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", soundSearchs);
		return "channel/soundSearchManager";
	}
	/*
	 * 
	 * 
	 * 音频播放统计
	 * 
	 * */
	@RequestMapping("soundPlayManage")
	public String soundPlayManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		map.put("startDate", ParameterFilter.emptyFilter("", "startDate", request));
		map.put("endDate", ParameterFilter.emptyFilter("", "endDate", request));		
		Page soundSearchs = channelService.soundPlayManage(map); 
		List soundSearch = soundSearchs.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(soundSearch));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", soundSearchs);
		return "channel/soundPlayManager";
	}
	
	/*
	 * 
	 * 
	 * 音频标签分组管理
	 * 
	 * */
	@RequestMapping("soundTagGroupManage")
	public String soundTagGroupManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));		
		Page soundTagGroupS = channelService.getSoundTagGroupList(map); 
		List soundTagGroup = soundTagGroupS.getItems();
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(soundTagGroup));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", soundTagGroupS);
		return "channel/soundTagGroupManager";
	}
}
