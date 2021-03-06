package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseElectrismorder<M extends BaseElectrismorder<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMemberid(java.lang.Integer memberid) {
		set("memberid", memberid);
		return (M)this;
	}

	public java.lang.Integer getMemberid() {
		return getInt("memberid");
	}

	public M setCreatedate(java.util.Date createdate) {
		set("createdate", createdate);
		return (M)this;
	}

	public java.util.Date getCreatedate() {
		return get("createdate");
	}

	public M setElectrismid(java.lang.Integer electrismid) {
		set("electrismid", electrismid);
		return (M)this;
	}

	public java.lang.Integer getElectrismid() {
		return getInt("electrismid");
	}

	public M setOrdernumber(java.lang.String ordernumber) {
		set("ordernumber", ordernumber);
		return (M)this;
	}

	public java.lang.String getOrdernumber() {
		return getStr("ordernumber");
	}

	public M setPayment(java.lang.Double payment) {
		set("payment", payment);
		return (M)this;
	}

	public java.lang.Double getPayment() {
		return getDouble("payment");
	}

	public M setContacts(java.lang.String contacts) {
		set("contacts", contacts);
		return (M)this;
	}

	public java.lang.String getContacts() {
		return getStr("contacts");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setAddress(java.lang.String address) {
		set("address", address);
		return (M)this;
	}

	public java.lang.String getAddress() {
		return getStr("address");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setElectrismname(java.lang.String electrismname) {
		set("electrismname", electrismname);
		return (M)this;
	}

	public java.lang.String getElectrismname() {
		return getStr("electrismname");
	}

	public M setServiceitem(java.lang.String serviceitem) {
		set("serviceitem", serviceitem);
		return (M)this;
	}

	public java.lang.String getServiceitem() {
		return getStr("serviceitem");
	}

	public M setOrderdate(java.lang.String orderdate) {
		set("orderdate", orderdate);
		return (M)this;
	}

	public java.lang.String getOrderdate() {
		return getStr("orderdate");
	}

}
