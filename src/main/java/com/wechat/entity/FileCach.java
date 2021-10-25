package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "filecach")
public class FileCach {
	@Id
	@GeneratedValue(generator ="filecachtableGenerator")       
    @GenericGenerator(name ="filecachtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "epal_id")
	private String epalId;
	
	@Column(name = "device_no")
	private String deviceNo;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "cach_data")
	private String cachData;
	
	@Column(name = "insertdate")
	private String insertdate;
	
	@Column(name = "updatedate")
	private String updatedate;

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getEpalId(){
		return this.epalId;
	}
	public void setEpalId(String epalId){
		this.epalId = epalId;
	}
	
	public String getDeviceNo(){
		return this.deviceNo;
	}
	public void setDeviceNo(String deviceNo){
		this.deviceNo = deviceNo;
	}
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path = path;
	}
	public String getCachData(){
		return this.cachData;
	}
	public void setCachData(String cachData){
		this.cachData = cachData;
	}
	
	public String getInsertdate(){
		return this.insertdate;
	}
	public void setInsertdate(String insertdate){
		this.insertdate = insertdate;
	}
	
	public String getUpdatedate(){
		return this.updatedate;
	}
	public void setUpdatedate(String updatedate){
		this.updatedate = updatedate;
	}
}
