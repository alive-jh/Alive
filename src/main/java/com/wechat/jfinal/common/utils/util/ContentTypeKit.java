package com.wechat.jfinal.common.utils.util;

import java.util.HashMap;

public class ContentTypeKit {

    static HashMap<String,String> map = new HashMap<>();
    static{
        map.put("jpeg","image/jpeg");
        map.put("jpg","image/jpeg");
        map.put("jpe","image/jpeg");
        map.put("png","image/png");
        map.put("zip","application/zip");
    }

    public static String getMime(String suffix){
        if(map.get(suffix) != null)
            return map.get(suffix);
        else{
            //System.out.print("————————————————————暂未收录有"+suffix+"后缀对应的MIME，请到ContentTypeKit.java中补充");
            return null;
        }
    }

}

