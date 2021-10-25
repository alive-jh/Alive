package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：sound_alias
 * 数据说明：音频别名表
 * 
 * 
 * */

@Entity
@Table(name = "sound_alias")
public class SoundAlias {

	@Id
	@GeneratedValue(generator ="SoundAliastableGenerator")       
    @GenericGenerator(name ="SoundAliastableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 别名
	@Column(name = "aliasName")
	private String aliasName;
	
	// 音频ID
	@Column(name = "soundId")
	private Integer soundId;
	
	// 状态
	@Column(name = "status")
	private Integer status;

		
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getSoundId(){
		return this.soundId;
	}
	public void setSoundId(Integer soundId){
		this.soundId = soundId;
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
