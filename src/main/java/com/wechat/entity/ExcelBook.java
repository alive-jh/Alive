package com.wechat.entity;

public class ExcelBook {
	
	private String cateId;		//书籍ID
	private String barCode;		//书籍库存编码
	private String name;		//书籍名称
	private String message;		//书籍导入消息
	private Integer status;		//导入状态,0成功,1失败
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	
	

}
