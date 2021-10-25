package com.wechat.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {
	
	
	
	@Id
	@GeneratedValue(generator ="MessagetableGenerator")       
    @GenericGenerator(name ="MessagetableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "to_user")
	private String toUser;						//接受者
	
	@Column(name = "from_user")
	private String fromUser;							// 发送者
	
	@Column(name = "type")
	private String type;							//消息类型
	
	@Column(name = "content")
	private String content;						//消息体
	
	@Column(name = "create_date")
	private String createDate;						//创建时间
	
	@Column(name = "update_date")
	private String updateDate;						//更新时间
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}	

}
