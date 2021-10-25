package com.wechat.qiniu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.BucketManager.FileListIterator;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wechat.util.Keys;

import net.sf.json.JSONObject;

public class QiniuUtil {

	  //设置好账号的ACCESS_KEY和SECRET_KEY
	 
	  //密钥配置
	  private static Auth auth = Auth.create(Keys.ACCESS_KEY, Keys.SECRET_KEY);

	  //简单上传，使用默认策略，只需要设置上传的空间名就可以了
	  public String getUpToken(String mykey,String bucketName){
		  
		  /*
		   * 设置上传策略
		   */
		  StringMap putPolicy = new StringMap();
	      putPolicy.putNotEmpty("mimeLimit", "!text/html;image/svg+xml");//禁止上传html文件
	      
	      return auth.uploadToken(bucketName, null, 1800, putPolicy);
	      
		 //return auth.uploadToken(bucketName);
	     // return auth.uploadToken(bucketName, mykey, 3600, new StringMap().put("insertOnly", 1));
	  }
	  

	  /**
	   * 
	   * @param path 图片上传路径
	   * @param fileType 图片类型
	   * @param space 图片空间:image
	   * @return
	   */
		public static String addFile(String path,String fileName,String space) {
			
			
	        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
	        Zone z = Zone.autoZone();
	        Configuration c = new Configuration(z);
	        //创建上传对象
	        UploadManager uploadManager = new UploadManager(c);
	        String qrCodeUrl="";
	        try {
	            //调用put方法上传
	            Response res = uploadManager.put(path+fileName, fileName, new QiniuUtil().getUpToken(fileName,space));
	            //打印返回的信息
	            JSONObject qiniuRes = JSONObject.fromObject(res.bodyString());
	            qrCodeUrl = Keys.URL_ADDRESS + qiniuRes.getString("key");
	        } catch (QiniuException e) {
	            Response r = e.response;
	            // 请求失败时打印的异常的信息
	            //System.out.println(r.toString());
	            try {
	                //响应的文本信息
	                System.out.println(r.bodyString());
	            } catch (QiniuException e1) {
	                //ignore
	            }
	        }
	        //System.out.println("url = "+qrCodeUrl);
	        return qrCodeUrl;
	        
		}
		
		
		/**
		 * 
		 * @param space 图片空间
		 * @param fileName 图片名称
		 */
		public  static void  delFile(String space,String fileName)
		{
			Zone z = Zone.zone0();
		    Configuration cfg = new Configuration(z);
		    ////System.out.println("fileName = "+fileName);
			BucketManager bucketManager = new BucketManager(auth, cfg);
			
			 try {
				 bucketManager.delete(space, fileName);
				} catch (QiniuException ex) {
				    //如果遇到异常，说明删除失败
				    System.err.println(ex.code());
				    System.err.println(ex.response.toString());
				}
			
		}
		
		
		public static void main(String[] args) throws QiniuException {
		
			//QiniuUtil qiniuUtil = new QiniuUtil();
			////System.out.println(QiniuUtil.addFile(Keys.USER_PIC_PATH +"/lesson/" + "1003101.mp3", "mp3",Keys.QINIU_IMAGE));
			//QiniuUtil.delFile(Keys.QINIU_IMAGE, "1e5b6c9070054213825d88e636169278.mp3");
			
			String bucket = "word";
			QiniuUtil qiniuUtil = new QiniuUtil();
			
			BucketManager.FileListIterator fileListIterator = qiniuUtil.fileList(bucket);
			
			while (fileListIterator.hasNext()) {
				// 处理获取的file list结果
				FileInfo[] items = fileListIterator.next();
				for (FileInfo item : items) {

					if (item.mimeType.equals("image/svg+xml")) {
						System.out.println(item.key);
					}

				}
			}
			
		}
		
		public void uploadHtml(String path) throws QiniuException{
			
			Configuration cfg = new Configuration(Zone.autoZone());
			UploadManager uploadManager = new UploadManager(cfg);
			
			Response response =  uploadManager.put(new File(path), null, getUpToken(null, "word"));
			
			System.out.println(response.toString());
			
			
		}

	public FileListIterator fileList(String bucket) {

		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.autoZone());

		BucketManager bucketManager = new BucketManager(auth, cfg);

		// 文件名前缀
		String prefix = "";
		// 每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		// 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "";

		// 列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit,
				delimiter);


		return bucketManager.createFileListIterator(bucket, prefix, limit,
				delimiter);
	}
}
