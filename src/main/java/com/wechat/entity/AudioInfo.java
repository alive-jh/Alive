package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "audioinfo")
public class AudioInfo {
	
	@Id
	@GeneratedValue(generator ="paymentableGenerator")       
    @GenericGenerator(name ="paymentableGenerator", strategy ="identity")
    @Column(name = "id",length = 32)
	private Integer id;
	
	@Column(name = "audioid")
	private String audioId;//资源ID
	
	@Column(name = "name")
	private String name;//名称
	
	@Column(name = "cover")
	private String cover;//封面
	
	@Column(name = "cn")
	private String cn;//跟读文件
	
	@Column(name = "exp")
	private String exp;//翻译音频
	
	@Column(name = "picrecog")
	private String picrecog;//xml
	
	@Column(name = "mediainfo")
	private String mediainfo;//配置文件
	
	@Column(name = "src")
	private String src;//音频文件

	@Column(name = "user_id")
	private Integer userId;//音频文件
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAudioId() {
		return audioId;
	}

	public void setAudioId(String audioId) {
		this.audioId = audioId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getPicrecog() {
		return picrecog;
	}

	public void setPicrecog(String picrecog) {
		this.picrecog = picrecog;
	}

	public String getMediainfo() {
		return mediainfo;
	}

	public void setMediainfo(String mediainfo) {
		this.mediainfo = mediainfo;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
