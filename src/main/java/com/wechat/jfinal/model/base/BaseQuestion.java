package com.wechat.jfinal.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseQuestion<M extends BaseQuestion<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setQuestion(java.lang.String question) {
		set("question", question);
		return (M)this;
	}

	public java.lang.String getQuestion() {
		return getStr("question");
	}

	public M setQuestionType(java.lang.String questionType) {
		set("question_type", questionType);
		return (M)this;
	}

	public java.lang.String getQuestionType() {
		return getStr("question_type");
	}

	public M setInsertDate(java.util.Date insertDate) {
		set("insert_date", insertDate);
		return (M)this;
	}

	public java.util.Date getInsertDate() {
		return get("insert_date");
	}

}