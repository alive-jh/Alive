package com.wechat.service.impl;

import com.wechat.dao.DeviceHeartBeatDao;
import com.wechat.entity.DeviceHeartBeat;
import com.wechat.service.DeviceHeartBeatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


@Service
public class DeviceHeartBeatServiceImpl implements DeviceHeartBeatService {
	
	@Resource
	private DeviceHeartBeatDao deviceHeartBeatDao;
	
	@Override
	public int getDeviceHeartBeat(HashMap map) {
		return deviceHeartBeatDao.getDeviceOnlineStatus(map);
		
	}

	@Override
	public void saveDeviceHeartBeat(DeviceHeartBeat deviceHeartBeat) {
		// TODO Auto-generated method stub
		deviceHeartBeatDao.saveDeviceOnlineStatus(deviceHeartBeat);
		
	}
}
