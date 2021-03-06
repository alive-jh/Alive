package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseReportDayActive<M extends BaseReportDayActive<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDate(java.lang.String date) {
		set("date", date);
		return (M)this;
	}

	public java.lang.String getDate() {
		return getStr("date");
	}

	public M setRealCount(java.lang.Integer realCount) {
		set("real_count", realCount);
		return (M)this;
	}

	public java.lang.Integer getRealCount() {
		return getInt("real_count");
	}

	public M setShowCount(java.lang.Integer showCount) {
		set("show_count", showCount);
		return (M)this;
	}

	public java.lang.Integer getShowCount() {
		return getInt("show_count");
	}

	public M setRealActiveCount(java.lang.Integer realActiveCount) {
		set("real_active_count", realActiveCount);
		return (M)this;
	}

	public java.lang.Integer getRealActiveCount() {
		return getInt("real_active_count");
	}

	public M setShowActiveCount(java.lang.Integer showActiveCount) {
		set("show_active_count", showActiveCount);
		return (M)this;
	}

	public java.lang.Integer getShowActiveCount() {
		return getInt("show_active_count");
	}

}
