package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCouponsinfo<M extends BaseCouponsinfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCouponsid(java.lang.Integer couponsid) {
		set("couponsid", couponsid);
		return (M)this;
	}

	public java.lang.Integer getCouponsid() {
		return getInt("couponsid");
	}

	public M setMemberid(java.lang.Integer memberid) {
		set("memberid", memberid);
		return (M)this;
	}

	public java.lang.Integer getMemberid() {
		return getInt("memberid");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}