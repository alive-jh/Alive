package com.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class MiniappInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		//System.out.println("经过了这里");
		inv.invoke();
	}
	
}
