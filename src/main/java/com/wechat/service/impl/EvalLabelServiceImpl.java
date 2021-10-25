package com.wechat.service.impl;

import com.wechat.dao.EvalLabelDao;
import com.wechat.entity.EvalLabel;
import com.wechat.service.EvalLabelService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class EvalLabelServiceImpl implements EvalLabelService{

	
	@Resource
	private EvalLabelDao selDao;
	@Override
	public void add(EvalLabel o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalLabel getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalLabel o) {
		selDao.updates(o);
		
	}

	@Override
	public Page<EvalLabel> getByParam(Map<String, Object> map) {
		return selDao.getByParam(map);
	}

}
