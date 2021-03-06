package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassCourse<M extends BaseClassCourse<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDoTitle(java.lang.String doTitle) {
		set("do_title", doTitle);
		return (M)this;
	}

	public java.lang.String getDoTitle() {
		return getStr("do_title");
	}

	public M setDoSlot(java.lang.Integer doSlot) {
		set("do_slot", doSlot);
		return (M)this;
	}

	public java.lang.Integer getDoSlot() {
		return getInt("do_slot");
	}

	public M setDoDay(java.lang.Integer doDay) {
		set("do_day", doDay);
		return (M)this;
	}

	public java.lang.Integer getDoDay() {
		return getInt("do_day");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setClassGradesId(java.lang.Integer classGradesId) {
		set("class_grades_id", classGradesId);
		return (M)this;
	}

	public java.lang.Integer getClassGradesId() {
		return getInt("class_grades_id");
	}

	public M setCover(java.lang.String cover) {
		set("cover", cover);
		return (M)this;
	}

	public java.lang.String getCover() {
		return getStr("cover");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setDoDate(java.lang.String doDate) {
		set("do_date", doDate);
		return (M)this;
	}

	public java.lang.String getDoDate() {
		return getStr("do_date");
	}

	public M setDifficultyLevel(java.lang.Integer difficultyLevel) {
		set("difficulty_level", difficultyLevel);
		return (M)this;
	}

	public java.lang.Integer getDifficultyLevel() {
		return getInt("difficulty_level");
	}

	public M setLiveDodate(java.util.Date liveDodate) {
		set("live_dodate", liveDodate);
		return (M)this;
	}

	public java.util.Date getLiveDodate() {
		return get("live_dodate");
	}

	public M setTakePicture(java.lang.Integer takePicture) {
		set("take_picture", takePicture);
		return (M)this;
	}

	public java.lang.Integer getTakePicture() {
		return getInt("take_picture");
	}

	public M setLiveMode(java.lang.Integer liveMode) {
		set("live_mode", liveMode);
		return (M)this;
	}

	public java.lang.Integer getLiveMode() {
		return getInt("live_mode");
	}

	public M setBLive(java.lang.Integer bLive) {
		set("b_live", bLive);
		return (M)this;
	}

	public java.lang.Integer getBLive() {
		return getInt("b_live");
	}

	public M setTakeTime(java.lang.Integer takeTime) {
		set("take_time", takeTime);
		return (M)this;
	}

	public java.lang.Integer getTakeTime() {
		return getInt("take_time");
	}

}
