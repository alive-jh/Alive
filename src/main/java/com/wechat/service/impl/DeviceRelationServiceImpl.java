package com.wechat.service.impl;

import com.wechat.dao.DeviceRelationDao;
import com.wechat.entity.DeviceRelation;
import com.wechat.service.DeviceRelationService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DeviceRelationServiceImpl implements DeviceRelationService {

	@Resource
	private
	DeviceRelationDao deviceRelationDao;
	
	@Override
	public void saveDeviceRelation(DeviceRelation deviceRelation) {
		this.deviceRelationDao.saveDeviceRelation(deviceRelation);
	}

	@Override
	public void deleteDeviceRelation(Integer id) {
		this.deviceRelationDao.deleteDeviceRelation(id);
	}

	@Override
	public Page searchDeviceRelations(HashMap map) {
		return this.deviceRelationDao.searchDeviceRelations(map);
	}

	public DeviceRelationDao getDeviceRelationDao() {
		return deviceRelationDao;
	}

	public void setDeviceRelationDao(DeviceRelationDao deviceRelationDao) {
		this.deviceRelationDao = deviceRelationDao;
	}

	@Override
	public DeviceRelation searchDeviceRelation(Integer id) {
		return this.deviceRelationDao.searchDeviceRelation(id);
	}


}
