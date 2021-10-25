package com.wechat.entity;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Test entity. @author zlisten
 */
@Entity
@Table(name = "eval_wx_user", catalog = "wechat")
public class EvalWxUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer status;
	private Integer sort;
	private Timestamp creatTime;
	private Timestamp editTime;
	
	private String openId;
	private String childName;
	private Integer childAge;
	private String childGrade;
	private String childNickName;
	private String parentPhone;
	private String memberId;
	

	// Constructors

	/** default constructor */
	public EvalWxUser() {
	}

	/** full constructor */
	public EvalWxUser(Integer status,Integer sort,String openId,String childName,Integer childAge,String childGrade,
			String childNickName,String parentPhone){
		this.status = status;
		this.sort = sort;
		this.openId = openId;
		this.childName = childName;
		this.childAge = childAge;
		this.childGrade = childGrade;
		this.childNickName = childNickName;
		this.parentPhone = parentPhone;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Column(name = "creat_time", insertable =false, updatable = false)
	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}
	@Column(name = "edit_time", insertable =false, updatable = false)
	public Timestamp getEditTime() {
		return editTime;
	}

	public void setEditTime(Timestamp editTime) {
		this.editTime = editTime;
	}	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "open_id")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Column(name = "child_name")
	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}
	@Column(name = "child_age")
	public Integer getChildAge() {
		return childAge;
	}

	public void setChildAge(Integer childAge) {
		this.childAge = childAge;
	}
	@Column(name = "child_grade")
	public String getChildGrade() {
		return childGrade;
	}

	public void setChildGrade(String childGrade) {
		this.childGrade = childGrade;
	}
	@Column(name = "child_nick_name")
	public String getChildNickName() {
		return childNickName;
	}

	public void setChildNickName(String childNickName) {
		this.childNickName = childNickName;
	}
	@Column(name = "parent_phone")
	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
	@Column(name = "memberId")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}