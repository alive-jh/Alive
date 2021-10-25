package com.wechat.jfinal.model;

import com.jfinal.plugin.activerecord.Db;
import com.wechat.jfinal.model.base.BaseRandComment;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class RandComment extends BaseRandComment<RandComment> {
	public static final RandComment dao = new RandComment().dao();

	public List<RandComment> findByCommentId(Integer momentId) {
		return find("select a.*,b.avatar,b.`name` from rand_comment a,class_student b where moment_id=? and a.student_id=b.id order by a.createDate desc",momentId);
	}

	public int findMommentCount(Integer momentId) {
		return Db.findFirst("select count(*) as count from rand_comment where moment_id=?",momentId).getInt("count");
	}


	public RandComment findSaveInfo(Integer studentId) {
		return  findFirst("select b.avatar,b.`name` from class_student b where  b.id=?",studentId);
	}

	public List<RandComment> findTeacher(Integer momentId) {
		return  find("select b.cover,b.`name`,a.* from rand_comment a,class_teacher b  where a.teacher_id=b.id AND a.teacher_id is not null AND moment_id=137  order by a.createDate desc");
	}
}
