package com.wechat.service;

import com.wechat.entity.DeviceRelation;
import com.wechat.util.Page;

import java.util.HashMap;

public interface DeviceRelationService {
	
	
	void saveDeviceRelation(DeviceRelation deviceRelation);
	
	void deleteDeviceRelation(Integer id);
	
	Page searchDeviceRelations(HashMap map);
	
	DeviceRelation searchDeviceRelation(Integer id);

}
