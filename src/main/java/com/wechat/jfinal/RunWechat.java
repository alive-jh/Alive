package com.wechat.jfinal;

import com.jfinal.core.JFinal;

public class RunWechat {
    public  static void main(String[] args){
        JFinal.start("src/main/webapp",8080,"/");
    }
}
