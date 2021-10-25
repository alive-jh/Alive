package com.wechat.dao;

import com.wechat.entity.DeviceTest;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DeviceTestDao {
	
	
	void saveDeviceTest(DeviceTest deviceTest);
	
	void deleteDeviceTest(Integer id);
	
	Page searchDeviceTests(HashMap map);
	
	DeviceTest searchDeviceTest(Integer id);

	DeviceTest searchDeviceTest(String deviceNo);
	

}
