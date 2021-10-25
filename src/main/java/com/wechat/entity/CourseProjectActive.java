package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "courseproject_active")
public class CourseProjectActive implements Serializable{
	
	@Id
	@GeneratedValue(generator ="courseproject_activeTableGenerator")       
    @GenericGenerator(name ="courseproject_activeTableGenerator", strategy ="identity")
    @Column(name = "id",length = 11)
	/**
	 * 系统课程计划激活ID
	 */
	private Integer id;
	/**
	 * 二维码网络地址
	 */
	@Column(name="qrcode_url")
	private String qrcodeUrl;
	/**
	 * 二维码存储内容
	 */
	@Column(name="qrcode_content")
	private String qrcodeContent;
	/**
	 * 系统课程计划ID
	 */
	@Column(name="plan_id")
	private Integer planId;
	/**
	 * 系统课程计划名称
	 */
	@Column(name="plan_name")
	private String planName;
	/**
	 * 激活次数
	 */
	@Column(name="active_count")
	private Integer activeCount;
	/**
	 * 二维码状态
	 */
	@Column(name="status")
	private Integer status;
	/**
	 * 二维码创建时间
	 */
	@Column(name="create_time")
	private Timestamp createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getQrcodeContent() {
		return qrcodeContent;
	}
	public void setQrcodeContent(String qrcodeContent) {
		this.qrcodeContent = qrcodeContent;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getActiveCount() {
		return activeCount;
	}
	public void setActiveCount(Integer activeCount) {
		this.activeCount = activeCount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	

	
}
