package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMemberbookinfo<M extends BaseMemberbookinfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMemberid(java.lang.Integer memberid) {
		set("memberid", memberid);
		return (M)this;
	}

	public java.lang.Integer getMemberid() {
		return getInt("memberid");
	}

	public M setCateid(java.lang.String cateid) {
		set("cateid", cateid);
		return (M)this;
	}

	public java.lang.String getCateid() {
		return getStr("cateid");
	}

	public M setBookexpressid(java.lang.Integer bookexpressid) {
		set("bookexpressid", bookexpressid);
		return (M)this;
	}

	public java.lang.Integer getBookexpressid() {
		return getInt("bookexpressid");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setBarcode(java.lang.String barcode) {
		set("barcode", barcode);
		return (M)this;
	}

	public java.lang.String getBarcode() {
		return getStr("barcode");
	}

}
