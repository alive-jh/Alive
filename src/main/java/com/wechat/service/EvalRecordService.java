package com.wechat.service;

import com.wechat.entity.EvalRecord;

import java.util.Map;

public interface EvalRecordService {

	void add(EvalRecord o);
	void delById(int ID);
	EvalRecord getById(int id);
	void update(EvalRecord o);
	
	int scoring(Map<String, Integer> map);
}
