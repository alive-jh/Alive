package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseLessonAdRel<M extends BaseLessonAdRel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setClassRoomId(java.lang.Integer classRoomId) {
		set("class_room_id", classRoomId);
		return (M)this;
	}

	public java.lang.Integer getClassRoomId() {
		return getInt("class_room_id");
	}

	public M setLessonAds(java.lang.Integer lessonAds) {
		set("lesson_ads", lessonAds);
		return (M)this;
	}

	public java.lang.Integer getLessonAds() {
		return getInt("lesson_ads");
	}

	public M setLessonShareRuleId(java.lang.Integer lessonShareRuleId) {
		set("lesson_share_rule_id", lessonShareRuleId);
		return (M)this;
	}

	public java.lang.Integer getLessonShareRuleId() {
		return getInt("lesson_share_rule_id");
	}

	public M setClassGradeId(java.lang.String classGradeId) {
		set("class_grade_id", classGradeId);
		return (M)this;
	}

	public java.lang.String getClassGradeId() {
		return getStr("class_grade_id");
	}

}
