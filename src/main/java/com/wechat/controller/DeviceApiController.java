package com.wechat.controller;

import com.google.gson.JsonArray;
import com.wechat.entity.*;
import com.wechat.service.DeviceService;
import com.wechat.service.DeviceTestService;
import com.wechat.service.LessonService;
import com.wechat.util.EpalIDGenerator;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("api")
public class DeviceApiController {

	@Resource
	private DeviceService deviceService;

	@Resource
	private DeviceTestService deviceTestService;
	
	@Autowired
	LessonService lessonService;
	
	private static Logger logger = Logger.getLogger(DeviceApiController.class);  

	public DeviceTestService getDeviceTestService() {
		return deviceTestService;
	}

	public void setDeviceTestService(DeviceTestService deviceTestService) {
		this.deviceTestService = deviceTestService;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public boolean whetherIfDevice(String deviceNo) {
		Device device = deviceService.searchDeviceByDeviceNo(deviceNo);
		if (null != device) {
			return true;
		}
		return false;
	}
	
	/**
	 * 机器人是否在线
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("deviceOnLineState")
	public void deviceOnLineState(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("{\"success\":1,\"isOnLine\":\"true\"}");
	}
	
	

	// ***********************************************************************************

	/**
	 * DeviceTest表操作
	 */

	/**
	 * Epal根据设备唯一标示得到所有测试设备信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceTests")
	public void searchDeviceTests(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("deviceNo",
					ParameterFilter.emptyFilter("", "deviceNo", request));
			Page resultPage = deviceService.searchDeviceTests(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 添加/修改设备测试信息web
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceTest")
	public void saveDeviceTest(HttpServletRequest request,
			HttpServletResponse response, DeviceTest deviceTest) {
		try {
			Integer id = deviceTest.getId();
			deviceTest.setRegistTime(new Timestamp(System.currentTimeMillis()));
			if (!"".equals(id) && null != id) {
				DeviceTest deviceT = this.getDeviceTestService()
						.searchDeviceTest(id);
				deviceTest.setBackResult(deviceT.getBackResult());
				deviceTest.setReviewMen(deviceT.getReviewMen());
				deviceTest.setReviewTime(deviceT.getReviewTime());
			}
			deviceTestService.saveDeviceTest(deviceTest);
			JsonResult.JsonResultInfo(response, deviceTest);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 添加/修改设备审核信息app
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("changeDeviceTestStatusApp")
	public void changeDeviceTestStatusApp(HttpServletRequest request,
			HttpServletResponse response, DeviceTest deviceTest) {
		try {
			if (whetherIfDevice(deviceTest.getDeviceNo())) {
				deviceTest.setRegistTime(new Timestamp(System
						.currentTimeMillis()));
				if (!"".equals(deviceTest.getDeviceNo())
						&& null != deviceTest.getDeviceNo()) {
					DeviceTest deviceT = this.getDeviceTestService()
							.searchDeviceTest(deviceTest.getDeviceNo());
					deviceTest.setId(deviceT.getId());
					deviceTest.setBackResult(deviceT.getBackResult());
					deviceTest.setReviewMen(deviceT.getReviewMen());
					deviceTest.setReviewTime(deviceT.getReviewTime());
				}
				deviceTestService.saveDeviceTest(deviceTest);
				JsonResult.JsonResultInfo(response, deviceTest);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 修改设备审核信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("changeDeviceTestStatusWeb")
	public void changeDeviceTestStatusWeb(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			String backResult = request.getParameter("backResult");
			String reviewMen = request.getParameter("reviewMen");
			Timestamp reviewTime = new Timestamp(System.currentTimeMillis());
			DeviceTest deviceTest = new DeviceTest();
			if (!"".equals(id)) {
				deviceTest = this.getDeviceTestService().searchDeviceTest(
						new Integer(id));
			}
			deviceTest.setStatus(Integer.parseInt(status));
			deviceTest.setBackResult(backResult);
			deviceTest.setReviewMen(reviewMen);
			deviceTest.setReviewTime(reviewTime);
			deviceTestService.saveDeviceTest(deviceTest);
			JsonResult.JsonResultInfo(response, deviceTest);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 根据设备唯一标示查询设备测试信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceTestByDeviceNo")
	public void searchDeviceTestByDeviceNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String deviceNo = ParameterFilter.emptyFilter("", "deviceNo",
					request);
			DeviceTest deviceTest = new DeviceTest();
			deviceTest = deviceService.searchDeviceTestByDeviceNo(deviceNo);
			if (null != deviceTest.getId()) {
				JsonResult.JsonResultInfo(response, deviceTest);
			} else {
				JsonResult.JsonResultError(response, 1004);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal根据设备唯一标示删除设备测试信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteDeviceTestByDeviceNo")
	public void deleteDeviceTestByDeviceNo(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deviceNo = request.getParameter("deviceNo");
			deviceService.deleteDeviceTestByDeviceNo(deviceNo);
			JSONObject data = new JSONObject();
			data.put("deviceNo", deviceNo);
			JsonResult.JsonResultInfo(response, data);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * DeviceTest表操作
	 */

	// ***********************************************************************************

	/**
	 * Device表操作
	 */

	/**
	 * Epal所有设备信息查询接口
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("searchDevices")
	public void searchDevices(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			HashMap map = new HashMap();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize",
					ParameterFilter.emptyFilter("30", "pageSize", request));
			Page resultPage = deviceService.searchDevices(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal注册初始化信息到服务器,
	 * 一、传参数：epalId和nickName，修改昵称。
	 * 二、传参数：epalId和version，修改当前版本号
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("registDevice")
	public void registDevice(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deviceNo = ParameterFilter.emptyFilter("", "deviceNo",
					request);
			String epalId = ParameterFilter.emptyFilter("", "epalId",
					request);
			String deviceType = ParameterFilter.emptyFilter("eggy", "deviceType",
					request);
			String nickName = ParameterFilter.emptyFilter("", "nickName",
					request);
			String version = ParameterFilter.emptyFilter("", "version",
					request);
			String sn = request.getParameter("sn");
			if(null != epalId && !"".equals(epalId)){
				Device device = new Device();
				device = deviceService.searchDeviceByEpalId(epalId);
				if(null != nickName && !"".equals(nickName)){
					device.setNickName(nickName);
				}
				if(null != version && !"".equals(version)){
					device.setVersion(version);
				}				
				deviceService.saveDevice(device);
				JsonResult.JsonResultInfo(response, device);
			}else{
				if (deviceNo.length() == 13) {
					String[] deviceInfo = EpalIDGenerator.getDeviceInfo(deviceNo);
					Device device = new Device();
					device = deviceService.searchDeviceByDeviceNo(deviceNo);
					if (device.getId() != null) {
						JSONObject obj = new JSONObject();
						obj.put("deviceId", device.getId());
						JsonResult.JsonResultInfo(response, obj);
					} else {
						device.setDeviceNo(deviceInfo[0]);
						device.setEpalId(deviceInfo[1]);
						device.setEpalPwd(deviceInfo[2]);
						device.setSn(sn);
						device.setNickName(deviceInfo[1]);
						device.setDeviceType(deviceType);
						deviceService.saveDevice(device);
						JSONObject obj = new JSONObject();
						obj.put("deviceId", device.getId());
						JsonResult.JsonResultInfo(response, obj);
					}
				} else {
					JsonResult.JsonResultError(response, 1003);
				}
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal根据设备唯一标示查询设备信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceByDeviceNo")
	public void searchDeviceByDeviceNo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String deviceNo = ParameterFilter.emptyFilter("", "deviceNo",
					request);
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (null != device.getId()) {
				JsonResult.JsonResultInfo(response, device);
			} else {
				JsonResult.JsonResultError(response, 1004);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * Epal修改设备信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("modifyDevice")
	public void modifyDevice(HttpServletRequest request,
			HttpServletResponse response, Device device) {
		try {
			if (null != device.getId()) {
				deviceService.saveDevice(device);
				JsonResult.JsonResultInfo(response, device);
			} else {
				JsonResult.JsonResultError(response, 1005);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Device表操作
	 */

	// ***********************************************************************************

	/**
	 * DeviceStudy表操作
	 */

	/**
	 * Epal根据设备唯一标示获取学习信息接口
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("searchDeviceStudyInfos")
	public void searchDeviceStudyInfos(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("deviceNo",
					ParameterFilter.emptyFilter("", "deviceNo", request));
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDeviceStudys(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal添加/修改学习信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceStudy")
	public void saveDeviceStudy(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String content = request.getParameter("content");
			String isdone = request.getParameter("isdone");
			String missionId = request.getParameter("missionId");
			String missionName = request.getParameter("missionName");
			String url = request.getParameter("url");
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (device.getId() != null) {
				DeviceStudy deviceStudy = new DeviceStudy();
				if (null != id && !"".equals(id)) {
					deviceStudy.setId(Integer.parseInt(id));
				}
				deviceStudy.setDeviceNo(device.getDeviceNo());
				deviceStudy.setEpalId(device.getEpalId());
				deviceStudy.setContent(content);
				deviceStudy.setIsdone(Integer.parseInt(isdone));
				deviceStudy.setMissionId(missionId);
				deviceStudy.setMissionName(missionName);
				deviceStudy.setUrl(url);
				deviceService.saveDeviceStudy(deviceStudy);
				JsonResult.JsonResultInfo(response, deviceStudy);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除学习信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceStudy")
	public void deleteDeviceStudy(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			DeviceStudy deviceStudy = new DeviceStudy();
			deviceStudy.setId(new Integer(id));
			deviceService.deleteDeviceStudy(deviceStudy);
			JsonResult.JsonResultInfo(response, deviceStudy);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * DeviceStudy表操作
	 */

	/**
	 * DeviceCollection表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有收藏信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceCollections")
	public void searchDeviceCollections(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("deviceNo",
					ParameterFilter.emptyFilter("", "deviceNo", request));
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDeviceCollections(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal添加/修改收藏信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceCollection")
	public void saveDeviceCollection(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String resFrom = request.getParameter("resFrom");
			String createTime = request.getParameter("createTime");
			String audioId = request.getParameter("audioId");
			String audioName = request.getParameter("audioName");
			String url = request.getParameter("url");
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (device.getId() != null) {
				DeviceCollection deviceCollection = new DeviceCollection();
				if (null != id && !"".equals(id)) {
					deviceCollection.setId(Integer.parseInt(id));
				}
				deviceCollection.setDeviceNo(device.getDeviceNo());
				deviceCollection.setEpalId(device.getEpalId());
				deviceCollection.setResFrom(resFrom);
				deviceCollection.setCreateTime(Timestamp.valueOf(createTime));
				deviceCollection.setAudioId(audioId);
				deviceCollection.setAudioName(audioName);
				deviceCollection.setUrl(url);
				deviceService.saveDeviceCollection(deviceCollection);
				JsonResult.JsonResultInfo(response, deviceCollection);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * Epal删除收藏信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceCollection")
	public void deleteDeviceCollection(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			DeviceCollection deviceCollection = new DeviceCollection();
			deviceCollection.setId(Integer.parseInt(id));
			deviceService.deleteDeviceCollection(deviceCollection);
			JsonResult.JsonResultInfo(response, deviceCollection);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * DeviceCollection表操作
	 */

	// ***********************************************************************************
	/**
	 * DeviceStory表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有故事信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceStorys")
	public void searchDeviceStorys(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("deviceNo",
					ParameterFilter.emptyFilter("", "deviceNo", request));
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDeviceStorys(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * Epal添加/修改故事信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceStory")
	public void saveDeviceStory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String storyId = request.getParameter("storyId");
			String storyName = request.getParameter("storyName");
			String authorId = request.getParameter("authorId");
			String uploadTime = request.getParameter("uploadTime");
			String publicTime = request.getParameter("publicTime");
			String url = request.getParameter("url");
			String playTimes = request.getParameter("playTimes");
			String positiveRate = request.getParameter("positiveRate");
			String comment = request.getParameter("comment");
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (device.getId() != null) {
				DeviceStory deviceStory = new DeviceStory();
				if (null != id && !"".equals(id)) {
					deviceStory.setId(Integer.parseInt(id));
				}
				deviceStory.setDeviceNo(device.getDeviceNo());
				deviceStory.setEpalId(device.getEpalId());
				deviceStory.setStoryId(storyId);
				deviceStory.setStoryName(storyName);
				deviceStory.setAuthorId(authorId);
				deviceStory.setUploadTime(Timestamp.valueOf(uploadTime));
				deviceStory.setPublicTime(Timestamp.valueOf(publicTime));
				deviceStory.setUrl(url);
				deviceStory.setPlayTimes(Integer.parseInt(playTimes));
				deviceStory.setPositiveRate(Float.parseFloat(positiveRate));
				deviceStory.setComment(comment);
				deviceService.saveDeviceStory(deviceStory);
				JsonResult.JsonResultInfo(response, deviceStory);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除故事信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceStory")
	public void deleteDeviceStory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String deviceStoryId = request.getParameter("id");
			DeviceStory deviceStory = new DeviceStory();
			deviceStory.setId(Integer.parseInt(deviceStoryId));
			deviceService.deleteDeviceStory(deviceStory);
			JsonResult.JsonResultInfo(response, deviceStory);
			
			
			
			
			
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}

	/**
	 * DeviceStory表操作
	 */
	// ***********************************************************************************

	/**
	 * DevicePlayRecord表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有播放记录信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDevicePlayRecords")
	public void searchDevicePlayRecords(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("deviceNo",
					ParameterFilter.emptyFilter("", "deviceNo", request));
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDevicePlayRecords(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal添加/修改播放记录信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDevicePlayRecord")
	public void saveDevicePlayRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String audioId = request.getParameter("audioId");
			String audioName = request.getParameter("audioName");
			String startTime = request.getParameter("startTime");
			String from = request.getParameter("from");
			String lastid = request.getParameter("lastid");
			String nextid = request.getParameter("nextid");
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (device.getId() != null) {
				DevicePlayRecord devicePlayRecord = new DevicePlayRecord();
				if (null != id && !"".equals(id)) {
					devicePlayRecord.setId(Integer.parseInt(id));
				}
				devicePlayRecord.setDeviceNo(device.getDeviceNo());
				devicePlayRecord.setEpalId(device.getEpalId());
				devicePlayRecord.setAudioId(audioId);
				devicePlayRecord.setAudioName(audioName);
				devicePlayRecord.setStartTime(Timestamp.valueOf(startTime));
				devicePlayRecord.setFrom(from);
				devicePlayRecord.setLastid(lastid);
				devicePlayRecord.setNextid(nextid);
				deviceService.saveDevicePlayRecord(devicePlayRecord);
				JsonResult.JsonResultInfo(response, devicePlayRecord);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除播放记录信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDevicePlayRecord")
	public void deleteDevicePlayRecord(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			DevicePlayRecord devicePlayRecord = new DevicePlayRecord();
			devicePlayRecord.setId(Integer.parseInt(id));
			deviceService.deleteDevicePlayRecord(devicePlayRecord);
			JsonResult.JsonResultInfo(response, devicePlayRecord);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * DevicePlayRecord表操作
	 */
	// ***********************************************************************************

	/**
	 * DeviceProperty表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有设置信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDevicePropertys")
	public void searchDevicePropertys(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "30");
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDevicePropertys(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal添加/修改设置信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceProperty")
	public void saveDeviceProperty(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String propertyKey = request.getParameter("propertyKey");
			String propertyValue = request.getParameter("propertyValue");
			Device device = new Device();
			device = deviceService.searchDeviceByDeviceNo(deviceNo);
			if (device.getId() != null) {
				DeviceProperty deviceProperty = new DeviceProperty();
				if (null != id && !"".equals(id)) {
					deviceProperty.setId(Integer.parseInt(id));
				}
				deviceProperty.setDeviceNo(device.getDeviceNo());
				deviceProperty.setEpalId(device.getEpalId());
				deviceProperty.setPropertyKey(propertyKey);
				deviceProperty.setPropertyValue(propertyValue);
				deviceService.saveDeviceProperty(deviceProperty);
				JsonResult.JsonResultInfo(response, deviceProperty);
			} else {
				JsonResult.JsonResultError(response, 1002);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除设置信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceProperty")
	public void deleteDeviceProperty(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			DeviceProperty deviceProperty = new DeviceProperty();
			deviceProperty.setId(Integer.parseInt(id));
			deviceService.deleteDeviceProperty(deviceProperty);
			JsonResult.JsonResultInfo(response, deviceProperty);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * DeviceProperty表操作
	 * 
	 */

	// ***********************************************************************************

	/**
	 * DeviceRelation表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有关系信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceRelations")
	public void searchDeviceRelations(HttpServletRequest request,
			HttpServletResponse response,String epalId,String epal_id) throws Exception {
		try {
			HashMap map = new HashMap();
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("1000", "pageSize", request);

			String friendId = ParameterFilter.emptyFilter("", "userId", request);
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? pageSize
					: "1000");
			
			if(epalId==null){
				epalId = epal_id;
			}
			
			map.put("epalId",epalId);
			
			Page resultPage = deviceService.searchDeviceRelations(map);
			List dataList = resultPage.getItems();
	
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	// ***********************************************************************************

	/**
	 * DeviceRelation表操作
	 */

	/**
	 * 获取用户所有绑定的设备列表
	 * 
	 * @param request
	 * @param response
	 * 带userId为APP获取自己当前所绑定的机器列表
	 * 带EpalId为机器人查寻自己当前被绑定的用户。
	 */
	@RequestMapping("searchDevicebBindList")
	public void searchDevicebBindList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String friendId = ParameterFilter.emptyFilter("", "userId", request);
			map.put("friendId",friendId);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			map.put("epalId",epalId);
			if("".equals(friendId)&&"".equals(epalId)){
				//不带参数直接返回异常信息
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 402);
				JsonResult.JsonResultInfo(response, result);
			}else{
				Page resultPage = deviceService.searchDevicebBindList(map);
				List dataList = resultPage.getItems();
				JsonResult.JsonResultInfo(response, dataList);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/**
	 * Epal添加/修改关系信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceRelation")
	public void saveDeviceRelation(HttpServletRequest request,
			HttpServletResponse response,DeviceRelation deviceRelation) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String deviceNo = ParameterFilter.emptyFilter("", "deviceNo",
					request);
			String friendId = ParameterFilter.emptyFilter("", "friendId",
					request);
			String friendName = ParameterFilter.emptyFilter("", "friendName",
					request);
			String role = ParameterFilter.emptyFilter("", "role", request);
			String isBind = ParameterFilter.emptyFilter("0", "isBind", request);
			DeviceRelation deviceRelation1 = new DeviceRelation();
			if (null != id && !"".equals(id)) {
				deviceRelation1.setId(Integer.parseInt(id));
				if(isBind.equals("1")){
					DeviceActivity deviceActivity = new DeviceActivity();
					deviceActivity.setDeviceNo(deviceNo);
					deviceActivity.setEpalId(epalId);
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String currentTime = df.format(new Date());
					deviceActivity.setActivityTime(currentTime);
					deviceService.saveDeviceActivity(deviceActivity);
				}
			}
			deviceRelation1.setDeviceNo(deviceNo);
			deviceRelation1.setEpalId(epalId);
			deviceRelation1.setFriendId(friendId);
			deviceRelation1.setFriendName(friendName);
			deviceRelation1.setRole(role);
			deviceRelation1.setIsbind(Integer.parseInt(isBind));
			deviceService.saveDeviceRelation(deviceRelation1);
			JsonResult.JsonResultInfo(response, deviceRelation1);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除关系信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceRelation")
	public void deleteDeviceRelation(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			DeviceRelation deviceRelation = new DeviceRelation();
			deviceRelation.setId(Integer.parseInt(id));
			deviceService.deleteDeviceRelation(deviceRelation);
			logger.info(id);
			JsonResult.JsonResultInfo(response, deviceRelation);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	
	/**
	 * Epal添加/修改关系信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateDeviceBindRelation")
	public void updateDeviceBindRelation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			String epalId = ParameterFilter.emptyFilter("", "epalId", request);

			String friendId = ParameterFilter.emptyFilter("", "userId",
					request);
			String isBind = ParameterFilter.emptyFilter("0", "isBind", request);
			HashMap map = new HashMap();
			map.put("epalId", epalId);
			map.put("friendId", friendId);
			map.put("isBind", isBind);
			deviceService.updateDeviceBindRelation(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/**
	 * DeviceRelation表操作
	 */

	// ***********************************************************************************
	/**
	 * DeviceSchedule表操作
	 */

	/**
	 * Epal根据设备唯一标示得到设备所有日程信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceSchedules")
	public void searchDeviceSchedules(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			map.put("page", null != page && !"".equals(page) ? page : "1");
			map.put("pageSize", null != pageSize && !"".equals(pageSize) ? page
					: "1000");
			map.put("epalId",
					ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = deviceService.searchDeviceSchedules(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 机器人保存历史日程
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveHistorySchedules")
	public void saveHistorySchedules(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			String event = ParameterFilter.emptyFilter("", "event", request);
			String sid = ParameterFilter.emptyFilter("", "sid", request);
			String do_time = ParameterFilter.emptyFilter("", "do_time", request);
			String picture = ParameterFilter.emptyFilter("", "picture", request);
			String content = ParameterFilter.emptyFilter("", "content", request);
			String note = ParameterFilter.emptyFilter("", "note", request);
			String period = ParameterFilter.emptyFilter("", "period", request);
			String type = ParameterFilter.emptyFilter("", "type", request);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String scheduleId = ParameterFilter.emptyFilter("", "scheduleId", request);
			String title = ParameterFilter.emptyFilter("", "title", request);
			String exe_time = ParameterFilter.emptyFilter("", "exe_time", request);
			String description = ParameterFilter.emptyFilter("", "description", request);
			HistorySchedules historySchedules = new HistorySchedules();
			historySchedules.setEvent(event);
			historySchedules.setSid(sid);
			historySchedules.setDoTime(do_time);
			historySchedules.setPicture(picture);
			historySchedules.setContent(content);
			historySchedules.setNote(note);
			historySchedules.setPeriod(period);
			historySchedules.setType(type);
			historySchedules.setEpalId(epalId);
			historySchedules.setScheduleId(scheduleId);
			historySchedules.setTitle(title);
			historySchedules.setExeTime(Long.parseLong(exe_time));
			historySchedules.setDescription(description);
			deviceService.saveHistorySchedules(historySchedules);
			JsonResult.JsonResultInfo(response, historySchedules);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveUploadFileInfo")
	public void saveUploadFileInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = ParameterFilter.emptyFilter("", "userId", request);
			String createTime = ParameterFilter.emptyFilter("", "time", request);
			String imageUrl = ParameterFilter.emptyFilter("", "imageUrl", request);
			String musicUrl = ParameterFilter.emptyFilter("", "musicUrl", request);
			String type = ParameterFilter.emptyFilter("", "type", request);
			String fileName = ParameterFilter.emptyFilter("", "fileName", request);
			String filePath = ParameterFilter.emptyFilter("", "filePath", request);
			DeviceUploadFile deviceUploadFile = new DeviceUploadFile();
			deviceUploadFile.setUserId(userId);
			deviceUploadFile.setCreateTime(createTime);
			deviceUploadFile.setImageUrl(imageUrl);
			deviceUploadFile.setMusicUrl(musicUrl);
			deviceUploadFile.setFileName(fileName);
			deviceUploadFile.setType(type);
			deviceUploadFile.setFilePath(filePath);
			deviceService.saveUploadFileInfo(deviceUploadFile);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	
	/**
	 * 获取文件列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getUploadFileList")
	public void getUploadFileList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			String userId = request.getParameter("userId");
			
			if(userId == null){
				JsonResult.JsonResultInfo(response, new ArrayList());
				return;
			}
			
			HashMap map = new HashMap();
			map.put("userId", userId);
			JSONArray dataList = deviceService.getUploadFileList(map);
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 删除上传文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteUploadFile")
	public void deleteUploadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			deviceService.deleteUploadFile(id);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 修改上传文件信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateUploadFile")
	public void updateUploadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * Epal根据设备唯一标示得到设备所有日程信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchHistorySchedules")
	public void searchHistorySchedules(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String startTime = ParameterFilter.emptyFilter("", "startTime", request);
			String endTime = ParameterFilter.emptyFilter("", "endTime", request);
			HashMap map = new HashMap();
			map.put("epalId", epalId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			JsonResult.JsonResultInfo(response, this.deviceService.searchHistorySchedules(map));
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}
	/**
	 * Epal添加/修改日程信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveDeviceSchedule")
	public void saveDeviceSchedule(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try { 
			String id = ParameterFilter.emptyFilter("", "id", request);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String deviceNo = ParameterFilter.emptyFilter("", "deviceNo", request);
			String event = ParameterFilter.emptyFilter("", "event", request);
			int groupId = Integer.parseInt(ParameterFilter.emptyFilter("1", "groupId", request));
			HashMap map = new HashMap();
			map.put("morning", "起床音乐");
			map.put("lesson_read", "英语课堂");
			map.put("lesson_morpheme", "英语磨耳朵");
			map.put("read", "百宝袋");
			map.put("book", "阅读培养");
			map.put("book1", "中文阅读一");
			map.put("book2", "中文阅读二");
			map.put("story_en", "英文故事");
			map.put("sleep", "睡前故事和儿歌");
			String eventCN = "";
			if(map.containsKey(event)){
				eventCN = map.get(event).toString();
			}else{
				eventCN = event;
			}

			String note = ParameterFilter.emptyFilter("", "note", request);
			String content = ParameterFilter.emptyFilter("", "content", request);
			String period = ParameterFilter.emptyFilter("", "period", request);
			String type = ParameterFilter.emptyFilter("", "type", request);
			String doTime = ParameterFilter.emptyFilter("", "doTime", request);
			String state = ParameterFilter.emptyFilter("0", "state", request);
			String isDef = ParameterFilter.emptyFilter("0", "isDef", request);
			String catalogFile = ParameterFilter.emptyFilter("", "catalogFile", request);
			String title = ParameterFilter.emptyFilter("", "title", request);
			String description = ParameterFilter.emptyFilter("", "description", request);
			String sid = ParameterFilter.emptyFilter("", "sid", request);
			
			DeviceSchedule deviceSchedule = new DeviceSchedule();
			if(null != epalId && !"".equals(epalId)){
				deviceSchedule.setEpalId(epalId);
				Device device = new Device();

				device = deviceService.searchDeviceByEpalId(epalId);
				if(device.getId() == null){

				}
				deviceSchedule.setDeviceNo(device.getDeviceNo());
			}else{
				Device device = new Device();
				device = deviceService.searchDeviceByDeviceNo(deviceNo);
				if(device.getId() == null){
				}
				deviceSchedule.setDeviceNo(device.getDeviceNo());
				deviceSchedule.setEpalId(device.getEpalId());
			}
			
			if(null != deviceSchedule.getDeviceNo() && !"".equals(deviceSchedule.getDeviceNo())
					&&null != deviceSchedule.getEpalId() && !"".equals(deviceSchedule.getEpalId())){
				if(doTime.contains(",")){ //如果doTime中间发现逗号，则是批量添加
					String[] doTimes = doTime.split(",");
					for(int i=0;i<doTimes.length;i++){
						deviceSchedule.setEvent(event);
						deviceSchedule.setEventCN(eventCN);
						deviceSchedule.setContent(content);
						deviceSchedule.setPeriod(period);
						deviceSchedule.setType(type);
						deviceSchedule.setNote(note);
						deviceSchedule.setDoTime(doTimes[i]);
						deviceSchedule.setState(Integer.parseInt(state));
						deviceSchedule.setIsDef(Integer.parseInt(isDef));
						deviceSchedule.setCatalogFile(catalogFile);
						deviceSchedule.setTitle(title);
						deviceSchedule.setSid(sid);
						deviceSchedule.setDescription(description);
						deviceSchedule.setGroupId(groupId);
						deviceService.saveDeviceSchedule(deviceSchedule);
						deviceSchedule.setId(null);
					}
					JSONObject result = new JSONObject();
					result.put("msg", "batch add success!");
					result.put("code", 200);
					JsonResult.JsonResultInfo(response, result);
				}else{
					if (null != id && !"".equals(id)) {
						deviceSchedule.setId(Integer.parseInt(id));
					}
					deviceSchedule.setEvent(event);
					deviceSchedule.setEventCN(eventCN);
					deviceSchedule.setContent(content);
					deviceSchedule.setPeriod(period);
					deviceSchedule.setType(type);
					deviceSchedule.setNote(note);
					deviceSchedule.setDoTime(String.valueOf(doTime));
					deviceSchedule.setState(Integer.parseInt(state));
					deviceSchedule.setIsDef(Integer.parseInt(isDef));
					deviceSchedule.setCatalogFile(catalogFile);
					deviceSchedule.setTitle(title);
					deviceSchedule.setSid(sid);
					deviceSchedule.setDescription(description);
					deviceSchedule.setGroupId(groupId);
					deviceService.saveDeviceSchedule(deviceSchedule);
					JsonResult.JsonResultInfo(response, deviceSchedule);
				}
				
			}else{
				JsonResult.JsonResultError(response, 1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * Epal删除日程信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceSchedule")
	public void deleteDeviceSchedule(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			if(id.contains(",")){
				String [] ids = id.split(",");
				for(int i=0;i<ids.length;i++){
					id = ids[i];
					DeviceSchedule deviceSchedule = new DeviceSchedule();
					deviceSchedule.setId(Integer.parseInt(id));
					deviceService.deleteDeviceSchedule(deviceSchedule);
				}
				JSONObject result = new JSONObject();
				result.put("msg", "delete success!");
				result.put("code", 200);
				JsonResult.JsonResultInfo(response, result);
			}else{
				DeviceSchedule deviceSchedule = new DeviceSchedule();
				deviceSchedule.setId(Integer.parseInt(id));
				deviceService.deleteDeviceSchedule(deviceSchedule);
				JsonResult.JsonResultInfo(response, deviceSchedule);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * DeviceSchedule表操作
	 */
	// ***********************************************************************************

	
	
	/**
	 * 根据机器人的IM账号，获取孩子的兴趣爱好等信息
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("getChildrenInfoFromDevice")
	public void getChildrenInfoFromDevice(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String epalId = request.getParameter("epalId");
			MemberChildren memberChildren = deviceService.getChildrenInfoFromDevice(epalId);

			JsonResult.JsonResultInfo(response, memberChildren);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * 检测机器人是否存在
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("checkDeviceExist")
	public void checkDeviceExist(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		try {
			String epalId = request.getParameter("epalId");
			Device device = deviceService.searchDeviceByEpalId(epalId);
			
			if(null != device.getEpalId() && !"".equals(device.getEpalId())){
				result.put("epalId", epalId);
				result.put("status", 1);
				result.put("deviceInfo", device);
				JsonResult.JsonResultInfo(response, result);
			}else{
				result.put("epalId", epalId);
				result.put("status", 0);
				result.put("deviceInfo", device);
				JsonResult.JsonResultInfo(response, result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("epalId", "");
			result.put("status", 0);
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 上传机器人播放列表
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("uploadSoundPlayHistoryFromDevice")
	public void uploadSoundPlayHistoryFromDevice(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String epalId = request.getParameter("epalId");
			String SoundIds = request.getParameter("SoundIds");
			String playType = ParameterFilter.emptyFilter("bigV", "playType", request);
			if(null!=epalId && null!=SoundIds){
				String [] ids = SoundIds.split(",");
				for(int i = 0 ; i < ids.length ; i++){
			           String soundId = ids[i];
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
						String data = df.format(new Date());
						Date currentTime = df.parse(data);
						DeviceSoundPlayHistory dsph = new DeviceSoundPlayHistory();
						dsph.setEpalId(epalId);
						dsph.setSoundId(Integer.parseInt(soundId));
						dsph.setPlayType(playType);
						dsph.setInsertDate(data);
						deviceService.saveSoundPlayHistory(dsph);
			       }
				JSONObject result = new JSONObject();
				result.put("code", 200);
				result.put("msg", "ok");
				JsonResult.JsonResultInfo(response, result);
			}else{
				JSONObject result = new JSONObject();
				result.put("code", 204);
				result.put("msg", "Parameter error!");
				JsonResult.JsonResultInfo(response, result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * 获取机器人播放历史记录
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("getSoundPlayHistoryFromDevice")
	public void getSoundPlayHistoryFromDevice(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String epalId = request.getParameter("epalId");
			if(null!=epalId && !"".equals(epalId)){
				HashMap map = new HashMap();
				map.put("epalId", epalId);
				map.put("playType", ParameterFilter.emptyFilter("bigV", "playType", request));
				map.put("page", ParameterFilter.emptyFilter("1", "page", request));
				map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
				
				Page list = deviceService.getSoundPlayHistoryFromDevice(map);
				JSONObject result = new JSONObject();
				result.put("code", 200);
				result.put("msg", "ok");
				result.put("list", list);
				JsonResult.JsonResultInfo(response, result);
			}else{
				JSONObject result = new JSONObject();
				result.put("msg", "No Parameter!");
				result.put("code", 405);
				JsonResult.JsonResultInfo(response, result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/**
	 * 删除机器人信息
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceById")
	public void deleteDeviceById(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			if(null!=id && !"".equals(id)){
				deviceService.deleteDevice(Integer.parseInt(id));
				JSONObject result = new JSONObject();
				result.put("code", 200);
				result.put("msg", "ok");
				JsonResult.JsonResultInfo(response, result);
			}else{
				JSONObject result = new JSONObject();
				result.put("code", 204);
				result.put("msg", "Parameter error!");
				JsonResult.JsonResultInfo(response, result);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	/**
	 * 保存换机记录
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveReplacement")
	public void saveReplacement(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String lastEpalId = request.getParameter("lastEpalId");
			String currentEpalId = request.getParameter("currentEpalId");
			String remark = request.getParameter("remark");
			Replacement replacement = new Replacement();
			replacement.setCurrentEpalId(currentEpalId);
			replacement.setRemark(remark);
			replacement.setLastEpalId(lastEpalId);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			replacement.setCreateTime(df.format(new Date()));
			deviceService.saveReplacement(replacement);
			JsonResult.JsonResultInfo(response, replacement);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	
	/**
	 * 同步机器人数据
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveSynchronousData")
	public void saveSynchronousData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String oldEpalId = request.getParameter("oldEpalId");
			String newEpalId = request.getParameter("newEpalId");
			String online = ParameterFilter.emptyFilter("","online",request);
			String id = ParameterFilter.emptyFilter("","id",request);
			String deviceBookStack = ParameterFilter.emptyFilter("","deviceBookStack",request);
			HashMap map = new HashMap();
			map.put("oldEpalId", oldEpalId);
			map.put("online", online);
			map.put("deviceBookStack", deviceBookStack);
			map.put("newEpalId", newEpalId);
			map.put("id", id);
			deviceService.saveSynchronousData(map);
			
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}	
	/**
	 * 获取机器人总数量
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getDeviceCount")
	public void getDeviceCount(HttpServletRequest request,
			HttpServletResponse response) {
		try {

			Integer deviceCount = deviceService.getDeviceCount();
			JSONObject deviceInfo = new JSONObject();
			deviceInfo.put("deviceCount", deviceCount);
			JsonResult.JsonResultInfo(response, deviceInfo);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}	
	
	
	/**
	 * 导入机器人列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadDeviceListFile")
	public void uploadDeviceListFile(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value = "fileUpload", required = false) MultipartFile file1) {
		try {

			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}	
	
	/**
	 * 通过分类ID获取机器人列表
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("getDeviceListByCategory")
	public void getDeviceListByCategory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String categoryId = ParameterFilter.emptyFilter("", "categoryId", request);
			HashMap map = new HashMap();
			map.put("categoryId", categoryId);
			JSONArray data = this.deviceService.getDeviceListByCategory(map);
			JsonResult.JsonResultInfo(response, data);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	
	/**
	 * 通过分类ID获取机器人列表
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveDeviceCategory")
	public void saveDeviceCategory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String categoryName = ParameterFilter.emptyFilter("", "categoryName", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			DeviceCategory deviceCategory = new DeviceCategory();
			if (null!=id&&!"".equals(id)){
				deviceCategory.setId(Integer.parseInt(id));
			}else{
				deviceCategory.setId(null);
			}
			deviceCategory.setName(categoryName);
			this.deviceService.saveDeviceCategory(deviceCategory);
			JsonResult.JsonResultInfo(response, deviceCategory);

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * 通过分类ID获取机器人列表
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveDeviceNoToCategory")
	public void saveDeviceNoToCategory(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String categoryId = ParameterFilter.emptyFilter("", "categoryId", request);
			String deviceNoList = ParameterFilter.emptyFilter("", "deviceNoList", request);
			HashMap map = new HashMap();
			map.put("categoryId", categoryId);
			map.put("deviceNoList", deviceNoList);
			this.deviceService.saveDeviceNoToCategory(map);
			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
}



