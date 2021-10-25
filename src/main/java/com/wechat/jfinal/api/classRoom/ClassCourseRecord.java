package com.wechat.jfinal.api.classRoom;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourseRecordOfStudent;
import com.wechat.jfinal.model.ClassCourseRecordOfTeacher;
import com.wechat.util.ParameterFilter;

import java.util.Calendar;



public class ClassCourseRecord extends Controller{
	//老师保存课堂记录
	/**
	 * 参数：
	 * 	classCourseId:课程表ID
	 * 	content：记录内容
	 * 	images：图片列表
	 * 	teacherId:老师ID
	 * 	
	 * 
	 * */
	public void saveRecordOfTeacher(){
		ClassCourseRecordOfTeacher classCourseRecordOfTeacher = getBean(ClassCourseRecordOfTeacher.class,"");
		if(null!=classCourseRecordOfTeacher.getId()){ //id不为空，修改
			classCourseRecordOfTeacher.update();
		}else{
			//System.out.println(classCourseRecordOfTeacher.getContent());
			classCourseRecordOfTeacher.save();
		}
		Result.ok(classCourseRecordOfTeacher,this);
	}

	/**
	 * 参数：
	 * 	classCourseId:课程表ID
	 * 	content：记录内容
	 * 	images：图片列表
	 * 	student:学生ID
	 * */
	public void saveRecordOfStudent(){
		ClassCourseRecordOfStudent classCourseRecordOfStudent = getBean(ClassCourseRecordOfStudent.class,"");
		if(null!=classCourseRecordOfStudent.getId()){ //id不为空，修改
			classCourseRecordOfStudent.update();
		}else{
			classCourseRecordOfStudent.save();
		}
		Result.ok(classCourseRecordOfStudent,this);
	}
	
	
	/**
	 * 参数：老师获取课堂记录
	 * 	classCourseId:课程表ID
	 *	teacherId：老师ID
	 *	pageNumber：页码
	 *	pageSize：每页数据条数
	 * 	
	 * 
	 * */
	public void getRecordOfTeacher(){
		Integer teacherId = getParaToInt("teacherId",0);
		Integer classCourseId = getParaToInt("classCourseId",0);
		Integer pageNumber = getParaToInt("pageNumber",1);
		Integer pageSize = getParaToInt("pageSize",10);
		Page<Record> dataList  = Db.paginate(pageNumber, pageSize,"select *" ,"from class_course_record_of_teacher where class_course_id=? and teacher_id=?",classCourseId,teacherId);
		
		Result.ok(dataList,this);
	}
	
	
	/**
	 * 学生获取课堂记录
	 * 	classCourseId:课程表ID
	 *	studentId：学生ID
	 *	pageNumber：页码
	 *	pageSize：每页数据条数
	 * 
	 * */
	public void getRecordOfStudent(){
		Integer studentId = getParaToInt("studentId",0);
		Integer classCourseId = getParaToInt("classCourseId",0);
		Integer pageNumber = getParaToInt("pageNumber",1);
		Integer pageSize = getParaToInt("pageSize",10);
		Page<Record> dataList  = Db.paginate(pageNumber, pageSize, "select *","from class_course_record_of_student where class_course_id=? and student_id=?",classCourseId,studentId);
		Result.ok(dataList,this);
	}

	public void  saveClassCourseRecord()  {
		//获取前端传过来的上课记录字段
		Integer id = getParaToInt("id");
		Integer studentId = getParaToInt("student_id");
		Integer classGradesId = getParaToInt("class_grades_id");
		Integer classCourseId = getParaToInt("class_course_id");
		Integer classRoomId = getParaToInt("class_room_id");
		Integer complete = getParaToInt("complete");
		Integer score = getParaToInt("score",0);
		String doTitle = getPara("do_title");
		Integer usedTime = getParaToInt("used_time");
		String completeTime = getPara("complete_time");

		//转换时间Date对象
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(completeTime));


		//根据student_id和classCourse_id 去查询class_course_record表
		com.wechat.jfinal.model.ClassCourseRecord first = com.wechat.jfinal.model.ClassCourseRecord.dao.findFirst
				("select * from class_course_record where class_course_id = ? and student_id=?", classCourseId, studentId);

		com.wechat.jfinal.model.ClassCourseRecord classCourseRecord = null;

			//判断课程记录是否为空  不为空时则是更新
			if (first != null){

				String score1 = ParameterFilter.emptyFilter("0",first.getScore());
				//取出数据库字段 转换类型 再与之对比
				if (score>Integer.parseInt(score1)){
					first.setScore(score.toString());
				}

				first.setCompleteTime(calendar.getTime());
				first.update();

			}else {
				//课程记录为空时则创建并添加一条记录
				classCourseRecord = new com.wechat.jfinal.model.ClassCourseRecord();
				classCourseRecord.setComplete(complete);
				classCourseRecord.setCompleteTime(calendar.getTime());
				classCourseRecord.setUsedTime(usedTime);
				classCourseRecord.setScore(score.toString());
				classCourseRecord.setDoTitle(doTitle);
				classCourseRecord.setClassCourseId(classCourseId);
				classCourseRecord.setClassRoomId(classRoomId);
				classCourseRecord.setClassGradesId(classGradesId);
				classCourseRecord.setStudentId(studentId);
				classCourseRecord.save();

			    }

			    Result.ok(this);
		}


	}

