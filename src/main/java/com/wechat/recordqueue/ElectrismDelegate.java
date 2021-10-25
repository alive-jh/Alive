package com.wechat.recordqueue;

import com.wechat.entity.ElectrismOrderPayment;
import com.wechat.service.ElectrismService;
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



public class ElectrismDelegate implements Runnable{
	
	
	private ApplicationContext appContext; 

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private static BlockingQueue<ElectrismOrderPayment> queue ;
	private ServletContext servletContext;
	private ElectrismService electrismService;
	private ElectrismOrderPayment electrismOrderPayment;

	
	public ElectrismDelegate(ServletContext servletContext){
		
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        this.servletContext = servletContext;
        appContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext); 
        this.electrismService = (ElectrismService) appContext.getBean("electrismService");
		this.servletContext = servletContext;
		if(servletContext != null){
			if(queue!= null)
			{
				queue = (LinkedBlockingQueue<ElectrismOrderPayment>) servletContext.getAttribute("taskqueue_Indentify");
				
			}
			else
			{
				queue = new LinkedBlockingQueue<ElectrismOrderPayment>(100);
				servletContext.setAttribute("taskqueue_Indentify", queue);
			}
		}
	}
	

	public static void addDataToTaskQueue(ElectrismOrderPayment electrismOrderPayment) throws Exception{
		
		//System.out.println("-------add edtity-------");
		
		queue.put(electrismOrderPayment);
	}
	private void saveMallProductLog()throws Exception
	{
		try{
			this.electrismOrderPayment = ElectrismDelegate.queue.take();
			//DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			this.electrismService.saveElectrismOrderPayment(electrismOrderPayment);
			
			
			//System.out.println("-------end-------");
			
			
		}
		catch (Exception e) {
			log.error(" saveElectrismOrderPayment is error = "+e.getMessage());
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
			log.error(" saveElectrismOrderPayment run() is error = "+e.getMessage());
		}
		
		
	}
	
	public static void main(String[] args) {
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time=format.format(date);
		//System.out.println("time = "+time);
	}

}
