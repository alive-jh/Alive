package com.wechat.service.impl;

import com.wechat.dao.IntegralDao;
import com.wechat.entity.Integral;
import com.wechat.service.IntegralService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("integralService")
public class IntegralServiceImpl implements IntegralService {

	@Resource
	private IntegralDao integralDao;
	
	public IntegralDao getIntegralDao() {
		return integralDao;
	}

	public void setIntegralDao(IntegralDao integralDao) {
		this.integralDao = integralDao;
	}

	@Override
	public List getIntegralByMemberId(String memberId,String memberType) {
	
		return this.integralDao.getIntegralByMemberId(memberId,memberType);
	}

	@Override
	public Integer getIntegralCount(String memberId,String memberType) {
	
		return this.integralDao.getIntegralCount(memberId,memberType);
	}

	@Override
	public void saveIntegral(Integral integral) {
	
		this.integralDao.saveIntegral(integral);
	}

	@Override
	public Page searchIntegral(HashMap map) {
	
		return this.integralDao.searchIntegral(map);
	}

	
	@Override
	public List searchIntegralList(String memberId,String memberType) {
		
		return this.integralDao.searchIntegralList(memberId,memberType);
	}

}
