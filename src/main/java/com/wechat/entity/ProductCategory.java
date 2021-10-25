package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;



@Entity
@Table(name = "product_category")
public class ProductCategory {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "cat_id")
	private Integer cat_id;				//CAT_ID
	
	@Column(name = "unique_id")
	private String unique_id;			//别名
	
	@Column(name = "cat_name")
	private String cat_name;			//描述
	
	@Column(name = "keywords")
	private String keywords;		//创建日期
	
	
	@Column(name = "description")		
	private String description;			//简单描述
	
	@Column(name = "parent_id")		
	private Integer parent_id = new Integer(0);	;			//父类Id

	@Column(name = "sort")		
	private Integer sort = new Integer(0);			//排序
	
	@Transient
	private String mark;

	public Integer getCat_id() {
		return cat_id;
	}
	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}

	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}
	public String getUnique_id() {
		return unique_id;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String catName) {
		cat_name = catName;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parentId) {
		parent_id = parentId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getMark() {
		return mark;
	}
	
	
	
	
	
	
	
	

}
