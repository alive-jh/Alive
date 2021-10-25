package com.wechat.channel.events;

import com.wechat.channel.ChannelLeader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EventRegister implements Event {
	private List< EventListener > listeners = new ArrayList< EventListener >();
	private ChannelLeader channel;
	private String name = "";

	public EventRegister( ChannelLeader channel ) {
		this.channel = channel;
	}

	public void addEvent( EventListener listener ){
		listeners.add( listener );
	}

	/**
	 * 
	 */
	@Override
	public void notifyEvent(){
		Iterator< EventListener > el = listeners.iterator();
		while (el.hasNext()) {
			channel.task( el.next() );
		}
		listeners.clear();
	}

	public void showInfo() {
		
		//System.out.println("name = "+this.name);
		
	}
}
