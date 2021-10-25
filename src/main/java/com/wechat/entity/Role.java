package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;		//角色名
	@Column(name = "modularids")
	private String modularIds;//模块id
	
	
	
	public String getModularIds() {
		return modularIds;
	}
	public void setModularIds(String modularIds) {
		this.modularIds = modularIds;
	}
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

	
	
	
	
}
