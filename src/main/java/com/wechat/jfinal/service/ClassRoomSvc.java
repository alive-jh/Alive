package com.wechat.jfinal.service;

import java.util.HashMap;

import com.wechat.jfinal.model.ClassRoomOrder;

public class ClassRoomSvc {

	public boolean createOrder(HashMap<String, Object> paramets){
		
		ClassRoomOrder classRoomOrder = new ClassRoomOrder();
		classRoomOrder.setClassRoomId((Integer) paramets.get("classRoomId"));
		classRoomOrder.setClassPackId((Integer) paramets.get("classPackId"));
		classRoomOrder.setStudentId((Integer) paramets.get("classStudentId"));
		classRoomOrder.setMemberId((Integer) paramets.get("memberId"));
		classRoomOrder.setOrderNumber(paramets.get("orderNumber").toString());
		classRoomOrder.setTotalPrice((Double) paramets.get("totalPrice"));
		classRoomOrder.setStatus(0);
		return classRoomOrder.save();
		
	}
}
