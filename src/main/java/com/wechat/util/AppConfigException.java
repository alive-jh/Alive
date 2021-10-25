package com.wechat.util;

public class AppConfigException extends Exception {
    public AppConfigException(String message){
        super(message);
    }
    public AppConfigException(String message,Throwable course){
        super(message,course);
    }
}
