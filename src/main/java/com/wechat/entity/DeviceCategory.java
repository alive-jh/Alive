package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：device_category
 * 数据说明：设备分类管理
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "device_category")
public class DeviceCategory {

	@Id
	@GeneratedValue(generator ="DeviceCategorytableGenerator")       
    @GenericGenerator(name ="DeviceCategorytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 答案
	@Column(name = "name")
	private String name;
	
	
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	

}
