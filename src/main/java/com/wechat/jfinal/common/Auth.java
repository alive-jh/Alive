package com.wechat.jfinal.common;

import java.util.Scanner;

import com.wechat.jfinal.common.utils.EncryptUtil;

/**
 * 系统认证层
 */
public class Auth {

    //TODO 确认加密是否正确
    static final String password =  EncryptUtil.getMd5("fandou2017").toLowerCase();

    /**
     * password=fandou2017----固定
     * 时间戳到秒
     * sign = MD5[api/saveClassRoom@MD5[password]@timestamp]
     *
     * @param uri 例：/v2/res/category/getById
     * @param sign 客户端传来的密文
     * @param timeStamp 客户端传来的时间戳
     * @return
     */
    public static boolean apiAuth(String uri, String sign, String timeStamp) {
        //拼接
        String raw = uri + "@" + password + "@" + timeStamp;
        sign = sign.toUpperCase();
        String me = EncryptUtil.getMd5(raw);
       if(sign.equals( me))
           return true;
        return false;
    }
    
    public static void main(String[] args) {

    	Scanner scan = new Scanner(System.in);
    	String uri = scan.nextLine();
    	//String uri = "/v2/device/deviceHandle/deviceList";
    	String timeStamp = "1525842696825";
    	System.out.println("&_sign="+EncryptUtil.getMd5(uri + "@" + password + "@" + timeStamp)+"&_time="+timeStamp);
    	
	}


}
