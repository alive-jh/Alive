package com.wechat.recordqueue;

import com.wechat.entity.MallProductLog;
import com.wechat.service.MallProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;





public class MallProductLogDelegate implements Runnable{
	
	
	private ApplicationContext appContext; 

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private static BlockingQueue<MallProductLog> queue ;
	private ServletContext servletContext;
	private MallProductService mallProductService;
	private MallProductLog mallProductLog ;

	
	public MallProductLogDelegate(ServletContext servletContext){
		
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        this.servletContext = servletContext;
        appContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext); 
        this.mallProductService = (MallProductService) appContext.getBean("mallProductService");
		this.servletContext = servletContext;
		if(servletContext != null){
			if(queue!= null)
			{
				queue = (LinkedBlockingQueue<MallProductLog>) servletContext.getAttribute("taskqueue_Indentify");
				
			}
			else
			{
				queue = new LinkedBlockingQueue<MallProductLog>(100);
				servletContext.setAttribute("taskqueue_Indentify", queue);
			}
			

		}
	}
	

	public static void addDataToTaskQueue(MallProductLog mallProductLog) throws Exception{
		
		//System.out.println("-------add edtity-------");
		
		queue.put(mallProductLog);
	}
	private void saveMallProductLog()throws Exception
	{
		try{
			this.mallProductLog = MallProductLogDelegate.queue.take();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			Integer count = this.mallProductService.getMallProductLogCount(mallProductLog.getMemberId().toString(),format.format(mallProductLog.getCreateDate()));
			if(count ==0)
			{
				this.mallProductService.saveMallProductLog(mallProductLog);
			}
			
			//System.out.println("-------end-------");
			
			
		}
		catch (Exception e) {
			log.error(" saveMallProductLog is error = "+e.getMessage());
		}

		
	}

	@Override
	public void run() {
		
		try {
			while(true)
			{
				saveMallProductLog();
			}
		} catch (Exception e) {
			log.error(" LogProductDelegate run() is error = "+e.getMessage());
		}
		
		
	}
	
	public static void main(String[] args) {
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=format.format(date);
		//System.out.println("time = "+time);
	}

}
