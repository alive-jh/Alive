package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseOssRoleDetail<M extends BaseOssRoleDetail<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOssAccountId(java.lang.Integer ossAccountId) {
		set("oss_account_id", ossAccountId);
		return (M)this;
	}

	public java.lang.Integer getOssAccountId() {
		return getInt("oss_account_id");
	}

	public M setRoleId(java.lang.Integer roleId) {
		set("role_id", roleId);
		return (M)this;
	}

	public java.lang.Integer getRoleId() {
		return getInt("role_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}

	public java.lang.String getName() {
		return getStr("name");
	}

	public M setContact(java.lang.String contact) {
		set("contact", contact);
		return (M)this;
	}

	public java.lang.String getContact() {
		return getStr("contact");
	}

	public M setTel(java.lang.String tel) {
		set("tel", tel);
		return (M)this;
	}

	public java.lang.String getTel() {
		return getStr("tel");
	}

	public M setPid(java.lang.Integer pid) {
		set("pid", pid);
		return (M)this;
	}

	public java.lang.Integer getPid() {
		return getInt("pid");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

}
