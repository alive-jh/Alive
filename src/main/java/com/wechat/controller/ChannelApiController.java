package com.wechat.controller;

import com.wechat.entity.*;
import com.wechat.service.ChannelService;
import com.wechat.service.RedisService;
import com.wechat.spider.HttpRequestor;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/*
 * 大V频道Api接口列表
 * 
 * 
 * */
@Controller
@RequestMapping("api")
public class ChannelApiController {

	@Resource
	private ChannelService channelService;
	
	@Resource
	private RedisService redisService;
	
	
	/*
	 * 添加频道
	 * 
	 * */
	
	@RequestMapping("/channel/saveChannel")
	public void addChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String name = ParameterFilter.emptyFilter("","name",request);
			String image = ParameterFilter.emptyFilter("","image",request);
			String intro = ParameterFilter.emptyFilter("暂无简介","intro",request);
			String fans = ParameterFilter.emptyFilter("1","fans",request);
			String status = ParameterFilter.emptyFilter("2","status",request);
			String level = ParameterFilter.emptyFilter("10","level",request);
			String id = ParameterFilter.emptyFilter("","id",request);
			String nextUpdateTime = ParameterFilter.emptyFilter("","nextUpdateTime",request);
			String lastUpdateTime = ParameterFilter.emptyFilter("","lastUpdateTime",request);
			String updateCycl = ParameterFilter.emptyFilter("1","updateCycl",request);			
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = df.format(new Date());
			
			XMLYChannel xmlyChannel = new XMLYChannel();
			if(!"".equals(id)&&null!=id){
				xmlyChannel.setId(Integer.parseInt(id));
				xmlyChannel.setNextUpdateTime(currentTime);
				xmlyChannel.setLastUpdateTime(currentTime);
			}else{
				JSONObject data = new JSONObject();
				data.put("channelId", channelId);
				data.put("type", "channel");
				redisService.lpush("channelSpiderTask", data.toString());
			}
			xmlyChannel.setNextUpdateTime(nextUpdateTime);
			xmlyChannel.setLastUpdateTime(lastUpdateTime);
			xmlyChannel.setUpdateCycle(12);
			xmlyChannel.setChannelId(Integer.parseInt(channelId));
			xmlyChannel.setName(name);
			xmlyChannel.setImage(image);
			xmlyChannel.setIntro(intro);
			xmlyChannel.setFans(Integer.parseInt(fans));
			xmlyChannel.setStatus(Integer.parseInt(status));
			xmlyChannel.setLevel(Integer.parseInt(level));
			xmlyChannel.setNextUpdateTime(nextUpdateTime);
			xmlyChannel.setLastUpdateTime(lastUpdateTime);
			xmlyChannel.setUpdateCycle(Integer.parseInt(updateCycl));
			channelService.saveChannel(xmlyChannel);
			JsonResult.JsonResultInfo(response, xmlyChannel);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 添加专辑
	 * 
	 * */
	
	@RequestMapping("/channel/saveAlbum")
	public void saveAlbum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String name = ParameterFilter.emptyFilter("","name",request);
			String image = ParameterFilter.emptyFilter("","image",request);
			String intro = ParameterFilter.emptyFilter("暂无简介","intro",request);
			String albumId = ParameterFilter.emptyFilter("1","albumId",request);
			String status = ParameterFilter.emptyFilter("1","status",request);
			String sortId = ParameterFilter.emptyFilter("1","sortId",request);
			String id = ParameterFilter.emptyFilter("","id",request);
			XMLYAlbum xmlyAlbum = new XMLYAlbum();
			if(!"".equals(id)&&null!=id){
				xmlyAlbum.setId(Integer.parseInt(id));
			}
			xmlyAlbum.setChannelId(Integer.parseInt(channelId));
			xmlyAlbum.setName(name);
			xmlyAlbum.setImage(image);
			xmlyAlbum.setIntro(intro);
			xmlyAlbum.setAlbumId(Integer.parseInt(albumId));
			xmlyAlbum.setStatus(Integer.parseInt(status));
			xmlyAlbum.setSortId(Integer.parseInt(sortId));
			channelService.saveAlbum(xmlyAlbum);
			JsonResult.JsonResultInfo(response, xmlyAlbum);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 添加音频
	 * 
	 * */
	
	@RequestMapping("/channel/saveSound")
	public void saveSound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String name = ParameterFilter.emptyFilter("","name",request);
			String image = ParameterFilter.emptyFilter("","image",request);
			String intro = ParameterFilter.emptyFilter("暂无简介","intro",request);
			String albumId = ParameterFilter.emptyFilter("1","albumId",request);
			String status = ParameterFilter.emptyFilter("1","status",request);
			String sortId = ParameterFilter.emptyFilter("1","sortId",request);
			String id = ParameterFilter.emptyFilter("","id",request);
			XMLYAlbum xmlyAlbum = new XMLYAlbum();
			if(!"".equals(id)&&null!=id){
				xmlyAlbum.setId(Integer.parseInt(id));
			}
			xmlyAlbum.setChannelId(Integer.parseInt(channelId));
			xmlyAlbum.setName(name);
			xmlyAlbum.setImage(image);
			xmlyAlbum.setIntro(intro);
			xmlyAlbum.setAlbumId(Integer.parseInt(albumId));
			xmlyAlbum.setStatus(Integer.parseInt(status));
			xmlyAlbum.setSortId(Integer.parseInt(sortId));
//			channelService.saveSound(xmlyAlbum);
			JsonResult.JsonResultInfo(response, xmlyAlbum);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	/*
	 * 删除频道
	 * 
	 * */
	
	@RequestMapping("channel/deleteChannel")
	public void deleteChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("","id",request);
			HashMap map = new HashMap();
			map.put("id", id);
			channelService.deleteChannel(map);
			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 删除音频
	 * 
	 * */
	
	@RequestMapping("channel/deleteSound")
	public void deleteSound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String id = ParameterFilter.emptyFilter("","id",request);
			HashMap map = new HashMap();
			map.put("id", id);
			channelService.deleteSound(map);
			JSONObject result = new JSONObject();
			result.put("msg", "delete sound success!");
			result.put("code", 200);
			JsonResult.JsonResultInfo(response, result);
				
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	/*
	 * 删除推荐
	 * 
	 * */
	
	@RequestMapping("channel/deleteRecommend")
	public void deleteRecommend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("","id",request);
			HashMap map = new HashMap();
			map.put("id", id);
			channelService.deleteRecommend(map);
			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 按照名字搜索频道列表
	 * 
	 * */
	
	@RequestMapping("channel/searchChannel")
	public void searchChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String channelStr = ParameterFilter.emptyFilter("","channelStr",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			if("".equals(channelStr) || null==channelStr){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("searchStr", channelStr);
				map.put("page", page);
				map.put("pageSize", pageSize);
				Page result = channelService.searchChannels(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 按照名字搜索专辑列表
	 * 
	 * */
	
	@RequestMapping("channel/searchAlbums")
	public void searchAlbums(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String albumNameStr = ParameterFilter.emptyFilter("","albumNameStr",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			if("".equals(albumNameStr) || null==albumNameStr){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("albumNameStr", albumNameStr);
				map.put("page", page);
				map.put("pageSize", pageSize);
				Page result = channelService.searchAlbums(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 按照名字搜索专辑列表
	 * 
	 * */
	
	@RequestMapping("channel/searchSounds")
	public void searchSounds(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String soundNameStr = ParameterFilter.emptyFilter("","soundNameStr",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			if("".equals(soundNameStr) || null==soundNameStr){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("soundNameStr", soundNameStr);
				map.put("page", page);
				map.put("pageSize", pageSize);
				Page result = channelService.searchSounds(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 根据用户ID获取个人频道列表:
	 * 1、如果不带userId：默认为用户名称为fandou
	 * 2、如果userId没有指定的频道，取用户为fandou的频道
	 * 
	 * */
	
	@RequestMapping("channel/getChannelList")
	public void getChannelList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = ParameterFilter.emptyFilter("fandou","userId",request);
			JSONArray dataList = channelService.getChannelList(userId);
			if(dataList.isEmpty()){
				dataList = channelService.getChannelList("fandou");
			}
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	/*
	 * 根据频道ID获取频道下的音频列表
	 * 默认为第一页，每页个数为20个
	 * */
	
	@RequestMapping("channel/getSoundFromChannel")
	public void getSoundFromChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			if("".equals(channelId) || null==channelId){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("channelId", channelId);
				map.put("page", page);
				map.put("pageSize", pageSize);
				Page result = channelService.getSoundFromChannel(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 根据频道ID获取频道下的音频列表
	 * 默认为第一页，每页个数为20个
	 * */
	
	@RequestMapping("channel/getSoundFromAlbum")
	public void getSoundFromAlbum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			String tagId = ParameterFilter.emptyFilter("","tagId",request);
			if("".equals(albumId) || null==albumId){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("albumId", albumId);
				map.put("page", page);
				map.put("pageSize", pageSize);
				map.put("tagId", tagId);
				Page result = channelService.getSoundFromAlbum(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 通过主播名称和专辑名称获取专辑下的音频列表
	 * 默认为第一页，每页个数为20个
	 * */
	
	@RequestMapping("channel/getSoundFromAlbumByZhuboAlbumAlias")
	public void getSoundFromAlbumByZhuboAlbumAlias(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String albumName = ParameterFilter.emptyFilter("","albumName",request);
			String zhuboName = ParameterFilter.emptyFilter("","zhuboName",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			if("".equals(albumName) || null==albumName){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("albumName", albumName);
				map.put("page", page);
				map.put("pageSize", pageSize);
				map.put("zhuboName", zhuboName);
				Page result = channelService.getSoundFromAlbumByZhuboAlbumAlias(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 根据频道ID获取频道下的专辑列表
	 * 默认为第一页，每页个数为20个
	 * */
	
	@RequestMapping("channel/getAlbumsFromChannel")
	public void getAlbumsFromChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String id = ParameterFilter.emptyFilter("","channelId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			String tagId = ParameterFilter.emptyFilter("","tagId",request);
			if("".equals(id) || null==id){
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}else{
				HashMap map = new HashMap();
				map.put("id", id);
				map.put("page", page);
				map.put("pageSize", pageSize);
				map.put("tagId", tagId);
				Page result = channelService.getAlbumsFromChannel(map);
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	

	/*
	 * 删除频道和专辑关系
	 * 
	 * */
	
	@RequestMapping("channel/deleteAlbumFromChannel")
	public void deleteAlbumFromChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			HashMap map = new HashMap();
			map.put("channelId", channelId);
			map.put("albumId", albumId);
			channelService.deleteAlbumFromChannel(map);
			JSONObject result = new JSONObject();
			result.put("msg", "delete sound success!");
			result.put("code", 200);
			JsonResult.JsonResultInfo(response, result);
				
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	/*
	 * 删除专辑和音频关系
	 * 
	 * */
	
	@RequestMapping("channel/deleteSoundFromAlbum")
	public void deleteSoundFromAlbum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String soundId = ParameterFilter.emptyFilter("","soundId",request);
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			HashMap map = new HashMap();
			map.put("soundId", soundId);
			map.put("albumId", albumId);
			channelService.deleteSoundFromAlbum(map);
			JSONObject result = new JSONObject();
			result.put("msg", "delete sound success!");
			result.put("code", 200);
			JsonResult.JsonResultInfo(response, result);
				
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}		
	
	
	/*
	 * 通过网络获取频道信息
	 * 
	 * */
	
	@RequestMapping("channel/getChanelInfoFromWeb")
	public void getChanelInfoFromWeb(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			XMLYChannel xmlyChannel = getChannelInfo(channelId);
			JsonResult.JsonResultInfo(response, xmlyChannel);
				
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 通过网络获取频道信息
	 * 
	 * */
	
	@RequestMapping("channel/deleteDeviceChannel")
	public void deleteDeviceChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String id = ParameterFilter.emptyFilter("","id",request);
			channelService.deleteDeviceChannel(id);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 保存机器人绑定频道关系
	 * 
	 * */
	
	@RequestMapping("channel/saveDeviceChannel")
	public void saveDeviceChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String epalId = ParameterFilter.emptyFilter("","epalId",request);
			String channelIds = ParameterFilter.emptyFilter("","channelIds",request);
			String []channelList = channelIds.split(",");
			for(int i=0;i<channelList.length;i++){ //最后一个为空
				UserChannel userChannel = new UserChannel();
				userChannel.setchannelId(channelList[i]);
				userChannel.setuserId(epalId);
				userChannel.setId(null);
				channelService.saveDeviceChannel(userChannel);
			}
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 保存频道和专辑的关系
	 * 
	 * */
	
	@RequestMapping("channel/saveAlbumsToChannel")
	public void saveAlbumsToChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String albumsIds = ParameterFilter.emptyFilter("","albumsIds",request);
			String [] albumList = albumsIds.split(",");
			for(int i=0;i<albumList.length;i++){ //最后一个为空
				XMLYChannelAlbum xmlyChannelAlbum = new XMLYChannelAlbum();
				xmlyChannelAlbum.setAlbumId(Integer.parseInt(albumList[i]));
				xmlyChannelAlbum.setChannelId(Integer.parseInt(channelId));
				xmlyChannelAlbum.setSortId(1);
				xmlyChannelAlbum.setId(null);
				channelService.saveAlbumsToChannel(xmlyChannelAlbum);
			}
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 保存音频和专辑对应关系
	 * 
	 * */
	
	@RequestMapping("channel/saveSoundToAlbum")
	public void saveSoundToAlbum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			String soundIdList = ParameterFilter.emptyFilter("","soundIdList",request);
			String [] temp = soundIdList.split(",");
			for(int i=0;i<temp.length;i++){ //最后一个为空
				//System.out.println(temp[i]);
				XMLYAlbumSound xmlyAlbumSound = new XMLYAlbumSound();
				xmlyAlbumSound.setAlbumId(Integer.parseInt(albumId));
				xmlyAlbumSound.setSoundId(Integer.parseInt(temp[i]));
				xmlyAlbumSound.setSortId(1);
				xmlyAlbumSound.setId(null);
				channelService.saveSoundToAlbum(xmlyAlbumSound);
			}
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
		
	
	
	public XMLYChannel getChannelInfo(String channelId){
		XMLYChannel xmlyChannel = new XMLYChannel();
		String url = "http://www.ximalaya.com/zhubo/" + channelId;
		HttpRequestor httpget = new HttpRequestor();
		String html = "";
		
		try {
			//System.out.println(url);
			html = httpget.doGet(url);
			//System.out.println(html);
			Document doc = Jsoup.parse(html,"UTF-8");
			Elements content = doc.getElementsByClass("elli mgtb-10"); 
			xmlyChannel.setChannelId(Integer.parseInt(channelId));
			xmlyChannel.setImage("");
			xmlyChannel.setFans(0);
			xmlyChannel.setIntro("");
			xmlyChannel.setLevel(2);
			xmlyChannel.setName("");
			xmlyChannel.setStatus(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlyChannel;
	}
	
	
	/*
	 * 修改音频状态
	 * 
	 * */
	
	@RequestMapping("channel/switchSoundStatus")
	public void switchSoundStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String soundId = ParameterFilter.emptyFilter("","soundId",request);

			channelService.switchSoundStatus(soundId);

			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	/*
	 * 修改频道状态
	 * 
	 * */
	
	@RequestMapping("channel/switchChannelStatus")
	public void switchChannelStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String id = ParameterFilter.emptyFilter("","id",request);

			channelService.switchChannelStatus(id);

			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	/*
	 * 保存推荐专辑
	 * 
	 * */
	
	@RequestMapping("channel/saveRecommendAlbums")
	public void saveRecommendAlbums(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String tag = ParameterFilter.emptyFilter("","tag",request);
			String albums = ParameterFilter.emptyFilter("","albums",request);
			HashMap map = new HashMap();
			map.put("tag", tag);
			map.put("albums", albums);
			channelService.saveRecommendAlbums(map);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 保存推荐专辑
	 * 
	 * */
	
	@RequestMapping("channel/getSearchDetail")
	public void getSearchDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String searchName = ParameterFilter.emptyFilter("","searchName",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			HashMap map = new HashMap();
			map.put("searchName", searchName);
			map.put("pageSize", pageSize);
			map.put("page", page);
			Page result = channelService.getSearchDetail(map);
			JsonResult.JsonResultInfo(response, result);
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	//修改频道更新周期
	@RequestMapping("channel/updateCycle")
	public void updateCycle(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String id = ParameterFilter.emptyFilter("","id",request);
			String num = ParameterFilter.emptyFilter("","num",request);
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("num", num);
			channelService.updateCycle(map);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	//立即更新频道数据
	@RequestMapping("channel/updateChannelInfo")
	public void updateChannelInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String id = ParameterFilter.emptyFilter("","id",request);
			HashMap map = new HashMap();
			map.put("id", id);
			channelService.updateChannelInfo(map);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	
	//通过标签分组id获取分组下所有标签
	
	@RequestMapping("channel/getTagListFromGroup")
	public void getTagListFromGroup(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String groupId = ParameterFilter.emptyFilter("1","groupId",request);
			String tagStatus = ParameterFilter.emptyFilter("1","tagStatus",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			HashMap map = new HashMap();
			map.put("groupId", groupId);
			map.put("tagStatus", tagStatus);
			map.put("pageSize", pageSize);
			map.put("page", page);
			Page result = channelService.getTagListFromGroup(map);
			JsonResult.JsonResultInfo(response, result);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}
		
		@RequestMapping("channel/searchTagListFromGroupId")
		public void searchTagListFromGroupId(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				String groupId = ParameterFilter.emptyFilter("1","groupId",request);
				String tagStatus = ParameterFilter.emptyFilter("-1","tagStatus",request);
				String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
				String page = ParameterFilter.emptyFilter("1","page",request);
				HashMap map = new HashMap();
				map.put("groupId", groupId);
				map.put("tagStatus", tagStatus);
				map.put("pageSize", pageSize);
				map.put("page", page);
				Page result = channelService.getTagListFromGroup(map);
				JsonResult.JsonResultInfo(response, result);
			} catch (Exception e) {
				JsonResult.JsonResultError(response, 1000);
			}
		
	}
		/*
		 * 通过标签Id获取资源列表
		 * 
		 * */
		@RequestMapping("channel/getSourceFromTag")
		public void getSourceFromTag(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				String tagId = ParameterFilter.emptyFilter("1","tagId",request);
				String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
				String page = ParameterFilter.emptyFilter("1","page",request);
				HashMap map = new HashMap();
				map.put("tagId", tagId);
				map.put("pageSize", pageSize);
				map.put("page", page);
				JSONObject result = channelService.getSourceFromTag(map);
				JsonResult.JsonResultInfo(response, result);
			} catch (Exception e) {
				e.printStackTrace();
				JsonResult.JsonResultError(response, 1000);
			}
		
	}
	/*
	 * 通过标签Id获取频道列表
	 * 
	 * */
	@RequestMapping("channel/getChannelFromTag")
	public void getChannelFromTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String tagId = ParameterFilter.emptyFilter("1","tagId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			HashMap map = new HashMap();
			map.put("tagId", tagId);
			map.put("pageSize", pageSize);
			map.put("page", page);
			Page result = channelService.getChannelFromTag(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
	
	
	/*
	 * 通过标签Id获取专辑列表
	 * 
	 * */
	@RequestMapping("channel/getAlbumFromTag")
	public void getAlbumFromTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String tagId = ParameterFilter.emptyFilter("1","tagId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			HashMap map = new HashMap();
			map.put("tagId", tagId);
			map.put("pageSize", pageSize);
			map.put("page", page);
			Page result = channelService.getAlbumFromTag(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}
		
	/*
	 * 通过标签Id获取音频列表
	 * 
	 * */
	@RequestMapping("channel/getSoundFromTag")
	public void getSoundFromTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String tagId = ParameterFilter.emptyFilter("","tagId",request);
			String pageSize = ParameterFilter.emptyFilter("20","pageSize",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			HashMap map = new HashMap();
			map.put("tagId", tagId);
			map.put("pageSize", pageSize);
			map.put("page", page);
			Page result = channelService.getSoundFromTag(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}	
	/*
	 *  标签名字搜索标签
	 * 
	 * */
	@RequestMapping("channel/deleteSourceFromTag")
	public void deleteSourceFromTag(HttpServletRequest request,	
			HttpServletResponse response) throws Exception {
		String sourceId = ParameterFilter.emptyFilter("","sourceId",request);
		String type = ParameterFilter.emptyFilter("","type",request);
		HashMap map = new HashMap();
		map.put("type", type);
		map.put("sourceId", sourceId);
		channelService.deleteSourceFromTag(map);
		JsonResult.JsonResultInfo(response, "ok");
	}
	
	/*
	 *  标签名字搜索标签
	 * 
	 * */
	@RequestMapping("channel/searchTag")
	public void searchTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String tagName = ParameterFilter.emptyFilter("","tagName",request);

			HashMap map = new HashMap();
			map.put("tagName", tagName);
			Page result = channelService.searchTag(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		
	}	
	
	/*
	 * 保存标签到资源
	 * 
	 * */
	
	@RequestMapping("channel/saveTagToSource")
	public void saveTagToSource(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			String tagCheckedId = ParameterFilter.emptyFilter("","tagCheckedId",request);
			HashMap map = new HashMap();
			map.put("albumId", albumId);
			map.put("tagCheckedId", tagCheckedId);
			channelService.saveTagToSource(map);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 根据专辑ID/频道ID/音频ID获取标签列表
	 * 
	 * */
	
	@RequestMapping("channel/getSourceListFromTagId")
	public void getAlbumTagFromAlbumId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String albumId = ParameterFilter.emptyFilter("","albumId",request);
			String channelId = ParameterFilter.emptyFilter("","channelId",request);
			String soundId = ParameterFilter.emptyFilter("","soundId",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			String pageSize = ParameterFilter.emptyFilter("1000","pageSize",request);
			HashMap map = new HashMap();
			map.put("channelId", channelId);
			map.put("albumId", albumId);
			map.put("soundId", soundId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			
			Page tagList = channelService.getAlbumTagFromAlbumId(map);
			JsonResult.JsonResultInfo(response, tagList);
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	
	/*
	 * 删除标签
	 * 
	 * */
	
	@RequestMapping("channel/deleteTag")
	public void deleteTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {	
			String id = ParameterFilter.emptyFilter("","id",request);
			channelService.deleteTag(id);
			JsonResult.JsonResultInfo(response, "ok");
				
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}	

	
	/*
	 * 添加别名
	 * 
	 * */
	
	@RequestMapping("channel/saveAlias")
	public void saveAlias(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String soundId = ParameterFilter.emptyFilter("","soundId",request);
			String aliasName = ParameterFilter.emptyFilter("","aliasName",request);
			String type = ParameterFilter.emptyFilter("","type",request);
			HashMap map = new HashMap();
			map.put("soundId", soundId);
			map.put("aliasName", aliasName);
			map.put("type", type);
			channelService.saveAlias(map);
			JsonResult.JsonResultInfo(response, "ok");
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	
	/*
	 * 获取别名列表
	 * 
	 * */
	
	@RequestMapping("channel/getAliasList")
	public void getAliasList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String type = ParameterFilter.emptyFilter("","type",request);
			String page = ParameterFilter.emptyFilter("1","page",request);
			String pageSize = ParameterFilter.emptyFilter("100","pageSize",request);
			HashMap map = new HashMap();

			redisService.incr("test");
			if(!"".equals(type)&&null!=type){
				map.put("type", type);
				map.put("page", page);
				map.put("pageSize", pageSize);
				Page result = channelService.getAliasList(map);
				JsonResult.JsonResultInfo(response, result);
			}else{
				JSONObject result = new JSONObject();
				result.put("code", 404);
				result.put("msg", "no parameter!");
				JsonResult.JsonResultInfo(response, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
}
