package com.wechat.jfinal.service;

import java.util.Map;
import java.util.UUID;

import com.wechat.jfinal.model.ChaozhiOrder;

public class ChaozhiSvc {

	public boolean createOrder(Map map) {
		
		ChaozhiOrder chaozhiOrder = new ChaozhiOrder();
		chaozhiOrder.setOrderNumber(map.get("orderNumber").toString());
		chaozhiOrder.setTotalPrice(Double.parseDouble(map.get("totalPrice").toString()));
		chaozhiOrder.setOpenId(map.get("openId")!=null?map.get("openId").toString():"");
		chaozhiOrder.setMobile(map.get("mobile").toString());
		chaozhiOrder.setStatus(0);
		
		return chaozhiOrder.save();
		
	}

}
