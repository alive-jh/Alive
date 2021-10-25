package com.wechat.channel.task;

import com.wechat.channel.events.Event;
import com.wechat.channel.scheduler.CallBack;
import org.apache.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskDispatcher extends Thread implements Task {

	private Logger logger = Logger.getLogger( TaskDispatcher.class );

	private LinkedBlockingQueue< Event > schedulerTask;

	private String poolName;

	private CallBack callback;

	private Integer schedulerTimer;

	private boolean running = false;

	public CallBack getCallback(){
		return callback;
	}

	public void setCallback( CallBack callback ){
		this.callback = callback;
	}

	/**
	 * 设置运行状态，如果是true，则唤醒任务调度器
	 * 
	 * @param running
	 */
	public synchronized void setRunning( boolean running ){
		this.running = running;
		if (running)
			this.notify();
	}

	public boolean isRunning(){
		return running;
	}

	/**
	 * 初始化任务调度器
	 * 
	 * @param schedulerTask
	 *            缓冲队列，存放任务
	 * @param name
	 *            缓冲池的名称
	 * @param channelProperties
	 *            加载了channel.properties，从中得到频道缓冲调整时间
	 */
	public TaskDispatcher( LinkedBlockingQueue< Event > schedulerTask , String name , Integer schedulerTimer ) {
		this.schedulerTask = schedulerTask;
		this.poolName = name;
		this.schedulerTimer = schedulerTimer;
	}

	// channelLeader
	@Override
	public synchronized void run(){
		logger.info( "[Pool " + this.poolName + "] TaskDispatcher is ready ! config = { schedulerTimer : " + this.schedulerTimer + " }" );
		while (true) {
			try {// 第一次进入这个run方法时schedulerTask为空
				if (!schedulerTask.isEmpty() && callback != null) {
					callback.execute( schedulerTask );// 直接进入channelLeader
				} else {
					logger.info( "[Pool " + this.poolName + "] TaskDispatcher scheduler wait !" );
					this.setRunning( false );
					this.wait();
				}
				Thread.sleep( this.schedulerTimer );
			} catch (InterruptedException e) {
				logger.error( "[Pool " + this.poolName + "] TaskDispatcher pushData error !" , e );
				this.interrupt();
			}
		}
	}
}
