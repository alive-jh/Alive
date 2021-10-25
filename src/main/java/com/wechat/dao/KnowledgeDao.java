package com.wechat.dao;

import org.springframework.stereotype.Repository;

import com.wechat.entity.TrainAnswerItem;
import com.wechat.entity.TrainQuestionItem;

@Repository
public interface KnowledgeDao {

	int saveAnswer(String content, Integer answerId);


	int saveQuestion(TrainQuestionItem trainQuestionItem, Integer knowledgeId);


	String anwerPage(Integer answerId);

	int delAnswer(Integer delAnswer);


	int delQuestion(Integer delQuestion);


	int saveKnowledge(String epalId);


	int delKnowledgeById(Integer knowledgeId);

	Integer saveAnswer(TrainAnswerItem trainAnswerItem, Integer knowledgeId);


	int saveTrainAnswerPage(Integer answerId, String pageContent);


	int saveAnswer(TrainAnswerItem trainAnswerItem);


	Object[] getAnswerPageByAnswerId(Integer id);


	int updateAnswerPage(Integer id, String content,String answerText);

}
