package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGoodVideo<M extends BaseGoodVideo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setArea(java.lang.String area) {
		set("area", area);
		return (M)this;
	}

	public java.lang.String getArea() {
		return getStr("area");
	}

	public M setSchool(java.lang.String school) {
		set("school", school);
		return (M)this;
	}

	public java.lang.String getSchool() {
		return getStr("school");
	}

	public M setInsertDate(java.lang.String insertDate) {
		set("insert_date", insertDate);
		return (M)this;
	}

	public java.lang.String getInsertDate() {
		return getStr("insert_date");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

}
