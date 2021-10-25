package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "special")
public class Special {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "title")
	private String title;		//专题标题
	@Column(name = "logo")
	private String logo;		//专题logo
	@Column(name = "status")
	private Integer status;		//专题状态:0启动,1停用
	@Column(name = "sort")
	private Integer sort;		//排序
	@Column(name = "cat_id")
	private Integer catId;		//选择对应的主题分类
	@Column(name = "topic_type")
	private Integer topicType;	//主题类别
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getCatId() {
		return catId;
	}
	public void setCatId(Integer catId) {
		this.catId = catId;
	}
	public Integer getTopicType() {
		return topicType;
	}
	public void setTopicType(Integer topicType) {
		this.topicType = topicType;
	}
	
	
	
	
}
