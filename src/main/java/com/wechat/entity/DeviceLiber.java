package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：机器人书库
 * 数据说明：
 * 
 * */

@Entity
@Table(name = "device_liber")
public class DeviceLiber {

	@Id
	@GeneratedValue(generator ="DeviceLibertableGenerator")       
    @GenericGenerator(name ="DeviceLibertableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 机器人账号
	@Column(name = "user_id")
	private String userId;
	
	// 文件名称
	@Column(name = "file_name")
	private String fileName;
	
	//封面地址
	@Column(name = "image_url")
	private String imageUrl;
	
	// 类型
	@Column(name = "type")
	private String type;

	
	// 音频地址
	@Column(name = "music_url")
	private String musicUrl;
	
	// 创建时间
	@Column(name = "create_date")
	private String createDate;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getUserId(){
		return this.userId;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getFileName(){
		return this.fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}

	public String getCreateDate(){
		return this.createDate;
	}
	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}

	public String getMusicUrl(){
		return this.musicUrl;
	}
	public void setMusicUrl(String musicUrl){
		this.musicUrl = musicUrl;
	}
	
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}	

	public String getImageUrl(){
		return this.imageUrl;
	}
	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}	
	
	
}
