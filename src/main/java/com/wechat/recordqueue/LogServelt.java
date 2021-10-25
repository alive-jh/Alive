package com.wechat.recordqueue;

import com.wechat.task.WechatAccessTokenTask;
import com.wechat.util.TokenThread;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class LogServelt extends HttpServlet {
	private static Logger log = Logger.getLogger(LogServelt.class);
	private static ServletContext servletContext;
	
	public void init() throws ServletException {
		
		
		super.init();
//		Runnable task = new LogProductDelegate(getServletContext());
//		Thread t = new Thread(task);
//		t.setDaemon(true);
//		t.start();
		Runnable task = new LogProductDelegateMember(getServletContext());//会员签到队列
		Thread t = new Thread(task);
//		t.setDaemon(true);
//		t.start();
		task = new MallProductLogDelegate(getServletContext());//商品访问日志队列
		t = new Thread(task);
		t.setDaemon(true);
		t.start();
//		task = new ElectrismDelegate(getServletContext());//电工流水账单队列
//		t = new Thread(task);
//		t.setDaemon(true);
//		t.start();
		
		new Thread(new TokenThread()).start();
		new Thread(new WechatAccessTokenTask()).start();
		
	}

	
	public void init(ServletConfig arg0) throws ServletException {
		
		super.init(arg0);
	}
}
