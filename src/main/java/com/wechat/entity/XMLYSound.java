package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：xmly_album
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "xmly_sound")
public class XMLYSound {

	@Id
	@GeneratedValue(generator ="XMLYSoundtableGenerator")       
    @GenericGenerator(name ="XMLYSoundtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	// 音频ID
	@Column(name = "soundId")
	private Integer soundId;
	
	public Integer getSoundId(){
		return this.soundId;
	}
	public void setSoundId(Integer soundId){
		this.soundId = soundId;
	}
	
	// 音频名称
	@Column(name = "name")
	private String name;
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	// 音频图片
	@Column(name = "image")
	private String image;
	
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	
	// 音频简介
	@Column(name = "intro")
	private String intro;
	
	public String getIntro(){
		return this.intro;
	}
	public void setIntro(String intro){
		this.intro = intro;
	}
	
	//播放次数
	@Column(name = "playTimes")
	private Integer playTimes;
	
	public Integer getPlayTimes(){
		return this.playTimes;
	}
	public void setPlayTimes(Integer playTimes){
		this.playTimes = playTimes;
	}
	
	// 播放地址
	@Column(name = "playUrl")
	private String playUrl;
	
	public String getPlayUrl(){
		return this.playUrl;
	}
	public void setPlayUrl(String playUrl){
		this.playUrl = playUrl;
	}
	
	// 更新时间
	@Column(name = "updateTime")
	private String updateTime;
	
	public String getUpdateTime(){
		return this.updateTime;
	}
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}	
	
	// 专辑名称
	@Column(name = "albumName")
	private String albumName;
	
	public String getAlbumName(){
		return this.albumName;
	}
	public void setAlbumName(String albumName){
		this.albumName = albumName;
	}	
	
	
	// 频道ID
	@Column(name = "channelId")
	private Integer channelId;
	
	public Integer getChannelId(){
		return this.channelId;
	}
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	}
	
	// 专辑ID
	@Column(name = "albumId")
	private Integer albumId;
	
	public Integer getAlbumId(){
		return this.albumId;
	}
	public void setAlbumId(Integer albumId){
		this.albumId = albumId;
	}
	
	// 状态
	@Column(name = "status")
	private Integer status;
	
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	
	
	// 排序ID
	@Column(name = "sortId")
	private Integer sortId;
	
	public Integer getSortId(){
		return this.sortId;
	}
	public void setSortId(Integer sortId){
		this.sortId = sortId;
	}
	
	
	// 书籍编码
	@Column(name = "cate_id")
	private String cateId;
	
	public String getCateId(){
		return this.cateId;
	}
	public void setCateId(String cateId){
		this.cateId = cateId;
	}
	
	
	// 评分
	@Column(name = "score")
	private Integer score;
	
	public Integer getScore(){
		return this.score;
	}
	public void setScore(Integer score){
		this.score = score;
	}
	
	// 凡豆播放次数
	@Column(name = "fd_playCount")
	private Integer FDPlayCount;
	
	public Integer getFDPlayCount(){
		return this.FDPlayCount;
	}
	public void setFDPlayCount(Integer FDPlayCount){
		this.FDPlayCount = FDPlayCount;
	}
}
