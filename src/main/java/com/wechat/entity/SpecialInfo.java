package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "specialinfo")
public class SpecialInfo {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	

	@Column(name = "specialid")
	private Integer specialId;		//专题id
	
	@Column(name = "categoryid")
	private String categoryId;		//书籍类型id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
	
	
}
