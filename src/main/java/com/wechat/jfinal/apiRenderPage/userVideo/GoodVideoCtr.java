package com.wechat.jfinal.apiRenderPage.userVideo;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.service.UserVideoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodVideoCtr  extends Controller {
	
	static UserVideoService userVideoService = new UserVideoService();
	
    public void goodVideo(){
    	Page<Record> page = userVideoService.getAllVideo();
        renderJson(JsonResult.JsonResultOK(page));
    }
    
    public void getVideoById(){
    	String vid = getPara("vid");
    	Record data = userVideoService.getVideoById(vid);
    	setAttr("data", data);
        render("/studenVideo/videoInfo.jsp");
    }
    
    public void goodVideoByAJAX(){
    	String page = getPara("page");
    	Page<Record> list = userVideoService.goodVideoByAJAX(page);
    	renderJson(JsonResult.JsonResultOK(list));
    }
    
    public void getShowStudents(){
    	int page = 1;
    	Page<Record> list = userVideoService.getShowStudents(page);
    	setAttr("dataWithAll", list.getList());
    	setAttr("type", "all");
        render("/studenVideo/index.jsp");
    }
    
    public void getStudentVideoByStudentId(){
    	int studentId = Integer.parseInt(getPara("studentId"));
    	List<Record> list = userVideoService.getStudentVideoByStudentId(studentId);
    	setAttr("data", list);
    	render("/studenVideo/videoList.jsp");
    }
    
    public void getShowStudentsByAJAX(){
    	String type = getPara("type");
    	
    	int page = Integer.parseInt(getPara("page"));
    	
    	String agentId = getPara("agentId");
    	
    	String period = getPara("period");
    	
    	Page<Record> list = null;
    	
    	if(type.equals("all")){list = userVideoService.getShowStudents(page);}
    	
    	if(type.equals("area")){list = userVideoService.getShowStudentsByArea(Integer.parseInt(agentId),page);}
    	
    	if(type.equals("learTime")){list = userVideoService.getPeriodVideo(Integer.parseInt(period),page);}
    	
        renderJson(JsonResult.JsonResultOK(list));
    }
    
    public void getVideosByStudentId(){
    	int studentId = Integer.parseInt(getPara("sid"));
    	List<Record> list = userVideoService.getVideosByStudentId(studentId);
    	setAttr("data", list);
        render("/studenVideo/index.jsp");
    }
    
    public void getPeriodVideo(){
    	int period = Integer.parseInt(getPara("period"));
    	int page=1;
    	Page<Record> list = userVideoService.getPeriodVideo(period,page);
    	setAttr("dataWithLearnTime", list.getList());
    	setAttr("type", "learTime");
    	setAttr("period", period);
    	setAttr("title", "??????"+period+"??????");
        render("/studenVideo/index.jsp");
    }
    
    //????????????????????????
    public void getShowStudentsByArea(){
    	int agentId = Integer.parseInt(getPara("agentId"));
    	int page = 1;
    	Map<String,String> map = new HashMap<String,String>();
		map.put("10000", "??????");map.put("10001", "??????");map.put("10002", "??????");map.put("10003", "??????");map.put("10004", "??????");map.put("10005", "??????");map.put("10006", "??????");
    	map.put("10007", "??????");map.put("10008", "??????");map.put("100010", "??????");map.put("20020", "??????");
		Page<Record> list = userVideoService.getShowStudentsByArea(agentId,page);
    	if(list.getList().size()!=0){
        	setAttr("dataWithArea", list.getList());
        	setAttr("title",map.get(""+agentId)+"????????????");
        	setAttr("agentId",agentId);
        	setAttr("type","area");
        	}else{
        		setAttr("title",map.get(""+agentId)+"????????????");
        		setAttr("type","area");
        		setAttr("msg","?????????????????????");
        	}
            render("/studenVideo/index.jsp");
    }
    
    
    
    
}
