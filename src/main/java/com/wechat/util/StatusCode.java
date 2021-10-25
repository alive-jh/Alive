package com.wechat.util;


/**
 * 状态码（数据字典）
 * 
 * @author zlisten
 *
 */
public enum StatusCode {
	/**
	 *100-199 用于指定客户端应相应的某些动作。 
	 *200-299 用于表示请求成功。 
	 *300-399 用于已经移动的文件并且常被包含在定位头信息中指定新的地址信息。 
	 *400-499 用于指出客户端的错误。 
	 *500-599 用于支持服务器错误。
	 */
	
	/**
	 * 一切正常
	 */
	OK("OK", 200), 
	/**
	 * 无有效内容返回
	 */
	NO_CONTENT("NO_CONTENT", 204), 
	
	
	/**
	 * 未授权
	 */
	UNAUTHORIZED("UNAUTHORIZED", 401), 
	/**
	 * 参数校验失败
	 */
	PARM_FAILED("PARM_FAILED", 420),
	
	
	/**
	 * 服务器端报错
	 */
	SERVER_ERROE("SERVER_ERROE", 500); 

	private String name;
	private int val;

	private StatusCode(String name, int val) {
		this.name = name;
		this.val = val;
	}

	public int getVal() {
		return this.val;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.name;
	}

}
