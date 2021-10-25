package com.wechat.service.impl;

import com.wechat.dao.EvalOptionDao;
import com.wechat.entity.EvalOption;
import com.wechat.service.EvalOptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class EvalOptionServiceImpl implements EvalOptionService {

	@Resource
	private EvalOptionDao selDao;
	@Override
	public void add(EvalOption o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalOption getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalOption o) {
		selDao.update(o);
		
	}

	@Override
	public List<EvalOption> getByQuestionId(Integer id) {
		return selDao.getByQuestionId(id).getItems();
	}
}
