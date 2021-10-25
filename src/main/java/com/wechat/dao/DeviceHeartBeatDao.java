package com.wechat.dao;

import com.wechat.entity.DeviceHeartBeat;

import java.util.HashMap;


public interface DeviceHeartBeatDao {
	
	void saveDeviceOnlineStatus(DeviceHeartBeat deviceHeartBeat);
	
	int getDeviceOnlineStatus(HashMap map);
}
