package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "integral")

public class Integral {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	@Column(name = "memberid")
	private Integer memberId;	//会员id
	@Column(name = "status")
	private Integer status;		//积分状态0入账,1出账
	@Column(name = "fraction")
	private Integer fraction;	//积分数
	@Column(name = "createdate")
	private Date createDate;	//创建日期
	@Column(name = "typeid")
	private Integer typeId;		//积分类型
	
	private String remark;		//备注
	
	@Column(name = "membertype")
	private Integer memberType;	//会员类型:0IOS,1安卓or微信
	
	
	
	
	
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getFraction() {
		return fraction;
	}
	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
