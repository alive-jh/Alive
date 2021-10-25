package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "video_info", catalog = "wechat")
public class VideoInfo {

	@Id
	@GeneratedValue(generator = "video_infotableGenerator")
	@GenericGenerator(name = "video_infotableGenerator", strategy = "identity")
	@Column(name = "id")
	private Integer Id;

	@Column(name = "v_title")
	private String vTitle;

	@Column(name = "epal_id")
	private String epalId;

	@Column(name = "acount_id")
	private String acountId;

	@Column(name = "s_name")
	private String sName;
	
	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "v_url")
	private String vUrl;

	@Column(name = "pic_url")
	private String picUrl;

	@Column(name = "localfile_id")
	private String localfileId;
	
	@Column(name = "share_url")
	private String shareUrl;

	@Column(name = "modify_time")
	private Date modifyTime;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "admission")
	private Integer admission;
	
	@Column(name = "vote")
	private Integer vote;
	
	@Column(name = "access_num")
	private Integer accessNum;
	
	@Column(name = "video_score_id")
	private Integer videoScoreId;
	
	@Column(name = "v_like")
	private Integer vLike;
	
	@Column(name = "v_reward")
	private double vRseward;
	
	@Column(name = "learn_time")
	private String learnTime;
	
	@Column(name = "comment_status")
	private Integer commentStatus;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "learn_day")
	private Integer learnDay;
	

	public VideoInfo(){
		
	}
	
	public VideoInfo(Integer id, String shareUrl, Integer admission) {
		super();
		Id = id;
		this.shareUrl = shareUrl;
		this.admission = admission;
	}
	
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getvTitle() {
		return vTitle;
	}

	public void setvTitle(String vTitle) {
		this.vTitle = vTitle;
	}

	public String getEpalId() {
		return epalId;
	}

	public void setEpalId(String epalId) {
		this.epalId = epalId;
	}

	public String getAcountId() {
		return acountId;
	}

	public void setAcountId(String acountId) {
		this.acountId = acountId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getvUrl() {
		return vUrl;
	}

	public void setvUrl(String vUrl) {
		this.vUrl = vUrl;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getLocalfileId() {
		return localfileId;
	}

	public void setLocalfileId(String localfileId) {
		this.localfileId = localfileId;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getAdmission() {
		return admission;
	}

	public void setAdmission(Integer admission) {
		this.admission = admission;
	}

	public Integer getVote() {
		return vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public Integer getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(Integer accessNum) {
		this.accessNum = accessNum;
	}

	public Integer getVideoScoreId() {
		return videoScoreId;
	}

	public void setVideoScoreId(Integer videoScoreId) {
		this.videoScoreId = videoScoreId;
	}

	public Integer getvLike() {
		return vLike;
	}

	public void setvLike(Integer vLike) {
		this.vLike = vLike;
	}

	public double getvRseward() {
		return vRseward;
	}

	public void setvRseward(double vRseward) {
		this.vRseward = vRseward;
	}

	public String getLearnTime() {
		return learnTime;
	}

	public void setLearnTime(String learnTime) {
		this.learnTime = learnTime;
	}


	
	

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(Integer commentStatus) {
		this.commentStatus = commentStatus;
	}

	
	public Integer getLearnDay() {
		return learnDay;
	}

	public void setLearnDay(Integer learnDay) {
		this.learnDay = learnDay;
	}

	@Override
	public String toString() {
		return "VideoInfo [Id=" + Id + ", vTitle=" + vTitle + ", epalId=" + epalId + ", acountId=" + acountId
				+ ", sName=" + sName + ", studentId=" + studentId + ", vUrl=" + vUrl + ", picUrl=" + picUrl
				+ ", localfileId=" + localfileId + ", shareUrl=" + shareUrl + ", modifyTime=" + modifyTime
				+ ", createTime=" + createTime + ", admission=" + admission + ", vote=" + vote + ", accessNum="
				+ accessNum + ", videoScoreId=" + videoScoreId + ", vLike=" + vLike + ", vRseward=" + vRseward
				+ ", learnTime=" + learnTime + ", commentStatus=" + commentStatus + ", status=" + status + "]";
	}
	
	





	
	
	

	



	

	

}
