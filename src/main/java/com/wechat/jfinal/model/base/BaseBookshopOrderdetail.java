package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBookshopOrderdetail<M extends BaseBookshopOrderdetail<M>> extends Model<M> implements IBean {

	public M setOdid(java.lang.Integer odid) {
		set("odid", odid);
		return (M)this;
	}

	public java.lang.Integer getOdid() {
		return getInt("odid");
	}

	public M setOrderId(java.lang.String orderId) {
		set("order_id", orderId);
		return (M)this;
	}

	public java.lang.String getOrderId() {
		return getStr("order_id");
	}

	public M setCateId(java.lang.String cateId) {
		set("cate_id", cateId);
		return (M)this;
	}

	public java.lang.String getCateId() {
		return getStr("cate_id");
	}

}
