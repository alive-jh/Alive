package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseAgentChargeOrder<M extends BaseAgentChargeOrder<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setAgentId(java.lang.Integer agentId) {
		set("agent_id", agentId);
		return (M)this;
	}

	public java.lang.Integer getAgentId() {
		return getInt("agent_id");
	}

	public M setAmount(java.math.BigDecimal amount) {
		set("amount", amount);
		return (M)this;
	}

	public java.math.BigDecimal getAmount() {
		return get("amount");
	}

	public M setOrderNumber(java.lang.String orderNumber) {
		set("order_number", orderNumber);
		return (M)this;
	}

	public java.lang.String getOrderNumber() {
		return getStr("order_number");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}