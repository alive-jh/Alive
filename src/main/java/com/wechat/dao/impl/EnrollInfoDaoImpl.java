package com.wechat.dao.impl;

import com.wechat.dao.EnrollInfoDao;
import com.wechat.entity.EnrollInfo;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class EnrollInfoDaoImpl extends BaseDaoImpl<EnrollInfo> implements
	EnrollInfoDao {



	@Override
	public void add(EnrollInfo o) {
		this.save(o);
	}

	@Override
	public void delById(int id) {
		StringBuffer sql = new StringBuffer(
				"delete from EnrollInfo where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public EnrollInfo getById(int id) {
		return (EnrollInfo) this.executeHQL("from EnrollInfo where id= " + id)
				.get(0);
	}

	@Override
	public void updates(EnrollInfo o) {
		this.update(o);
	}

	@Override
	public Page<EnrollInfo> getByParam(Map<String, Object> paramMap) {
		// TO DO Auto-generated method stub
		return null;
	}

}
