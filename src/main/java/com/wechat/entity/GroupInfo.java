package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "group_info")
public class GroupInfo {
	@Id
	@GeneratedValue(generator ="groupInfotableGenerator")       
    @GenericGenerator(name ="groupInfotableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "group_id")
	private String groupId;
	
	
	@Column(name = "note_name")
	private String noteName;
	

	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setNoteName(String noteName){
		this.noteName = noteName;
	}
	
	public String getNoteName(){
		return this.noteName;
	}
	
	public void setGroupId(String groupId){
		this.groupId = groupId;
	}
	public String getGroupId(){
		return this.groupId;
	}
}

