package com.wechat.service.impl;

import com.wechat.dao.ElectrismDao;
import com.wechat.entity.Complaint;
import com.wechat.entity.DistrictInfo;
import com.wechat.entity.Electrism;
import com.wechat.entity.ElectrismOrderPayment;
import com.wechat.service.ElectrismService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("electrismService")
public class ElectrismServiceImpl  implements ElectrismService{

	
	@Resource
	private ElectrismDao electrismDao;
	
	
	public ElectrismDao getElectrismDao() {
		return electrismDao;
	}


	public void setElectrismDao(ElectrismDao electrismDao) {
		this.electrismDao = electrismDao;
	}


	@Override
	public void deleteDistricInfo(String electrismId) {
		
		this.electrismDao.deleteDistricInfo(electrismId);
	}

	
	@Override
	public void deleteElectrism(String id) {
		
		this.electrismDao.deleteElectrism(id);
	}

	
	@Override
	public void saveElectrism(Electrism electrism) {
		
		this.electrismDao.saveElectrism(electrism);
	}

	
	@Override
	public List searchDistrict() {
		
		return this.electrismDao.searchDistrict();
	}

	
	@Override
	public List searchDistrictByElectrism(String electrismId) {
		
		return this.electrismDao.searchDistrictByElectrism(electrismId);
	}

	
	@Override
	public Page searchElectrism(HashMap map) {
		
		return this.electrismDao.searchElectrism(map);
	}
	
	@Override
	public 	void saveDistrictInfo(DistrictInfo districtInfo)
	{
		this.electrismDao.saveDistrictInfo(districtInfo);
	}


	
	@Override
	public List getMemberByAccount(String account) {
		
		return this.electrismDao.getMemberByAccount(account);
	}


	
	@Override
	public void updateElectrismStatus(String electrismId,String status) {
		
		this.electrismDao.updateElectrismStatus(electrismId,status);
	}


	
	@Override
	public Electrism getElectrism(String id) {
		
		
		return this.electrismDao.getElectrism(id);
	}



	@Override
	public Complaint getComplaint(Integer id) {
		
		return this.electrismDao.getComplaint(id);
	}



	@Override
	public void saveComplaint(Complaint complaint) {
		
		this.electrismDao.saveComplaint(complaint);
	}



	@Override
	public Page searchComplaint(HashMap map) {
		
		return this.electrismDao.searchComplaint(map);
	}



	@Override
	public List seartchComplaint(String orderId) {
		
		return this.electrismDao.seartchComplaint(orderId);
	}


	
	@Override
	public void saveElectrismOrderPayment(
			ElectrismOrderPayment electrismOrderPayment) {
		
		this.electrismDao.saveElectrismOrderPayment(electrismOrderPayment);
	}


	
	@Override
	public Page searchElectrismOrderPayment(HashMap map) {
		
		return this.electrismDao.searchElectrismOrderPayment(map);
	}


	
	@Override
	public void updateElectrismOrderPayment(String electrismId) {
		
		this.electrismDao.updateElectrismOrderPayment(electrismId);
	}


	
	@Override
	public void updatePaymentStatus(String id, String status) {
		
		this.electrismDao.updatePaymentStatus(id, status);
	}


	
	@Override
	public String getElectrismOrderSumPayment(String electrismId) {
		
		
		return this.electrismDao.getElectrismOrderSumPayment(electrismId);
	}


	
	@Override
	public Electrism getElectrismByMemberId(String memberId) {
		
		
		return this.electrismDao.getElectrismByMemberId(memberId);
	}


	
	
}
