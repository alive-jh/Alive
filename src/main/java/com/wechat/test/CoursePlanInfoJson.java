package com.wechat.test;

import com.wechat.entity.CoursePlanInfo;
import com.wechat.util.JsonResult;

import java.util.ArrayList;

public class CoursePlanInfoJson {

	public static void main(String[] args) {
		ArrayList<CoursePlanInfo> coursePlanInfos=new ArrayList<CoursePlanInfo>();
		CoursePlanInfo coursePlanInfo=new CoursePlanInfo();
		coursePlanInfo.setCourseId(2386);
		coursePlanInfo.setProductId(1480);
		coursePlanInfo.setPeriodId(6);
		coursePlanInfo.setSort(3);
		coursePlanInfo.setSummary("英语初级>语素英语-b>第一章>第一课时");
		coursePlanInfos.add(coursePlanInfo);
		//System.out.println(JsonResult.JsonResultToStr(coursePlanInfos));
	}

}
