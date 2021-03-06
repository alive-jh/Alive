package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseEpalsystem<M extends BaseEpalsystem<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEpalid(java.lang.String epalid) {
		set("epalid", epalid);
		return (M)this;
	}

	public java.lang.String getEpalid() {
		return getStr("epalid");
	}

	public M setRecommend(java.lang.Integer recommend) {
		set("recommend", recommend);
		return (M)this;
	}

	public java.lang.Integer getRecommend() {
		return getInt("recommend");
	}

	public M setSchedule(java.lang.Integer schedule) {
		set("schedule", schedule);
		return (M)this;
	}

	public java.lang.Integer getSchedule() {
		return getInt("schedule");
	}

	public M setTesting(java.lang.Integer testing) {
		set("testing", testing);
		return (M)this;
	}

	public java.lang.Integer getTesting() {
		return getInt("testing");
	}

	public M setDistinguish(java.lang.Integer distinguish) {
		set("distinguish", distinguish);
		return (M)this;
	}

	public java.lang.Integer getDistinguish() {
		return getInt("distinguish");
	}

	public M setChat(java.lang.Integer chat) {
		set("chat", chat);
		return (M)this;
	}

	public java.lang.Integer getChat() {
		return getInt("chat");
	}

	public M setIntelligentScore(java.lang.Integer intelligentScore) {
		set("intelligent_score", intelligentScore);
		return (M)this;
	}

	public java.lang.Integer getIntelligentScore() {
		return getInt("intelligent_score");
	}

	public M setBQuiet(java.lang.Integer bQuiet) {
		set("b_quiet", bQuiet);
		return (M)this;
	}

	public java.lang.Integer getBQuiet() {
		return getInt("b_quiet");
	}

	public M setPeriod(java.lang.String period) {
		set("period", period);
		return (M)this;
	}

	public java.lang.String getPeriod() {
		return getStr("period");
	}

}
