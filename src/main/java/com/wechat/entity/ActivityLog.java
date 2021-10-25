package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activitylog")
public class ActivityLog {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	private Integer activityId;//活动id
	
	private String openId;//openId
	
	
	private Integer memberId;//会员ID
	
	private Date createDate;//创建日期
	
	
	
	
	
}
