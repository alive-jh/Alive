package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseGroupInfo<M extends BaseGroupInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setUserId(java.lang.String userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.String getUserId() {
		return getStr("user_id");
	}

	public M setGroupId(java.lang.String groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.String getGroupId() {
		return getStr("group_id");
	}

	public M setNoteName(java.lang.String noteName) {
		set("note_name", noteName);
		return (M)this;
	}

	public java.lang.String getNoteName() {
		return getStr("note_name");
	}

}
