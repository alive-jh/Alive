package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBookCopy1<M extends BaseBookCopy1<M>> extends Model<M> implements IBean {

	public M setBarcode(java.lang.String barcode) {
		set("barcode", barcode);
		return (M)this;
	}

	public java.lang.String getBarcode() {
		return getStr("barcode");
	}

	public M setIsexist(java.lang.Integer isexist) {
		set("isexist", isexist);
		return (M)this;
	}

	public java.lang.Integer getIsexist() {
		return getInt("isexist");
	}

	public M setCateId(java.lang.String cateId) {
		set("cate_id", cateId);
		return (M)this;
	}

	public java.lang.String getCateId() {
		return getStr("cate_id");
	}

	public M setBelong(java.lang.Integer belong) {
		set("belong", belong);
		return (M)this;
	}

	public java.lang.Integer getBelong() {
		return getInt("belong");
	}

	public M setUrl(java.lang.String url) {
		set("url", url);
		return (M)this;
	}

	public java.lang.String getUrl() {
		return getStr("url");
	}

	public M setCodeinfo(java.lang.String codeinfo) {
		set("codeinfo", codeinfo);
		return (M)this;
	}

	public java.lang.String getCodeinfo() {
		return getStr("codeinfo");
	}

}
