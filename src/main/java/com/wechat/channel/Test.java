package com.wechat.channel;


public class Test {
	
	public static void main( String[] args ) throws InterruptedException{
		
		ChannelLeader leader = new ChannelLeader();
		
		
		for (int i = 0; i < 10; i++) {
			leader.task( new TestEvent("aaa" + i) );
//			registerr.addEvent( new EventListener() {
//			
//				public void notifyEvent(){
//					//System.out.println( "介个是批量提交  ");
//				}
//
//			} );
		}
		//批量提交
//		registerr.notifyEvent();
			
		
	}
	
	public static void testList()throws Exception
	{
		for (int i = 0; i < 1000; i++) {
			//System.out.println( "save entity = "+i);
		}
	}

}
