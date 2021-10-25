package com.wechat.dao.impl;

import com.wechat.dao.DeviceTestDao;
import com.wechat.entity.DeviceTest;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class DeviceTestDaoImpl extends BaseDaoImpl implements DeviceTestDao {

	@Override
	public void saveDeviceTest(DeviceTest deviceTest) {
		this.saveOrUpdate(deviceTest);
	}

	@Override
	public void deleteDeviceTest(Integer id) {
		StringBuffer sql=new StringBuffer("delete from DeviceTest where id = "+id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceTests(HashMap map) {
		StringBuffer sql=new StringBuffer("from DeviceTest where 1 = 1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public DeviceTest searchDeviceTest(Integer id) {
		StringBuffer sql=new StringBuffer("from DeviceTest where id = "+id);
		ArrayList result=(ArrayList) this.executeHQL(sql.toString());
		DeviceTest deviceTest=new DeviceTest();
		if(result.size()>0){
			deviceTest=(DeviceTest) result.get(0);
		}
		return deviceTest;
	}

	@Override
	public DeviceTest searchDeviceTest(String deviceNo) {
		StringBuffer sql=new StringBuffer("from DeviceTest where deviceNo = " + deviceNo);
		ArrayList result=(ArrayList) this.executeHQL(sql.toString());
		DeviceTest deviceTest=new DeviceTest();
		if(result.size()>0){
			deviceTest=(DeviceTest) result.get(0);
		}
		return deviceTest;
	}


}
