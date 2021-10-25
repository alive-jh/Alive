package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Test entity. @author zlisten
 */
@Entity
@Table(name = "eval_template", catalog = "wechat")
public class EvalTemplate implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	private Integer id;
	private Integer status;
	private Integer sort;
	private Timestamp creatTime;
	private Timestamp editTime;
	
	private String type;
	private Integer teacherId;
	private Integer gradeId;
	private String sound;
	private String text;
	private String name;
	private Integer startScore;
	private Integer endScore;
	

	// Constructors

	/** default constructor */
	public EvalTemplate() {
	}

	/** full constructor */
	public EvalTemplate(Integer status,Integer sort,String type,String groups,
			Integer teacherId,Integer gradeId,String sound,String text,String name,Integer startScore,Integer endScore){
		this.status = status;
		this.sort = sort;
		
		this.type = type;
		this.teacherId = teacherId;
		this.gradeId = gradeId;
		this.sound = sound;
		this.text = text;
		this.name = name;
		this.startScore = startScore;
		this.endScore = endScore;
		
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
	
	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "teacher_id")
	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	@Column(name = "grade_id")
	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	@Column(name = "sound")
	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}
	@Column(name = "text")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@Column(name = "start_score")
	public Integer getStartScore() {
		return startScore;
	}

	public void setStartScore(Integer startScore) {
		this.startScore = startScore;
	}
	@Column(name = "end_score")
	public Integer getEndScore() {
		return endScore;
	}

	public void setEndScore(Integer endScore) {
		this.endScore = endScore;
	}
	
}