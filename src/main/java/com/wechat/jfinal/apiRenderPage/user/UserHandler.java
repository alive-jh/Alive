package com.wechat.jfinal.apiRenderPage.user;

import java.util.HashMap;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.util.TAOBAOSMS;

public class UserHandler extends Controller {

	
	public void toRegisterPage(){
		String epalId = getPara("epalId","");
		if(xx.isEmpty(epalId)){
			renderJson(JsonResult.JsonResultError(203));
		}else{
			getRequest().setAttribute("epalId", epalId);
			renderJsp("/user/register.jsp");
		}
		
	}
	
	public void regisiter(){
		Long mobile = getParaToLong("mobile",1L);
		if(xx.isEmpty(mobile)){
			JsonResult.JsonResultError(203);
		}
		
		Integer code = (int) (Math.random()*9000+1000);
		try {
			TAOBAOSMS.sendCode(code+"", mobile+"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		getSession().setAttribute("code", code);
		
		renderJson(JsonResult.JsonResultOK());
	}
	
	public void verificationCode(){
		
		Long mobile = getParaToLong("mobile",1L);
		if(xx.isEmpty(mobile)){
			renderJson(JsonResult.JsonResultError(203));
		}
		
		HashMap<String, Object> map = new HashMap<>();
		int status = 0;
		int registed = 0;
		Integer code = getParaToInt("code",0);
		//System.out.println(code+"------"+getSession().getAttribute("code"));
		if(code.equals(getSession().getAttribute("code"))){
			status=1;
		};
		
		//System.out.println(status);
		List<Record> list = Db.find("select * from	memberaccount where account = "+mobile);
		if(list.size()!=0){
			registed = 1;
		}
		
		map.put("status", status);
		map.put("registed", registed);
		
		renderJson(JsonResult.JsonResultOK(map));
	}

}
