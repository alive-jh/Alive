package com.wechat.dao;

import com.wechat.entity.ClassTeacher;
import com.wechat.entity.VideoAcitivity;
import com.wechat.entity.VideoComment;
import com.wechat.entity.VideoCompetition;
import com.wechat.entity.VideoInfo;

import java.util.List;

public interface VideoDao {
	
	
	VideoInfo getVideoInfo(Integer vid);
	
	
	void saveVideoInfo(VideoInfo videoInfo);
	
	
	void updatVideoInfo(VideoInfo videoInfo);
	
	
	void delVideoInfo(Integer vid);

	//查询用户id下的所有视频
	List<VideoInfo> getVedioAll(String acountId);


	void test();


	VideoAcitivity getVideoAcitivity();


	Integer saveVideoCompetition(VideoCompetition videoCompetition);


	VideoCompetition getVideoCompetition(Integer activityId, String acountId);


	List getMyVideoCompetitionOfAcount(String acountId);


	List<Object[]> getAllVideoCompetitionByVerifSuccess(int i);


	Object[] queryVote(Integer memberId, Integer vid, Integer date);


	Integer updataVideoVote(Integer memberId, Integer vid, Integer date);


	int delVideoCompetitionById(Integer videoCompetitionId, Integer vid);


	Object[] getVideoCompetitionByVid(Integer vid);


	int voteIsExits(Integer memberId);


	boolean verifcationVideoInfo(Integer videoInfoId, Integer id);


	int updateVideoAccess_num(Integer value, String key);


	List getOtherInfo(Integer vid);


	boolean memberId(Integer memberId);


	long getVoteNum();


	List queryVideo(String searchVal);


	Object[] getStatisticalInfo();


	List getAreaRanking(String areaId);


	Object[] getVideoInfoByVid(Integer vid);


	boolean queryOpenId(String openid);


	Object[] queryLike(String openid, String vid, int time);


	Integer updataVideoLike(String openid, String vid, int time);


	boolean isTeacher(String memberid);


	int insertVideoComment(VideoComment videoComment,String messageid);


	List<VideoComment> getVideoComtentById(Integer vid);


	void delVideoCommentById(String vid);


	String getLearnTime(String epalId,String createTime);


	int savePayRecord(int vid, int memberid, double price);


	List getTeacherID(String epalId);


	int sendInvite(List teacherId,String epalId,int vid);


	int getUnreadNum(String teacherId);


	List getScenarioEnglishVideo();


	List<Object[]> getInviteMessage(String teacherId);


	void delMessage(String messageId);


	String getTeacherName(String teacherid);


	void updataCommentStatus(int vid);


	List<Object[]> getStudentVideoShow(int i);


	int scenarioEnglishLike(String vid);


	Object[] scenarioEnglishById(String vid);


	List queryscenarioEnglish(String searchVal);


	List<Object[]> getAllVideoCompetitionByVerifSuccess(Integer pageNumber, Integer pageSize, Integer activtId,
			String search);


	Object[] videoInfoByActivity(Integer vid);


	Long getStudentFirstLearnTime(Integer studentId);


	void sendInvite(List<ClassTeacher> teachers, Integer vid);


}
