package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.HashMap;

public interface QuestionDao {
	
	void saveQuestion(Question question);
	
	void saveUserAnswerHistory(UserAnswerHistory userAnswerHistory);
	
	void deleteQuestion(Integer id);
	
	Page searchQuestion(HashMap map);
	
	Page searchQuestions(HashMap map);

	Page getAnswerByQuestionId(String questionId);

	void deleteAnswerById(String id);

	void saveAnswer(Answer answers);

	void saveDeviceSoundQuestion(DeviceSoundQuestion deviceSoundQuestion);

	void saveDeviceSoundQuestionAnswer(
            DeviceSoundQuestionAnswer deviceSoundQuestionAnswer);

	Page getSoundQuestionList(HashMap map);

	Page getSoundQuestionAnswer(HashMap map);

	Page getSoundQuestionNoMyself(HashMap map);
}
