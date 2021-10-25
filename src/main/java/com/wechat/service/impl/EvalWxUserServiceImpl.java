package com.wechat.service.impl;

import com.wechat.dao.EvalWxUserDao;
import com.wechat.entity.EvalWxUser;
import com.wechat.service.EvalWxUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class EvalWxUserServiceImpl implements EvalWxUserService {

	@Resource
	private EvalWxUserDao selDao;
	@Override
	public void add(EvalWxUser o) {
		selDao.add(o);
		
	}

	@Override
	public void delById(int id) {
		selDao.delById(id);
		
	}

	@Override
	public EvalWxUser getById(int id) {
		return selDao.getById(id);
	}

	@Override
	public void update(EvalWxUser o) {
		selDao.update(o);
	}
}
