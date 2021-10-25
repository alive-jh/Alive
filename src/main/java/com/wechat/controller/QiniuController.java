package com.wechat.controller;

import com.wechat.util.QiniuUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Controller
@CrossOrigin
@RequestMapping("api")
public class QiniuController {
	
	 
	@RequestMapping("uploadFiles")
	@ResponseBody
	public Object saveFile(MultipartFile file){
		String fileName1=file.getOriginalFilename();
		
		/*String fileName = (UUID.randomUUID().toString().replace("-", ""))+fileName1.subSequence(fileName1.indexOf("."),
				fileName1.length());*/
		
		int random = (int) (Math.random() * 1000000);
		String fileName = new Date().getTime()+ random	+ ""+ fileName1.subSequence(fileName1.indexOf("."),
				fileName1.length());
		String key = QiniuUtil.fileUpdate(file, fileName);
		Map<String,String> map = new HashMap<String,String>();
		map.put("key", "http://source.fandoutech.com.cn/"+key);
		return map;
	}
	
	@RequestMapping("ueditorUpload")
	@ResponseBody
	public Object ueditorUpload(MultipartFile file){
		
		//System.out.println("here");
		String fileName1=file.getOriginalFilename();
		
		/*String fileName = (UUID.randomUUID().toString().replace("-", ""))+fileName1.subSequence(fileName1.indexOf("."),
				fileName1.length());*/
		int random = (int) (Math.random() * 1000000);
		String fileName = new Date().getTime()+ random	+ ""+ fileName1.subSequence(fileName1.indexOf("."),
				fileName1.length());
		String key = QiniuUtil.fileUpdate(file, fileName);
		Map<String,String> map = new HashMap<String,String>();
		map.put("url", "http://source.fandoutech.com.cn/"+key);
		map.put("state", "SUCCESS");	
		map.put("title","demo.jpg");
		map.put("original", "demo.jpg");
		return map;
	}
	
	
}

