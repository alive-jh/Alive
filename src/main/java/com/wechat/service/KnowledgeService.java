package com.wechat.service;

import com.wechat.entity.TrainAnswerItem;
import com.wechat.entity.TrainQuestionItem;

public interface KnowledgeService {

	int saveAnswer(String content, Integer answerId);

	int saveQuestion(TrainQuestionItem trainQuestionItem,Integer knowledgeId);

	String anwerPage(Integer answerId);

	int delAnswer(Integer answerId);

	int delQuestion(Integer questionId);

	int saveKnowledge(String user);

	int delKnowledgeById(Integer knowledgeId);

	Integer saveAnswer(TrainAnswerItem trainAnswerItem, Integer knowledgeId);

	int saveTrainAnswerPage(Integer answerId, String pageContent);

	int saveAnswer(TrainAnswerItem trainAnswerItem);

	Object[] getAnswerPageByAnswerId(Integer id);

	int updateAnswerPage(Integer id, String content,String answerText);
	
}
