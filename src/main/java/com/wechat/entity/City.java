package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "city")
public class City {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "provinceid")
	private Integer provinceId;
}
