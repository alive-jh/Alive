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
@Table(name = "sound_tag_group")
public class SoundTagGroup {

	@Id
	@GeneratedValue(generator ="SoundTagGrouptableGenerator")       
    @GenericGenerator(name ="SoundTagGrouptableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	
	
	// 分组名称
	@Column(name = "groupName")
	private String groupName;
	
	public String getGroupName(){
		return this.groupName;
	}
	public void setGroupName(String groupName){
		this.groupName = groupName;
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
}
