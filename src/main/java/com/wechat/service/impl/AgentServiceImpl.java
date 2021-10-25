package com.wechat.service.impl;

import com.wechat.dao.AgentDao;
import com.wechat.entity.AgentBillOfSales;
import com.wechat.entity.ShipmentRecord;
import com.wechat.service.AgentService;
import com.wechat.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service("agentService")
public class AgentServiceImpl implements AgentService{

	@Resource
	private AgentDao agentDao;

	public Page getAgentList(HashMap map) {
		// TODO Auto-generated method stub
		return this.agentDao.getAgentList(map);
	}

	@Override
	public Page getBillOfSalesByAgentId(HashMap map) {
		// TODO Auto-generated method stub
		return this.agentDao.getBillOfSalesByAgentId(map);
	}

	@Override
	public void saveShipmentRecord(ShipmentRecord shipmentRecord) {
		// TODO Auto-generated method stub
		this.agentDao.saveShipmentRecord(shipmentRecord);
		
	}

	@Override
	public void saveAgentBillOfSales(AgentBillOfSales agentBillOfSales) {
		// TODO Auto-generated method stub
		this.agentDao.saveAgentBillOfSales(agentBillOfSales);
	}

	@Override
	public Page getShipmentDetailList(HashMap map) {
		// TODO Auto-generated method stub
		return this.agentDao.getShipmentDetailList(map);
	}

}
