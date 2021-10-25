package com.wechat.entity.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

public class ClassRoomDto {

	private Integer id;
	
	private String className;
	
	private Integer teacherId;
	
	private String teacherName;
	
	private String summary;
	
	private String cover;
	
	private Timestamp createTime;
	
	private BigInteger studiedStuCount;
	
	private BigInteger studiedScriCount;
	
	private Integer bookResId;
	
	private Integer sort;
	
	private Integer status;
	
	private Integer categoryId;
	
	private String groupId;
	
	private String bookResIds;
	
	private String classRoomType;
	
	private String videoUrl;
	
	private Integer totalTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigInteger getStudiedStuCount() {
		return studiedStuCount;
	}

	public void setStudiedStuCount(BigInteger studiedStuCount) {
		this.studiedStuCount = studiedStuCount;
	}

	public BigInteger getStudiedScriCount() {
		return studiedScriCount;
	}

	public void setStudiedScriCount(BigInteger studiedScriCount) {
		this.studiedScriCount = studiedScriCount;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getBookResId() {
		return bookResId;
	}

	public void setBookResId(Integer bookResId) {
		this.bookResId = bookResId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getBookResIds() {
		return bookResIds;
	}

	public void setBookResIds(String bookResIds) {
		this.bookResIds = bookResIds;
	}
	
	public String getClassRoomType() {
		return classRoomType;
	}

	public void setClassRoomType(String classRoomType) {
		this.classRoomType = classRoomType;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}	
	
	public Integer getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
}
