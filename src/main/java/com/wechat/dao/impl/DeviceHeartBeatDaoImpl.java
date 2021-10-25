package com.wechat.dao.impl;

import com.wechat.dao.DeviceHeartBeatDao;
import com.wechat.entity.DeviceHeartBeat;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class DeviceHeartBeatDaoImpl extends BaseDaoImpl implements DeviceHeartBeatDao {

	@Override
	public void saveDeviceOnlineStatus(DeviceHeartBeat deviceHeartBeat) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceHeartBeat);
	}



	@Override
	public int getDeviceOnlineStatus(HashMap map) {
		// TODO Auto-generated method stub
		String epalId = (String) map.get("epalId");
		String sql = "select active_time from device_heart_beat where epal_id ='"+ epalId +"' order by id desc  ";
		List list = this.executeSQL(sql);
		if(list.size()>0)
		{
			String code = list.get(0).toString();
			long currenttime = System.currentTimeMillis();
			long temp = currenttime - Long.parseLong(code);
			if (temp > 2*60*1000){
				return 0;
			}else{
				return 1;
			}
		}else{
			return 0;
		}

	}

}
