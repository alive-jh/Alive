package com.wechat.jfinal.apiRenderPage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.Memberaccount;

public class RouteCtr extends Controller {
    public void getLineChartPage() {
        String objectType = getPara("objectType", "grade");

        int id = getParaToInt("id", 0);

        setAttr("objectType", objectType);
        setAttr("id", id);
        if(objectType.equals("grade")){
            setAttr("gradeName", ClassGrades.dao.findFirst("SELECT * FROM class_grades WHERE id= ?",id).getClassGradesName());
            renderTemplate("/statistical/LineChartPageForGrade.html");
        }

        else renderTemplate("/statistical/LineChartPage.html");

    }
    
   public void showStudent() throws ParseException{
	   String mobile = getPara("mobile");
	   ClassStudent classStudent = ClassStudent.dao.findFirst("select a.* from class_student as a,device_relation as b where a.epal_id=b.epal_id and b.friend_id=? ORDER BY isbind desc",mobile);
	   if(classStudent==null){
		   setAttr("notdata", 1);
		   renderTemplate("/statistical/studyRecord.html");
		   return;
	   }
	   
	   int studentId = classStudent.getId();
	   String StudentName = classStudent.getName();
	   StringBuffer sql = new StringBuffer("select IFNULL(b.score,a.score) AS score,IFNULL(b.timeCost,a.used_time) AS timeCost,IFNULL(b.recordDate,a.complete_time)");
	   sql.append(" as recordDate from class_course_record as a LEFT JOIN class_course_score_record as b ON a.id = b.classCourseRecord_id WHERE a.student_id=? order by recordDate asc");
	   List<Record> temp = Db.find(sql.toString(),studentId);
	   if(temp==null||temp.size()==0){
		   setAttr("notdata", 1);
		   renderTemplate("/statistical/studyRecord.html");
  		   return;
	   }
	   int countCourse = temp.size();

	   String startStudentData = ""; //开始学习时间
	   String endStudentData = ""; //最后学习时间
	   int maxScore = 0; //最高得分
	   
	   int totalCostTime = 0; //总学习时间
	   int totalCostTimes = 0;//计算评价耗时的有效次数
	   int perCostTime = 0; //平均每节课耗时
	   
	   int perScore = 0; //平均得分
	   int totalScore = 0; //总得分
	   int scoreTimes = 0; //有效得分次数
	   
	   String learnTime = "";
	   

	   
	   for(int i=0;i<temp.size();i++){
		   if(i==0){
			   startStudentData = temp.get(i).getStr("recordDate");
		   }else if(i == countCourse-1){
			   endStudentData = temp.get(i).getStr("recordDate");
		   }else{
			   
		   }
		   
		   int score = Integer.parseInt(temp.get(i).getStr("score"));
		   int timeCost = Integer.parseInt(temp.get(i).getStr("timeCost"));
		   if(timeCost > 0){
			   totalCostTime += timeCost;
			   totalCostTimes += 1;
		   }
		   
				   
		   if(score > maxScore){
			   maxScore = score;
		   }
		   
		   if(score > 20){
			   totalScore += score;
			   scoreTimes += 1;
		   }
		   
	   }

	   if(0!=totalCostTimes){
		   perCostTime = totalCostTime/totalCostTimes;
	   }
	   
	   if(0!=scoreTimes){ //如果有效得分次数不为0,计算平均每次得分
		   perScore = totalScore/scoreTimes;
	   }
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date start = sdf.parse(startStudentData);
	   Date end = sdf.parse(endStudentData);
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(start);
	   int day1 = calendar.get(Calendar.DAY_OF_YEAR);
	   calendar.setTime(end);
	   int day2 = calendar.get(Calendar.DAY_OF_YEAR);
	   setAttr("StudentName",StudentName);
	   setAttr("countCourse", countCourse);
	   setAttr("startStudentData", startStudentData);
	   setAttr("endStudentData", endStudentData);
	   setAttr("maxScore", maxScore);
	   setAttr("totalCostTime",totalCostTime/3600);
	   setAttr("perCostTime",perCostTime/60);
	   setAttr("perScore",perScore);
	   setAttr("joinDays",day2-day1);
	   setAttr("notdata", 0);
	   renderTemplate("/statistical/studyRecord.html");
   }
}
