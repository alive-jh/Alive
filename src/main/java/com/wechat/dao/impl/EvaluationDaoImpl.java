package com.wechat.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wechat.dao.EvaluationDao;

@Repository
public class EvaluationDaoImpl extends BaseDaoImpl implements EvaluationDao{

	@Override
	public List<Object[]> getQuestionLabels() {
		return this.createSQLQuery	("select a.id,a.name from eval_label a where a.status>0").list();
	}

	@Override
	public List<Object[]> getQuestionTypes() {
		// TODO Auto-generated method stub
		return this.createSQLQuery("select id ,text from sys_dict where pid = 185").list();
	}

	
}
