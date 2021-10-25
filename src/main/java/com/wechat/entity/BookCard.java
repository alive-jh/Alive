package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "bookcard")
public class BookCard {
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "memberid")
	private Integer memberId;						//会员ID
	
	@Column(name = "shopid")
	private Integer shopId = new Integer(0);		//店铺ID
	
	@Column(name = "status")
	private Integer status = new Integer(0);		//状态0未使用,1已使用
	
	@Column(name = "card")
	private String card;							//卡号
	
	@Column(name = "type")
	private Integer type =new Integer(2);			//0黄金会员,1白金会员,2加盟店会员					
	
	@Column(name = "price")
	private Integer price;							//价格
	
	
	@Column(name = "createdate")
	private Date createDate;					//创建时间
	
	@Column(name = "year")
	private Integer year = new Integer(1);			//年限

	
	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
	
}
