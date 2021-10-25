package com.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.common.utils.xx;

public class FandouLibraryAccessInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		Controller controller = inv.getController();

		String access_token = controller.getPara("access_token");

		if (xx.isOneEmpty(access_token)) {
			controller.renderJson("{\"code\":203,\"msg\":\"参数无效\"}");
			return;
		}

		Cache redis = Redis.use();

		String redisToken = redis.get(access_token);
		
		//System.out.println("here----"+redisToken);

		if (redisToken == null) {
			
			controller.renderJson("{\"code\":209,\"msg\":\"未登录，请重新登录\"}");
			return;
			
		} else {
			controller.getRequest().setAttribute("redisToken", redisToken);
			inv.invoke();
		}
		
	}
	

}
