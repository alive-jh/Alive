package com.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.i18n.Res;
import com.jfinal.kit.Ret;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.util.ArrayUtils;
import com.wechat.util.RequestUtils;
import com.wechat.util.StringUtils;

public class ParaValidateInterceptor implements Interceptor {

	public static final int DEFAULT_ERROR_CODE = 99;

	@Override
	public void intercept(Invocation inv) {

		EmptyParaValidate emptyParaValidate = inv.getMethod().getAnnotation(EmptyParaValidate.class);
		if (emptyParaValidate == null) {
			inv.invoke();
			return;
		}

		String[] paraKeys = emptyParaValidate.params();
		if (ArrayUtils.isNullOrEmpty(paraKeys)) {
			inv.invoke();
			return;
		}

		for (String param : paraKeys) {
			String value = inv.getController().getPara(param);
			if (value == null || value.trim().length() == 0) {
				renderError(inv, param, emptyParaValidate.errorRedirect());
				return;
			}
		}

		inv.invoke();
	}

	private void renderError(Invocation inv, String param, String errorRedirect) {
		
		if (StringUtils.isNotBlank(errorRedirect)) {
			inv.getController().redirect(errorRedirect);
			return;
		}
		
		//inv.getController().renderJson(Ret.fail("msg", "数据不能为空").set("errorCode", DEFAULT_ERROR_CODE).set("field", param));
		
		inv.getController().renderJson(Result.result(203));

	}

}
