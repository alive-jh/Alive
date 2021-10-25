package com.wechat.dao.impl;

import com.wechat.dao.VideoDao;
import com.wechat.entity.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VideoDaoImpl extends BaseDaoImpl<VideoInfo> implements VideoDao {

	@Override
	public VideoInfo getVideoInfo(Integer vid) {

		return (VideoInfo) this.getEntity(VideoInfo.class, vid);
	}

	// 根据账户id和活动id查参赛数据
	public VideoCompetition getVideoCompetition(Integer activityId, String acountId) {
		/*
		 * String hql = "from VideoCompetition where acoutnId =  "
		 * +acountId+" AND videoActivityId="+activityId; List list =
		 * this.executeHQL(hql); if(list.size()!=0){ return (VideoCompetition)
		 * list.get(0); }else{ return null; }
		 */
		String sql = "select id,video_info_id from video_competition where video_activity_id=" + activityId
				+ " and acoutn_id=" + acountId;
		List list = this.executeSQL(sql);
		if (list.size() != 0) {
			VideoCompetition temp = new VideoCompetition();
			Object[] obj = (Object[]) list.get(0);
			temp.setId((Integer) obj[0]);
			temp.setVideoInfoId((Integer) obj[1]);
			return temp;
		} else {
			return null;
		}

	}

	// 根据账户id查参赛数据
	public VideoCompetition getVideoCompetitionOfAcount(String acountId) {

		String hql = "from VideoCompetition where acoutnId = " + acountId;
		List list = this.executeHQL(hql);
		if (list.size() != 0) {
			return (VideoCompetition) list.get(0);
		} else {
			return null;
		}
	}

	// 根据账户id查参赛数据列表
	public List getMyVideoCompetitionOfAcount(String acountId) {

		// String hql = "from VideoCompetition a, VideoInfo b ,VideoAcitivity c
		// where a.videoInfoId = b.Id and a.videoActivityId=c.Id and a.acoutnId
		// = "+acountId;
		String hql = "from VideoCompetition where acoutnId =  " + acountId;
		List list = this.executeHQL(hql);
		if (list.size() != 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public void saveVideoInfo(VideoInfo videoInfo) {

		Date date = new Date();
		videoInfo.setModifyTime(date);
		videoInfo.setCreateTime(date);
		videoInfo.setVote(0);
		videoInfo.setAdmission(0);
		videoInfo.setAccessNum(0);
		this.saveOrUpdate(videoInfo);
		VideoScore videoScore = new VideoScore();
		videoScore.setVideoInfoId(videoInfo.getId());
		this.saveOrUpdate(videoScore);
	}

	@Override
	public void updatVideoInfo(VideoInfo videoInfo) {
		VideoInfo v2 = this.getVideoInfo(videoInfo.getId());
		if (videoInfo.getvTitle() != null) {
			v2.setvTitle(videoInfo.getvTitle());
		}
		if (videoInfo.getAdmission() != null) {
			v2.setAdmission(videoInfo.getAdmission());
		}
		v2.setModifyTime(new Date());
		this.update(v2);

	}

	@Override
	public void delVideoInfo(Integer vid) {

		this.executeUpdateSQL("update video_info set status = -1 where id=" + vid);
	}

	@Override
	public List<VideoInfo> getVedioAll(String acountId) {

		StringBuffer hql = new StringBuffer();
		hql.append("from VideoInfo where acountId=" + acountId + " and status=0 order by createTime desc");
		List<VideoInfo> list = this.executeHQL(hql.toString());
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public VideoAcitivity getVideoAcitivity() {
		return (VideoAcitivity) this.executeHQL("from VideoAcitivity where status = 1").get(0);
	}

	@Override
	public Integer saveVideoCompetition(VideoCompetition videoCompetition) {

		VideoCompetition v1 = this.getVideoCompetition(videoCompetition.getVideoActivityId(),
				videoCompetition.getAcoutnId()); 
		if (null != v1) {
			try {
				this.updatVideoInfo(new VideoInfo(v1.getVideoInfoId(), null, 0));
			} catch (NullPointerException e) {
				e.printStackTrace();
				this.delVideoCompetition(v1.getId());
				return 0;
			}
			;

			Integer isAdmission = v1.getId();
			try {
				StringBuffer sql = new StringBuffer();
				sql.append("UPDATE video_competition set video_info_id = " + videoCompetition.getVideoInfoId());
				sql.append(",verify_status=0");
				sql.append(" where id=" + isAdmission);
				this.executeUpdateSQL(sql.toString());
				return isAdmission;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}

		} else {
			this.save(videoCompetition);
			return videoCompetition.getId();
		}

	}

	// 获取所有审核成功的视频
	public List<Object[]> getAllVideoCompetitionByVerifSuccess(int i) {
		i = i * 10;
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT video_activity.activity_name,video_info.*,class_student.`name`,COUNT(rowNum) FROM video_competition,video_info,video_activity,class_student,");
		sql.append(
				"(SELECT (@rowNum \\:= @rowNum + 1) rowNum,t.* FROM (SELECT @rowNum \\:= 0,a.`acount_id` FROM video_info a WHERE 1=1 GROUP BY a.`acount_id` ) t) d");
		sql.append(" WHERE video_info.`id` IN");
		sql.append(" (select a.`video_info_id` FROM video_competition a WHERE a.`verify_status` = 1)");
		sql.append(" AND video_info.`epal_id`=class_student.`epal_id`");
		sql.append(
				" AND video_competition.`id`= video_info.`admission` AND video_competition.video_activity_id=video_activity.`id` GROUP BY video_competition.id ORDER BY video_info.`vote` DESC limit "
						+ i + ",10");
		List<Object[]> list = this.executeSQL(sql.toString());
		return list;

	}
	/*
	 * //获取所有审核成功的视频 public List<Object[]>
	 * getAllVideoCompetitionByVerifSuccess(){ StringBuffer sql = new
	 * StringBuffer(); sql.
	 * append("SELECT video_activity.activity_name,video_info.* FROM video_competition,video_info,video_activity"
	 * ); sql.append(" WHERE video_info.`id` IN"); sql.
	 * append(" (select a.`video_info_id` FROM video_competition a WHERE a.`verify_status` = 1)"
	 * ); sql.
	 * append(" AND video_competition.`id`= video_info.`admission` AND video_competition.video_activity_id=video_activity.`id` GROUP BY video_competition.id;"
	 * ); //System.out.println(sql.toString()); List<Object[]> list =
	 * this.executeSQL(sql.toString()); return list; }
	 */

	@Override
	public Object[] queryVote(Integer memberId, Integer vid, Integer date) {

		StringBuffer sql = new StringBuffer();
		sql.append("select * from video_vote where member_id=" + memberId);
		sql.append(" and vid=" + vid);
		sql.append(" and vote_date=" + date);
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return (Object[]) list.get(0);
		}
		return null;
	}

	@Override
	public Integer updataVideoVote(Integer memberId, Integer vid, Integer date) {
		String sql = "insert into video_vote (member_id,vid,vote_date) values (" + memberId + "," + vid + "," + date
				+ ")";
		String sql2 = "update video_info set vote=vote+1 where id=" + vid;
		try {
			this.executeUpdateSQL(sql);
			this.executeUpdateSQL(sql2);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int delVideoCompetitionById(Integer videoCompetitionId, Integer vid) {
		/*
		 * String sql2 =
		 * "DELETE FROM video_competition WHERE id="+videoCompetitionId; String
		 * sql = "UPDATE video_info SET vote=0,admission=0 WHERE id="+vid; int
		 * status=0; try{ status=this.createSQLQuery(sql).executeUpdate();
		 * if(status==1){ this.createSQLQuery(sql2).executeUpdate(); return 1;
		 * }else{ return 0; } }catch(Exception e){ e.printStackTrace(); return
		 * 0; }
		 */

		String hql = "DELETE VideoCompetition WHERE Id = " + videoCompetitionId;
		int status = 0;
		try {
			VideoInfo v1 = this.getVideoInfo(vid);
			v1.setAdmission(0);
			this.updatVideoInfo(v1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		status = this.getSession().createQuery(hql).executeUpdate();
		if (status == 1) {
			return 1;
		} else {
			return 0;
		}

	}

	public int delVideoCompetition(Integer videoCompetitionId) {
		String sql = "DELETE FROM video_competition WHERE id=" + videoCompetitionId;
		return this.createSQLQuery(sql).executeUpdate();
	}

	@Override
	public Object[] getVideoCompetitionByVid(Integer vid) {
		
		Object[] videoInfo = null;
		
		int activity = 8;
		
		String sql = "SELECT a.id AS video_id, a.v_url, a.pic_url, a.student_id, a.vote, c.id"
				+ " AS activity_id, c.activity_name, d.`name` AS student_name FROM video_info a,"
				+ " video_competition b, video_activity c, class_student d WHERE a.id = b.video_info_id"
				+ " AND b.video_activity_id = c.id AND a.student_id = d.id AND c.id = ? AND a.id = ?";
		
		List<Object[]> result = this.createSQLQuery(sql, activity,vid).list();
		
		if(result != null && result.size()!=0){
			videoInfo = result.get(0);
		}
		
		return videoInfo;
		/*StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT video_activity.activity_name,video_info.*,class_student.`name` FROM video_competition,video_info,video_activity,class_student");
		sql.append(" WHERE video_info.`id`=" + vid);
		sql.append(" AND video_info.`epal_id`=class_student.`epal_id`");
		sql.append(
				" AND video_competition.`id`= video_info.`admission` AND video_competition.video_activity_id=video_activity.`id` GROUP BY video_competition.id ORDER BY video_info.`vote` DESC");
		
		//System.out.println(sql.toString());
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return (Object[]) list.get(0);
		} else {
			return null;
		}*/
	}

	// 判断是不是第一次投票
	@Override
	public int voteIsExits(Integer memberId) {
		String sql = "select * from video_vote where member_id=" + memberId;
		List list = this.createSQLQuery(sql).list();
		if (list.size() != 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public void test() {

	}

	@Override
	public boolean verifcationVideoInfo(Integer videoInfoId, Integer id) {
		String sql = "select * from video_info where id = " + videoInfoId;
		List list = this.executeSQL(sql);
		if (list.size() != 0) {
			return true;
		} else {
			this.delVideoCompetition(id);
			return false;
		}

	}

	@Override
	public int updateVideoAccess_num(Integer value, String key) {

		return this.getSession()
				.createSQLQuery(
						"update video_info set `access_num`=`access_num`+" + value + " where v_url = '" + key + "'")
				.executeUpdate();

	}

	@Override
	public List getOtherInfo(Integer vid) {

		List list = this.executeSQL(
				"SELECT class_grades_name FROM class_grades WHERE id IN (SELECT class_grades_id FROM class_grades_rela WHERE class_student_id = ANY(SELECT id FROM class_student WHERE epal_id=(SELECT epal_id FROM video_info WHERE id="
						+ vid + ")))");
		return list;

	}

	@Override
	public boolean memberId(Integer memberId) {
		String sql = "select * from member where id=" + memberId;
		List list = this.executeSQL(sql);
		if (list == null || list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public long getVoteNum() {
		String sql = "select sum(vote) from video_info";
		return Long.parseLong(this.executeSQL(sql).get(0).toString());
	}

	@Override
	public List queryVideo(String searchVal) {
		String sql = " SELECT a.*,b.name FROM video_info a,class_student b WHERE ((a.epal_id IN (SELECT epal_id FROM class_student WHERE NAME LIKE '%"
				+ searchVal + "%') OR a.id='+" + searchVal + "') AND admission>0) AND a.epal_id=b.epal_id";
		List list = this.executeSQL(sql);
		if (list.size() != 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Object[] getStatisticalInfo() {
		String sql = "select count(*) as epal_num,SUM(vote) as vote_num from video_competition LEFT JOIN video_info ON video_competition.video_info_id=video_info.id where video_activity_id=8 AND verify_status = 1";
		return (Object[]) this.executeSQL(sql).get(0);
	}

	@Override
	public List getAreaRanking(String areaId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT a.*, b.`name`, d.`class_grades_name` FROM video_info a, class_student b, class_grades_rela c, class_grades d WHERE ");
		sql.append(
				"a.epal_id IN (SELECT epal_id FROM class_student WHERE id IN (SELECT class_student_id FROM class_grades_rela WHERE class_grades_id ");
		sql.append("IN (SELECT class_grades_id FROM user_to_class_grades WHERE user_id =" + areaId
				+ "))) AND admission > 0 AND a.`epal_id` = b.`epal_id` AND ");
		sql.append(
				"b.`id` = c.`class_student_id` AND c.`class_grades_id` = d.`id` GROUP BY b.`id` order by a.vote desc limit 10");
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return list;
		}
		return null;
	}

	@Override
	public Object[] getVideoInfoByVid(Integer vid) {

		String sql = "select a.*,b.name from video_info a,class_student b where a.student_id = b.id and a.id = "+vid;
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return (Object[]) list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public boolean queryOpenId(String openid) {
		String sql = "select * from member where openid='" + openid + "'";
		List list = this.executeSQL(sql);
		if (list == null || list.size() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public Object[] queryLike(String openid, String vid, int time) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from video_like where openid='" + openid + "'");
		sql.append(" and vid=" + vid);
		sql.append(" and add_date=" + time);
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return (Object[]) list.get(0);
		}
		return null;
	}

	@Override
	public Integer updataVideoLike(String openid, String vid, int time) {

		String sql = "insert into video_like (openid,vid,add_date) values ('" + openid + "'," + vid + "," + time + ")";
		String sql2 = "update video_info set v_like=v_like+1 where id=" + vid;
		try {
			this.executeUpdateSQL(sql);
			this.executeUpdateSQL(sql2);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public boolean isTeacher(String memberid) {
		String sql = "select id from class_teacher where member_id=" + memberid;
		List list = this.executeSQL(sql);
		if (list.size() != 0) {
			return true;
		}
		return false;
	}

	@Override
	public int insertVideoComment(VideoComment videoComment, String messageid) {
		this.save(videoComment);
		if (messageid.equals("skip") == false) {
			this.executeUpdateSQL("update video_invite_message set status = 1 where id =  " + messageid);
		}
		return videoComment.getId();
	}

	@Override
	public List<VideoComment> getVideoComtentById(Integer vid) {

		String hql = "FROM VideoComment WHERE vid=" + vid + "ORDER BY sort DESC,creatTime DESC";
		return this.createQuery(hql).list();
	}

	@Override
	public void delVideoCommentById(String vid) {

		String sql = "DELETE FROM video_comment WHERE id = " + vid;
		this.createSQLQuery(sql).executeUpdate();
	}

	@Override
	public String getLearnTime(String epalId, String createTime) {

		String sql = "SELECT complete_time FROM class_course_record WHERE student_id = (SELECT id FROM class_student WHERE epal_id='"
				+ epalId + "') ORDER BY complete_time ASC LIMIT 1";
		List list = this.createSQLQuery(sql).list();

		if (list.size() != 0) {
			return list.get(0).toString();
		}

		return null;

	}

	@Override
	public int savePayRecord(int vid, int memberid, double price) {
		String insertPayRecord = "insert into video_pay_record (vid,member_id,price) values (" + vid + "," + memberid
				+ "," + price + ")";
		String updataVreward = "update video_info set v_reward=v_reward+" + price + " where id=" + vid;
		int status = 0;
		try {
			this.executeUpdateSQL(insertPayRecord);
			this.executeUpdateSQL(updataVreward);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public List getTeacherID(String epalId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.teacher_id FROM class_student a, class_grades_rela b, class_grades c WHERE a.epal_id = '"
				+ epalId + "' AND a.id = b.class_student_id");
		sql.append(
				" AND c.id = b.class_grades_id AND b.gradesStatus = 1 GROUP BY c.teacher_id ORDER BY c.create_time DESC");
		List list = this.executeSQL(sql.toString());
		if (list.size() != 0) {
			return list;
		}
		return null;
	}

	@Override
	public int sendInvite(List teacherId, String epalId, int vid) {

		String message = "有学生邀请你进行评分";
		String sql = "";
		for (int i = 0; i < teacherId.size(); i++) {
			sql = "insert into video_invite_message (vid,teacher_id,message,status) values (" + vid + ","
					+ teacherId.get(i).toString() + ",'" + message + "'," + 0 + ")";
			this.executeUpdateSQL(sql);
		}

		String sql2 = "update video_info set comment_status = 0 where epal_id='" + epalId + "' and id=" + vid;
		if (this.createSQLQuery(sql2).executeUpdate() == 1) {
			return 1;
		} else {
			return 0;
		}

	}

	@Override
	public int getUnreadNum(String teacherId) {

		String sql = "select count(id) from video_invite_message where status=0 and teacher_id=" + teacherId;
		List list = this.createSQLQuery(sql).list();
		if (list.size() != 0) {
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	@Override
	public List getScenarioEnglishVideo() {

		String sql = "select * from scenario_english_video where 1=1";
		return this.executeSQL(sql);
	}

	@Override
	public List<Object[]> getInviteMessage(String teacher_id) {
		String sql = "select * from video_invite_message where teacher_id=" + teacher_id
				+ " ORDER BY status asc, create_time DESC";

		List list = this.executeSQL(sql);

		return list;
	}

	@Override
	public void delMessage(String messageId) {
		this.executeUpdateSQL("DELETE FROM video_invite_message WHERE id=" + messageId);
	}

	@Override
	public String getTeacherName(String teacherid) {
		// TODO Auto-generated method stub
		return this.executeSQL("select name from class_teacher where id = " + teacherid).get(0).toString();
	}

	@Override
	public void updataCommentStatus(int vid) {
		// TODO Auto-generated method stub
		this.executeUpdateSQL("update video_info set comment_status=1 where id = " + vid);
	}

	@Override
	public List<Object[]> getStudentVideoShow(int i) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from scenario_english_video limit " + (i * 10) + ",10");
		return this.executeSQL(sql.toString());
	}

	@Override
	public int scenarioEnglishLike(String vid) {
		// TODO Auto-generated method stub
		this.executeUpdateSQL("update scenario_english_video set v_like=v_like+1 where id = " + vid);
		return 1;
	}

	@Override
	public Object[] scenarioEnglishById(String vid) {
		// TODO Auto-generated method stub
		return (Object[]) this.executeSQL("select * from scenario_english_video where id = " + vid).get(0);
	}

	@Override
	public List queryscenarioEnglish(String searchVal) {
		String sql = " select * from scenario_english_video where student_name like '%" + searchVal + "%'";
		List list = this.executeSQL(sql);
		if (list.size() != 0) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public List<Object[]> getAllVideoCompetitionByVerifSuccess(Integer pageNumber, Integer pageSize, Integer activtId,
			String search) {
		StringBuffer sql = new StringBuffer(
				"SELECT b.id AS vid, IFNULL(b.s_name,'') AS score, b.vote, b.pic_url, c.activity_name , d.`name` FROM video_competition a, video_info b,");
		sql.append(
				"video_activity c, class_student d WHERE a.video_info_id = b.id AND a.video_activity_id = c.id AND b.student_id = d.id AND ");
		sql.append("a.video_activity_id = ? AND a.verify_status = 1 ");

		if (search != null) {
			sql.append(" AND (d.`name` LIKE '%" + search + "%' or b.id = '" + search + "')");
		}

		sql.append("limit " + (pageNumber - 1) * 10 + "," + pageSize);

		return this.createSQLQuery(sql.toString(), activtId).list();
	}


	@Override
	public Object[] videoInfoByActivity(Integer vid) {
		
		Object[] videoInfo = null;
		
		int activity = 8;
		
		String sql = "SELECT a.id AS video_id, a.v_url, a.pic_url, a.student_id, a.vote, c.id"
				+ " AS activity_id, c.activity_name, d.`name` AS student_name FROM video_info a,"
				+ " video_competition b, video_activity c, class_student d WHERE a.id = b.video_info_id"
				+ " AND b.video_activity_id = c.id AND a.student_id = d.id AND c.id = ? AND a.id = ?";
		
		List<Object[]> result = this.createSQLQuery(sql, activity,vid).list();
		
		if(result != null && result.size()!=0){
			videoInfo = result.get(0);
		}
		
		return videoInfo;
	}
	
	@Override
	public Long getStudentFirstLearnTime(Integer studentId) {
		
		String sql = "SELECT UNIX_TIMESTAMP(complete_time)*1000 AS first_complete_time FROM class_course_record WHERE student_id = ? ORDER BY complete_time ASC LIMIT 1";
		
		List<Object> result = this.createSQLQuery(sql, studentId).list();
		
		if(result!=null && result.size()>0){
			return Long.parseLong(result.get(0).toString()) ;
		}
		
		return null;
	}

	@Override
	public void sendInvite(List<ClassTeacher> teachers, Integer vid) {
		
		String message = "有学生邀请你进行评分";
		
		String sql = "";
		List<String> sqls = new ArrayList<String>(teachers.size());
		
		for(ClassTeacher classTeacher : teachers){
			sql = "INSERT INTO video_invite_message (vid,teacher_id,message) VALUES ("+vid+","+classTeacher.getId()+",'"+message+"')";
			sqls.add(sql);
		}
		
		this.batchSql(sqls, sqls.size());
		
		this.executeUpdateSQL("update video_info set comment_status = 0 where id = "+vid);
		
	}
	
}
