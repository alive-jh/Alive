package com.wechat.service;

import com.wechat.entity.DeviceProperty;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DevicePropertyService {
	
	
	void saveDeviceProperty(DeviceProperty deviceProperty);
	
	void deleteDeviceProperty(Integer id);
	
	Page searchDevicePropertys(HashMap map);
	
	DeviceProperty searchDeviceProperty(Integer id);

}
