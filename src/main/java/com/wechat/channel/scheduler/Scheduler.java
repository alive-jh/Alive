package com.wechat.channel.scheduler;

import com.wechat.channel.events.Event;
import com.wechat.channel.task.TaskDispatcher;
import org.apache.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 调度器分配者
 * 
 * @author Vixlen.Lee
 * 
 */
public class Scheduler {
	private Logger logger = Logger.getLogger( Scheduler.class );

	private LinkedBlockingQueue< Event > schedulerTask = new LinkedBlockingQueue< Event >();// 并发队列，非阻塞队列

	private TaskDispatcher dispatcher;

	/**
	 * 初始化任务调度器并开始执行缓冲池中的任务
	 * 
	 * @param name
	 *            池的名称
	 * @param propertySet
	 *            加载的配置文件
	 */
	public Scheduler( String name , Integer schedulerTimer ) {
		dispatcher = new TaskDispatcher( schedulerTask , name , schedulerTimer );
		dispatcher.start();
	}

	/**
	 * 将任务加到任务缓冲池，唤醒调度器
	 * 
	 * @param argument
	 *            将要加到缓冲队列的任务
	 * @param callback
	 *            回调函数
	 */
	protected void task( Event argument , CallBack callback ){
		schedulerTask.add( argument );
		dispatcher.setCallback( callback );
		if (!dispatcher.isRunning()) {
			dispatcher.setRunning( true );
		}

		logger.debug( " >>. push argument : " + argument );
	}

	/**
	 * 得到任务的个数
	 * 
	 * @return
	 */
	public int getTask(){
		return schedulerTask.size();
	}

	/**
	 * 查看任务是否执行完毕
	 * 
	 * @return
	 */
	public boolean isSchPoolEmpty(){
		return schedulerTask.isEmpty();
	}

}
