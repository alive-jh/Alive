package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.model.TrainAnswerItem;
import com.wechat.jfinal.model.TrainKnowledgeAnswerRela;

public class TrainKnowledgeAnswerRelaService {


    public void delete(int answerId) {
        Db.update("UPDATE train_knowledge_answer_rela  set status = -1 WHERE id = ?", answerId);
    }

    public TrainAnswerItem create(int knowledgeId, String answerText) {
        TrainAnswerItem a = new TrainAnswerItem().setText(answerText);
        a.save();
        new TrainKnowledgeAnswerRela().setKnowledgeId(knowledgeId).setAnswerItemId(a.getId());
        return a;
    }

    public void delete(int knowledgeId, int answerItemId) {
        Db.update("UPDATE train_knowledge_answer_rela  set status = -1 WHERE knowledge_id  = ? AND answer_item_id = ?",knowledgeId,answerItemId);
    }
}
