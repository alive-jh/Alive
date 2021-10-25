package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "electrismorder")
public class ElectrismOrder {


	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;	//会员id
	
	@Column(name = "electrismid")
	private Integer electrismId;//电工id
	                
	
	@Column(name = "status")
	private Integer status = new Integer(0);	//订单状态,0进行中,1已完成,2已取消
	
	@Column(name = "ordernumber")
	private String  orderNumber;//订单编号
	
	@Column(name = "contacts")
	private String contacts;//联系人
	
	@Column(name = "mobile")
	private String mobile;//手机号码
	
	@Column(name = "address")
	private String address;//服务地址
	
	@Column(name = "payment")
	private Double payment;//订单金额
	
	@Column(name = "createdate")
	private Date createDate;//创建时间

	@Column(name = "serviceitem")
	private String serviceItem;//服务项目
	
	
	@Column(name = "electrismname")
	private String electrismName;//电工名称
	
	
	@Column(name = "orderdate")
	private String orderDate;//预约时间
	
	@Column(name = "addpayment")
	private Double addPayment = new Double(0);//追加金额
	
	@Column(name = "remarks")
	private String remarks;	// 备注
	
	
	
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getAddPayment() {
		return addPayment;
	}

	public void setAddPayment(Double addPayment) {
		this.addPayment = addPayment;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getServiceItem() {
		return serviceItem;
	}

	public void setServiceItem(String serviceItem) {
		this.serviceItem = serviceItem;
	}

	public String getElectrismName() {
		return electrismName;
	}

	public void setElectrismName(String electrismName) {
		this.electrismName = electrismName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getElectrismId() {
		return electrismId;
	}

	public void setElectrismId(Integer electrismId) {
		this.electrismId = electrismId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
