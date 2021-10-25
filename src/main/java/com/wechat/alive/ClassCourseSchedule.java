package com.wechat.alive;

import java.text.SimpleDateFormat;

public class ClassCourseSchedule {
	private int id;
	private int studentId;
	private int classCourseId;
	private int classGradesId;
	private int classRoomId;
	private int doDay;
	private String startTime;
	private String lastTime;
	
	public static final int TAG_FINISH = 0;
	public static final int TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE = -1;
	
	public boolean isUpdateToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String curDate = sdf.format(System.currentTimeMillis());
		return lastTime.startsWith(curDate);
	}
	
	public boolean hasPrimaryCourse() {
		return classCourseId > 0;
	}
	
	public boolean isFinish() {
		return classCourseId == TAG_FINISH;
	}
	
	public boolean isNotFinishButNoMorePrimaryCourse() {
		return classCourseId == TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getClassCourseId() {
		return classCourseId;
	}
	public void setClassCourseId(int classCourseId) {
		this.classCourseId = classCourseId;
	}
	public int getClassGradesId() {
		return classGradesId;
	}
	public void setClassGradesId(int classGradesId) {
		this.classGradesId = classGradesId;
	}
	public int getClassRoomId() {
		return classRoomId;
	}
	public void setClassRoomId(int classRoomId) {
		this.classRoomId = classRoomId;
	}
	public int getDoDay() {
		return doDay;
	}
	public void setDoDay(int doDay) {
		this.doDay = doDay;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}
