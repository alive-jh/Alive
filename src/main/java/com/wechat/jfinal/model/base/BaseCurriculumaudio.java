package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseCurriculumaudio<M extends BaseCurriculumaudio<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCurriculumid(java.lang.Integer curriculumid) {
		set("curriculumid", curriculumid);
		return (M)this;
	}

	public java.lang.Integer getCurriculumid() {
		return getInt("curriculumid");
	}

	public M setAudioid(java.lang.Integer audioid) {
		set("audioid", audioid);
		return (M)this;
	}

	public java.lang.Integer getAudioid() {
		return getInt("audioid");
	}

}
