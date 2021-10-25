package com.wechat.channel;

import java.io.Serializable;

public class ChannelState implements Serializable, StateFace {
	
	private static final long serialVersionUID = 1L;

	private String name;
	private String threadName;
	private String threadState;
	private Integer task;

	@Override
	public String getName(){
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}

	@Override
	public String getThreadState(){
		return threadState;
	}

	public void setThreadState( String threadState ){
		this.threadState = threadState;
	}

	@Override
	public Integer getTask(){
		return task;
	}

	public void setTask( Integer task ){
		this.task = task;
	}

	@Override
	public String getThreadName(){
		return threadName;
	}

	public void setThreadName( String threadName ){
		this.threadName = threadName;
	}

}
