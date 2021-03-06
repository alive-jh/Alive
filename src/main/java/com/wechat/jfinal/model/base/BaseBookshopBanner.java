package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseBookshopBanner<M extends BaseBookshopBanner<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setShopId(java.lang.Integer shopId) {
		set("shop_id", shopId);
		return (M)this;
	}

	public java.lang.Integer getShopId() {
		return getInt("shop_id");
	}

	public M setTitle(java.lang.String title) {
		set("title", title);
		return (M)this;
	}

	public java.lang.String getTitle() {
		return getStr("title");
	}

	public M setPicture(java.lang.String picture) {
		set("picture", picture);
		return (M)this;
	}

	public java.lang.String getPicture() {
		return getStr("picture");
	}

	public M setProductId(java.lang.Integer productId) {
		set("product_id", productId);
		return (M)this;
	}

	public java.lang.Integer getProductId() {
		return getInt("product_id");
	}

	public M setType(java.lang.String type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.String getType() {
		return getStr("type");
	}

}
