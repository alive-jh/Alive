package com.wechat.jfinal.api.qy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.AgentSchool;
import com.wechat.jfinal.model.QyDevice;
import com.wechat.jfinal.model.QyPunchRecord;
import com.wechat.jfinal.model.User;
import com.wechat.jfinal.service.QyPunchRecordService;
import com.wechat.util.MD5UTIL;

import net.sf.json.JSONObject;


public class QYPunchRecord extends Controller{
	QyPunchRecordService qyPunchRecordService = new QyPunchRecordService();

	/**
	*  function: 机器上传打卡数据
	*  description:使用POST方法上传打卡记录。
	*  return:返回打卡对象
	* @param:
	* 	cardId:学生卡号
	* 	deviceId:打卡机器设备号
	*  @author
	*	alex
	*/
    public void save(){
    	QyPunchRecord bean = getBean(QyPunchRecord.class,"");
		if(null == bean.getId()){//是否带主键ID
			
			//后台定义打卡照片文件的key，机器负责按照后台返回的key，上传文件到七牛
			String punchImage = "http://source.fandoutech.com.cn/epal/attendance/pic/" +bean.getCardId() + System.currentTimeMillis() + ".jpg" ;  
			bean.setPunchImage(punchImage);
			bean.save();
			renderJson(JsonResult.JsonResultOK(bean));
		}else{
			//可以修改，暂时预留
			bean.update();
			renderJson(JsonResult.JsonResultOK(QyPunchRecord.dao.findById(bean.getId())));
		}
    }

	/**
	*  function: 机器上传打卡数据
	*  description:使用POST方法上传打卡记录。
	*  return:返回打卡对象
	* @param:
	* 	cardId:学生卡号
	* 	deviceId:打卡机器设备号
	*  @author
	*	alex
	*/
    public void savePunchRecord(){
		String img = getPara("jpgName","");
		
		String punchTimesStr = getPara("punchTime");
		
		String cardId = getPara("cardId");
		
		int id = getParaToInt("id",0);
		String temperature = getPara("temperature","37.0");
		if(0!=id){
			Db.update("update qy_punch_record set temperature=? where id=?",temperature,id);
			return;
		}
		if(!img.contains("_")) {
			renderJson(Rt.paraError());
			return;
		}
		
		String[] paras = img.split("_");
		String action = getPara("action");
		String deviceId = getPara("deviceId");
		int type = getParaToInt("actionTypeId");
		
		if(xx.isOneEmpty(paras,action,deviceId)) {
			renderJson(Rt.paraError());
			return;
		}
		
		if(cardId == null){
			cardId = paras[0];
		}
		
		long punchTime = 0;
		
		if(punchTimesStr == null){
			punchTime = Long.parseLong(paras[paras.length-1]);
		}else{
			punchTime = Long.parseLong(punchTimesStr);
		}
		
    	QyPunchRecord record = new QyPunchRecord();
    	record.setCardId(cardId).setPunchImage(img).setDeviceId(deviceId).setPunchTime(new Date(punchTime)).setAction(action).setType(type);
    	
    	if(!xx.isEmpty(temperature)){
    		record.setTemperature(Float.parseFloat(temperature));
		}
    	record.save();
    	renderJson(Rt.success(record));
    }

	/**
	*  function: 获取打卡设备的打卡记录
	*  description:获取打卡设备的打卡记录
	*  return:返回打卡记录
	* @param:
	* 	deviceId:打卡机器设备号
	*  @author
	*	alex
	*/
    public void getPunchListByDeviceId(){
		String deviceId = getPara("deviceId","");
	    int currentPage = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	Page<Record> temp= Db.paginate(currentPage,pageSize,"select a.card_id cardId,a.punch_time punchTime,CONCAT('http://source.fandoutech.com.cn/epal/attendance/pic/',a.punch_image,'.jpg')  punchImage ,IFNULL(a.temperature,'') temperature,c.name studentName,c.avatar avatar,e.class_name className,a.action action"," from qy_punch_record as a,qy_student_card as b,class_student as c,qy_class_to_student as d,qy_class as e where a.card_id=b.card_id and b.student_id=c.id and d.student_id=c.id and d.class_id=e.id and device_id=? order by a.id desc",deviceId);
    	Map<String, Object> data = new HashMap<>();
    	data.put("_list", temp);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }
    
    public void getSchoolPunchList(){
		String schoolId = getPara("schoolId","");
	    int currentPage = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	Page<Record> temp= Db.paginate(currentPage,pageSize,"select a.card_id cardId,a.punch_time punchTime,CONCAT('http://source.fandoutech.com.cn/epal/attendance/pic/',a.punch_image,'.jpg')  punchImage ,IFNULL(a.temperature,'') temperature,c.name studentName,c.avatar avatar,e.class_name className,a.action action"," from qy_punch_record as a,qy_student_card as b,class_student as c,qy_class_to_student as d,qy_class as e where a.card_id=b.card_id and b.student_id=c.id and d.student_id=c.id and d.class_id=e.id and device_id IN (SELECT device_id FROM qy_device WHERE qy_device.belong = ?) order by a.id desc",schoolId);
    	Map<String, Object> data = new HashMap<>();
    	data.put("_list", temp);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }
  
	/**
	*  function: 通过学生卡号，获取学生的历史打卡记录
	*  description:通过学生卡号，获取学生的历史打卡记录
	*  return:返回历史打卡记录
	* @param:
	* 	cardId:学生卡号
	*  @author
	*	alex
	*/
    public void getPunchListByCardId(){
		String cardId = getPara("cardId","");
	    int currentPage = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	Page<Record> temp= Db.paginate(currentPage,pageSize,"select a.card_id cardId,a.punch_time punchTime,CONCAT('http://source.fandoutech.com.cn/epal/attendance/pic/',a.punch_image,'.jpg') punchImage,c.name studentName,c.avatar avatar,e.class_name className,a.action action"," from qy_punch_record as a,qy_student_card as b,class_student as c,qy_class_to_student as d,qy_class as e where a.card_id=b.card_id and b.student_id=c.id and d.student_id=c.id and d.class_id=e.id and a.card_id=? ORDER BY a.id desc",cardId);
    	Map<String, Object> data = new HashMap<>();
    	data.put("_list", temp);
    	data.put("code", 200);
    	data.put("msg", "ok");
    	renderJson(data);
    }

	public void getPunchListByGroupIdAndStudentId(){
    	int groupId = getParaToInt("groupId");
    	int studentId = getParaToInt("studentId");
		if (xx.isOneEmpty(groupId,studentId)){
			renderJson(Rt.paraError());
			return;
		}
		List<Record> result =  qyPunchRecordService.getListByGroupIdAndStudentId(groupId,studentId);
		renderJson(Rt.success(result == null ? new Record() : result));
	}

	
	/**
     * 通过类型id和学生id获取打卡列表
     */
	public void getPunchListByTypeIdAndStudentId(){
    	int typeId = getParaToInt("typeId");
    	int studentId = getParaToInt("studentId");
		if (xx.isOneEmpty(typeId,studentId)){
			renderJson(Rt.paraError());
			return;
			
		}
		List<Record> result =  qyPunchRecordService.getPunchListByTypeIdAndStudentId(typeId,studentId);
		renderJson(Rt.success(result == null ? new Record() : result));
	}
	
	
	public void login(){
		String account = getPara("account");
		String password = getPara("password");
		
		User user = User.dao.findFirst("select * from user where account = ? and password = ?",account,MD5UTIL.encrypt(password));
		
		List<QyDevice> qyDevices = QyDevice.dao.find("SELECT c.* FROM `user` a JOIN agent_school b on a.id = b.a_id JOIN qy_device c ON b.id = c.belong WHERE a.id = ?",user.getId());
		
		JSONObject json = new JSONObject();
		json.put("user", Result.toJson(user));
		json.put("qyDevice", Result.makeupList(qyDevices));
		json.put("agentSchool", Result.makeupList(AgentSchool.dao.find("select * from agent_school where a_id = ?",user.getId())));
		
		Result.ok(json,this);
	}
    
}
