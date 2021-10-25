package com.wechat.service;

import com.wechat.entity.EvalQuestion;
import com.wechat.util.Page;

public interface EvalQuestionService {

	void add(EvalQuestion o);
	void delById(int ID);
	EvalQuestion getById(int id);
	void update(EvalQuestion o);
	
	Page<EvalQuestion> getByLabelId(int labelId);
	
	int saveQuestion(Integer level, String questionText, String string);
}
