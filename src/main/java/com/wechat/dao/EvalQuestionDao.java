package com.wechat.dao;

import com.wechat.entity.EvalLabelRelation;
import com.wechat.entity.EvalQuestion;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EvalQuestionDao {


	Page<EvalQuestion> getByLabelorGroup(HashMap<String, String> map);
	void add(EvalQuestion o);
	void delById(int ID);
	EvalQuestion getById(int id);
	void updates(EvalQuestion o);
	
	Page<EvalQuestion> getByParmMap(Map<String, Object> map);
	Page<EvalQuestion> getbyIds(List<EvalLabelRelation> items);
	
	int saveQuestion(Integer level, String questionText, String file);
}
