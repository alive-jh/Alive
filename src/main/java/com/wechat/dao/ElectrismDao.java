package com.wechat.dao;

import com.wechat.entity.Complaint;
import com.wechat.entity.DistrictInfo;
import com.wechat.entity.Electrism;
import com.wechat.entity.ElectrismOrderPayment;
import com.wechat.util.Page;

import java.util.HashMap;
import java.util.List;

public interface ElectrismDao {

	
	/**
	 * 保存电工
	 * @param electrism
	 */
	void saveElectrism(Electrism electrism);
	
	
	/**
	 * 查询电工
	 * @param map
	 * @return
	 */
	Page searchElectrism(HashMap map);
	
	/**
	 * 删除电工
	 * @param id
	 */
	void deleteElectrism(String id);
	/**
	 * 查询服务商圈
	 * @return
	 */
	List searchDistrict();
	
	/**
	 * 根据电工的商圈查询
	 * @param electrismId
	 * @return
	 */
	List searchDistrictByElectrism(String electrismId);
	
	/**
	 * 删除电工商圈关系
	 * @param electrismId
	 */
	void deleteDistricInfo(String electrismId);
	
	/**
	 * 保存电工商圈
	 * @param districtInfo
	 */
	void saveDistrictInfo(DistrictInfo districtInfo);
	
	/**
	 * 查询电工微信号
	 * @param account
	 * @return
	 */
	public List getMemberByAccount(String account);
	
	/**
	 * 锁定电工状态
	 * @param electrismId
	 */
	public void updateElectrismStatus(String electrismId, String status);
	
	/**
	 * 查询电工
	 * @param id
	 * @return
	 */
	public Electrism getElectrism(String id);
	
	/**
	 * 保存投诉反馈
	 * @param complaint
	 */
	void saveComplaint(Complaint complaint);
	
	/**
	 * 查询投诉反馈
	 * @param orderId
	 * @return
	 */
	List seartchComplaint(String orderId);
	
	/**
	 * 查询投诉反馈
	 * @param map
	 * @return
	 */
	Page searchComplaint(HashMap map);
	
	/**
	 * 查看投诉反馈
	 * @param id
	 * @return
	 */
	Complaint getComplaint(Integer id);
	
	/**
	 * 更新电工提现状态
	 * @param id
	 */
	void updatePaymentStatus(String id, String status);
	
	/**
	 * 添加电工订单流水记录
	 * @param electrismOrderPayment
	 * @return
	 */
	void saveElectrismOrderPayment(ElectrismOrderPayment electrismOrderPayment);
	
	/**
	 * 查询电工订单流水
	 * @param map
	 * @return
	 */
	Page searchElectrismOrderPayment(HashMap map);
	
	/**
	 * 更新电工流水订单状态
	 * @param electrismId
	 */
	void updateElectrismOrderPayment(String electrismId);
	
	/**
	 * 查询电工体现总额
	 * @param electrismId
	 * @return
	 */
	String getElectrismOrderSumPayment(String electrismId);
	
	/**
	 * 根据会员id查询电工id
	 * @param memberId
	 * @return
	 */
	Electrism getElectrismByMemberId(String memberId);
	
	
	
}
