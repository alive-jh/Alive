package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;

public class TrainQuestionAnswerRelaService {


    public void delete(int questionId, int answerId) {
        Db.update("UPDATE train_question_answer_rela  set status = -1 WHERE question_id = ? and answer_id = ?", questionId,answerId);
    }
}
