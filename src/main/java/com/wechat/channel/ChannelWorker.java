package com.wechat.channel;


import com.wechat.channel.events.Event;
import org.apache.log4j.Logger;

/**
 * 工人执行任务
 * 
 * @author Vixlen.Lee
 * 
 */
public class ChannelWorker extends ChannelGroup {

	private Logger logger = Logger.getLogger( ChannelWorker.class );

	/**
	 * 
	 * @param name
	 *            池的名称
	 * @param single
	 *            是否为单个工人循环执行
	 */
	public ChannelWorker( String name , boolean single ) {
		super( name , single );
	}

	/**
	 * 查看池的状况，正常就执行任务，最初为等待状态
	 */
	@Override
	public synchronized void run(){
		while (true) {

			if (isShutdown()) {
				logger.debug( "[Pool " + this.getPoolName() + "]" + this.getName() + " stop !" );
				break;
			}
			if (!isRunning()) {
				try {
					logger.debug( "[Pool " + this.getPoolName() + "]" + this.getName() + " wait !" );
					this.wait();
				} catch (InterruptedException e) {
					logger.error( "[Pool " + this.getPoolName() + "]" + this.getName() + " wait error !" , e );
					this.interrupt();
				}

			} else {// 调用channelLeader.task(..)中匿名内部类的execute函数时
				logger.debug( "[Pool " + this.getPoolName() + "]" + this.getName() + " notify!" );

				while (!this.getTask().isEmpty()) {
					this.sizeLower();

					try {
						Thread.sleep( this.getTimer() );
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}

					Event object = this.getTask().poll();// 检索并移除此队列的头，如果此队列为空，则返回null

					if (object == null) {
						continue;
					}
					object.notifyEvent();// very important
					// 由此进入HttpCrawl或LocalFile

					if (!this.isSingle()) {
						break;
					}
					

				}
				if(this.getTask().isEmpty())
				this.setRunning( false );
			}
		}
	}
}
