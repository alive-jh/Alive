package com.wechat.service.impl;


import com.wechat.dao.QuestionDao;
import com.wechat.entity.*;
import com.wechat.service.QuestionService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {
	
	@Resource
	private QuestionDao questionDao;
	
	/**
	 * 保存app用户的好友列表到数据库
	 * @param articleLog
	 */



	@Override
	public void saveQuestion(Question question) {
		// TODO Auto-generated method stub
		this.questionDao.saveQuestion(question);
		
	}

	@Override
	public void deleteQuestion(Integer id) {
		// TODO Auto-generated method stub
		this.questionDao.deleteQuestion(id);
		
	}

	@Override
	public Page searchQuestion(HashMap map) {
		// TODO Auto-generated method stub
		return this.questionDao.searchQuestion(map);
	}
	
	@Override
	public Page searchQuestions(HashMap map) {
		// TODO Auto-generated method stub
		return this.questionDao.searchQuestions(map);
	}

	@Override
	public void saveUserAnswerHistory(UserAnswerHistory userAnswerHistory) {
		// TODO Auto-generated method stub
		this.questionDao.saveUserAnswerHistory(userAnswerHistory);
		
	}

	@Override
	public Page getAnswerByQuestionId(String questionId) {
		// TODO Auto-generated method stub
		return this.questionDao.getAnswerByQuestionId(questionId);
	}

	@Override
	public void deleteAnswerById(String id) {
		// TODO Auto-generated method stub
		this.questionDao.deleteAnswerById(id);
		
	}

	@Override
	public void saveAnswer(Answer answers) {
		// TODO Auto-generated method stub
		this.questionDao.saveAnswer(answers);
		
	}

	@Override
	public void saveDeviceSoundQuestion(DeviceSoundQuestion deviceSoundQuestion) {
		// 保存机器人语音问题
		this.questionDao.saveDeviceSoundQuestion(deviceSoundQuestion);
	}

	@Override
	public void saveDeviceSoundQuestionAnswer(
			DeviceSoundQuestionAnswer deviceSoundQuestionAnswer) {
		// 保存机器人语音答案
		this.questionDao.saveDeviceSoundQuestionAnswer(deviceSoundQuestionAnswer);
		
	}

	@Override
	public Page getSoundQuestionList(HashMap map) {
		// TODO Auto-generated method stub
		Page dataPage = this.questionDao.getSoundQuestionList(map);
		ArrayList<DeviceSoundQuestion> dataList = (ArrayList<DeviceSoundQuestion>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONObject temp = new JSONObject();
				DeviceSoundQuestion deviceSoundQuestion = (DeviceSoundQuestion) dataList.get(i);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(deviceSoundQuestion.getInsertDate());
				temp.put("createTime", dateString);
				temp.put("soundUrl", deviceSoundQuestion.getSoundUrl());
				temp.put("epalId", deviceSoundQuestion.getEpalId());
				temp.put("id", deviceSoundQuestion.getId());
				temp.put("status", deviceSoundQuestion.getStatus());
				result.add(temp);
			}
	
		}
		Page resultPage = new Page<DeviceSoundQuestion>(result,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;

	}

	@Override
	public Page getSoundQuestionAnswer(HashMap map) {
		// TODO Auto-generated method stub
		Page dataPage = this.questionDao.getSoundQuestionAnswer(map);
		ArrayList<DeviceSoundQuestionAnswer> dataList = (ArrayList<DeviceSoundQuestionAnswer>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONObject temp = new JSONObject();
				DeviceSoundQuestionAnswer deviceSoundQuestionAnswer = (DeviceSoundQuestionAnswer) dataList.get(i);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String dateString = formatter.format(deviceSoundQuestionAnswer.getInsertDate());
				temp.put("createTime", dateString);
				temp.put("soundUrl", deviceSoundQuestionAnswer.getSoundUrl());
				temp.put("epalId", deviceSoundQuestionAnswer.getEpalId());
				temp.put("id", deviceSoundQuestionAnswer.getId());
				temp.put("status", deviceSoundQuestionAnswer.getStatus());
				temp.put("questionId", deviceSoundQuestionAnswer.getQuestionId());
				result.add(temp);
			}
	
		}
		Page resultPage = new Page<DeviceSoundQuestion>(result,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getSoundQuestionNoMyself(HashMap map) {
		// TODO Auto-generated method stub
		return this.questionDao.getSoundQuestionNoMyself(map);
	}
	

}
