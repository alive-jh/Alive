package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBelong<M extends BaseBelong<M>> extends Model<M> implements IBean {

	public M setBid(java.lang.Integer bid) {
		set("bid", bid);
		return (M)this;
	}

	public java.lang.Integer getBid() {
		return getInt("bid");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setBookAmount(java.lang.Integer bookAmount) {
		set("book_amount", bookAmount);
		return (M)this;
	}

	public java.lang.Integer getBookAmount() {
		return getInt("book_amount");
	}

}
