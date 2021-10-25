package com.wechat.service;


import com.wechat.entity.*;
import com.wechat.util.Page;

import java.util.HashMap;

public interface QuestionService {
	
	void saveQuestion(Question question);
	
	void deleteQuestion(Integer id);
	
	Page searchQuestion(HashMap map);
	
	Page searchQuestions(HashMap map);
	
	Page getAnswerByQuestionId(String questionId);
	
	void saveUserAnswerHistory(UserAnswerHistory userAnswerHistory);

	void deleteAnswerById(String id);

	void saveAnswer(Answer answers);

	void saveDeviceSoundQuestion(DeviceSoundQuestion deviceSoundQuestion);

	void saveDeviceSoundQuestionAnswer(
            DeviceSoundQuestionAnswer deviceSoundQuestionAnswer);

	Page getSoundQuestionList(HashMap map);

	Page getSoundQuestionAnswer(HashMap map);

	Page getSoundQuestionNoMyself(HashMap map);
}
