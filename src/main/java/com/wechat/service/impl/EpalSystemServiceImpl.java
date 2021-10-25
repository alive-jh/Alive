package com.wechat.service.impl;

import com.wechat.dao.EpalSystemDao;
import com.wechat.entity.EpalSystem;
import com.wechat.service.EpalSystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("epalSystemService")
public class EpalSystemServiceImpl implements EpalSystemService {

	@Resource
	private EpalSystemDao epalSystemDao;
	
	public EpalSystemDao getEpalSystemDao() {
		return epalSystemDao;
	}

	
	public void setEpalSystemDao(EpalSystemDao epalSystemDao) {
		this.epalSystemDao = epalSystemDao;
	}

	@Override
	public void saveEpalSystem(EpalSystem epalSystem) {
		
		this.epalSystemDao.saveEpalSystem(epalSystem);
		
	}

	@Override
	public void updateEpalSystem(EpalSystem epalSystem) {
		
		this.epalSystemDao.updateEpalSystem(epalSystem);
		
	}

	@Override
	public EpalSystem getEpalSystem(String epalId) {
		
		
		return this.epalSystemDao.getEpalSystem(epalId);
	}

}
