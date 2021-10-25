package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：album_alias
 * 数据说明：专辑别名表
 * 
 * 
 * */

@Entity
@Table(name = "album_alias")
public class AlbumAlias {

	@Id
	@GeneratedValue(generator ="AlbumAliastableGenerator")       
    @GenericGenerator(name ="AlbumAliastableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 别名
	@Column(name = "aliasName")
	private String aliasName;
	
	// 专辑ID
	@Column(name = "albumId")
	private Integer albumId;
	
	// 状态
	@Column(name = "status")
	private Integer status;

		
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
	

	
	public String getAliasName(){
		return this.aliasName;
	}
	
	public void setAliasName(String aliasName){
		this.aliasName = aliasName;
	}
	

	
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	

}
