package com.wechat.service;

import com.wechat.entity.DeviceTest;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DeviceTestService {
	
	
	void saveDeviceTest(DeviceTest deviceTest);
	
	void deleteDeviceTest(Integer id);
	
	Page searchDeviceTests(HashMap map);
	
	DeviceTest searchDeviceTest(Integer id);

	DeviceTest searchDeviceTest(String deviceNo);

}
