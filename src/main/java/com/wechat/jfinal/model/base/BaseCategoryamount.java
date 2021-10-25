package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCategoryamount<M extends BaseCategoryamount<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setBelong(java.lang.Integer belong) {
		set("belong", belong);
		return (M)this;
	}

	public java.lang.Integer getBelong() {
		return getInt("belong");
	}

	public M setCateId(java.lang.String cateId) {
		set("cate_id", cateId);
		return (M)this;
	}

	public java.lang.String getCateId() {
		return getStr("cate_id");
	}

	public M setTotality(java.lang.Integer totality) {
		set("totality", totality);
		return (M)this;
	}

	public java.lang.Integer getTotality() {
		return getInt("totality");
	}

	public M setRemain(java.lang.Integer remain) {
		set("remain", remain);
		return (M)this;
	}

	public java.lang.Integer getRemain() {
		return getInt("remain");
	}

}