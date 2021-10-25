package com.wechat.jfinal.api.classRoom;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.wechat.jfinal.common.utils.xx;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassRoomComment;
import com.wechat.jfinal.model.ClassRoomLike;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONObject;


public class Comment extends Controller{
	/**
	 * 保存课堂评论
	 * 参数：
	 * 	classRoomId：课堂ID
	 * userId：用户ID
	 * type：类型（文字，录音，）
	 * content：内容
	 * status：状态（默认待审核）
	 * imageUrls：评论图片列表
	 * 	 6、综合评分（score）
	 * 	7、老师评分（teacherScore）
	 * 	8、课堂评分（classRoomScore）
	 * 	9、是否匿名（isAnonymous）
	 * 
	 * */
	public void saveComment() {//保存评论
		
		ClassRoomComment bean = getBean(ClassRoomComment.class,"");
		if(null == bean.getId()){//判断是修改还是保存
			int classRoomId = bean.getClassRoomId();
			int userId = bean.getUserId();
			//查询该用户是否评论过某一个课程
			ClassRoomComment classRoomComment =  ClassRoomComment.dao.findFirst("select * from class_room_comment where user_id=? and class_room_id=?",userId,classRoomId);
			if(null == classRoomComment){
				bean.save();
				Result.ok(bean,this);
			}else{
				JSONObject result = new JSONObject();
				result.put("msg", "你已经评论过，不能重复评论");
				result.put("code", 210);
				Result.ok(result,this);
			}

		}else{//不支持修改评论
			Result.ok(ClassRoomComment.dao.findById(bean.getId()),this);
		}
	}
	/*
	 * 	获取课堂评论列表
	 * 	返回结果属性：
	 * 	1、评论内容
	 * 	2、评论图片
	 * 	3、评论者ID
	 * 	4、评论者昵称
	 * 	5、评论者头像
	 * 	6、综合评分（score）
	 * 	7、老师评分（teacherScore）
	 * 	8、课堂评分（classRoomScore）
	 * 	9、是否匿名（isAnonymous）
	 * */
	public void getComment(){
		int classRoomId = getParaToInt("classRoomId",0); //课堂ID
		int userId = getParaToInt("userId",0); //课堂ID
		if(xx.isAllEmpty(classRoomId)){
			Result.error(201, this);
			return;
		}
		//获取自己的评论数据
		List<ClassRoomComment> classRoomComment = ClassRoomComment.dao.find("select * from class_room_comment where class_room_id=? and user_id=? order by id desc",classRoomId,userId);

		
		int pageNumber = getParaToInt("pageNumber",1);//默认第一页
		int pageSize = getParaToInt("pageSize",10);//默认每页10条数据
	
		String sql = " from class_room_comment as crc,member as m where crc.user_id = m.id and crc.class_room_id=? order by crc.id desc";
		Page<Record> page = Db.paginate(pageNumber, pageSize, "select crc.class_room_id classRoomId,crc.content content,crc.image_urls imageUrls,crc.insert_time insertTime,crc.score score,crc.is_anonymous isAnonymous,m.id userId,m.nickname nickName,m.headimgurl headImgUrl",sql,classRoomId);
		JSONObject result = new JSONObject();
		if(classRoomComment.isEmpty()){//判断自己是否已经评论过，0为未评价过，1为未评价过
			result.put("isComment", 0);
			result.put("myComment", new JSONObject());
			result.put("commentList", Result.makeupPage(page));
		}else{
			result.put("isComment", 1);
			result.put("myComment", classRoomComment.get(0).toJson());
			result.put("commentList", Result.makeupPage(page));
		}


		Result.ok(result.toString(),this);
	}
	
	/**
	 * 课堂点赞
	 * 参数：
	 * 	classRoomId：课堂ID
	 *	userId：用户ID
	 * 
	 * */
	public void saveLike() {
		
		ClassRoomLike bean = getBean(ClassRoomLike.class,"");
		if(null == bean.getClassRoomId()||null == bean.getUserId()){
			Result.error(201, this);
			return;
		}
		int classRoomId = bean.getClassRoomId();
		int userId = bean.getUserId();
		ClassRoomLike classRoomLike = ClassRoomLike.dao.findFirst("select * from class_room_like where user_id=? and class_room_id=?",userId,classRoomId);
		if(null == classRoomLike){
			bean.save();
			Result.ok(this);
		}else{
			JSONObject result = new JSONObject();
			result.put("msg", "你已经点赞，不能重复点赞");
			result.put("code", 210);
			Result.ok(result,this);
		}


	}
	
	
	/**
	 * 取消课堂点赞
	 * 参数：
	 * 	classRoomId：课堂ID
	 * 	userId：用户ID
	 * 
	 * */
	public void cancelLike() {
		ClassRoomLike bean = getBean(ClassRoomLike.class,"");
		if(null == bean.getClassRoomId()||null == bean.getUserId()){
			Result.error(201, this);
		}
		int classRoomId = bean.getClassRoomId();
		int userId = bean.getUserId();
		ClassRoomLike classRoomLike = ClassRoomLike.dao.findFirst("select * from class_room_like where user_id=? and class_room_id=?",userId,classRoomId);
		if(null == classRoomLike){
			
		}else{
			classRoomLike.delete();
		}
		Result.ok(this);
	}
}
