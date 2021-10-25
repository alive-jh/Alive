package com.wechat.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateUitl {

	static SessionFactory sessionFactory;
	String springXmlPath = "springmvc-servlet.xml";
	
	public HibernateUitl(){
		sessionFactory = (SessionFactory) new ClassPathXmlApplicationContext(springXmlPath).getBean("sessionFactory"); 
	}
	
	public static Session getSession(){
		return sessionFactory.openSession();
	}
	
	
}
