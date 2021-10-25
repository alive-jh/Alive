package com.wechat.jfinal.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.wechat.jfinal.common.Rt;
import com.wechat.jfinal.common.utils.web.Result;

import net.sf.json.JSONObject;

/**
 * 全局Action拦截器
 */
public class ActionInterceptor implements Interceptor {
    public void intercept(Invocation inv) {
        String uri =inv.getActionKey();
        if(uri.startsWith("/v2/") || uri.startsWith("/ajax/")){
            apiActionInterceptor(inv);
        }else {
            pageActionInterceptor(inv);
        }
    }

    /**
     * 统一处理Api类请求
     * @param inv
     */
    private void apiActionInterceptor(Invocation inv){
        Controller controller = inv.getController();
        try{
            inv.invoke();
        }catch (Exception e){
            e.printStackTrace();
            
            if(e.getCause() instanceof NumberFormatException){
            	JSONObject json = new JSONObject();
            	json.put("code", 20301);
            	json.put("msg", "参数转换异常,NumberFormatException");
            	controller.renderJson(json);
        	}else{
        		controller.renderJson(Rt.sysError());
        	}
        }
    }

    /**
     * 统一处理页面类请求
     * @param inv
     */
    private void pageActionInterceptor(Invocation inv){
        Controller controller = inv.getController();
        try{
            inv.invoke();
        }catch (Exception e){
        	e.printStackTrace();
            controller.render("/sys/404.html");
        }
    }

}