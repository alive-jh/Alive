package com.wechat.service;

import com.wechat.entity.EvalTemplate;
import com.wechat.util.Page;

import java.util.List;
import java.util.Map;

public interface EvalTemplateService {

	void add(EvalTemplate o);
	void delById(int ID);
	EvalTemplate getById(int id);
	void update(EvalTemplate o); 
	Page<EvalTemplate> getByParam(Map<String, Object> map);
	
	EvalTemplate getTemplateByScore(List<EvalTemplate> templates, int score);
	Page<EvalTemplate> getByTIdAndGId(int teacherId, int classGradeId);

}
