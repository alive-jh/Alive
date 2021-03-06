package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseIsOutdate<M extends BaseIsOutdate<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setIsOutdate(java.lang.Integer isOutdate) {
		set("is_outdate", isOutdate);
		return (M)this;
	}

	public java.lang.Integer getIsOutdate() {
		return getInt("is_outdate");
	}

	public M setEnvironment(java.lang.String environment) {
		set("environment", environment);
		return (M)this;
	}

	public java.lang.String getEnvironment() {
		return getStr("environment");
	}

	public M setVersion(java.lang.String version) {
		set("version", version);
		return (M)this;
	}

	public java.lang.String getVersion() {
		return getStr("version");
	}

}
