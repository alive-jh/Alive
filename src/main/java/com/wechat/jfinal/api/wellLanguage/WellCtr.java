package com.wechat.jfinal.api.wellLanguage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.LiveLesson;
import com.wechat.jfinal.model.LiveLessonRel;
import com.wechat.jfinal.model.WellLesson;
import com.wechat.util.MD5UTIL;

import net.sf.json.JSONObject;

public class WellCtr extends Controller{
	
	@ActionKey("/v2/well/record/upload")
	@EmptyParaValidate(params={"time","data","sign"})
	public void saveGradeInfo(){
		
		JSONObject data = null;
		
		try {
			data = JSONObject.fromObject(getPara("data"));
		} catch (Exception e) {
			e.printStackTrace();
			Result.error(20509, "data error",this);
			return;
		}
		
		String bespeak_id = data.getString("bespeak_id");
		
		//签名规则:well#fandou#{{time}}#{{bespeak_id}}32位md5加密大写
		String local_sign = MD5UTIL.MD5("well#fandou#"+getPara("time")+"#"+bespeak_id);
		
		if(local_sign.equals(getPara("sign").toUpperCase()) == false){
			Result.error(20510,"sign error",this);
			return;
		}
		
		WellLesson wellLesson = new WellLesson();
		
		try {
			
			wellLesson.setBespeakId(bespeak_id);
			wellLesson.setClassTime(data.getString("class_time"));
			wellLesson.setTeacherId(data.getString("teacher_id"));
			wellLesson.setSchoolName(data.getString("school_name"));
			wellLesson.setClassName(data.getString("class_name"));
			wellLesson.setChineseTeacherName(data.getString("chinese_teacher_name"));
			wellLesson.setPackageName(data.getString("package_name"));
			wellLesson.setBookName(data.getString("book_name"));
			wellLesson.setBookId(data.getInt("book_id"));
			wellLesson.setStateSchoolId(data.getString("state_school_id"));
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(getPara("data"));
			Result.error(20509, "data error",this);
			return;
		}
		
		wellLesson.save();
		
		LiveLesson liveLesson = LiveLesson.dao.findFirst("SELECT * FROM live_lesson WHERE live_lesson_id = ?",bespeak_id);
		
		if(liveLesson != null){
			ClassGrades classGrades = ClassGrades.dao.findFirst("SELECT b.* FROM live_lesson_grade a,class_grades b WHERE a.grade_id = b.id AND a.id = ?",liveLesson.getLiveLessonGrade());
			
			if(classGrades != null){
				List<ClassCourse> classCourseOld = ClassCourse.dao.find(
						"SELECT * FROM class_course WHERE class_grades_id = ?",
						classGrades.getId());
				
				List<LiveLessonRel> lessonRels = LiveLessonRel.dao.find("SELECT a.* from live_lesson_rel a,live_lesson b WHERE a.live_lesson_id = b.id AND b.live_lesson_id = ?",bespeak_id);
				
				Iterator<LiveLessonRel> iteratorLiveLessonRel =  lessonRels.iterator();
				
				while (iteratorLiveLessonRel.hasNext()){
					
					LiveLessonRel lessonRel = iteratorLiveLessonRel.next();
					
					Iterator<ClassCourse> iteratorClassCourse =  classCourseOld.iterator();
					
					while(iteratorClassCourse.hasNext()){
						
						ClassCourse classCourse = iteratorClassCourse.next();
						
						if(classCourse.getClassRoomId().equals(lessonRel.getClassRoomId())){
							iteratorLiveLessonRel.remove();
							break;
						};
						
					}
					
				}
				
				if(lessonRels != null && lessonRels.size() != 0){
					
					ClassCourse classCourse = ClassCourse.dao.findFirst("SELECT * FROM class_course WHERE class_grades_id = ? ORDER BY do_day DESC LIMIT 1",classGrades.getId());
					
					int doday = classCourse == null?1:classCourse.getDoDay()+1;
					
					String sql = "update class_course_schedule set class_course_id = ?, class_room_id = ?, do_day =?, sort=sort+1 WHERE class_grades_id = ?";
					
					boolean flag = true;
					
					for(LiveLessonRel lessonRel :lessonRels){
						
						ClassRoom classRoom = ClassRoom.dao.findById(lessonRel.getClassRoomId());
						ClassCourse newClassCourse = new ClassCourse();
						newClassCourse.setDoTitle(classRoom == null?"":classRoom.getClassName());
						newClassCourse.setDoSlot(300);
						newClassCourse.setDoDay(doday);
						newClassCourse.setClassRoomId(lessonRel.getClassRoomId());
						newClassCourse.setClassGradesId(classGrades.getId());
						newClassCourse.setCover("");
						newClassCourse.save();
						
						if(flag){
							flag=false;
							Db.update(sql,newClassCourse.getId(),newClassCourse.getClassRoomId(),doday,classGrades.getId());
						}
					}
					
				}
				
				
			}
			
		}

		
		Result.ok(this);
	}
	
	@ActionKey("/v2/well/record/uploadState")
	@EmptyParaValidate(params={"bespeak_id","sign","state","time"})
	public void uploadState(){
		
		String bespeak_id = getPara("bespeak_id");
		
		String sign = getPara("sign");
		
		//签名规则:well#fandou#{{time}}#{{bespeak_id}}32位md5加密大写
		String local_sign = MD5UTIL.MD5("well#fandou#"+getPara("time")+"#"+bespeak_id);
		
		if(local_sign.equals(getPara("sign").toUpperCase()) == false){
			Result.error(20510,"sign error",this);
			return;
		}		
		
		WellLesson wellLesson = WellLesson.dao.findFirst("select * from well_lesson where bespeak_id = ?",bespeak_id);
		
		if(wellLesson == null){
			Result.error(20501,"查询不到该直播课程",this);
		}else{
			wellLesson.setStatus(getParaToInt("state")).update();
			Result.ok(this);
		}
		
		
	}
	
	public static void main(String[] args) { 
		
		System.err.println(MD5UTIL.MD5("well#fandou#1533632871#666666"));
		System.out.println(System.currentTimeMillis());
		
	}

}
