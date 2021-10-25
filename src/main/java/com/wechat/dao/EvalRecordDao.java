package com.wechat.dao;

import com.wechat.entity.EvalRecord;
import com.wechat.util.Page;

public interface EvalRecordDao {

	Page<EvalRecord> getByUserID(int userId);

	void add(EvalRecord o);
	void delById(int ID);
	EvalRecord getById(int id);
	void update(EvalRecord o);
}
