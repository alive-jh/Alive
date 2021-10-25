package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "app_fuc_show")
public class AppFucShow {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id")
	private Integer id;	
	
	@Column(name = "epal_id")
	private String epalId;	//机器人唯一标示
	
	@Column(name = "fuc_name")
	private String fucName;	//功能名称
	
	@Column(name = "is_show")
	private Integer show;			//是否显示，0 不显示 1显示

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShow() {
		return show;
	}

	public void setShow(Integer show) {
		this.show = show;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getFucName() {
		return fucName;
	}

	public void setFucName(String fucName) {
		this.fucName = fucName;
	}

	

}
