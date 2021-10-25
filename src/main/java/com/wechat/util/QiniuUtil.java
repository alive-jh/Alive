package com.wechat.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.hibernate.SessionFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class QiniuUtil {

	static boolean result=false;
	static byte[] buffer;
	
	
	public static String fileUpdate(MultipartFile file,String fileName){
		CommonsMultipartFile cf = (CommonsMultipartFile) file;
		DiskFileItem fi = (DiskFileItem) cf.getFileItem();
		File f = fi.getStoreLocation();
		
		try {
			buffer = getByte(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		String bucket = "source";
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		// String key = "http://oud4k0amd.bkt.clouddn.com/"+new
		// Date().getTime()+"123.jpg";
		// String key= "http://oud4k0amd.bkt.clouddn.com/"+new
		// Date().getTime()+"123456.jpg";
		String key = fileName;
		Auth auth = Auth.create(Keys.ACCESS_KEY, Keys.SECRET_KEY);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(buffer, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
					DefaultPutRet.class);
			////System.out.println(putRet.key);
			////System.out.println(putRet.hash);
			if(putRet.key!=null&&putRet.hash!=null){
				result=true;
			}
			
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		return key;
	}
	
	public static String fileUpdate(File file,String fileName){
		try {
			buffer = getByte(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Configuration cfg = new Configuration(Zone.zone0());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);
		String bucket = "source";
		// 默认不指定key的情况下，以文件内容的hash值作为文件名
		// String key = "http://oud4k0amd.bkt.clouddn.com/"+new
		// Date().getTime()+"123.jpg";
		// String key= "http://oud4k0amd.bkt.clouddn.com/"+new
		// Date().getTime()+"123456.jpg";
		String key = fileName;
		Auth auth = Auth.create(Keys.ACCESS_KEY, Keys.SECRET_KEY);
		String upToken = auth.uploadToken(bucket);
		try {
			Response response = uploadManager.put(buffer, key, upToken);
			// 解析上传成功的结果
			DefaultPutRet putRet = new Gson().fromJson(response.bodyString(),
					DefaultPutRet.class);
			////System.out.println(putRet.key);
			////System.out.println(putRet.hash);
			if(putRet.key!=null&&putRet.hash!=null){
				result=true;
			}
			
		} catch (QiniuException ex) {
			Response r = ex.response;
			System.err.println(r.toString());
			try {
				System.err.println(r.bodyString());
			} catch (QiniuException ex2) {
				// ignore
			}
		}
		return key;
	}
	
	//文件转二进制
	public static byte[] getByte(File file) throws IOException {
		FileInputStream fis=null;
		byte[] buffer = null;
		fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		fis.close();
		bos.close();
		buffer = bos.toByteArray();
		
		return buffer;
	}
	
	
	
	
	
}
