package com.wechat.dao.impl;

import com.wechat.dao.DeviceShareDao;
import com.wechat.entity.DeviceShare;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;

@Repository
public class DeviceShareDaoImpl extends BaseDaoImpl implements DeviceShareDao {

	@Override
	public void saveDeviceShare(DeviceShare deviceShare) {
		this.saveOrUpdate(deviceShare);
	}

	@Override
	public void deleteDeviceShare(DeviceShare deviceShare) {
		StringBuffer sql=new StringBuffer("delete from DeviceShare where id = "+deviceShare.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceShares(HashMap map) {
		StringBuffer sql=new StringBuffer("from DeviceShare where 1 = 1 ");
		if(null!=map.get("name")&&!"".equals(map.get("name").toString())){
			sql.append(" and deviceNo  like '%").append(map.get("name")).append("%'");
		}
		sql.append(" order by id desc ");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page").toString()), new Integer(map.get("pageSize").toString()));
	}

	@Override
	public DeviceShare searchDeviceShare(Integer id) {
		StringBuffer sql=new StringBuffer("from DeviceShare where id = "+id);
		ArrayList result=(ArrayList) this.executeHQL(sql.toString());
		DeviceShare deviceShare=new DeviceShare();
		if(result.size()>0){
			deviceShare=(DeviceShare) result.get(0);
		}
		return deviceShare;
	}


}
