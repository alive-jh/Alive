package com.wechat.util;

import java.util.Map;

//用于controller接收map对象
//将form表单提交的数据封装成map对象
public class MapForm {
	private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
