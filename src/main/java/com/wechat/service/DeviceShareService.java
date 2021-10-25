package com.wechat.service;

import com.wechat.entity.DeviceShare;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DeviceShareService {
	
	
	void saveDeviceShare(DeviceShare deviceShare);
	
	void deleteDeviceShare(DeviceShare deviceShare);
	
	Page searchDeviceShares(HashMap map);
	
	DeviceShare searchDeviceShare(Integer id);

}
