package com.wechat.entity;

import javax.persistence.*;
@Entity
@Table(name="category")
public class Category {
	
	@Id
	
	@Column(name = "cateID")
	private String cateID;//书的编号
	
	@Column(name = "bname")
	private String bName;//书的标题
	
	@Column(name = "author")
	private String author="";//作者
	
	@Column(name = "translator")
	private String translator="";//译者
	
	@Column(name = "publish")
	private String publish="";//出版社
	
	@Column(name = "page")
	private Integer page=0;//页数
	
	@Column(name = "price")
	private Double price;//价钱
	
	@Column(name = "series")
	private String series="";//系列
	
	@Column(name = "remark")
	private String remark="";//标记
	
	@Column(name = "cover")
	private String cover;//封面
	
	@Column(name = "content")
	private String content="";//内容
	
	@Column(name = "count")
	private Integer count;//总数
	
	@Column(name = "bquantity")
	private Integer bquantity;//借阅量
	
	@Column(name = "mp3")
	private String mp3;	//mp3访问路径
	
	@Column(name = "testmp3")
	private String testMp3;	//试听音频
	
	
	@Column(name = "right_id")
	private Integer rightId;//书籍分类
	
	@Column(name = "status")
	private Integer status;//书籍状态:0上架,1下架

	@Column(name = "catalog")
	private String cataLog;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "mp3type")
	private Integer mp3Type = new Integer(0);//音频类型:中文= 1,英文= 2,中+英=3,粤语=4,中+粤=5,中英 = 8
	
	@Column(name = "book_cate_id")//图书分类
	private Integer book_cate_id;
	

	@Transient 
	private String excelMessage;

	
	
	public String getExcelMessage() {
		return excelMessage;
	}

	public void setExcelMessage(String excelMessage) {
		this.excelMessage = excelMessage;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getMp3Type() {
		return mp3Type;
	}

	public void setMp3Type(Integer mp3Type) {
		this.mp3Type = mp3Type;
	}

	public String getTestMp3() {
		return testMp3;
	}

	public void setTestMp3(String testMp3) {
		this.testMp3 = testMp3;
	}

	public String getCateID() {
		return cateID;
	}

	public void setCateID(String cateID) {
		this.cateID = cateID;
	}

	

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTranslator() {
		return translator;
	}

	public void setTranslator(String translator) {
		this.translator = translator;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getBquantity() {
		return bquantity;
	}

	public void setBquantity(Integer bquantity) {
		this.bquantity = bquantity;
	}



	public Integer getRightId() {
		return rightId;
	}

	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}

	

	public String getMp3() {
		return mp3;
	}

	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}

	public String getCataLog() {
		return cataLog;
	}

	public void setCataLog(String cataLog) {
		this.cataLog = cataLog;
	}

	public Integer getBook_cate_id() {
		return book_cate_id;
	}

	public void setBook_cate_id(Integer book_cate_id) {
		this.book_cate_id = book_cate_id;
	}

	
	


}
