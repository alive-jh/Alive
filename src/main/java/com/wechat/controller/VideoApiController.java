package com.wechat.controller;

import com.wechat.entity.ClassGrades;
import com.wechat.entity.ClassTeacher;
import com.wechat.entity.VideoAcitivity;
import com.wechat.entity.VideoComment;
import com.wechat.entity.VideoCompetition;
import com.wechat.entity.VideoInfo;
import com.wechat.service.ClassTeacherService;
import com.wechat.service.LessonService;
import com.wechat.service.RedisService;
import com.wechat.service.VideoService;
import com.wechat.util.JsonResult;
import com.wechat.util.Page;
import com.wechat.util.ParameterFilter;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class VideoApiController {

	@Resource
	VideoService videoService;

	@Resource
	RedisService redisService;
	
	@Resource
	LessonService lessonService;
	
	@Resource
	ClassTeacherService classTeacherService;
	
	String key = "access_num_xf";

	//保存视频信息
	@RequestMapping("saveVedioInfo")
	@ResponseBody
	public Map<String,Object> saveVedioInfo(HttpServletRequest request,VideoInfo videoInfo){

		Map<String,Object> map = new HashMap<String, Object>();
		
		try{
			videoInfo.setShareUrl(videoInfo.getvUrl());
			videoInfo.setLearnTime(this.videoService.getStudentLearnTime(videoInfo.getStudentId()));
			videoInfo.setCommentStatus(-1);
			videoInfo.setvLike(0);
			videoInfo.setStatus(0);
			this.videoService.saveVideoInfo(videoInfo);
			map.put("success", 1);
			map.put("data", videoInfo);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}

		return map;
	}

	@RequestMapping("getVedioInfo")
	@ResponseBody
	public Map<String,Object> getVedioInfo(HttpServletRequest request,Integer vid){
		Map<String,Object> map = new HashMap<String, Object>();
		//根据视频ID获取视频详情
		try{
			VideoInfo videoInfo = this.videoService.getVideoInfo(vid);
			map.put("success", 1);
			map.put("videoInfo",videoInfo);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}

		return map;
	}
	
	//通过menberId获取视频列表（废弃，改为jfinal通过学生ID获取视频列表）
	@RequestMapping("getAllVedioByAcount")
	@ResponseBody
	public Map<String,Object> getAllVedio(String acountId){

		Map<String,Object> map = new HashMap<String, Object>();

		try{
			List<VideoInfo> list = this.videoService.getVedioAll(acountId);
			List<VideoInfo> list2 = new ArrayList<>();
			for(VideoInfo videoInfo : list){
				videoInfo.setShareUrl("https://wechat.fandoutech.com.cn/wechat/member/wechatShareForward?vid="+videoInfo.getId());
				list2.add(videoInfo);
			}
			map.put("success", 1);
			map.put("data", list);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}

		return map;
	}

	@RequestMapping("updataVedioInfo")
	@ResponseBody
	public Map<String,Object> updataVedioInfo(HttpServletRequest request,VideoInfo videoInfo){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			this.videoService.updatVideoInfo(videoInfo);
			map.put("success", 1);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}

		return map;
	}

	@RequestMapping("delVedioInfo")
	@ResponseBody
	public Map<String,Object> delVedioInfo(HttpServletRequest request,Integer vid){
		Map<String,Object> map = new HashMap<String, Object>();

		try{
			this.videoService.delVideoInfo(vid);
			map.put("success", 1);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}

		return map;
	}

	@RequestMapping("saveVideoCompetition")
	@ResponseBody
	public Map<String,Object> saveVideoCompetition(HttpServletRequest request,Integer vid,Integer activityId){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			VideoInfo videoInfo = this.videoService.getVideoInfo(vid);
			if(videoInfo!=null){
				Integer admissionNum =  this.videoService.saveVideoCompetition(new VideoCompetition(vid,activityId,0,videoInfo.getAcountId()));
				if(!admissionNum.equals(0)){
					videoInfo.setShareUrl(videoInfo.getvUrl());
					videoInfo.setAdmission(admissionNum);
					this.videoService.updatVideoInfo(videoInfo);
					map.put("success", 1);
				}else{
					map.put("success", 0);
				}
			}else{
				map.put("success", 0);
			}
		}catch(Exception e){
			map.put("success", 0);
		}
		return map;
	}

	@RequestMapping("showScore")
	public String showScore(HttpServletRequest request,Integer vid){
		
		Object[] videoInfo = this.videoService.videoInfoByActivity(vid);
		
		if(videoInfo!=null){
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("studentId", videoInfo[3]);
			map.put("page", 1);
			map.put("pageSize", 1);
			List<ClassGrades> classGrades = lessonService.getClassGradesByStudentId(map).getItems();
			
			request.setAttribute("video", videoInfo);
			if(classGrades!=null && classGrades.size()!=0){
				request.setAttribute("classGrades",classGrades.get(0));
			}
			request.setAttribute("video_id", videoInfo[0]);
			
		}
		
		return "/activity/showScore";
	}

	@RequestMapping("getVideoAcitivity2")
	public String getVideoAcitivity2(HttpServletRequest request,Integer vid){
		VideoAcitivity videoAcitivity = this.videoService.getVideoAcitivity();
		request.setAttribute("videoAcitivity", videoAcitivity);
		request.setAttribute("vid", vid);
		request.setAttribute("activityId", videoAcitivity.getId());
		request.setAttribute("activityName", videoAcitivity.getActivityName());
		request.setAttribute("startTime", videoAcitivity.getStartTime());
		request.setAttribute("endTime", videoAcitivity.getEndTime());
		return "activity/videoActivity";
	}

	//验证活动是否已经参加
	@RequestMapping("verificationVideo")
	@ResponseBody
	public Map<String,Object> verificationVideo(HttpServletRequest request,Integer vid,Integer activityId){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			VideoInfo videoInfo = this.videoService.getVideoInfo(vid);
			VideoCompetition videoCompetition = this.videoService.getVideoCompetition(activityId,videoInfo.getAcountId());
			if(videoCompetition!=null){
				boolean flag = this.videoService.verifcationVideoInfo(videoCompetition.getVideoInfoId(),videoCompetition.getId());
				if(flag){
					map.put("msg", "已参加过比赛");
					map.put("success", 1);
				}else{
					map.put("msg", "未参加过比赛");
					map.put("success", 0);
				}
			}else{
				map.put("msg", "未参加过比赛");
				map.put("success", 0);
				return map;
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "参数异常");
			map.put("success", -1);
		}
		return map;
	}

	//获取我的比赛，（废弃，修改为jfinal下）
	@RequestMapping("getMyVideoCompetitionOfAcount")
	@ResponseBody
	public Map<String,Object> getMyVideoCompetitionOfAcount(String acountId){
		Map<String,Object> map = new HashMap<String, Object>();
		try{
			List list = this.videoService.getMyVideoCompetitionOfAcount(acountId);
			if(list==null||list.size()==0){
				String[] temp=new String[]{};
				map.put("data", temp);
			}else{
				map.put("data", list);
			}
			map.put("success", 1);
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}


		return map;
	}

	//获取所有审核通过的视频
	@RequestMapping("getAllVideoCompetitionByVerifSuccess")
	public String getAllVideoCompetitionByVerifSuccess(HttpServletRequest request){
		redisService.incr(key);
		/*List<Object[]> list = this.videoService.getAllVideoCompetitionByVerifSuccess(0);
		for(Object[] obj:list){
			for(int i=0;i<obj.length;i++){
				//System.out.println(i+"--------"+obj[i]);
			}
		}
		Object[] statistical = this.videoService.getStatisticalInfo();
		String dd = redisService.get(key);
		if(list!=null){
			request.setAttribute("data", list);
			request.setAttribute("memberId", memberId);
			request.setAttribute("stuNum", Integer.parseInt(statistical[0].toString()));
			request.setAttribute("voteNum", 0);
			request.setAttribute("acessNum",Integer.parseInt(dd));
		}else{
			request.setAttribute("data", list);
		}*/
		return "activity/videoList";
	}
	
	@RequestMapping("getAllVideoCompetitionByVerifSuccessWithAjax")
	@ResponseBody
	public Map getAllVideoCompetitionByVerifSuccessWithAjax(
			HttpServletRequest request,Integer pageNumber,Integer pageSize,Integer activtId,String search){
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		pageNumber = ParameterFilter.NumberEmptyFilter(1, pageNumber);
		pageSize = ParameterFilter.NumberEmptyFilter(10, pageSize);
		
		List<Object[]> list = this.videoService.getAllVideoCompetitionByVerifSuccess(pageNumber,pageSize,activtId,search);
		
		map.put("code", 200);
		map.put("msg", "success");
		map.put("data",list);
		
		return map;

	}

	/*@RequestMapping("getAllVideoCompetitionByVerifSuccessWithAjax")
	@ResponseBody
	public Map<String,Object> getAllVideoCompetitionByVerifSuccessWithAjax(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String, Object>();
		int page = 1;
		if(request.getParameter("page")!=null||request.getParameter("page")==""){
			page=Integer.parseInt(request.getParameter("page"));
		}
		List<Object[]> list = this.videoService.getAllVideoCompetitionByVerifSuccess(page);
		map.put("data", list);
		return map;

	}*/

	//获取一个视频列表
	@RequestMapping("getAllVideoCompetitionByVerifSuccess2")
	public String getAllVideoCompetitionByVerifSuccess2(HttpServletRequest request,Integer vid){
		
		//活动访问量+1
		redisService.incr(key);
		
		Object[] videoInfo = this.videoService.videoInfoByActivity(vid);
		
		if(videoInfo!=null){
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("studentId", videoInfo[3]);
			map.put("page", 1);
			map.put("pageSize", 1);
			List<ClassGrades> classGrades = lessonService.getClassGradesByStudentId(map).getItems();
			
			request.setAttribute("video", videoInfo);
			if(classGrades!=null && classGrades.size()!=0){
				request.setAttribute("classGrades",classGrades.get(0));
			}
			
			request.setAttribute("video_id", videoInfo[0]);
			
		}
		
		return "activity/videoInfo";
	}

	//删除参赛信息
	@RequestMapping("delVideoCompetitionById")
	@ResponseBody
	public Map<String, Object> delVideoCompetitionById(HttpServletRequest request,Integer kkkvcid,Integer vid){
		Map<String, Object> map = new HashMap<String, Object>();
		int status=0;
		try{
			status = this.videoService.delVideoCompetitionById(kkkvcid,vid);
			if(status==1)
				map.put("success", 1);
			else{
				map.put("success", 0);
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", 0);
		}
		return map;
	}

	//投票接口
	@RequestMapping("addVote")
	public String addVote(Integer memberId,Integer vid){
		Map<String,Object> map = new HashMap<String, Object>();
		Date now = new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.set(2017, 9, 18,20,00,00);  //年月日时分秒
		Date end=calendar.getTime();//date就是你需要的时间
		if(now.getTime()<=end.getTime()){
			boolean flag = this.videoService.queryMemberId(memberId);
			if(flag){
				Calendar date = Calendar.getInstance();
				int time = Integer.parseInt((""+(date.get(Calendar.MONTH)+1)+date.get(Calendar.DATE)));
				Object[] obj = this.videoService.queryVote(memberId,vid,time);
				int stauts = this.videoService.voteIsExits(memberId);
				if(obj==null){
					Integer status = this.videoService.updataVideoVote(memberId,vid,time);
					if(status==1){
						map.put("status", 1);
						map.put("msg", "投票成功");
						return "activity/follow";
					}
				}else{
					map.put("msg", "今天已经投票");
					map.put("status", "0");
					return "activity/success";
				}
				map.put("first", stauts);
			}else{
				map.put("msg", "今天已经投票");
				map.put("status", "0");
			}
			return "activity/success";
		}else{
			return "activity/success";
		}

	}

	/*//投票登录微信
	@RequestMapping("videoLogin")
	public String videoLogin(Integer vid){
		StringBuffer wxUrl = new StringBuffer();
		wxUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=");
		wxUrl.append("http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&");
		wxUrl.append("state=toupiao:"+vid+"#wechat_redirect");
		return "redirect:"+wxUrl.toString();
	}	*/

	@RequestMapping("videoLogin")
	public String videoLogin(Integer vid){
		String url = "getAllVideoCompetitionByVerifSuccess2?vid="+vid;
		return "redirect:"+url;
	}

	//投票登录微信，跳转到首页
	@RequestMapping("videoLoginIndex")
	public String videoLoginIndex(HttpServletRequest request){
		StringBuffer wxUrl = new StringBuffer();
		wxUrl.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbeda1313a1604ddf&redirect_uri=");
		wxUrl.append("http://wechat.fandoutech.com.cn/wechat/oauth2Servlet&response_type=code&scope=snsapi_userinfo&");
		wxUrl.append("state=toupiao2#wechat_redirect");
		return "redirect:"+wxUrl.toString();
	}

	@RequestMapping("queryVideo")
	@ResponseBody
	public Map<String,Object> queryVideo(HttpServletRequest request){
		String searchVal = request.getParameter("serachVal");
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.videoService.queryVideo(searchVal);
		if(list!=null){
			map.put("searchVideo", list);
			return map;
		}else{
			map.put("searchVideo", null);
			return map;
		}
	}

	@RequestMapping("updataStatisticalInfo")
	@ResponseBody
	public Map<String,Object> updataStatisticalInfo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Object[] statistical = this.videoService.getStatisticalInfo();
		String accessNUm = redisService.get(key);
		int studentNum = Integer.parseInt(statistical[0].toString());
		int voteNum = Integer.parseInt(statistical[1].toString());
		map.put("accessNUm", Integer.parseInt(accessNUm));
		map.put("studentNum", studentNum);
		map.put("voteNum", voteNum);
		return map;
	}

	@RequestMapping("getAreaRanking")
	public String getAreaRanking(HttpServletRequest request,String areaId){
		////System.out.println(Keys.QINIU_LOG_PATH);
		Map<String,String> map = new HashMap<String,String>();
		map.put("10000", "兰陵");map.put("10001", "安徽");map.put("10002", "南充");map.put("10003", "河南");map.put("10004", "临汾");map.put("10005", "遵义");map.put("10006", "怀宁");
		Object[] statistical = this.videoService.getStatisticalInfo();
		String accessNUm = redisService.get(key);
		int studentNum = Integer.parseInt(statistical[0].toString());
		int voteNum = Integer.parseInt(statistical[1].toString());
		List data = this.videoService.getAreaRanking(areaId);
		request.setAttribute("area", map.get(areaId));
		request.setAttribute("data", data);
		request.setAttribute("accessNUm", Integer.parseInt(accessNUm));
		request.setAttribute("studentNum", studentNum*2);
		request.setAttribute("voteNum", voteNum);
		return "activity/areaRanking";
	}

	
	@RequestMapping("addlike")
	@ResponseBody
	public Map<String,Object> addlike(HttpServletRequest request){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String openid = request.getParameter("openid");
		String vid = request.getParameter("vid");
		if(null==openid||"".equals(openid)){
			map.put("code", 500);
			map.put("message", "openid为空");
			return map;
		}
		if(null==vid||"".equals(vid)){
			map.put("code", 500);
			map.put("message", "vid为空");
			return map;
		}
		boolean flag = this.videoService.queryOpenId(openid);
		if(flag){
			Calendar date = Calendar.getInstance();
			int time = Integer.parseInt((""+(date.get(Calendar.MONTH)+1)+date.get(Calendar.DATE)));
			Object[] obj = this.videoService.queryLike(openid,vid,time);
			if(obj==null){
				Integer status = this.videoService.updataVideoLike(openid,vid,time);
				if(status==1){
					map.put("code", 200);
					map.put("message", "点赞成功");
					return map;
				}
			}else{
				map.put("code", "201");
				map.put("message", "今天已经投票");
				return map;
			}
		}else{
			map.put("code", "今天已经投票");
			map.put("message", "0");
		}
		return map;
	}
	
	@RequestMapping("addVideoComment")
	@ResponseBody
	public Map<String,Object> addVideoComment(@Validated VideoComment videoComment,
			BindingResult result, @RequestParam(value="memberid",required=true) String memberid){
		Map<String,Object> map = new HashMap<String,Object>();
		//////System.out.println(vid+"---"+openid+"---"+commenter+"---"+content+"--------"+memberid);
		////System.out.println(videoComment.toString());
		
		if (result.hasErrors()) {
			map.put("code", 201);
			map.put("msg", "输入文本非法");
			return map;
		}
		
		boolean isTeacher = this.videoService.isTeacher(memberid);
		int status = 0;
		////System.out.println(memberid);
		if(isTeacher){
			videoComment.setCommenter("凡豆老师");
			videoComment.setSort(1);
			
		}if(memberid.equals("iamteacher")){
			videoComment.setCommenter("凡豆老师");
			videoComment.setSort(1);
		}else{
			videoComment.setSort(0);
		}
		status = this.videoService.insertVideoComment(videoComment,"skip");
		if(status>0){
		map.put("code", 200);
		map.put("vcID", status);
		}else{
			map.put("code", 201);
		}
		return map;
	}
	
	@RequestMapping("delVideoComment")
	@ResponseBody
	public Map<String,Object> delVideoComment(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String vid = request.getParameter("vid");
		this.videoService.delVideoCommentById(vid);
		map.put("code", 200);
		return map;
	}
	
	@RequestMapping("savePayRecord")
	@ResponseBody
	public Map<String,Object> savePayRecord(HttpServletRequest request,
											@RequestParam(value="vid",required=true) int vid,
											@RequestParam(value="memberid",required=true) int memberid,
											@RequestParam(value="price",required=true) double price){
		Map<String,Object> map = new HashMap<String,Object>();
		int status = this.videoService.savePayRecord(vid,memberid,price);
		if(status==1){
		map.put("code", 200);
		}else{
			map.put("code", 500);
		}
		return map;
	}
	
	@RequestMapping("getScenarioEnglishVideo")
	public String getScenarioEnglishVideo(HttpServletRequest request){
		List list = this.videoService.getScenarioEnglishVideo();
		request.setAttribute("data", list);
		return "activity/scenarioEnglish/videoList";
	}
	
	@RequestMapping("showComment")
	public String wechatShare(HttpServletRequest request) throws Exception{
		Integer vid = Integer.parseInt(request.getParameter("vid"));
		String teacherid = request.getParameter("teacherid");
			Object[] videoInfo = this.videoService.getVideoInfoByVid(vid);
			if(videoInfo!=null){
				request.setAttribute("video", videoInfo);
				List OtherInfo =  this.videoService.getOtherInfo(vid);
				List<VideoComment> vclist = this.videoService.getVideoComtentById(vid);
				int learnTime = this.videoService.getLearnTime(videoInfo[7].toString(),videoInfo[10].toString());
				////System.out.println(videoInfo[10].toString());
				request.setAttribute("learnTime", "学龄：0年"+learnTime/30+"个月"+learnTime%30+"天");
				request.setAttribute("score", videoInfo[5]);
				request.setAttribute("vclist", vclist);
				if(OtherInfo!=null&&OtherInfo.size()!=0){
					request.setAttribute("gradeName", OtherInfo.get(0));
					request.setAttribute("openid", teacherid);
				}else{
					request.setAttribute("OtherInfo", null);
				}
			}
			else{
				request.setAttribute("video", null);
				return "messageBox/video_share";
			}
			/*for(int i=0;i<videoInfo.length;i++){
				//System.out.println(i+"-------"+videoInfo[i]);
			}*/
			
			return "messageBox/video_share";
		
	}
		
	@RequestMapping("inviteComment")
	@ResponseBody
	public Map<String,Object> inviteComment(HttpServletRequest request,
											@RequestParam(value="vid",required=true) Integer vid
											){
		
		Map<String, Object> map = new HashMap<String, Object>();

		VideoInfo videoInfo = this.videoService.getVideoInfo(vid);

		if (videoInfo == null) {
			
			map.put("code", 400);
			
		} else {
			
			List<ClassTeacher> teachers = this.classTeacherService.getTeachersByStudent(videoInfo.getStudentId());

			if (teachers != null && teachers.size() > 0) {
				this.videoService.sendInvite(teachers, vid);
				map.put("code", 200);
			} else {
				map.put("code", 400);
			}
			
		}
		
		return map;
	}
	
	
	@RequestMapping("gotoMailBox")
	public String gotoMailBox(HttpServletRequest request,String teacherId){
		List<Object[]> data = this.videoService.getInviteMessage(teacherId);
		/*for(Object[] obj:data){
			for(int i=0;i<obj.length;i++){
				////System.out.println(i+"-----------"+obj[i]);
			}
		}*/
		if(data.size()>0)
		request.setAttribute("data", data);
		else
			request.setAttribute("data", null);	
		return "messageBox/index";
	}
	
	@RequestMapping("delMessage")
	@ResponseBody
	public Map<String,Object> delMessage(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String messageId = request.getParameter("messageId");
		this.videoService.delMessage(messageId);
		map.put("code", 200);
		return map;
	}
	
	@RequestMapping("gotoComment")
	public String gotoComment(HttpServletRequest request,Integer vid,Integer teacherid,Integer mid){

		Object[] videoInfo = this.videoService.getVideoInfoByVid(vid);
		if(videoInfo!=null){
			request.setAttribute("video", videoInfo);
			List OtherInfo =  this.videoService.getOtherInfo(vid);
			List<VideoComment> vclist = this.videoService.getVideoComtentById(vid);
			int learnTime = this.videoService.getLearnTime(videoInfo[7].toString(),videoInfo[10].toString());
			////System.out.println(videoInfo[10].toString());
			request.setAttribute("learnTime", "学龄：0年"+learnTime/30+"个月"+learnTime%30+"天");
			request.setAttribute("score", videoInfo[5]);
			request.setAttribute("vclist", vclist);
			if(OtherInfo!=null && OtherInfo.size()!=0){
				request.setAttribute("gradeName", OtherInfo.get(0));
			}else{
				request.setAttribute("OtherInfo", null);
			}
			request.setAttribute("teacherId", teacherid);
			request.setAttribute("messageId", mid);
		} 
		else{
			request.setAttribute("video", null);
			return "messageBox/videoComment";
		}
		/*for(int i=0;i<videoInfo.length;i++){
			////System.out.println(i+"-------"+videoInfo[i]);
		}*/
		
		return "messageBox/videoComment";
	}
	
	@RequestMapping("saveTeacherComment")
	@ResponseBody
	public Map<String,Object> saveTeacherComment(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		String teacherid=request.getParameter("teacherid");
		String messageid=request.getParameter("messageid");
		int vid = Integer.parseInt(request.getParameter("vid"));
		String techerName = this.videoService.getTeacherName(teacherid);
		
		VideoComment videoComment = new VideoComment();
		videoComment.setContent(request.getParameter("pinglun"));
		videoComment.setVid(vid);
		
		if(techerName.contains("老师")){
			videoComment.setCommenter(techerName);
		}else{
			videoComment.setCommenter(techerName+"老师");
		}
		
		videoComment.setSort(1);
		videoComment.setOpenid(teacherid);
		int status = 0;
		try {
			status = this.videoService.insertVideoComment(videoComment,messageid);
		} catch (Exception e) {
			map.put("code", 201);
			map.put("msg", "非法输入");
			return map;
		}
		
		this.videoService.updataCommentStatus(vid);
		if(status>0){
		map.put("code", 200);
		map.put("vcID", status);
		}else{
			map.put("code", 201);
		}
		return map;
	} 
	
	@RequestMapping("testResponse")
	public void testResponse(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<>();
		List<Object[]> data = this.videoService.getInviteMessage("2");
		
		map.put("data", data);
		
		JsonResult.JsonResultOK(response,map);
	}
	
	@RequestMapping("scenarioEnglishAll")
	public String scenarioEnglish(HttpServletRequest request,HttpServletResponse response){
		List<Object[]> list = this.videoService.getStudentVideoShow(0);
		/*for(Object[] obj:list){
			for(int i=0;i<obj.length;i++){
				//System.out.println(i+"--------"+obj[i]);
			}
		}*/
		if(list.size()!=0){
			request.setAttribute("data", list);
		}else{
			request.setAttribute("data", null);
		}
		return "activity/scenarioEnglish/videoList";
	}
	
	@RequestMapping("scenarioEnglishAllByAjax")
	@ResponseBody
	public Map<String,Object> scenarioEnglishAllByAjax(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		int index = Integer.parseInt(request.getParameter("page"));
		List<Object[]> list = this.videoService.getStudentVideoShow(index);
		/*for(Object[] obj:list){
			for(int i=0;i<obj.length;i++){
				//System.out.println(i+"--------"+obj[i]);
			}
		}*/
		if(list.size()!=0){
			result.put("data", list);
		}else{
			result.put("data", null);
		}
		return result;
	}
	
	@RequestMapping("scenarioEnglishById")
	public String scenarioEnglishById(HttpServletRequest request,HttpServletResponse response){
		String vid = request.getParameter("vid");
		Object[] data = this.videoService.scenarioEnglishById(vid);
			/*for(int i=0;i<data.length;i++){
				//System.out.println(i+"--------"+data[i]);
			}*/
		if(data.length!=0){
			request.setAttribute("data", data);
		}else{
			request.setAttribute("data", null);
		}
		return "activity/scenarioEnglish/videoInfo";
	}
	
	@RequestMapping("scenarioEnglishLike")
	@ResponseBody
	public Map<String,Object> scenarioEnglishLike(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		String vid = request.getParameter("vid");
		////System.out.println(vid);
		int status = this.videoService.scenarioEnglishLike(vid);
		result.put("code", 200);
		return result;
	}
	
	@RequestMapping("queryscenarioEnglish")
	@ResponseBody
	public Map<String,Object> queryscenarioEnglish(HttpServletRequest request){
		String searchVal = request.getParameter("serachVal");
		Map<String, Object> map = new HashMap<String, Object>();
		List list = this.videoService.queryscenarioEnglish(searchVal);
		if(list!=null){
			map.put("searchVideo", list);
			return map;
		}else{
			map.put("searchVideo", null);
			return map;
		}
	}
	
	@RequestMapping("randomVideos")
	public String randomVideos(HttpServletRequest request){
		return "/activity/xiangfen_acticity/VideoScore";
	}
	
	@RequestMapping("voteRecord")
	public String voteRecord(HttpServletRequest request){
		return "/activity/xiangfen_acticity/VoteInfo";
	}
}
