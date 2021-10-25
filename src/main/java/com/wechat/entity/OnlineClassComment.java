package com.wechat.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * ClassCourse entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "online_class_comment", catalog = "wechat")
public class OnlineClassComment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer teacherId;
	private Integer score;
	private Integer modelId;
	private Integer gradesId;
	private String title;
	private String content;
	private String studentIds;
	private String commentSound;
	private String startDate;
	private String endDate;
	private String insertDate;

	// Constructors

	/** default constructor */
	public OnlineClassComment() {
	}

	/** full constructor */
	public OnlineClassComment(Integer teacherId, Integer score, Integer modelId,
			Integer gradesId, String title,String content, String studentIds
			, String commentSound, String startDate, String endDate, String insertDate) {
		this.teacherId = teacherId;
		this.score = score;
		this.modelId = modelId;
		this.gradesId = gradesId;
		this.title = title;
		this.content=content;
		this.studentIds = studentIds;
		
		this.commentSound = commentSound;
		this.startDate=startDate;
		this.endDate = endDate;
		this.insertDate = insertDate;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "teacherId")
	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Column(name = "modelId")
	public Integer getModelId() {
		return this.modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	@Column(name = "gradesId")
	public Integer getGradesId() {
		return this.gradesId;
	}

	public void setGradesId(Integer gradesId) {
		this.gradesId = gradesId;
	}



	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "studentIds")
	public String getStudentIds() {
		return studentIds;
	}

	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}
	
	@Column(name = "commentSound")
	public String getCommentSound() {
		return commentSound;
	}

	public void setCommentSound(String commentSound) {
		this.commentSound = commentSound;
	}

	@Column(name = "startDate")
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@Column(name = "endDate")
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "insertDate")
	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

}