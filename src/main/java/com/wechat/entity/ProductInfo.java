package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

//LED产品参数表
@Entity
@Table(name = "product_info")
public class ProductInfo {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	@Column(name="name")
	private String name;//名称
	@Column(name="model")
	private String model;//型号
	@Column(name="power")
	private String power;//功率
	@Column(name="factor")
	private String factor;//功率因数
	@Column(name="colorttemperature")
	private String colortTemperature;//色温
	@Column(name="cri")
	private String cri;//显指
	@Column(name="dimming")
	private String dimming;//调光
	@Column(name="luminousflux")
	private String luminousFlux;//光通量
	@Column(name="createdate")
	private Date createDate;// 创建日期
	@Column(name="description")
	private String description;//产品描述
	@Column(name="characteristic")
	private String characteristic;//特点
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	public String getColortTemperature() {
		return colortTemperature;
	}
	public void setColortTemperature(String colortTemperature) {
		this.colortTemperature = colortTemperature;
	}
	public String getCri() {
		return cri;
	}
	public void setCri(String cri) {
		this.cri = cri;
	}
	public String getDimming() {
		return dimming;
	}
	public void setDimming(String dimming) {
		this.dimming = dimming;
	}
	public String getLuminousFlux() {
		return luminousFlux;
	}
	public void setLuminousFlux(String luminousFlux) {
		this.luminousFlux = luminousFlux;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}
	
	
	
}
