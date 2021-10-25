package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name = "video_competition", catalog = "wechat")
public class VideoCompetition {
	

		@Id
		@GeneratedValue(generator = "video_competitiontableGenerator")
		@GenericGenerator(name = "video_competitiontableGenerator", strategy = "identity")
		@Column(name = "id")
		private Integer Id;

		@Column(name = "video_info_id")
		private Integer videoInfoId;

		@Column(name = "video_activity_id")
		private Integer videoActivityId;

		@Column(name = "verify_status")
		private Integer verifyStatus;
		
		@Column(name = "acoutn_id")
		private String acoutnId;
		
		
		@OneToOne
	    @JoinColumn(name="video_info_id",updatable=false,insertable=false)  
	    private VideoInfo videoInfo;
		
		@OneToOne
	    @JoinColumn(name="video_activity_id",updatable=false,insertable=false) 
	    private VideoAcitivity videoAcitivity;
		
		

	
		public VideoInfo getVideoInfo() {
			return videoInfo;
		}

		public void setVideoInfo(VideoInfo videoInfo) {
			this.videoInfo = videoInfo;
		}

		public VideoAcitivity getVideoAcitivity() {
			return videoAcitivity;
		}

		public void setVideoAcitivity(VideoAcitivity videoAcitivity) {
			this.videoAcitivity = videoAcitivity;
		}

		public VideoCompetition(){
			
		}

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

		public Integer getVideoActivityId() {
			return videoActivityId;
		}

		public void setVideoActivityId(Integer videoActivityId) {
			this.videoActivityId = videoActivityId;
		}

		public Integer getVerifyStatus() {
			return verifyStatus;
		}

		public void setVerifyStatus(Integer verifyStatus) {
			this.verifyStatus = verifyStatus;
		}

		public String getAcoutnId() {
			return acoutnId;
		}

		public void setAcoutnId(String acoutnId) {
			this.acoutnId = acoutnId;
		}

		public VideoCompetition(Integer videoInfoId, Integer videoActivityId,
				Integer verifyStatus, String acoutnId) {
			super();
			this.videoInfoId = videoInfoId;
			this.videoActivityId = videoActivityId;
			this.verifyStatus = verifyStatus;
			this.acoutnId = acoutnId;
		}

		@Override
		public String toString() {
			return "VideoCompetition [Id=" + Id + ", videoInfoId="
					+ videoInfoId + ", videoActivityId=" + videoActivityId
					+ ", verifyStatus=" + verifyStatus + ", acoutnId="
					+ acoutnId + "]";
		}

		
		
		

		
		
		

	

}
