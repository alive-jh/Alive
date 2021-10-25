package com.wechat.channel;

import com.wechat.channel.events.Event;
import com.wechat.channel.scheduler.CallBack;
import com.wechat.channel.scheduler.Scheduler;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 池（请求池|执行池）的管理者
 * 
 * @author Vixlen.Lee
 * 
 */
public class ChannelLeader extends Scheduler {

//	public Logger logger = Logger.getLogger(ChannelLeader.class);
	private static final Logger logger = Logger.getLogger(ChannelLeader.class);
//	private Logger logger = Logger.getLogger( ChannelLeader.class );

	private VectorComparator vectorComparator = new VectorComparator();
	private Vector< ChannelWorker > workers = new Vector< ChannelWorker >();

	private Integer maxWorkers = 0;

	private Long workertimer = 0L;

	private String channelName;

	private Integer taskSize = 0;

	public ChannelLeader() {
		this( "leader channel" , 500 );
	}

	/**
	 * 根据配置文件初始化任务池，任务进入等待状态
	 * 
	 * @param name
	 *            crawl或context
//	 * @param propertySet
	 *            加载有配置文件的对象
	 */
	public ChannelLeader( String name , Integer schedulerTimer ) {
		this( 10 , 500L , name , schedulerTimer );
	}

	/**
	 * 初始化任务池，任务进入等待状态
	 * 
	 * @param maxWorkers
	 *            最大工人数
	 * @param workertimer
	 *            任务间隔时间
//	 * @param name
	 *            池的名称
//	 * @param propertySet
	 *            加载有配置文件
	 */
	public ChannelLeader( Integer maxWorkers , Long workertimer , String channelName , Integer schedulerTimer ) {
		super( channelName , schedulerTimer );
		this.channelName = channelName;
		this.maxWorkers = maxWorkers;
		this.workertimer = workertimer;
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker worker = new ChannelWorker( channelName , false );
			workers.add( worker );
			worker.start();
		}
		logger.info( "[Pool " + channelName + "] Workers all ready ! config = { MaxWorkers : " + this.maxWorkers + " , workertimer = " + this.workertimer + " }" );
	}

	/**
	 * 将任务加到请求池，唤醒调度器（分配者）
	 * 
	 * @param argument
	 *            将要加到缓冲队列的任务
	 */
	public void task( Event argument ){
		super.task( argument , new CallBack() {
			@Override
			public void execute( LinkedBlockingQueue< Event > task ){

				// worker checker
				workersChecker();

				if ((taskSize = task.size()) <= maxWorkers && maxWorkers != 1)
					Collections.sort( workers , vectorComparator );

				for (int i = 0; i < taskSize; i++) {
					ChannelWorker currentWorker = workers.elementAt( i % maxWorkers );// 将任务平均的分给工人
					currentWorker.pushTask().offer( task.poll() );// 从任务队列中取出头并把它加入正在执行队列的队尾
					if (!currentWorker.isRunning()) {
						currentWorker.setRunning( true );
						currentWorker.setTimer( workertimer );
					}
				}
			}
		} );

	}

	public void workersChecker(){
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker currentWorker = workers.elementAt( i );
			if (!currentWorker.isAlive()) {
				ConcurrentLinkedQueue< Event > eventTask = currentWorker.getTask();
				currentWorker = new ChannelWorker( currentWorker.getName() , currentWorker.isSingle() );
				if (currentWorker.getTask().addAll( eventTask )) {
					currentWorker.setAddSize( eventTask.size() );
					workers.set( i , currentWorker );

					eventTask.clear();
					eventTask = null;

					currentWorker.start();
				} else {
					logger.error( "[Pool " + channelName + "] workers checker is addAll Task error !" );
				}
			}
		}
	}

	public void notifyEvents(){
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker currentWorker = workers.elementAt( i );// 将任务平均的分给工人
			if (!currentWorker.isRunning()) {
				currentWorker.setRunning( true );
			}
		}
	}

	public void shutdown(){
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker currentWorker = workers.elementAt( i );
			currentWorker.setShutdown( true );
			currentWorker.setRunning( false );
		}

	}

	public void clear(){
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker currentWorker = workers.elementAt( i );
			currentWorker.getTask().clear();
			currentWorker.sizeClear();
			currentWorker.setRunning( false );
		}
	}

	public int getMaxWorkers(){
		return maxWorkers;
	}

	/**
	 * 得到装有工人的向量
	 * 
	 * @return
	 */
	public Vector< ChannelWorker > getWorkers(){
		return workers;
	}

	/**
	 * 得到池的名称
	 * 
	 * @return
	 */
	public String getChannelName(){
		return channelName;
	}

	public void stateList( List< StateFace > stateList ){
		if (stateList != null)
			for (int i = 0; i < maxWorkers; i++) {
				ChannelState state = new ChannelState();
				ChannelWorker currentWorker = workers.elementAt( i );
				state.setName( this.channelName );
				state.setThreadName( currentWorker.getName() );
				state.setThreadState( currentWorker.getState().toString() );
				state.setTask( currentWorker.getSize() );
				stateList.add( state );
			}
	}

	public boolean isTaskPoolEmpty(){

		boolean isEmpty = true;
		for (int i = 0; i < maxWorkers; i++) {
			ChannelWorker currentWorker = workers.elementAt( i );
			if (currentWorker.getTask().isEmpty()) {
				isEmpty = true;
				continue;
			} else {
				isEmpty = false;
				break;
			}
		}
		return isEmpty;
	}

}
