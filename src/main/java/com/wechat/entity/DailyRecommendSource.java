package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：daily_recommend_source
 * 
 * 
 * 
 * */

@Entity
@Table(name = "daily_recommend_source")
public class DailyRecommendSource {

	@Id
	@GeneratedValue(generator ="DailyRecommendSourcetableGenerator")       
    @GenericGenerator(name ="DailyRecommendSourcetableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 标签ID
	@Column(name = "tagId")
	private Integer tagId;
	
	// 排序ID
	@Column(name = "sortId")
	private Integer sortId;
		
	// 状态
	@Column(name = "status")
	private Integer status;
	
	
	// 创建时间
	@Column(name = "createTime")
	private String createTime;
	
	
	
	// 资源Id
	@Column(name = "sourceId")
	private Integer sourceId;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getTagId(){
		return this.tagId;
	}
	public void setTagId(Integer tagId){
		this.tagId =tagId;
	}


	public Integer getSortId(){
		return this.sortId;
	}
	public void setSortId(Integer sortId){
		this.sortId = sortId;
	}
	
	
	public Integer getStatus(){
		return this.status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	
	public String getCreateTime(){
		return this.createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime =createTime;
	}
	
	
	public Integer getSourceId(){
		return this.sourceId;
	}
	public void setSourceId(Integer sourceId){
		this.sourceId = sourceId;
	}
}
