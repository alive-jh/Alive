package com.wechat.service;

import com.wechat.entity.ElectrismOrder;
import com.wechat.entity.ElectrismOrderTime;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface ElectrismOrderService {

	/**
	 * 保存预约订单
	 * @param electrismOrder
	 */
	void saveElectrismOrder(ElectrismOrder electrismOrder);
	
	/**
	 * 保存预约时间表
	 * @param electrismOrderTime
	 */
	void saveElectrismOrderTime(ElectrismOrderTime electrismOrderTime);
	
	/**
	 * 更新预约订单状态
	 * @param orderId
	 * @param status
	 */
	void updateElectrismOrderStatis(String orderId, String status);
	
	/**
	 * 更新预约时间状态
	 * @param orderId
	 */
	void updateElectrismOrderTimeStatis(String orderId);
	
	/**
	 * 查询预约订单
	 * @param map
	 * @return
	 */
	Page searchElectrismOrder(HashMap map);
	
	/**
	 * 查询预约时间
	 * @param orderId
	 * @return
	 */
	String searchsElectrismOrderTime(String electrismId);
	
	
	/**
	 * 后台查询预约订单
	 * @param map
	 * @return
	 */
	Page searchElectrismOrderInfo(HashMap map);
	
	/**
	 * 根据手机号码查询微信号
	 * @param mobile
	 * @return
	 */
	String getMemberIdByMobile(String mobile);
	
	/**
	 * 查询电工订单总数
	 * @param ElectrismId
	 * @return
	 */
	HashMap getElectrismOrderCount();
	/**
	 * 电工追加订单金额
	 * @param orderId
	 * @param payment
	 */
	void addOrderPayment(String orderId, String payment, String remarks);
	
	/**
	 * 用户付款
	 * @param orderId
	 */
	void updateOrderPayment(String orderId);
	
	/**
	 * 查询单个订单信息
	 * @param id
	 * @return
	 */
	ElectrismOrder getElectrismOrder(String id);
	
	/**
	 * 查询电工名称集合
	 * @return
	 */
	List getElectrismNameMap();
	
	/**
	 * 提现流水汇总信息
	 * @param map
	 * @return
	 */
	Page searchOrderPaymentInfo(HashMap map);
}
