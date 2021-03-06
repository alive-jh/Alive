package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMediainfoWords<M extends BaseMediainfoWords<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setAudioId(java.lang.Integer audioId) {
		set("audio_id", audioId);
		return (M)this;
	}

	public java.lang.Integer getAudioId() {
		return getInt("audio_id");
	}

	public M setWords(java.lang.String words) {
		set("words", words);
		return (M)this;
	}

	public java.lang.String getWords() {
		return getStr("words");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

}
