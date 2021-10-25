package com.wechat.test;

import com.wechat.entity.ClassCourse;
import com.wechat.util.JsonResult;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Test {

//	public static void main(String[] args) {
//		String test="2664#16#Biffs Fun Phonics#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/Biffs Fun Phonics#0#null@2665#16#Kippers Alphabet I Spy#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/Kippers Alphabet I Spy#0#null@2666#16#The Pancake#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/The Pancake#0#null@2667#16#A Good Trick#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/A Good Trick#0#null@2668#16#CHIPS LETTER SOUNDS#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/CHIPS LETTER SOUNDS#0#null@2669#16#BIFFS WONDER WORDS#https://wechat.fandoutech.com.cn//wechat/wechatImages/book/mp3/BIFFS WONDER WORDS#0#null";
//		String[] tests=test.split("@");
//		for (int i = 0; i < tests.length; i++) {
//			//System.out.println(tests[i]);
//		}
//	}
	
	public static void main(String[] args) {
		ArrayList<ClassCourse> classCourses=new ArrayList<ClassCourse>();
		ClassCourse classCourse1=new ClassCourse("test1", 1, 2, 5, 6,"", new Timestamp(System.currentTimeMillis()));
		ClassCourse classCourse2=new ClassCourse("test2", 1, 2, 5, 6,"", new Timestamp(System.currentTimeMillis()));
		//ClassCourse classCourse3=new ClassCourse("test3", 1, 2, 5, 6,"", new Timestamp(System.currentTimeMillis()));
		classCourses.add(classCourse1);
		classCourses.add(classCourse2);
		//classCourses.add(classCourse3);
		//System.out.println(JsonResult.JsonResultToStr(classCourses));
	}
}
