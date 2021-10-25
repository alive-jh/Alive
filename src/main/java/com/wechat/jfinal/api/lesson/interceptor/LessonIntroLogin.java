package com.wechat.jfinal.api.lesson.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

public class LessonIntroLogin implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		
		inv.invoke();
		
	}

}
