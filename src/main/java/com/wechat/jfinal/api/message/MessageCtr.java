package com.wechat.jfinal.api.message;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;

public class MessageCtr extends Controller{
	
	public void inviteMessage(){
		Integer teacherId = getParaToInt("teacherId",0);
		if(xx.isEmpty(teacherId)){
			renderJson(JsonResult.JsonResultError(203));
		}
		
		List list= Db.find("SELECT * FROM video_invite_message WHERE teacher_id = ? AND `status`= 0",teacherId);
		
		int has = 0;
		if(list.size()>0){
			has=1;
		}
		
		renderJson(JsonResult.JsonResultOK(has));
	}

}
