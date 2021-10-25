package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/*
 * 表名：sound_best
 * 数据说明：音频置顶库
 * 补充说明：
 * 
 * */

@Entity
@Table(name = "sound_best")
public class SoundBest {

	@Id
	@GeneratedValue(generator ="soundbesttableGenerator")       
    @GenericGenerator(name ="soundbesttableGenerator", strategy ="identity")
	@Column(name = "id",length = 32)
	private Integer id;
	
	// 音频ID
	@Column(name = "sound_id")
	private String soundId;
	
	// 音频图片
	@Column(name = "image")
	private String image;
	
	// 音频图片
	@Column(name = "sound_url")
	private String soundUrl;
	
	
	// 音频图片
	@Column(name = "sound_name")
	private String soundName;
	
	// 音频图片
	@Column(name = "serier_name")
	private String serierName;
	
	// 音频图片
	@Column(name = "isbn")
	private String ISBN;
	
	//插入时间
	@Column(name="insert_date")
	private Date insertDate;

	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public String getSoundId(){
		return this.soundId;
	}
	public void setSoundId(String soundId){
		this.soundId =soundId;
	}
	
	public String getImage(){
		return this.image;
	}
	public void setImage(String image){
		this.image = image;
	}
	
	public String getSoundUrl(){
		return this.soundUrl;
	}
	public void setSoundUrl(String soundUrl){
		this.soundUrl = soundUrl;
	}
	
	public String getSoundName(){
		return this.soundName;
	}
	public void setSoundName(String soundName){
		this.soundName = soundName;
	}
	
	public String getSerierName(){
		return this.serierName;
	}
	public void setSerierName(String serierName){
		this.serierName = serierName;
	}
	
	public Date getInsertDate(){
		return this.insertDate;
	}
	public void setInsertDate(Date insertDate){
		this.insertDate = insertDate;
	}
	public String getISBN(){
		return this.ISBN;
	}
	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}
}
