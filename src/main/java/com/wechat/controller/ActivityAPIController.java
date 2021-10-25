package com.wechat.controller;

import com.wechat.entity.EnrollInfo;
import com.wechat.entity.ExhibitionSign;
import com.wechat.entity.UserSign;
import com.wechat.service.CampaignService;
import com.wechat.service.StatisticalService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@Component
@RequestMapping("/activityAPI")
public class ActivityAPIController {
	
	
	@Resource
	private CampaignService campaignService;
	
	@Resource
	private StatisticalService statisticalService;

	//保存登记信息
	@RequestMapping("savaEnrollInfo")
	@ResponseBody
	public Map<String, Object> addAutoEvalTemplate(EnrollInfo i){
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			campaignService.saveEnrollInfo(i);
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}
	
	//通过memberId获取member表
	@RequestMapping("getMemberInfo")
	public String signIn(HttpServletRequest request,Integer memberId){
		
		Integer sign = this.statisticalService.signed(memberId);
		
		UserSign userSign = this.statisticalService.getUserSign(memberId);
		
		Object[] memberInfo = this.statisticalService.getMemberInfo(memberId);
		
		request.setAttribute("memberInfo", memberInfo);
		
		//放入用户报名信息
		if(null!=userSign){
			request.setAttribute("userSignInfo", userSign);
			request.setAttribute("userSign", 1);
		}else{
			request.setAttribute("userSign", 0);
		}
		
		//未签到
		if(sign==0)	
			request.setAttribute("signId", 0);
		else
			request.setAttribute("signId", sign);
		
		
		return "activity/sign";
	}
	
	//保存签到信息
	@RequestMapping("saveSignInfo")
	@ResponseBody
	public Map<String,Object> saveSignInfo(HttpSession session,UserSign userSign,ExhibitionSign exhibitionSign){
		
		Integer signId = this.statisticalService.saveSignInfo(exhibitionSign);
		this.statisticalService.saveUserSign(userSign);
		session.setAttribute("userName", userSign.getuName());
		Map<String, Object> result = new HashMap<String,Object>();
		//放入抽奖号码
		result.put("signId", signId);
		return result;
		
	}
	
}
