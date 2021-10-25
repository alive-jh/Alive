package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/*
 * 表名：library
 * 数据说明：保存条形码与书名的对应关系
 * 补充说明：获取规则先取book表
 * 
 * */

@Entity
@Table(name = "report_day_active")
public class ReportDayActive {

	@Id
	@GeneratedValue(generator ="ReportDayActivetableGenerator")       
    @GenericGenerator(name ="ReportDayActivetableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 日期
	@Column(name = "date")
	private String date;
	
	// 当天真是活跃数
	@Column(name = "real_count")
	private Integer realCount;
	
	// 页面展示活跃数
	@Column(name = "show_count")
	private Integer showCount;
	
	// 当天真实激活数
	@Column(name = "real_active_count")
	private Integer realActiveCount;
	
	// 当天展示激活数
	@Column(name = "show_active_count")
	private Integer showActiveCount;	
	
	
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getDate(){
		return this.date;
	}
	public void setDate(String date){
		this.date = date;
	}
	
	public Integer getRealCount(){
		return this.realCount;
	}
	public void setRealCount(Integer realCount){
		this.realCount = realCount;
	}
	
	public Integer getShowCount(){
		return this.showCount;
	}
	public void setShowCount(Integer showCount){
		this.showCount = showCount;
	}
	
	public Integer getRealActiveCount(){
		return this.realActiveCount;
	}
	public void setRealActiveCount(Integer realActiveCount){
		this.realActiveCount = realActiveCount;
	}
	
	public Integer getShowActiveCount(){
		return this.showActiveCount;
	}
	public void setShowActiveCount(Integer showActiveCount){
		this.showActiveCount = showActiveCount;
	}
	

}
