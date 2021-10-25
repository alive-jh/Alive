package com.wechat.alive;

import com.wechat.alive.ClassCourse.Course;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AliveProgress {
	private DataHelper dataHelper;
	
	public final static String NOT_STUDY_DAY = "NOT_STUDY_DAY";
	public final static String ALREADY_UPDATE_TODAY = "ALREADY_UPDATE_TODAY";
	public final static String NO_COURSE = "NO_COURSE";
	public final static String NO_MORE_COURSE_FROM_CURRENT_DAY = "NO_MARE_COURSE_FROM_CURRENT_DAY";
	public final static String PRIMARY_NOT_FINISH = "PRIMARY_NOT_FINISH";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public AliveProgress(DataHelper dataHelper) {
		this.dataHelper = dataHelper;
	}
	
	public void updateProgress(int studentId, int classGradeId) {
		
		StudyDay studyDay = dataHelper.getStudyDay(studentId, classGradeId);
		
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (!studyDay.isDayForClass(dayOfWeek)) {
			dataHelper.noChange(NOT_STUDY_DAY);
			return;
		}
		
		ClassCourseSchedule ccs = dataHelper.getClassCourseSchedule(studentId, classGradeId);
//		if (ccs.isFinish()) {
//			if (ccs.isUpdateToday()) {
//				
//			} else {
//				//go ahead
//			}
//		} else {
//			if (ccs.isUpdateToday()) {
//				dataHelper.noChange(ALREADY_UPDATE_TODAY);
//				return;
//			} else {
//				//go ahead
//			}
//		}
		if (!ccs.isFinish() && ccs.isUpdateToday()) {
			dataHelper.noChange(ALREADY_UPDATE_TODAY);
			return;
		}
		
		ClassCourse classCourse = dataHelper.getClassCourse(classGradeId, ccs.getDoDay());
		if (classCourse == null) {
			dataHelper.noChange(NO_COURSE);
			return;
		}
		
		if (!classCourse.hasMoreCourse(ccs.getDoDay())) {
			dataHelper.noChange(NO_MORE_COURSE_FROM_CURRENT_DAY);
			return;
		}
//		if (studyDay.isTeacherDefault()) {
		
			if (ccs.isFinish() && classCourse.hasCourse(ccs.getDoDay())) {//֮ǰ�Ѿ�ѧ�����пγ̣�Ȼ����ʦ�����༶������ӿγ�
				int nextDoDay = ccs.getDoDay();
				Course nextPrimaryCourse = classCourse.getPrimaryClassCourseFrom(ccs.getDoDay());
				dataHelper.updateClassCourseSchedule(ccs.getId(),
						nextPrimaryCourse != null ? nextPrimaryCourse.getId() : ClassCourseSchedule.TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE,//���û�������ˣ��ͼ�¼��0
						nextPrimaryCourse != null ? nextPrimaryCourse.getClassRoomId() : -1,//���û�������ˣ��ͼ�¼��-1
						nextDoDay);//�����ǰ�Ѿ������һ�죬�Ͱ�doDayָ����һ��
				return;
			}
			
			String startTime = ccs.getLastTime().substring(0, 10) + " 00:00:00";//��һ�θ���ʱ������賿0��
			String endTime = sdf.format(System.currentTimeMillis()) + " 00:00:00";//�����賿0��
//			System.out.println(startTime + " ----> " + endTime);
			List<Integer> recordList = dataHelper.getRecordsAfterTime(studentId, classGradeId, startTime, endTime);//��һ�θ��½���֮�󵽽���֮ǰ��ɹ������пγ�
			List<Integer> primaryList = classCourse.getPrimaryClassCourses(ccs.getDoDay());//ָ����һ�����������
			
			if (recordList.containsAll(primaryList)) {//�ϴθ��½��Ⱥ�����˽����е���������
				//���½���
				int nextDoDay = -1;
				Course nextPrimaryCourse = null;
				List<Course> courseList = classCourse.getCourseList();
				int size = courseList.size();
				for (int i = 0; i < size; i++) {
					Course c = courseList.get(i);
					if (c.getDoDay() > 0 //aliveֱ����doday��0�����˵�
							&& (c.getDoDay() > ccs.getDoDay()
									/*|| (!ccs.hasPrimaryCourse() && c.getDoDay() == ccs.getDoDay())*/)) {//֮ǰ�Ѿ�ѧ�����пγ̣�Ȼ����ʦ�����༶������ӿγ�
						if (nextDoDay < 0) {
							//ȡ��һ�����ڵ�ǰdoDay��doDay
							nextDoDay = c.getDoDay();
						}
						if (c.isPrimary()) {
							//ȡ��һ��doDay���ڵ�ǰdoDay������
							nextPrimaryCourse = c;
							break;//nextDoDay �� nextPrimaryCourse ���ҵ��˾Ϳ����˳�ѭ��
						}
					}
				}
				
//				if (nextDoDay < 0 && !classCourse.hasCourse(ccs.getDoDay())) {
//					dataHelper.noChange(NO_MORE_COURSE_FROM_CURRENT_DAY);
//					return;//��ǰ��������û�пΣ��γ̱����Ҳû�и���ĿΣ����Ȳ���
//				}
//				
				dataHelper.updateClassCourseSchedule(ccs.getId(),
						nextPrimaryCourse != null ? nextPrimaryCourse.getId() : 
							nextDoDay > 0 ? ClassCourseSchedule.TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE : ClassCourseSchedule.TAG_FINISH,//���û�������ˣ����ݿγ��Ƿ���ѧ���жϸ�ֵ
						nextPrimaryCourse != null ? nextPrimaryCourse.getClassRoomId() : -1,//���û�������ˣ��ͼ�¼��-1
						nextDoDay > 0 ? nextDoDay : ccs.getDoDay() + 1);//�����ǰ�Ѿ������һ�죬�Ͱ�doDayָ����һ��
				
			} else {
//				if (ccs.isFinish() && classCourse.hasCourse(ccs.getDoDay())) {//֮ǰ�Ѿ�ѧ�����пγ̣�Ȼ����ʦ�����༶������ӿγ�
//					int nextDoDay = ccs.getDoDay();
//					Course nextPrimaryCourse = classCourse.getPrimaryClassCourseFrom(ccs.getDoDay());
//					dataHelper.updateClassCourseSchedule(ccs.getId(),
//							nextPrimaryCourse != null ? nextPrimaryCourse.getId() : ClassCourseSchedule.TAG_NOT_FINISH_BUT_NO_MORE_PRIMARY_COURSE,//���û�������ˣ��ͼ�¼��0
//							nextPrimaryCourse != null ? nextPrimaryCourse.getClassRoomId() : -1,//���û�������ˣ��ͼ�¼��-1
//							nextDoDay);//�����ǰ�Ѿ������һ�죬�Ͱ�doDayָ����һ��
//					return;
//				}
				dataHelper.noChange(PRIMARY_NOT_FINISH);
			}
//		} else {
			
//		}
	}
}
