package com.wechat.util;

import com.wechat.entity.AccessToken;
import com.wechat.entity.Account;
import com.wechat.service.AccountService;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;

import java.util.List;



/**
 * 定时获取微信access_token的线程
 * 
 * @author linzj
 * @date 2013-05-02
 */

public class TokenThread implements Runnable {

	// 第三方用户唯一凭证
	public static String appid = "";
	// 第三方用户唯一凭证密钥
	public static String appsecret = "";
	
	private List accountList = null;
	private ApplicationContext appContext; 

	
	public static AccessToken accessToken = null;

	@Override
	public void run()  {
		
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
//        ServletContext servletContext = webApplicationContext.getServletContext();  
//        appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext); 
//		AccountService accountService = (AccountService) appContext.getBean("accountService");
		try {
			AccountService accountService = (AccountService)BeanUtil.getBeanByName("accountService");
		} catch (Exception e2) {
			
			e2.printStackTrace();
		}
		//获取不了accountService
		try {
			
			Account account = new Account();
			
			account.setAppId(Keys.APP_ID);
			account.setAppSecret(Keys.APP_SECRET);
			account.setName("中国好电工");
			AccessToken accessToken =  new AccessToken();
			int count = 0;
			while (true) {
										
				
				accessToken = WeChatUtil.createAccessToKen(account.getAppId(), account.getAppSecret());
				if(!"".equals(accessToken.getAccessToken()) && accessToken.getAccessToken()!= null )
				{
					String ticket="";
					String url2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";//请求URL
					String data2 = "access_token="+accessToken.getAccessToken()+"&type=jsapi";//设置参数
					JSONObject jsonObject2  = WeChatUtil.httpRequest(url2, data2);
					if("ok".equals(jsonObject2.getString("errmsg"))){
						ticket=jsonObject2.getString("ticket");
					}
					WeChatUtil.updateAccessToKen(account.getAppId(), account.getAppSecret());
					////System.out.println("公众账号: "+account.getName()+" 获取access_token成功，有效时长秒 token:"+ accessToken.getExpiresIn());
				}
				else
				{
					//System.out.println("TokenThread 获取access_token失败!");
				}
			
			Thread.sleep(7200 * 1000);
			////System.out.println("重新获取access_token!");
						
			}	
				
			} catch (InterruptedException e) {
				
					try {
						Thread.sleep(50 * 1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				//System.out.println("error"+ e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
	}
}
