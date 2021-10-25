package com.wechat.channel;

import java.util.Comparator;

public class VectorComparator implements Comparator< ChannelWorker > {

	
	@Override
	public int compare( ChannelWorker o1 , ChannelWorker o2 ){
		
		return  o1.getIndex() > o2.getIndex() ? 1 : -1;
	}

}
