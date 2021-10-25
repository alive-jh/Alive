package com.wechat.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class TAOBAOSMS {

    private static final String product = "Dysmsapi";// 短信API产品名称
    private static final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名

    public static void sendCode(String code, String mobile) throws Exception {

    }

    public static void sendVoiceCode(String code, String mobile) throws Exception {


    }

    public static void sendPwdCode(String code, String mobile) throws Exception {


    }

    /**
     * 发送课程邀请码短信
     *
     * @return
     */
    public static void sendClassInvite(String code, String endTime, String mobile)  {


    }

    public static void main(String[] args) {

    }

    public static void sendQyRegCode(String code, String tel)  {


    }


    public static void sendSnpPwdCode(String code, String mobile) throws Exception {


    }
}
