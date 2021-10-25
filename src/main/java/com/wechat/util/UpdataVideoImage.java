package com.wechat.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations={"classpath:springmvc-servlet.xml"})
public class UpdataVideoImage {

	@Autowired
	SessionFactory sessionFactory;
	
	Session session;
	
	@Before
	public void befor(){
		session = sessionFactory.openSession();
	}
	
	Map<String,String> vids = new HashMap<String,String>(); 
	
	@Test
	public void getVideoInfo() throws Exception{
		String sql = "select id,v_url from video_info where pic_url='https://word.fandoutech.com.cn/timg.jpg'";
		List list = session.createSQLQuery(sql).list();
		for(int i=0;i<list.size();i++){
			download(((Object[]) list.get(i))[1].toString(), ((Object[]) list.get(i))[0].toString());
		}
	}
	
	public void download(String fileUrl, String fileName) throws Exception {
		//System.out.println("开始下载id为"+fileName+"的视频");
		URL url = new URL(fileUrl);
		HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
		urlCon.setConnectTimeout(6000);
		urlCon.setReadTimeout(6000);
		int code = urlCon.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new Exception(fileName+"文件读取失败");
		}
		File filedir = new File("F://videos");
		if(!filedir.exists()){
			filedir.mkdir();
		}
		DataInputStream in = new DataInputStream(urlCon.getInputStream());
		DataOutputStream out = new DataOutputStream(new FileOutputStream("F://videos//"+fileName+".mp4"));
		byte[] buffer = new byte[2048];
		int count = 0;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
		//System.out.println(fileName+"文件下载成功");
	}
	
	@Test
	public void readFilesWithVideo(){
		String videosDirectory="F://videos//";
		String imgDirectory="F://imgs";
		String ffmpeg_path="F//ffmpeg.exe";
		File directory = new File(videosDirectory);
		int num=0;
		if(directory.isDirectory()){
             String[] filelist = directory.list();
             for (int i = 0; i < filelist.length; i++) {
            	 File readfile = new File(videosDirectory + "\\" + filelist[i]);
                 String veido_path=readfile.getName();
                 String imgName=readfile.getName().split(".mp4")[0]+".jpg";
                 processImg(videosDirectory+veido_path,"f://ffmpeg.exe");
                 //System.out.println(veido_path+"---截取成功");
             }
             
		}
	}
	
	@Test
	public void readFilesWithImg(){
		String imgDirectory="F://imgs";
		File directory = new File(imgDirectory);
		int num=0;
		if(directory.isDirectory()){
             String[] filelist = directory.list();
             for (int i = 0; i < filelist.length; i++) {
            	 File readfile = new File(imgDirectory + "\\" + filelist[i]);
                 int vid=Integer.parseInt(readfile.getName().split(".jpg")[0]);
                 String pic_url = uploadFile(readfile);
                 String sql = "update video_info set pic_url='"+pic_url+"' where id="+vid;
                 //System.out.println(session.createSQLQuery(sql).executeUpdate());
                 num++;
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
		
	public void processImg(String veido_path,String ffmpeg_path) {
		File file = new File(veido_path);
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("8");//这个参数是设置截取视频多少秒时的画面
		//commands.add("-t");
		//commands.add("0.001");
		commands.add("-s");
		commands.add("480x640");
		commands.add(veido_path.substring(0, veido_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
		try {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(commands);
		builder.start();
		//System.out.println("截取成功");
		} catch (Exception e) {
		e.printStackTrace();
		}
}	
		
		
		
}
