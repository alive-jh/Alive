package com.wechat.dao;

import com.wechat.entity.EvalLabelRelation;
import com.wechat.util.Page;

import java.util.Map;

public interface EvalLabelRelationDao {

	Page<EvalLabelRelation> getByParmMap(Map<String, Object> map);
	
	
	void add(EvalLabelRelation o);
	void delById(int ID);
	EvalLabelRelation getById(int id);
	void update(EvalLabelRelation o);
}
