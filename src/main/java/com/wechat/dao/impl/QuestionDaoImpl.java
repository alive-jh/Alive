package com.wechat.dao.impl;


import com.wechat.dao.QuestionDao;
import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class QuestionDaoImpl extends BaseDaoImpl implements QuestionDao {
	
	
	/*
	 * 上传问题答案
	 * 
	 * 
	 * */
	@Override
	public void saveQuestion(Question question) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(question);
		
	}
	
	@Override
	public void saveUserAnswerHistory(UserAnswerHistory userAnswerHistory) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(userAnswerHistory);
		
	}
	

	@Override
	public void saveAnswer(Answer answers) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(answers);
	}
	
	@Override
	public void deleteQuestion(Integer id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from Question where id="
				+ id);
		this.executeHQL(sql.toString());
		
	}
	

	@Override
	public void deleteAnswerById(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from Answer where id="
				+ Integer.parseInt(id));
		this.executeHQL(sql.toString());
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Answer> getAnswers(int questionId){
		List<Answer> answers = new ArrayList<Answer>();
		StringBuffer sql=new StringBuffer("select id,answer from answer where question_id=:questionId");
		Query query = this.getQuery(sql.toString());
		query.setInteger("questionId",questionId);
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Friends> friends = new ArrayList<Friends>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Answer answer = new Answer();
				answer.setId((Integer) obj[0]);
				answer.setAnswer((String) obj[1]);
				answers.add(answer);
			}
		}
		return answers;
	}
	
	
	public ArrayList getExitesAnswerFromUser(HashMap map){
		ArrayList  questions = new ArrayList();
		StringBuffer sql=new StringBuffer("select * from user_answer_history where user_id=:userId");
		Query query = this.getQuery(sql.toString());
		if (null != map.get("userId")
				&& !"".equals(map.get("userId").toString())) {
			query.setString("userId", map.get("userId").toString());
		}
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				questions.add(obj[2]);
			}

		}else{
			
		}
		return questions;
	}
	/*
	 * 根据用户ID获取未完成的问卷调查
	 * 
	 * 
	 * */
	@Override
	public Page searchQuestion(@SuppressWarnings("rawtypes") HashMap map) {
		// TODO Auto-generated method stub
				StringBuffer sql=new StringBuffer("select * from question where 1=1");
				Query query = this.getQuery(sql.toString());
				@SuppressWarnings("rawtypes")
				Page dataPage = this.pageQueryBySQL(query, 1, 1000);
				ArrayList<Question> questions = new ArrayList<Question>();
				@SuppressWarnings("unchecked")
				ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
				
				List<Integer> question_ids = getExitesAnswerFromUser(map);
				int count = 1;
				
				if (dataList.size() > 0) {

					for (int i = 0; i < dataList.size(); i++) {
						if (count > 4){
							break;
						}
						Object[] obj = dataList.get(i);
						Question question= new Question();
						question.setId((Integer) obj[0]);
						if(question_ids.contains(obj[0])){
							continue;
						}
						count += 1;
						question.setQuestion((String) obj[1]);
						question.setQuestionType((String) obj[2]);
//						question.setInsertDate((Date)obj[3]);
						question.setAnswer(getAnswers((Integer) obj[0]));
						questions.add(question);
					}

				}

				Page resultPage = new Page<Question>(questions, dataPage.getTotalCount(),
						dataPage.getIndexes(), dataPage.getStartIndex(),
						dataPage.getPageSize());

				return resultPage;
	}
	/*
	 * 根据用户ID获取未完成的问卷调查
	 * 
	 * 
	 * */
	@Override
	public Page searchQuestions(HashMap map) {
		// TODO Auto-generated method stub
				StringBuffer sql=new StringBuffer("select * from question where 1=1");
				Query query = this.getQuery(sql.toString());
				Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
						.toString()), new Integer(map.get("pageSize").toString()));
				
				ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
				
				ArrayList<Question> questions=new  ArrayList<Question>();
				
				if (dataList.size() > 0) {

					for (int i = 0; i < dataList.size(); i++) {
						Object[] obj = dataList.get(i);
						Question question= new Question();
						question.setId((Integer) obj[0]);
						question.setQuestion((String) obj[1]);
						question.setQuestionType((String) obj[2]);
						question.setInsertDate((Date)obj[3]);
						questions.add(question);
					}

				}

				Page resultPage = new Page<Question>(questions, dataPage.getTotalCount(),
						dataPage.getIndexes(), dataPage.getStartIndex(),
						dataPage.getPageSize());

				return resultPage;
	}

	@Override
	public Page getAnswerByQuestionId(String questionId) {
		// TODO Auto-generated method stub
		StringBuffer sql=new StringBuffer();
		sql.append("select * from answer where question_id=:questionId");
		Query query=this.getQuery(sql.toString());
		query.setInteger("questionId", Integer.parseInt(questionId));
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);
	
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> answers = new  ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap answer=new HashMap();
				answer.put("id", obj[0]);
				answer.put("answer", obj[1]);
				answer.put("insertDate", obj[2]);
				answer.put("questionId", obj[3]);
				answers.add(answer);
			}
	
		}
		Page resultPage = new Page<HashMap>(answers,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void saveDeviceSoundQuestion(DeviceSoundQuestion deviceSoundQuestion) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceSoundQuestion);
	}

	@Override
	public void saveDeviceSoundQuestionAnswer(
			DeviceSoundQuestionAnswer deviceSoundQuestionAnswer) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceSoundQuestionAnswer);
	}

	@Override
	public Page getSoundQuestionList(HashMap map) {
		// TODO Auto-generated method stub
		String epalId = map.get("epalId").toString();
		StringBuffer sql=new StringBuffer("from DeviceSoundQuestion where epalId='" + epalId + "'");
		Page dataPage = this.pageQueryByHql(sql.toString(), 1, 1000);
		return dataPage;
	}

	@Override
	public Page getSoundQuestionAnswer(HashMap map) {
		// TODO Auto-generated method stub
		String questionId = map.get("questionId").toString();
		StringBuffer sql=new StringBuffer("from DeviceSoundQuestionAnswer where questionId=" + questionId);
		Page dataPage = this.pageQueryByHql(sql.toString(), 1, 1000);
		return dataPage;
	}

	@Override
	public Page getSoundQuestionNoMyself(HashMap map) {
		// TODO Auto-generated method stub
		String epalId = map.get("epalId").toString();
		StringBuffer sql=new StringBuffer("SELECT id,sound_url,epal_id,status,create_time FROM device_sound_question WHERE epal_id!=:epalId AND id NOT IN (SELECT question_id FROM device_sound_question_answer WHERE epal_id=:epalId2) ORDER BY RAND()");
		Query query = this.getQuery(sql.toString());
		query.setString("epalId", epalId);
		query.setString("epalId2", epalId);
		Page dataPage = this.pageQueryBySQL(query, 1, Integer.parseInt(map.get("count").toString()));
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONObject temp = new JSONObject();
				Object[] obj = dataList.get(i);
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format((Date)obj[4]);
				temp.put("createTime", dateString);
				temp.put("soundUrl", (String)obj[1]);
				temp.put("epalId", (String)obj[2]);
				temp.put("id", (Integer)obj[0]);
				temp.put("status", (Integer)obj[3]);
				result.add(temp);
			}
	
		}
		Page resultPage = new Page<DeviceSoundQuestion>(result,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}


}
