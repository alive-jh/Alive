package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassRoom<M extends BaseClassRoom<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setTeacherId(java.lang.Integer teacherId) {
		set("teacher_id", teacherId);
		return (M)this;
	}

	public java.lang.Integer getTeacherId() {
		return getInt("teacher_id");
	}

	public M setTeacherName(java.lang.String teacherName) {
		set("teacher_name", teacherName);
		return (M)this;
	}

	public java.lang.String getTeacherName() {
		return getStr("teacher_name");
	}

	public M setClassName(java.lang.String className) {
		set("class_name", className);
		return (M)this;
	}

	public java.lang.String getClassName() {
		return getStr("class_name");
	}

	public M setCover(java.lang.String cover) {
		set("cover", cover);
		return (M)this;
	}

	public java.lang.String getCover() {
		return getStr("cover");
	}

	public M setSummary(java.lang.String summary) {
		set("summary", summary);
		return (M)this;
	}

	public java.lang.String getSummary() {
		return getStr("summary");
	}

	public M setStatus(java.lang.Integer status) {
		set("status", status);
		return (M)this;
	}

	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	public M setSort(java.lang.Integer sort) {
		set("sort", sort);
		return (M)this;
	}

	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	public M setCategoryId(java.lang.Integer categoryId) {
		set("category_id", categoryId);
		return (M)this;
	}

	public java.lang.Integer getCategoryId() {
		return getInt("category_id");
	}

	public M setBookResId(java.lang.Integer bookResId) {
		set("book_res_id", bookResId);
		return (M)this;
	}

	public java.lang.Integer getBookResId() {
		return getInt("book_res_id");
	}

	public M setGroupId(java.lang.String groupId) {
		set("group_id", groupId);
		return (M)this;
	}

	public java.lang.String getGroupId() {
		return getStr("group_id");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setClassRoomType(java.lang.String classRoomType) {
		set("class_room_type", classRoomType);
		return (M)this;
	}

	public java.lang.String getClassRoomType() {
		return getStr("class_room_type");
	}

	public M setVideoUrl(java.lang.String videoUrl) {
		set("video_url", videoUrl);
		return (M)this;
	}

	public java.lang.String getVideoUrl() {
		return getStr("video_url");
	}

	public M setType(java.lang.Integer type) {
		set("type", type);
		return (M)this;
	}

	public java.lang.Integer getType() {
		return getInt("type");
	}

	public M setBookResIds(java.lang.String bookResIds) {
		set("book_res_ids", bookResIds);
		return (M)this;
	}

	public java.lang.String getBookResIds() {
		return getStr("book_res_ids");
	}

	public M setDifficultyLevel(java.lang.Integer difficultyLevel) {
		set("difficulty_level", difficultyLevel);
		return (M)this;
	}

	public java.lang.Integer getDifficultyLevel() {
		return getInt("difficulty_level");
	}

	public M setOriginal(java.lang.Integer original) {
		set("original", original);
		return (M)this;
	}

	public java.lang.Integer getOriginal() {
		return getInt("original");
	}

	public M setMemberAccountId(java.lang.Integer memberAccountId) {
		set("member_account_id", memberAccountId);
		return (M)this;
	}

	public java.lang.Integer getMemberAccountId() {
		return getInt("member_account_id");
	}

	public M setCredit(java.lang.Integer credit) {
		set("credit", credit);
		return (M)this;
	}

	public java.lang.Integer getCredit() {
		return getInt("credit");
	}

	public M setClassroomType(java.lang.Integer classroomType) {
		set("classroom_type", classroomType);
		return (M)this;
	}

	public java.lang.Integer getClassroomType() {
		return getInt("classroom_type");
	}

	public M setLiveStatus(java.lang.Integer liveStatus) {
		set("live_status", liveStatus);
		return (M)this;
	}

	public java.lang.Integer getLiveStatus() {
		return getInt("live_status");
	}

	public M setLength(java.lang.Integer length) {
		set("length", length);
		return (M)this;
	}

	public java.lang.Integer getLength() {
		return getInt("length");
	}

	public M setSubjectId(java.lang.Integer subjectId) {
		set("subject_id", subjectId);
		return (M)this;
	}

	public java.lang.Integer getSubjectId() {
		return getInt("subject_id");
	}

	public M setBVisible(java.lang.Boolean bVisible) {
		set("b_visible", bVisible);
		return (M)this;
	}

	public java.lang.Boolean getBVisible() {
		return get("b_visible");
	}

	public M setAliveForm(java.lang.Boolean aliveForm) {
		set("alive_form", aliveForm);
		return (M)this;
	}

	public java.lang.Boolean getAliveForm() {
		return get("alive_form");
	}

}