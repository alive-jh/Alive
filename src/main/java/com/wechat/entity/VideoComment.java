package com.wechat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "video_comment", catalog = "wechat")
public class VideoComment {

	@Id
	@GeneratedValue(generator = "video_commenttableGenerator")
	@GenericGenerator(name = "video_commenttableGenerator", strategy = "identity")
	@Column(name = "id")
	private Integer Id;

	@Column(name = "vid")
	private Integer vid;

	@Column(name = "openid")
	private String openid;
	
	@Column(name = "content")
	@Pattern(regexp="^((?!<script>|&lt;script&gt;).)*$",message="非法参数")
	private String content;
	
	@Column(name = "commenter")
	private String commenter;

	@Column(name = "creat_time", insertable =false, updatable = false)
	private String creatTime;
	
	@Column(name = "update_time", insertable =false, updatable = false)
	private String updateTime;

	@Column(name = "sort")
	private Integer sort;

	

	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}

	

	public String getOpenid() {
		return openid;
	}



	public void setOpenid(String openid) {
		this.openid = openid;
	}



	public Integer getVid() {
		return vid;
	}



	public void setVid(Integer vid) {
		this.vid = vid;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getCommenter() {
		return commenter;
	}



	public void setCommenter(String commenter) {
		this.commenter = commenter;
	}



	public String getCreatTime() {
		return creatTime;
	}



	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}



	public String getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



	public Integer getSort() {
		return sort;
	}



	public void setSort(Integer sort) {
		this.sort = sort;
	}



	@Override
	public String toString() {
		return "VideoComment [Id=" + Id + ", vid=" + vid + ", content=" + content + ", commenter=" + commenter
				+ ", creatTime=" + creatTime + ", updateTime=" + updateTime + ", sort=" + sort + "]";
	}
	
	
	
}
