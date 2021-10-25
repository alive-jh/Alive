package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：sound_search
 * 数据说明：音频搜索记录库
 * 补充说明：
 * 
 * */

@Entity
@Table(name = "sound_search")
public class SoundSearch {

	@Id
	@GeneratedValue(generator ="SoundSearchtableGenerator")       
    @GenericGenerator(name ="SoundSearchtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 搜索名称
	@Column(name = "soundName")
	private String soundName;
	
	// 入库时间
	@Column(name = "insertDate")
	private String insertDate;
	
	// 
	@Column(name = "userName")
	private String userName;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}

	public String getSoundName(){
		return this.soundName;
	}
	public void setSoundName(String soundName){
		this.soundName = soundName;
	}

	public String getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(String insertDate){
		this.insertDate = insertDate;
	}
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String userName){
		this.userName = userName;
	}
}
