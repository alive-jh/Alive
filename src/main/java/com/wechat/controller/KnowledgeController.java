package com.wechat.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.TrainAnswerItem;
import com.wechat.entity.TrainQuestionItem;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.service.KnowledgeService;
import com.wechat.service.RedisService;
import com.wechat.util.QiniuUtil;

import net.sf.json.JSONObject;


@Controller
@CrossOrigin
@RequestMapping("knowledge")
public class KnowledgeController {

	@Resource
	KnowledgeService knowledgeService;

	@Resource
	RedisService redisService;

	@RequestMapping("myKnowledge")
	public String knowledgeManager(HttpServletRequest request,String epalId) {

		String user = redisService.get("loginUser1");
		if(user.indexOf("qingyuan")!=-1){
			user = user.replaceAll("qingyuan", "");
			request.setAttribute("user", user);
		}
		request.setAttribute("epalId", epalId);
		return "knowledge/myKnowledge";
	}

	@RequestMapping("saveQuestion")
	@ResponseBody
	public Map saveQuestion(HttpServletRequest request, TrainQuestionItem trainQuestionItem, Integer knowledgeId) {
		int i = knowledgeService.saveQuestion(trainQuestionItem, knowledgeId);
		return JsonResult.JsonResultOK();
	}

	@RequestMapping("saveAnswer")
	@ResponseBody
	public Map saveAnswer(HttpServletRequest request, String answerText, String content,
			Integer knowledgeId, Integer answerType, MultipartFile file,Integer id) {

		
		if(null!=id&&!"".equals(id)){
			int i = this.knowledgeService.updateAnswerPage(id,content,answerText);
			return JsonResult.JsonResultOK(); 
		}
		
		TrainAnswerItem trainAnswerItem = new TrainAnswerItem();
		trainAnswerItem.setText(answerText);
		trainAnswerItem.setType(answerType);
		trainAnswerItem.setPriority(2);
		trainAnswerItem.setLoop_count(0);

		if (answerType == 1) {
		} else if(answerType>=2&&answerType<5){
			trainAnswerItem.setType(5);
			String fileName1 = file.getOriginalFilename();
			int random = (int) (Math.random() * 1000000);
			String fileName = new Date().getTime() + random + ""
					+ fileName1.subSequence(fileName1.indexOf("."), fileName1.length());
			String key = QiniuUtil.fileUpdate(file, fileName);

			trainAnswerItem.setContent("http://source.fandoutech.com.cn/"+key);

		}

		Integer answerId = knowledgeService.saveAnswer(trainAnswerItem, knowledgeId);

		if (answerId != null && answerType == 5 && content != null && !"".equals(content)) {
			int i = this.knowledgeService.saveTrainAnswerPage(answerId, content);
			trainAnswerItem.setContent("http://test.fandoutech.com.cn/wechat/knowledge/anwerPage?answerId="+answerId);
			int k = this.knowledgeService.saveAnswer(trainAnswerItem);
		}
		
		Record rc = Db.findFirst("select * from train_knowledge where id = ?",knowledgeId);
		String epalId = rc.getStr("epal_id");
		if(epalId!=null){
			String[] users = {epalId};
			try {
				EasemobUtil.sendPenetrate(users,"train_qa_update");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

		return JsonResult.JsonResultOK();
	}

	@RequestMapping("anwerPage")
	public String anwerPage(HttpServletRequest reqeust, Integer answerId) {

		String content = knowledgeService.anwerPage(answerId);

		reqeust.setAttribute("content", content);
		return "knowledge/answer";
	}

	@RequestMapping("delAnswer")
	@ResponseBody
	public Map delAnswer(HttpServletRequest request, Integer answerId) {

		//System.out.println(answerId);

		int i = knowledgeService.delAnswer(answerId);

		return JsonResult.JsonResultOK();
	}

	@RequestMapping("delQuestion")
	@ResponseBody
	public Map delQuestion(HttpServletRequest request, Integer questionId) {

		//System.out.println(questionId);

		int i = knowledgeService.delQuestion(questionId);

		return JsonResult.JsonResultOK();
	}

	@RequestMapping("saveKnowledge")
	@ResponseBody
	public Map saveKnowledge(HttpServletRequest request, String epalId1) {

		//System.out.println(epalId1);
		int i = knowledgeService.saveKnowledge(epalId1);

		return JsonResult.JsonResultOK();
	}

	@RequestMapping("delKnowledge")
	@ResponseBody
	public Map delKnowledge(HttpServletRequest request, Integer knowledgeId) {

		int i = knowledgeService.delKnowledgeById(knowledgeId);
		return JsonResult.JsonResultOK();
	}

	@RequestMapping("getAnswerPageByAnswerId")
	@ResponseBody
	public JSONObject getAnswerPageByAnswerId(Integer answerId){
		
		Object[] obj = this.knowledgeService.getAnswerPageByAnswerId(answerId);
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("text", obj[0].toString());
		data.put("pageContent", obj[1].toString());
		return JsonResult.JsonResultOK(data);
	}
	
}
