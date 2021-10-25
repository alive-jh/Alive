package com.wechat.dao;

import com.wechat.entity.AgentBillOfSales;
import com.wechat.entity.ShipmentRecord;
import com.wechat.util.Page;

import java.util.HashMap;

public interface AgentDao {
	
	/**
	 * @param
	 * @return
	 */
	Page getAgentList(HashMap map);

	Page getBillOfSalesByAgentId(HashMap map);

	void saveShipmentRecord(ShipmentRecord shipmentRecord);

	void saveAgentBillOfSales(AgentBillOfSales agentBillOfSales);

	Page getShipmentDetailList(HashMap map);
	
}
