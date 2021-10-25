package com.wechat.controller;

import com.wechat.entity.*;
import com.wechat.service.QuestionService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Controller
@RequestMapping("api")
public class QuestionApiController {

	@Resource
	private QuestionService questionService;
	

	/**
	 * 搜索机器人问题
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("searchQuestion")
	public void saveFriends(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
	
			HashMap map = new HashMap();
			map.put("userId", userId);

			Page result = questionService.searchQuestion(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	@RequestMapping("updateQuestionById")
	public void updateQuestionById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String question = request.getParameter("question");
			String id = ParameterFilter.emptyFilter("", "id", request);
			Question question1 = new Question();
			question1.setQuestion(question);
			if(null != id && !"".equals(id)){
				question1.setId(Integer.parseInt(id));

			}
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=format.format(date);
			Date currentDate = format.parse(time);
			question1.setInsertDate(currentDate);
			questionService.saveQuestion(question1);
			
			JsonResult.JsonResultInfo(response, question1);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	/*
	 * 
	 * 通过问题ID获取答案列表
	 * 
	 * */
	@RequestMapping("getAnswerByQuestionId")
	public void getAnswerByQuestionId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String questionId = request.getParameter("questionId");
			Page answer = this.questionService.getAnswerByQuestionId(questionId);
			JsonResult.JsonResultInfo(response, answer.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	
	
	@RequestMapping("uploadQuesionHistory")
	public void uploadQuesionHistory(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
			String questionId = request.getParameter("questionId");
			String answerId = request.getParameter("answerId");
			String id = request.getParameter("id");

			UserAnswerHistory userAnswerHistory = new UserAnswerHistory();
			if(null != id&&!"".equals(id)){
				userAnswerHistory.setId(Integer.parseInt(id));
			}
			
			userAnswerHistory.setQuestionId(Integer.parseInt(questionId));
			userAnswerHistory.setUserId(userId);
			userAnswerHistory.setAnswerId(Integer.parseInt(answerId));
			
			questionService.saveUserAnswerHistory(userAnswerHistory);
			JsonResult.JsonResultInfo(response, userAnswerHistory);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}
	
	@RequestMapping("deleteAnswerById")
	public void deleteAnswerById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			questionService.deleteAnswerById(id);
			JsonResult.JsonResultInfo(response,"ok");
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}	
	
	
	@RequestMapping("deleteQuestionById")
	public void deleteQuestionById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			questionService.deleteQuestion(Integer.parseInt(id));
			
			JsonResult.JsonResultInfo(response,"ok");
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}

	}	
	
	@RequestMapping("saveAnswer")
	public void saveAnswer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			String answer = request.getParameter("answer");
			String questionId = request.getParameter("questionId");
			Answer answers = new Answer();
			answers.setAnswer(answer);
			answers.setQuestionId(Integer.parseInt(questionId));
			if(null!=id&&!"".equals(id)){
				answers.setId(Integer.parseInt(id));
			}
			
			questionService.saveAnswer(answers);
			
			JsonResult.JsonResultInfo(response,answers);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	/*
	 * 
	 * 机器人上传语音问题
	 * 
	 * 
	 * */
	
	@RequestMapping("saveSoundQuestion")
	public void saveSoundQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String soundUrl = ParameterFilter.emptyFilter("", "soundUrl", request);
			String id = ParameterFilter.emptyFilter("", "id", request);

			DeviceSoundQuestion deviceSoundQuestion = new DeviceSoundQuestion();
			deviceSoundQuestion.setEpalId(epalId);
			deviceSoundQuestion.setStatus(1);
			deviceSoundQuestion.setInsertDate(new Date());
			deviceSoundQuestion.setSoundUrl(soundUrl);
			if(null!=id&&!"".equals(id)){
				deviceSoundQuestion.setId(Integer.parseInt(id));
			}
			
			questionService.saveDeviceSoundQuestion(deviceSoundQuestion);
			JSONObject temp = new JSONObject();
			temp.put("epalId", epalId);
			temp.put("status", 1);
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			temp.put("createTime", dateString);
			temp.put("soundUrl", soundUrl);
			temp.put("id", deviceSoundQuestion.getId());
			JsonResult.JsonResultInfo(response,temp);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	
	/*
	 * 
	 * 机器人上传语音答案
	 * 
	 * 
	 * */
	
	@RequestMapping("saveSoundQuestionAnswer")
	public void saveSoundQuestionAnswer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String soundUrl = ParameterFilter.emptyFilter("", "soundUrl", request);
			String questionId = ParameterFilter.emptyFilter("", "questionId", request);
			String id = ParameterFilter.emptyFilter("", "id", request);

			DeviceSoundQuestionAnswer deviceSoundQuestionAnswer = new DeviceSoundQuestionAnswer();
			deviceSoundQuestionAnswer.setEpalId(epalId);
			deviceSoundQuestionAnswer.setSoundUrl(soundUrl);
			deviceSoundQuestionAnswer.setQuestionId(Integer.parseInt(questionId));
			deviceSoundQuestionAnswer.setStatus(1);
			deviceSoundQuestionAnswer.setInsertDate(new Date());
			if(null!=id&&!"".equals(id)){
				deviceSoundQuestionAnswer.setId(Integer.parseInt(id));
			}
			
			questionService.saveDeviceSoundQuestionAnswer(deviceSoundQuestionAnswer);
			JSONObject temp = new JSONObject();
			temp.put("epalId", epalId);
			temp.put("status", 1);
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(currentTime);
			temp.put("createTime", dateString);
			temp.put("soundUrl", soundUrl);
			temp.put("id", deviceSoundQuestionAnswer.getId());
			temp.put("questionId", questionId);
			JsonResult.JsonResultInfo(response,temp);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	
	/*
	 * 
	 * 机器人通过问题ID获取答案列表
	 * 
	 * 
	 * */
	
	@RequestMapping("getSoundQuestionAnswerList")
	public void getSoundQuestionAnswerList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String questionId = ParameterFilter.emptyFilter("", "questionId", request);
			HashMap map = new HashMap();
			map.put("questionId", questionId);
			Page dataList = questionService.getSoundQuestionAnswer(map);
			JsonResult.JsonResultInfo(response,dataList);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	
	
	/*
	 * 
	 * 机器人通过用户ID获取问题列表
	 * 
	 * 
	 * */
	
	@RequestMapping("getSoundQuestionList")
	public void getSoundQuestionList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			HashMap map = new HashMap();
			map.put("epalId", epalId);
			Page dataList = questionService.getSoundQuestionList(map);
			JsonResult.JsonResultInfo(response,dataList);
			
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
		}
	}	
	
	/*
	 * 
	 * 机器人随机获取问题
	 * 
	 * 
	 * */
	
	@RequestMapping("getSoundQuestionNoMyself")
	public void getSoundQuestionNoMyself(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String count = ParameterFilter.emptyFilter("5", "count", request);
			 
			HashMap map = new HashMap();
			map.put("epalId", epalId);
			map.put("count", count);
			Page dataList = questionService.getSoundQuestionNoMyself(map);
			JsonResult.JsonResultInfo(response,dataList);
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}
	}	
}
