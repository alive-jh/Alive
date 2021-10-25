package com.wechat.service;

import com.wechat.entity.EvalLabel;
import com.wechat.util.Page;

import java.util.Map;

public interface EvalLabelService {

	void add(EvalLabel o);
	void delById(int ID);
	EvalLabel getById(int id);
	void update(EvalLabel o); 
	Page<EvalLabel> getByParam(Map<String, Object> map);
}
