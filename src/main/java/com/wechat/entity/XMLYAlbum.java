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
@Table(name = "xmly_album")
public class XMLYAlbum {

	@Id
	@GeneratedValue(generator ="XMLYAlbumtableGenerator")       
    @GenericGenerator(name ="XMLYAlbumtableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 答案
	@Column(name = "album_id")
	private Integer albumId;
	
	// 插入时间
	@Column(name = "channel_id")
	private Integer channelId;
	
	// 对应问题ID
	@Column(name = "name")
	private String name;

	// 插入时间
	@Column(name = "image")
	private String image;
	
	// 对应问题ID
	@Column(name = "intro")
	private String intro;
	
	
	// 答案
	@Column(name = "status")
	private Integer status;
	
	// 插入时间
	@Column(name = "sort_id")
	private Integer sortId;
		
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getAlbumId(){
		return this.albumId;
	}
	public void setAlbumId(Integer albumId){
		this.albumId = albumId;
	}
	
	
	public Integer getChannelId(){
		return this.channelId;
	}
	public void setChannelId(Integer channelId){
		this.channelId = channelId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}

	public String getIntro(){
		return this.intro;
	}
	public void setIntro(String intro){
		this.intro = intro;
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
}
