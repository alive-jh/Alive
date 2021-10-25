package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：zhubo_alias
 * 数据说明：主播别名表
 * 
 * 
 * */

@Entity
@Table(name = "zhubo_alias")
public class ZhuboAlias {

	@Id
	@GeneratedValue(generator ="ZhuboAliastableGenerator")       
    @GenericGenerator(name ="ZhuboAliastableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 别名
	@Column(name = "aliasName")
	private String aliasName;
	
	// 主播ID
	@Column(name = "zhuboId")
	private Integer zhuboId;
	
	// 状态
	@Column(name = "status")
	private Integer status;

		
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getZhuboId(){
		return this.zhuboId;
	}
	public void setZhuboId(Integer zhuboId){
		this.zhuboId = zhuboId;
	}
	

	
	public String getAliasName(){
		return this.aliasName;
	}
	
	public void setAliasName(String aliasName){
		this.aliasName = aliasName;
	}
	

	
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	

}
