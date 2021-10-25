package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePublicRoomFidToStudent<M extends BasePublicRoomFidToStudent<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCardFid(java.lang.String cardFid) {
		set("card_fid", cardFid);
		return (M)this;
	}

	public java.lang.String getCardFid() {
		return getStr("card_fid");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("student_id", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("student_id");
	}

	public M setInsertDate(java.lang.String insertDate) {
		set("insert_date", insertDate);
		return (M)this;
	}

	public java.lang.String getInsertDate() {
		return getStr("insert_date");
	}

	public M setUpdateDate(java.util.Date updateDate) {
		set("update_date", updateDate);
		return (M)this;
	}

	public java.util.Date getUpdateDate() {
		return get("update_date");
	}

	public M setUpdateBy(java.lang.String updateBy) {
		set("update_by", updateBy);
		return (M)this;
	}

	public java.lang.String getUpdateBy() {
		return getStr("update_by");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setAgentId(java.lang.Integer agentId) {
		set("agent_id", agentId);
		return (M)this;
	}

	public java.lang.Integer getAgentId() {
		return getInt("agent_id");
	}

	public M setSchoolId(java.lang.Integer schoolId) {
		set("school_id", schoolId);
		return (M)this;
	}

	public java.lang.Integer getSchoolId() {
		return getInt("school_id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

}
