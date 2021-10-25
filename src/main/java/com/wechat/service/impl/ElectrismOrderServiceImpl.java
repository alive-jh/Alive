package com.wechat.service.impl;

import com.wechat.dao.ElectrismOrderDao;
import com.wechat.entity.ElectrismOrder;
import com.wechat.entity.ElectrismOrderTime;
import com.wechat.service.ElectrismOrderService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("electrismOrderService")
public class ElectrismOrderServiceImpl implements ElectrismOrderService{

	
	@Resource
	private ElectrismOrderDao electrismOrderDao;
	
	
	public ElectrismOrderDao getElectrismOrderDao() {
		return electrismOrderDao;
	}


	public void setElectrismOrderDao(ElectrismOrderDao electrismOrderDao) {
		this.electrismOrderDao = electrismOrderDao;
	}


	@Override
	public void saveElectrismOrder(ElectrismOrder electrismOrder) {
		
		this.electrismOrderDao.saveElectrismOrder(electrismOrder);
	}

	
	@Override
	public void saveElectrismOrderTime(ElectrismOrderTime electrismOrderTime) {
		
		this.electrismOrderDao.saveElectrismOrderTime(electrismOrderTime);
	}

	
	@Override
	public Page searchElectrismOrder(HashMap map) {
		
		return this.electrismOrderDao.searchElectrismOrder(map);
	}

	
	@Override
	public String searchsElectrismOrderTime(String electrismId) {
		
		return this.electrismOrderDao.searchsElectrismOrderTime(electrismId);
	}

	
	@Override
	public void updateElectrismOrderStatis(String orderId, String status) {
		
		this.electrismOrderDao.updateElectrismOrderStatis(orderId, status);
	}

	
	@Override
	public void updateElectrismOrderTimeStatis(String orderId) {
		
		this.electrismOrderDao.updateElectrismOrderTimeStatis(orderId);
	}


	
	@Override
	public Page searchElectrismOrderInfo(HashMap map) {
		
		return this.electrismOrderDao.searchElectrismOrderInfo(map);
	}


	
	@Override
	public String getMemberIdByMobile(String mobile) {
		
		
		return this.electrismOrderDao.getMemberIdByMobile(mobile);
	}


	
	@Override
	public void addOrderPayment(String orderId, String payment,String remarks) {
		
		this.electrismOrderDao.addOrderPayment(orderId, payment,remarks);
	}



	@Override
	public HashMap getElectrismOrderCount() {
		
		return this.electrismOrderDao.getElectrismOrderCount();
	}


	
	@Override
	public void updateOrderPayment(String orderId) {
		
		this.electrismOrderDao.updateOrderPayment(orderId);
	}


	
	@Override
	public ElectrismOrder getElectrismOrder(String id) {
		
		
		return this.electrismOrderDao.getElectrismOrder(id);
	}


	
	@Override
	public List getElectrismNameMap() {
		
		return this.electrismOrderDao.getElectrismNameMap();
	}


	
	@Override
	public Page searchOrderPaymentInfo(HashMap map) {
		

		return this.electrismOrderDao.searchOrderPaymentInfo(map);
	}

}
