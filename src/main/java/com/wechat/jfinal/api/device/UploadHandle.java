package com.wechat.jfinal.api.device;


import com.jfinal.core.Controller;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourseErrorWord;
import com.wechat.jfinal.model.RobotState;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//机器人上传操作
public class UploadHandle extends Controller{
	/*
	 * 
	 *  上传课堂发音不准确的单词（机器人）
	 *  表格：class_course_error_word
	 *  备注：
	 * 
	 * 
	 * */
    public void saveDeviceErrorWords(){
    	String studentId = getPara("studentId","");
    	String classCourseRecordId = getPara("classCourseRecordId","");
    	String words = getPara("words","");
    	if("".equals(studentId)||"".equals(classCourseRecordId)){
        	JSONObject data = new JSONObject();
        	data.put("code", 400);
        	data.put("msg", "missing parameters!");
        	renderJson(data);
    	}else{
    		if(words.startsWith("[")){
        		JSONArray json = JSONArray.fromObject(words); 
        		for(int i=0;i<json.size();i++){
        			JSONObject temp = json.getJSONObject(i);
        			ClassCourseErrorWord classCourseErrorWord = new ClassCourseErrorWord();
        			classCourseErrorWord.setClassCourseRecordId(Integer.parseInt(classCourseRecordId));
        			classCourseErrorWord.setScore(Integer.parseInt(temp.getString("score")));
        			classCourseErrorWord.setStudentId(Integer.parseInt(studentId));
        			classCourseErrorWord.setWord(temp.getString("word"));
        			classCourseErrorWord.save();
        		}

            	JSONObject data = new JSONObject();
            	data.put("code", 200);
            	data.put("msg", "save success!");
            	renderJson(data);
    		}else{
    			//words非json字符串
            	JSONObject data = new JSONObject();
            	data.put("code", 203);
            	data.put("msg", "data error!");
            	renderJson(data);
    		}
    	}
    	return;
    }
    
    /**
     * 	机器人上传当前状态
     * */
    public void uploadDeviceState(){
    	RobotState robotState = getBean(RobotState.class,"");
    	robotState.save();
    	Result.ok(this);
    }
}
