package com.wechat.dao.impl;

import com.wechat.dao.DevicePropertyDao;
import com.wechat.entity.DeviceProperty;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class DevicePropertyDaoImpl extends BaseDaoImpl implements DevicePropertyDao {

	@Override
	public void saveDeviceProperty(DeviceProperty deviceProperty) {
		this.saveOrUpdate(deviceProperty);
	}

	@Override
	public void deleteDeviceProperty(Integer id) {
		StringBuffer sql=new StringBuffer("delete from DeviceProperty where id = "+id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDevicePropertys(HashMap map) {
		StringBuffer sql=new StringBuffer("from DeviceProperty where 1 = 1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public DeviceProperty searchDeviceProperty(Integer id) {
		StringBuffer sql=new StringBuffer("from DeviceProperty where id = "+id);
		ArrayList result=(ArrayList) this.executeHQL(sql.toString());
		DeviceProperty deviceProperty=new DeviceProperty();
		if(result.size()>0){
			deviceProperty=(DeviceProperty) result.get(0);
		}
		return deviceProperty;
	}


}
