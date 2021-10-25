package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "activityinfo")
public class ActivityInfo {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	private Integer activityId;//活动id
	
	private Integer prizeId;//奖品id
	
	private String name;//会员名称
	
	private String mobile;//手机号码
	
	private String address;//地址
	
	private Integer status;//状态0为领奖,1已领奖
	
}
