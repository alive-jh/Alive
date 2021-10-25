package com.wechat.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "video_activity", catalog = "wechat")
public class VideoAcitivity {
	

		@Id
		@GeneratedValue(generator = "video_activitytableGenerator")
		@GenericGenerator(name = "video_activitytableGenerator", strategy = "identity")
		@Column(name = "id")
		private Integer Id;

		@Column(name = "activity_name")
		private String activityName;

		@Column(name = "start_time")
		private Date startTime;

		@Column(name = "end_time")
		private Date endTime;
		
		@Column(name = "status")
		private Integer status;

		public VideoAcitivity(){
			
		}
		
		public VideoAcitivity(String activityName, Date startTime,
				Date endTime, Integer status) {
			super();
			this.activityName = activityName;
			this.startTime = startTime;
			this.endTime = endTime;
			this.status = status;
		}

		public Integer getId() {
			return Id;
		}

		public void setId(Integer id) {
			Id = id;
		}

		public String getActivityName() {
			return activityName;
		}

		public void setActivityName(String activityName) {
			this.activityName = activityName;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "VideoAcitivity [Id=" + Id + ", activityName="
					+ activityName + ", startTime=" + startTime + ", endTime="
					+ endTime + ", status=" + status + "]";
		}
		
		

	

}
