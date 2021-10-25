package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue(generator ="courseGenerator")       
    @GenericGenerator(name ="courseGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "productid")
	private Integer productId;	//商品课程ID
	
	@Column(name = "name")
	private String name;	//子课程名字
	
	@Column(name = "url")
	private String url;	 //子课程资源地址
	
	@Column(name = "total_class")
	private Integer totalClass;	 //子课程总课时

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTotalClass() {
		return totalClass;
	}

	public void setTotalClass(Integer totalClass) {
		this.totalClass = totalClass;
	}


	
	
}
