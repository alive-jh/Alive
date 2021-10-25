package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBookShopUser<M extends BaseBookShopUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setShopid(java.lang.Integer shopid) {
		set("shopid", shopid);
		return (M)this;
	}

	public java.lang.Integer getShopid() {
		return getInt("shopid");
	}

	public M setUserid(java.lang.Integer userid) {
		set("userid", userid);
		return (M)this;
	}

	public java.lang.Integer getUserid() {
		return getInt("userid");
	}

}
