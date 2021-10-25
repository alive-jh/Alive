package com.wechat.dao;

import com.wechat.entity.EvalOption;
import com.wechat.util.Page;

public interface EvalOptionDao {
	Page<EvalOption> getByQuestionId(int questionId);
	void add(EvalOption o);
	void delById(int ID);
	EvalOption getById(int id);
	void update(EvalOption o);
}
