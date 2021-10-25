package com.wechat.entity;

import java.util.List;

public class CommentInfo {

	private Integer productId;	// 产品id
	
	private String content;		//评论内容
	
	private String createDate;	//创建时间
	
	private Integer star;		//评论星级
	
	private String memberName;	//会员名称
	
	private List imgList;		//图片列表

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public List getImgList() {
		return imgList;
	}

	public void setImgList(List imgList) {
		this.imgList = imgList;
	}


	
	
	
	
}
