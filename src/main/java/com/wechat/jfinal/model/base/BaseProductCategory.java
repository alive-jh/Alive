package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseProductCategory<M extends BaseProductCategory<M>> extends Model<M> implements IBean {

	public M setCatId(java.lang.Integer catId) {
		set("cat_id", catId);
		return (M)this;
	}

	public java.lang.Integer getCatId() {
		return getInt("cat_id");
	}

	public M setUniqueId(java.lang.String uniqueId) {
		set("unique_id", uniqueId);
		return (M)this;
	}

	public java.lang.String getUniqueId() {
		return getStr("unique_id");
	}

	public M setCatName(java.lang.String catName) {
		set("cat_name", catName);
		return (M)this;
	}

	public java.lang.String getCatName() {
		return getStr("cat_name");
	}

	public M setKeywords(java.lang.String keywords) {
		set("keywords", keywords);
		return (M)this;
	}

	public java.lang.String getKeywords() {
		return getStr("keywords");
	}

	public M setDescription(java.lang.String description) {
		set("description", description);
		return (M)this;
	}

	public java.lang.String getDescription() {
		return getStr("description");
	}

	public M setParentId(java.lang.Integer parentId) {
		set("parent_id", parentId);
		return (M)this;
	}

	public java.lang.Integer getParentId() {
		return getInt("parent_id");
	}

	public M setSort(java.lang.Boolean sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Boolean getSort() {
		return get("sort");
	}

}