package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：sound_tag_source
 * 数据说明：标签和资源对应关系表
 * 
 * 
 * */

@Entity
@Table(name = "sound_tag_source")
public class SoundTagSource {

	@Id
	@GeneratedValue(generator ="SoundTagSourcetableGenerator")       
    @GenericGenerator(name ="SoundTagSourcetableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	// 标签ID
	@Column(name = "tagId")
	private Integer tagId;
	
	public Integer getTagId(){
		return this.tagId;
	}
	public void setTagId(Integer tagId){
		this.tagId = tagId;
	}
	
	// 资源ID
	@Column(name = "sourceId")
	private Integer sourceId;
	
	public Integer getSourceId(){
		return this.sourceId;
	}
	
	public void setSourceId(Integer sourceId){
		this.sourceId = sourceId;
	}
	
	// 资源类型
	@Column(name = "sourceType")
	private String sourceType;
	
	public void setSourceType(String sourceType){
		this.sourceType = sourceType;
	}
	public String getSourceType(){
		return this.sourceType;
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
	
}
