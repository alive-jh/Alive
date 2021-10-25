package com.wechat.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CourseStruct {
	
	public static void main(String[] args) {
		//主课程数组
		JSONArray mainCourses=new JSONArray();
		JSONObject mainCourse=new JSONObject();
		mainCourse.accumulate("groupList", new JSONArray());
		mainCourse.accumulate("teacherList", new JSONArray());
		mainCourse.accumulate("level", 0);
		mainCourse.accumulate("name", "语素 - c");
		mainCourse.accumulate("content", "语素 - c");
		mainCourse.accumulate("logo", "http://ocvbx5hih.bkt.clouddn.com/images/yusu-c/1.jpg");
		mainCourse.accumulate("type", 2);
		//子课程数组
		JSONArray childCourses=new JSONArray();
		JSONObject childCourse1=new JSONObject();
		childCourse1.accumulate("resourceList", new JSONArray());
		childCourse1.accumulate("name", "语素-c>第一章");
		childCourse1.accumulate("url", "http://ocghbl1t3.bkt.clouddn.com/sound/yusu-c/1/1.mp3");
		
		JSONObject childCourse2=new JSONObject();
		childCourse2.accumulate("resourceList", new JSONArray());
		childCourse2.accumulate("name", "语素-c>第二章");
		childCourse2.accumulate("url", "http://ocghbl1t3.bkt.clouddn.com/sound/yusu-c/1/2.mp3");
		
		//子课时数组
		JSONArray periodCourses1=new JSONArray();
		JSONObject periodCourse=new JSONObject();
		periodCourse.accumulate("id", 1);
		periodCourse.accumulate("name", "语素-c>第一章>第一课时");
		
		//指令集数组
		JSONArray cmdCourses=new JSONArray();
		JSONObject cmdCourse=new JSONObject();
		cmdCourse.accumulate("id", 1);
		cmdCourse.accumulate("command", 1);
		cmdCourse.accumulate("times", 60);
		cmdCourse.accumulate("content", "");
		cmdCourse.accumulate("srcUrl", "http://ocghbl1t3.bkt.clouddn.com/sound/yusu-c/1/1/1.mp3");
		cmdCourse.accumulate("expUrl", "http://ocghbl1t3.bkt.clouddn.com/sound/yusu-c/1/1/2.mp3");
		cmdCourse.accumulate("mediaInfo", "http://odhfd40yt.bkt.clouddn.com/script/yusu-c/1/1/1.xsd");
		cmdCourse.accumulate("wordList", new JSONArray());
		//添加指令集
		cmdCourses.add(cmdCourse);
		periodCourse.accumulate("missionCmdList", cmdCourses);
		//添加子课时
		periodCourses1.add(periodCourse);
		
		childCourse1.accumulate("missionList", periodCourses1);
		periodCourse.remove("name");
		periodCourse.accumulate("name", "语素-c>第二章>第一课时");
		periodCourses1.remove(0);
		periodCourses1.add(periodCourse);
		childCourse2.accumulate("missionList", periodCourses1);
		//添加子课程
		childCourses.add(childCourse1);
		childCourses.add(childCourse2);
		mainCourse.accumulate("subLessonList", childCourses);
		//添加主课程
		mainCourses.add(mainCourse);
		
		//System.out.println(mainCourses.toString());
	}

}
