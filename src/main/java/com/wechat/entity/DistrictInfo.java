package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "districtinfo")
public class DistrictInfo {
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;
	@Column(name = "districtid")
	private Integer districtId;
	@Column(name = "electrismid")
	private Integer electrismId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}
	public Integer getElectrismId() {
		return electrismId;
	}
	public void setElectrismId(Integer electrismId) {
		this.electrismId = electrismId;
	}
	
	
	
}
