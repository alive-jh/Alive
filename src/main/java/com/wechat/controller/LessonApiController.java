package com.wechat.controller;

import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.*;
import com.wechat.entity.dto.ClassRoomDto;
import com.wechat.entity.dto.ClassRoomIndexDto;
import com.wechat.entity.dto.ClassStudentDto;
import com.wechat.service.*;
import com.wechat.test.DataGenerator;
import com.wechat.test.MultipleTreeUtil;
import com.wechat.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class LessonApiController {

	@Autowired
	LessonService lessonService;

	@Autowired
	ClassCourseService classCourseService;

	@Autowired
	EvalTemplateService evalTemplateService;

	@Autowired
	ClassCourseCommentService classCourseCommentService;

	@Autowired
	VideoService videoService;

	/**
	 * 添加或者修改在线课堂
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassRoom")
	public void saveClassRoom(HttpServletRequest request, HttpServletResponse response) {
		try {

			String id = ParameterFilter.emptyFilter(null, "id", request);
			// 模板ID
			String tempId = ParameterFilter.emptyFilter(null, "tempId", request);
			String teacherId = ParameterFilter.emptyFilter(null, "teacherId", request);
			String status = ParameterFilter.emptyFilter(null, "status", request);
			String sort = ParameterFilter.emptyFilter(null, "sort", request);
			String teacherName = ParameterFilter.emptyFilter(null, "teacherName", request);
			String className = ParameterFilter.emptyFilter(null, "className", request);
			String cover = ParameterFilter.emptyFilter(null, "cover", request);
			String summary = ParameterFilter.emptyFilter(null, "summary", request);
			String categoryId = ParameterFilter.emptyFilter(null, "categoryId", request);
			String bookResId = ParameterFilter.emptyFilter(null, "bookResId", request);
			String groupId = ParameterFilter.emptyFilter(null, "groupId", request);
			String bookResIds = ParameterFilter.emptyFilter(null, "bookResIds", request);

			if (null == bookResIds && null == bookResId) {
				bookResIds = null;
				bookResId = null;

			} else {
				if (null == bookResIds) {
					bookResIds = bookResId;
				}
				if (null == bookResId) {
					bookResId = bookResIds.split(",")[0];

				}
			}
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoom classRoom = new ClassRoom(ParameterFilter.nullFilter(teacherId), teacherName, className, cover,
					summary, ParameterFilter.nullFilter(status), ParameterFilter.nullFilter(sort),
					ParameterFilter.nullFilter(categoryId), ParameterFilter.nullFilter(bookResId), groupId, createTime,
					bookResIds);
			if (id != null) {
				classRoom.setCreateTime(null);
				classRoom = (ClassRoom) BeanModifyFilter.modifyFilter(classRoom,
						this.lessonService.getClassRoom(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoom(classRoom);

			try {

				String audioIds = "";

				if (bookResIds != null) {
					audioIds += bookResIds + ",";
				}

				if (bookResId != null) {
					audioIds += bookResId;
				}

				this.lessonService.addAudioRelation(audioIds, classRoom.getId());

			} catch (Exception e) {
				e.printStackTrace();
			}

			// 判断有没有模板ID，有就添加模板数据，没有就不管
			if (null != tempId) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("classRoomId", tempId);
				map.put("page", ParameterFilter.emptyFilter("1", "page", request));
				map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
				Page<ClassScriptNormalTemp> classScriptNormals = this.lessonService.findClassScriptNormalTemp(map);
				for (int i = 0; i < classScriptNormals.getItems().size(); i++) {
					ClassScriptNormal classScriptNormal = new ClassScriptNormal();
					BeanUtils.copyProperties(classScriptNormal, classScriptNormals.getItems().get(i));
					classScriptNormal.setId(null);
					classScriptNormal.setClassRoomId(classRoom.getId());
					this.lessonService.saveClassScriptNormal(classScriptNormal);
				}

			}

			JsonResult.JsonResultInfo(response, classRoom);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassRoomTemp")
	public void saveClassRoomTemp(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String teacherId = ParameterFilter.emptyFilter(null, "teacherId", request);
			String status = ParameterFilter.emptyFilter(null, "status", request);
			String sort = ParameterFilter.emptyFilter(null, "sort", request);
			String teacherName = ParameterFilter.emptyFilter(null, "teacherName", request);
			String className = ParameterFilter.emptyFilter(null, "className", request);
			String cover = ParameterFilter.emptyFilter(null, "cover", request);
			String summary = ParameterFilter.emptyFilter(null, "summary", request);
			String categoryId = ParameterFilter.emptyFilter(null, "categoryId", request);
			String bookResId = ParameterFilter.emptyFilter(null, "bookResId", request);
			String groupId = ParameterFilter.emptyFilter(null, "groupId", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoomTemp classRoom = new ClassRoomTemp(ParameterFilter.nullFilter(teacherId), teacherName, className,
					cover, summary, ParameterFilter.nullFilter(status), ParameterFilter.nullFilter(sort),
					ParameterFilter.nullFilter(categoryId), ParameterFilter.nullFilter(bookResId), groupId, createTime);
			if (id != null) {
				classRoom = (ClassRoomTemp) BeanModifyFilter.modifyFilter(classRoom,
						this.lessonService.getClassRoomTemp(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoomTemp(classRoom);
			JsonResult.JsonResultInfo(response, classRoom);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂执行脚本类型
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassScriptType")
	public void saveClassScriptType(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String name = ParameterFilter.emptyFilter(null, "name", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassScriptType classScriptType = new ClassScriptType(name, createTime);
			if (id != null) {
				classScriptType = (ClassScriptType) BeanModifyFilter.modifyFilter(classScriptType,
						this.lessonService.getClassScriptType(Integer.parseInt(id)));
			}
			this.lessonService.saveClassScriptType(classScriptType);
			JsonResult.JsonResultInfo(response, classScriptType);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂执行脚本
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassScriptNormal")
	public void saveClassScriptNormal(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String classScriptTypeId = ParameterFilter.emptyFilter(null, "classScriptTypeId", request);
			String classScriptContent = ParameterFilter.emptyFilter(null, "classScriptContent", request);
			String totalTime = ParameterFilter.emptyFilter(null, "totalTime", request);
			String sort = ParameterFilter.emptyFilter("0", "sort", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassScriptNormal classScriptNormal = new ClassScriptNormal(ParameterFilter.nullFilter(classRoomId),
					ParameterFilter.nullFilter(classScriptTypeId), classScriptContent, ParameterFilter.nullFilter(sort),
					createTime, ParameterFilter.nullFilter(totalTime));
			if (id != null) {
				classScriptNormal = (ClassScriptNormal) BeanModifyFilter.modifyFilter(classScriptNormal,
						this.lessonService.getClassScriptNormal(Integer.parseInt(id)));
			}
			this.lessonService.saveClassScriptNormal(classScriptNormal);
			JsonResult.JsonResultInfo(response, classScriptNormal);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂执行脚本模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassScriptNormalTemp")
	public void saveClassScriptNormalTemp(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String classScriptTypeId = ParameterFilter.emptyFilter(null, "classScriptTypeId", request);
			String classScriptContent = ParameterFilter.emptyFilter(null, "classScriptContent", request);
			String sort = ParameterFilter.emptyFilter("0", "sort", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassScriptNormalTemp classScriptNormal = new ClassScriptNormalTemp(ParameterFilter.nullFilter(classRoomId),
					ParameterFilter.nullFilter(classScriptTypeId), classScriptContent, ParameterFilter.nullFilter(sort),
					createTime);
			if (id != null) {
				classScriptNormal = (ClassScriptNormalTemp) BeanModifyFilter.modifyFilter(classScriptNormal,
						this.lessonService.getClassScriptNormalTemp(Integer.parseInt(id)));
			}
			this.lessonService.saveClassScriptNormalTemp(classScriptNormal);
			JsonResult.JsonResultInfo(response, classScriptNormal);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加在线课堂脚本执行记录
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassScriptDone")
	public void saveClassScriptDone(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String classScriptTypeId = ParameterFilter.emptyFilter(null, "classScriptTypeId", request);
			String classScriptContent = ParameterFilter.emptyFilter(null, "classScriptContent", request);
			String studentId = ParameterFilter.emptyFilter(null, "studentId", request);
			String classCourseId = ParameterFilter.emptyFilter(null, "classCourseId", request);
			String sort = ParameterFilter.emptyFilter(null, "sort", request);
			String reply = ParameterFilter.emptyFilter(null, "reply", request);
			String feedback = ParameterFilter.emptyFilter(null, "feedback", request);
			String epalId = ParameterFilter.emptyFilter(null, "epalId", request);
			Timestamp createTime = null;
			String createTimeStr = ParameterFilter.emptyFilter(null, "createTime", request);
			if (null != createTimeStr) {
				createTime = Timestamp.valueOf(createTimeStr);
			}
			ClassScriptDone classScriptDone = new ClassScriptDone(ParameterFilter.nullFilter(classRoomId),
					ParameterFilter.nullFilter(classScriptTypeId), classScriptContent, reply, feedback, epalId,
					ParameterFilter.nullFilter(sort), createTime, ParameterFilter.nullFilter(studentId),
					ParameterFilter.nullFilter(classCourseId));
			if (id != null) {
				classScriptDone = (ClassScriptDone) BeanModifyFilter.modifyFilter(classScriptDone,
						this.lessonService.getClassScriptDone(Integer.parseInt(id)));
			}
			this.lessonService.saveClassScriptDone(classScriptDone);
			JsonResult.JsonResultInfo(response, classScriptDone);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂老师
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassTeacher")
	public void saveClassTeacher(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String teacherName = ParameterFilter.emptyFilter(null, "name", request);
			String memberId = ParameterFilter.emptyFilter(null, "memberId", request);
			Cookie[] enu = request.getCookies();
			String userId = "1";
			for (int i = 0; i < enu.length; i++) {
				Cookie cookie = enu[i];
				String value = cookie.getValue();
				String name = cookie.getName();
				if ("adminUser".equals(name)) {
					userId = value;
				}
			}
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassTeacher classTeacher = new ClassTeacher(teacherName, memberId, createTime, 1,
					Integer.parseInt(userId));
			if (id != null) {
				classTeacher = (ClassTeacher) BeanModifyFilter.modifyFilter(classTeacher,
						this.lessonService.getClassTeacher(Integer.parseInt(id)));
			}
			this.lessonService.saveClassTeacher(classTeacher);
			JsonResult.JsonResultInfo(response, classTeacher);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂学生 学生状态控制：1、为正常学生，2、为电教室学生。
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassStudent")
	public void saveClassStudent(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String name = ParameterFilter.emptyFilter(null, "name", request);
			String epalId = ParameterFilter.emptyFilter(null, "epalId", request);
			String studentType = ParameterFilter.emptyFilter("1", "studentType", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassStudent classStudent = new ClassStudent(name, epalId, createTime, Integer.parseInt(studentType), 0);
			if (id != null) {
				classStudent = (ClassStudent) BeanModifyFilter.modifyFilter(classStudent,
						this.lessonService.getClassStudent(Integer.parseInt(id)));
			}
			this.lessonService.saveClassStudent(classStudent);
			JsonResult.JsonResultInfo(response, classStudent);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂学生课堂关联关系(学生绑定到在线课堂)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassStudentRela")
	public void saveClassStudentRela(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String name = ParameterFilter.emptyFilter(null, "name", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String epalId = ParameterFilter.emptyFilter(null, "epalId", request);
			String classStudentId = ParameterFilter.emptyFilter(null, "classStudentId", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("epalId", epalId);
			map.put("classRoomId", classRoomId);
			map.put("page", "1");
			map.put("pageSize", "30");

			if (epalId != null) {
				Page<ClassStudent> classStudents = this.lessonService.findClassStudentByEpalId(map);
				ClassStudent classStudent = null;
				if (classStudents.getItems().size() == 0) {
					classStudent = new ClassStudent(epalId, epalId, createTime, 1, 0);
					this.lessonService.saveClassStudent(classStudent);
				} else {
					classStudent = classStudents.getItems().get(0);
				}
				classStudentId = classStudent.getId() + "";
			}
			map.put("classStudentId", classStudentId);
			Page<ClassStudentRela> classStudentRelas = this.lessonService.findStudentByRoomIdAndStudentId(map);
			if (classStudentRelas.getItems().size() > 0) {
				JsonResult.JsonResultError(response, 1002);
			} else {
				// 添加学生关系
				ClassStudentRela classStudentRela = new ClassStudentRela(name, ParameterFilter.nullFilter(classRoomId),
						ParameterFilter.nullFilter(classStudentId), createTime);
				if (id != null) {
					classStudentRela = (ClassStudentRela) BeanModifyFilter.modifyFilter(classStudentRela,
							this.lessonService.getClassStudentRela(Integer.parseInt(id)));
				}
				this.lessonService.saveClassStudentRela(classStudentRela);
				JsonResult.JsonResultInfo(response, classStudentRela);
			}
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂学生班级关联关系(学生绑定到班级)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassGradesRela")
	public void saveClassGradesRela(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classGradesId = ParameterFilter.emptyFilter(null, "classGradesId", request);
			String classStudentId = ParameterFilter.emptyFilter(null, "classStudentId", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassGradesRela classGradesRela = new ClassGradesRela(ParameterFilter.nullFilter(classGradesId),
					ParameterFilter.nullFilter(classStudentId), createTime);
			if (id != null) {
				classGradesRela = (ClassGradesRela) BeanModifyFilter.modifyFilter(classGradesRela,
						this.lessonService.getClassGradesRela(Integer.parseInt(id)));
			}
			this.lessonService.saveClassGradesRela(classGradesRela);
			JsonResult.JsonResultInfo(response, classGradesRela);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加学生到虚拟班级（支持批量）
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveStudentToGrades")
	public void saveStudentToGrades(HttpServletRequest request, HttpServletResponse response) {
		try {
			String studentIds = ParameterFilter.emptyFilter("", "studentIds", request);
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String entityClassIds = ParameterFilter.emptyFilter("", "entityClassIds", request);
			HashMap map = new HashMap();
			map.put("studentIds", studentIds);
			map.put("classGradesId", classGradesId);
			map.put("entityClassIds", entityClassIds);
			this.lessonService.saveStudentToGrades(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加学生到虚拟班级 ——新接口，对应加密二维码
	 *
	 * @param
	 * @param
	 */
	@RequestMapping("addStd2Class")
	@ResponseBody
	public StdResponse addStd2Class(Integer sid, String code) {
		// 新建返回对象
		StdResponse rsp = new StdResponse();

		// 校验输入参数
		if (null == sid || 1 > sid) {
			rsp.setMsg("学生id不合法").setCode(StatusCode.PARM_FAILED.getVal());
			return rsp;
		}
		// 检验code是否合法
		if (null == code || code.length() != 25) {
			rsp.setMsg("二维码无效或已扫描").setCode(StatusCode.PARM_FAILED.getVal());
			return rsp;
		}
		// 进行业务处理
		try {
			// 获得二维码对应信息
			ClassQRInfo info = classCourseService.getQRInfo(code);
			if (null == info)
				return rsp.setMsg("二维码无效或已扫描").setCode(StatusCode.PARM_FAILED.getVal());

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("classId", info.getClassId());
			paramMap.put("studentId", sid);
			if (classCourseService.getQRInfoByParm(paramMap).size() > 0) {
				return rsp.setMsg("你已经在该班级内，不需要重复添加").setCode(StatusCode.PARM_FAILED.getVal());
			}
			// 检查是否已通过其他途径加入过该班级
			if (classCourseService.getGradesRelas(sid, info.getClassId()).size() > 0) {
				return rsp.setMsg("你已经在该班级内，不需要再次加入").setCode(421);
			}

			HashMap map = new HashMap();
			map.put("studentIds", sid);
			map.put("classGradesId", info.getClassId());
			map.put("entityClassIds", "");
			this.lessonService.saveStudentToGrades(map);
			info.setStudentId(sid);
			classCourseService.updateQRInfo(info);

			HashMap<String, Integer> data = new HashMap<>();
			data.put("classId", info.getClassId());
			rsp.addData(data);
		} catch (Exception e) {
			rsp.setMsg(StatusCode.SERVER_ERROE);
			e.printStackTrace();
			return rsp;
		}
		return rsp;
	}

	/**
	 * 添加学生到虚拟班级（支持批量）
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("getClassCourseByClassGradesId")
	public void getClassCourseByClassGradesId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "gradesId", request);
			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			JSONObject result = this.lessonService.getClassCourseByClassGradesId(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂课程班级关联关系(在线课程绑定到班级，班级绑定到在线课程)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassRoomGradesRela")
	public void saveClassRoomGradesRela(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String classGradesId = ParameterFilter.emptyFilter(null, "classGradesId", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoomGradesRela classRoomGradesRela = new ClassRoomGradesRela(ParameterFilter.nullFilter(classRoomId),
					ParameterFilter.nullFilter(classGradesId), createTime);
			if (id != null) {
				classRoomGradesRela = (ClassRoomGradesRela) BeanModifyFilter.modifyFilter(classRoomGradesRela,
						this.lessonService.getClassRoomGradesRela(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoomGradesRela(classRoomGradesRela);
			JsonResult.JsonResultInfo(response, classRoomGradesRela);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂点到
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassRoomSign")
	public void saveClassRoomSign(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String epalId = ParameterFilter.emptyFilter(null, "epalId", request);
			String startTime = ParameterFilter.emptyFilter(null, "startTime", request);
			String pushId = ParameterFilter.emptyFilter("1", "pushId", request);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoomSign classRoomSign = new ClassRoomSign(ParameterFilter.nullFilter(classRoomId), epalId,
					Timestamp.valueOf(startTime), createTime, pushId);
			if (id != null) {
				classRoomSign = (ClassRoomSign) BeanModifyFilter.modifyFilter(classRoomSign,
						this.lessonService.getClassRoomSign(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoomSign(classRoomSign);
			JsonResult.JsonResultInfo(response, classRoomSign);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改在线课堂推送缓存
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveClassRoomPush")
	public void saveClassRoomPush(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String classRoomId = ParameterFilter.emptyFilter(null, "classRoomId", request);
			String classRoomScript = ParameterFilter.emptyFilter(null, "classRoomScript", request);
			String cmdDoStatus = ParameterFilter.emptyFilter(null, "cmdDoStatus", request);
			String epalId = ParameterFilter.emptyFilter(null, "epalId", request);

			String type = ParameterFilter.emptyFilter("", "type", request);

			String groupId = ParameterFilter.emptyFilter(null, "groupId", request);
			String appAccount = ParameterFilter.emptyFilter(null, "appAccount", request);
			String epalIds = ParameterFilter.emptyFilter("", "epalIds", request);
			String classIds = ParameterFilter.emptyFilter("", "classIds", request);
			HashMap map = new HashMap();
			map.put("type", type);
			map.put("epalIds", epalIds);
			map.put("classIds", classIds);
			Timestamp createTime = new Timestamp(System.currentTimeMillis());
			ClassRoomPush classRoomPush = new ClassRoomPush(ParameterFilter.nullFilter(classRoomId), epalId,
					classRoomScript, ParameterFilter.nullFilter(cmdDoStatus), appAccount, groupId, createTime);
			if (id != null) {
				classRoomPush = (ClassRoomPush) BeanModifyFilter.modifyFilter(classRoomPush,
						this.lessonService.getClassRoomPush(Integer.parseInt(id)));
			}
			this.lessonService.saveClassRoomPush(classRoomPush, map);
			JsonResult.JsonResultInfo(response, classRoomPush);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 删除在线课堂或者删除在线课堂执行脚本
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteTableRecord")
	public void deleteTableRecord(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String table = ParameterFilter.emptyFilter("", "table", request);

			if (id != null) {
				if (table.equals("ClassRoom")) {

					// 根据在线课堂ID查询下面是否有学生。有学生的话不允许删除课堂
					// 根据在线课堂ID查询下面是否有绑定的课堂表。有的话不允许删除课堂
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("classRoomId", id);
					map.put("page", "1");
					map.put("pageSize", "30");
					Page<ClassCourse> classCourses = this.classCourseService.findClassCoursesByRoomId(map);
					Page<ClassStudentDto> classStudentDtos = this.lessonService.findClassStudents(map);
					if (classStudentDtos.getItems().size() > 0) {
						JsonResult.JsonResultInfo(response, "在线课堂有正在学习的学生，不能删除");
					} else if (classCourses.getItems().size() > 0) {
						JsonResult.JsonResultInfo(response, "在线课堂已添加到课程表，请先从课程表里移除");
					} else {
						this.lessonService.deleteTableRecord(id, "class_room");
						JsonResult.JsonResultInfo(response, "OK");
					}
				} else if (table.equals("ClassScriptNormal")) {
					// 删除在线课堂执行脚本
					String classRoomId = ParameterFilter.emptyFilter("", "classRoomId", request);
					HashMap map = new HashMap();
					map.put("id", id);
					map.put("classRoomId", classRoomId);
					this.lessonService.deleteClassScriptNormal(map);
					JsonResult.JsonResultInfo(response, "OK");
				} else {
					JsonResult.JsonResultError(response, 400);
				}
			} else {
				JsonResult.JsonResultError(response, 400);
			}

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 500);
			e.printStackTrace();
		}
	}

	/**
	 * 删除在线课堂模板
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("delClassRoomTemp")
	public void delClassRoomTemp(HttpServletRequest request, HttpServletResponse response) {
		try {
			String teacherId = ParameterFilter.emptyFilter("0", "teacherId", request);
			String tempId = ParameterFilter.emptyFilter("0", "tempId", request);
			HashMap map = new HashMap();
			map.put("teacherId", teacherId);
			map.put("tempId", tempId);
			this.lessonService.delClassRoomTemp(map);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 删除在线课堂模板执行脚本
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("delClassScriptNormalTemp")
	public void delClassScriptNormalTemp(HttpServletRequest request, HttpServletResponse response) {
		try {
			String teacherId = ParameterFilter.emptyFilter("0", "teacherId", request);
			String tempScriptId = ParameterFilter.emptyFilter("0", "tempScriptId", request);
			String classRoomId = ParameterFilter.emptyFilter("0", "classRoomId", request);
			HashMap map = new HashMap();
			map.put("teacherId", teacherId);
			map.put("tempScriptId", tempScriptId);
			map.put("classRoomId", classRoomId);
			this.lessonService.delClassScriptNormalTemp(map);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 学生退出在线课堂
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("signOutClassRoom")
	public void signOutClassRoom(HttpServletRequest request, HttpServletResponse response) {
		try {
			String studentId = ParameterFilter.emptyFilter("0", "studentId", request);
			String classRoomId = ParameterFilter.emptyFilter("0", "classRoomId", request);
			HashMap map = new HashMap();
			map.put("studentId", studentId);
			map.put("classRoomId", classRoomId);
			this.lessonService.signOutClassRoom(map);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 老师删除在线课堂学生
	 *
	 * @param request
	 * @param response
	 *
	 *            备注：缺少插入时间的判断
	 */

	@RequestMapping("deleteStudentFromGradesByteacher")
	public void deleteStudentFromGradesByteacher(HttpServletRequest request, HttpServletResponse response) {
		try {
			String studentId = ParameterFilter.emptyFilter("", "studentId", request);
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String gradesId = ParameterFilter.emptyFilter("", "gradesId", request);

			String status = ParameterFilter.emptyFilter("0", "status", request);
			String id = ParameterFilter.emptyFilter("", "id", request);

			OnlineClassDeleteStudentRecord onlineClassDeleteStudentRecord = new OnlineClassDeleteStudentRecord();

			onlineClassDeleteStudentRecord.setGradesId(Integer.parseInt(gradesId));
			onlineClassDeleteStudentRecord.setStudentId(Integer.parseInt(studentId));
			onlineClassDeleteStudentRecord.setTeacherId(Integer.parseInt(teacherId));
			onlineClassDeleteStudentRecord.setStatus(Integer.parseInt(status));

			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = sdf.format(d);

			if (!"".equals(id) && null != id) {
				onlineClassDeleteStudentRecord.setId(Integer.parseInt(id));

			} else {
				onlineClassDeleteStudentRecord.setId(null);
				onlineClassDeleteStudentRecord.setInsertDate(currentDate);
			}
			onlineClassDeleteStudentRecord.setEditDate(currentDate);
			this.lessonService.saveStudentFromGradesByteacher(onlineClassDeleteStudentRecord);
			JsonResult.JsonResultInfo(response, "OK");

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂所有执行脚本类型
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassScriptTypes")
	public void findClassScriptTypes(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = null;
			ArrayList<ClassScriptType> classScriptTypes = this.lessonService.findClassScriptTypes(map);
			JsonResult.JsonResultInfo(response, classScriptTypes);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂所有老师
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassTeachers")
	public void findClassTeachers(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("memberId", ParameterFilter.emptyFilter("", "memberId", request));
			ArrayList<ClassTeacher> classTeachers = this.lessonService.findClassTeachers(map);
			JsonResult.JsonResultInfo(response, classTeachers);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂首页列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomsIndex")
	public void findClassRoomsIndex(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassRoomIndexDto> classRoomCategorys = this.lessonService.findClassRoomsIndex(map);
			ArrayList<ClassRoomIndexDto> classRoomIndexs = (ArrayList<ClassRoomIndexDto>) classRoomCategorys.getItems();
			DataGenerator dataGenerator = new DataGenerator();
			dataGenerator.setClassRoomIndexs(classRoomIndexs);
			MultipleTreeUtil multipleTree = new MultipleTreeUtil();
			multipleTree.setDataList(dataGenerator.getClassRoomIndexs());
			// 得到树形结构的数据分类
			JsonResult.JsonResultInfo(response, multipleTree.getMultipleTree(1));
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取老师已制作的在线课堂列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRooms")
	public void findClassRooms(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("className", ParameterFilter.emptyFilter("", "className", request));
			map.put("teacherId", ParameterFilter.emptyFilter("", "teacherId", request));
			map.put("teacherName", ParameterFilter.emptyFilter("", "teacherName", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassRoomDto> classRoomDtos = this.lessonService.findClassRooms(map);
			JsonResult.JsonResultInfo(response, classRoomDtos.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取老师已制作的在线课堂模板列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomTemps")
	public void findClassRoomTemps(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("teacherId", ParameterFilter.emptyFilter("-1", "teacherId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassRoomTemp> classRoomTemps = this.lessonService.findClassRoomTemps(map);
			JsonResult.JsonResultInfo(response, classRoomTemps.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取学生已添加的在线课堂
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findStudiedClassRooms")
	public void findStudiedClassRooms(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("className", ParameterFilter.emptyFilter("", "className", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("classStudentId", ParameterFilter.emptyFilter("", "classStudentId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassRoomDto> studiedClassRoomDtos = this.lessonService.findStudiedClassRooms(map);
			JsonResult.JsonResultInfo(response, studiedClassRoomDtos.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取在线课堂学生列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassStudents")
	public void findClassStudents(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassStudentDto> classStudentDtos = this.lessonService.findClassStudents(map);
			JsonResult.JsonResultInfo(response, classStudentDtos.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据epalId获取在线课堂学生
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassStudentByEpalId")
	public void findClassStudentByEpalId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassStudent> classStudents = this.lessonService.findClassStudentByEpalId(map);
			JsonResult.JsonResultInfo(response, classStudents.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取在线课堂标准执行流程
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomNormalScript")
	public void findClassRoomNormalScript(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassScriptNormal> classScriptNormals = this.lessonService.findClassRoomNormalScript(map);

			JsonResult.JsonResultInfo(response, classScriptNormals.getItems().toArray());
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);

		}
	}

	/**
	 * 根据查询条件获取在线课堂模板标准执行流程
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomNormalScriptTemp")
	public void findClassRoomNormalScriptTemp(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassScriptNormalTemp> classScriptNormals = this.lessonService.findClassScriptNormalTemp(map);
			JsonResult.JsonResultInfo(response, classScriptNormals.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取在线课堂执行过的脚本记录
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomDoneScript")
	public void findClassRoomDoneScript(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("classCourseId", ParameterFilter.emptyFilter("", "classCourseId", request));
			map.put("studentId", ParameterFilter.emptyFilter("", "studentId", request));
			map.put("classScriptTypeId", ParameterFilter.emptyFilter("", "classScriptTypeId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassScriptDone> classScriptDones = this.lessonService.findClassRoomDoneScript(map);
			JsonResult.JsonResultInfo(response, classScriptDones.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件获取在线课堂执行过的脚本记录
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomDoneScriptOptimization")
	public void findClassRoomDoneScriptOptimization(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("epalId", ParameterFilter.emptyFilter("", "epalId", request));

			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("classScriptTypeId", ParameterFilter.emptyFilter("", "classScriptTypeId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassScriptDone> classScriptDones = this.lessonService.findClassRoomDoneScript(map);

			List<JSONObject> jsonList = new ArrayList();
			List<ClassScriptDone> infoList = classScriptDones.getItems();

			// 封装成JSON显示对象
			JSONObject jsonObj = new JSONObject();

			if (infoList != null) {
				JSONArray AgentKeyWordInfo = new JSONArray();

				int i = 1;
				int num = 0;
				for (ClassScriptDone classScriptDone : infoList) {
					JSONObject jobj = new JSONObject();
					jobj.put("id", classScriptDone.getId());
					jobj.put("classRoomId", classScriptDone.getClassRoomId());
					jobj.put("status", "未完成");
					if (classScriptDone.getSort() == 0) {
						jobj.put("title",
								"第" + i + "遍(" + classScriptDone.getCreateTime().toString().substring(0, 16) + ")");
						if (classScriptDone.getClassScriptContent().lastIndexOf("finish") != -1) {
							jobj.put("status", "已完成");
						}

						num = i - 1;
						jsonList.add(jobj);
						i++;

					} else {
						if (classScriptDone.getClassScriptContent().lastIndexOf("finish") != -1) {
							jsonList.get(num).put("status", "已完成");

						}

					}
					jobj.put("createTime", classScriptDone.getCreateTime().toString().substring(0, 19));

				}
				jsonObj.put("infoList", jsonList);

			}
			JsonResult.JsonResultInfo(response, jsonObj);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂分类列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomCategorys")
	public void findClassRoomCategorys(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassRoomIndexDto> classRoomCategorys = this.lessonService.findClassRoomCategorys(map);
			ArrayList<ClassRoomIndexDto> classRoomCates = (ArrayList<ClassRoomIndexDto>) classRoomCategorys.getItems();
			DataGenerator dataGenerator = new DataGenerator();
			dataGenerator.setClassRoomCategorys(classRoomCates);
			MultipleTreeUtil multipleTree = new MultipleTreeUtil();
			multipleTree.setDataList(dataGenerator.getClassRoomCategorys());
			// 得到树形结构的数据分类
			JsonResult.JsonResultInfo(response, multipleTree.getMultipleTree(1));
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取班级信息数据{Mark-Tree结构}(后台管理系统用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/findClassGradesTree")
	public void findClassGradesTree(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassGrades> classGrades = this.lessonService.findClassGrades(map);
			ArrayList<ClassGrades> classGradesList = (ArrayList<ClassGrades>) classGrades.getItems();

			// 处理list 添加层级Mark
			classGradesList = this.gradesTransformToLevelList(classGradesList, 0, 1, new ArrayList<ClassGrades>());

			// 得到分类数据List
			JsonResult.JsonResultInfo(response, classGradesList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加班级(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/saveClassGrades")
	public void saveClassGrades(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			String cover = ParameterFilter.emptyFilter("", "cover", request);
			String summary = ParameterFilter.emptyFilter("", "summary", request);
			String parentId = ParameterFilter.emptyFilter("", "parentId", request);
			String classGradesName = ParameterFilter.emptyFilter("", "classGradesName", request);
			String sortId = ParameterFilter.emptyFilter("", "sortId", request);
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String gradesType = ParameterFilter.emptyFilter("", "gradesType", request);
			String status = ParameterFilter.emptyFilter("0", "status", request);
			String auditingStatus = ParameterFilter.emptyFilter("1", "auditingStatus", request);
			String price = ParameterFilter.emptyFilter("0", "price", request);
			String id = ParameterFilter.emptyFilter("", "id", request);
			String classOpenDate = ParameterFilter.emptyFilter("", "classOpenDate", request);
			String joinStatus = ParameterFilter.emptyFilter("-9", "joinStatus", request);
			ClassGrades classGrades = new ClassGrades();
			if (null != id && !"".equals(id)) {
				classGrades.setId(Integer.parseInt(id));
			}else{
				Timestamp createTime = new Timestamp(System.currentTimeMillis());
				classGrades.setCreateTime(createTime);
			}
			Cookie[] enu = request.getCookies();
			String userId = "1";
			if (null == enu) {

			} else {
				for (int i = 0; i < enu.length; i++) {
					Cookie cookie = enu[i];
					String value = cookie.getValue();
					String name = cookie.getName();
					if ("adminUser".equals(name)) {
						userId = value;
					}
				}
			}
			classGrades.setSort(Integer.parseInt(sortId));
			classGrades.setCover(cover);
			classGrades.setParentId(Integer.parseInt(parentId));
			classGrades.setSummary(summary);
			classGrades.setClassGradesName(classGradesName);
			classGrades.setTeacherId(Integer.parseInt(teacherId));
			classGrades.setGradesType(gradesType);
			classGrades.setStatus(Integer.parseInt(status));
			
			classGrades.setAuditingStatus(Integer.parseInt(auditingStatus));
			classGrades.setPrice(Integer.parseInt(price));
			// 新增保存字段
			if (!"-9".equals(joinStatus)) {
				classGrades.setClassOpenDate(classOpenDate);
				classGrades.setJoinStatus(Integer.parseInt(joinStatus));
			}

			this.lessonService.saveClassGrades(classGrades);
			if (Integer.parseInt(userId) > 1000) {
				UserToClassGrades userToClassGrades = new UserToClassGrades();
				userToClassGrades.setUserId(Integer.parseInt(userId));
				userToClassGrades.setClassGradesId(classGrades.getId());
				this.lessonService.saveUserToClassGrades(userToClassGrades);
			}
			JsonResult.JsonResultInfo(response, classGrades);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 删除虚拟班级(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/deleteClassGrades")
	public void deleteClassGrades(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			this.lessonService.deleteClassGrades(id);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 添加班级(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/getClassGradesDetailInfo")
	public void getClassGradesInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			JSONObject result = this.lessonService.getClassGradesInfo(classGradesId);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 修改班级状态(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/switchClassGradesStatus")
	public void switchClassGradesStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String status = ParameterFilter.emptyFilter("", "status", request);
			this.lessonService.switchClassGradesStatus(id, status);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 机器人根据课堂ID获取课堂信息
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/findClassRoomByClassRoomId")
	public void findClassRoomByClassRoomId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classRoomId = ParameterFilter.emptyFilter("", "classRoomId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("classRoomId", classRoomId);
			map.put("page", page);
			map.put("pageSize", pageSize);

			Page result = this.lessonService.findClassRoomByClassRoomId(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 学生通过学生ID获取所有虚拟班级(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/getClassGradesByStudentId")
	public void getClassGradesByStudentId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String studentId = ParameterFilter.emptyFilter("", "studentId", request);
			String gradesType = ParameterFilter.emptyFilter("", "gradesType", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("studentId", studentId);
			map.put("gradesType", gradesType);
			map.put("page", page);
			map.put("pageSize", pageSize);

			Page result = this.lessonService.getClassGradesByStudentId(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 老师通过ID获取所有的班级列表(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/getClassGradesByTeacherId")
	public void getClassGradesByTeacherId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String gradesType = ParameterFilter.emptyFilter("", "gradesType", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("teacherId", teacherId);
			map.put("gradesType", gradesType);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page result = this.lessonService.getClassGradesByTeacherId(map);
			int unReadNum = this.videoService.getUnreadNum(teacherId);
			JsonResult.JsonResultInfoNew(response, result, unReadNum);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取虚拟班级列表(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/getClassGradesList")
	public void getClassGradesList(HttpServletRequest request, HttpServletResponse response) {
		try {
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			String name = ParameterFilter.emptyFilter("", "name", request);
			HashMap map = new HashMap();
			map.put("page", page);
			map.put("pageSize", pageSize);
			map.put("name", name);
			Page result = this.lessonService.getClassGradesList(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 提交审核(APP用,废弃)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/saveClassGradesAuditing")
	public void saveClassGradesAuditing(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String auditingStatus = ParameterFilter.emptyFilter("", "auditingStatus", request);
			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("auditingStatus", auditingStatus);
			this.lessonService.saveClassGradesAuditing(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据在线课堂ID获取已加入的班级信息{Tree结构}(APP用)
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/findClassGradesTreeByClassRoomId")
	public void findClassGradesTreeByClassRoomId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classRoomId", ParameterFilter.emptyFilter("-1", "classRoomId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassGrades> classGrades = this.lessonService.findClassGradesTreeByClassRoomId(map);
			ArrayList<ClassGrades> classGradesList = (ArrayList<ClassGrades>) classGrades.getItems();
			DataGenerator dataGenerator = new DataGenerator();
			dataGenerator.setClassGradess(classGradesList);
			MultipleTreeUtil multipleTree = new MultipleTreeUtil();
			multipleTree.setDataList(dataGenerator.getClassGradess());
			// 得到树形结构的数据分类
			JsonResult.JsonResultInfo(response, multipleTree.getMultipleTree(10));
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据在线课堂ID获取已加入的班级信息{List结构}（APP用）
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassGradesByClassRoomId")
	public void findClassGradesByClassRoomId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassGrades> classGradess = this.lessonService.findClassGradesByClassRoomId(map);
			JsonResult.JsonResultInfo(response, classGradess.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据班级ID获取班级学生列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassStudentsByClassGradesId")
	public void findClassStudentsByClassGradesId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classGradesId", ParameterFilter.emptyFilter("", "classGradesId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassStudentDto> classStudentDtos = this.lessonService.findClassStudentsByClassGradesId(map);
			JsonResult.JsonResultInfo(response, classStudentDtos.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据班级ID获取班级学生列表(加统计数据，班级总数，上课数，最后一次上课时间)
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassStudentsRecordByClassGradesId")
	public void findClassStudentsRecordByClassGradesId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classGradesId", ParameterFilter.emptyFilter("", "classGradesId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			JSONArray classStudentDtos = this.lessonService.findClassStudentsRecordByClassGradesId(map);
			JsonResult.JsonResultInfo(response, classStudentDtos);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂首页信息数据{Tree结构}
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/findClassRoomsIndexTree")
	public void findClassRoomsIndexTree(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page<ClassRoomCategory> classRoomCategorys = this.lessonService.findClassRoomsIndexList(map);
			ArrayList<ClassRoomCategory> classRoomCategoryList = (ArrayList<ClassRoomCategory>) classRoomCategorys
					.getItems();

			// 处理list 添加层级Mark
			classRoomCategoryList = this.classRoomCategoryTransformToLevelList(classRoomCategoryList, 0, 1,
					new ArrayList<ClassRoomCategory>());

			// 得到分类数据List
			JsonResult.JsonResultInfo(response, classRoomCategoryList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取在线课堂推送信息数据
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("/findClassRoomPushList")
	public void findClassRoomPushList(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			map.put("epalId", ParameterFilter.emptyFilter("-1", "epalId", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("-1", "classRoomId", request));
			Page<ClassRoomPush> classRoomPushs = this.lessonService.findClassRoomPushList(map);
			ArrayList<ClassRoomPush> classRoomPushList = (ArrayList<ClassRoomPush>) classRoomPushs.getItems();

			if ("-1".equals(map.get("epalId"))) {
				ArrayList<ClassRoomPush> roomPushs = new ArrayList<ClassRoomPush>();
				for (int i = 0; i < classRoomPushList.size(); i++) {
					if (classRoomPushList.get(i).getEpalId() == null) {
						roomPushs.add(classRoomPushList.get(i));
					}
				}
				classRoomPushList = roomPushs;
			}
			JsonResult.JsonResultInfo(response, classRoomPushList);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 根据班级ID获取在线课堂列表
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("findClassRoomsByClassGradesId")
	public void findClassRoomsByClassGradesId(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classGradesId", ParameterFilter.emptyFilter("", "classGradesId", request));
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("30", "pageSize", request));
			Page<ClassRoomDto> classRoomGradesRelaDtos = this.lessonService.findClassRoomsByClassGradesId(map);
			JsonResult.JsonResultInfo(response, classRoomGradesRelaDtos.getItems());
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	public ArrayList<ClassGrades> gradesTransformToLevelList(ArrayList<ClassGrades> bData, int parentId, int level,
			ArrayList<ClassGrades> fData) {
		for (int i = 0; i < bData.size(); i++) {
			ClassGrades classGrades = bData.get(i);
			if (classGrades.getParentId() == parentId) {
				classGrades.setMark(strRepeat(level));
				fData.add(classGrades);
				gradesTransformToLevelList(bData, classGrades.getId(), level + 1, fData);
			}
		}
		return fData;
	}

	private ArrayList<ClassRoomCategory> classRoomCategoryTransformToLevelList(ArrayList<ClassRoomCategory> bData,
			int parentId, int level, ArrayList<ClassRoomCategory> fData) {
		for (int i = 0; i < bData.size(); i++) {
			ClassRoomCategory classRoomCategory = bData.get(i);
			if (classRoomCategory.getParentId() == parentId) {
				classRoomCategory.setMark(strRepeat(level));
				fData.add(classRoomCategory);
				classRoomCategoryTransformToLevelList(bData, classRoomCategory.getId(), level + 1, fData);
			}
		}
		return fData;
	}

	public String strRepeat(int level) {
		String mark = "";
		for (int i = 1; i < level; i++) {
			mark += " - ";
		}
		return mark;
	}

	/**
	 * 获取学校班级列表
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getSchoolClassList")
	public void getSchoolClassList(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("page", ParameterFilter.emptyFilter("1", "page", request));
			map.put("pageSize", ParameterFilter.emptyFilter("1000", "pageSize", request));
			Page result = lessonService.getSchoolClassList(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存班级学生到课堂
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("savePushStudentToClass")
	public void savePushStudentToClass(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classIds", ParameterFilter.emptyFilter("", "classIds", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("classRoomName", ParameterFilter.emptyFilter("", "classRoomName", request));
			lessonService.savePushStudentToClass(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 批量绑定班级到课堂
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveBatchGradesToClassRoom")
	public void saveBatchGradesToClassRoom(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("classGradesIds", ParameterFilter.emptyFilter("", "classGradesIds", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			lessonService.saveBatchGradesToClassRoom(map);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 复制课堂到老师名下
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("copyClassRoomToTeacher")
	public void copyClassRoomToTearcher(HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("teacherId", ParameterFilter.emptyFilter("", "teacherId", request));
			map.put("classRoomId", ParameterFilter.emptyFilter("", "classRoomId", request));
			map.put("teacherName", ParameterFilter.emptyFilter("", "teacherName", request));
			lessonService.copyClassRoomToTearcher(map);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/*********************************
	 * 在线课堂评论 start
	 ***************************************/
	/**
	 * 保存或者修改在线课堂评论模板
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveOnlineClassCommentModel")
	public void saveCommentModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String modelName = ParameterFilter.emptyFilter("", "modelName", request);
			String content = ParameterFilter.emptyFilter("", "content", request);
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String modelType = ParameterFilter.emptyFilter("text", "modelType", request);
			String status = ParameterFilter.emptyFilter("1", "status", request);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentDate = sdf.format(d);
			OnlineClassCommentModel onlineClassCommentModel = new OnlineClassCommentModel();
			onlineClassCommentModel.setContent(content);
			onlineClassCommentModel.setEditDate(currentDate);

			if (!"".equals(id) && null != id) {
				onlineClassCommentModel.setId(Integer.parseInt(id));
			} else {
				onlineClassCommentModel.setId(null);
				onlineClassCommentModel.setInsertDate(currentDate);
			}
			onlineClassCommentModel.setStatus(Integer.parseInt(status));
			onlineClassCommentModel.setModelName(modelName);
			onlineClassCommentModel.setTeacherId(Integer.parseInt(teacherId));
			onlineClassCommentModel.setModelType(modelType);

			lessonService.saveCommentModel(onlineClassCommentModel);
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 删除在线课堂评论模板
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("deleteOnlineClassCommentModel")
	public void deleteOnlineClassCommentModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			lessonService.deleteTableRecord(id, "online_class_comment_model");
			JsonResult.JsonResultInfo(response, "OK");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 通过老师ID获取在线课堂评论模板
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getOnlineClassCommentModel")
	public void getOnlineClassCommentModel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("teacherId", teacherId);
			map.put("page", page);
			map.put("pageSize", pageSize);

			Page result = lessonService.getOnlineClassCommentModel(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存老师评价
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveOnlineClassComment")
	public void saveOnlineClassComment(HttpServletRequest request, HttpServletResponse response) {
		try {
			String teacherId = ParameterFilter.emptyFilter("", "teacherId", request);
			String score = ParameterFilter.emptyFilter("80", "score", request);
			String content = ParameterFilter.emptyFilter("", "content", request);
			String modelId = ParameterFilter.emptyFilter("1", "modelId", request);
			String studentIds = ParameterFilter.emptyFilter("", "studentIds", request);
			String title = ParameterFilter.emptyFilter("", "title", request);
			String commentSound = ParameterFilter.emptyFilter("", "commentSound", request);
			String gradesId = ParameterFilter.emptyFilter("", "gradesId", request);
			String startDate = ParameterFilter.emptyFilter("", "startDate", request);
			String endDate = ParameterFilter.emptyFilter("", "endDate", request);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String currentDate = df.format(new Date());
			OnlineClassComment onlineClassComment = new OnlineClassComment(Integer.parseInt(teacherId),
					Integer.parseInt(score), Integer.parseInt(modelId), Integer.parseInt(gradesId), title, content,
					studentIds, commentSound, startDate, endDate, currentDate);

			lessonService.saveOnlineClassComment(onlineClassComment);

			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 修改班级状态
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("updateClassGradesStatus")
	public void updateClassGradesStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String status = ParameterFilter.emptyFilter("", "status", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("status", status);
			map.put("page", page);
			map.put("pageSize", pageSize);

			ClassGrades classGrades = lessonService.updateClassGradesStatus(map);
			JsonResult.JsonResultInfo(response, classGrades);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 修改班级上课时间
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("updateClassGradesOpenDate")
	public void updateClassGradesOpenDate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = ParameterFilter.emptyFilter("", "id", request);
			String classOpenDate = ParameterFilter.emptyFilter("", "classOpenDate", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("30", "pageSize", request);
			HashMap map = new HashMap();
			map.put("id", id);
			map.put("classOpenDate", classOpenDate);
			map.put("page", page);
			map.put("pageSize", pageSize);

			ClassGrades classGrades = lessonService.updateClassGradesOpenDate(map);
			JsonResult.JsonResultInfo(response, classGrades);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存引用班级课程
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveQuoteClassCourse")
	public void saveQuoteClassCourse(HttpServletRequest request, HttpServletResponse response) {
		try {
			String targetClassGradesId = ParameterFilter.emptyFilter("", "targetClassGradesId", request);
			String sourceClassGradesId = ParameterFilter.emptyFilter("", "sourceClassGradesId", request);

			HashMap map = new HashMap();
			map.put("sourceClassGradesId", sourceClassGradesId);
			map.put("targetClassGradesId", targetClassGradesId);
			lessonService.saveQuoteClassCourse(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 通过班级ID获取班级课程列表
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getClassRoomListByClassGradesId")
	public void getClassRoomListByClassGradesId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("1000", "pageSize", request);

			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page result = lessonService.getClassRoomListByClassGradesId(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 通过过班级ID和课程ID获取学过的学生列表
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getStudentListByClassGradesIdAndClassCourseId")
	public void getStudentListByClassGradesIdAndClassCourseId(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String classCourseId = ParameterFilter.emptyFilter("", "classCourseId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("1000", "pageSize", request);

			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("classCourseId", classCourseId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page result = lessonService.getStudentListByClassGradesIdAndClassCourseId(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 通过过班级ID和日期获取学过的学生列表
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getStudentListByClassGradesIdAndDate")
	public void getStudentListByClassGradesIdAndDate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("1000", "pageSize", request);
			String date = ParameterFilter.emptyFilter("2000-05-02", "date", request);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("classGradesId", classGradesId);
			map.put("date", date);
			map.put("page", page);
			map.put("pageSize", pageSize);
			Page result = lessonService.getStudentListByClassGradesIdAndDate(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存评价
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveClassCourseComment")
	public void saveClassCourseComment(HttpServletRequest request, HttpServletResponse response) {
		try {
			String data = ParameterFilter.emptyFilter("", "data", request);
			JSONArray jsStr = JSONArray.fromObject(data);
			
			List<ClassCourseComment> classCourseComments = new ArrayList<ClassCourseComment>(jsStr.size());
			
			for (int i = 0; i < jsStr.size(); i++) {
				Integer id = Integer.parseInt(jsStr.getJSONObject(i).get("classCourseCommendId").toString());
				Integer classCourseId = Integer.parseInt(jsStr.getJSONObject(i).get("classCourseId").toString());
				String teacherSound = jsStr.getJSONObject(i).get("teacherSound").toString();
				String teacherScore = jsStr.getJSONObject(i).get("teacherScore").toString();
				Integer studentId = Integer.parseInt(jsStr.getJSONObject(i).get("studentId").toString());
				Integer commentTeacherId = 0;
				boolean hasTeacherId = false;
				if (jsStr.getJSONObject(i).get("commentTeacherId") != null) {
					hasTeacherId = true;
					commentTeacherId = Integer.parseInt(jsStr.getJSONObject(i).get("commentTeacherId").toString());
				}
				ClassCourseComment classCourseComment = new ClassCourseComment();
				if (id == 0) {
					classCourseComment.setId(null);
				} else {
					classCourseComment.setId(id);
				}
				classCourseComment.setClassCourseId(classCourseId);
				classCourseComment.setStudentId(studentId);
				classCourseComment.setTeacherScore(teacherScore);
				classCourseComment.setTeacherSound(teacherSound);
				// 生效时间设为当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				classCourseComment.setEffectiveDate(sdf.format(new Date()));
				if (hasTeacherId)
					classCourseComment.setCommentTeacherId(commentTeacherId);
				
				classCourseComments.add(classCourseComment);
			}
			
			lessonService.batchSaveClassCourseComment(classCourseComments,200);

			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	// @RequestMapping("testing")
	// @ResponseBody
	// public void test(Integer id,Integer classCourseId,String
	// teacherSound,String teacherScore,Integer studentId,Integer
	// commentTeacherId) {
	// try {
	// ClassCourseComment c = new ClassCourseComment();
	// c.setId(id);
	// c.setClassCourseId(classCourseId);
	// c.setTeacherScore(teacherScore);
	// c.setTeacherSound(teacherSound);
	// c.setStudentId(studentId);
	// c.setCommentTeacherId(commentTeacherId);
	// lessonService.saveClassCourseComment(c);
	// } catch (Exception e) {
	//
	// }
	// }

	/**
	 * 通过学生ID和课程ID获取单节课老师评价
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getClassCourseComment")
	public void getClassCourseComment(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classCourseId = ParameterFilter.emptyFilter("", "classCourseId", request);
			String studentId = ParameterFilter.emptyFilter("", "studentId", request);
			String page = ParameterFilter.emptyFilter("1", "page", request);
			String pageSize = ParameterFilter.emptyFilter("100", "pageSize", request);
			HashMap map = new HashMap();
			map.put("classCourseId", classCourseId);
			map.put("studentId", studentId);
			map.put("page", page);
			map.put("pageSize", pageSize);
			JSONArray result = lessonService.getClassCourseComment(map);
			JsonResult.JsonResultInfo(response, result);
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * ------------------------------自动评价相关 start------------------------------
	 */
	/**
	 * 自动评价
	 */
	@RequestMapping("autoEvalSetEffectiveDate")
	@ResponseBody
	public Map<String, Object> updateEffectiveDate(String teacherId, String gradeId, String courseId, String date,
			int h) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			// 查出该老师的评价模板
			HashMap<String, Object> map = new HashMap<>();
			map.put("gradeId", gradeId);
			map.put("teacherId", teacherId);
			List<EvalTemplate> templates = evalTemplateService.getByParam(map).getItems();
			if (templates.size() == 0) {
				result.put("code", 420);
				result.put("msg", "还未保存有评价模板，无法自动评价");
				return result;
			}
			// 根据班级id和时间确定学生
			map.put("classGradesId", gradeId);
			map.put("date", date);
			map.put("page", "1");
			map.put("pageSize", "1000");
			map.put("classCourseId", courseId);
			List<JSONObject> comments = new ArrayList<>();
			if (null != date && !"".equals(date)) {
				comments = lessonService.getStudentListByClassGradesIdAndDate(map).getItems();
			} else if (null != courseId && !"".equals(courseId))
				comments = lessonService.getStudentListByClassGradesIdAndClassCourseId(map).getItems();
			if (comments.size() == 0) {
				result.put("code", 403);
				result.put("msg", "未查到对应的评价数据");
				return result;
			}
			if (null == courseId || "".equals(courseId))
				courseId = comments.get(0).getString("classCourseId");
			// 将数据封装成对象
			List<ClassCourseComment> list = new ArrayList<>();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			for (int i = 0; i < comments.size(); i++) {
				JSONObject o = (JSONObject) comments.get(i);
				ClassCourseComment ccc = new ClassCourseComment();
				// 如果教师评分为空，则进行自动评分
				if ("".equals(o.getString("teacherScore")) || o.getString("teacherScore") == null
						|| "0".equals(o.getString("teacherScore"))) {
					ccc.setId(Integer.parseInt(o.getString("classCourseCommendId")));
					int score = Integer.parseInt(o.get("score").toString());
					if (score < 20)
						score = 20;

					ccc.setTeacherScore(score + "");
					try {
						ccc.setTeacherSound(evalTemplateService
								.getTemplateByScore(templates, Integer.parseInt(o.get("score").toString())).getSound());
					} catch (Exception e) {

						e.printStackTrace();
						result.put("code", 420);
						result.put("msg", "请先完善评价模板");
						return result;
					}
					ccc.setEffectiveDate(df.format(new Date(new Date().getTime() + h * 3600000)));
					ccc.setCommentTeacherId(Integer.parseInt(teacherId));
					// 如果Id = 0， 则新增
					if (ccc.getId() == 0) {
						ccc.setId(null);
						ccc.setClassCourseId(Integer.parseInt(courseId));
						ccc.setStudentId(Integer.parseInt(o.getString("studentId")));
					}
					list.add(ccc);
				}
			}
			// 保存相关数据
			for (ClassCourseComment classCourseComment : list) {
				if (classCourseComment.getId() != null) {
					classCourseComment = (ClassCourseComment) BeanModifyFilter.modifyFilter(classCourseComment,
							this.lessonService.getClassCourseCommentById(classCourseComment.getId()));
				}
				classCourseCommentService.saveClassCourseComment(classCourseComment);
			}
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;

	}

	@RequestMapping("addAutoEvalTemplate")
	@ResponseBody
	public Map<String, Object> addAutoEvalTemplate(EvalTemplate t) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			evalTemplateService.add(t);
			result.put("data", evalTemplateService.getById(t.getId()));
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}

	@RequestMapping("delAutoEvalTemplate")
	@ResponseBody
	public Map<String, Object> delAutoEvalTemplate(int id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			evalTemplateService.delById(id);
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}

	@RequestMapping("getAutoEvalTemplate")
	@ResponseBody
	public Map<String, Object> getAutoEvalTemplate(int teacherId, int classGradeId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("data", evalTemplateService.getByTIdAndGId(teacherId, classGradeId).getItems());
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}

	@RequestMapping("updateAutoEvalTemplate")
	@ResponseBody
	public Map<String, Object> updateAutoEvalTemplate(EvalTemplate t) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			evalTemplateService.update(t);
			result.put("data", evalTemplateService.getById(t.getId()));
			result.put("code", 200);
			result.put("msg", "处理成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", "网络出错，请稍后重试");
		}
		return result;
	}

	/**
	 * ------------------------------自动评价相关 end------------------------------
	 */

	/*********************************
	 * 在线课堂评论 end
	 ***************************************/

	/**
	 * 通过班级ID和学生ID获取所有老师的评价
	 *
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping("getCommentByClassGradeIdAndStudentId")
	@ResponseBody
	public HashMap<String, Object> getComByCIdAndSId(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<ClassCourseComment> list = new ArrayList<>();
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String studentId = ParameterFilter.emptyFilter("", "studentId", request);
			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("studentId", studentId);
			list = lessonService.getComByCIdAndSId(map).getItems();
			for (ClassCourseComment c : list) {
				c.setEffectiveDate(c.getEffectiveDate().substring(0, 19));
				c.setUpdateDate((c.getUpdateDate().substring(0, 19)));
				c.setInsertDate((c.getInsertDate().substring(0, 19)));
			}
		} catch (Exception e) {
			result.put("code", 500);
			result.put("msg", "服务器出错");
			e.printStackTrace();
		}
		result.put("data", list);
		result.put("code", 200);
		result.put("msg", "处理成功");

		return result;

	}

	/**
	 * 批量重置加班时间
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveJoinClassDate")
	public void saveJoinClassDate(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String joinClassDate = ParameterFilter.emptyFilter("", "joinClassDate", request);
			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("joinClassDate", joinClassDate);
			lessonService.saveJoinClassDate(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 修改班级审核状态
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("updateClassGradesAuditingStatus")
	public void updateClassGradesAuditingStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String auditingStatus = ParameterFilter.emptyFilter("", "auditingStatus", request);
			HashMap map = new HashMap();
			map.put("classGradesId", classGradesId);
			map.put("auditingStatus", auditingStatus);
			lessonService.updateClassGradesAuditingStatus(map);
			JsonResult.JsonResultInfo(response, "ok");
		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存FID和机器人绑定关系
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveDeviceBindStudentRelation")
	public void saveDeviceBindStudentRelation(HttpServletRequest request, HttpServletResponse response) {
		try {
			String epalId = ParameterFilter.emptyFilter("", "epalId", request);
			String fid = ParameterFilter.emptyFilter("", "fid", request);

			if ("".equals(epalId) || "".equals(fid)) {
				JSONObject result = new JSONObject();
				result.put("code", 200);
				result.put("code", "no paramater!");
				JsonResult.JsonResultInfo(response, result);
			} else {
				PublicRoomFidToStudent publicRoomFidToStudent = this.lessonService.getPublicRoomFidToStudent(fid);
				Integer studentId = publicRoomFidToStudent.getStudentId();
				ClassStudent classStudent = new ClassStudent();
				if (null != studentId || 0 != studentId) {
					ClassStudent classStudentTemp = this.lessonService.getClassStudent(studentId);
					classStudent.setEpalId(epalId);
					classStudent.setId(studentId);
					classStudent.setStudentType(2);
					classStudent = (ClassStudent) BeanModifyFilter.modifyFilter(classStudent, classStudentTemp);
					ClassStudent classStudentLast = this.lessonService.getClassStudentByEpalId(epalId);
					if (null != classStudentLast.getId() && !"".equals(classStudentLast.getId())) {
						classStudentLast.setEpalId(null);
						lessonService.saveClassStudent(classStudentLast);

					} else {

					}

					// 发送环信消息个上一个绑定的机器人，如果机器人是同一台，不发
					if (epalId.equals(classStudentTemp.getEpalId())) {

					} else {
						EasemobUtil easemobUtil = new EasemobUtil();
						String action = "lo_multi_inactivate:" + studentId;
						String[] user = new String[1];
						user[0] = classStudentTemp.getEpalId();
						easemobUtil.sendMessage(user, action);
					}
					lessonService.saveClassStudent(classStudent);
				}
				JsonResult.JsonResultInfo(response, classStudent);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JsonResult.JsonResultError(response, 1000);
		}

	}

	/**
	 * 保存电教室学生
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveEleStudent")
	public void saveEleStudent(HttpServletRequest request, HttpServletResponse response) {
		try {
			String classGradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String studentName = ParameterFilter.emptyFilter("", "studentName", request);
			String cardFid = ParameterFilter.emptyFilter("", "cardFid", request);
			String remark = ParameterFilter.emptyFilter("", "remark", request);

			ClassStudent classStudent = new ClassStudent();
			classStudent.setName(studentName);
			classStudent.setStudentType(2);
			this.lessonService.saveClassStudent(classStudent);
			Integer studentId = classStudent.getId();
			ClassGradesRela classGradesRela = new ClassGradesRela();
			classGradesRela.setClassGradesId(Integer.parseInt(classGradesId));
			classGradesRela.setClassStudentId(studentId);
			this.lessonService.saveClassGradesRela(classGradesRela);
			PublicRoomFidToStudent publicRoomFidToStudent = new PublicRoomFidToStudent();
			publicRoomFidToStudent.setCardFid(cardFid);
			publicRoomFidToStudent.setStudentId(studentId);
			publicRoomFidToStudent.setRemark(remark);
			this.lessonService.savepublicRoomFidToStudent(publicRoomFidToStudent);
			JsonResult.JsonResultInfo(response, "ok");

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 保存班级上课规则
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("saveStudyRule")
	public void saveStudyRule(HttpServletRequest request, HttpServletResponse response) {
		try {
			String gradesId = ParameterFilter.emptyFilter("", "classGradesId", request);
			String stdId = ParameterFilter.emptyFilter("", "studentId", request);
			String rule = ParameterFilter.emptyFilter("", "rule", request);
			String week = ParameterFilter.emptyFilter("", "week", request);
			String isTeacherDefault = ParameterFilter.emptyFilter("1", "isTeacherDefault", request);
			String id = ParameterFilter.emptyFilter(null, "id", request);
			String ruleType = ParameterFilter.emptyFilter("1", "ruleType", request);
			StdDiyStudyDay stdDiyStudyDay = new StdDiyStudyDay();

			stdDiyStudyDay.setGradeId(Integer.parseInt(gradesId));
			stdDiyStudyDay.setStdId(Integer.parseInt(stdId));
			stdDiyStudyDay.setRule(rule);
			stdDiyStudyDay.setWeek(week);
			if ("".equals(isTeacherDefault)) {

			} else {
				stdDiyStudyDay.setIsTeacherDefault(Integer.parseInt(isTeacherDefault));

			}

			stdDiyStudyDay.setRuleType(Integer.parseInt(ruleType));
			if (id != null) {
				stdDiyStudyDay.setId(Integer.parseInt(id));
				stdDiyStudyDay.setCreatTime(null);
				stdDiyStudyDay = (StdDiyStudyDay) BeanModifyFilter.modifyFilter(stdDiyStudyDay,
						this.lessonService.getStdDiyStudyDay(Integer.parseInt(id)));
			} else {
				stdDiyStudyDay.setId(null);
			}
			this.lessonService.saveStudyRule(stdDiyStudyDay);

			EasemobUtil easemobUtil = new EasemobUtil();
			String action = "lo_timetag_update:{\"classId\":" + gradesId + "}";
			String[] user = new String[1];
			ClassStudent classStudent = this.lessonService.getClassStudent(Integer.parseInt(stdId));
			user[0] = classStudent.getEpalId();
			easemobUtil.sendMessage(user, action);
			JsonResult.JsonResultInfo(response, stdDiyStudyDay);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}

	/**
	 * 获取班级上课规则
	 *
	 * @param request
	 * @param response
	 */

	@RequestMapping("getStudyRule")
	public void getStudyRule(HttpServletRequest request, HttpServletResponse response) {
		try {

			String stdId = ParameterFilter.emptyFilter("", "studentId", request);
			HashMap map = new HashMap();
			map.put("stdId", stdId);
			Page result = this.lessonService.getStudyRule(map);

			JsonResult.JsonResultInfo(response, result);

		} catch (Exception e) {
			JsonResult.JsonResultError(response, 1000);
			e.printStackTrace();
		}
	}
}
