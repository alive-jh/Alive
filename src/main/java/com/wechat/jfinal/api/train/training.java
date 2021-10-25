package com.wechat.jfinal.api.train;

import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.model.TrainAnswerItem;
import com.wechat.jfinal.model.TrainKnowledge;
import com.wechat.jfinal.model.TrainQuestionItem;
import com.wechat.jfinal.service.*;

import java.util.ArrayList;
import java.util.List;

public class training extends Controller {
    TrainKnowledgeService knowledgeService = new TrainKnowledgeService();
    TrainQuestionItemService questionItemService = new TrainQuestionItemService();
    TrainKnowledgeQuestionRelaService knowledgeQuestionRelaService = new TrainKnowledgeQuestionRelaService();
    TrainAnswerItemService answerItemService = new TrainAnswerItemService();
    TrainKnowledgeAnswerRelaService knowledgeAnswerRelaService = new TrainKnowledgeAnswerRelaService();


    /**
     *创建知识块
     *
     *		 
     */
    public void creatKnowledge() {
        int memberId = getParaToInt("memberId",0);
        String epalId = getPara("epalId","");
        if (xx.isAllEmpty(memberId, epalId)) {
            renderJson(Rt.paraError());
            return;
        }
        TrainKnowledge knowledge;
        if(!xx.isEmpty(memberId)){
            knowledge = new TrainKnowledge().setMemberId(memberId);
        }else
            knowledge = new TrainKnowledge().setEpalId(epalId);
        knowledge.save();
        renderJson(Rt.success(Kv.by("knowledgeId", knowledge.getId())));
    }

    /**
     * 删除知识块，包括相关的问题和答案
     */
    public void deleteKnowledge() {
        int knowledgeId = getParaToInt("knowledgeId");
        if (xx.isEmpty(knowledgeId)) {
            renderJson(Rt.paraError());
            return;
        }
        knowledgeService.delete(knowledgeId);
        renderJson(Rt.success());
    }

    
    /**
     * 保存问题
     */
    public void saveQuestionItem() {
        String questionText = getPara("text");
        int knowledgeId = getParaToInt("knowledgeId");
        if (xx.isOneEmpty(knowledgeId, questionText)) {
            renderJson(Rt.paraError());
            return;
        }
        renderJson(Rt.success(Kv.by("questionItemId", questionItemService.create(knowledgeId, questionText).getId())));
    }
    
    /**
     * 删除问题
     */

    public void deleteQuestionItem() {
        int questionItemId = getParaToInt("questionItemId", 0);
        int knowledgeId = getParaToInt("knowledgeId", 0);

        if (xx.isAllEmpty(questionItemId, knowledgeId)) {
            renderJson(Rt.paraError());
            return;
        }
        if (questionItemId != 0 && knowledgeId == 0) {//假删除这个问题,包括所有关联
            questionItemService.delete(questionItemId);
        }
        if (questionItemId != 0 && knowledgeId != 0) {//假删除这个知识块-问题关联
            knowledgeQuestionRelaService.delete(knowledgeId, questionItemId);
        }
        renderJson(Rt.success());
    }

    /**
     * 修改问题
     */
    public void updateQuestionItem() {
        int questionItemId = getParaToInt("questionItemId", 0);
        String text = getPara("text");
        if (xx.isOneEmpty(questionItemId, text)) {
            renderJson(Rt.paraError());
            return;
        }
        new TrainQuestionItem().setId(questionItemId).setText(text).update();
        renderJson(Rt.success());
    }


    /**
     * 保存答案
     */
    public void saveAnswerItem() {
        String answerText = getPara("text");
        int knowledgeId = getParaToInt("knowledgeId");
        int type = getParaToInt("type", 1);
        String content = "";
        int priority = getParaToInt("priority",2);
        int loop = getParaToInt("loop",1);
        if (type != 1) {
            content = getPara("content");
        }
        if (xx.isOneEmpty(knowledgeId)) {
            renderJson(Rt.paraError());
            return;
        }
        renderJson(Rt.success(Kv.by("answerItemId", answerItemService.create(knowledgeId, answerText, type, content,priority,loop).getId())));
    }

    /**
     * 删除答案
     */
    public void deleteAnswerItem() {
        int answerItemId = getParaToInt("answerItemId", 0);
        int knowledgeId = getParaToInt("knowledgeId", 0);

        if (xx.isAllEmpty(answerItemId, knowledgeId)) {
            renderJson(Rt.paraError());
            return;
        }
        if (answerItemId != 0 && knowledgeId == 0) {//假删除这个问题,包括所有关联
            answerItemService.delete(answerItemId);
        }
        if (answerItemId != 0 && knowledgeId != 0) {//假删除这个知识块-问题关联
            knowledgeAnswerRelaService.delete(knowledgeId, answerItemId);
        }
        renderJson(Rt.success());
    }
    
    /**
     * 修改答案
     */

    public void updateAnswerItem() {

        TrainAnswerItem a = getBean(TrainAnswerItem.class, "");
        if (xx.isEmpty(a.getId())) {
            renderJson(Rt.paraError());
            return;
        } else
            a.update();
        renderJson(Rt.success());
    }

    /**
     * 通过memberId或epalId获得问题列表
     */
    public void getKnowledgeList() {
        int memberId = getParaToInt("memberId", 0);
        String epalId = getPara("epalId","");
        Integer agentId = getParaToInt("agentId",1);
        
        
        int pageIndex = getParaToInt("pageIndex", 0);
        int pageNumber ;
        if(xx.isEmpty(pageIndex)){
            pageNumber = getParaToInt("pageNumber", 1);
        }
        else pageNumber = pageIndex;
        int pageSize = getParaToInt("pageSize", 10000);
        if (xx.isAllEmpty(memberId,epalId)) {
            renderJson(Rt.paraError());
            return;
        }
        renderJson(Rt.success(knowledgeService.getDetail(memberId, epalId, pageNumber, pageSize,agentId)));
    }

    /**
     * 复制知识块列表到其他的机器人
     */
    public void copyToRobot(){
        String knowledgeIds = getPara("knowledgeIds","");
        String epalId = getPara("epalId","");

        if (xx.isOneEmpty(knowledgeIds,epalId)){
            renderJson(Rt.paraError());
            return;
        }
        String[] ids = knowledgeIds.split(",");
        List<Integer> idList = new ArrayList<>();
        try{
            for (String id : ids) {
                idList.add(Integer.parseInt(id));
            }
        }catch (Exception e){
            e.printStackTrace();
            renderJson(Rt.paraError());
        }
        knowledgeService.copyToRobot(idList,epalId);
        renderJson(Rt.success());
    }
}
