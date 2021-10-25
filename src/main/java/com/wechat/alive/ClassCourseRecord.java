package com.wechat.alive;

public class ClassCourseRecord {
	private int studentId;
	private int classGradesId;
	private int classRoomId;
	private int classCourseId;
	private int complete;
	private String completeTime;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
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
	public int getClassCourseId() {
		return classCourseId;
	}
	public void setClassCourseId(int classCourseId) {
		this.classCourseId = classCourseId;
	}
	public int getComplete() {
		return complete;
	}
	public void setComplete(int complete) {
		this.complete = complete;
	}
	public String getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}
}
