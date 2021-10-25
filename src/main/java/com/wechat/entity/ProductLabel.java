package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "productlabel")
public class ProductLabel {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "productid")
	private Integer productId;
	 
	 
	@Column(name = "labelid")
	private Integer labelId;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public Integer getLabelId() {
		return labelId;
	}


	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
	
	

}
