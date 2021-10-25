package com.wechat.jfinal.api.question;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.interceptor.AccessTokenInterceptor;
import com.wechat.jfinal.model.BulletinQuestion;
import com.wechat.jfinal.model.BulletinQuestionAnswer;
import com.wechat.jfinal.model.TeacherQuestion;
import com.wechat.util.MD5UTIL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BulletinQuestionCtr extends Controller {

	public void saveQuestion() {

		BulletinQuestion bulletinQuestion = getBean(BulletinQuestion.class, "");

		if (bulletinQuestion.getId() == null) {
			bulletinQuestion.save();
		} else {
			bulletinQuestion.update();
		}
		
		Integer teacherId = getParaToInt("toTeacher");
		
		if(teacherId!=null){
			TeacherQuestion teacherQuestion = new TeacherQuestion();
			teacherQuestion.setQuestionId(bulletinQuestion.getId());
			teacherQuestion.setTeacherId(teacherId).save();
		}

		Result.ok(bulletinQuestion, this);
	}

	public void questionList() {

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.id AS question_id,IFNULL(a.title,'') AS title, IFNULL(a.content,'') AS content,IFNULL(a.question_script_content,'{}') AS question_script_content,a.reward_integral,");
		sql.append(
				"a.create_time,b.id AS student_id,b.`name` AS student_name,b.avatar from bulletin_question a LEFT JOIN class_student b ");
		sql.append("ON a.student_id = b.id where a.is_close = 0 and a.`status` = 1 order by a.create_time desc");

		List<BulletinQuestion> bulletinQuestions = BulletinQuestion.dao.find(sql.toString());

		JSONObject json = new JSONObject();
		json.put("bulletinQuestions", Result.makeupList(bulletinQuestions));

		Result.ok(json, this);

	}

	public void getQuestion() {

		int questionId = getParaToInt("questionId", 0);

		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.id AS question_id,IFNULL(a.title,'') AS title, IFNULL(a.content,'') AS content,IFNULL(a.question_script_content,'{}') AS question_script_content,a.reward_integral,");
		sql.append(
				"a.create_time,b.id AS student_id,b.`name` AS student_name,b.avatar from bulletin_question a LEFT JOIN class_student b ");
		sql.append("ON a.student_id = b.id where a.id = ? and a.`status` = 1 order by a.create_time desc");

		JSONObject json = new JSONObject();
		json.put("bulletinQuestion", Result.toJson(BulletinQuestion.dao.findFirst(sql.toString(), questionId)));

		StringBuffer answersSql = new StringBuffer();
		answersSql.append(
				"SELECT a.id AS answer_id ,a.content,IFNULL(a.answer_script,'{}') AS answer_script,a.is_adoption,a.create_time,b.id AS student_id,b.`name` AS student_name,b.avatar");
		answersSql.append(
				",c.name AS teacher_name,c.avatar AS teacher_avatar FROM bulletin_question_answer a LEFT JOIN class_student b ON a.student_id = b.id LEFT JOIN class_teacher c on a.teacher_id = c.id WHERE question_id = ?");

		List<Record> answers = Db.find(answersSql.toString(), questionId);

		json.put("answers", Result.makeupList(answers));

		Result.ok(json, this);

	};

	@Before(AccessTokenInterceptor.class)
	public void userQuestionList() {

		String sql = "select * from bulletin_question where status = 1 and member_id = ?";

		Integer studentId = getParaToInt("studentId");

		if (studentId != null) {
			sql += " and student_id = " + studentId;
		}

		List<BulletinQuestion> bulletinQuestion = BulletinQuestion.dao.find(sql + " order by create_time desc",
				getParaToInt("memberId"));

		JSONObject json = new JSONObject();
		json.put("bulletinQuestion", Result.makeupList(bulletinQuestion));

		Result.ok(json, this);

	}

	public void saveAnswer() {
		BulletinQuestionAnswer bulletinQuestionAnswer = getBean(BulletinQuestionAnswer.class, "");

		if (bulletinQuestionAnswer.getId() == null) {
			bulletinQuestionAnswer.save();
		} else {
			bulletinQuestionAnswer.update();
		}

		Result.ok(Db.findFirst(
				"SELECT a.*,b.`name` AS teacher_name,b.avatar FROM `bulletin_question_answer` a LEFT JOIN class_teacher b ON a.teacher_id = b.id WHERE a.id = ?",
				bulletinQuestionAnswer.getId()), this);
	}

	public void answerList() {

		Integer questionId = getParaToInt("questionId");

		if (questionId == null) {
			Result.error(203, this);
			return;
		}

		List<BulletinQuestionAnswer> answers = BulletinQuestionAnswer.dao.find(
				"select * from bulletin_question_answer where question_id = ? order by create_time desc", questionId);

		JSONObject json = new JSONObject();
		json.put("answers", Result.makeupList(answers));

		Result.ok(json, this);

	}

	@EmptyParaValidate(params = { "group" })
	public void groupQuestion(){
		
		String sql = "SELECT * FROM (bulletin_question c, `fandou_group_member` a) JOIN fandou_group b "
				+ "ON a.group_id = b.id AND b.id = ? "
				+ "WHERE c.student_id = a.student_id AND c.b_reply = 0";
		
		List<BulletinQuestion> bulletinQuestions = BulletinQuestion.dao.find(sql,getParaToInt("group"));
		
		/*
		 * 数据按照 time_sn 分组
		 */
        Map<Integer, List<BulletinQuestion>> studentQuestionMap = new HashMap<Integer, List<BulletinQuestion>>();
        
        for(BulletinQuestion bulletinQuestion : bulletinQuestions){
        	
        	 List<BulletinQuestion> tempList = studentQuestionMap.get(bulletinQuestion.getStudentId());
        	 
        	 if (tempList == null) {
                 tempList = new ArrayList<>();
                 tempList.add(bulletinQuestion);
                 studentQuestionMap.put((bulletinQuestion.getStudentId()), tempList);
             }
             else {
                 tempList.add(bulletinQuestion);
             }
        	 
        }
		
        JSONObject result = new JSONObject();
		JSONArray questionArray = new JSONArray();
        
		for(Integer key:studentQuestionMap.keySet()){
			
			JSONObject json = new JSONObject();
			json.put("_list", Result.makeupList(studentQuestionMap.get(key)));
			json.put("student", key);
			questionArray.add(json);
			
		}
		
		result.put("questions", questionArray);
		
		Result.ok(result,this);
		
		
	}

	@EmptyParaValidate(params = { "teacher" })
	public void teacherQuestion(){
		
		String sql = "SELECT cs.`name`, cs.avatar, a.* FROM (class_student cs, `bulletin_question` a) JOIN teacher_question b"
				+ " ON a.id = b.question_id AND b.b_reply = 0 WHERE b.teacher_id = ? AND cs.id = a.student_id";
		
		List<Record> questions = Db.find(sql,getParaToInt("teacher"));
		
		for(Record question : questions){
			
		}
		
		
		JSONObject json = new JSONObject();
		json.put("questions", Result.makeupList(questions));
		
		Result.ok(json, this);
	}
}
