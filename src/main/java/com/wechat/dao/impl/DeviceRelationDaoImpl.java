package com.wechat.dao.impl;

import com.wechat.dao.DeviceRelationDao;
import com.wechat.entity.DeviceRelation;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class DeviceRelationDaoImpl extends BaseDaoImpl implements DeviceRelationDao {

	@Override
	public void saveDeviceRelation(DeviceRelation deviceRelation) {
		this.saveOrUpdate(deviceRelation);
	}

	@Override
	public void deleteDeviceRelation(Integer id) {
		StringBuffer sql=new StringBuffer("delete from DeviceRelation where id = "+id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceRelations(HashMap map) {
		StringBuffer sql=new StringBuffer("from DeviceRelation where 1 = 1 ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("rowsPerPage").toString()));
	}

	@Override
	public DeviceRelation searchDeviceRelation(Integer id) {
		StringBuffer sql=new StringBuffer("from DeviceRelation where id = "+id);
		ArrayList result=(ArrayList) this.executeHQL(sql.toString());
		DeviceRelation deviceRelation=new DeviceRelation();
		if(result.size()>0){
			deviceRelation=(DeviceRelation) result.get(0);
		}
		return deviceRelation;
	}


}
