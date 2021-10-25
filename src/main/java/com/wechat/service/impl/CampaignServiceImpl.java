package com.wechat.service.impl;

import com.wechat.dao.EnrollInfoDao;
import com.wechat.entity.EnrollInfo;
import com.wechat.service.CampaignService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CampaignServiceImpl implements CampaignService{

	@Resource
	private EnrollInfoDao enrollInfoDao;

	@Override
	public void saveEnrollInfo(EnrollInfo i) {
		enrollInfoDao.add(i);		
	}
	
	

}
