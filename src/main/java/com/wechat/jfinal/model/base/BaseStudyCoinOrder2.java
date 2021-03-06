package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseStudyCoinOrder2<M extends BaseStudyCoinOrder2<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOrderNumber(java.lang.String orderNumber) {
		set("order_number", orderNumber);
		return (M)this;
	}

	public java.lang.String getOrderNumber() {
		return getStr("order_number");
	}

	public M setStudyCoinPackId(java.lang.Integer studyCoinPackId) {
		set("study_coin_pack_id", studyCoinPackId);
		return (M)this;
	}

	public java.lang.Integer getStudyCoinPackId() {
		return getInt("study_coin_pack_id");
	}

	public M setTotalPrice(java.math.BigDecimal totalPrice) {
		set("total_price", totalPrice);
		return (M)this;
	}

	public java.math.BigDecimal getTotalPrice() {
		return get("total_price");
	}

	public M setAccountId(java.lang.Integer accountId) {
		set("account_id", accountId);
		return (M)this;
	}

	public java.lang.Integer getAccountId() {
		return getInt("account_id");
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
