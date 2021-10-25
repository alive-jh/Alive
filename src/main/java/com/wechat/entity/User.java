package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Serializable {

	//基于hibernate的annocation 注解
	@Id
	@GeneratedValue(generator ="paymentableGenerator")
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id ;
	@Column(name="name",length = 50)
	private String name;//用户名称

	@Column(name = "account",length = 50)
	private String account;//账号

	@Column(name = "password",length = 50)
	private String password;//密码

	@Column(name = "email",length = 100)
	private String email;//email

	@Column(name = "sex",length = 11)
	private Integer sex;//性别:0男,1女

	@Column(name = "mobile",length = 11)
	private String mobile;//手机号码

	@Column(name = "status")
	private Integer status = new Integer(0);//用户状态0正常，1锁定

	@Column(name = "roleid")
	private Integer roleId;//用户角色

	@Temporal(TemporalType.DATE)
	@Column(name = "createdate")
	private Date createdate;//创建日期

	@Temporal(TemporalType.DATE)
	@Column(name = "lastdate")
	private Date lastdate;//最后登录时间

	@Column(name = "operatorid")
	private Integer operatorid = new Integer(0);//创建人




	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getLastdate() {
		return lastdate;
	}

	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}

	public Integer getOperatorid() {
		return operatorid;
	}

	public void setOperatorid(Integer operatorid) {
		this.operatorid = operatorid;
	}









}
