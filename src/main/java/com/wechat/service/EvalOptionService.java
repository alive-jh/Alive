package com.wechat.service;

import com.wechat.entity.EvalOption;

import java.util.List;

public interface EvalOptionService {

	void add(EvalOption o);
	void delById(int ID);
	EvalOption getById(int id);
	void update(EvalOption o);
	List<EvalOption> getByQuestionId(Integer id);
}
