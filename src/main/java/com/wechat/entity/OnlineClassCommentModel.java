package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="online_class_comment_model")
public class OnlineClassCommentModel
{
	  @Id
	  @GeneratedValue(generator="paymentableGenerator")
	  @GenericGenerator(name="paymentableGenerator", strategy="identity")
	  @Column(name="id", length=32)
	  private Integer id;
	  

	  
	  @Column(name="teacher_id")
	  private Integer teacherId;//老师ID
	  
	  @Column(name="model_name")
	  private String modelName;//模板名称
	  
	  @Column(name="content")
	  private String content;	//评论内容
	  
	
	  @Column(name="model_type")
	  private String modelType;	//模板类型
	  
	  

	  
	  @Column(name="status")
	  private Integer status;//状态
	  
	  @Column(name="insert_date")
	  private String insertDate;//添加时间
	  
	  
	  @Column(name="edit_date")
	  private String editDate;//修改时间
	

	
	  
	  public Integer getId()
	  {
	    return this.id;
	  }
	  
	  public void setId(Integer id)
	  {
	    this.id = id;
	  }
	  
	  public Integer getTeacherId()
	  {
	    return this.teacherId;
	  }
	  
	  public void setTeacherId(Integer teacherId)
	  {
	    this.teacherId = teacherId;
	  }	  
	  
	  
	  public String getModelName()
	  {
	    return this.modelName;
	  }
	  
	  public void setModelName(String modelName)
	  {
	    this.modelName = modelName;
	  }
	  	  
	  
	  
	  public String getContent()
	  {
	    return this.content;
	  }
	  
	  public void setContent(String content)
	  {
	    this.content = content;
	  }
	  
	  public Integer getStatus()
	  {
	    return this.status;
	  }
	  
	  public void setStatus(Integer status)
	  {
	    this.status = status;
	  }
	  

	  
	  public String getModelType()
	  {
	    return this.modelType;
	  }
	  
	  public void setModelType(String modelType)
	  {
	    this.modelType = modelType;
	  }
	  
	  
	  
	  public String getInsertDate()
	  {
	    return this.insertDate;
	  }
	  
	  public void setInsertDate(String insertDate)
	  {
	    this.insertDate = insertDate;
	  }
	  
	  
	  public String getEditDate()
	  {
	    return this.editDate;
	  }
	  
	  public void setEditDate(String editDate)
	  {
	    this.editDate = editDate;
	  }
}
