package com.wechat.controller;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.common.dto.QueryDto;
import com.wechat.entity.AudioInfo;
import com.wechat.entity.CurriculumAudio;
import com.wechat.mp3.ExcelParser;
import com.wechat.mp3.HttpUtil;
import com.wechat.mp3.MediaInfo;
import com.wechat.qiniu.QiniuUtil;
import com.wechat.service.AudioInfoService;
import com.wechat.service.RedisService;
import com.wechat.util.JsonResult;
import com.wechat.util.Keys;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("app")
public class AudioController {

	@Resource
	private AudioInfoService audioInfoService;

	@Resource
	private RedisService redisService;

	public AudioInfoService getAudioInfoService() {
		return audioInfoService;
	}

	public void setAudioInfoService(AudioInfoService audioInfoService) {
		this.audioInfoService = audioInfoService;
	}

	@RequestMapping("audioManager")
	public String audioManager(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) {

		HashMap map = new HashMap();

		try {

			map.put("audioId", request.getParameter("audioId"));

			if (!"".equals(request.getParameter("page"))
					&& request.getParameter("page") != null) {
				queryDto.setPage(request.getParameter("page"));
			}
			if (!"".equals(request.getParameter("pageSize"))
					&& request.getParameter("pageSize") != null) {
				queryDto.setPageSize(request.getParameter("pageSize"));
			}
			map.put("name", ParameterFilter.emptyFilter("", "name", request));
			map.put("page", queryDto.getPage());
			map.put("rowsPerPage", queryDto.getPageSize());
			Page resultPage = this.audioInfoService.searchAudioInfo(map);
			List infoList = new ArrayList();
			// infoList = resultPage.getItems();
			JSONObject result = new JSONObject();
			JSONObject obj = new JSONObject();
			for (int i = 0; i < resultPage.getItems().size(); i++) {

				obj = new JSONObject();
				obj.put("id",
						((AudioInfo) resultPage.getItems().get(i)).getAudioId());
				obj.put("name",
						((AudioInfo) resultPage.getItems().get(i)).getName());
				obj.put("cover",
						((AudioInfo) resultPage.getItems().get(i)).getCover());
				obj.put("cn",
						((AudioInfo) resultPage.getItems().get(i)).getCn());
				obj.put("exp",
						((AudioInfo) resultPage.getItems().get(i)).getExp());
				obj.put("picrecog", ((AudioInfo) resultPage.getItems().get(i))
						.getPicrecog());
				obj.put("mediainfo", ((AudioInfo) resultPage.getItems().get(i))
						.getMediainfo());
				obj.put("src",
						((AudioInfo) resultPage.getItems().get(i)).getSrc());
				infoList.add(obj);

			}
			result.put("totalCount", resultPage.getTotalCount());
			result.put("pageCount", resultPage.getTotalPageCount());
			result.put("infoList", infoList);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

		return null;

	}

	@RequestMapping(value = "/saveAudioInfo")
	public String saveAudioInfo(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "cover", required = false) String file1,
			@RequestParam(value = "src", required = false) String file2,
			@RequestParam(value = "cn", required = false) String file3,
			@RequestParam(value = "exp", required = false) String file4,
			@RequestParam(value = "mediainfo", required = false) String file5,
			@RequestParam(value = "picrecog", required = false) String file6,
			AudioInfo audioInfo) throws Exception {

		try {
			Cookie[] enu = request.getCookies();
			String userId = "0";
			for(int i=0;i<enu.length;i++){
				Cookie cookie = enu[i];
				String value  = cookie.getValue();
				String name = cookie.getName();
				if("adminUser".equals(name)){
					userId = value;
				}
			}
			AudioInfo tempAudioInfo = new AudioInfo();
			if (!"".equals(audioInfo.getId()) && audioInfo.getId() != null) {
				tempAudioInfo = this.audioInfoService.getAudioIdInfo(audioInfo
						.getId().toString());
			}
			File targetFile = null;
			String pathName = "";
			String fileName = "";
			
			if (audioInfo.getId() != null) {
				audioInfo.setAudioId(audioInfo.getId() + "");
			}
			if (file1 != null) {

				audioInfo.setCover(file1);// 七牛上传

			}

			if (file2 != null) {

				audioInfo.setSrc(file2);// 七牛上传

			}

			if (file3 != null) {

				audioInfo.setCn(file3);// 七牛上传

			}

			if (file4 != null) {

				audioInfo.setExp(file4);// 七牛上传

			}

			if (file5 != null) {
				audioInfo.setMediainfo(file5);// 七牛上传

			}

			if (file6 != null) {

				audioInfo.setPicrecog(file6);// 七牛上传

			}
			audioInfo.setUserId(Integer.parseInt(userId));
			this.audioInfoService.saveAudioInfo(audioInfo);

		} catch (Exception e) {

		}

		return "redirect:audioInfoManager";
	}

	@RequestMapping("/audioInfoManager")//备课资源管理页面
	public String audioInfoManager(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) throws Exception {
		Cookie[] enu = request.getCookies();
		String userId = "0";
		for(int i=0;i<enu.length;i++){//取cookies中的用户ID
			Cookie cookie = enu[i];
			String value  = cookie.getValue();
			String name = cookie.getName();
			if("adminUser".equals(name)){
				userId = value;
			}
		}
		HashMap map = new HashMap();
		map.put("audioId", request.getParameter("audioId"));
		map.put("userId", userId);
		if (!"".equals(request.getParameter("page"))
				&& request.getParameter("page") != null) {
			queryDto.setPage(request.getParameter("page"));
		}
		if (!"".equals(request.getParameter("pageSize"))
				&& request.getParameter("pageSize") != null) {
			queryDto.setPageSize(request.getParameter("pageSize"));
		}
		map.put("page", queryDto.getPage());
		map.put("rowsPerPage", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = this.audioInfoService.searchAudioInfo(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);

		HashMap classRoomHap = this.audioInfoService.getClassRoomMap();
		List jsonList = new ArrayList();
		JSONObject jsonObject = new JSONObject();
		List infoList = new ArrayList();
		JSONObject result = new JSONObject();
		JSONObject obj = new JSONObject();
		for (int i = 0; i < resultPage.getItems().size(); i++) {//查询结果从新封装
			obj = new JSONObject();
			obj.put("id",
					((AudioInfo) resultPage.getItems().get(i)).getAudioId());
			obj.put("pid", ((AudioInfo) resultPage.getItems().get(i)).getId());
			obj.put("name",
					((AudioInfo) resultPage.getItems().get(i)).getName());
			obj.put("cover",
					((AudioInfo) resultPage.getItems().get(i)).getCover());
			obj.put("cn", ((AudioInfo) resultPage.getItems().get(i)).getCn());
			obj.put("exp", ((AudioInfo) resultPage.getItems().get(i)).getExp());
			obj.put("picrecog",
					((AudioInfo) resultPage.getItems().get(i)).getPicrecog());
			obj.put("mediainfo",
					((AudioInfo) resultPage.getItems().get(i)).getMediainfo());
			obj.put("src", ((AudioInfo) resultPage.getItems().get(i)).getSrc());
			obj.put("userId", ((AudioInfo) resultPage.getItems().get(i)).getUserId());
			if (classRoomHap.get(((AudioInfo) resultPage.getItems().get(i))
					.getAudioId().toString()) != null) {
				obj.put("status", "1");
			} else {
				obj.put("status", "0");
			}
			infoList.add(obj);
		}
		jsonObject.put("infoList", infoList);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("jsonStr", jsonObject.toString());
		return "audio/audioInfoManager";
	}

	/**
	 *  通过备课资源ID，获取备课资源的信息，带source,单资源备课
	 * 
	 * */
	@RequestMapping("getAudioInfo")
	public String getAudioInfo(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) {
		AudioInfo audioInfo = this.audioInfoService.getAudioIdInfo(request
				.getParameter("audioId"));
		String noCach = ParameterFilter.emptyFilter("", "noCach", request);//是否需要更新缓存
		try {
			List jsonList = new ArrayList();
			JSONObject jsonObj = new JSONObject();
			JSONObject jobj = new JSONObject();
			String key = "AudioInfo_" + request.getParameter("audioId");
			String result = redisService.get(key);
			// 如果nocach=true强制刷新缓存
			if ("true".equals(noCach)) {
				result = "";
			}
			if (null != result && !"".equals(result)) {
				JsonResult.JsonResultInfo(response, result);
			} else {
				//System.out.println(audioInfo.getMediainfo());
				boolean success = HttpUtil.downLoadFromUrl(
						audioInfo.getMediainfo(), "mediainfo.xls",
						Keys.USER_PIC_PATH);
				if (success) {
					result = ExcelInfo2Json(Keys.USER_PIC_PATH
							+ "mediainfo.xls");
					redisService.set(key, result);
					JsonResult.JsonResultInfo(response, result);
				} else {
					JsonResult.JsonResultError(response, 1000);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
		return null;
	}

	/**
	 *  通过备课资源ID，获取备课资源的信息，带source,多资源备课
	 * 
	 * */
	@RequestMapping("getAudioInfo2")
	public String getAudioInfo2(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) {
		String audioIds = request.getParameter("audioId");
		String[] temp = audioIds.split(",");
		JSONArray data = new JSONArray();
		for(int i=0;i<temp.length;i++){
			JSONObject item = new JSONObject();
			String key = "AudioInfo_" + temp[i];
			String result = redisService.get(key);
			AudioInfo audioInfo = this.audioInfoService.getAudioIdInfo(temp[i]);
			if (null != result && !"".equals(result)) {
				item.put("audioId", temp[i]);
				item.put("source", result);
				if(null!=audioInfo.getName()){
					item.put("name", audioInfo.getName());
				}else{
					item.put("name", "");
				}
				
				if(null!=audioInfo.getSrc()){
					item.put("src", audioInfo.getSrc());
				}else{
					item.put("src", "");
				}
				if(null!=audioInfo.getExp()){
					item.put("exp", audioInfo.getExp());
				}else{
					
					item.put("exp", "");
				}
				if(null!=audioInfo.getCn()){
					item.put("cn", audioInfo.getCn());
				}else{
					item.put("cn", "");
				}
				
				if(null!=audioInfo.getMediainfo()){
					
					item.put("mediainfo", audioInfo.getMediainfo());
				}else{
					item.put("mediainfo", "");
				}
				
				data.add(item);
			} else {
				
				boolean success = HttpUtil.downLoadFromUrl(
						audioInfo.getMediainfo(), "mediainfo.xls",
						Keys.USER_PIC_PATH);
				//System.out.print(success);
				if (success) {
					result = ExcelInfo2Json(Keys.USER_PIC_PATH
							+ "mediainfo.xls");
					redisService.set(key, result);
					item.put("audioId", temp[i]);
					item.put("source", result);
					item.put("name", audioInfo.getName());
					item.put("src", audioInfo.getSrc());
					item.put("exp", audioInfo.getExp());
					item.put("cn", audioInfo.getCn());
					item.put("mediainfo", audioInfo.getMediainfo());
					data.add(item);
				} else {
					item.put("audioId", temp[i]);
					//缓存没有课程标注数据同时标识文件也 不存在，取数据库中当前资源的总时长，组合成默认的标识文件
					JSONObject temp1 = new JSONObject();
					JSONArray temp2 = new JSONArray();
					JSONObject temp3 = new JSONObject();
					
					Integer totalTime = this.audioInfoService.getAudioLength(temp[i]);
					
					if(totalTime!=null){
						totalTime = totalTime*1000;
					}
					
					temp3.put("id", 1);
					temp3.put("expStartTime", 0);
					temp3.put("startTime", 0);
					temp3.put("isSelected", false);
					temp3.put("homework", -1);
					temp3.put("page", 0);
					temp3.put("enText", "all");
					
					temp3.put("readLength", totalTime);
					temp3.put("expLength", 0);
					temp3.put("length", totalTime);
					temp3.put("expText", "");     
    
					temp2.add(temp3);
					temp1.put("shortSentenceList", temp2);
					item.put("source", temp1);
					item.put("name", audioInfo.getName());
					item.put("src", audioInfo.getSrc());
					item.put("exp", audioInfo.getExp());
					item.put("cn", audioInfo.getCn());
					item.put("mediainfo", audioInfo.getMediainfo());
					//System.out.print(item);
					data.add(item);
				}
			}
		}
		
		JsonResult.JsonResultInfo(response, data);
		return null;
		
	}

	
	@RequestMapping(value = "/deleteAudioInfo")
	public String deleteAudioInfo(HttpServletRequest request,
			HttpServletResponse response) {

		AudioInfo audioInfo = new AudioInfo();
		audioInfo = this.audioInfoService.getAudioIdInfo(request
				.getParameter("audioId"));

		File deleteFile = null;

		if (!"".equals(audioInfo.getCover()) && audioInfo.getCover() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/cover/"
					+ audioInfo.getCover().substring(
							audioInfo.getCover().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(
					Keys.QINIU_IMAGE,
					audioInfo.getCover().substring(
							audioInfo.getCover().lastIndexOf("/") + 1));
		}

		if (!"".equals(audioInfo.getSrc()) && audioInfo.getSrc() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/src/"
					+ audioInfo.getSrc().substring(
							audioInfo.getSrc().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(
					Keys.QINIU_IMAGE,
					audioInfo.getSrc().substring(
							audioInfo.getSrc().lastIndexOf("/") + 1));
		}
		if (!"".equals(audioInfo.getCn()) && audioInfo.getCn() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/cn/"
					+ audioInfo.getCn().substring(
							audioInfo.getCn().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(
					Keys.QINIU_IMAGE,
					audioInfo.getCn().substring(
							audioInfo.getCn().lastIndexOf("/") + 1));
		}
		if (!"".equals(audioInfo.getExp()) && audioInfo.getExp() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/exp/"
					+ audioInfo.getExp().substring(
							audioInfo.getExp().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(
					Keys.QINIU_IMAGE,
					audioInfo.getExp().substring(
							audioInfo.getExp().lastIndexOf("/") + 1));
		}
		if (!"".equals(audioInfo.getMediainfo())
				&& audioInfo.getMediainfo() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/mediainfo/"
					+ audioInfo.getMediainfo().substring(
							audioInfo.getMediainfo().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(Keys.QINIU_IMAGE, audioInfo.getMediainfo()
					.substring(audioInfo.getMediainfo().lastIndexOf("/") + 1));
		}
		if (!"".equals(audioInfo.getPicrecog())
				&& audioInfo.getPicrecog() != null) {
			deleteFile = new File(Keys.USER_PIC_PATH
					+ "/lesson/picrecog/"
					+ audioInfo.getPicrecog().substring(
							audioInfo.getPicrecog().lastIndexOf("/") + 1));
			deleteFile.delete();
			QiniuUtil.delFile(Keys.QINIU_IMAGE, audioInfo.getPicrecog()
					.substring(audioInfo.getPicrecog().lastIndexOf("/") + 1));
		}

		this.audioInfoService.deleteAudioInfo(request.getParameter("audioId"));

		return "redirect:audioInfoManager";
	}

	public static String ExcelInfo2Json(String path) {
		MediaInfo info = ExcelParser.getInstance().getMediaInfo(path);

		if (info != null) {
			Gson gson = new GsonBuilder().setExclusionStrategies(
					new ExclusionStrategy() {

						@Override
						public boolean shouldSkipClass(Class<?> arg0) {
							return false;
						}

						@Override
						public boolean shouldSkipField(FieldAttributes f) {
							return f.getName().contains("longSentenceList")
									| f.getName().contains("pageInfo")
									| f.getName().contains("isExpExist");
						}

					}).create();
			return gson.toJson(info);
		}
		return "";
	}

	@RequestMapping("getAudioInfoByCurriculumId")
	public void getAudioIdByCurriculumId(HttpServletRequest request,
			HttpServletResponse response) {

		String curriculumId = ParameterFilter.emptyFilter("", "curriculumId",
				request);
		if (null != curriculumId && !"".equals(curriculumId)) {
			JSONArray curriculumAudio = this.audioInfoService
					.getAudioIdByCurriculumId(curriculumId);
			JsonResult.JsonResultInfo(response, curriculumAudio);
		} else {
			JsonResult.JsonResultError(response, 404);
		}

	}

	@RequestMapping("getAudioBindCourseList")
	public void getAudioBindCourseList(HttpServletRequest request,
			HttpServletResponse response) {

		String audioId = ParameterFilter.emptyFilter("", "audioId", request);
		if (null != audioId && !"".equals(audioId)) {
			JSONArray curriculumAudio = this.audioInfoService
					.getAudioBindCourseList(audioId);
			JsonResult.JsonResultInfo(response, curriculumAudio);
		} else {
			JsonResult.JsonResultError(response, 404);
		}

	}

	@RequestMapping("saveCourseToAudio")
	public void saveCourseToAudio(HttpServletRequest request,
			HttpServletResponse response) {

		String audioId = ParameterFilter.emptyFilter("", "audioId", request);
		String pidList = ParameterFilter.emptyFilter("", "pidList", request);
		if (null != audioId && !"".equals(audioId)) {
			String[] mallProductList = pidList.split(",");
			for (int i = 0; i < mallProductList.length; i++) {
				CurriculumAudio curriculumAudio = new CurriculumAudio();
				curriculumAudio.setAudioId(Integer.parseInt(audioId));
				curriculumAudio.setCurriculumId(Integer
						.parseInt(mallProductList[i]));
				this.audioInfoService.saveCourseToAudio(curriculumAudio);
			}
			JsonResult.JsonResultInfo(response, "ok");
		} else {
			JsonResult.JsonResultError(response, 404);
		}

	}

	public static void main(String[] args) {

		String aa = "http://img.hexun.com/2011-06-21/130726386.jpg";
		//System.out.println(aa.indexOf("/"));
		//System.out.println(aa.substring(aa.lastIndexOf("/") + 1));
	}

}
