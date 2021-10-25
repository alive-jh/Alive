package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="online_class_delete_student_record")
public class OnlineClassDeleteStudentRecord
{
	  @Id
	  @GeneratedValue(generator="paymentableGenerator")
	  @GenericGenerator(name="paymentableGenerator", strategy="identity")
	  @Column(name="id", length=32)
	  private Integer id;
	  

	  
	  @Column(name="teacher_id")
	  private Integer teacherId;//老师ID
	  
	  @Column(name="student_id")
	  private Integer studentId;//学生ID
	  
	  @Column(name="grades_id")
	  private Integer gradesId;	//班级ID
	  
	  
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
	  
	  
	  public Integer getStudentId()
	  {
	    return this.studentId;
	  }
	  
	  public void setStudentId(Integer studentId)
	  {
	    this.studentId = studentId;
	  }
	  	  
	  
	  
	  public Integer getGradesId()
	  {
	    return this.gradesId;
	  }
	  
	  public void setGradesId(Integer gradesId)
	  {
	    this.gradesId = gradesId;
	  }
	  
	  public Integer getStatus()
	  {
	    return this.status;
	  }
	  
	  public void setStatus(Integer status)
	  {
	    this.status = status;
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
