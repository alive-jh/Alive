package com.wechat.service.impl;

import com.wechat.dao.VideoDao;
import com.wechat.entity.ClassTeacher;
import com.wechat.entity.VideoAcitivity;
import com.wechat.entity.VideoComment;
import com.wechat.entity.VideoCompetition;
import com.wechat.entity.VideoInfo;
import com.wechat.service.VideoService;
import com.wechat.util.CommonUtils;

import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

	@Resource
	VideoDao videoDao;
	
	@Override
	public VideoInfo getVideoInfo(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoInfo(vid);
	}

	@Override
	public void saveVideoInfo(VideoInfo videoInfo) {
		// TODO Auto-generated method stub
		this.videoDao.saveVideoInfo(videoInfo);
	}

	@Override
	public void updatVideoInfo(VideoInfo videoInfo) {
		// TODO Auto-generated method stub
		this.videoDao.updatVideoInfo(videoInfo);
	}

	@Override
	public void delVideoInfo(Integer vid) {
		// TODO Auto-generated method stub
		this.videoDao.delVideoInfo(vid);
	}


	@Override
	public List<VideoInfo> getVedioAll(String acountId) {
		// TODO Auto-generated method stub
		return this.videoDao.getVedioAll(acountId);
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		this.videoDao.test();
	}

	@Override
	public VideoAcitivity getVideoAcitivity() {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoAcitivity();
	}

	@Override
	public Integer saveVideoCompetition(VideoCompetition videoCompetition) {
		// TODO Auto-generated method stub
		return this.videoDao.saveVideoCompetition(videoCompetition);
	}

	@Override
	public VideoCompetition getVideoCompetition(Integer activityId,
			String acountId) {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoCompetition(activityId,acountId);
	}

	@Override
	public List getMyVideoCompetitionOfAcount(String acountId) {
		
		return this.videoDao.getMyVideoCompetitionOfAcount(acountId);
	}

	@Override
	public List<Object[]> getAllVideoCompetitionByVerifSuccess(int i) {
		// TODO Auto-generated method stub
		return this.videoDao.getAllVideoCompetitionByVerifSuccess(i);
	}

	@Override
	public Object[] queryVote(Integer memberId, Integer vid, Integer date) {
		// TODO Auto-generated method stub
		return this.videoDao.queryVote(memberId,vid,date);
	}

	@Override
	public Integer updataVideoVote(Integer memberId, Integer vid, Integer date) {
		// TODO Auto-generated method stub
		return this.videoDao.updataVideoVote(memberId,vid,date);
	}

	@Override
	@Transactional
	public int delVideoCompetitionById(Integer videoCompetitionId, Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.delVideoCompetitionById(videoCompetitionId,vid);
		
	}

	@Override
	public Object[] getVideoCompetitionByVid(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoCompetitionByVid(vid);
	}

	@Override
	public int voteIsExits(Integer memberId) {
		// TODO Auto-generated method stub
		return this.videoDao.voteIsExits(memberId);
	}

	@Override
	public boolean verifcationVideoInfo(Integer videoInfoId, Integer id) {
		// TODO Auto-generated method stub
		return this.videoDao.verifcationVideoInfo(videoInfoId,id);
	}

	@Override
	public int updateVideoAccess_num(Integer value, String key) {
		// TODO Auto-generated method stub
		return this.videoDao.updateVideoAccess_num(value,key);
	}

	@Override
	public List getOtherInfo(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.getOtherInfo(vid);
	}

	@Override
	public boolean queryMemberId(Integer memberId) {
		// TODO Auto-generated method stub
		return this.videoDao.memberId(memberId);
	}

	@Override
	public long getVoteNum() {
		// TODO Auto-generated method stub
		return this.videoDao.getVoteNum();
	}

	@Override
	public List queryVideo(String searchVal) {
		// TODO Auto-generated method stub
		return this.videoDao.queryVideo(searchVal);
	}

	@Override
	public Object[] getStatisticalInfo() {
		// TODO Auto-generated method stub
		return this.videoDao.getStatisticalInfo();
	}

	@Override
	public List getAreaRanking(String areaId) {
		// TODO Auto-generated method stub
		return this.videoDao.getAreaRanking(areaId);
	}

	@Override
	public Object[] getVideoInfoByVid(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoInfoByVid(vid);
	}

	@Override
	public boolean queryOpenId(String openid) {
		// TODO Auto-generated method stub
		return this.videoDao.queryOpenId(openid);
	}

	@Override
	public Object[] queryLike(String openid, String vid, int time) {
		// TODO Auto-generated method stub
		return this.videoDao.queryLike(openid,vid,time);
	}

	@Override
	public Integer updataVideoLike(String openid, String vid, int time) {
		// TODO Auto-generated method stub
		return this.videoDao.updataVideoLike(openid,vid,time);
	}

	@Override
	public boolean isTeacher(String memberid) {
		// TODO Auto-generated method stub
		return this.videoDao.isTeacher(memberid);
	}

	@Override
	public int insertVideoComment(VideoComment videoComment, String messageid) {
		// TODO Auto-generated method stub
		return this.videoDao.insertVideoComment(videoComment,messageid);
	}

	@Override
	public List<VideoComment> getVideoComtentById(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.getVideoComtentById(vid);
	}

	@Override
	public void delVideoCommentById(String vid) {
		// TODO Auto-generated method stub
		this.videoDao.delVideoCommentById(vid);
	}

	@Override
	public int getLearnTime(String epalId,String createTime) {
		// TODO Auto-generated method stub
		 String completeTime = this.videoDao.getLearnTime(epalId,createTime);
		 int useDays=0;
		 if(null!=completeTime && !"".equals(completeTime)){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		 
		 Calendar calendar = Calendar.getInstance();
		 try {
			 calendar.setTime(sdf.parse(completeTime));
			 int days1 = calendar.get(calendar.DAY_OF_YEAR);
			 calendar.setTime(sdf.parse(createTime));
			 int days2 = calendar.get(calendar.DAY_OF_YEAR);
			 useDays = days2-days1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		 return useDays;
	}
	
	@Test
	public void test1(){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		//System.out.println(calendar.get(calendar.DAY_OF_YEAR));
	}

	@Override
	public int savePayRecord(int vid, int memberid, double price) {
		// TODO Auto-generated method stub
		return this.videoDao.savePayRecord(vid,memberid,price);
	}

	@Override
	public List getTeacherID(String epalId) {
		// TODO Auto-generated method stub
		return this.videoDao.getTeacherID(epalId);
	}

	@Override
	public int sendInvite(List teacherId,String epalId,int vid) {
		// TODO Auto-generated method stub
		return this.videoDao.sendInvite(teacherId,epalId,vid);
	}

	@Override
	public int getUnreadNum(String teacherId) {
		// TODO Auto-generated method stub
		return this.videoDao.getUnreadNum(teacherId);
	}

	@Override
	public List getScenarioEnglishVideo() {
		// TODO Auto-generated method stub
		return this.videoDao.getScenarioEnglishVideo();
	}

	@Override
	public List<Object[]> getInviteMessage(String teacherId) {
		// TODO Auto-generated method stub
		return this.videoDao.getInviteMessage(teacherId);
	}

	@Override
	public void delMessage(String messageId) {
		// TODO Auto-generated method stub
		this.videoDao.delMessage(messageId);
	}

	@Override
	public String getTeacherName(String teacherid) {
		// TODO Auto-generated method stub
		return this.videoDao.getTeacherName(teacherid);
	}

	@Override
	public void updataCommentStatus(int vid) {
		// TODO Auto-generated method stub
		this.videoDao.updataCommentStatus(vid);
	}

	@Override
	public List<Object[]> getStudentVideoShow(int i) {
		// TODO Auto-generated method stub
		return this.videoDao.getStudentVideoShow(i);
	}


	@Override
	public int scenarioEnglishLike(String vid) {
		// TODO Auto-generated method stub
		return this.videoDao.scenarioEnglishLike(vid);
	}

	@Override
	public Object[] scenarioEnglishById(String vid) {
		// TODO Auto-generated method stub
		return this.videoDao.scenarioEnglishById(vid);
	}

	@Override
	public List queryscenarioEnglish(String searchVal) {
		// TODO Auto-generated method stub
		return this.videoDao.queryscenarioEnglish(searchVal);
	}

	@Override
	public List<Object[]> getAllVideoCompetitionByVerifSuccess(Integer pageNumber, Integer pageSize, Integer activtId,
			String search) {
		// TODO Auto-generated method stub
		return this.videoDao.getAllVideoCompetitionByVerifSuccess(pageNumber, pageSize, activtId,
				search);
	}

	@Override
	public Object[] videoInfoByActivity(Integer vid) {
		// TODO Auto-generated method stub
		return this.videoDao.videoInfoByActivity(vid);
	}

	@Override
	public String getStudentLearnTime(Integer studentId) {
		// TODO Auto-generated method stub
		
		Long firstLearnTimeMillis = this.videoDao.getStudentFirstLearnTime(studentId);
		
		String result = "";
		if(firstLearnTimeMillis!=null){
			
			long now = System.currentTimeMillis();
			int months = CommonUtils.getMonths(now, firstLearnTimeMillis);
			int days = CommonUtils.getDayOfMomth(now);
			
			if(months>11){
				result += months/12+"年-"+(months%12)+"个月-"+days+"天";
			}else{
				result += 0+"年-"+months+"个月-"+days+"天";
			}
			
		}
		return result;
	}
	
	public static void main(String[] args) {
		
		Long firstLearnTimeMillis = 1499940156000L;
		Calendar calendar = Calendar.getInstance();
		int months = CommonUtils.getMonths(System.currentTimeMillis(), firstLearnTimeMillis);
		//System.out.println(months/12+"-"+months%12+"-"+calendar.get(Calendar.DAY_OF_MONTH));
		
	}

	@Override
	public void sendInvite(List<ClassTeacher> teachers, Integer vid) {
		this.videoDao.sendInvite(teachers, vid);
	}
	
	


}
