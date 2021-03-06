package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseClassCourseRankingList<M extends BaseClassCourseRankingList<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setStudentId(java.lang.Integer studentId) {
		set("studentId", studentId);
		return (M)this;
	}

	public java.lang.Integer getStudentId() {
		return getInt("studentId");
	}

	public M setStudentName(java.lang.String studentName) {
		set("studentName", studentName);
		return (M)this;
	}

	public java.lang.String getStudentName() {
		return getStr("studentName");
	}

	public M setEpalId(java.lang.String epalId) {
		set("epalId", epalId);
		return (M)this;
	}

	public java.lang.String getEpalId() {
		return getStr("epalId");
	}

	public M setTotalTimeCost(java.lang.Integer totalTimeCost) {
		set("totalTimeCost", totalTimeCost);
		return (M)this;
	}

	public java.lang.Integer getTotalTimeCost() {
		return getInt("totalTimeCost");
	}

	public M setTotalScore(java.lang.Integer totalScore) {
		set("totalScore", totalScore);
		return (M)this;
	}

	public java.lang.Integer getTotalScore() {
		return getInt("totalScore");
	}

	public M setTimes(java.lang.Integer times) {
		set("times", times);
		return (M)this;
	}

	public java.lang.Integer getTimes() {
		return getInt("times");
	}

	public M setPerScore(java.lang.Integer perScore) {
		set("perScore", perScore);
		return (M)this;
	}

	public java.lang.Integer getPerScore() {
		return getInt("perScore");
	}

	public M setStartTime(java.lang.String startTime) {
		set("startTime", startTime);
		return (M)this;
	}

	public java.lang.String getStartTime() {
		return getStr("startTime");
	}

	public M setEndTime(java.lang.String endTime) {
		set("endTime", endTime);
		return (M)this;
	}

	public java.lang.String getEndTime() {
		return getStr("endTime");
	}

	public M setClassGradesList(java.lang.String classGradesList) {
		set("classGradesList", classGradesList);
		return (M)this;
	}

	public java.lang.String getClassGradesList() {
		return getStr("classGradesList");
	}

	public M setCategoryId(java.lang.Integer categoryId) {
		set("category_id", categoryId);
		return (M)this;
	}

	public java.lang.Integer getCategoryId() {
		return getInt("category_id");
	}

	public M setMobile(java.lang.String mobile) {
		set("mobile", mobile);
		return (M)this;
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public M setReadStatus(java.lang.Integer readStatus) {
		set("read_status", readStatus);
		return (M)this;
	}

	public java.lang.Integer getReadStatus() {
		return getInt("read_status");
	}

}
