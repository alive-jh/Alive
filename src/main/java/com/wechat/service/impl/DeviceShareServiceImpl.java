package com.wechat.service.impl;

import com.wechat.dao.DeviceShareDao;
import com.wechat.entity.DeviceShare;
import com.wechat.service.DeviceShareService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DeviceShareServiceImpl implements DeviceShareService {

	@Resource
	private
	DeviceShareDao deviceShareDao;
	
	public DeviceShareDao getDeviceShareDao() {
		return deviceShareDao;
	}

	public void setDeviceShareDao(DeviceShareDao deviceShareDao) {
		this.deviceShareDao = deviceShareDao;
	}
	
	@Override
	public void saveDeviceShare(DeviceShare deviceShare) {
		this.deviceShareDao.saveDeviceShare(deviceShare);
	}

	@Override
	public void deleteDeviceShare(DeviceShare deviceShare) {
		this.deviceShareDao.deleteDeviceShare(deviceShare);;
	}

	@Override
	public Page searchDeviceShares(HashMap map) {
		return this.deviceShareDao.searchDeviceShares(map);
	}

	@Override
	public DeviceShare searchDeviceShare(Integer id) {
		return this.deviceShareDao.searchDeviceShare(id);
	}

}
