package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQyUser<M extends BaseQyUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUser(java.lang.String user) {
		set("user", user);
		return (M)this;
	}

	public java.lang.String getUser() {
		return getStr("user");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}

	public java.lang.String getPassword() {
		return getStr("password");
	}

}
