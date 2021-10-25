package com.wechat.jfinal.api.system;

import java.util.Calendar;
import java.util.List;

import com.jfinal.core.Controller;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.AllMsg;
import com.wechat.jfinal.model.App;
import com.wechat.jfinal.model.CareFeedback;
import com.wechat.jfinal.model.CareSignIn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Fandou extends Controller {
	public void getAppList() {// 获取第三方app列表
		Result.ok(App.dao.find("select * from app"), this);
	}

	public void saveCareFeedback() {

		CareFeedback careFeedback = getBean(CareFeedback.class, "");
		careFeedback.save();

		Result.ok(this);

	}

	public void careSignIn() {

		Integer studentId = getParaToInt("studentId");

		if (studentId == null) {
			Result.error(203, this);
			return;
		}

		Calendar calendar = Calendar.getInstance();

		CareSignIn careSignIn = new CareSignIn();
		careSignIn.setStudentId(studentId);
		careSignIn.setYear(calendar.get(Calendar.YEAR));
		careSignIn.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
		careSignIn.setDay(calendar.get(Calendar.DAY_OF_WEEK));
		
		try {
			careSignIn.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Result.ok(this);

	}

	public void getCareSignIn() {

		Integer studentId = getParaToInt("studentId");
		Integer year = getParaToInt("year");
		Integer week = getParaToInt("week");

		if (xx.isOneEmpty(year,studentId, week)) {
			Result.error(203, this);
			return;
		}

		JSONObject json = new JSONObject();
		json.put("careSignIns", Result.makeupList(
				CareSignIn.dao.find("select * from care_sign_in where student_id = ? and week = ? and year = ?", studentId,year, week)));

		Result.ok(json, this);

	}

	public void getAnnouncement (){
		List<AllMsg> msgs = AllMsg.dao.find("select * from all_msg where `status` = 1");
		Result.ok(msgs,this);
	}


}
