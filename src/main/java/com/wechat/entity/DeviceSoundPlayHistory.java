package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "device_sound_play_history")
public class DeviceSoundPlayHistory {

	@Id
	@GeneratedValue(generator ="DeviceSoundPlayHistorytableGenerator")       
    @GenericGenerator(name ="DeviceSoundPlayHistorytableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 问题
	@Column(name = "epal_id")
	private String epalId;
	
	
	// 插入时间
	@Column(name = "sound_id")
	private Integer soundId;
	
	// 插入时间
	@Column(name = "insert_date")
	private String insertDate;
	
	
	// 播放类型
	@Column(name = "playType")
	private String playType;
	
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
		this.epalId =epalId;
	}

	public Integer getSoundId(){
		return this.soundId;
	}
	public void setSoundId(Integer soundId){
		this.soundId =soundId;
	}
	
	public String getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(String insertDate){
		this.insertDate = insertDate;
	}
	
	
	public String getPlayType(){
		return this.playType;
	}
	public void setPlayType(String playType){
		this.playType = playType;
	}
}
