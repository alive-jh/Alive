package com.wechat.controller;

import com.wechat.entity.*;
import com.wechat.service.ChannelService;
import com.wechat.service.SoundService;
import com.wechat.util.Base64;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class SoundApiController {

	@Resource
	private SoundService soundService;

	@Resource
	private ChannelService channelService;

	public static JSONObject getHtmlJsonByUrl(String urlTemp) {
		URL url = null;
		InputStreamReader input = null;
		HttpURLConnection conn;
		JSONObject jsonObj = null;
		try {
			url = new URL(urlTemp);
			conn = (HttpURLConnection) url.openConnection();
			input = new InputStreamReader(conn.getInputStream(), "utf-8");
			Scanner inputStream = new Scanner(input);
			StringBuffer sb = new StringBuffer();
			while (inputStream.hasNext()) {
				sb.append(inputStream.nextLine());
			}
			jsonObj = JSONObject.fromObject(sb.toString());
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
		return jsonObj;
	}

	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/*
	 * 音频搜索 根据音频名模糊搜索 , 或者ISBN精确查询 默认分页20每页
	 */
	@RequestMapping("sound/search")
	@ResponseBody
	public Map<String, Object> soundSearch(String soundName, String ISBN,
										   Integer page, String userName, String searchType, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(page == null)
			page = 1;
		try {
			//System.out.println(new Date());
			Map<String, Object> data  = new HashMap<>();
			if (ISBN != null) {
				//System.out.println(new Date());
				 data = soundService.searchByISBN(ISBN,userName, soundName, page, 20);
				 //System.out.println(new Date());
			}else {
				data = soundService.fuzzySearchByName(soundName, page, 20);

				List list = (List) data.get("data");
				int count = 0;
				while (list.size() == 0){
					count++;
					data = soundService.fuzzySearchByName(soundName, ++page, 20);
					list = (List) data.get("data");
					if (count == 20){
						break;
					}
				}

			}

			result.put("total", data.get("totalPage"));
			result.put("currentPage", data.get("currentPage"));
			result.put("nextpage", data.get("nextPage"));
			result.put("data", data.get("data"));

			result.put("msg", "ok");
			result.put("code", 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}


	/*
	 * 保存用户收藏
	 */
	@RequestMapping("sound/collect")
	public void soundCollect(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String soundId = request.getParameter("soundId");
			String image = request.getParameter("image");
			String soundUrl = request.getParameter("soundUrl");
			String soundName = request.getParameter("soundName");
			String serierName = request.getParameter("serierName");
			String ISBN = request.getParameter("ISBN");
			String userId = request.getParameter("userId");

			SoundCollection soundCollection = new SoundCollection();
			soundCollection.setImage(image);
			soundCollection.setISBN(ISBN);
			soundCollection.setSerierName(serierName);
			soundCollection.setSoundId(soundId);
			soundCollection.setSoundName(soundName);
			soundCollection.setSoundUrl(soundUrl);
			soundCollection.setUserId(userId);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = df.format(new Date());
			Date date = df.parse(currentDate);
			soundCollection.setInsertDate(date);

			soundService.saveCollection(soundCollection);
			JsonResult.JsonResultInfo(response, soundCollection);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 获取用户收藏
	 */
	@RequestMapping("sound/searchCollection")
	public void soundsearchCollection(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");

			HashMap map = new HashMap();
			map.put("userId", userId);

			Page result = soundService.searchCollection(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 用户删除收藏
	 */
	@RequestMapping("sound/deleteSoundCollection")
	public void deleteSoundCollection(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
			String soundId = request.getParameter("soundId");

			HashMap map = new HashMap();
			map.put("userId", userId);
			map.put("soundId", soundId);

			soundService.deleteCollection(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 置顶功能接口
	 */

	@RequestMapping("sound/top")
	public void soundTop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String soundId = request.getParameter("soundId");
			String image = request.getParameter("image");
			String soundUrl = request.getParameter("soundUrl");
			String soundName = request.getParameter("soundName");
			String serierName = request.getParameter("serierName");
			String ISBN = request.getParameter("ISBN");

			SoundBest soundBest = new SoundBest();
			soundBest.setImage(image);
			soundBest.setISBN(ISBN);
			soundBest.setSerierName(serierName);
			soundBest.setSoundId(soundId);
			soundBest.setSoundName(soundName);
			soundBest.setSoundUrl(soundUrl);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = df.format(new Date());
			Date date = df.parse(currentDate);
			soundBest.setInsertDate(date);

			soundService.saveSoundTop(soundBest);
			JsonResult.JsonResultInfo(response, soundBest);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 取消置顶
	 */

	@RequestMapping("sound/CancelTop")
	public void soundCancelTop(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			soundService.CancelTop(id);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 取消置顶
	 */

	@RequestMapping("sound/play")
	public void soundPlay(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Base64 base64 = new Base64();
			String source = request.getParameter("source");
			byte[] decodeStr = Base64.decode(source);
			String url = new String(decodeStr);
//			MongoHandle mongoHandle = new MongoHandle("fandou",
//					"PlaySoundRecord");
//			JSONObject parameter = new JSONObject();
//			parameter.put("url", url);
//			parameter.put("type", "sound_play");
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
//			parameter.put("insertDate", df.format(new Date()));
//			mongoHandle.saveDocument(parameter);
			response.sendRedirect(url);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 获取分类列表 （废弃）
	 */

	@RequestMapping("sound/getTagListFromCategory")
	public void soundCatagory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
//			JSONObject parameter = new JSONObject();
//			Enumeration enu = request.getParameterNames();
//			while (enu.hasMoreElements()) {
//				String paraName = (String) enu.nextElement();
//				parameter.put(paraName, request.getParameter(paraName));
//			}
//			MongoHandle mongo = new MongoHandle("fandou", "SoundCategory");
//			JSONObject sortParameter = new JSONObject();
//			sortParameter.put("sortId", 1);
//			JSONArray dataList = mongo.getDocument(parameter, sortParameter);
//			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 获取分类列表 暂时废弃（不用）
	 */

	@RequestMapping("sound/getSoundListForTag")
	public void soundGetSound(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		JSONObject parameter = new JSONObject();
//		Enumeration enu = request.getParameterNames();
//		while (enu.hasMoreElements()) {
//			String paraName = (String) enu.nextElement();
//			parameter.put(paraName, request.getParameter(paraName));
//		}
//		MongoHandle mongo = new MongoHandle("fandou", "data");
//		JSONObject sortParameter = new JSONObject();
//		sortParameter.put("play_count", -1);
//		JSONObject dataList = mongo.getPageDocument(parameter, sortParameter);
//		JsonResult.JsonResultInfo(response, dataList);
	}

	/*
	 * 模糊搜索主播
	 */

	@RequestMapping("sound/searchChannels")
	@ResponseBody
	public Map<String, Object> searchChannels(Integer page, Integer pageSize,
			String name) {
		if (page == null)
			page = 1;
		if (pageSize == null)
			pageSize = 20;

		Map<String, Object> result = new HashMap<>();
		try {
			result.put("success", 1);
			result.put("data",
					soundService.fuzzySearchChannels(page, pageSize, name));

		} catch (Exception e) {
			result.put("success", 0);
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 模糊搜索专辑
	 */

	@RequestMapping("sound/searchAlbums")
	@ResponseBody
	public Map<String, Object> fuzzySearchAlbums(Integer page,
			Integer pageSize, String name) {

		if (page == null)
			page = 1;
		if (pageSize == null)
			pageSize = 20;

		Map<String, Object> result = new HashMap<>();
		try {
			result.put("success", 1);
			result.put("data",
					soundService.fuzzySearchAlbums(page, pageSize, name));

		} catch (Exception e) {
			result.put("success", 0);
			e.printStackTrace();
		}

		return result;

	}

	/*
	 * 通过channelId获取专辑列表
	 */

	@RequestMapping("sound/getAlbums")
	@ResponseBody
	public Map<String, Object> getAlbums(Integer page, Integer pageSize,
			Integer channelId) {
		if (page == null)
			page = 1;
		if (pageSize == null)
			pageSize = 20;
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("success", 1);
			result.put("data",
					soundService.getAlbumsByCollectionId(page, pageSize, channelId));

		} catch (Exception e) {
			result.put("success", 0);
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 根据albumId获取声音
	 */
	@RequestMapping("sound/getSound")
	@ResponseBody
	public Map<String, Object> getSound(Integer albumId, Integer page,
			Integer pageSize) {
		if (page == null)
			page = 1;
		if (pageSize == null)
			pageSize = 20;

		Map<String, Object> result = new HashMap<>();
		try {
			result.put("success", 1);
			result.put("data",
					soundService.SearchByAlbumId(page, pageSize, albumId));

		} catch (Exception e) {
			result.put("success", 0);
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 随机获取推荐音频
	 */

	@RequestMapping("sound/getRecommend")
	public void getRecommend(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tag = ParameterFilter.emptyFilter("", "tag", request);
		String pageSize = ParameterFilter
				.emptyFilter("20", "pageSize", request);
		String page = ParameterFilter.emptyFilter("1", "page", request);
		if (null != tag && !"".equals(tag)) {
			HashMap map = new HashMap();
			map.put("tag", tag);
			map.put("page", page);
			map.put("pageSize", pageSize);
			JSONObject dataList = channelService.getRecommendSound(map);
			JsonResult.JsonResultInfo(response, dataList);
		} else {
			JSONObject result = new JSONObject();
			result.put("msg", "No Parameter!");
			result.put("code", 405);
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/*
	 * 通过日期ID获取分类列表
	 */

	@RequestMapping("sound/getTagListFromDateId")
	public void getTagListFromDateId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dateId = ParameterFilter.emptyFilter("", "dateId", request);
		String pageSize = ParameterFilter.emptyFilter("1000", "pageSize",
				request);
		String page = ParameterFilter.emptyFilter("1", "page", request);
		if (null != dateId && !"".equals(dateId)) {
			HashMap map = new HashMap();
			map.put("dateId", dateId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page dataList = soundService.getTagListFromDateId(map);
			JsonResult.JsonResultInfo(response, dataList);
		} else {
			JSONObject result = new JSONObject();
			result.put("msg", "No Parameter!");
			result.put("code", 405);
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/*
	 * 通过分类ID获取资源列表
	 */

	@RequestMapping("sound/getSourceFromTagId")
	public void getSourceFromTagId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tagId = ParameterFilter.emptyFilter("", "tagId", request);
		String pageSize = ParameterFilter.emptyFilter("1000", "pageSize",
				request);
		String page = ParameterFilter.emptyFilter("1", "page", request);
		if (null != tagId && !"".equals(tagId)) {
			HashMap map = new HashMap();
			map.put("tagId", tagId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page dataList = soundService.getSourceFromTagId(map);
			JsonResult.JsonResultInfo(response, dataList);
		} else {
			JSONObject result = new JSONObject();
			result.put("msg", "No Parameter!");
			result.put("code", 405);
			JsonResult.JsonResultInfo(response, result);
		}
	}

	/*
	 * 保存每日推荐的日期信息
	 */

	@RequestMapping("sound/saveDailyRecommendDate")
	public void saveDailyRecommendDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String dateName = ParameterFilter.emptyFilter("", "dateName",
					request);
			String intro = ParameterFilter.emptyFilter("", "intro", request);
			DailyRecommendDate dailyRecommendDate = new DailyRecommendDate();
			if (!"".equals(id) && id != null) {
				dailyRecommendDate.setId(Integer.parseInt(id));
			}
			dailyRecommendDate.setDateName(dateName);
			dailyRecommendDate.setIntro(intro);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String createTime = df.format(new Date());
			dailyRecommendDate.setCreateTime(createTime);
			soundService.saveDailyRecommendDate(dailyRecommendDate);

			JsonResult.JsonResultInfo(response, dailyRecommendDate);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 删除每日推荐的日期信息
	 */

	@RequestMapping("sound/deleteDailyDate")
	public void deleteDailyDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			soundService.deleteDailyDate(id);

			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 删除每日推荐分类
	 */

	@RequestMapping("sound/deleteTagFromDate")
	public void deleteTagFromDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			soundService.deleteTagFromDate(id);

			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 删除分类中的资源
	 */

	@RequestMapping("sound/deleteSoundFromTag")
	public void deleteSoundFromTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			soundService.deleteSoundFromTag(id);

			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/*
	 * 保存每日推荐的日期分类
	 */

	@RequestMapping("sound/saveDailyRecommendTag")
	public void saveDailyRecommendTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String tagName = ParameterFilter
					.emptyFilter("", "tagName", request);
			String intro = ParameterFilter.emptyFilter("", "intro", request);
			String dateId = ParameterFilter.emptyFilter("", "dateId", request);
			String createTime = ParameterFilter.emptyFilter("", "createTime",
					request);
			DailyRecommendTag dailyRecommendTag = new DailyRecommendTag();
			if (!"".equals(id) && id != null) {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String currentTime = df.format(new Date());
				dailyRecommendTag.setId(Integer.parseInt(id));
				dailyRecommendTag.setCreateTime(currentTime);
			}
			dailyRecommendTag.setTagName(tagName);
			dailyRecommendTag.setIntro(intro);
			dailyRecommendTag.setDateId(Integer.parseInt(dateId));
			dailyRecommendTag.setCreateTime(createTime);
			soundService.saveDailyRecommendTag(dailyRecommendTag);
			//System.out.println(dailyRecommendTag);
			JsonResult.JsonResultInfo(response, dailyRecommendTag);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 保存每日推荐的日期分类
	 */

	@RequestMapping("sound/updateDailyRecommendTagSort")
	public void updateDailyRecommendTagSort(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String data = ParameterFilter.emptyFilter("", "data", request);

			String[] temp = data.split(",");
			for (int i = 0; i < temp.length; i++) {
				String temp2[] = temp[i].split("_");
				String id = temp2[0];
				String sortId = temp2[1];
				soundService.updateDailyRecommendTagSort(id, sortId);
			}

			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 从分类中删除每日推荐音频
	 */

	@RequestMapping("sound/deleteDailyRecommendSoundFromTag")
	public void deleteDailyRecommendSoundFromTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);

			soundService.deleteDailyRecommendSoundFromTag(id);

			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 通过日期获取每日推荐音频
	 */

	@RequestMapping("sound/getDailyRecommendSoundList")
	public void getDailyRecommendSoundList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String dateId = ParameterFilter.emptyFilter("", "dateId", request);
			if (!"".equals(dateId) && null != dateId) {

			} else {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
				dateId = df.format(new Date());
			}

			Page result = soundService.getDailyRecommendSoundList(dateId);

			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/*
	 * 保存音频到分类
	 */

	@RequestMapping("sound/saveSoundToTag")
	public void saveSoundToTag(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			String tagId = ParameterFilter.emptyFilter("", "tagId", request);
			String soundIdList = ParameterFilter.emptyFilter("", "soundIdList",
					request);
			String[] temp = soundIdList.split(",");
			for (int i = 0; i < temp.length; i++) { // 最后一个为空
			// //System.out.println(temp[i]);
				DailyRecommendSource dailyRecommendSource = new DailyRecommendSource();
				dailyRecommendSource.setTagId(Integer.parseInt(tagId));
				dailyRecommendSource.setSourceId(Integer.parseInt(temp[i]));
				dailyRecommendSource.setSortId(1);
				dailyRecommendSource.setId(null);
				dailyRecommendSource.setStatus(1);
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String currentTime = df.format(new Date());
				dailyRecommendSource.setCreateTime(currentTime);
				soundService.saveSoundToTag(dailyRecommendSource);
			}
			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}

}
