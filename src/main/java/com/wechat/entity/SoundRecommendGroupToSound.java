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
@Table(name = "sound_recommend_group_to_sound")
public class SoundRecommendGroupToSound {

	@Id
	@GeneratedValue(generator ="SoundRecommendtableGenerator")       
    @GenericGenerator(name ="SoundRecommendtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 标签名称
	@Column(name = "groupId")
	private Integer groupId;
	
	//频道ID
	@Column(name = "soundId")
	private Integer soundId;
	
	//频道ID
	@Column(name = "albumId")
	private Integer albumId;
	
	//频道ID
	@Column(name = "sortId")
	private Integer sortId;

	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public int getGroupId(){
		return this.groupId;
	}
	public void setGroupId(Integer groupId){
		this.groupId =groupId;
	}


	public int getSoundId(){
		return this.soundId;
	}
	public void setSoundId(int soundId){
		this.soundId = soundId;
	}
	
	
	public int getAlbumId(){
		return this.albumId;
	}
	public void setAlbumId(Integer albumId){
		this.albumId =albumId;
	}


	public int getSortId(){
		return this.sortId;
	}
	public void setSortId(int sortId){
		this.sortId = sortId;
	}
}
