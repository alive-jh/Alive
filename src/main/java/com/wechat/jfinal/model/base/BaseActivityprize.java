package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseActivityprize<M extends BaseActivityprize<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setActivityid(java.lang.Integer activityid) {
		set("activityid", activityid);
		return (M)this;
	}

	public java.lang.Integer getActivityid() {
		return getInt("activityid");
	}

	public M setPercentage(java.lang.Integer percentage) {
		set("percentage", percentage);
		return (M)this;
	}

	public java.lang.Integer getPercentage() {
		return getInt("percentage");
	}

	public M setCount(java.lang.Integer count) {
		set("count", count);
		return (M)this;
	}

	public java.lang.Integer getCount() {
		return getInt("count");
	}

}
