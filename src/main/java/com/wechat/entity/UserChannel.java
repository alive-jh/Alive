package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_channel")
public class UserChannel {
	
	
	@Id
	@GeneratedValue(generator ="UserChanneltableGenerator")       
    @GenericGenerator(name ="UserChanneltableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;

	@Column(name = "user_id")
	private String userId;	//机器人ID
	
	@Column(name = "channel_id")
	private String channelId;		//频道ID

	
	public String getUserId() {
		return userId;
	}
	public void setuserId(String userId) {
		this.userId = userId;
	}
	public String getchannelId() {
		return channelId;
	}
	public void setchannelId(String channelId) {
		this.channelId = channelId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
