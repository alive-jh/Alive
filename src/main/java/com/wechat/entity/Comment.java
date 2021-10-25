package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comment")
public class Comment
{
	  @Id
	  @GeneratedValue(generator="paymentableGenerator")
	  @GenericGenerator(name="paymentableGenerator", strategy="identity")
	  @Column(name="id", length=32)
	  private Integer id;
	  
	  @Column(name="content")
	  private String content;	//评论内容
	  
	  @Column(name="memberid")
	  private Integer memberId;//会员id
	  
	  @Column(name="productid")
	  private Integer productId;//订单or书籍id
	  
	  @Column(name="createdate")
	  private Date createDate;//创建时间
	  
	  @Column(name="type")
	  private Integer type;//评论类型0,商品,1导师预约,2书籍评论
	  
	  @Column(name="star")
	  private Integer star;//  星级
	
	  
	
	  
	  public Integer getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(Integer id)
	  {
	    this.id = id;
	  }
	  
	  public String getContent()
	  {
	    return this.content;
	  }
	  
	  public void setContent(String content)
	  {
	    this.content = content;
	  }
	  
	  public Integer getMemberId()
	  {
	    return this.memberId;
	  }
	  
	  public void setMemberId(Integer memberId)
	  {
	    this.memberId = memberId;
	  }
	  
	  public Integer getProductId()
	  {
	    return this.productId;
	  }
	  
	  public void setProductId(Integer productId)
	  {
	    this.productId = productId;
	  }
	  
	  public Date getCreateDate()
	  {
	    return this.createDate;
	  }
	  
	  public void setCreateDate(Date createDate)
	  {
	    this.createDate = createDate;
	  }
	  
	  public Integer getType()
	  {
	    return this.type;
	  }
	  
	  public void setType(Integer type)
	  {
	    this.type = type;
	  }
	  
	  public Integer getStar()
	  {
	    return this.star;
	  }
	  
	  public void setStar(Integer star)
	  {
	    this.star = star;
	  }
}
