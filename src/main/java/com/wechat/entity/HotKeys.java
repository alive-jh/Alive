package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "hot_keys")
public class HotKeys {

	@Id
	@GeneratedValue(generator ="hotKeystableGenerator")       
    @GenericGenerator(name ="hotKeystableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "keys")
	private String keys;
	
	@Column(name = "keyType")
	private String keyType;
	
	
	@Column(name = "score")
	private Integer score;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}
	
	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
