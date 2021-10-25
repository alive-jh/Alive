package com.wechat.controller;

import com.wechat.entity.*;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.service.*;
import com.wechat.util.QiniuUtil;
import com.wechat.util.StatusCode;
import com.wechat.util.StdResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("eval")
public class EvalManageController {

	@Resource
	EvalLabelService labelService;

	@Resource
	EvalQuestionService questionService;

	@Resource
	EvalOptionService optionService;

	@Resource
	EvalQuestionOptionService questionOptionService;

	@Resource
	EvalWxUserService evalWxUserService;

	@Resource
	EvalRecordService evalRecordService;

	@Resource
	StatisticalService statisticalService;
	
	@Resource
	EvaluationService evaluationService;

	Logger logger = LogManager.getLogger("EvalManageController.class");

	// 总接口
	@RequestMapping("saveQuestion")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> saveQuestion(EvalQuestion question) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			questionService.add(question);
			List<EvalOption> options = question.getOptions();
			for (EvalOption o : options) {
				optionService.add(o);
				EvalQuestionOption qoR = new EvalQuestionOption(1, 1, question.getId(), o.getId(), o.getIsCorrect());
				questionOptionService.add(qoR);
			}
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! saveQuestion Error");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("loadQuestions")
	@ResponseBody
	public HashMap<String, Object> loadQuestions(Integer level, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		// 获得labelID
		@SuppressWarnings("unused")
		int labelId = 0;
		try {
			EvalWxUser u = (EvalWxUser) session.getAttribute("userWxInfo");
			if (u == null) {
				u = new EvalWxUser();
				u.setId(66);
				u.setChildNickName("sky的孩子");
			}
			String labelName = "";
			switch (level) {
			case 1:
				labelName = "一级";
				break;
			case 2:
				labelName = "二级";
				break;
			case 3:
				labelName = "三级";
				break;
			case 4:
				labelName = "四级";
				break;

			default:
				break;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", labelName);
			map.put("groups", "微信测评");
			List<EvalLabel> labels = labelService.getByParam(map).getItems();
			if (labels.size() > 0)
				labelId = labels.get(0).getId();
			// 根据labelId获得关联的问题
			List<EvalQuestion> questions = questionService.getByLabelId(labelId).getItems();
			// 组装题目选项
			for (EvalQuestion q : questions) {
				List<EvalOption> os = optionService.getByQuestionId(q.getId());
				q.setOptions(os);
			}
			result.put("questions", questions);
			result.put("nickName", u.getChildNickName());
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! loadQuestions Error");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("saveInfo")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> saveInfo(EvalWxUser user, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			evalWxUserService.add(user);
			session.setAttribute("userWxInfo", user);
			session.setAttribute("userName", user.getChildNickName());
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! saveInfo Error");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("saveResult")
	@ResponseBody
	@Transactional
	public HashMap<String, Object> saveResult(@RequestBody Map<String, Integer> map, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			EvalWxUser u = (EvalWxUser) session.getAttribute("userWxInfo");
			if (u == null) {
				u = new EvalWxUser();
				u.setId(66);
				u.setChildNickName("sky的孩子");
			}

			for (String key : map.keySet()) {
				EvalRecord r = new EvalRecord(1, 1, u.getId(), Integer.parseInt(key), map.get(key));
				evalRecordService.add(r);
			}
			int score = evalRecordService.scoring(map);
			result.put("score", score * 10);
			result.put("count", score);
			result.put("nickName", u.getChildNickName());
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! saveResult Error");
			e.printStackTrace();
		}

		return result;
	}

	// label相关
	@RequestMapping("addLabel")
	@ResponseBody
	public HashMap<String, Object> addLabel(EvalLabel label) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			labelService.add(label);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! addLabel Error");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("delLabel")
	@ResponseBody
	public HashMap<String, Object> delLabel(Integer id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			labelService.delById(id);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! delLabel Error");
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping("getLabelById")
	@ResponseBody
	public StdResponse getLabelById(Integer id) {
		// 新建返回对象
		StdResponse rsp = new StdResponse();

		// 校验输入参数
		if (id == null || id < 1)
			return rsp.setMsg(StatusCode.PARM_FAILED);

		// 进行业务处理
		try {
			rsp.addData(labelService.getById(id));
		} catch (Exception e) {
			logger.error("#############NO!! getLabelById Error");
			rsp.setMsg(StatusCode.SERVER_ERROE);
			e.printStackTrace();
		}
		return rsp;
	}

	@RequestMapping("updateLabel")
	@ResponseBody
	public HashMap<String, Object> updateLabel(EvalLabel label) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			labelService.update(label);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! updateLabel Error");
			e.printStackTrace();
		}
		return result;
	}

	// question相关
	@RequestMapping("addQuestion")
	@ResponseBody
	public HashMap<String, Object> addQuestion(EvalQuestion question) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			questionService.add(question);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! addQuestion Error");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("delQuestion")
	@ResponseBody
	public HashMap<String, Object> delQuestion(Integer id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			questionService.delById(id);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! delQuestion Error");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("getQuestionById")
	@ResponseBody
	public HashMap<String, Object> getQuestionById(Integer id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			questionService.getById(id);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! getQuestionById Error");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("updateQuestion")
	@ResponseBody
	public HashMap<String, Object> updateQuestion(EvalQuestion question) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			questionService.update(question);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! updateQuestion Error");
			e.printStackTrace();
		}
		return result;
	}

	// question相关
	@RequestMapping("addOption")
	@ResponseBody
	public HashMap<String, Object> addOption(EvalOption option) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("isSucess", 0);
		try {
			optionService.add(option);
			result.put("isSucess", 1);
		} catch (Exception e) {
			logger.error("#############NO!! addOption Error");
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping("getSignInfo")
	@ResponseBody
	public HashMap<String, Object> getSignInfo(HttpSession session, String memberId) {

		Integer memberid = Integer.parseInt(memberId);
		HashMap<String, Object> result = new HashMap<String, Object>();
		List list = this.statisticalService.getSignInfo(memberid);
		//System.out.println(list.size());
		if (list.size() != 0) {
			result.put("sign", 1);
			String uName = ((Object[]) list.get(0))[2].toString();
			session.setAttribute("userName", uName);
		} else {
			result.put("sign", 0);
		}
		return result;
	}

	@RequestMapping("saveUserSign")
	@ResponseBody
	public HashMap<String, Object> saveUserSign(HttpSession session, UserSign userSign) {

		session.setAttribute("userName", userSign.getuName());
		HashMap<String, Object> map = new HashMap<String, Object>();
		this.statisticalService.saveUserSign(userSign);
		return map;
	}

	@RequestMapping("saveQuestionNew")
	@ResponseBody
	public JSONObject saveQuestion(Integer level, String questionText, MultipartFile file) {

		String fileName1 = file.getOriginalFilename();

		int random = (int) (Math.random() * 1000000);
		String fileName = new Date().getTime() + random + ""
				+ fileName1.subSequence(fileName1.indexOf("."), fileName1.length());
		String key = QiniuUtil.fileUpdate(file, fileName);
		
		int i = questionService.saveQuestion(level,questionText,"http://source.fandoutech.com.cn/" + key);
		
		return JsonResult.JsonResultOK();

	}
	
	@RequestMapping("evaluationManager")
	public String evaluationManager (HttpServletRequest request){
		
		List<Object[]> labels = evaluationService.getQuestionLabels();
		
		List<Object[]> questionTypes = evaluationService.getQuestionTypes();
		
		request.setAttribute("labels", labels);
		request.setAttribute("questionTypes", questionTypes);
		
		return "eval/newEvaluation/evaluationManager";
		
	}

}
