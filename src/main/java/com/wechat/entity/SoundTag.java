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
@Table(name = "sound_tag")
public class SoundTag {

	@Id
	@GeneratedValue(generator ="SoundTagtableGenerator")       
    @GenericGenerator(name ="SoundTagtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 标签名称
	@Column(name = "tagName")
	private String tagName;
	
	// 状态
	@Column(name = "status")
	private Integer status;
	
	// 排序ID
	@Column(name = "sortId")
	private Integer sortId;

	
	// 频道ID
	@Column(name = "channelId")
	private Integer channelId;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}

	public String getTagName(){
		return this.tagName;
	}
	public void setTagName(String tagName){
		this.tagName = tagName;
	}

	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public Integer getSortId(){
		return this.sortId;
	}
	public void setSortId(Integer sortId){
		this.sortId = sortId;
	}
	
	public Integer getChannelId(){
		return this.channelId;
	}
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	}
}
