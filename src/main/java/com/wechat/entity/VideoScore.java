package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "video_score", catalog = "wechat")
public class VideoScore {

	@Id
	@GeneratedValue(generator = "video_scoretableGenerator")
	@GenericGenerator(name = "video_scoretableGenerator", strategy = "identity")
	@Column(name = "id")
	private Integer Id;
	
	@Column(name = "video_info_id")
	private Integer videoInfoId;
	
	@Column(name = "expert1")
	private String expert1;
	
	@Column(name = "expert2")
	private String expert2;
	
	@Column(name = "expert3")
	private String expert3;
	
	@Column(name = "expert4")
	private String expert4;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	

	public Integer getVideoInfoId() {
		return videoInfoId;
	}

	public void setVideoInfoId(Integer videoInfoId) {
		this.videoInfoId = videoInfoId;
	}

	public String getExpert1() {
		return expert1;
	}

	public void setExpert1(String expert1) {
		this.expert1 = expert1;
	}

	public String getExpert2() {
		return expert2;
	}

	public void setExpert2(String expert2) {
		this.expert2 = expert2;
	}

	public String getExpert3() {
		return expert3;
	}

	public void setExpert3(String expert3) {
		this.expert3 = expert3;
	}

	public String getExpert4() {
		return expert4;
	}

	public void setExpert4(String expert4) {
		this.expert4 = expert4;
	}

	@Override
	public String toString() {
		return "VideoScore [Id=" + Id + ", videoInfoId=" + videoInfoId
				+ ", expert1=" + expert1 + ", expert2=" + expert2
				+ ", expert3=" + expert3 + ", expert4=" + expert4 + "]";
	}
	
	
	
	
}
