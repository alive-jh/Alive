package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.ConvetUtil;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainKnowledgeService {
    private TrainAnswerItemService answerItemService = new TrainAnswerItemService();
    private TrainQuestionItemService questionItemService = new TrainQuestionItemService();

    public void delete(int knowledgeId) {
        Db.update("UPDATE train_knowledge  set status = -1 WHERE id = ?", knowledgeId);
        answerItemService.deleteByKnowledgeId(knowledgeId);
        questionItemService.deleteByKnowledgeId(knowledgeId);
    }

    public Page<Record> getDetail(int memberId, String epalId, int pageIndex, int pageSize,int agentId) {

        //get knowledge ids
        Page<Record> knowledgeIds = new Page<>();
        
        String sql = "FROM train_knowledge WHERE member_id = ? AND status = 1 ORDER BY id desc";
        
        String sql2 = "FROM train_knowledge WHERE epal_id = ? AND status = 1 ORDER BY id desc";
        
        String sql3 = "FROM (SELECT * FROM train_knowledge WHERE epal_id IN (SELECT b.device_id FROM agent_school a LEFT JOIN qy_device b ON a.id = b.belong WHERE a.a_id= "+agentId+")) a WHERE a.epal_id = ? AND a.status = 1 ORDER BY a.id desc";
       
        if(!xx.isEmpty(memberId)){//如果凡豆伴账号不为空，取当前账号下的知识库
            knowledgeIds = Db.paginate(pageIndex, pageSize, "SELECT id ", sql, memberId);
        }else if(!xx.isEmpty(epalId)){
            knowledgeIds = Db.paginate(pageIndex, pageSize, "select id ",sql2, epalId);
        }if(agentId!=1){
        	knowledgeIds = Db.paginate(pageIndex, pageSize, "select a.id ",sql3, epalId);
        }

        List<Record> raw = knowledgeIds.getList();
        List<Integer> ids = ConvetUtil.records2IntList(raw, "id");
        return  new Page<>(getDetailByknowledgeIds(ids),knowledgeIds.getPageNumber(),knowledgeIds.getPageSize(),knowledgeIds.getTotalPage(),knowledgeIds.getTotalRow());
    }

    public void copyToRobot(List<Integer> idList, String epalId) {

        List<TrainKnowledgeQuestionRela> questionRelas = new ArrayList<>();
        List<TrainKnowledgeAnswerRela> answerRelas = new ArrayList<>();

        List<Record> details = getDetailByknowledgeIds(idList);
        for (Record detail : details) {//一个个知识
            TrainKnowledge knowledge = new TrainKnowledge().setEpalId(epalId);
            knowledge.save();
            List<Record> questionList = detail.get("questionList");//问题列表
            for (Record record : questionList) {
                TrainQuestionItem questionItem = new TrainQuestionItem().setText(record.getStr("text"));
                questionItem.save();
                questionRelas.add(new TrainKnowledgeQuestionRela().setKnowledgeId(knowledge.getId()).setQuestionItemId(questionItem.getId()));
            }
            List<Record> answerList = detail.get("answerList");//答案列表
            for (Record record : answerList) {
                TrainAnswerItem answerItem = new TrainAnswerItem().setContent(record.getStr("content")).setText(record.getStr("text")).setType(record.getInt("type"));
                answerItem.save();
                answerRelas.add(new TrainKnowledgeAnswerRela().setAnswerItemId(answerItem.getId()).setKnowledgeId(knowledge.getId()));
            }

        }
        Db.batchSave(questionRelas,50);
        Db.batchSave(answerRelas,50);
    }

    public List<Record> getDetailByknowledgeIds(List<Integer> ids){
        List<Record> result = new ArrayList<>();
        if (xx.isEmpty(ids))
            return result;
        List<Record> answerList = answerItemService.getDetailByKnoeLedgeIds(ids);
        List<Record> questionList = questionItemService.getDetailByKnoeLedgeIds(ids);

        Map<Integer, List<Record>> answerGroupMap = new HashMap<>();
        for (Record record : answerList) {
            int knowledgeId = record.getInt("knowledgeId");
            if (answerGroupMap.get(knowledgeId) == null) {
                answerGroupMap.put(knowledgeId, new ArrayList<Record>());
            }
            answerGroupMap.get(knowledgeId).add(record);
        }

        Map<Integer, List<Record>> questionGroupMap = new HashMap<>();
        for (Record record : questionList) {
            int knowledgeId = record.getInt("knowledgeId");
            if (questionGroupMap.get(knowledgeId) == null) {
                questionGroupMap.put(knowledgeId, new ArrayList<Record>());
            }
            questionGroupMap.get(knowledgeId).add(record);
        }

        for (Integer id : ids) {
            Record r = new Record();
            r.set("knowledgeId", id);
            if (answerGroupMap.get(id) != null)
                r.set("answerList",answerGroupMap.get(id));
            else
                r.set("answerList",new ArrayList<>());
            if(questionGroupMap.get(id) != null )
                r.set("questionList",questionGroupMap.get(id));
            else
                r.set("questionList",new ArrayList<>());

            result.add(r);
        }
        return result;
    }
}
