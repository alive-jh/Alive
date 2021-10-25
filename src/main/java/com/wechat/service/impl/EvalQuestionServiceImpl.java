package com.wechat.service.impl;

import com.wechat.dao.EvalLabelRelationDao;
import com.wechat.dao.EvalQuestionDao;
import com.wechat.entity.EvalLabelRelation;
import com.wechat.entity.EvalQuestion;
import com.wechat.service.EvalQuestionService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
@Service
public class EvalQuestionServiceImpl implements EvalQuestionService {

	@Resource
	private EvalQuestionDao selDao;
	
	@Resource
	private EvalLabelRelationDao relationDao;
	
	@Override
	public void add(EvalQuestion o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalQuestion getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalQuestion o) {
		selDao.updates(o);
		
	}

	@Override
	public Page<EvalQuestion> getByLabelId(int labelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("labelId",labelId);
		Page<EvalLabelRelation> lr =  relationDao.getByParmMap(map);
		return selDao.getbyIds(lr.getItems());
	}

	@Override
	public int saveQuestion(Integer level, String questionText, String file) {
		// TODO Auto-generated method stub
		return selDao.saveQuestion(level,questionText,file);
	}

	
}
