package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseVideoCompetition<M extends BaseVideoCompetition<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setVideoInfoId(java.lang.Integer videoInfoId) {
		set("video_info_id", videoInfoId);
		return (M)this;
	}

	public java.lang.Integer getVideoInfoId() {
		return getInt("video_info_id");
	}

	public M setVideoActivityId(java.lang.Integer videoActivityId) {
		set("video_activity_id", videoActivityId);
		return (M)this;
	}

	public java.lang.Integer getVideoActivityId() {
		return getInt("video_activity_id");
	}

	public M setAcoutnId(java.lang.String acoutnId) {
		set("acoutn_id", acoutnId);
		return (M)this;
	}

	public java.lang.String getAcoutnId() {
		return getStr("acoutn_id");
	}

	public M setVerifyStatus(java.lang.Integer verifyStatus) {
		set("verify_status", verifyStatus);
		return (M)this;
	}

	public java.lang.Integer getVerifyStatus() {
		return getInt("verify_status");
	}

	public M setCreatTime(java.util.Date creatTime) {
		set("creat_time", creatTime);
		return (M)this;
	}

	public java.util.Date getCreatTime() {
		return get("creat_time");
	}

	public M setEditTime(java.util.Date editTime) {
		set("edit_time", editTime);
		return (M)this;
	}

	public java.util.Date getEditTime() {
		return get("edit_time");
	}

}
