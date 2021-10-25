package com.wechat.entity.dto;

public class ClassCourseEvaluateDto {
	
	private Integer studentId; //学生ID
	
	private Integer classGradesId; //所属班级
	
	private Integer courseSum; //学习了几个课时
	
	private Integer scoreSum; //学习总分数
	
	private Integer timeSum; //学习总时长
	

	public ClassCourseEvaluateDto(Integer studentId, Integer classGradesId,
			Integer courseSum, Integer scoreSum, Integer timeSum) {
		super();
		this.studentId = studentId;
		this.classGradesId = classGradesId;
		this.courseSum = courseSum;
		this.scoreSum = scoreSum;
		this.timeSum = timeSum;
	}

	public ClassCourseEvaluateDto() {
		
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getClassGradesId() {
		return classGradesId;
	}

	public void setClassGradesId(Integer classGradesId) {
		this.classGradesId = classGradesId;
	}

	public Integer getScoreSum() {
		return scoreSum;
	}

	public void setScoreSum(Integer scoreSum) {
		this.scoreSum = scoreSum;
	}

	public Integer getTimeSum() {
		return timeSum;
	}

	public void setTimeSum(Integer timeSum) {
		this.timeSum = timeSum;
	}

	public Integer getCourseSum() {
		return courseSum;
	}

	public void setCourseSum(Integer courseSum) {
		this.courseSum = courseSum;
	}
	
	

}
