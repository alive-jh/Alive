package com.wechat.alive;

public class StudyDay {
	private int studentId;
	private int classGradeId;
	private int ruleType;
	private String week;
	private int isTeacherDefault;
	
	/**
	 * ָ������һ���Ƿ��Ͽ�
	 * */
	public boolean isDayForClass(int dayOfWeek) {
		if (isTeacherDefault()) {
			return true;
		}
		return week.charAt(dayOfWeek) == '1';
	}
	
	public boolean isTeacherDefault() {
		return isTeacherDefault == 1;
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getClassGradeId() {
		return classGradeId;
	}
	public void setClassGradeId(int classGradeId) {
		this.classGradeId = classGradeId;
	}
	public int getRuleType() {
		return ruleType;
	}
	public void setRuleType(int ruleType) {
		this.ruleType = ruleType;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public int getIsTeacherDefault() {
		return isTeacherDefault;
	}
	public void setIsTeacherDefault(int isTeacherDefault) {
		this.isTeacherDefault = isTeacherDefault;
	}

	
}
