package com.wechat.dao;

import com.wechat.entity.DeviceProperty;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DevicePropertyDao {
	
	
	void saveDeviceProperty(DeviceProperty deviceProperty);
	
	void deleteDeviceProperty(Integer id);
	
	Page searchDevicePropertys(HashMap map);
	
	DeviceProperty searchDeviceProperty(Integer id);
	

}
