package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.DeviceShare;
import com.wechat.service.DeviceShareService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("device")
public class FileController {

	@Resource
	private DeviceShareService deviceShareService;

	public DeviceShareService getDeviceShareService() {
		return deviceShareService;
	}

	public void setDeviceShareService(DeviceShareService deviceShareService) {
		this.deviceShareService = deviceShareService;
	}
	

	@RequestMapping("getDeviceSharesInfo")
	public String getDevicesInfo(HttpServletRequest request,
			HttpServletResponse response, QueryDto queryDto) {
		
		HashMap map = new HashMap();
		map.put("page", queryDto.getPage());
		map.put("pageSize", queryDto.getPageSize());
		map.put("name", queryDto.getName());
		Page resultPage = deviceShareService.searchDeviceShares(map);
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("queryDto", queryDto);
		ArrayList deviceShares=(ArrayList) resultPage.getItems();
		JSONArray array=new JSONArray();
		for (int i = 0; i < deviceShares.size(); i++) {
			DeviceShare deviceShare=(DeviceShare) deviceShares.get(i);
			JSONObject object=new JSONObject();
			object.put("id", deviceShare.getId());
			object.put("shareTitle", deviceShare.getShareTitle());
			object.put("shareUrl", deviceShare.getShareUrl());
			object.put("deviceNo", deviceShare.getDeviceNo());
			object.put("fileType", deviceShare.getFileType());
			array.add(object);
		}
		request.setAttribute("jsonData", array.toString());
		
		return "device/fileShareManager";
		
	}

	

}
