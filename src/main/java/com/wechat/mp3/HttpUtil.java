package com.wechat.mp3;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {
	/**
	 * 从http下载单个文件
	 * 
	 * @param srcUrl
	 *            文件网络路径
	 * @param tarUrl
	 *            本地保存路径
	 * */
	public static int precent = 0;
	public static long file_length = 0, total_length = 0;;

	public static boolean downloadFromHTTP(String httpUrl1, String localUrl) {
		boolean res = false;
		OutputStream output = null;
		File localFile = null;
		File tmpLocalFile = null;
		File oldLocalFile = null;
		try {
			// processTimer();
			localFile = new File(localUrl);
			tmpLocalFile = new File(localUrl + ".tmp");
			oldLocalFile = new File(localUrl + ".old");

			String httpUrl;// = httpUrl1;

			String path = "";
			if (httpUrl1.startsWith("http://wechat.")) {
				httpUrl = "https" + httpUrl1.substring(4);
			} else {
				httpUrl = httpUrl1;
			}
			httpUrl = httpUrl.replace(" ", "%20");

			URL url = new URL(httpUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(15 * 1000); // 其中：
												// sun.net.client.defaultConnectTimeout：连接主机的超时时间（单位：毫秒）
			conn.setReadTimeout(10 * 1000); // sun.net.client.defaultReadTimeout：从主机读取数据的超时时间（单位：毫秒）
			
			System.setProperty("sun.net.client.defaultConnectTimeout", "15000");
			System.setProperty("sun.net.client.defaultReadTimeout", "15000");
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
			conn.setRequestProperty("Accept", "*/*");
//			if (ProxyManager.getInstance().getIsProxyEnable() > 0) {
//				String encoded = ProxyManager.getInstance().getEncode();
//				if (!encoded.isEmpty()) {
//					conn.setRequestProperty("Proxy-Authorization", "Basic " + encoded);
//				}
//			}
			conn.setDoOutput(true);
			InputStream input = conn.getInputStream();

			if (!tmpLocalFile.exists()) {
				tmpLocalFile.getParentFile().mkdirs();
				tmpLocalFile.createNewFile();
			}
			output = new FileOutputStream(tmpLocalFile);
			file_length = conn.getContentLength();

			// 读取大文件
			byte[] buffer = new byte[4 * 1024]; //
			int tempLen = 1;

			tempLen = input.read(buffer);
			while (tempLen > 0) {// 将input流中的信息写入SDCard
				// Log.e("HTTP", "HTTP tmp="+tempLen);
				if (tempLen <= 0) {
					break;
				}
				output.write(buffer, 0, tempLen);
				total_length += tempLen;
				tempLen = input.read(buffer);
				// publishProgress(value);
			}
			output.flush();

			if (localFile.exists()) {
				localFile.renameTo(oldLocalFile);// 备份原文件
			}
			if (tmpLocalFile.renameTo(localFile)) {// 把下载文件重命名成正确名称
				res = true;
				oldLocalFile.delete();// 重命名成功之后，删除备份的原文件
			} else {
				oldLocalFile.renameTo(localFile);// 重命名失败，恢复备份的原文件
			}

			// if(localFile.exists()){
			// localFile.delete();
			// }
			// tmpLocalFile.renameTo(localFile);
			// res = true;
		} catch (MalformedURLException e) {
			res = false;
			tmpLocalFile.delete();
			e.printStackTrace();
		} catch (IOException e) {
			res = false;
			tmpLocalFile.delete();
			e.printStackTrace();
		} catch (Exception e) {
			res = false;
			tmpLocalFile.delete();
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!localFile.exists()) {
				if (oldLocalFile.exists()) {
					oldLocalFile.renameTo(localFile);
				}
			}
		}
		file_length = 0;
		total_length = 0;
		precent = 0;
		return res;
	}
	
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static boolean downLoadFromUrl(String urlStr,String fileName,String savePath){  
		try {
			if(urlStr  == null || urlStr.equals("")){
				return false;
			}
			
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}    
         
		return true;
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
}
