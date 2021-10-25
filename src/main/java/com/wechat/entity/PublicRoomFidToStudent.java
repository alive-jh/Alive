package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/*
 * 表名：public_room_fid_to_student
 * 数据说明：fid卡和学生ID对应关系表
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "public_room_fid_to_student")
public class PublicRoomFidToStudent {

	@Id
	@GeneratedValue(generator ="PublicRoomFidToStudenttableGenerator")       
    @GenericGenerator(name ="PublicRoomFidToStudenttableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// fid 卡号
	@Column(name = "card_fid")
	private String cardFid;
	
	
	// 学生ID
	@Column(name = "student_id")
	private Integer studentId;
	
	// 插入时间
	@Column(name = "insert_date")
	private String insertDate;
	

	// 修改时间
	@Column(name = "update_date")
	private Date updateDate;
	
	// 更新者
	@Column(name = "update_by")
	private String updateBy;
	
	// 备注
	@Column(name = "remark")
	private String remark;
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getCardFid(){
		return this.cardFid;
	}
	public void setCardFid(String cardFid){
		this.cardFid =cardFid;
	}

	
	public Integer getStudentId(){
		return this.studentId;
	}
	public void setStudentId(Integer studentId){
		this.studentId =studentId;
	}
	
	public String getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(String insertDate){
		this.insertDate =insertDate;
	}
	
	public Date getUpdateDate(){
		return this.updateDate;
	}
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	
	
	
	public String getUpdateBy(){
		return this.updateBy;
	}
	
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	
	
	public String getRemark(){
		return this.remark;
	}
	
	public void setRemark(String remark){
		this.remark = remark;
	}
}
