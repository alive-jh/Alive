package com.wechat.controller;

import com.wechat.entity.Device;
import com.wechat.entity.DeviceFile;
import com.wechat.entity.DeviceShare;
import com.wechat.entity.FileCach;
import com.wechat.qiniu.QiniuUtil;
import com.wechat.service.DeviceService;
import com.wechat.service.DeviceShareService;
import com.wechat.service.FileCachService;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class FileApiController {

	@Resource
	private FileCachService filecachservice;
	
	@Resource
	private DeviceShareService deviceShareService;

	@Resource
	private DeviceService deviceService;

	public DeviceShareService getDeviceShareService() {
		return deviceShareService;
	}

	public void setDeviceShareService(DeviceShareService deviceShareService) {
		this.deviceShareService = deviceShareService;
	}
	
	
	/**
	 * 获取七牛上传文件Uptoken
	 */
	@RequestMapping("getUptoken")
	public void getUptoken(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String myKey=ParameterFilter.emptyFilter("temp"+Math.random(), "fileName", request);
		String bucketName=ParameterFilter.emptyFilter("words", "bucketName", request);
		response.getWriter().write(
				"{\"uptoken\":\"" + new QiniuUtil().getUpToken(myKey,bucketName) + "\"}");
	}

	/**
	 * 机器人上传分享音频（待废弃,2017-08-19）
	 * 
	 * @param upFile
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadShareFile")
	public void uploadShareFile(
			@RequestParam(value = "upFile", required = false) MultipartFile upFile,
			HttpServletRequest request, HttpServletResponse response) {

		PrintWriter writer = null;

		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String deviceNo = request.getParameter("deviceNo");
			String fileType = request.getParameter("fileType");
			String createTime = request.getParameter("createTime");
			String shareTitle = request.getParameter("shareTitle");
			String epalId = request.getParameter("epalId");
			DeviceShare deviceShare = new DeviceShare();
			String epalFilePath = Config.getProperty("EPAL_FILE_PATH");
			//System.out.println("epalFilePath=========" + epalFilePath);
			String path = epalFilePath + deviceNo + "/";
			// String path = epalFilePath + deviceNo + "/";
			/** 获取文件的后缀 **/
			String suffix = upFile.getOriginalFilename().substring(
					upFile.getOriginalFilename().lastIndexOf("."));
			if (suffix.equalsIgnoreCase(".mp3")
					|| suffix.equalsIgnoreCase(".mp4")) {
				/** 使用UUID生成文件名称 **/
				String fileName = UUID.randomUUID().toString() + suffix;
				//System.out.println(path);
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				upFile.transferTo(targetFile);
				String shareUrl = Keys.STAT_NAME + "wechat/share/" + deviceNo
						+ "/" + fileName;
				deviceShare.setPath(path + fileName);
				deviceShare.setShareTitle(shareTitle);
				deviceShare.setDeviceNo(deviceNo);
				deviceShare.setShareUrl(shareUrl);
				Timestamp shareTime = Timestamp.valueOf(createTime);
				deviceShare.setCreateTime(shareTime);
				deviceShare.setFileType(fileType);
				deviceShare.setEpalId(epalId);
				deviceShareService.saveDeviceShare(deviceShare);
				result.put("success", 1);
				JSONObject data = new JSONObject();
				data.put("deviceId", deviceShare.getId());
				data.put("deviceNo", deviceShare.getDeviceNo());
				data.put("shareUrl", shareUrl);
				result.put("data", data);
				writer.print(result.toString());
			}
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}

	}

	
	/**
	 * 机器人上传分享音频（新）
	 * 
	 * @param upFile
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadShareFileUrl")
	public void uploadShareFileUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			String deviceNo = request.getParameter("deviceNo");
			String fileType = request.getParameter("fileType");
			String createTime = request.getParameter("createTime");
			String shareTitle = request.getParameter("shareTitle");
			String epalId = request.getParameter("epalId");
			String shareUrl = request.getParameter("shareUrl");
			String id = ParameterFilter.emptyFilter("", "id", request);
			
			DeviceShare deviceShare = new DeviceShare();
			deviceShare.setShareTitle(shareTitle);
			deviceShare.setDeviceNo(deviceNo);
			deviceShare.setShareUrl(shareUrl);
			Timestamp shareTime = Timestamp.valueOf(createTime);
			deviceShare.setCreateTime(shareTime);
			deviceShare.setFileType(fileType);
			deviceShare.setEpalId(epalId);
			if(null!=id&&!"".equals(id)){
				deviceShare.setId(Integer.parseInt(id));
			}
			deviceShareService.saveDeviceShare(deviceShare);
			JsonResult.JsonResultInfo(response, deviceShare);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * Epal添加上传文件接口(废弃：2017-08-19)
	 * 
	 * @param upFile
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadDeviceFile")
	public void uploadDeviceFile(
			@RequestParam(value = "upFile", required = false) MultipartFile upFile,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String filePath = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");
			String createTime = request.getParameter("createTime");
			String epalId = request.getParameter("epalId");
			String duration = request.getParameter("duration");
			String source = ParameterFilter.emptyFilter("", "source", request);
			DeviceFile deviceFile = new DeviceFile();
			String epalFilePath = Config.getProperty("EPAL_CUSTOM_PATH");
			String path = epalFilePath + epalId + "/";
			
			/** 获取文件的后缀 **/
			String suffix = upFile.getOriginalFilename().substring(
					upFile.getOriginalFilename().lastIndexOf("."));
			if (suffix.equalsIgnoreCase(".fandou")
					|| suffix.equalsIgnoreCase(".jpg")
					|| suffix.equalsIgnoreCase(".amr")
					|| suffix.equalsIgnoreCase(".mp3")
					|| suffix.equalsIgnoreCase(".mp4")
					|| suffix.equalsIgnoreCase(".wav")
					|| suffix.equalsIgnoreCase(".m4a")
					|| suffix.equalsIgnoreCase(".txt")) {
				/** 使用UUID生成文件名称 **/
				String uuName = UUID.randomUUID().toString() + suffix;
				File targetFile = new File(path, uuName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				upFile.transferTo(targetFile);
				String fileUrl = Keys.STAT_NAME + "wechat/file/" + epalId + "/"
						+ uuName;
				deviceFile.setPath(path + uuName);
				deviceFile.setFileUrl(fileUrl);
				Timestamp shareTime = Timestamp.valueOf(createTime);
				deviceFile.setCreateTime(shareTime);
				deviceFile.setEpalId(epalId);
				deviceFile.setFilePath(filePath);
				deviceFile.setFileName(fileName);
				deviceFile.setDuration(duration);
				deviceFile.setSource(source);
				deviceService.saveDeviceFile(deviceFile);
				result.put("success", 1);
				JSONObject data = new JSONObject();
				data.put("fileName", deviceFile.getFileName());
				data.put("deviceId", deviceFile.getId());
				data.put("fileUrl", fileUrl);
				data.put("duration", duration);
				data.put("source", source);
				result.put("data", data);
				
				writer.print(result.toString());
			}
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}

	}
	
	

	/**
	 * Epal添加上传文件接口(新)
	 * 
	 * @param upFile
	 * @param request
	 * @param response
	 */
	@RequestMapping("uploadDeviceFileUrl")
	public void uploadDeviceFileUrl(HttpServletRequest request, HttpServletResponse response) {
		try {
			String filePath = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");
			String createTime = request.getParameter("createTime");
			String epalId = request.getParameter("epalId");
			String duration = request.getParameter("duration");
			String source = ParameterFilter.emptyFilter("", "source", request);
			String fileUrl = ParameterFilter.emptyFilter("", "fileUrl", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			DeviceFile deviceFile = new DeviceFile();
			deviceFile.setPath(fileUrl);
			deviceFile.setFileUrl(fileUrl);
			Timestamp shareTime = Timestamp.valueOf(createTime);
			deviceFile.setCreateTime(shareTime);
			deviceFile.setEpalId(epalId);
			deviceFile.setFilePath(filePath);
			deviceFile.setFileName(fileName);
			deviceFile.setDuration(duration);
			deviceFile.setSource(source);
			if(null!=id&&!"".equals(id)){
				deviceFile.setId(Integer.parseInt(id));
			}
			deviceService.saveDeviceFile(deviceFile);
			JsonResult.JsonResultInfo(response, deviceFile);
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	/**
	 * 根据epalId或者deviceId修改上传文件信息(废弃，统一用添加接口：2017-08-19)
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateDeviceFile")
	public void updateDeviceFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id  = ParameterFilter.emptyFilter("", "id", request);
			String filePath = ParameterFilter.emptyFilter("", "filePath", request);
			String path = ParameterFilter.emptyFilter("", "path", request);
			String fileName = ParameterFilter.emptyFilter("", "fileName", request);
			String createTime = ParameterFilter.emptyFilter("", "createTime", request);
			String fileUrl = ParameterFilter.emptyFilter("", "fileUrl", request);
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String duration = ParameterFilter.emptyFilter("", "duration", request);
			String source = ParameterFilter.emptyFilter("", "source", request);
			DeviceFile deviceFile = new DeviceFile();
			Timestamp shareTime = Timestamp.valueOf(createTime);
			deviceFile.setCreateTime(shareTime);
			deviceFile.setDuration(duration);
			deviceFile.setEpalId(epalId);
			deviceFile.setFileName(fileName);
			deviceFile.setFilePath(filePath);
			deviceFile.setFileUrl(fileUrl);
			if(null!=id&&!"".equals(id)){
				deviceFile.setId(Integer.parseInt(id));
			}
			deviceFile.setPath(path);
			deviceFile.setSource(source);
			deviceService.saveDeviceFile(deviceFile);
			JsonResult.JsonResultInfo(response, deviceFile);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	

	/**
	 * 根据epalId或者deviceId获取账号下所有上传文件列表
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchDeviceFile")
	public void searchDeviceFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			map.put("epalId",ParameterFilter.emptyFilter("", "epalId", request));
			map.put("source",ParameterFilter.emptyFilter("", "source", request));
			Page resultPage = deviceService.searchDeviceFiles(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * Epal修改设备上传文件信息(废弃，使用添加接口：2017-08-19)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("modifyShareFileTitle")
	public void modifyShareFileTitle(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String id = request.getParameter("id");
			String deviceNo = request.getParameter("deviceNo");
			String shareTitle = request.getParameter("shareTitle");
			String fileType = request.getParameter("fileType");
			DeviceShare deviceShare = new DeviceShare();
			if (!"".equals(id)) {
				deviceShare = this.deviceShareService
						.searchDeviceShare(new Integer(id));
			}
			deviceShare.setShareTitle(shareTitle);
			deviceShare.setDeviceNo(deviceNo);
			deviceShare.setFileType(fileType);
			deviceShareService.saveDeviceShare(deviceShare);
			result.put("success", 1);
			JSONObject data = new JSONObject();
			data.put("id", deviceShare.getId());
			data.put("deviceNo", deviceShare.getDeviceNo());
			data.put("shareTitle", deviceShare.getShareTitle());
			data.put("fileType", deviceShare.getFileType());
			result.put("data", data);
			writer.print(result.toString());

		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}

	}

	/**
	 * Epal删除上传语音信息接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceShare")
	public void deleteDeviceShare(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String id = request.getParameter("id");
			DeviceShare deviceShare = new DeviceShare();
			deviceShare.setId(new Integer(id));
			deviceShareService.deleteDeviceShare(deviceShare);
			FileUtil fileUtil = new FileUtil();
			fileUtil.deleteFile(deviceShare.getPath());
			result.put("success", 1);
			JSONObject data = new JSONObject();
			data.put("id", deviceShare.getId());
			result.put("data", data);
			writer.print(result.toString());
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Epal删除设备自定义文件接口
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteDeviceFile")
	public void deleteDeviceFile(HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String id = request.getParameter("id");
			DeviceFile deviceFile = new DeviceFile();
			deviceFile.setId(new Integer(id));
			deviceService.deleteDeviceFile(deviceFile);
			FileUtil fileUtil = new FileUtil();
			fileUtil.deleteFile(deviceFile.getPath());
			result.put("success", 1);
			JSONObject data = new JSONObject();
			data.put("id", deviceFile.getId());
			result.put("data", data);
			writer.print(result.toString());
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Epal根据设备唯一标示获取上传语音信息接口
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getDeviceShareInfo")
	public void getDeviceShareInfo(HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String deviceNo = request.getParameter("deviceNo");
			String page = request.getParameter("page");
			String pageSize = request.getParameter("pageSize");
			HashMap map = new HashMap();
			map.put("name", deviceNo);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page resultPage = deviceShareService.searchDeviceShares(map);
			ArrayList deviceShares = (ArrayList) resultPage.getItems();
			JSONArray array = new JSONArray();
			for (int i = 0; i < deviceShares.size(); i++) {
				DeviceShare deviceShare = (DeviceShare) deviceShares.get(i);
				JSONObject object = new JSONObject();
				object.put("id", deviceShare.getId());
				object.put("shareTitle", deviceShare.getShareTitle());
				object.put("shareUrl", deviceShare.getShareUrl());
				object.put("deviceNo", deviceShare.getDeviceNo());
				object.put("fileType", deviceShare.getFileType());
				array.add(object);
			}
			result.put("success", 1);
			result.put("data", array);
			writer.print(result.toString());
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}

	}

	/**
	 * Epal根据设备唯一标示获取设备文件信息接口列表(option filePath 文件路径)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("getDeviceFileInfo")
	public void getDeviceFileInfo(HttpServletRequest request,
			HttpServletResponse response) {

		PrintWriter writer = null;
		try {
			JSONObject result = new JSONObject();
			writer = response.getWriter();
			String epalId = request.getParameter("epalId");
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			String filePath = ParameterFilter.emptyFilter("", "filePath", request);
			HashMap map = new HashMap();
			map.put("name", epalId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			map.put("filePath", filePath);
			Page resultPage = deviceService.searchDeviceFiles(map);
			ArrayList deviceFiles = (ArrayList) resultPage.getItems();
			JsonResult.JsonResultInfo(response, deviceFiles);

		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("success", 0);
			JSONObject error = new JSONObject();
			error.put("code", 1000);
			result.put("error", error);
			writer.print(result.toString());
			e.printStackTrace();
		}

	}
	/**
	 * filecach表操作
	 */
	
	/**
	 * 上传本地文件目录信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("saveFileCach")
	public void saveFileCach(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			PrintWriter writer = null;
			StringBuilder stb = new StringBuilder();
			String s = null;
			String epalId = request.getParameter("epalId");
			String deviceNo = request.getParameter("deviceNo");
			String id = request.getParameter("id");
			String path = request.getParameter("path");
			String cachData = request.getParameter("cachData");
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currenttime=format.format(date);
			if(null != epalId && !"".equals(epalId)){
	
				Device device = new Device();
				device = deviceService.searchDeviceByEpalId(epalId);
				deviceNo = device.getDeviceNo().toString();
				if(device.getId() == null){
					JsonResult.JsonResultError(response, 1002);
				}
			}else{
				Device device = new Device();
				device = deviceService.searchDeviceByDeviceNo(deviceNo);
				epalId = device.getEpalId().toString();
				if(device.getId() == null){
					JsonResult.JsonResultError(response, 1002);
				}
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((s = br.readLine()) != null) {
				stb.append(s);
			}
			FileCach cach = new FileCach();
			cach.setCachData(cachData);
			cach.setDeviceNo(deviceNo);
			cach.setEpalId(epalId);
			if (null != id && !"".equals(id)) {
				cach.setId(Integer.parseInt(id));
				
			}
			cach.setPath(path);
			cach.setInsertdate(currenttime);
			cach.setUpdatedate(currenttime);
			filecachservice.saveFileCach(cach);//测试 
			
			JsonResult.JsonResultInfo(response, cach);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
		
	}


	/**
	 * 根据epalId或者deviceId获取账号下所有文件的结构
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchFileCach")
	public void searchFileCach(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String path = request.getParameter("path");
			map.put("path",path);
			map.put("epalId",ParameterFilter.emptyFilter("", "epalId", request));
			Page resultPage = filecachservice.searchFileCach(map);
			List dataList = resultPage.getItems();
			JsonResult.JsonResultInfo(response, dataList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/**
	 * 暂时不用delete方法
	 * 删除方法可以用save方法实现
	 * 
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("deleteFileCach")
	public void deleteFileCach(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			HashMap map = new HashMap();
			String path = request.getParameter("path");
			String epalId = request.getParameter("epalId");
			String id = request.getParameter("id");
			map.put("path",path);
			map.put("epalId",ParameterFilter.emptyFilter("", "epalId", request));
			filecachservice.deleteFileCach(Integer.parseInt(id));
			JsonResult.JsonResultInfo(response, 200);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}

}
