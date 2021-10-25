package com.wechat.jfinal.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.TrainAnswerItem;
import com.wechat.jfinal.model.TrainKnowledgeAnswerRela;
import com.wechat.jfinal.model.TrainQuestionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainAnswerItemService {
    TrainQuestionItem questionDao = new TrainQuestionItem().dao();
    TrainKnowledgeAnswerRelaService knowledgeAnswerRelaService = new TrainKnowledgeAnswerRelaService();

    public void delete(int answerId) {
        Db.update("UPDATE train_answer_item  set status = -1 WHERE id = ?", answerId);
        knowledgeAnswerRelaService.delete(answerId);
    }

    public TrainAnswerItem create(int knowledgeId, String text, int type, String content,int priority,int loop){
        TrainAnswerItem a = new TrainAnswerItem().setText(text).setType(type).setPriority(priority).setLoopCount(loop);
        if(type != 1)
            a.setContent(content);
        a.save();
        new TrainKnowledgeAnswerRela().setKnowledgeId(knowledgeId).setAnswerItemId(a.getId()).save();
        return a;
    }

    public List<Map<String, Object>> getDetailBymemberId(int memberId) {
        Kv cond = Kv.by("memberId", memberId);
        List<Record> data = Db.find(Db.getSqlPara("train.getDetailBymemberId", cond));

        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> question;
        List<Map<String, Object>> answerList = new ArrayList<>();
        Map<String, Object> answer;
        int questionId = 0;
        for (Record record : data) {
            if (questionId == 0) { // init
                questionId = record.getInt("questionId");
                question = new HashMap<>();
                result.add(question);
                question.put("questionId", record.getInt("questionId"));
                question.put("memberId", record.getInt("memberId"));
                question.put("qusetionText", record.getStr("qusetionText"));
                question.put("answerList", answerList);
                answer = new HashMap<>();
                answer.put("relaId", record.getInt("relaId"));
                answer.put("answerId", record.getInt("answerId"));
                answer.put("answerText", record.getStr("answerText"));
                if (answer.get("answerId") != null)
                    answerList.add(answer);
            } else if (questionId == record.getInt("questionId")) { //keep going
                answer = new HashMap<>();
                answer.put("relaId", record.getInt("relaId"));
                answer.put("answerId", record.getInt("answerId"));
                answer.put("answerText", record.getStr("answerText"));
                if (answer.get("answerId") != null)
                    answerList.add(answer);
            } else if (questionId != record.getInt("questionId")) { //new
                questionId = record.getInt("questionId");
                question = new HashMap<>();
                result.add(question);
                question.put("questionId", record.getInt("questionId"));
                question.put("memberId", record.getInt("memberId"));
                question.put("qusetionText", record.getStr("qusetionText"));
                answerList = new ArrayList<>();
                question.put("answerList", answerList);
                answer = new HashMap<>();
                answer.put("relaId", record.getInt("relaId"));
                answer.put("answerId", record.getInt("answerId"));
                answer.put("answerText", record.getStr("answerText"));
                if (answer.get("answerId") != null)
                    answerList.add(answer);
            }
        }
        return result;
    }

    public List<Record> getDetailByKnoeLedgeIds(List<Integer> ids) {

        Kv cond = Kv.by("knowledgeIds",ids);
        return Db.find(Db.getSqlPara("train.getAnswerByKnowledgeIds",cond));
    }

    public void deleteByKnowledgeId(int knowledgeId) {
        Db.update("UPDATE train_answer_item a, train_knowledge_answer_rela ar SET a.STATUS = -1,ar.status = -1 WHERE ar.knowledge_id = ? AND a.id = ar.answer_item_id ", knowledgeId);
    }

}
