package com.wechat.channel.events;

public interface Event {
	/**
	 * 处理文件，获取整个HTML或者过滤出所需信息
	 */
	public void notifyEvent();

}
