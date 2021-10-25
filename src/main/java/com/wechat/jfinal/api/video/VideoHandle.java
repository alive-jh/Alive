package com.wechat.jfinal.api.video;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.i18n.Res;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.redis.Redis;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.Member;
import com.wechat.jfinal.model.VideoCollection;
import com.wechat.jfinal.model.VideoComment;
import com.wechat.jfinal.model.VideoCommentMiniapp;
import com.wechat.jfinal.model.VideoCompetition;
import com.wechat.jfinal.model.VideoInfo;
import com.wechat.jfinal.model.VideoLike;
import com.wechat.jfinal.model.VideoLikeRecoad;
import com.wechat.jfinal.model.VideoMemberVoteCount;
import com.wechat.jfinal.model.VideoVote;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class VideoHandle extends Controller {

	// 通过学生ID获取学生所有视频列表
	public void getAllVedioByStudentId() {
		int studentId = getParaToInt("studentId", 0);
		int currentPage = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		Result.ok(VideoInfo.dao.paginate(currentPage, pageSize, "select *", "from video_info where student_id=?",
				studentId), this);
		return;
	}

	// 获取比赛视频列表
	public void getMatchVideo() {
		int studentId = getParaToInt("studentId", 0);
		int currentPage = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);
		VideoInfo videoInfo = VideoInfo.dao.findFirst("select * from video_info where student_id = ?", studentId);
		JSONArray jsonArray = new JSONArray();

		if (videoInfo == null) {
			Result.ok(jsonArray, this);
			return;
		}

		List<VideoCompetition> list = VideoCompetition.dao.find("select * from video_competition where acoutn_id = ?",
				videoInfo.getAcountId());

		for (VideoCompetition videoCompetition : list) {
			JSONObject json = Result.toJson(videoCompetition.toRecord().getColumns());
			json.put("videoInfo", Result.toJson(videoCompetition.getVideoInfo().toRecord().getColumns()));
			json.put("videoAcitivity", Result.toJson(videoCompetition.getVideoActivity().toRecord().getColumns()));
			jsonArray.add(json);
		}

		Result.ok(jsonArray, this);
	}

	/**
	 * 学习视频比赛投票接口
	 * @param memberId 会员id 必填
	 * @param vId 视频id 必填
	 */
	public void vote() {
		
		Integer memberId = getParaToInt("memberId",0);
		Integer vId = getParaToInt("vId");

		if (xx.isOneEmpty(memberId, vId)) {
			Result.error(203, "参数异常", this);
			return;
		}

		Calendar calendar = Calendar.getInstance();
		
		//获取投票日期 ，一年的第几天
		int today = calendar.get(Calendar.DAY_OF_YEAR);
		
		//设置投票截止时间
		calendar.set(2018, 5, 20, 00, 00, 00);

		//判断投票是否截止
		if (System.currentTimeMillis() <= calendar.getTimeInMillis()) {

			//获取会员信息
			Member member = Member.dao.findById(memberId);
			
			if (member != null) {

				//获取当天的投票记录
				VideoMemberVoteCount videoMemberVoteCount = VideoMemberVoteCount.dao.findFirst(
						"select * from video_member_vote_count where member_id = ? and day = ?", memberId, today);

				//初始化投票记录
				if (videoMemberVoteCount == null) {
					videoMemberVoteCount = new VideoMemberVoteCount();
					videoMemberVoteCount.setMemberId(memberId);
					videoMemberVoteCount.setCount(0);
					videoMemberVoteCount.setDay(today).save();
				}

				//判断当天投票限制次数
				if (videoMemberVoteCount.getCount() <= 2) {

					//获取视频投票记录
					VideoVote videoVote = VideoVote.dao.findFirst(
							"select * from video_vote where member_id = ? and vid = ? and	vote_date = ?", memberId,
							vId, today);

					
					if (videoVote == null) {
						
						//创建投票记录
						videoVote = new VideoVote();
						videoVote.setMemberId(memberId);
						videoVote.setVid(vId);
						videoVote.setVoteDate(today).save();
						
						//投票
						Db.update("update video_info set vote = vote+1 where id = ?", vId);
						
						//判断是不是当天第一次投票，如果是则存到redis，之后每次投票都给这个人加一票
						if(videoMemberVoteCount.getCount()==0){
							//存redis
							Redis.use().setex("firstVid"+memberId, 60*60*60*24, vId);
						}else{
							//给第一个投票的人加一票
							String str = Redis.use().get("firstVid"+memberId);
							Db.update("update video_info set vote = vote+1 where id = ?", str);
						}
						
						videoMemberVoteCount.setCount(videoMemberVoteCount.getCount() + 1).update();

						JSONObject json = new JSONObject();
						json.put("count", 3-videoMemberVoteCount.getCount());
						Result.ok(json,this);

					} else {
						Result.error(20503, "你今天已经给他投过票了呢，也给其他小朋友投一投", this);
						return;
					}

				} else {
					Result.error(20504, "你今天的投票机会已经用完了，明天再来吧", this);
					return;
				}

			} else {
				Result.error(20502, "非法投票", this);
				return;
			}

		} else {
			Result.error(20501, "投票已经截止", this);
			return;
		}

	}
	
	/**
	 * 随机获取参赛视频
	 */
	public void randomVideos(){
		
		//随机取2条数据
		int count = 2;
		
		//id为8的活动
		int activityId = 8;
		
		StringBuffer sql = new StringBuffer("SELECT b.id AS student_id,b.`name` AS student_name,d.class_grades_name,a.id AS video_id,a.pic_url,a.v_url,a.");
		sql.append("vote FROM video_info a,class_student b,video_competition c,class_grades d,class_grades_rela e WHERE a.");
		sql.append("student_id = b.id AND a.id = c.video_info_id AND b.id = e.class_student_id AND d.id = e.class_grades_id AND ");
		sql.append("c.video_activity_id = ? GROUP BY b.id ORDER BY RAND() LIMIT ?");
		
		List<Record> data = Db.find(sql.toString(),activityId,count);
		
		JSONObject json = new JSONObject();
		json.put("list", Result.makeupList(data));
		
		Result.ok(json,this);
		
	}

	/**
	 * 视频投票记录
	 */
	public void voteHistory(){
		
		Integer memberId = getParaToInt("memberId",0);
		
		if(memberId==null){
			Result.error(203, this);
			return;
		}
		
		StringBuffer sql = new StringBuffer("SELECT DATE_FORMAT(c.update_time,'%Y-%m-%d') AS vote_time, GROUP_CONCAT(a.id,'@',b.`name`) AS infos FROM ");
		sql.append("video_info a,class_student b,video_vote c WHERE a.student_id = b.id AND a.id = c.vid AND c.member_id = ");
		sql.append("? GROUP BY c.vote_date ORDER BY c.update_time DESC");
		
		List<Record> list = Db.find(sql.toString(),memberId);
		
		for(Record record : list){
			String[] infos = record.getStr("infos").split(",");
			
			List<Record> _infos = new ArrayList<Record>(infos.length);
			
			for(String info : infos){
				String[] temp = info.split("@");
				Record _info = new Record(); 
				_info.set("vid", temp[0]);
				_info.set("name", temp[1]);
				_infos.add(_info);
			}
			
			record.set("voteList", Result.makeupList(_infos));
			record.remove("infos");
		}
		
		JSONObject json = new JSONObject();
		json.put("list", Result.makeupList(list));
		Result.ok(json,this);
		
		
		
	}

	/**
	 * 获取所有视频
	 */
	public void videoAll(){
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		Integer memberId = getParaToInt("memberId");
		
		if (memberId==null) {
			Result.error(203, this);
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM video_info a LEFT JOIN video_like_recoad b ON a.id = b.v_id AND b.member_id = ?");
		sql.append(" LEFT JOIN video_collection c ON a.id = c.v_id AND c.member_id = ? LEFT JOIN video_like_recoad v ON");
		sql.append(" a.id = v.v_id WHERE a.`status` > -1 GROUP BY a.id ORDER BY a.create_time DESC");
		
		Page<Record> page = Db.paginate(pageNumber, pageSize
										,"SELECT a.*, IFNULL(b.`status`, 0) AS is_like , IFNULL(c.`status`, 0) AS is_collection , COUNT(v.id) AS like_num"
										,sql.toString()
										,memberId,memberId);
		
		Result.ok(page,this);
		
	}
	
	/**
	 * 获取推荐视频列表
	 */
	public void recommendVideoList(){
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		Integer memberId = getParaToInt("memberId");
		
		if(memberId==null){
			Result.error(203, this);
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM video_info a LEFT JOIN video_like_recoad b ON a.id = b.v_id AND b.member_id = ?");
		sql.append(" LEFT JOIN video_collection c ON a.id = c.v_id AND c.member_id = ? LEFT JOIN video_like_recoad v ON");
		sql.append(" a.id = v.v_id WHERE a.is_quote = 1 AND a.`status` > -1 GROUP BY a.id ORDER BY a.create_time DESC");
		
		Page<Record> page = Db.paginate(pageNumber, pageSize
				,"SELECT a.*, IFNULL(b.`status`, 0) AS is_like , IFNULL(c.`status`, 0) AS is_collection , COUNT(v.id) AS like_num"
				,sql.toString()
				,memberId,memberId);
		
		Result.ok(page,this);
		
	}
	
	/**
	 * 根据学习时间获取视频列表
	 * @param learnMonth 学习了几个月
	 */
	public void videoListWithLearnMonth(){
		
		Integer learnMonth = getParaToInt("learnMonth");
		Integer memberId = getParaToInt("memberId");
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		
		if(xx.isOneEmpty(learnMonth,memberId)){
			Result.error(203, this);
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("FROM video_info a LEFT JOIN video_like_recoad b ON a.id = b.v_id AND b.member_id = ? LEFT JOIN video_collection c ON a.id = c.v_id AND c.member_id = ?");
		sql.append(" LEFT JOIN video_like_recoad v ON a.id = v.v_id WHERE ABS(TIMESTAMPDIFF(MONTH, DATE_FORMAT(( SELECT complete_time FROM");
		sql.append(" class_course_record WHERE student_id = a.student_id ORDER BY complete_time ASC LIMIT 1 ), '%Y-%m-%d'), DATE_FORMAT(a.create_time,");
		sql.append(" '%Y-%m-%d'))) = ? AND a.`status` > -1 GROUP BY a.id ORDER BY a.create_time DESC");
		
		Page<Record> page = Db.paginate(pageNumber, pageSize
				, "SELECT a.*, IFNULL(b.`status`, 0) AS is_like , IFNULL(c.`status`, 0) AS is_collection , COUNT(v.id) AS like_num"
				,sql.toString()
				,memberId,memberId,learnMonth);
		
		Result.ok(page,this);
		
	}
	
	/**
	 * 视频点赞
	 * @param memberId 
	 * @param openId 
	 * @param vId 视频id
	 */ 
	public void addLike(){
		
		Integer memberId = getParaToInt("memberId");
		Integer vId = getParaToInt("vId");
		Integer status = getParaToInt("status",1);
		
		if(xx.isOneEmpty(memberId,vId)){
			Result.error(203, this);
			return;
		}
		
		String sql = "INSERT INTO video_like_recoad (v_id,member_id,status) VALUES (?,?,?) ON DUPLICATE KEY UPDATE status=?";
		
		Db.update(sql,vId,memberId,status,status);
		
		Result.ok(this);
		
	}
	
	/**
	 * 收藏视频
	 */
	public void addCollection(){
		Integer vId = getParaToInt("vId");
		Integer memberId = getParaToInt("memberId");
		Integer status = getParaToInt("status",1);
		
		if(xx.isOneEmpty(vId,memberId)){
			Result.error(203, this);
			return;
		}
		
		String sql = "INSERT INTO video_collection (v_id,member_id,status) VALUES (?,?,?) ON DUPLICATE KEY UPDATE status=?";
		Db.update(sql,vId,memberId,status,status);
		
		Result.ok(this);
	}
	
	
	/**
	 * 视频添加评论
	 */
	public void addComment(){
		
		Integer vId = getParaToInt("vId");
		String content = getPara("content");
		Integer memberId = getParaToInt("memberId");
		
		if(xx.isOneEmpty(vId,memberId,content)){
			Result.error(203, this);
			return;
		}
		
		VideoCommentMiniapp videoCommentMiniapp = new VideoCommentMiniapp();
		videoCommentMiniapp.setVideoId(vId);
		videoCommentMiniapp.setContent(content);
		videoCommentMiniapp.setMemberId(memberId).save();
		
		Result.ok(this);
		
	}
	
	/**
	 * 获取视频详情包括学生信息
	 */
	public void getVideoWithStrudentInfo(){
		
		Integer vid = getParaToInt("vid");
		
		if(vid==null){
			Result.error(203, this);
			return;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.*, b.`name` AS student_name, GROUP_CONCAT(d.class_grades_name) AS class_grader_names FROM video_info a JOIN");
		sql.append(" class_student b ON a.student_id = b.id JOIN class_grades_rela c ON b.id = c.class_student_id JOIN class_grades d ON");
		sql.append(" c.class_grades_id = d.id WHERE a.id = ?");
		
		Record videoInfo = Db.findFirst(sql.toString(),vid);
		
		Result.ok(videoInfo,this);
		
	}
	
	
	/**
	 * 获取学生所有视频
	 */
	public void studentVideos(){
		
		Integer studentId = getParaToInt("id");
		
		int pageNumber = getParaToInt("pageNumber",1);
		int pageSize = getParaToInt("pageSize",10);
		
		if(studentId==null){
			Result.error(203, this);
			return;
		}
		
		Page<Record> page = Db.paginate(pageNumber, pageSize,"select * ","from video_info where student_id = ? and `status` = 0",studentId);
		
		Result.ok(page,this);
		
	}
	
	/**
	 * 视频收藏记录
	 */
	public void videoCollectionRecord(){
		
		Integer vId = getParaToInt("vId");
		
		if(vId == null){
			Result.error(203, this);
			return;
		}
		
		List<Record> records  = Db.find("SELECT b.nickname,a.create_time AS collection_date FROM video_collection a LEFT JOIN member b ON a.member_id = b.id WHERE a.v_id = ?",vId);
		
		Result.ok(records,this);
		
	}
	
	/**
	 * 视频点赞记录
	 */
	public void videoLikeRecord(){
		
		Integer vId = getParaToInt("vId");
		
		if(vId == null){
			Result.error(203, this);
			return;
		}
		
		List<Record> records  = Db.find("SELECT b.nickname,a.create_time AS like_date FROM video_like_recoad a LEFT JOIN member b ON a.member_id = b.id WHERE a.v_id = ?",vId);
		
		Result.ok(records,this);
		
	}
	
	/**
	 * 视频评论列表
	 */
	public void videoCommentRecord(){
		
		Integer vId = getParaToInt("vId");
		
		if(vId == null){
			Result.error(203, this);
			return;
		}
		
		List<Record> records  = Db.find("SELECT b.headimgurl,b.nickname,a.content,a.create_time FROM video_comment_miniapp a LEFT JOIN member b ON a.member_id = b.id WHERE a.video_id = ?",vId);
		
		Result.ok(records,this);
		
	}
	
	public void videoStudentInfo(){
		
		Integer videoId = getParaToInt("vid");
		
		if(videoId==null){
			Result.error(203, this);
			return;
		}
		
		VideoInfo videoInfo  = VideoInfo.dao.findById(videoId);
		
		JSONObject json = new JSONObject();
		json.put("videoInfo", videoInfo.toJson());
		
		Result.ok(json,this);
		
	}
	
	
	public static void main(String[] args) {
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM video_info a LEFT JOIN video_like_recoad b ON a.id = b.v_id AND b.member_id = ?");
		sql.append(" LEFT JOIN video_collection c ON a.id = c.v_id AND c.member_id = ? LEFT JOIN video_like_recoad v ON");
		sql.append(" a.id = v.v_id WHERE 1=1 GROUP BY a.id ORDER BY a.create_time DESC");
		
		//System.out.println(sql.toString());
	}
}
