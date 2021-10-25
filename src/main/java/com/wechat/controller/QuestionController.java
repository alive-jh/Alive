package com.wechat.controller;

import com.wechat.common.dto.QueryDto;
import com.wechat.entity.Question;
import com.wechat.service.QuestionService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("question")
public class QuestionController {

	@Resource
	private QuestionService questionService;
	

	/**
	 * 上传好友关系到服务器
	 * 
	 * 
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("quesionManage")
	public String quesionManage(HttpServletRequest request,
			HttpServletResponse response,QueryDto queryDto) throws Exception {
		HashMap map=new HashMap();
		map.put("page", ParameterFilter.emptyFilter("1", "page", request));
		map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
		map.put("searchStr", ParameterFilter.emptyFilter("", "searchStr", request));
		Page questionInfos=questionService.searchQuestions(map); 
		List questions=questionInfos.getItems();
		
		ArrayList queArr = new ArrayList();
		for (int i = 0; i < questions.size(); i++) {
			Question question=(Question) questions.get(i);
			HashMap questionMap = new HashMap();
			questionMap.put("id", question.getId());
			questionMap.put("question", question.getQuestion());
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insertDate =sdf.format(question.getInsertDate());
			questionMap.put("insertDate", insertDate);
			queArr.add(questionMap);
		}
		request.setAttribute("jsonData",JsonResult.JsonResultToStr(queArr));
		request.setAttribute("queryDto", queryDto);
		request.setAttribute("resultPage", questionInfos);
		return "question/questionManager";
	}
	

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

}
