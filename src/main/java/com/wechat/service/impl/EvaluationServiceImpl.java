package com.wechat.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.dao.EvaluationDao;
import com.wechat.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService{

	@Resource
	EvaluationDao evaluationDao;

	@Override
	public List<Object[]> getQuestionLabels() {
		// TODO Auto-generated method stub
		return evaluationDao.getQuestionLabels();
	}

	@Override
	public List<Object[]> getQuestionTypes() {
		// TODO Auto-generated method stub
		return evaluationDao.getQuestionTypes();
	}
}
