package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friends {
	@Id
	@GeneratedValue(generator ="friendstableGenerator")       
    @GenericGenerator(name ="friendstableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "friend_id")
	private String friendId;
	
	@Column(name = "friend_type")
	private String friendType;
	
	@Column(name = "note_name")
	private String noteName;
	
	@Column(name = "group_id")
	private String groupId;
	
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
	
	public void setFriendId(String friendId){
		this.friendId = friendId;
	}
	
	public String getFriendId(){
		return this.friendId;
	}
	
	public void setFriendType(String friendType){
		this.friendType = friendType;
	}
	
	public String getFriendType(){
		return this.friendType;
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

