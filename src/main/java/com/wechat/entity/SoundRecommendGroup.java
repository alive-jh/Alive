package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：sound_recommend
 * 数据说明：保存分类推荐与频道之间的管理
 * 
 * 
 * */

@Entity
@Table(name = "sound_recommend_group")
public class SoundRecommendGroup {

	@Id
	@GeneratedValue(generator ="SoundRecommendtableGenerator")       
    @GenericGenerator(name ="SoundRecommendtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 标签名称
	@Column(name = "tagEN")
	private String tagEN;
	
	//频道ID
	@Column(name = "channelId")
	private Integer channelId;
		
	// 标签名称
	@Column(name = "tagCN")
	private String tagCN;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getTagEN(){
		return this.tagEN;
	}
	public void setTagEN(String tagEN){
		this.tagEN =tagEN;
	}


	public int getChannelID(){
		return this.channelId;
	}
	public void setChannelId(int channelId){
		this.channelId = channelId;
	}
	
	public String getTagCN(){
		return this.tagCN;
	}
	public void setTagCN(String tagCN){
		this.tagCN =tagCN;
	}
}
