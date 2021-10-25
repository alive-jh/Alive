package com.wechat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="book")
public class Book {
	@Id
	@Column(name="barcode")
	private String barCode;						//图书编码
	
	@Column(name="cate_id")
	private String cateId;						//图书ID
	
	@Column(name="isexist")
	private Integer isexist = new Integer(1);	//图书是否在库:1在库,0已出库
	
	@Column(name="belong")
	private Integer belong = new Integer(1);	//加盟店id,0公司总部 
	
	@Column(name="url")
	private String url;							//二维码路径
	
	@Column(name="codeinfo")
	private String codeInfo;					//二维码内容
	
	
	@Column(name="copy_times")
	private Integer copyTimes;					//二维码内容
	
	
	@Column(name="status")
	private Integer status;					//二维码内容
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCodeInfo() {
		return codeInfo;
	}
	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getCateId() {
		return cateId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public Integer getIsexist() {
		return isexist;
	}
	public void setIsexist(Integer isexist) {
		this.isexist = isexist;
	}
	public Integer getBelong() {
		return belong;
	}
	public void setBelong(Integer belong) {
		this.belong = belong;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getCopyTimes() {
		return copyTimes;
	}
	public void setCopyTimes(Integer copyTimes) {
		this.copyTimes = copyTimes;
	}

}
