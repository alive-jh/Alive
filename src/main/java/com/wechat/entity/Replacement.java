package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：replacement
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "replacement")
public class Replacement {

	@Id
	@GeneratedValue(generator ="replacementtableGenerator")       
    @GenericGenerator(name ="replacementtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 旧EpalId
	@Column(name = "last_epal_id")
	private String lastEpalId;
	
	
	// 新EpalId
	@Column(name = "current_epal_id")
	private String currentEpalId;
	
	// 备注
	@Column(name = "remark")
	private String remark;
	
	
	// 备注
	@Column(name = "create_time")
	private String createTime;
	

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getLastEpalId(){
		return this.lastEpalId;
	}
	public void setLastEpalId(String lastEpalId){
		this.lastEpalId =lastEpalId;
	}

	public String getCurrentEpalId(){
		return this.currentEpalId;
	}
	public void setCurrentEpalId(String currentEpalId){
		this.currentEpalId =currentEpalId;
	}
	
	public String getRemark(){
		return this.remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}	
	
}
