package com.wechat.service;

import com.wechat.entity.EvalLabelRelation;

public interface EvalLabelRelationService {

	void add(EvalLabelRelation o);
	void delById(int id);
	EvalLabelRelation getById(int id);
	void update(EvalLabelRelation o);
}
