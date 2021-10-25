package com.wechat.jfinal.api.qy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.easemob.EasemobUtil;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.QyDevice;
import com.wechat.jfinal.model.QyPunchActionRule;
import com.wechat.jfinal.service.QyDeviceService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QYDeviceHandle extends Controller{

	QyDeviceService qyDeviceService = new QyDeviceService();


	/**
	*
	* 保存机器
	*
	*/
    public void saveDevice(){
    	QyDevice bean = getBean(QyDevice.class,"");
    	String deviceId = bean.getDeviceId();
    	//System.out.println(deviceId);
    	QyDevice qyDevice = QyDevice.dao.findFirst("select * from qy_device where device_id=?",deviceId);
		if(null == qyDevice){//判断机器人是否已经添加
			bean.save();
			renderJson(JsonResult.JsonResultOK(bean));
		}else{//该机器已经添加
			renderJson(Rt.notFound());
		}
    }

    /**
	*  function: 根据id获取机器人信息
	*  return:机器人设备对象
	* @param:
	* 	deviceId(机器人信息id)
	*/
    public void initInfo(){
    	String deviceId = getPara("deviceId");
    	if(xx.isEmpty(deviceId)){
    		renderJson(Rt.paraError());
    		return;
		}
    	renderJson(Rt.success(qyDeviceService.getInfo(deviceId)));
	}
    
	/**
	* 通过memberId获取机器列表
	*
	*/
    public void getDeviceList(){
    	int belong = getParaToInt("belong");
	    int currentPage = getParaToInt("pageNumber",1);//页码
    	int pageSize = getParaToInt("pageSize",10);//每页个数
        Page<QyDevice> page = QyDevice.dao.paginate(currentPage,pageSize,"SELECT *"," FROM qy_device where belong=? order by id desc",belong);
        renderJson(JsonResult.JsonResultOKWithPage(page));
    }
	/**
	*  function: 通过设备ID获取，当前设备所属用户所有学生列表
	*  description:
	*  		第一步：通过设备账号，获取设备所属的会员ID；
	*  		第二步：获取用户下所有的学生列表
	*  		查询会员所有的班级----》查询班级下的所有学生
	*  return:返回学生列表
	* @param:
	* 	deviceId:机器ID
	*  @author
	*	alex
	*/
    public void getStudentListByDeviceId(){
		String deviceId = getPara("deviceId");
	    int currentPage = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	
    	//第一步：获取机器对应的用户ID
    	QyDevice qyDevice = QyDevice.dao.findFirst("select * from qy_device where device_id=?",deviceId);
    	if(null==qyDevice){
    		//机器无法找到
    		renderJson(JsonResult.JsonResultError(404));
    	}else{
    		//第二步：查询所有学生
    		int beLongId = qyDevice.getBelong();
    	    Page<Record> page = Db.paginate(currentPage,pageSize,"select qc.id,cs.id studentId,cs.name studentName,cs.avatar avatar,qcs.class_id classId,qc.class_name className,qsc.card_id cardId,qsc.contacts contacts","from qy_class_to_student as qcs,class_student as cs,qy_class as qc,qy_student_card as qsc where qcs.student_id=cs.id and qc.id=qcs.class_id and qsc.student_id=cs.id and qcs.class_id in (select c.id from school_teacher_rela as b,qy_class as c where  b.member_id=c.member_id and b.school_id=?)",beLongId);

    	    StringBuffer sql = new StringBuffer("SELECT b.id AS class_id,b.class_name,GROUP_CONCAT(d.id,'#_',d.`name`,'#_',d.avatar,'#_',e.card_id,'#_',e.contacts) AS student_list ");
    	    sql.append("FROM qy_device_class a,qy_class b,qy_class_to_student c,class_student d,qy_student_card e ");
    	    sql.append("WHERE a.class_id = b.id AND b.id = c.class_id AND c.student_id = d.id AND c.student_id = e.student_id AND e.`status` = 1 AND a.device_id = ? GROUP BY b.id");
    	    
    	    List<Record> rcs = Db.find(sql.toString(),deviceId);
    	    
    	    JSONArray jsonArray = new JSONArray(); 
    	    for(Record rc:rcs){
    	    	JSONObject qyClass = new JSONObject();
    	    	qyClass.put("classId", rc.getInt("class_id"));
    	    	qyClass.put("className", rc.getStr("class_name"));
    	    	
    	    	String studentList = rc.getStr("student_list");
    	    	
    	    	String[] studentArray = studentList.split(",");
    	    	JSONArray qyStudentList = new JSONArray();
    	    	for(String student :studentArray){
    	    		String[] Field = student.split("#_");
    	    		JSONObject qyStudent = new JSONObject();
    	    		qyStudent.put("studentId", Integer.parseInt(Field[0]));
    	    		qyStudent.put("studentName",Field[1] );
    	    		qyStudent.put("avatar",Field[2] );
    	    		qyStudent.put("cardId",Field[3] );
    	    		qyStudent.put("contacts",Field[4] );
    	    		qyStudentList.add(qyStudent);
    	    	}
    	    	
    	    	qyClass.put("studentList", qyStudentList);
    	    	jsonArray.add(qyClass);
    	    }
    	    
    	    Map<String, Object> data = new HashMap<>();
    	    data.put("_class", jsonArray);
        	data.put("_list", page);
        	data.put("code", 200);
        	data.put("msg", "ok");
        	renderJson(data);
    	}

    }
	/**
	*  function: 通过设备ID获取，当前设备所属用户所有老师列表
	*  description:
	*  		第一步：通过设备账号，获取设备所属的会员ID；
	*  		第二步：获取用户下所有的学生列表
	*  		查询会员所有的班级----》查询班级下的所有学生
	*  return:返回学生列表
	* @param:
	* 	deviceId:机器ID
	*  @author
	*	alex
	*/
    public void getTeacherListByDeviceId(){
		String deviceId = getPara("deviceId");
	    int currentPage = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	
    	//第一步：获取机器对应的用户ID
    	QyDevice qyDevice = QyDevice.dao.findFirst("select * from qy_device where device_id=?",deviceId);
    	if(null==qyDevice){
    		//机器无法找到
    		renderJson(JsonResult.JsonResultError(404));
    	}else{
    		//第二步：查询所有老师
    		int beLongId = qyDevice.getBelong();
    		Page<Record> page = Db.paginate(currentPage,pageSize,"select b.*"," from school_teacher_rela as a,class_teacher as b where a.member_id=b.member_id and a.school_id=?",beLongId);
        	Map<String, Object> data = new HashMap<>();
        	data.put("_list", page);
        	data.put("code", 200);
        	data.put("msg", "ok");
        	renderJson(data);
    	}

    }
	/**
	*  function: 通过学生卡号，获取学生信息
	*  description:通过学生卡号，获取学生信息
	*  return:返回学生信息
	* @param:
	* 	cardId:学生卡号
	*  @author
	*	alex
	*/
    public void getStudentInfoByCardId(){
		String cardId = getPara("cardId");
		Record record = Db.findFirst("select b.id studentId,b.name studentName,b.avatar avatar,a.card_id cardId,a.contacts contacts,d.class_name className,d.id classId from qy_student_card as a,class_student as b,qy_class_to_student as c,qy_class as d where a.student_id=b.id and b.id=c.student_id and c.class_id=d.id and a.card_id=?",cardId);
		renderJson(JsonResult.JsonResultOK(record.toJson()));
    }
    
    public void sendMessage() throws Exception{
    	
    	
    	String recodIdStr = getPara("recodIdStr");
    	
    	////System.out.println("recodIdStr----"+recodIdStr);
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time = sdf.format(new Date());
    	
    	JSONObject ext = new JSONObject();
    	JSONObject punch = new JSONObject();
    	
    	if(recodIdStr!=null&&!recodIdStr.equals("")){
    		
    		StringBuffer sql = new StringBuffer();
    		sql.append("SELECT a.class_name, CONCAT('http://source.fandoutech.com.cn/epal/attendance/pic/', b.punch_image) AS punch_image,IFNULL(b.temperature,'') AS temperature, c.contacts,b.action FROM ( SELECT a.class_name, b.student_id FROM qy_class a LEFT JOIN ");
    		sql.append("qy_class_to_student b ON a.id = b.class_id ) a, qy_punch_record b, qy_student_card c WHERE b.id IN ("+recodIdStr+") AND b.card_id = ");
    		sql.append("c.card_id AND c.student_id = a.student_id ");
    		List<Record> list = Db.find(sql.toString());
    		
    		for(Record rc:list){
    			    		            	
            	punch.put("time", time);	
            	punch.put("picture",rc.getStr("punch_image")+".jpg"); 	
            	punch.put("className", rc.getStr("class_name"));
            	punch.put("temperature", rc.getStr("temperature"));
            	    	
            	ext.put("punch", punch);
    			
            	//System.out.println(ext.toString());
            	
            	EasemobUtil.sendTextMessage(rc.getStr("contacts"), rc.getStr("action"), ext);
            	
    		}
    		
    			Db.update("update qy_punch_record set send_status = 1 where id in ("+recodIdStr+")");
    		
    		
    	}else{
    		
    		String imgUrl = getPara("imgUrl");
    		String contacts = getPara("contacts");
    		String message = getPara("message");    		        	        	       	
        	
        	punch.put("time", time);	
        	
        	if(null!=imgUrl&&!"".equals(imgUrl)){
            	punch.put("picture", imgUrl);        		
        	}else{
        		punch.put("picture", "https://source.fandoutech.com.cn/20180104124021.jpg");
        	}    
        	
        	
        	punch.put("className", "");
        	    	
        	ext.put("punch", punch);
        	
        	
        	EasemobUtil.sendTextMessage(contacts, message, ext);
    		
    		
    	}
    	
    	
    	
    	
    	renderJson(JsonResult.JsonResultOK());
    }
    
    public void sendMessageTest() throws Exception{
    	
    	String message=getPara("message");
    	String contacts = getPara("contacts");
    	String className = getPara("className");
    	String picture = getPara("picture");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time = sdf.format(new Date());
    	
    	JSONObject ext = new JSONObject();
    	JSONObject punch = new JSONObject();
    	
    	punch.put("className", className);
    	punch.put("picture", picture);
    	punch.put("time", time);
    	
    	ext.put("punch", punch);
    	
    	//System.out.println(ext.toString());
    	
    	EasemobUtil.sendTextMessage(contacts, message, ext);
    	
    	renderJson(JsonResult.JsonResultOK());
    }
    
    public void getPunchActionRule(){
    	
    	String deviceId = getPara("deviceId");
    	int pageNumber = getParaToInt("pageNumber",1);
    	int pageSize = getParaToInt("pageSize",10);
    	
    	if(xx.isEmpty(deviceId)){
    		Result.error(203, this);
    		return;
    	}
    	
    	String sqlHead = "SELECT a.class_id,b.start_time,b.end_time,c.id AS action_id,c.`name` AS action_name,c.msg";
    	
    	StringBuffer sql = new StringBuffer(" FROM qy_device_class a,qy_punch_action_rule b,qy_punch_action_type c WHERE ");
    	sql.append(" a.class_id = b.class_id AND b.action_id = c.id AND c.`status`>-1 AND a.device_id = ?");
    	
    	Page<Record> page = Db.paginate(pageNumber, pageSize, sqlHead,sql.toString(),deviceId);
    	
    	Result.ok(page,this);
    	
    }
    
    
}
