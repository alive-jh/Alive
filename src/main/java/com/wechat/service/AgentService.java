package com.wechat.service;


import com.wechat.entity.AgentBillOfSales;
import com.wechat.entity.ShipmentRecord;
import com.wechat.util.Page;

import java.util.HashMap;

public interface AgentService {
	
	Page getAgentList(HashMap map);

	Page getBillOfSalesByAgentId(HashMap map);

	void saveShipmentRecord(ShipmentRecord shipmentRecord);

	void saveAgentBillOfSales(AgentBillOfSales agentBillOfSales);

	Page getShipmentDetailList(HashMap map);


}
