package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rightcategory")
public class RightCategory {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "left_id")
	private Integer leftId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLeftId() {
		return leftId;
	}
	public void setLeftId(Integer leftId) {
		this.leftId = leftId;
	}
	
	
	
}
