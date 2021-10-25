package com.wechat.jfinal.service;

import com.wechat.jfinal.common.utils.CalculateUtil;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassCourseSchedule;
import com.wechat.jfinal.model.StdDiyStudyDay;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StudentStudyDayService {
    private static final ClassCourseService classCourseService = new ClassCourseService();

    /**
     *
     * @param rule
     * @param date
     * @param doday
     * @param schedule
     * @return 0:数据有误 1：学传入doday的主辅课2：学习传入doday的辅课 3：学后doday后一节的主辅课
     */
    public int isStudyDay(StdDiyStudyDay rule, Date date, int doday , ClassCourseSchedule schedule) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(rule == null  || c == null)
            return 0;
        int isTeacherDefault = rule.getIsTeacherDefault();
        //是否是默认规则？ 跟课程表走：跟自定义规则（rule）走
        if(isTeacherDefault == 1){
//            根据当前游标的完成时间和下一节课程的间隔天判断是否要上主课
            List<ClassCourse> courses = classCourseService.getDodayAndNextDodayCourse(rule.getGradeId(), doday);
            if(courses == null || courses.size() ==0 ){
                return 0;
            }else if (courses.size() == 1){
                return 1;
            }else{
                int dateBetween = CalculateUtil.howDaysBetween(schedule.getLastTime(),date);
                int courseBetween = courses.get(1).getDoDay() - courses.get(0).getDoDay();
               if(dateBetween == 0)
                    return 1;
                else if(dateBetween < courseBetween)
                    return 2;
                else if(dateBetween >= courseBetween)
                    return 3;
                else return 0;
            }
        }else {
            if(rule.getRuleType()<2){
                int weekIndex = c.get(Calendar.DAY_OF_WEEK )-1;
                boolean isStudyDay =rule.getWeek().charAt(weekIndex) == 1;
                if(isStudyDay)
                    return 1;
                else{
                    return 2;
                }
            }else {
                //System.out.print("暂时未有对应type为" + rule.getRuleType() + "类型的规则");
                return 0;
            }
        }


    }
}
