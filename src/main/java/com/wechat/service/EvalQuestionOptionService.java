package com.wechat.service;

import com.wechat.entity.EvalQuestionOption;

public interface EvalQuestionOptionService {

	void add(EvalQuestionOption o);
	void delById(int ID);
	EvalQuestionOption getById(int id);
	void update(EvalQuestionOption o);
}
