package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseVideoCompetition;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class VideoCompetition extends BaseVideoCompetition<VideoCompetition> {
	public static final VideoCompetition dao = new VideoCompetition().dao();
	
	public VideoInfo getVideoInfo() {
	       return VideoInfo.dao.findById(get("video_info_id"));
	} 
	
	public VideoActivity getVideoActivity(){
		return VideoActivity.dao.findById(get("video_activity_id"));
	}
}