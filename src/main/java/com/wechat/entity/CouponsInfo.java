package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "couponsinfo")
public class CouponsInfo {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	@Column(name = "couponsid")
	private Integer couponsId;		/*优惠卷id*/
	@Column(name = "memberid")
	private Integer memberId;		/*会员id*/
	@Column(name = "status")
	private Integer status;			/*状态,0未使用,1已使用*/
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCouponsId() {
		return couponsId;
	}
	public void setCouponsId(Integer couponsId) {
		this.couponsId = couponsId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	

}
