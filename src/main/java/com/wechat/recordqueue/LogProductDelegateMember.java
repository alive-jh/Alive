package com.wechat.recordqueue;

import com.wechat.entity.Integral;
import com.wechat.entity.MemberAccount;
import com.wechat.service.IntegralService;
import com.wechat.service.MemberService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;





public class LogProductDelegateMember implements Runnable{
	
	
	private ApplicationContext appContext; 
	private String memberIds;
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private static BlockingQueue<String> queue ;
	private ServletContext servletContext;
	private IntegralService integralService;
	private MemberService memberService;
	
	public LogProductDelegateMember(ServletContext servletContext){
		
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
        this.servletContext = servletContext;
        appContext = WebApplicationContextUtils.getWebApplicationContext(this.servletContext); 
        this.integralService = (IntegralService) appContext.getBean("integralService");
        this.memberService =  (MemberService) appContext.getBean("memberService");
		this.servletContext = servletContext;
		if(servletContext != null){
			if(queue!= null)
			{
				queue = (LinkedBlockingQueue<String>) servletContext.getAttribute("taskqueue_Indentify");
			}
			else
			{
				queue = new LinkedBlockingQueue<String>(100);
				servletContext.setAttribute("taskqueue_Indentify", queue);
			}
			

		}
	}
	

	public static void addDataToTaskQueue(String memberId) throws Exception{
		
		//System.out.println("-------add edtity-------");
		String memberInfoId = memberId;
		queue.put(memberInfoId);
	}
	private void memberSign()throws Exception
	{
		try{
			this.memberIds = LogProductDelegateMember.queue.take();
			MemberAccount memberAccount = this.memberService.searchMemberAccountByMemberId(memberIds);
			List list = this.integralService.getIntegralByMemberId(memberIds,memberAccount.getType().toString());
			if(list.size() ==0)
			{
				Integral integral = new Integral();
				integral.setCreateDate(new Date());
				integral.setFraction(new Integer(10));
				integral.setMemberId(new Integer(memberIds));
				integral.setStatus(new Integer(0));//积分状态入账
				integral.setTypeId(new Integer(2));//用户签到
				this.integralService.saveIntegral(integral);
			}
			

			//System.out.println("-------end-------");
			
			
		}
		catch (Exception e) {
			log.error(" memberSign(memberId) is error = "+e.getMessage());
		}

		
	}

	@Override
	public void run() {
		
		try {
			while(true)
			{
				memberSign();
			}
		} catch (Exception e) {
			log.error(" LogProductDelegate run() is error = "+e.getMessage());
		}
		
		
	}
	
	

}
