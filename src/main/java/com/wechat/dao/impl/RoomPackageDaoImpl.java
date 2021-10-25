package com.wechat.dao.impl;

import com.wechat.dao.RoomPackageDao;
import com.wechat.entity.RoomPackage;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RoomPackageDaoImpl extends BaseDaoImpl<RoomPackage> implements
		RoomPackageDao {

	@Override
	public void add(RoomPackage roomPackage) {
		this.save(roomPackage);
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from room_package where id = " + id);
		this.executeSQL(sql.toString());
	}

	@Override
	public RoomPackage getById(int id) {
		List list = this.executeHQL("from RoomPackage where id= " + id);
		if(list.size() == 0)
			return null;
		return (RoomPackage)list.get(0);
	}

	@Override
	public Page<RoomPackage> getByParam(Map<String, Object> paramMap) {
		return null;
	}

	@Override
	public void updates(RoomPackage roomPackage) {
		this.update(roomPackage);
	}
}
