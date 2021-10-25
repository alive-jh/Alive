package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Test entity. @author zlisten
 */
@Entity
@Table(name = "eval_question", catalog = "wechat")
public class EvalQuestion implements java.io.Serializable {

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
	
	private String text;
	private String picUrl;
	private String soundUrl;
	private String explains;
	private String explainSoundUrl;
	
	private String type; 
	private List<EvalOption> options;


	// Constructors

	/** default constructor */
	public EvalQuestion() {
	}

	/** full constructor */
	public EvalQuestion(Integer status,Integer sort,String text,String picUrl,String soundUrl,
			String explains,String explainSoundUrl,String type){
		this.status = status;
		this.sort = sort;
		this.text = text;
		this.picUrl = picUrl;
		this.soundUrl = soundUrl;
		this.explains = explains;
		this.explainSoundUrl = explainSoundUrl;
		this.type = type;
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
	@Column(name = "explains")
	public String getExplain() {
		return explains;
	}

	public void setExplain(String explain) {
		this.explains = explain;
	}
	@Column(name = "explain_sound_url")
	public String getExplainSoundUrl() {
		return explainSoundUrl;
	}

	public void setExplainSoundUrl(String explainSoundUrl) {
		this.explainSoundUrl = explainSoundUrl;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	@Transient
	public List<EvalOption> getOptions() {
		return options;
	}

	public void setOptions(List<EvalOption> options) {
		this.options = options;
	}

}