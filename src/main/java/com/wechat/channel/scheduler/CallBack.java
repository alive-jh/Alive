package com.wechat.channel.scheduler;

import com.wechat.channel.events.Event;

import java.util.concurrent.LinkedBlockingQueue;

public interface CallBack {
	/**
	 * 执行队列中的任务
	 * @param task 装有任务的无界线程安全队列
	 */
	public void execute(LinkedBlockingQueue<Event> task);
}
