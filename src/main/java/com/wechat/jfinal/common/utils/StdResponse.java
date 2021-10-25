package com.wechat.jfinal.common.utils;

import java.util.ArrayList;
import java.util.List;

public class StdResponse {

	private String msg;
	
	private Integer code;
	
	private List<Object> data;
	
	//构造函数
	public StdResponse(){
		this.data = new ArrayList<>();
	}	
	
	//设置msg时，连带设置code
	public StdResponse setMsg(StatusCode sc) {
		this.code = sc.getVal();
		this.msg = sc.toString();
		return this;
	}

	public StdResponse setMsg(String msg){
		this.msg = msg;
		return this;
	}
	
	//增加返回值
	public void addData(Object o){
		if(o == null){
			this.setMsg(StatusCode.NO_CONTENT);
		}else{			
			this.data.add(o);
			this.setMsg(StatusCode.OK);
		}
	}

	public String getMsg() {
		return msg;
	}

	public Integer getCode() {
		return code;
	}

	public StdResponse setCode(Integer code) {
		this.code = code;
		return this;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}



	
}

