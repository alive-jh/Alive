package com.wechat.alive.sample;


import com.jfinal.json.FastJson;
import com.jfinal.json.JFinalJson;
import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.alive.*;
import com.wechat.jfinal.model.StdDiyStudyDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sample {
	public void updateProgress(int studentId, int classGradesId) {
		AliveProgress aliveProgress = new AliveProgress(new DataHelper() {

			@Override
			public StudyDay getStudyDay(int studentId, int classGradeId) {
				/*
				 * TODO ��ȡѧ�����Ͽ�ʱ�䰲��
				 * select * from std_diy_study_day where std_id=studentId and grade_id=classGradeId;
				 * */
				StdDiyStudyDay stdDiyStudyDay = StdDiyStudyDay.dao.findFirst("select * from std_diy_study_day where std_id=? and grade_id=?",studentId,classGradeId);
				if (stdDiyStudyDay == null){
					stdDiyStudyDay = new StdDiyStudyDay();
					stdDiyStudyDay.setStdId(studentId);
					stdDiyStudyDay.setGradeId(classGradeId);
					stdDiyStudyDay.setRule("[{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":0},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":1},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":2},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":3},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":4},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":5},{\"data\":[{\"id\":100,\"time\":\"07:00:00\",\"name\":\"早读\"},{\"id\":300,\"time\":\"19:00:00\",\"name\":\"课堂\"},{\"id\":500,\"time\":\"20:00:00\",\"name\":\"晚听\"}],\"sort\":6}]");
					stdDiyStudyDay.setWeek("1111111");
					stdDiyStudyDay.setIsTeacherDefault(1);
					stdDiyStudyDay.save();
				}
				return FastJson.getJson().parse(JFinalJson.getJson().toJson(CPI.getAttrs(stdDiyStudyDay)), StudyDay.class);
			}

			@Override
			public ClassCourseSchedule getClassCourseSchedule(int studentId, int classGradeId) {
				/*
				 * TODO ��ȡ��ǰ��¼�Ľ���
				 * select * from class_course_schedule where class_grades_id=classGradeId and student_id=studentId;
				 * */
				com.wechat.jfinal.model.ClassCourseSchedule classCourseSchedule = com.wechat.jfinal.model.ClassCourseSchedule.dao.findFirst("select * from class_course_schedule where class_grades_id=? and student_id=?",classGradeId,studentId);

				if (classCourseSchedule == null) {
					com.wechat.jfinal.model.ClassCourse classCourse = com.wechat.jfinal.model.ClassCourse.dao.findFirst("SELECT * FROM `class_course` WHERE class_grades_id = ? AND do_slot = 300 ORDER BY do_day ASC LIMIT 1", classGradeId);
					classCourseSchedule = new com.wechat.jfinal.model.ClassCourseSchedule();
					classCourseSchedule.setStudentId(studentId);
					classCourseSchedule.setClassCourseId(classCourse == null?0:classCourse.getId());
					classCourseSchedule.setClassGradesId(classGradeId);
					classCourseSchedule.setClassRoomId(classCourse == null?-1:classCourse.getClassRoomId());
					classCourseSchedule.setDoDay(classCourse == null?-1:classCourse.getDoDay());
					classCourseSchedule.setLastTime(new Date());
					classCourseSchedule.save();
				}

				return FastJson.getJson().parse(JFinalJson.getJson().toJson(CPI.getAttrs(classCourseSchedule)), ClassCourseSchedule.class);
			}

			@Override
			public void updateClassCourseSchedule(int id, int classCourseId, int classRoomId, int doDay) {
				/*
				 * TODO ���½���
				 * update class_course_schedule set class_course_id=classCourseId, class_room_id=classRoomId, do_day=doDay where id=id; 
				 * */
				Db.update("update class_course_schedule set class_course_id=?, class_room_id=?, do_day=? where id=?",classCourseId,classRoomId,doDay,id);
			}

			@Override
			public ClassCourse getClassCourse(int classGradeId, int startDoDay) {
				/*
				 * TODO ��ȡ��startDoDay֮��Ŀγ̱�����startDoDay���죩
				 * select * from class_course where class_grades_id=classGradeId and do_day>=startDoDay;
				 * */
				List<com.wechat.jfinal.model.ClassCourse> classCourses = com.wechat.jfinal.model.ClassCourse.dao.find("select * from class_course where class_grades_id=? and do_day>=?",classGradeId,startDoDay);
				List<ClassCourse.Course> courses = new ArrayList<ClassCourse.Course>(classCourses.size());
				classCourses.forEach(classCourse -> {
					courses.add(FastJson.getJson().parse(JFinalJson.getJson().toJson(CPI.getAttrs(classCourse)), ClassCourse.Course.class));
				});

				return new ClassCourse(courses);
			}

			@Override
			public List<Integer> getRecordsAfterTime(int studentId, int classGradesId, String startTime, String endTime) {
				/*
				 * TODO ��ȡѧ����ĳ���ָ࣬��ʱ�䣨startTime��֮����ɵ����пγ�<br/>
				 * select class_course_id from class_course_record where student_id=studentId
				 *  and class_grades_id=classGradesId and complete=1 and complete_time>=startTime and complete_time<endTime;
				 * */
				List<Record> records = Db.find("select class_course_id from class_course_record where student_id=? and class_grades_id=? and complete=1 and complete_time>=? and complete_time<?",studentId,classGradesId,startTime,endTime);
				List<Integer> ids = new ArrayList<Integer>(records.size());

				for (Record record:
						records) {
					ids.add(record.getInt("class_course_id"));
				}

				return ids;
			}

			@Override
			public void noChange(String log) {
				// TODO do nothing
			}
		});
		
		aliveProgress.updateProgress(studentId, classGradesId);
	}
}
