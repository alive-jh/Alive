package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSchoolClass<M extends BaseSchoolClass<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassName(java.lang.String className) {
		set("className", className);
		return (M)this;
	}

	public java.lang.String getClassName() {
		return getStr("className");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setSchoolId(java.lang.Integer schoolId) {
		set("schoolId", schoolId);
		return (M)this;
	}

	public java.lang.Integer getSchoolId() {
		return getInt("schoolId");
	}

}
