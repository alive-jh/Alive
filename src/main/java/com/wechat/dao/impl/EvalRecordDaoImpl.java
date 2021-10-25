package com.wechat.dao.impl;

import com.wechat.dao.EvalRecordDao;
import com.wechat.entity.EvalRecord;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;
@Repository
public class EvalRecordDaoImpl extends BaseDaoImpl<EvalRecord> implements EvalRecordDao{

	@Override
	public Page<EvalRecord> getByUserID(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void add(EvalRecord o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		this.delete(id);
		
	}

	@Override
	public EvalRecord getById(int id) {
		return (EvalRecord)this.get(id);
	}

	@Override
	public void update(EvalRecord o) {
		this.save(o);		
	}
}
