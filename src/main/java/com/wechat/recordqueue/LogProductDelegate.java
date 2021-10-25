package com.wechat.recordqueue;

import com.wechat.entity.SmsLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



public class LogProductDelegate implements Runnable{
	
	
	private ApplicationContext appContext; 
	//private AddressBookService addressBookService;
	private SmsLog smsLog;
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private static BlockingQueue<SmsLog> queue ;
	private ServletContext servletContext;
	
	public LogProductDelegate(ServletContext servletContext){
		
		appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
		//addressBookService = (AddressBookService)appContext.getBean("addressBookService");
		this.servletContext = servletContext;
		if(servletContext != null){
			if(queue!= null)
			{
				queue = (LinkedBlockingQueue<SmsLog>) servletContext.getAttribute("taskqueue_Indentify");
			}
			else
			{
				queue = new LinkedBlockingQueue<SmsLog>(100);
				servletContext.setAttribute("taskqueue_Indentify", queue);
			}
			

		}
	}
	

	public static void addDataToTaskQueue(SmsLog smsLog) throws Exception{
		
		queue.put(smsLog);
	}
	private void saveLog()throws Exception
	{
		try{
			this.smsLog = LogProductDelegate.queue.take();
			if(smsLog!= null)
			{
				String status = this.sendSMS(this.smsLog.getName(), this.smsLog.getMobile(), this.smsLog.getSmsInfo());
				//System.out.println("status = "+status);
				if("1000".equals(status))
				{
				//	this.addressBookService.saveSmsLog(this.smsLog);
				}
			}
			
			
		}
		catch (Exception e) {
			log.error(" saveLog(smsLog) is error = "+e.getMessage());
		}

		
	}

	@Override
	public void run() {
		
		try {
			while(true)
			{
				saveLog();
			}
		} catch (Exception e) {
			log.error(" LogProductDelegate run() is error = "+e.getMessage());
		}
		
		
	}
	
	public String sendSMS(String name,String mobile,String content)
	{

		
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sid="";
		 sid += sdf.format(date);
		int   randomNum   =   (int)(Math.random()   *   10000)+10000;
		String rm = String.valueOf(randomNum);	
		sid+=rm.substring(0,4);
		HttpURLConnection http = null;
		String totalstring = "";
		String currentLine;
		String link="";
		try {
			
			
			link = "http://sms.easou.com/smsend?user=easou&passwd=easou54321&mobile="+mobile+"&content="+URLEncoder.encode(content,"UTF-8")+"&sid="+sid;
			URL url = new URL(link);
			http = (HttpURLConnection) url.openConnection();
			http.connect();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
			while ((currentLine = l_reader.readLine()) != null) {
				totalstring += currentLine;
			}
			l_reader.close();
			http.disconnect();
			http = null;
			
			
		} catch (Exception ef) {
			
		}
		
		return totalstring;
	}
	
	

}
