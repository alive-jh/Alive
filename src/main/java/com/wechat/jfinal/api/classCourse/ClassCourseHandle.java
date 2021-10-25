package com.wechat.jfinal.api.classCourse;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourseRecord;
import com.wechat.jfinal.model.ClassCourseSchedule;
import com.wechat.jfinal.service.ClassCourseService;

public class ClassCourseHandle extends Controller {

    /*
     * 老师修改课程表进度
     *
     * */
    public void updateClassCourse() {
        Integer classGradesId = getParaToInt("classGradesId", 0);
        Integer classCourseId = getParaToInt("classCourseId", 0);
        Integer classRoomId = getParaToInt("classRoomId", 0);
        Integer doDay = getParaToInt("doDay", 0);
        if (xx.isAllEmpty(classGradesId, classCourseId, classRoomId,
                doDay)) {
            Result.error(203, this);
            return;

        }
        Db.update("update class_course_schedule set class_course_id=?,class_room_id=?,do_day=?,sort=sort+1 where class_grades_id=?", classCourseId, classRoomId, doDay, classGradesId);
        Result.ok(this);

    }

    /**
     * 添加或者修改在线课堂学习记录
     */
    @EmptyParaValidate(params = {"classCourseId", "studentId"})
    public void saveClassCourseRecord() {
        ClassCourseRecord classCourseRecord = getBean(ClassCourseRecord.class, "");
        Integer integral = getParaToInt("integral", 0);
        System.out.println(classCourseRecord);
        System.out.println(classCourseRecord.getCompleteTime());
        ClassCourseRecord findClassCourseRecord = ClassCourseRecord.dao.findFirst("select * from class_course_record where student_id = ? and class_course_id=?", classCourseRecord.getStudentId(), classCourseRecord.getClassCourseId());

        //增加积分
        ClassCourseService classCourseService = new ClassCourseService();
        classCourseService.saveIntegralByStudent(classCourseRecord.getStudentId(), classCourseRecord.getClassCourseId(), integral, classCourseRecord.getUsedTime());

        if (findClassCourseRecord != null) {
            findClassCourseRecord.setUsedTime(findClassCourseRecord.getUsedTime() + classCourseRecord.getUsedTime());
            if (Integer.parseInt(findClassCourseRecord.getScore()) < Integer.parseInt(classCourseRecord.getScore())) {
                findClassCourseRecord.setScore(classCourseRecord.getScore());
            }
            findClassCourseRecord.update();
            classCourseRecord = findClassCourseRecord;
        } else {
            classCourseRecord.save();
        }

        renderJson(Rt.success(classCourseRecord));
    }

    public void saveClassCourseSchedule (){

        ClassCourseSchedule model = getBean(ClassCourseSchedule.class,"");

        ClassCourseSchedule classCourseSchedule = ClassCourseSchedule.dao.findFirst("select * from class_course_schedule where student_id = ? and class_grades_id = ?",model.getStudentId(),model.getClassGradesId());

        if (classCourseSchedule == null){
            model.save();
        }else{
            model.setId(classCourseSchedule.getId()).update();
        }

        model =  ClassCourseSchedule.dao.findById(model.getId());

        Result.ok(model,this);
    }
}
