package com.wechat.util;

import com.wechat.entity.VideoInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"classpath:springmvc-servlet.xml"})
public class UploadFile {
	
	@Autowired
	SessionFactory sessionFactory;
	
	Session session;
	
	@Before
	public void befor(){
		session = sessionFactory.openSession();
	}
	
	String filepath="c:/userVideo7";
	String vTitle="用户参赛视频08";
	
	public void readFiles(){
		String studentName="";
		
		File directory = new File(filepath);
		int num=0;
		if(directory.isDirectory()){
             String[] filelist = directory.list();
             for (int i = 0; i < filelist.length; i++) {
                     File readfile = new File(filepath + "\\" + filelist[i]);
                     studentName = readfile.getName().split("-")[1].split(".mp4")[0];
                     //System.out.println(studentName+"的视频---读取成功");
                     String sql = "SELECT a.epal_id FROM class_student a WHERE a.name='"+studentName+"' group by a.epal_id";
                     List list = session.createSQLQuery(sql).list();
                     if(list.size()==1){
                    	  String sql2="SELECT b.memberid FROM device_relation a,memberaccount b WHERE a.epal_id='"+list.get(0).toString()+"' AND a.`friend_id`=b.account";
                    	  String acountId=session.createSQLQuery(sql2).list().get(0).toString();
                    	  String epalId=list.get(0).toString();
                    	  //System.out.println(studentName+"的视频---开始上传");
                    	  String vUrl = uploadFile(readfile);
                    	  //System.out.println(studentName+"的视频---上传成功");
                    	  VideoInfo video = new VideoInfo();
                    	  video.setvTitle(vTitle);
                    	  video.setAcountId(acountId);
                    	  video.setEpalId(epalId);
                    	  video.setvUrl(vUrl);
                    	  video.setShareUrl(vUrl);
                    	  video.setCreateTime(new Date());
                    	  video.setAdmission(0);
                    	  video.setVote(0);
                    	  video.setAccessNum(0);
                    	  video.setPicUrl("https://word.fandoutech.com.cn/timg.jpg");
                    	  session.save(video);
                    	  //System.out.println(studentName+"的视频---保存到数据库成功");
                    	  num++;
                     }else{
                    	 //System.out.println(studentName+"---没有执行");
                     }
             }
             //System.out.println(num);
		}
	}
	
	public String uploadFile(File file){
		String fileName1 = file.getName();
		int random = (int) (Math.random() * 1000000);
		String fileName = new Date().getTime()+ random	+ ""+ fileName1.subSequence(fileName1.indexOf("."),
				fileName1.length());
		String key = QiniuUtil.fileUpdate(file, fileName);
		return "https://word.fandoutech.com.cn/"+key;
	}
	
	@Test
	public void saveCompetition(){
		String sql = "select id,acount_id from video_info where v_title='"+vTitle+"'";
		List list = session.createSQLQuery(sql).list();
		int num=0;
		for(int i=0;i<list.size();i++){
			Object[] obj = (Object[]) list.get(i);
			String insert = "insert into video_competition(video_info_id,video_activity_id,acoutn_id,verify_status) values ("+obj[0]+",1,'"+obj[1]+"',0)";
			int result = session.createSQLQuery(insert).executeUpdate();
			if(result==1){
				//System.out.println("执行成功");
				num++;
			}else{
				//System.out.println(obj[0]+"执行失败");
			}
		}
		//System.out.println(num);
	}
	
	@Test
	public void saveCompetition2(){
		String sql = "select id from video_info where v_title='"+vTitle+"'";
		List list = session.createSQLQuery(sql).list();
		//System.out.println(list.size());
		int num=0;
		for(int i=0;i<list.size();i++){
			String sql2 = "select id from video_competition where video_info_id="+list.get(i);
			int admission = (int) session.createSQLQuery(sql2).list().get(0);
			String insert = "update video_info set admission="+admission+" where id="+list.get(i);
			//System.out.println(insert.toString());
			int result = session.createSQLQuery(insert).executeUpdate();
			if(result == 1){
			//System.out.println(list.get(i)+"执行成功");
			num++;
			}else{
				//System.out.println(list.get(i)+"执行失败");
			}
		}
		//System.out.println(num);
		
	}
	
	
	
	
	
	
	
	
	
	
}
