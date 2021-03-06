package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLibrary<M extends BaseLibrary<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setIsbn(java.lang.String isbn) {
		set("isbn", isbn);
		return (M)this;
	}

	public java.lang.String getIsbn() {
		return getStr("isbn");
	}

	public M setBookName(java.lang.String bookName) {
		set("book_name", bookName);
		return (M)this;
	}

	public java.lang.String getBookName() {
		return getStr("book_name");
	}

	public M setCoverUrl(java.lang.String coverUrl) {
		set("cover_url", coverUrl);
		return (M)this;
	}

	public java.lang.String getCoverUrl() {
		return getStr("cover_url");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
