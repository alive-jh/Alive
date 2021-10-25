package com.wechat.dao;

import com.wechat.entity.EvalQuestionOption;
import com.wechat.util.Page;

import java.util.HashMap;

public interface EvalQuestionOptionDao {

	
	Page<EvalQuestionOption> getByQuestionId(HashMap<String, String> map);
	
	void add(EvalQuestionOption o);
	void delById(int ID);
	EvalQuestionOption getById(int id);
	void update(EvalQuestionOption o);

	Page<EvalQuestionOption> pageQuery(String string, int i, int j);
}
