package com.wechat.service.impl;

import com.wechat.dao.DeviceTestDao;
import com.wechat.entity.DeviceTest;
import com.wechat.service.DeviceTestService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DeviceTestServiceImpl implements DeviceTestService {

	@Resource
	private
	DeviceTestDao deviceTestDao;
	
	public DeviceTestDao getDeviceTestDao() {
		return deviceTestDao;
	}

	public void setDeviceTestDao(DeviceTestDao deviceTestDao) {
		this.deviceTestDao = deviceTestDao;
	}
	
	@Override
	public void saveDeviceTest(DeviceTest deviceTest) {
		this.deviceTestDao.saveDeviceTest(deviceTest);
	}

	@Override
	public void deleteDeviceTest(Integer id) {
		this.deviceTestDao.deleteDeviceTest(id);
	}

	@Override
	public Page searchDeviceTests(HashMap map) {
		return this.deviceTestDao.searchDeviceTests(map);
	}

	@Override
	public DeviceTest searchDeviceTest(Integer id) {
		return this.deviceTestDao.searchDeviceTest(id);
	}

	@Override
	public DeviceTest searchDeviceTest(String deviceNo) {
		return this.deviceTestDao.searchDeviceTest(deviceNo);
	}


}
