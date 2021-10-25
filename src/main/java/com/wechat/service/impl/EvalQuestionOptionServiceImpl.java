package com.wechat.service.impl;

import com.wechat.dao.EvalQuestionOptionDao;
import com.wechat.entity.EvalQuestionOption;
import com.wechat.service.EvalQuestionOptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class EvalQuestionOptionServiceImpl implements EvalQuestionOptionService {

	@Resource
	private EvalQuestionOptionDao selDao;
	@Override
	public void add(EvalQuestionOption o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalQuestionOption getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalQuestionOption o) {
		selDao.update(o);
		
	}
}
