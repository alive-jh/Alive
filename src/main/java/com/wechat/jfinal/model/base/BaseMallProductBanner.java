package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMallProductBanner<M extends BaseMallProductBanner<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMallProductId(java.lang.Integer mallProductId) {
		set("mall_product_id", mallProductId);
		return (M)this;
	}

	public java.lang.Integer getMallProductId() {
		return getInt("mall_product_id");
	}

	public M setBannerUrl(java.lang.String bannerUrl) {
		set("banner_url", bannerUrl);
		return (M)this;
	}

	public java.lang.String getBannerUrl() {
		return getStr("banner_url");
	}

}
