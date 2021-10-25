package com.wechat.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class FileDownloader{

	int threadNum = 1;
	
	private Map<String, String> urlsMap;
	
	private String directory;
	
	public FileDownloader(int threadNum,Map<String, String> urlsMap,String directory){
		this.threadNum = threadNum;
		this.urlsMap = urlsMap;
		this.directory = directory;
	}
	
	public static int num = 0;
	
	public void start(){
		
		for(int i=0;i<threadNum;i++){
			new Thread(new Downloader()).start();
		}
		
	}
	
	public class Downloader implements Runnable{

		@Override
		public void run() {
			BatchDownload(urlsMap, directory);
		}
		
		public void BatchDownload(Map<String, String> urlsMap, String directory) {

			for (String tempfileName : urlsMap.keySet()) {

				try {
					BatchDownload(urlsMap.get(tempfileName), tempfileName, directory);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

			}

		}

		public void BatchDownload(String fileUrl, String fileName, String directory) throws Exception {

			File outFile = new File(directory + "//" + fileName);
			
			if(outFile.exists()){
				return;
			}
			
			URL url = new URL(fileUrl);
			HttpURLConnection urlConn = null;

			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setConnectTimeout(5 * 1000);

			InputStream is = urlConn.getInputStream();
			DataOutputStream out = new DataOutputStream(new FileOutputStream(outFile));
			byte[] buffer = new byte[urlConn.getContentLength()];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}

			out.close();
			is.close();
			
		}

		
	}
	
	
	public static void main(String[] args) {
		Map<String, String> urlsMap = new HashMap<String, String>();
		urlsMap.put("1000101.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000101.xls");
		urlsMap.put("1000102.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000102.xls");
		urlsMap.put("1000103.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000103.xls");
		urlsMap.put("1000104.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000104.xls");
		urlsMap.put("1000105.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000105.xls");
		urlsMap.put("1000106.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000106.xls");
		urlsMap.put("1000201.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000201.xls");
		urlsMap.put("1000202.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000202.xls");
		urlsMap.put("1000203.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000203.xls");
		urlsMap.put("1000204.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000204.xls");
		urlsMap.put("1000205.xls", "http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/1000205.xls");
		
		FileDownloader fileDownloader = new FileDownloader(10, urlsMap, "c://test111");
		fileDownloader.start();
	}


}
