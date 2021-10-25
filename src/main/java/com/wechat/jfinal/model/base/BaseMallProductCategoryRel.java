package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMallProductCategoryRel<M extends BaseMallProductCategoryRel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setMallProductCategory(java.lang.Integer mallProductCategory) {
		set("mall_product_category", mallProductCategory);
		return (M)this;
	}

	public java.lang.Integer getMallProductCategory() {
		return getInt("mall_product_category");
	}

	public M setMallProductId(java.lang.Integer mallProductId) {
		set("mall_product_id", mallProductId);
		return (M)this;
	}

	public java.lang.Integer getMallProductId() {
		return getInt("mall_product_id");
	}

}