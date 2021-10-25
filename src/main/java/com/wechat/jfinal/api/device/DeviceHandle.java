package com.wechat.jfinal.api.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.easemob.EasemobUtil;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.JsonResult;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.BookClassDownloadRecord;
import com.wechat.jfinal.model.Device;
import com.wechat.jfinal.model.DeviceRelation;
import com.wechat.jfinal.model.RobotExpression;
import com.wechat.jfinal.model.RobotHourse;

import net.sf.json.JSONObject;


//机器人上传操作
public class DeviceHandle extends Controller{
	/*
	 * 
	 *  机器人信息操作
	 *  表格：device
	 *  备注：
	 * */
    public void saveDevice(){
		Device bean = getBean(Device.class,"");
		if(null == bean.getId()){
			bean.save();
			renderJson(bean);
		}else{
			bean.update();
			renderJson(JsonResult.JsonResultOK(Device.dao.findById(bean.getId())));
		}
    }
    
	/*
	 * 
	 *  机器人信息操作
	 *  表格：device
	 *  备注：
	 * 
	 * 
	 * */
    public void getDeviceList(){

    	
    }
    
    
    public void getExpression(){
    	List<RobotExpression> robotExpressionList = RobotExpression.dao.find("select * from robot_expression");
    	Result.ok(robotExpressionList,this);
    	//renderJson(robotExpressionList);
    }

    
    /**
     * 批量删除机器人信息
     */
    public void clean(){
    	
    	String epals = "";
    	
    	
    	List<String> sqls = new ArrayList<String>(11);
    	sqls.add("delete from friends where friend_id in ();");
    	sqls.add("delete from courseproject where system_plan=-1 and epalId in ("+epals+");");
    	sqls.add("delete from course_schedule where epal_id in ("+epals+");");
    	sqls.add("delete from course_schedule_now where epal_id in ("+epals+") "
    			+ "and course_schedule_now.project_id not in (select id from courseproject where epalId in ("+epals+"));");
    	sqls.add("update course_schedule_now set cur_class=1 where epal_id in ("+epals+");");
    	sqls.add("delete from device_schedule where is_def=0 and epal_id in ("+epals+");");
    	sqls.add("delete from device_relation where epal_id in ("+epals+");");
    	sqls.add("update device set nickname=epal_id where epal_id in ("+epals+");");
    	sqls.add("delete from history_schedules where epalId in ("+epals+");");
    	sqls.add("update class_grades_rela cgr,class_student cs set cgr.gradesStatus=-2 where cgr.class_student_id=cs.id and cs.epal_id in ("+epals+");");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	String date = sdf.format(new Date());
    	
    	sqls.add("update class_student set epal_id=concat(epal_id, '-"+date+"') where epal_id in ("+epals+");");

    	//System.out.println(Db.batch(sqls, 200));
    	
    }
    
    /**
     * 发送CMD消息
     */
    public void sendCMDMessage(){
    	
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    	
    	String action = getPara("action");
    	String users = getPara("users");
    	Integer gradesId = getParaToInt("gradesId");
    	
    	if(action==null){
    		Result.error(203, this);
    		return;
    	}
    	
    	String[] _users = null;
    	
    	if(users!=null){
    		_users = users.split(",");
    	}
    	
    	if(gradesId!=null){
    		
    		/*Record epalIds =  Db.findFirst("SELECT GROUP_CONCAT(a.epal_id) AS epal_ids FROM class_student a JOIN class_grades_rela b "
    				+ "ON a.id = b.class_student_id AND b.gradesStatus > -1 WHERE b.class_grades_id = ? GROUP BY b.class_grades_id",gradesId);*/
    		
    		//Record epalIds = Db.findFirst("SELECT GROUP_CONCAT(stu_epal_id) AS epal_ids FROM robot_hourse WHERE hourse_id = ?",gradesId);
    		Record epalIds = Db.findFirst("SELECT GROUP_CONCAT(b.epal_id) AS epal_ids FROM `class_grades_rela` a,class_student b WHERE a.class_student_id = b.id AND a.class_grades_id = ?",gradesId);
    		
    		if(epalIds==null || epalIds.getStr("epal_ids") == null){
    			Result.error(20502,"查询不到任何学生", this);
        		return;
    		}else{
        		_users = epalIds.getStr("epal_ids").split(",");
    		}
    		
    	}
    	
    	EasemobUtil easemobUtil = new EasemobUtil();
    	
    	try {
			easemobUtil.sendMessage("fandousuper2014", _users, action);
		} catch (Exception e) {
			Result.error(20501, "发送失败",this);
			return;
		}
    	
    	Result.ok(this);
    	
    }
    
    /**
     * 获取预下载记录
     */
    public void getDeviceDownloadRecord(){
    	
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    	
    	String studentIds = getPara("studentId");
    	
    	if(studentIds==null){
    		Result.error(203, this);
    		return;
    	}
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT a.student_id , GROUP_CONCAT(a.epal_id, '¿', a.book_id, '¿', a.grades_id, '¿', a.class_grades_name, '¿', a.create_time,");
    	sql.append(" '¿', a.`status`, '¿', a.class_room_str SEPARATOR '∫') AS data_str FROM ( SELECT a.student_id, a.epal_id, a.book_id, b.grades_id,");
    	sql.append(" c.class_grades_name , b.create_time, a.`status` , GROUP_CONCAT(cr.id, '@', cr.class_name SEPARATOR '♂') AS class_room_str FROM");
    	sql.append(" `device_download_record` a JOIN book_class_download_record b ON a.book_id = b.id JOIN class_grades c ON b.grades_id = c.id LEFT");
    	sql.append(" JOIN class_room cr ON FIND_IN_SET(cr.id, b.class_room_ids) WHERE a.student_id IN ("+studentIds+") GROUP BY a.student_id, a.epal_id, ");
    	sql.append(" a.book_id ) a GROUP BY a.student_id");
    	
    	List<Record> deviceDownloadRecords = Db.find(sql.toString());
    	
    	for(Record deviceDownloadRecord : deviceDownloadRecords){
    		
    		String dataStr = deviceDownloadRecord.getStr("data_str");
    		
    		if(dataStr!=null){
    			String[] datas = dataStr.split("∫");
    			String[] data = null;
    			List<Record> downloadRecords = new ArrayList<Record>(datas.length);
    			for(String _data : datas){
    				Record downloadRecord = new Record();
    				data = _data.split("¿");
    				downloadRecord.set("epal_id", data[0]);
    				downloadRecord.set("book_id", data[1]);
    				downloadRecord.set("grades_id", data[2]);
    				downloadRecord.set("class_grades_name", data[3]);
    				downloadRecord.set("create_time", data[4]);
    				downloadRecord.set("status", data[5]);
    				
    				if(data[6]!=null){
    					String[] classRooms = data[6].split("♂");
    					List<Record> classRoomList = new ArrayList<Record>(classRooms.length);
    					for(String _classRoom : classRooms){
    						String[] classRoomFields = _classRoom.split("@");
    						Record classRoom = new Record();
    						classRoom.set("id", classRoomFields[0]);
    						classRoom.set("className", classRoomFields[1]);
    						classRoomList.add(classRoom);
    					}
    					downloadRecord.set("class_rooms", Result.makeupList(classRoomList));
    				}
    				
    				downloadRecords.add(downloadRecord);
    			}
    			
    			deviceDownloadRecord.set("downloadRecords", Result.makeupList(downloadRecords));
    		}
    		deviceDownloadRecord.remove("data_str");
    	}
    	
    	JSONObject json = new JSONObject();
    	json.put("deviceDownloadRecords", Result.makeupList(deviceDownloadRecords));
    	
    	Result.ok(json,this);
    	
    }
    
    
    /**
     * 保存下载记录
     */
    public void saveDeviceDownloadRecord(){
    	
    	String epalId = getPara("epalId");
    	Integer bookId = getParaToInt("bookId");
    	Integer studentId = getParaToInt("studentId");
    	
    	int status = getParaToInt("status",0);
    	
    	if(xx.isOneEmpty(epalId,bookId,studentId)){
    		Result.error(203, this);
    		return;
    	}
    	
    	String sql = "INSERT INTO device_download_record (epal_id,book_id,status,student_id) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE status=?";
    	
    	Db.update(sql,epalId,bookId,status,studentId,status);
    	
    	Result.ok(this);
    	
    }
    
    /**
     * 根据studentId,epal获取要预下载的课堂
     */
    public void getBookClassDownloads(){
    	
    	Integer studentId = getParaToInt("studentId");
    	
    	String epal = getPara("epal");
    	
    	if(xx.isOneEmpty(studentId,epal)){
    		Result.error(203, this);
    		return;
    	}
    	
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT d.id AS book_id, c.id AS class_grades_id, c.class_grades_name, d.class_room_ids,d.create_time FROM class_student a JOIN");
    	sql.append(" class_grades_rela b ON a.id = b.class_student_id JOIN class_grades c ON b.class_grades_id = c.id JOIN book_class_download_record");
    	sql.append(" d ON c.id = d.grades_id LEFT JOIN device_download_record e ON d.id = e.book_id AND e.epal_id = ? AND e.student_id = ? WHERE a.id = ? AND IFNULL(e.status, 0) <= 0");
    	
    	List<Record> bookClassRooms = Db.find(sql.toString(),epal,studentId,studentId);
    	
    	
    	for(Record bookClassRoom : bookClassRooms){
    		bookClassRoom.set("class_room_ids", bookClassRoom.getStr("class_room_ids").split(","));
    	}
    	
    	JSONObject json = new JSONObject();
    	json.put("bookClassRooms",Result.makeupList(bookClassRooms));
    	Result.ok(json,this);
    	
    }
    
    
    /**
     * 设置预下载的课堂
     */
    public void bookClassDownload(){
    	
    	getResponse().addHeader("Access-Control-Allow-Origin", "*");
    	
    	BookClassDownloadRecord bookClassDownloadRecord = getBean(BookClassDownloadRecord.class,"");
    	
    	if(xx.isEmpty(bookClassDownloadRecord)){
    		Result.error(203, this);
    		return;
    	}
    	
    	bookClassDownloadRecord.save();
    	
    	Result.ok(this);
    }
    
    public void saveRobotHourse(){
    	
    	String epal = getPara("epal");
    	
    	String stuEpalId = getPara("stuEpalId");
    	
    	if(xx.isOneEmpty(epal,stuEpalId)){
    		Result.error(203, this);
    		return;
    	}
    	
    	RobotHourse robotHourse = RobotHourse.dao.findFirst("SELECT hourse_id from robot_hourse where epal_id = ?",epal);
    	
    	if(robotHourse==null){
    		Result.error(20501, "查询不到机器人所在班级",this);
    		return;
    	}
    	
    	String clear = "update robot_hourse set stu_epal_id = '' where stu_epal_id = ? and hourse_id = ?";
    	
    	Db.update(clear,stuEpalId,robotHourse.getHourseId());
    	
    	String sql = "update robot_hourse set stu_epal_id = ? where epal_id = ?";
    	
    	int rows = Db.update(sql,stuEpalId,epal);
    	
    	JSONObject json = new JSONObject();
    	json.put("affectedRows", rows);
    	Result.ok(json,this);
    }
    
    @EmptyParaValidate(params={"member"})
    public void deviceList(){
    	
    	List<DeviceRelation> deviceRelations = DeviceRelation.dao.find(
    			"SELECT c.epal_id,c.nickname FROM memberaccount a,device_relation b,device c WHERE"
    			+ " a.memberid = ? AND a.account = b.friend_id AND b.epal_id = c.epal_id",getParaToInt("member"));
    	
    	Result.ok(Result.makeupList(deviceRelations),this);
    	
    }
    
}
