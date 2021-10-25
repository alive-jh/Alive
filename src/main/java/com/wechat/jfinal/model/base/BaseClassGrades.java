package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassGrades<M extends BaseClassGrades<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassGradesName(java.lang.String classGradesName) {
		set("class_grades_name", classGradesName);
		return (M)this;
	}

	public java.lang.String getClassGradesName() {
		return getStr("class_grades_name");
	}

	public M setParentId(java.lang.Integer parentId) {
		set("parent_id", parentId);
		return (M)this;
	}

	public java.lang.Integer getParentId() {
		return getInt("parent_id");
	}

	public M setSummary(java.lang.String summary) {
		set("summary", summary);
		return (M)this;
	}

	public java.lang.String getSummary() {
		return getStr("summary");
	}

	public M setCover(java.lang.String cover) {
		set("cover", cover);
		return (M)this;
	}

	public java.lang.String getCover() {
		return getStr("cover");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setGradesType(java.lang.String gradesType) {
		set("grades_type", gradesType);
		return (M)this;
	}

	public java.lang.String getGradesType() {
		return getStr("grades_type");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacher_id", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacher_id");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setAuditingStatus(java.lang.Integer auditingStatus) {
		set("auditing_status", auditingStatus);
		return (M)this;
	}

	public java.lang.Integer getAuditingStatus() {
		return getInt("auditing_status");
	}

	public M setPrice(java.lang.Integer price) {
		set("price", price);
		return (M)this;
	}

	public java.lang.Integer getPrice() {
		return getInt("price");
	}

	public M setClassOpenDate(java.lang.String classOpenDate) {
		set("classOpenDate", classOpenDate);
		return (M)this;
	}

	public java.lang.String getClassOpenDate() {
		return getStr("classOpenDate");
	}

	public M setJoinStatus(java.lang.Integer joinStatus) {
		set("joinStatus", joinStatus);
		return (M)this;
	}

	public java.lang.Integer getJoinStatus() {
		return getInt("joinStatus");
	}

	public M setLimitCount(java.lang.Integer limitCount) {
		set("limit_count", limitCount);
		return (M)this;
	}

	public java.lang.Integer getLimitCount() {
		return getInt("limit_count");
	}

	public M setRemark(java.lang.String remark) {
		set("remark", remark);
		return (M)this;
	}

	public java.lang.String getRemark() {
		return getStr("remark");
	}

	public M setShowInEova(java.lang.Integer showInEova) {
		set("show_in_eova", showInEova);
		return (M)this;
	}

	public java.lang.Integer getShowInEova() {
		return getInt("show_in_eova");
	}

	public M setClassroomPackId(java.lang.Integer classroomPackId) {
		set("classroom_pack_id", classroomPackId);
		return (M)this;
	}

	public java.lang.Integer getClassroomPackId() {
		return getInt("classroom_pack_id");
	}

	public M setClassScheduleVersion(java.lang.String classScheduleVersion) {
		set("class_schedule_version", classScheduleVersion);
		return (M)this;
	}

	public java.lang.String getClassScheduleVersion() {
		return getStr("class_schedule_version");
	}

}