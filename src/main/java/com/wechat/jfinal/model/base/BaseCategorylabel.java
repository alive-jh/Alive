package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCategorylabel<M extends BaseCategorylabel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCategoryid(java.lang.String categoryid) {
		set("categoryid", categoryid);
		return (M)this;
	}

	public java.lang.String getCategoryid() {
		return getStr("categoryid");
	}

	public M setLabelid(java.lang.Integer labelid) {
		set("labelid", labelid);
		return (M)this;
	}

	public java.lang.Integer getLabelid() {
		return getInt("labelid");
	}

}
