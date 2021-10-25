package com.wechat.controller;

import com.wechat.entity.ClassCourse;
import com.wechat.service.RoomService;
import com.wechat.util.StatusCode;
import com.wechat.util.StdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * 与课程（Room）有点儿关连的新接口，都写在这个控制器内
 */
@Controller
@RequestMapping("api")
public class RoomApiController {

	@Autowired
	RoomService roomService;

	//获得学生的课程表(带辅课)
//	@RequestMapping("getCourseBySid")
	public StdResponse getCourseBySid(Integer sid) {
		// 新建返回对象
		StdResponse rsp = new StdResponse();

		// 校验输入参数
		if (null == sid || 1 > sid) {
			rsp.setMsg("学生id不合法").setCode(StatusCode.PARM_FAILED.getVal());
			return rsp;
		}
		// 进行业务处理
		try {
			List<ClassCourse> list = roomService.getCourseBySid(sid);

			HashMap<String,Integer> data = new HashMap<>();
			data.put("classId", null);
			rsp.addData(data);
		} catch (Exception e) {
			rsp.setMsg(StatusCode.SERVER_ERROE);
			e.printStackTrace();
			return rsp;
		}
		return rsp;

	}
}
