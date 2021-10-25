package com.wechat.entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Material {
	
	
	
	private Integer id;
	
	private Integer type;					//素材类型:0单图文,1多图文
	
	private Integer accountId;				//公众账号ID
	
	private Integer parentId = 0;			//多图文父节点ID,正文,单图文为0,多图文为1
	
	private String logo;					//封面
	
	private String summary;					//摘要
	
	private String content;					//正文内容
	
	private String title;					//标题
	
	private Date createDate;				//创建时间
	
	private String url;						//链接地址
	
	private Integer contentType = 0;		//内容类型:0微官网页面,1显示正文,2显示跳转网址
	
	private Integer logoStatus = 0;			//logo状态:0上传,1引用
	
	private Integer keywordStatus = new Integer(0);//关键词是否使用,0启动,1不启动
	
	private Integer keywordType  = new Integer(0);//是否绑定关键词,0否,1是
	private String keywordName;
	
	private Integer keywordMatchingRules = new Integer(0); //关键词匹配规则,0精准匹配,1模糊匹配
	
	
	
	
	
	public Integer getKeywordMatchingRules() {
		return keywordMatchingRules;
	}
	public void setKeywordMatchingRules(Integer keywordMatchingRules) {
		this.keywordMatchingRules = keywordMatchingRules;
	}
	public Integer getKeywordType() {
		return keywordType;
	}
	public void setKeywordType(Integer keywordType) {
		this.keywordType = keywordType;
	}
	public String getKeywordName() {
		return keywordName;
	}
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	public Integer getKeywordStatus() {
		return keywordStatus;
	}
	public void setKeywordStatus(Integer keywordStatus) {
		this.keywordStatus = keywordStatus;
	}
	private List materialList = new ArrayList();
	
	
	public List getMaterialList() {
		return materialList;
	}
	public void setMaterialList(List materialList) {
		this.materialList = materialList;
	}
	public Integer getLogoStatus() {
		return logoStatus;
	}
	public void setLogoStatus(Integer logoStatus) {
		
		
		this.logoStatus = logoStatus;
	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
	
}
