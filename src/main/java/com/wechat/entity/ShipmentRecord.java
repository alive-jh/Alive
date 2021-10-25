package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "shipment_record")
public class ShipmentRecord {

	@Id
	@GeneratedValue(generator ="ShipmentRecordtableGenerator")       
    @GenericGenerator(name ="ShipmentRecordtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "agent_id")
	private Integer agentId;
	
	
	@Column(name = "user_id")
	private Integer userId;
	
	
	@Column(name = "bill_of_sales_id")
	private Integer billOfSalesId;
	
	@Column(name = "create_time")
	private String createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	public Integer getBillOfSalesId() {
		return billOfSalesId;
	}
	public void setBillOfSalesId(Integer billOfSalesId) {
		this.billOfSalesId = billOfSalesId;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
