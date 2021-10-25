package com.wechat.dao.impl;


import com.wechat.dao.EvalQuestionOptionDao;
import com.wechat.entity.EvalQuestionOption;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
@Repository
public class EvalQuestionOptionDaoImpl extends BaseDaoImpl<EvalQuestionOption> implements EvalQuestionOptionDao{

	
	@Override
	public Page<EvalQuestionOption> getByQuestionId(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void add(EvalQuestionOption o) {
		this.save(o);		
	}

	@Override
	public void delById(int id) {
		this.delete(id);
		
	}

	@Override
	public EvalQuestionOption getById(int id) {
		return (EvalQuestionOption)this.get(id);
	}

	@Override
	public void update(EvalQuestionOption o) {
		this.save(o);		
	}


	@Override
	public Page<EvalQuestionOption> pageQuery(String string, int i, int j) {
		return this.pageQueryByHql(string, i, j);
	}
	


}
