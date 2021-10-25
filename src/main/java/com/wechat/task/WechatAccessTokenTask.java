package com.wechat.task;

import com.wechat.util.Keys;
import com.wechat.util.WeChatUtil;

public class WechatAccessTokenTask implements Runnable {

	@Override
	public void run() {

		try {

			while (true) {
				
				WeChatUtil.updateAccessToKen(Keys.QYJY_APP_ID, Keys.QYJY_APP_SECRET);
				
				Thread.sleep(7000 * 1000);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
