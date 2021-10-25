package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
@Table(name = "mallbanner")
public class MallBanner {
	
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "title")
	private String title;		//标题
	
	@Column(name = "banner")
	private String banner;		//封面图片
	
	@Column(name = "url")
	private String url;			//跳转地址

	@Column(name = "status")
	private Integer status = new Integer(0);		//显示状态0显示,1不显示
	
	@Column(name = "urltype")
	private Integer urlType = new Integer(0);	//0跳转地址,1其他商品链接
	
	@Column(name = "type")
	private Integer type = new Integer(0);		//显示状态,0全部,1微信,2APP
	
	
	
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUrlType() {
		return urlType;
	}

	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
	
}
