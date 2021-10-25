package com.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wechat.dao.KnowledgeDao;
import com.wechat.entity.TrainAnswerItem;
import com.wechat.entity.TrainQuestionItem;
import com.wechat.service.KnowledgeService;

@Service("knowledgeService")
public class KnowledgeServiceImpl implements KnowledgeService{

	@Resource
	KnowledgeDao knowledgeDao;
	
	@Override
	public int saveAnswer(String content, Integer answerId) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveAnswer(content,answerId);
	}

	@Override
	public int saveQuestion(TrainQuestionItem trainQuestionItem,Integer knowledgeId) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveQuestion(trainQuestionItem,knowledgeId);
	}

	@Override
	public String anwerPage(Integer answerId) {
		// TODO Auto-generated method stub
		return knowledgeDao.anwerPage(answerId);
	}


	@Override
	public int delAnswer(Integer delAnswer) {
		// TODO Auto-generated method stub
		return knowledgeDao.delAnswer(delAnswer);
	}

	@Override
	public int delQuestion(Integer delQuestion) {
		// TODO Auto-generated method stub
		return knowledgeDao.delQuestion(delQuestion);
	}

	@Override
	public int saveKnowledge(String epalId) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveKnowledge(epalId);
	}

	@Override
	public int delKnowledgeById(Integer knowledgeId) {
		// TODO Auto-generated method stub
		return knowledgeDao.delKnowledgeById(knowledgeId);
	}


	@Override
	public Integer saveAnswer(TrainAnswerItem trainAnswerItem, Integer knowledgeId) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveAnswer(trainAnswerItem,knowledgeId);
	}

	@Override
	public int saveTrainAnswerPage(Integer answerId, String pageContent) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveTrainAnswerPage(answerId,pageContent);
	}

	@Override
	public int saveAnswer(TrainAnswerItem trainAnswerItem) {
		// TODO Auto-generated method stub
		return knowledgeDao.saveAnswer(trainAnswerItem);
	}

	@Override
	public Object[] getAnswerPageByAnswerId(Integer id) {
		// TODO Auto-generated method stub
		return knowledgeDao.getAnswerPageByAnswerId(id);
	}

	@Override
	public int updateAnswerPage(Integer id, String content,String answerText) {
		// TODO Auto-generated method stub
		return knowledgeDao.updateAnswerPage(id,content,answerText);
	}

}
