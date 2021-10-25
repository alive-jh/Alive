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
@Table(name = "xmly_channel")
public class XMLYChannel {

	@Id
	@GeneratedValue(generator ="xmlyChanneltableGenerator")       
    @GenericGenerator(name ="xmlyChanneltableGenerator", strategy ="identity")
	
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 频道名称
	@Column(name = "name")
	private String name;
	
	
	// 粉丝数
	@Column(name = "fans")
	private Integer fans;
	
	
	// 频道说明
	@Column(name = "intro")
	private String intro;

	// 频道简图
	@Column(name = "image")
	private String image;
	
	// 频道ID
	@Column(name = "channel_id")
	private Integer channelId;
	
	// 频道状态，屏蔽或者开放
	@Column(name = "status")
	private Integer status;
	
	
	// 频道等级
	@Column(name = "level")
	private Integer level;
	
	
	

	
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
	
	public int getFans(){
		return this.fans;
	}
	public void setFans(int fans){
		this.fans = fans;
	}
	
	public String getIntro(){
		return this.intro;
	}
	public void setIntro(String intro){
		this.intro = intro;
	}
	public String getImage(){
		return this.image;
	}
	public void setImage(String image){
		this.image = image;
	}	
	
	
	public int getChannelId(){
		return this.channelId;
	}
	public void setChannelId(int channelId){
		this.channelId = channelId;
	}
	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public int getLevel(){
		return this.level;
	}
	public void setLevel(int level){
		this.level = level;
	}
	
	
	// 上次更新时间
	@Column(name = "lastUpdateTime")
	private String lastUpdateTime;
	
	public String getLastUpdateTime(){
		return this.lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime){
		this.lastUpdateTime = lastUpdateTime;
	}	
	
	
	// 下次更新时间
	@Column(name = "nextUpdateTime")
	private String nextUpdateTime;
	
	public String getNextUpdateTime(){
		return this.nextUpdateTime;
	}
	public void setNextUpdateTime(String nextUpdateTime){
		this.nextUpdateTime = nextUpdateTime;
	}	
	
	// 更新周期
	@Column(name = "updateCycle")
	private Integer updateCycle;

	public int getUpdateCycle(){
		return this.updateCycle;
	}
	public void setUpdateCycle(int updateCycle){
		this.updateCycle = updateCycle;
	}
}
