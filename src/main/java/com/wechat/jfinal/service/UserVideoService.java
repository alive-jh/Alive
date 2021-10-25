package com.wechat.jfinal.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wechat.jfinal.model.StudentVideoShow;

import java.util.List;

public class UserVideoService {
    private static final StudentVideoShow dao = new StudentVideoShow().dao();

	public StudentVideoShow getBookShop(String vid) {
		// TODO Auto-generated method stub
		return dao.findById(Integer.parseInt(vid));
	}

	public Page getAllVideo() {
		// TODO Auto-generated method stub
		String sql = "select * from student_video_show where is_show=1";
		return Db.paginate(1, 10, sql,"");
	}

	public Record getVideoById(String vid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.pic_url,a.v_url,a.v_title,a.create_time,b.`name`,d.class_grades_name from video_info a,class_student b,class_grades_rela c,class_grades d where");
		sql.append(" a.id=? and a.student_id=b.id and b.id = c.class_student_id and c.class_grades_id = d.id");
		return Db.find(sql.toString(),vid).get(0);
	}

	public Page<Record> goodVideoByAJAX(String page) {
		// TODO Auto-generated method stub
		int pageNum = Integer.parseInt(page);
		String sql = "from student_video_show where is_show=1";
		return Db.paginate(pageNum, 10, "select *",sql);
	}

	public Page<Record> getShowStudents(int page) {
		// TODO Auto-generated method stub
		//String sql = "SELECT a.`id`,a.`name`,a.`class_grades_name`,b.pic_url FROM student_grades a,video_info b WHERE a.id=b.student_id and b.student_id IN( SELECT DISTINCT student_id FROM `video_info` WHERE is_quote = 1) GROUP BY `name` ";
		StringBuffer sql = new StringBuffer("from video_info a,class_student b,class_grades_rela c,class_grades d WHERE");
		sql.append(" a.is_quote=1 and a.student_id=b.id and a.student_id=c.class_student_id and c.class_grades_id=d.id GROUP BY student_id");
		return Db.paginate(page, 10, "select a.student_id,a.pic_url,b.`name`,d.class_grades_name ",sql.toString());
	}
	
	public List<Record> getVideosByStudentId(int stuId) {
		String sql = "select * from video_info where student_id = ? and is_quote!=-1 ORDER BY create_time DESC";
		return Db.find(sql,stuId);
	}

	public Page<Record> getPeriodVideo(int period,int page) {
		// TODO Auto-generated method stub
		int endDay=period*30;
		int startDay = 30*(period-1);
		StringBuffer sql = new StringBuffer();
		sql.append("from video_info v ,class_student s,class_grades_rela gr,class_grades g where student_id in");
		sql.append(" (select b.student_id from (select a.* from (select student_id,start_time from class_course_schedule ORDER BY start_time ASC) a");
		sql.append(" GROUP BY a.student_id) b WHERE DATEDIFF(NOW(),b.start_time) BETWEEN ? and ?)");
		sql.append(" and v.student_id=s.id and s.id=gr.class_student_id and gr.class_grades_id=g.id and v.is_quote=1 GROUP BY v.student_id ");
		return Db.paginate(page, 10, "select v.pic_url ,s.`name`,v.student_id,g.class_grades_name",sql.toString(),startDay,endDay);
	}

	public Page<Record> getShowStudentsByArea(int agentId,int page) {
		// TODO Auto-generated method stub
		//String sql = "SELECT a.`student_id`,b.`name`,b.`class_grades_name`,a.pic_url from video_info a,student_grades b where a.student_id in (select DISTINCT s_id from v_student WHERE agent_id=?) and a.is_quote=1 and a.student_id = b.id GROUP BY a.student_id limit "+(page-1)*10+",10";
		StringBuffer sql = new StringBuffer();
		sql.append("FROM video_info a, student_grades b, `user` c");
		sql.append(" WHERE a.student_id IN ( SELECT DISTINCT s_id FROM v_student WHERE agent_id = ? ) AND a.is_quote = 1 AND a.student_id = b.id");
		sql.append(" AND c.id = ? GROUP BY a.student_id");
		return Db.paginate(page, 10, "SELECT a.`student_id`, b.`name`, b.`class_grades_name`, a.pic_url, c.`name` AS area",sql.toString(),agentId,agentId);
	}

	public List<Record> getStudentVideoByStudentId(int studentId) {
		String sql = "SELECT a.*,b.name FROM video_info a JOIN class_student b ON a.student_id = b.id WHERE a.is_quote = 1 AND a.student_id = ? order by a.create_time desc";
		return Db.find(sql,studentId);
	}

	public Page<Record> testPage(int page) {
		// TODO Auto-generated method stub
		Page<Record> videoPage = Db.paginate(page, 10, "select *","from video_info");
		return videoPage;
	}

	public static void main(String[] args) {
		StringBuffer sql = new StringBuffer("select a.student_id,a.pic_url,b.`name`,d.class_grades_name from video_info a,class_student b,class_grades_rela c,class_grades d WHERE");
		sql.append(" a.is_quote=1 and a.student_id=b.id and a.student_id=c.class_student_id and c.class_grades_id=d.id GROUP BY student_id");
		//System.out.println(sql.toString());
	}
	
	
}
