package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course_period")
public class CoursePeriod {

	@Id
	@GeneratedValue(generator ="courseGenerator")       
    @GenericGenerator(name ="courseGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name="courseperiod_name")
	private String courseperiodName;	//课时名
	
	@Column(name="missionCmdList")
	private String missionCmdList;	//指令集
	
	@Column(name="course_id")
	private Integer courseId;	//子课程ID
	
	
	public String getCourseperiodName() {
		return courseperiodName;
	}

	public void setCourseperiodName(String courseperiodName) {
		this.courseperiodName = courseperiodName;
	}

	public String getMissionCmdList() {
		return missionCmdList;
	}

	public void setMissionCmdList(String missionCmdList) {
		this.missionCmdList = missionCmdList;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	
}
