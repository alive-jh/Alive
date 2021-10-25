package com.wechat.jfinal.api.teacher;


import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourseSchedule;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassGradesRela;
import com.wechat.jfinal.model.ClassTeacherGrades;
import com.wechat.jfinal.model.GradeCode;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.jfinal.model.Mallspecifications;
import com.wechat.jfinal.model.TeacherImportantClassGrades;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassGradesHandle extends Controller{
	/*
	 * 
	 *  老师保存提交重要班级（APP）
	 *  表格：teacher_important_class_grades
	 *  备注：保存班级和老师的对应关系到数据表
	 * 
	 * 
	 * */
    public void saveImportClassGrades(){
    	String teacherId = getPara("teacherId");
    	String classGradesId = getPara("classGradesId");
    	if(xx.isOneEmpty(teacherId,classGradesId)){ //判断参数是否为空
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
    	}else{
        	TeacherImportantClassGrades teacherImportantClassGrades = new TeacherImportantClassGrades();
        	teacherImportantClassGrades.setClassGradesId(Integer.parseInt(classGradesId));
        	teacherImportantClassGrades.setTeacherId(Integer.parseInt(teacherId));
        	teacherImportantClassGrades.save();
        	JSONObject data = new JSONObject();
        	data.put("code", 200);
        	data.put("msg", "save success!");
        	renderJson(data);
    	}
    	return;
    }
    
	/*
	 * 
	 *  老师取消重要班级（APP）
	 *  表格：teacher_important_class_grades
	 *  备注：保存班级和老师的对应关系到数据表
	 * 
	 * 
	 * */
    public void cancelImportClassGrades(){
    	String teacherId = getPara("teacherId");
    	String classGradesId = getPara("classGradesId");
    	if(xx.isOneEmpty(teacherId,classGradesId)){
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
        	
    	}else{
        	
        	List<TeacherImportantClassGrades> classGradesList= TeacherImportantClassGrades.dao.find("select id,class_room_id,teacher_id from teacher_important_class_grades where class_grades_id=? and teacher_id=?",Integer.parseInt(classGradesId),Integer.parseInt(teacherId));
        	if(classGradesList.size()>0){
        		TeacherImportantClassGrades teacherImportantClassGrades = classGradesList.get(0);
        		teacherImportantClassGrades.delete();
            	JSONObject data = new JSONObject();
            	data.put("code", 200);
            	data.put("msg", "cancel success!");
            	renderJson(data);
        	}else{
            	JSONObject data = new JSONObject();
            	data.put("code", 400);
            	data.put("msg", "The source Non-existent");
            	renderJson(data);
        	}

    	}
    	return;

    }
    
	/**
	 * 	提交班级审核接口
	 *  参数：classGradesId（班级ID）
	 *  
	 *  @author alex
	 *  
	 *  @param classGradesId
	 *  
	 *  @serialData  2018-04-26
	 * 
	 * */
	public void applyAuditing(){ 
		//获取参数
		int classGradesId = getParaToInt("classGradesId",0);
		
		String price = getPara("price");
		
		if( 0 == classGradesId){
			Result.error(201,this);
			return;
		}
		
		//修改审核状态（-1：内容违规；0：审核不通过，1：待审核，2：审核中；4：审核通过）
		ClassGrades classGrades = ClassGrades.dao.findById(classGradesId);
		if(null == classGrades){
			Result.error(404,this);
			return;
		}else{
			int auditingStatus = classGrades.getAuditingStatus();
			if(4==auditingStatus){//判断是否已经提交了申请请求
				Result.error(201, "你已提交审核，请勿重复提交", this);
				return;
			}else{
				
				classGrades.setAuditingStatus(4);
				classGrades.update(); //修改状态
				
				Mallproduct mallproduct = Mallproduct.dao.findFirst("select * from mallproduct where class_grade_id = ?",classGrades.getId());
				
				if(mallproduct ==null){
					mallproduct = new Mallproduct();
				}
				
				mallproduct.setName(classGrades.getClassGradesName());
				mallproduct.setCreatedate(new Date());
				mallproduct.setContent(classGrades.getSummary());

				if(classGrades.getCover().startsWith("[")){
					JSONArray mallLogo = JSONArray.fromObject(classGrades.getCover());
					for(int i=0;i<mallLogo.size();i++){
						mallproduct.set("logo"+i, mallLogo.get(i));
					}
				}else{
					mallproduct.setLogo1(classGrades.getCover());
				}
				
				mallproduct.setStatus(0);
				mallproduct.setClassGradeId(classGrades.getId());
				mallproduct.setPrice(price);
				
				
				if (mallproduct.getId()==null)
					 mallproduct.save();
				else
					mallproduct.update();
				
				//将商品与标签关联，labelId=105=班级标签
				Db.update("insert ignore into productlabel (productid,labelid) values (?,?)",mallproduct.getId(),105);
				
				Mallspecifications mallspecifications = Mallspecifications.dao.findFirst("select * from mallspecifications where productid = ?",mallproduct.getId());
				
				if(mallspecifications==null){
					mallspecifications = new Mallspecifications();
					mallspecifications.setCount(999);
				}
				
				mallspecifications.setName(classGrades.getClassGradesName());
				mallspecifications.setPrice(Double.parseDouble(price));
				mallspecifications.setProductid(mallproduct.getId());
				
				if (mallspecifications.getId()==null)
					mallspecifications.save();
				else
					mallspecifications.update();
				
				Result.error(200, "本次审核需要大约5分钟左右!", this);
				return;
			}
		}

	}
	/**
	 * 班级添加协管老师接口
	 * 
	 * 参数：
	 * classGradesId（班级ID）
	 * teacherId（老师ID）
	 * 
	 * */
	public void addTeacherToClassGrades(){
		Integer classGradesId = getParaToInt("classGradesId",0);
		Integer teacherId = getParaToInt("teacherId",0);
		
		if(0 == classGradesId || 0 == teacherId){
			Result.error(201, "参数错误!", this);
			return;
		}
		
		try {
			ClassTeacherGrades classTeacherGrades = new ClassTeacherGrades();
			classTeacherGrades.setClassGradesId(classGradesId);
			classTeacherGrades.setClassTeacherId(teacherId);
			
			classTeacherGrades.save(); //保存协管老师
		} catch (Exception e) {
			
			Result.error(20501,"重复添加",this);
			return;
			
		}
			
		Result.ok(this);
	}
      
	
	/**
	 * 删除班级协管老师
	 * 
	 * 参数：
	 * classGradesId（班级ID）
	 * teacherId（老师ID）
	 * 
	 * */
	public void deleteTeacherForClassGrades(){
		Integer classGradesId = getParaToInt("classGradesId",0);
		Integer teacherId = getParaToInt("teacherId",0);
		if(0 == classGradesId || 0 == teacherId){
			Result.error(201, "参数错误!", this);
			return;
		}else{
			ClassTeacherGrades classTeacherGrades = ClassTeacherGrades.dao.findFirst("select * from class_teacher_grades where class_grades_id=? and class_teacher_id=?",classGradesId,teacherId);
			classTeacherGrades.delete(); //保存协管老师
		}
		Result.ok(this);
	}
      
	
	/**
	 * 通过班级ID查看班级所有的老师
	 * 
	 * 参数：
	 * classGradesId（班级ID）
	 * */
	
	public void getTeacherListByGrades(){
		Integer classGradesId = getParaToInt("classGradesId",0);
		Result.ok(Db.find("select b.*,a.create_time as bind_time from class_teacher_grades as a,class_teacher as b where a.class_teacher_id=b.id and a.class_grades_id=?",classGradesId),this);
		return;
	}
	
	@EmptyParaValidate(params={"code","student"})
	public void addStudent(){
		
		String code = getPara("code");
		int studentId = getParaToInt("student");
		
		GradeCode gradeCode = GradeCode.dao.findFirst("select grade_id from grade_code where code = ?",code);
		
		if(gradeCode == null){
			Result.error(20501, "班级码不存在",this);
			return;
		}
		
		String sql = "INSERT IGNORE INTO class_grades_rela (class_grades_id,class_student_id) VALUES ("+gradeCode.getGradeId()+","+studentId+")";
		
		Db.update(sql);
		
		ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("SELECT * FROM class_course_schedule WHERE class_grades_id = ? and student_id = ?",gradeCode.getGradeId(),studentId);
		
		if(classCourseSchedule == null){
			
			classCourseSchedule = new ClassCourseSchedule();
			classCourseSchedule.setStudentId(studentId);
			classCourseSchedule.setClassCourseId(0);
			classCourseSchedule.setClassGradesId(gradeCode.getGradeId());
			classCourseSchedule.setClassRoomId(-1);
			classCourseSchedule.setDoDay(-1);
			classCourseSchedule.save();
			
		}
		
		
		Result.ok(this);
	}
}
