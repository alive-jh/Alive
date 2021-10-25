package com.wechat.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class qiniuTest {
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
    	//System.out.println(urlStr);
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
  
  
        //System.out.println("info:"+url+" download success");   
  
    }  
  
  
  
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    

	public static void main(String args[]) { 
        //System.out.println("Hello World!"); 
   
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释

		String accessKey = "vIkgOuBrwb1ySeLKY3NqhrfiZoY7J_I1baWxeMrX";
		String secretKey = "38Z2HlWeq-wy6huqDrnMy_3qBNLP_tB9yZvIUkoM";

		String bucket = "source";

		Auth auth = Auth.create(accessKey, secretKey);
		BucketManager bucketManager = new BucketManager(auth, cfg);

		//文件名前缀
		String prefix = "epal/21/crashLog";
		//每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "";

	    
	
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		while (fileListIterator.hasNext()) {
		    //处理获取的file list结果
		    FileInfo[] items = fileListIterator.next();
		    for (FileInfo item : items) {
		    
		        try{  
		            downLoadFromUrl("http://source.fandoutech.com.cn/" + item.key,  
		                    item.key,"F:\\crash\\");  
		        }catch (Exception e) {  
		        	e.printStackTrace();
		            // TODO: handle exception  
		        }  
		        try {
		            //单次批量请求的文件数量不得超过1000
		            String[] keyList = new String[]{
		            		item.key,
		            };
		            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		            batchOperations.addDeleteOp(bucket, keyList);
		            Response response = bucketManager.batch(batchOperations);
		            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
		            for (int i = 0; i < keyList.length; i++) {
		                BatchStatus status = batchStatusList[i];
		                String key = keyList[i];
		                //System.out.print(key + "\t");
		                if (status.code == 200) {
		                    //System.out.println("delete success");
		                } else {
		                    //System.out.println(status.data.error);
		                }
		            }
		        } catch (QiniuException ex) {
		            System.err.println(ex.response.toString());
		        }
		    }
		}

		
		
	}
	

}
