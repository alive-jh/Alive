package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Test entity. @author zlisten
 */
@Entity
@Table(name = "eval_option", catalog = "wechat")
public class EvalOption implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer status;
	private Integer sort;
	private Timestamp creatTime;
	private Timestamp editTime;
	
	private int type;
	private String text;
	private String picUrl;
	private String soundUrl;
	private int isCorrect;

	// Constructors

	/** default constructor */
	public EvalOption() {
	}

	/** full constructor */
	public EvalOption(Integer status,Integer sort,int type,String text, String picUrl, String soundUrl){
		this.status = status;
		this.sort = sort;
		this.type = type;
		this.text = text;
		this.picUrl = picUrl;
		this.soundUrl = soundUrl;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "creat_time", insertable =false, updatable = false)
	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	@Column(name = "edit_time", insertable =false, updatable = false)
	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	@Column(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column(name = "pic_url")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	@Column(name = "sound_url")
	public String getSoundUrl() {
		return soundUrl;
	}

	public void setSoundUrl(String soundUrl) {
		this.soundUrl = soundUrl;
	}
	@Transient
	public int getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	
}