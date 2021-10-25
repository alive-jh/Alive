package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;

public class TrainKnowledgeQuestionRelaService {


    public void delete(int knowledgeId, int questionId) {
        Db.update("UPDATE train_knowledge_question_rela  set status = -1 WHERE knowledge_id = ? AND question_item_id = ?", knowledgeId,questionId);
    }

    public void delete(int questionId){
        Db.update("UPDATE train_knowledge_question_rela  set status = -1 WHERE  question_item_id = ?",questionId);
    }
}
