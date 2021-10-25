package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "rolemodular")
public class RoleModular {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;
	@Column(name = "roleid")
	private Integer roleId;
	@Column(name = "modularid")
	private Integer modularId;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getModularId() {
		return modularId;
	}
	public void setModularId(Integer modularId) {
		this.modularId = modularId;
	}
	
	
	
	
}
