package com.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.util.SecurityUtil;

public class AccessTokenInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {

		Controller controller = inv.getController();

		String token = controller.getPara("token");

		Integer memberId = controller.getParaToInt("memberId");

		if (xx.isOneEmpty(token, memberId)) {
			Result.error(203, controller);
			return;
		}

		try {

			if (checkToken(token, memberId)) {
				inv.invoke();
			} else {
				Result.error(20501, "token验证失效,请重新登录", controller);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			Result.error(20502, "token验证异常", controller);
			return;
		}

	}

	public boolean checkToken(String token, Integer memberId) throws Exception {
		boolean result = false;

		Cache redis = Redis.use();

		String memberInfo = redis.get(token);

		redis.close(redis.getJedis());
		
		if (memberInfo != null && memberInfo.split("#")[0].equals(memberId.toString())) {
			
			result = true;
			
		} else {

		}

		return result;
	}

}
