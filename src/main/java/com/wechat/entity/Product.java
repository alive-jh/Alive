package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	@Column(name = "number")
	private String number;								//产品编号
	@Column(name = "createdate")
	private Date createDate;							//出厂日期
	@Column(name = "url")
	private String url;									//二维码url
	@Column(name = "productinfoid")
	private Integer productInfoId;						//产品参数ID
	@Column(name = "img")
	private String img;									//二维码图片
	
	@Column(name = "memberid")
	private Integer memberId = new Integer(0);			//会员ID
	
	@Column(name = "electricianid")
	private Integer electricianId = new Integer(0);		//电工ID
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getProductInfoId() {
		return productInfoId;
	}
	public void setProductInfoId(Integer productInfoId) {
		this.productInfoId = productInfoId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	

}
