package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "keyword")
public class Keyword {

	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;							//ID
	@Column(name = "status")
	private Integer status;						//启动状态:0启动,1不启动
	@Column(name = "accountId")
	private Integer accountId = new Integer(0);	//公众账号ID
	@Column(name = "contentType")
	private Integer contentType = 0;			//回复内容类型:0文本,1单图文,2多图文
	@Column(name = "matchingRules")
	private Integer matchingRules = 0;			//匹配规则:0精准匹配,1模糊匹配
	@Column(name = "keyword")
	private String keyword;						//关键词
	@Column(name = "content")
	private String content;						//回复内容
	@Column(name = "createDate")
	private Date createDate;					//创建日期
	@Column(name = "materialId")
	private Integer materialId =0;				//素材ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public Integer getMatchingRules() {
		return matchingRules;
	}
	public void setMatchingRules(Integer matchingRules) {
		this.matchingRules = matchingRules;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	
	
	
	
	
	
	
}
