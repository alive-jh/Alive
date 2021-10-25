package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "course_schedule")
public class CourseSchedule {

	@Id
	@GeneratedValue(generator ="course_scheduleGenerator")       
    @GenericGenerator(name ="course_scheduleGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "productid")
	private Integer productid;	//商品课程ID
	
	@Column(name = "courseid")
	private Integer courseid;	//商品子课程ID
	
	@Column(name = "periodid")
	private Integer periodId;	//商品子课时ID
	
	@Column(name = "device_no")
	private String deviceNo;	//设备唯一标示
	
	@Column(name = "epal_id")
	private String epalId;	//设备IM号
	
	@Column(name = "schedule")
	private String schedule;	//课程进度
	
	@Column(name = "cus_file")
	private String cusFile;	//课程进度文件
	
	@Column(name = "cur_class")
	private Integer curClass;	//当前子课程课时
	
	@Column(name = "create_time")
	private Timestamp createTime; //创建时间

	
	public Integer getCourseid() {
		return courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public Integer getCurClass() {
		return curClass;
	}

	public void setCurClass(Integer curClass) {
		this.curClass = curClass;
	}

	public String getCusFile() {
		return cusFile;
	}

	public void setCusFile(String cusFile) {
		this.cusFile = cusFile;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public Integer getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}


	
}
