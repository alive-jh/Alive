package com.wechat.service.impl;

import com.wechat.dao.CouponsDao;
import com.wechat.entity.Coupons;
import com.wechat.entity.CouponsInfo;
import com.wechat.service.CouponsService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("couponsService")
public class CouponsServiceImpl implements CouponsService{

	@Resource
	private CouponsDao couponsDao;
	
	
	public CouponsDao getCouponsDao() {
		return couponsDao;
	}


	public void setCouponsDao(CouponsDao couponsDao) {
		this.couponsDao = couponsDao;
	}


	@Override
	public void deleteCoupons(String id) {
		
		this.couponsDao.deleteCoupons(id);
	}

	
	@Override
	public void saveCoupons(Coupons coupons) {
		
		this.couponsDao.saveCoupons(coupons);
	}

	
	@Override
	public void saveCouponsInfo(CouponsInfo CouponsInfo) {
		
		this.couponsDao.saveCouponsInfo(CouponsInfo);
	}

	
	@Override
	public Page searchCoupons(HashMap map) {
		
		return this.couponsDao.searchCoupons(map);
	}

	
	@Override
	public List searchCouponsList(HashMap map) {
		
		return this.couponsDao.searchCouponsList(map);
	}

	
	@Override
	public void updateCouponsInfoStatus(String id) {
		
		this.couponsDao.updateCouponsInfoStatus(id);
	}


	
	@Override
	public List getCoupons(String memberId,String couponsId) {
		
		return this.couponsDao.getCoupons(memberId,couponsId);
	}


	@Override
	public void updateCouponsInfoEndDateStatus() {
	
		this.couponsDao.updateCouponsInfoEndDateStatus();
		
	}


	
	@Override
	public Coupons getCoupons(String id) {
		
		
		return this.couponsDao.getCoupons(id);
	}

}
