package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "exhibition_sign")
public class ExhibitionSign {

	@Id
	@GeneratedValue(generator ="exhibition_signTableGenerator")       
    @GenericGenerator(name ="exhibition_signTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	private Integer id;
	
	@Column(name = "member_id")
	private Integer memberId;
	
	@Column(name = "uname")
	private String uName;
	
	@Column(name = "mobile")
	private String mobile;

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


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return "ExhibitionSign [id=" + id + ", memberId=" + memberId
				+ ", uName=" + uName + ", mobile=" + mobile + "]";
	}

	
	
	
	
	

}
