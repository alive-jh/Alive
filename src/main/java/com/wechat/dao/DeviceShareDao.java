package com.wechat.dao;

import com.wechat.entity.DeviceShare;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DeviceShareDao {
	
	
	void saveDeviceShare(DeviceShare DeviceShare);
	
	void deleteDeviceShare(DeviceShare deviceShare);
	
	Page searchDeviceShares(HashMap map);
	
	DeviceShare searchDeviceShare(Integer id);
	

}
