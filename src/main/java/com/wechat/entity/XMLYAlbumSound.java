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
@Table(name = "xmly_album_sound")
public class XMLYAlbumSound {

	@Id
	@GeneratedValue(generator ="XMLYChannelAlbumtableGenerator")       
    @GenericGenerator(name ="XMLYChannelAlbumtableGenerator", strategy ="identity")
	
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 音频ID
	@Column(name = "soundId")
	private Integer soundId;
	
	
	// 专辑ID
	@Column(name = "albumId")
	private Integer albumId;
	
	
	// 排序ID
	@Column(name = "sortId")
	private Integer sortId;

	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
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
	public void setAlbumId(int albumId){
		this.albumId = albumId;
	}
	public int getSortId(){
		return this.sortId;
	}
	public void setSortId(int sortId){
		this.sortId = sortId;
	}

}
