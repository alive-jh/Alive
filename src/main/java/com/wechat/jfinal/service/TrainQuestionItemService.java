package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.TrainKnowledgeQuestionRela;
import com.wechat.jfinal.model.TrainQuestionItem;

import java.util.List;

public class TrainQuestionItemService {
    TrainKnowledgeQuestionRelaService knowledgeQuestionRelaService = new TrainKnowledgeQuestionRelaService();

    public void delete(int questionId) {
        Db.update("UPDATE train_question_item  set status = -1 WHERE id = ?", questionId);
        knowledgeQuestionRelaService.delete(questionId);
    }

    public TrainQuestionItem create(int knowledgeId,String text){
        TrainQuestionItem q = new TrainQuestionItem().setText(text);
        q.save();
        new TrainKnowledgeQuestionRela().setKnowledgeId(knowledgeId).setQuestionItemId(q.getId()).save();
        return q;
    }

    public List<Record> getDetailByKnoeLedgeIds(List<Integer> ids) {
        Kv cond = Kv.by("knowledgeIds",ids);
        return Db.find(Db.getSqlPara("train.getQuestionByKnowledgeIds",cond));
    }

    public void deleteByKnowledgeId(int knowledgeId) {
        Db.update("UPDATE train_question_item q, train_knowledge_question_rela qr SET q.STATUS = -1,qr.status = -1 WHERE qr.knowledge_id = ? AND q.id = qr.question_item_id ", knowledgeId);
    }
}
