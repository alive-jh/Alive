package com.wechat.dao.impl;

import org.springframework.stereotype.Repository;

import com.wechat.dao.KnowledgeDao;
import com.wechat.entity.TrainAnswerItem;
import com.wechat.entity.TrainQuestionItem;

@Repository
public class KnowledgeDaoImpl extends BaseDaoImpl implements KnowledgeDao {

	@Override
	public int saveAnswer(String content, Integer answerId) {
		// TODO Auto-generated method stub
		
		String sql = "";
		return 0;
	}

	@Override
	public int saveQuestion(TrainQuestionItem trainQuestionItem,Integer knowledgeId) {
		// TODO Auto-generated method stub
		if(trainQuestionItem.getId()!=null){
			this.saveOrUpdate(trainQuestionItem);
		}else{
			this.saveOrUpdate(trainQuestionItem);
			String sql = "insert into train_knowledge_question_rela (status,question_item_id,knowledge_id) values (?,?,?)";
			this.createSQLQuery(sql).setInteger(0, 1).setInteger(1, trainQuestionItem.getId()).setInteger(2, knowledgeId).executeUpdate();
		}
		
		return 1;
	}

	@Override
	public String anwerPage(Integer answerId) {
		// TODO Auto-generated method stub
		String sql = "select * from train_answer_page where answer_id = ?";
		Object[] obj = (Object[]) this.createSQLQuery(sql).setInteger(0, answerId).list().get(0);
		return obj[2].toString();
	}


	@Override
	public int delAnswer(Integer answerId) {
		// TODO Auto-generated method stub
		
		//String sql = "delete from train_knowledge_answer_rela where id = "+delAnswer;
		
		String sql = "update train_answer_item set status = -1 where id = "+answerId;
		this.createSQLQuery(sql).executeUpdate();
		return 1;
	}

	@Override
	public int delQuestion(Integer questionId) {
		// TODO Auto-generated method stub
		String sql = "update train_question_item set status = -1 where id = "+questionId;
		this.createSQLQuery(sql).executeUpdate();
		return 1;
	}

	@Override
	public int saveKnowledge(String epalId) {
		// TODO Auto-generated method stub
		//System.out.println("here-----"+epalId);
		String sql = "insert into train_knowledge (epal_id) values(?)";
		
		this.createSQLQuery(sql).setString(0, epalId).executeUpdate();
		return 1;
	}

	@Override
	public int delKnowledgeById(Integer knowledgeId) {
		// TODO Auto-generated method stub
		
		String sql = "update train_knowledge set status = -1 where id = "+knowledgeId;
		this.createSQLQuery(sql).executeUpdate();
		return 1;
	}

	@Override
	public Integer saveAnswer(TrainAnswerItem trainAnswerItem, Integer knowledgeId) {

		Integer answerId = null;
		
		this.saveOrUpdate(trainAnswerItem);
		answerId = trainAnswerItem.getId();
		
		String sql = "insert into train_knowledge_answer_rela (knowledge_id,answer_item_id,status) values(?,?,1)";
		
		this.createSQLQuery(sql).setInteger(0, knowledgeId).setInteger(1, answerId).executeUpdate();
		
		return answerId;
	}

	@Override
	public int saveTrainAnswerPage(Integer answerId, String pageContent) {
		// TODO Auto-generated method stub
		String sql = "insert into train_answer_page (answer_id,content) values(?,?)";
		this.createSQLQuery(sql).setInteger(0, answerId).setText(1, pageContent).executeUpdate();
		return 1;
	}

	@Override
	public int saveAnswer(TrainAnswerItem trainAnswerItem) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(trainAnswerItem);
		return 1;
	}

	@Override
	public Object[] getAnswerPageByAnswerId(Integer id) {
		
		String sql = "SELECT a.text,b.content FROM train_answer_item a LEFT JOIN train_answer_page b ON a.id = b.answer_id WHERE a.id ="+id;
		
		return (Object[]) this.createSQLQuery(sql).list().get(0);
	}

	@Override
	public int updateAnswerPage(Integer id, String content,String answerText) {

		String sql = "UPDATE train_answer_page SET content='"+content+"' WHERE answer_id = "+id;
		String sql2 = "UPDATE train_answer_item SET text='"+answerText+"' WHERE id = "+id;
		this.executeUpdateSQL(sql);
		this.executeUpdateSQL(sql2);
		return 1;
	}
	

	
}
