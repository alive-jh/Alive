package com.wechat.jfinal;

import com.jfinal.handler.Handler;
import com.wechat.jfinal.common.Auth;
import com.wechat.jfinal.common.utils.xx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class SkipOldUrlHandler extends Handler {


    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {

        this.next.handle(target, request, response, isHandled);

       /* //获得环境参数
    	String environment = System.getProperty("spring.profiles.active");
        if (environment == null || environment.equals("development")||request.getRequestURI().indexOf("getKnowledgeList")>-1) {
            this.next.handle(target, request, response, isHandled);
        } else {
            if (target.startsWith("/v2")) {
                //历史遗留接口跳过验证
                List<String> oldApi = new ArrayList<>();
                oldApi.add("/v2/icourse/getStdSchedule");
                oldApi.add("/v2/icourse/isBasicStd");
                oldApi.add("/v2/user/register");
                oldApi.add("/v2/classRoom/ClassRoomHandle/showProducts");//用户注册小程序需要用到，所以不用接口签名认证
                oldApi.add("/v2/miniapp");
                oldApi.add("/v2/video/vote");
                oldApi.add("/v2/video/randomVideos");
                oldApi.add("/v2/video/voteHistory");
                oldApi.add("/v2/device/deviceHandle/sendCMDMessage");
                oldApi.add("/v2/classproduct/payResult");
                oldApi.add("/v2/oauth2/login");
                oldApi.add("/v2/lessonProduct/captcha");
                oldApi.add("/v2/qyExhibition/index");
                oldApi.add("/v2/qyoauth2/login");
                oldApi.add("/v2/lessonProduct/payResult");
                oldApi.add("/v2/well/record/upload");
                oldApi.add("/v2/lesson/lessonReplyShare");

                if (target.startsWith("/v2/qy/")) {
                    this.next.handle(target, request, response, isHandled);
                }else if(target.startsWith("/v2/bookShop/")){
                	this.next.handle(target, request, response, isHandled);
                } else if (oldApi.contains(target)) {
                    this.next.handle(target, request, response, isHandled);
                } else {
                    //获得校验参数
                    String method = request.getMethod();
                    String sign = "";
                    String timeStamp = "";
                    if (method.equals("GET")) {
                        sign = request.getParameter("_sign");
                        timeStamp = request.getParameter("_time");
                    } else if (method.equals("POST")) {
                        Map<String, String> parameter = new HashMap<>();
                        Enumeration enu = request.getParameterNames();
                        while (enu.hasMoreElements()) {
                            String paraName = (String) enu.nextElement();
                            parameter.put(paraName, request.getParameter(paraName));
                        }
                        sign = parameter.get("_sign");
                        timeStamp = parameter.get("_time");
                    }
                    if (!xx.isOneEmpty(target, sign, timeStamp) && Auth.apiAuth(target, sign, timeStamp)) {
                        this.next.handle(target, request, response, isHandled);
                    } else {
                        try {
                            response.sendError(403);
                            //不再让tomcat或者jetty处理该请求
                            isHandled[0] = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
//            } else if (target.startsWith("/h5") || target.startsWith("/ajax")) {
//                this.next.handle(target, request, response, isHandled);
            } else {
                this.next.handle(target, request, response, isHandled);
            }
        }*/

    }
}
