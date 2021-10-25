package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_sign")
public class UserSign {

	@Id
	@GeneratedValue(generator ="user_signTableGenerator")       
    @GenericGenerator(name ="user_signTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "uname")
	private String uName;
	
	@Column(name = "ugrade")
	private String uGrade;
	
	@Column(name = "uage")
	private Integer uAge;
	
	@Column(name = "mobile")
	private String mobile;
	
	@Column(name = "sign_time")
	private Date signTime;

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getuGrade() {
		return uGrade;
	}

	public void setuGrade(String uGrade) {
		this.uGrade = uGrade;
	}

	public Integer getuAge() {
		return uAge;
	}

	public void setuAge(Integer uAge) {
		this.uAge = uAge;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "ExhibitionSign [id=" + id + ", memberId=" + memberId
				+ ", uName=" + uName + ", uGrade=" + uGrade + ", uAge=" + uAge
				+ ", mobile=" + mobile + "]";
	}
	
	
	
	

}
