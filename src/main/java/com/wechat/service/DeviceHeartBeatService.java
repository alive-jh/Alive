package com.wechat.service;


import com.wechat.entity.DeviceHeartBeat;

import java.util.HashMap;

public interface DeviceHeartBeatService {

	
	void saveDeviceHeartBeat(DeviceHeartBeat deviceHeartBeat);
	
	int getDeviceHeartBeat(HashMap map);
}
