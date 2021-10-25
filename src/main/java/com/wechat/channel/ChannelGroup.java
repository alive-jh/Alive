package com.wechat.channel;


import com.wechat.channel.events.Event;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 多个channelWorker形成ChannelGroup
 * 
 * @author Vixlen.Lee
 * 
 */
public class ChannelGroup extends Thread {

	// private Logger logger = Logger.getLogger( Scheduler.class );
	// ConcurrentLinkedQueue是一个基于链接节点的无界线程安全队列，它采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部，当我们获取一个元素时，它会返回队列头部的元素

	private ConcurrentLinkedQueue< Event > task = new ConcurrentLinkedQueue< Event >();

	private boolean running = false;

	private boolean shutdown = false;

	private boolean single = false;

	private Long timer = 0L;

	private String name;

	private Long index = 0L;

	/**
	 * 
	 * @param name
	 *            池的名称
	 * @param single
	 *            是否为单个工人循环执行
	 */
	public ChannelGroup( String name , boolean single ) {
		this.name = name;
		this.single = single;
	}

	public Long getTimer(){
		return timer;
	}

	public void setTimer( Long timer ){
		this.timer = timer;
	}

	public boolean isShutdown(){
		return shutdown;
	}

	public synchronized void setShutdown( boolean shutdown ){
		this.shutdown = shutdown;
		if (this.shutdown)
			this.notify();
	}

	public boolean isRunning(){
		return running;
	}

	/**
	 * 设置是否运行，如是true则唤醒线程
	 * 
	 * @param flag
	 */
	public synchronized void setRunning( boolean flag ){
		this.running = flag;
		if (this.running)
			this.notify();
	}

	/**
	 * 得到装有任务的队列
	 * 
	 * @return 队列
	 */
	protected ConcurrentLinkedQueue< Event > getTask(){
		return task;
	}

	protected ConcurrentLinkedQueue< Event > pushTask(){
		if (index >= Integer.MAX_VALUE)
			index = 0L;
		index++;
		size++;
		return task;
	}

	public Long getIndex(){
		return index;
	}

	/**
	 * 队列的长度
	 * 
	 * @return
	 */
	private int size = 0;

	public int getSize(){
		return size;
	}

	public void setAddSize( int lenght ){
		size += lenght;
	}

	public int sizeClear(){
		return size = 0;
	}

	public void sizeLower(){
		if (size > 0)
			size--;
		else
			this.sizeClear();
	}

	/**
	 * 得到池名
	 * 
	 * @return
	 */
	public String getPoolName(){
		return name;
	}

	/**
	 * 是否为单链模式
	 * 
	 * @return
	 */
	public boolean isSingle(){
		return single;
	}

}
