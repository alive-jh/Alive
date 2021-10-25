package com.wechat.alive.test;

import com.wechat.alive.AliveProgress;
import com.wechat.alive.ClassCourseSchedule;
import com.wechat.alive.DataHelper;
import com.wechat.alive.StudyDay;

import java.util.ArrayList;
import java.util.List;


public class Test {
	private static List<AbstractData> testDataList = new ArrayList<AbstractData>();
	
	public static void main(String[] args) {
		new TestData01();
		new TestData02();
		new TestData03();
		new TestData04();
		new TestData05();
		new TestData06();
		new TestData07();
		new TestData08();
		new TestData09();
		new TestData10();
		new TestData11();
		new TestData12();
		new TestData13();
		new TestData14();
		new TestData15();
		new TestData16();
		new TestData17();
		new TestData18();
		new TestData19();
		new TestData20();
		new TestData21();
		new TestData22();
		for (AbstractData testData : testDataList) {
			AliveProgress aliveProgress = new AliveProgress(testData);
			aliveProgress.updateProgress(8, 496);
		}
		
//		List<Integer> alist = new ArrayList<Integer>();
//		alist.add(123);
//		alist.add(123456789);
//		alist.add(890);
//		
//		List<Integer> blist = new ArrayList<Integer>();
//		blist.add(123456789);
//		blist.add(890);
//		blist.add(123);
//		blist.add(123);
//		boolean is = alist.containsAll(blist);
//		System.out.println(is);
	}
	
	public static abstract class AbstractData implements DataHelper {
		
		public AbstractData() {
			testDataList.add(this);
		}
		
		@Override
		public ClassCourseSchedule getClassCourseSchedule(int studentId, int classGradeId) {
			ClassCourseSchedule ccs = new ClassCourseSchedule();
			ccs.setId(1);
			ccs.setStudentId(8);
			ccs.setClassGradesId(496);
			ccs.setClassCourseId(ClassCourseSchedule.TAG_FINISH);
			ccs.setClassRoomId(-1);
			ccs.setDoDay(-1);
			ccs.setStartTime("2020-01-01 10:00:00");
			ccs.setLastTime("2020-01-01 10:00:00");
			return ccs;
		}
		
		@Override
		public StudyDay getStudyDay(int studentId, int classGradeId) {
			StudyDay studyDay = new StudyDay();
			studyDay.setStudentId(studentId);
			studyDay.setClassGradeId(classGradeId);
			studyDay.setRuleType(1);//TODO
			studyDay.setWeek("1110111");//TODO
			studyDay.setIsTeacherDefault(0);//TODO
			return studyDay;
		}
	}
}
