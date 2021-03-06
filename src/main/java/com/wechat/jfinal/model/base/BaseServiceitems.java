package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseServiceitems<M extends BaseServiceitems<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setElectrismid(java.lang.Integer electrismid) {
		set("electrismid", electrismid);
		return (M)this;
	}

	public java.lang.Integer getElectrismid() {
		return getInt("electrismid");
	}

	public M setServicename(java.lang.String servicename) {
		set("servicename", servicename);
		return (M)this;
	}

	public java.lang.String getServicename() {
		return getStr("servicename");
	}

}
