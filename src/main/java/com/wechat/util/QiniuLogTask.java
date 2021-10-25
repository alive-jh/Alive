package com.wechat.util;

import com.qiniu.cdn.CdnManager;
import com.qiniu.cdn.CdnResult;
import com.qiniu.cdn.CdnResult.LogData;
import com.qiniu.common.QiniuException;
import com.qiniu.util.Auth;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class QiniuLogTask extends TimerTask {


	Session session = null;
	
	@Override
	public void run() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("springmvc-servlet.xml");
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		session = sessionFactory.openSession();
		Auth auth = Auth.create(Keys.ACCESS_KEY, Keys.SECRET_KEY);
		CdnManager c = new CdnManager(auth);
			
		// 域名列表
		String[] domains = new String[] { "word.fandoutech.com.cn" };
		// 具体日期
		String logDate = "2017-10-16";
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String logDate = sdf.format(new Date());*/

		try {
			CdnResult.LogListResult logListResult = c.getCdnLogList(domains,
					logDate);
			LogData[] log = logListResult.data.get("word.fandoutech.com.cn");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			//int num = log.length - 1;
			for(int num=0;num<log.length;num++){
			String fileUrl = log[num].url;
			String fileName = sdf2.format(new Date((log[num].mtime) * 1000));
			//System.out.println(fileName + "日志开始解析");
			try {
				download(fileUrl, fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

		} catch (QiniuException e) {
			System.err.println(e.response.toString());
		}
		
		
		
	}

	public void download(String fileUrl, String fileName) throws Exception {

		URL url = new URL(fileUrl);
		HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
		urlCon.setConnectTimeout(6000);
		urlCon.setReadTimeout(6000);
		int code = urlCon.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new Exception("文件读取失败");
		}
		File filedir = new File(Keys.QINIU_LOG_PATH);
		if(!filedir.exists()){
			filedir.mkdir();
		}
		File logFile = new File(Keys.QINIU_LOG_PATH + fileName + ".gz");
		if(!logFile.exists()){
		DataInputStream in = new DataInputStream(urlCon.getInputStream());
		DataOutputStream out = new DataOutputStream(new FileOutputStream(
				Keys.QINIU_LOG_PATH + fileName + ".gz"));
		byte[] buffer = new byte[2048];
		int count = 0;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
		//System.out.println(fileName + "下载成功！");
		unGzipFile(fileName);
		}else{
			//System.out.println(fileName+"日志已经解析");
		}
	}

	public void unGzipFile(String fileName) throws Exception {
		// File file = new File("d:\\qiniuLog\\2017-09-27-15.gz");
		File file = new File(Keys.QINIU_LOG_PATH + fileName + ".gz");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(getByte(file));
		try {
			GZIPInputStream ungzip = new GZIPInputStream(in);
			byte[] buffer = new byte[256];
			int n;
			while ((n = ungzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		File outfile = new File(Keys.QINIU_LOG_PATH + fileName);

		OutputStream output = new FileOutputStream(outfile);

		BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);

		bufferedOutput.write(out.toByteArray());
		//System.out.println(fileName + "解压成功!");
		statistics(outfile);

	}

	public void statistics(File file) throws Exception {
		int status=0;
		String fileUrl="http://word.fandoutech.com.cn/app/upStudentMovie/";
		StringBuilder result = new StringBuilder();
		int num = 0;
		String[] log = new String[100000];
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				String lineContext = System.lineSeparator() + s;
				if (lineContext.contains(fileUrl)) {
					String tempUrl="";	
					tempUrl = ((lineContext.split(fileUrl)[1]).split(".mp4")[0]);
					tempUrl = fileUrl+tempUrl+".mp4";
					log[num] = tempUrl;
					//System.out.println(log[num]);
					num++;
				}
			}
			br.close();
			//System.out.println("一共"+num+"条记录");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String str1 : log) {
			Integer num1 = map.get(str1);
			num1 = null == num1 ? 1 : num1 + 1;
			map.put(str1, num1);
		}
		Set set = map.entrySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Entry<String, Integer> entry = (Entry<String, Integer>) it
					.next();
			//System.out.println(entry.getKey() + " 访问次数 " + entry.getValue());
			if (entry.getKey() != null) {
				status=session.createSQLQuery("update video_info set `access_num`=`access_num`+"+ entry.getValue()+ " where v_url = '"+ entry.getKey()+ "'").executeUpdate();
			}
			//System.out.println(entry.getKey()+"更新数据库"+status+"条");
		}
		//session.close();
		
	}

	// 文件转二进制
	public static byte[] getByte(File file) throws IOException {
		FileInputStream fis = null;
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
