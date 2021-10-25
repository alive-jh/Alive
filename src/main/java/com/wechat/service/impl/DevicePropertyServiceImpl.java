package com.wechat.service.impl;

import com.wechat.dao.DevicePropertyDao;
import com.wechat.entity.DeviceProperty;
import com.wechat.service.DevicePropertyService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DevicePropertyServiceImpl implements DevicePropertyService {

	@Resource
	private
	DevicePropertyDao devicePropertyDao;
	
	public DevicePropertyDao getDevicePropertyDao() {
		return devicePropertyDao;
	}

	public void setDevicePropertyDao(DevicePropertyDao devicePropertyDao) {
		this.devicePropertyDao = devicePropertyDao;
	}
	
	@Override
	public void saveDeviceProperty(DeviceProperty deviceProperty) {
		this.devicePropertyDao.saveDeviceProperty(deviceProperty);
	}

	@Override
	public void deleteDeviceProperty(Integer id) {
		this.devicePropertyDao.deleteDeviceProperty(id);
	}

	@Override
	public Page searchDevicePropertys(HashMap map) {
		return this.devicePropertyDao.searchDevicePropertys(map);
	}

	@Override
	public DeviceProperty searchDeviceProperty(Integer id) {
		return this.devicePropertyDao.searchDeviceProperty(id);
	}


}
