package com.wechat.channel;

import com.wechat.channel.events.EventListener;

public class TestEvent implements EventListener {

	private String name;
	@Override
	public void notifyEvent() {
			
			//System.out.println( "介个是批量提交  " + name);
	}
	
	public TestEvent(String name)
	{
		this.name = name;
	}

}
