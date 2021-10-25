package com.wechat.alive;

import java.util.ArrayList;
import java.util.List;

public class ClassCourse {
	private List<Course> courseList;
	
	public ClassCourse(List<Course> courseList) {
		this.courseList = courseList;
	}
	
	/**
	 * ��ָ��doDay��ʼ�����ҵ���һ������
	 * */
	public Course getPrimaryClassCourseFrom(int doDay) {
		for (Course course : courseList) {
			if (course.getDoDay() >= doDay && course.isPrimary()) {
				return course;
			}
		}
		return null;
	}
	
	/**
	 * ��ȡĳһ�����������
	 * */
	public List<Integer> getPrimaryClassCourses(int doDay) {
		List<Integer> primaryList = new ArrayList<Integer>();
		for (Course course : courseList) {
			if (course.getDoDay() == doDay && course.isPrimary()) {
				primaryList.add(course.getId());
			}
		}
		return primaryList;
	}
	
	/**
	 * �ж�ĳһ���Ƿ��пγ�
	 * */
	public boolean hasCourse(int doDay) {
		for (Course course : courseList) {
			if (course.getDoDay() == doDay) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * �ж�ĳһ�죨fromDoDay��֮���Ƿ��пγ̣�����fromDoDay��һ�죩
	 * */
	public boolean hasMoreCourse(int fromDoDay) {
		//�������򣬿����һ�ڿε�doday�Ƿ���ڻ����fromDoDay
		return courseList.size() > 0 && courseList.get(courseList.size() - 1).doDay >= fromDoDay;
	}
	
	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

	public static class Course implements Comparable<Course> {
		private int id;
		private String doTitle;
		private int doSlot;
		private int doDay;
		private int classRoomId;
		private int classGradesId;
		
		public static final int SLOT_MORNING = 100;//���
		public static final int SLOT_PRIMARY = 300;//����
		public static final int SLOT_EVENING = 500;//����
		
		
		public Course(int id, String doTitle, int doSlot, int doDay,
				int classRoomId, int classGradesId) {
			super();
			this.id = id;
			this.doTitle = doTitle;
			this.doSlot = doSlot;
			this.doDay = doDay;
			this.classRoomId = classRoomId;
			this.classGradesId = classGradesId;
		}

		/**
		 * �Ƿ�������
		 * */
		public boolean isPrimary() {
			return doSlot == SLOT_PRIMARY;
		}
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getDoTitle() {
			return doTitle;
		}
		public void setDoTitle(String doTitle) {
			this.doTitle = doTitle;
		}
		public int getDoSlot() {
			return doSlot;
		}
		public void setDoSlot(int doSlot) {
			this.doSlot = doSlot;
		}
		public int getDoDay() {
			return doDay;
		}
		public void setDoDay(int doDay) {
			this.doDay = doDay;
		}
		public int getClassRoomId() {
			return classRoomId;
		}
		public void setClassRoomId(int classRoomId) {
			this.classRoomId = classRoomId;
		}
		public int getClassGradesId() {
			return classGradesId;
		}
		public void setClassGradesId(int classGradesId) {
			this.classGradesId = classGradesId;
		}
		@Override
		public int compareTo(Course o) {
			if (o == null) {
				return -1;
			}
			return this.doDay - o.doDay;
		}
	}
}
