package com.wechat.service.impl;

import com.wechat.dao.EvalLabelRelationDao;
import com.wechat.entity.EvalLabelRelation;
import com.wechat.service.EvalLabelRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class EvalLabelRelationServiceImpl implements EvalLabelRelationService {

	@Resource
	private EvalLabelRelationDao selDao;
	@Override
	public void add(EvalLabelRelation o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalLabelRelation getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalLabelRelation o) {
		selDao.update(o);
		
	}

}
