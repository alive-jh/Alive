package com.wechat.jfinal.api.teacher;


import com.jfinal.core.Controller;
import com.wechat.jfinal.model.TeacherCollectionClassRoom;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.List;

//老师对课堂的操作
public class T_ClassRoomHandle extends Controller{
	/*
	 * 
	 *  保存课堂（APP）
	 *  表格：teacher_collection_class_room
	 *  备注：保存老师收藏到数据库
	 * 
	 * 
	 * */
    public void saveClassRoomCollection(){
    	String teacherId = getPara("teacherId","");
    	String classRoomId = getPara("classRoomId","");
    	if("".equals(teacherId)||"".equals(classRoomId)){
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
    	}else{
        	TeacherCollectionClassRoom teacherCollectionClassRoom = new TeacherCollectionClassRoom();
        	teacherCollectionClassRoom.setClassRoomId(Integer.parseInt(classRoomId));
        	teacherCollectionClassRoom.setTeacherId(Integer.parseInt(teacherId));
			teacherCollectionClassRoom.setCreateTime(new Date());
        	teacherCollectionClassRoom.save();
        	JSONObject data = new JSONObject();
        	data.put("code", 200);
        	data.put("msg", "save success!");
        	renderJson(data);
    	}
    	return;
    }
    
	/*
	 * 
	 *  老师取消收藏课堂（APP）
	 *  表格：teacher_collection_class_room
	 *  备注：老师取消课堂收藏
	 * 
	 * 
	 * */
    public void cancelClassRoomCollection(){
    	String teacherId = getPara("teacherId","");
    	String classRoomId = getPara("classRoomId","");
    	if("".equals(teacherId)||"".equals(classRoomId)){
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
    	}else{
        	
        	List<TeacherCollectionClassRoom> classRoomList= TeacherCollectionClassRoom.dao.find("select id from teacher_collection_class_room where class_room_id=? and teacher_id=?",Integer.parseInt(classRoomId),Integer.parseInt(teacherId));
        	if(classRoomList.size()>0){
        		TeacherCollectionClassRoom teacherCollectionClassRoom = classRoomList.get(0);
        		teacherCollectionClassRoom.delete();
            	JSONObject data = new JSONObject();
            	data.put("code", 200);
            	data.put("msg", "cancel success!");
            	renderJson(data);
        	}else{
            	JSONObject data = new JSONObject();
            	data.put("code", 400);
            	data.put("msg", "The source Non-existent");
            	renderJson(data);
        	}

    	}
    	return;

    }
      
    
	/*
	 * 
	 *  老师获取收藏课堂（APP）
	 *  表格：teacher_collection_class_room
	 *  备注：老师取消课堂收藏
	 * 
	 * 
	 * */
    public void getClassRoomCollection(){
    	String teacherId = getPara("teacherId","");
    	
    	if("".equals(teacherId)){
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
    	}else{
        	
        	List<TeacherCollectionClassRoom> classRoomList= TeacherCollectionClassRoom.dao.find("select id,teacher_id,class_room_id from teacher_collection_class_room where teacher_id=?",Integer.parseInt(teacherId));
        	if(classRoomList.size()>0){
            	JSONObject data = new JSONObject();
            	data.put("code", 200);
            	data.put("msg", "cancel success!");
            	renderJson(classRoomList);
        	}else{
            	JSONObject data = new JSONObject();
            	data.put("code", 400);
            	data.put("msg", "The source Non-existent");
            	renderJson(data);
        	}

    	}
    	return;

    }
}
