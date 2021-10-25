package com.wechat.jfinal.common;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
@Deprecated
public class StandardRsp {

//    @JsonIgnore
    private Map<Object,Object> context;

    public StandardRsp(){
        context = new HashMap<>();
        context.put("code",500);
    }

    public StandardRsp setCode(int code){
        context.put("code",code);
        return this;
    }

    public StandardRsp setMsg(String msg){
        context.put("msg",msg);
        return this;
    }

    public StandardRsp setData(Object o){
        context.put("data",o);
        return this;
    }


    public Map<Object, Object> getContext() {
        return context;
    }

    public StandardRsp setContext(Map<Object, Object> date) {
        this.context = date;
        return this;
    }
}
