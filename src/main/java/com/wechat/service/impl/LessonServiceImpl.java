package com.wechat.service.impl;

import com.wechat.dao.ClassCourseCommentDao;
import com.wechat.dao.LessonDao;
import com.wechat.entity.*;
import com.wechat.entity.dto.ClassRoomDto;
import com.wechat.entity.dto.ClassRoomIndexDto;
import com.wechat.entity.dto.ClassStudentDto;
import com.wechat.service.LessonService;
import com.wechat.util.Page;
import com.wechat.util.script.ScriptTransform;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonServiceImpl implements LessonService {

	@Autowired
	LessonDao lessonDao;

	@Resource
	private ClassCourseCommentDao classCourseCommentDao;

	@Override
	public void saveClassRoom(ClassRoom classRoom) {
		this.lessonDao.saveClassRoom(classRoom);
	}

	@Override
	public void saveClassScriptType(ClassScriptType classScriptType) {
		this.lessonDao.saveClassScriptType(classScriptType);
	}

	@Override
	public ClassScriptType getClassScriptType(int id) {
		return this.lessonDao.getClassScriptType(id);
	}

	@Override
	public ClassRoom getClassRoom(int id) {
		return this.lessonDao.getClassRoom(id);
	}

	@Override
	public void saveClassScriptNormal(ClassScriptNormal classScriptNormal) {
		this.lessonDao.saveClassScriptNormal(classScriptNormal);
	}

	@Override
	public ClassScriptNormal getClassScriptNormal(int id) {
		return lessonDao.getClassScriptNormal(id);
	}

	@Override
	public ClassScriptDone getClassScriptDone(int id) {
		return this.lessonDao.getClassScriptDone(id);
	}

	@Override
	public void saveClassScriptDone(ClassScriptDone classScriptDone) {
		this.lessonDao.saveClassScriptDone(classScriptDone);
	}

	@Override
	public ClassTeacher getClassTeacher(int id) {
		return this.lessonDao.getClassTeacher(id);
	}

	@Override
	public void saveClassTeacher(ClassTeacher classTeacher) {
		this.lessonDao.saveClassTeacher(classTeacher);

	}

	@Override
	public ClassStudent getClassStudent(int id) {
		return this.lessonDao.getClassStudent(id);
	}

	@Override
	public void saveClassStudent(ClassStudent classStudent) {
		this.lessonDao.saveClassStudent(classStudent);
	}

	@Override
	public ClassStudentRela getClassStudentRela(int id) {
		return this.lessonDao.getClassStudentRela(id);
	}

	@Override
	public void saveClassStudentRela(ClassStudentRela classStudentRela) {
		this.lessonDao.saveClassStudentRela(classStudentRela);

	}

	@Override
	public void deleteTableRecord(String id, String table) {
		this.lessonDao.deleteTableRecord(id, table);
	}

	@Override
	public ArrayList<ClassScriptType> findClassScriptTypes(
			HashMap<String, String> map) {
		return this.lessonDao.findClassScriptTypes(map);
	}

	@Override
	public Page<ClassScriptNormal> findClassRoomNormalScript(
			HashMap<String, String> map) {
		
		Page result = this.lessonDao.findClassRoomNormalScript(map);
		List<ClassScriptNormal> handResult = new ArrayList<ClassScriptNormal>();
		for(int i=0;i<result.getItems().size();i++){
			
			ClassScriptNormal temp = new ClassScriptNormal();
			
			ClassScriptNormal classScriptNormal = (ClassScriptNormal)result.getItems().get(i);
			
			temp.setClassRoomId(classScriptNormal.getId());
			temp.setClassScriptContent(classScriptNormal.getClassScriptContent());
			temp.setClassScriptTypeId(classScriptNormal.getClassScriptTypeId());
			temp.setCreateTime(classScriptNormal.getCreateTime());
			temp.setId(classScriptNormal.getId());
			temp.setSort(classScriptNormal.getSort());
			temp.setTotalTime(classScriptNormal.getTotalTime());
			
			
			String classScriptContent = temp.getClassScriptContent();
			if(classScriptContent!=null){
				if(classScriptContent.startsWith("[")){
					temp.setClassScriptContent(ScriptTransform.parseToOldScript(classScriptContent));
				}else{
					//如果是以｛开头的字符串，不做任何处理
				}
			}
			handResult.add(temp);
		}
		result.setItems(handResult);
		return result;
	}

	@Override
	public Page<ClassScriptDone> findClassRoomDoneScript(
			HashMap<String, String> map) {
		return this.lessonDao.findClassRoomDoneScript(map);
	}

	@Override
	public Page<ClassRoomDto> findStudiedClassRooms(
			HashMap<String, String> map) {
		return this.lessonDao.findStudiedClassRooms(map);
	}

	@Override
	public Page<ClassRoomDto> findClassRooms(HashMap<String, String> map) {
		return this.lessonDao.findClassRooms(map);
	}

	@Override
	public ArrayList<ClassTeacher> findClassTeachers(HashMap<String, String> map) {
		return this.lessonDao.findClassTeachers(map);
	}

	@Override
	public Page<ClassStudentDto> findClassStudents(HashMap<String, String> map) {
		return this.lessonDao.findClassStudents(map);
	}

	@Override
	public Page<ClassRoomIndexDto> findClassRoomsIndex(
			HashMap<String, String> map) {
		return this.lessonDao.findClassRoomsIndex(map);
	}

	@Override
	public Page<ClassStudent> findClassStudentByEpalId(
			HashMap<String, String> map) {
		return this.lessonDao.findClassStudentByEpalId(map);
	}

	@Override
	public void signOutClassRoom(HashMap map) {
		this.lessonDao.signOutClassRoom(map);
	}

	@Override
	public Page<ClassRoomIndexDto> findClassRoomCategorys(
			HashMap<String, String> map) {
		return this.lessonDao.findClassRoomCategorys(map);
	}

	@Override
	public Page<ClassGrades> findClassGrades(HashMap<String, String> map) {
		return this.lessonDao.findClassGrades(map);
	}

	@Override
	public ClassGrades getClassGrades(int id) {
		return this.lessonDao.getClassGrades(id);
	}

	@Override
	public void saveClassGrades(ClassGrades classGrades) {
		this.lessonDao.saveClassGrades(classGrades);
	}

	@Override
	public void saveClassGradesRela(ClassGradesRela classGradesRela) {
		this.lessonDao.saveClassGradesRela(classGradesRela);
	}

	@Override
	public ClassRoomGradesRela getClassRoomGradesRela(int id) {
		return this.lessonDao.getClassRoomGradesRela(id);
	}

	@Override
	public void saveClassRoomGradesRela(ClassRoomGradesRela classRoomGradesRela) {
		this.lessonDao.saveClassRoomGradesRela(classRoomGradesRela);
	}

	@Override
	public ClassGradesRela getClassGradesRela(int id) {
		return this.lessonDao.getClassGradesRela(id);
	}

	@Override
	public Page<ClassRoomDto> findClassRoomsByClassGradesId(
			HashMap<String, String> map) {
		return this.lessonDao.findClassRoomsByClassGradesId(map);
	}

	@Override
	public ClassRoomSign getClassRoomSign(int id) {
		return this.lessonDao.getClassRoomSign(id);
	}

	@Override
	public void saveClassRoomSign(ClassRoomSign classRoomSign) {
		this.lessonDao.saveClassRoomSign(classRoomSign);
	}

	@Override
	public ClassRoomPush getClassRoomPush(int id) {
		return this.lessonDao.getClassRoomPush(id);
	}

	@Override
	public void saveClassRoomPush(ClassRoomPush classRoomPush,HashMap map) {
		this.lessonDao.saveClassRoomPush(classRoomPush,map);
	}

	@Override
	public Page<ClassRoomPush> findClassRoomPushList(HashMap<String, String> map) {
		return this.lessonDao.findClassRoomPushList(map);
	}

	@Override
	public Page getSchoolClassList(HashMap<String, String> map) {
		return this.lessonDao.getSchoolClassList(map);
	}

	@Override
	public void savePushStudentToClass(HashMap<String, String> map) {
		this.lessonDao.savePushStudentToClass(map);
	}

	@Override
	public Page<ClassRoomCategory> findClassRoomsIndexList(
			HashMap<String, String> map) {
		return this.lessonDao.findClassRoomsIndexList(map);
	}

	@Override
	public ClassRoomCategory getClassRoomCategory(int id) {
		return this.lessonDao.getClassRoomCategory(id);
	}

	@Override
	public void saveClassRoomCategory(ClassRoomCategory classRoomCategory) {
		this.lessonDao.saveClassRoomCategory(classRoomCategory);
	}

	@Override
	public Page findClassRoomsByNameOrCate(HashMap map) {
		return this.lessonDao.findClassRoomsByNameOrCate(map);
	}

	@Override
	public Page<ClassStudentRela> findStudentByRoomIdAndStudentId(
			HashMap<String, String> map) {
		return this.lessonDao.findStudentByRoomIdAndStudentId(map);
	}

	@Override
	public Page<ClassGrades> findClassGradesByClassRoomId(
			HashMap<String, String> map) {
		return this.lessonDao.findClassGradesByClassRoomId(map);
	}

	@Override
	public Page<ClassStudentDto> findClassStudentsByClassGradesId(
			HashMap<String, String> map) {
		return this.lessonDao.findClassStudentsByClassGradesId(map);
	}

	@Override
	public Page<ClassGrades> findClassGradesTreeByClassRoomId(
			HashMap<String, String> map) {
		return this.lessonDao.findClassGradesTreeByClassRoomId(map);
	}

	@Override
	public void saveBatchGradesToClassRoom(HashMap<String, String> map) {
		this.lessonDao.saveBatchGradesToClassRoom(map);
	}

	@Override
	public ClassCourse getClassCourse(int id) {
		return this.lessonDao.getClassCourse(id);
	}

	@Override
	public void saveClassCourse(ClassCourse classCourse) {
		this.lessonDao.saveClassCourse(classCourse);
	}

	@Override
	public Page<ClassCourse> findClassCourses(HashMap<String, String> map) {
		List<ClassCourse> courses = new ArrayList<>();

		Page dataPage = this.lessonDao.findClassCourses(map);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		for (int i=0;i<dataList.size();i++) {

			ClassCourse classCourse = new ClassCourse();

			Object[] obj = dataList.get(i);
			classCourse.setClassGradesId((Integer) obj[5]);
			classCourse.setClassRoomId((Integer) obj[4]);
			classCourse.setCover((String) obj[6]);
			classCourse.setCreateTime((Timestamp)obj[7]);
			classCourse.setDoDay((Integer) obj[3]);
			classCourse.setDoSlot((Integer) obj[2]);
			classCourse.setDoTitle((String) obj[1]);
			classCourse.setId((Integer) obj[0]);

			courses.add(classCourse);
		}
		dataPage.setItems(courses);
		return dataPage;
	}

	@Override
	public void delClassRoomFromClassCourse(HashMap map) {
		this.lessonDao.delClassRoomFromClassCourse(map);
	}

	@Override
	public boolean saveBatchClassRoomToClassCourse(HashMap<String, String> map) {
		return this.lessonDao.saveBatchClassRoomToClassCourse(map);
	}

	@Override
	public ArrayList<ClassCourse> saveBatchClassCourse(
			HashMap<String, String> map) {
		return this.lessonDao.saveBatchClassCourse(map);
	}

	@Override
	public ClassRoomTemp getClassRoomTemp(int id) {
		return this.lessonDao.getClassRoomTemp(id);
	}

	@Override
	public void saveClassRoomTemp(ClassRoomTemp classRoom) {
		this.lessonDao.saveClassRoomTemp(classRoom);
	}

	@Override
	public ClassScriptNormalTemp getClassScriptNormalTemp(int id) {
		return this.lessonDao.getClassScriptNormalTemp(id);
	}

	@Override
	public void saveClassScriptNormalTemp(
			ClassScriptNormalTemp classScriptNormal) {
		this.lessonDao.saveClassScriptNormalTemp(classScriptNormal);
	}


	@Override
	public Page getClassGradesByStudentId(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassGradesByStudentId(map);
	}

	@Override
	public void deleteClassGrades(String id) {
		// TODO Auto-generated method stub
		this.lessonDao.deleteTableRecord(id,"class_grades");
	}

	@Override
	public void switchClassGradesStatus(String id, String status) {
		// TODO Auto-generated method stub
		this.lessonDao.switchClassGradesStatus(id,status);
	}

	@Override
	public Page getClassGradesByTeacherId(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassGradesByTeacherId(map);
	}


	@Override
	public Page<ClassRoomTemp> findClassRoomTemps(HashMap<String, String> map) {
		return this.lessonDao.findClassRoomTemps(map);
	}

	@Override
	public Page<ClassScriptNormalTemp> findClassScriptNormalTemp(
			HashMap<String, String> map) {
		return this.lessonDao.findClassScriptNormalTemp(map);
	}

	@Override
	public ClassCourseSchedule getClassCourseSchedule(int id) {
		return this.lessonDao.getClassCourseSchedule(id);
	}

	@Override
	public void saveClassCourseSchedule(ClassCourseSchedule classCourseSchedule) {
		this.lessonDao.saveClassCourseSchedule(classCourseSchedule);
	}

	@Override
	public Page<ClassCourseSchedule> findClassCourseSchedules(
			HashMap<String, String> map) {
		return this.lessonDao.findClassCourseSchedules(map);
	}

	@Override
	public ClassSlot getClassSlot(int id) {
		return this.lessonDao.getClassSlot(id);
	}

	@Override
	public void saveClassSlot(ClassSlot classSlot) {
		this.lessonDao.saveClassSlot(classSlot);
	}

	@Override
	public Page<ClassSlot> findClassSlots(HashMap<String, String> map) {
		return this.lessonDao.findClassSlots(map);
	}

	@Override
	public void saveStudentToGrades(HashMap map) {
		this.lessonDao.saveStudentToGrades(map);
	}

	@Override
	public Page findClassRoomByClassRoomId(HashMap map) {
		return this.lessonDao.findClassRoomByClassRoomId(map);
	}

	@Override
	public Page getClassGradesList(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassGradesList(map);
	}

	@Override
	public void saveClassGradesAuditing(HashMap map) {
		this.lessonDao.saveClassGradesAuditing(map);
	}

	@Override
	public void copyClassRoomToTearcher(HashMap<String, String> map) {
		this.lessonDao.copyClassRoomToTearcher(map);
	}

	@Override
	public void delClassRoomTemp(HashMap map) {
		this.lessonDao.delClassRoomTemp(map);
	}

	@Override
	public void delClassScriptNormalTemp(HashMap map) {
		this.lessonDao.delClassScriptNormalTemp(map);
	}

	@Override
	public void quitGradesByStudentId(HashMap<String, String> map) {
		// 退出班级
		this.lessonDao.quitGradesByStudentId(map);
	}

	@Override
	public void saveCommentModel(OnlineClassCommentModel onlineClassCommentModel) {
		// 保存评价模板
		this.lessonDao.saveCommentModel(onlineClassCommentModel);

	}

	@Override
	public Page getOnlineClassCommentModel(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getOnlineClassCommentModel(map);
	}

	@Override
	public void saveStudentFromGradesByteacher(
			OnlineClassDeleteStudentRecord onlineClassDeleteStudentRecord) {
		// TODO Auto-generated method stub
		this.lessonDao.saveStudentFromGradesByteacher(onlineClassDeleteStudentRecord);
	}

	@Override
	public void saveOnlineClassComment(OnlineClassComment onlineClassComment) {
		// TODO Auto-generated method stub
		this.lessonDao.saveOnlineClassComment(onlineClassComment);
	}

	@Override
	public JSONObject getClassGradesInfo(String classGradesId) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassGradesInfo(classGradesId);
	}

	@Override
	public void saveClassCourseScoreRecord(
			ClassCourseScoreRecord classCourseScoreRecord) {
		// TODO Auto-generated method stub
		this.lessonDao.saveClassCourseScoreRecord(classCourseScoreRecord);

	}

	@Override
	public Page getSignOutClassGradesListBystudentId(
			HashMap<String, String> map) {
		// 通过学生ID获取已经退出的班级列表
		return this.lessonDao.getSignOutClassGradesListBystudentId(map);
	}

	@Override
	public JSONObject getClassCourseByClassGradesId(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassCourseByClassGradesId(map);
	}

	@Override
	public Page searchClassGrades(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.searchClassGrades(map);
	}

	@Override
	public ClassGrades updateClassGradesStatus(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.updateClassGradesStatus(map);
	}

	@Override
	public ClassGrades updateClassGradesOpenDate(HashMap map) {
		// 修改班级开课时间
		return this.lessonDao.updateClassGradesOpenDate(map);
	}

	@Override
	public void saveQuoteClassCourse(HashMap map) {
		// 引用课程表
		this.lessonDao.saveQuoteClassCourse(map);

	}

	@Override
	public JSONArray findClassStudentsRecordByClassGradesId(
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.lessonDao.findClassStudentsRecordByClassGradesId(map);
	}

	@Override
	public Page getClassRoomListByClassGradesId(HashMap map) {
		// 通过班级ID获取班级课程列表
		return this.lessonDao.getClassRoomListByClassGradesId(map);
	}

	@Override
	public Page getStudentListByClassGradesIdAndClassCourseId(HashMap map) {
		// 通过过班级ID和课程ID获取学过的学生列表
		return this.lessonDao.getStudentListByClassGradesIdAndClassCourseId(map);
	}

	@Override
	public void saveClassCourseComment(ClassCourseComment classCourseComment) {
		// TODO Auto-generated method stub
		this.lessonDao.saveClassCourseComment(classCourseComment);
	}

	@Override
	public void saveJoinClassDate(HashMap map) {
		// TODO Auto-generated method stub
		this.lessonDao.saveJoinClassDate(map);
	}

	@Override
	public JSONArray getClassCourseComment(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassCourseComment(map);
	}

	@Override
	public void updateClassGradesAuditingStatus(HashMap map) {
		// TODO Auto-generated method stub
		this.lessonDao.updateClassGradesAuditingStatus(map);
	}

	@Override
	public PublicRoomFidToStudent getPublicRoomFidToStudent(String fid) {
		// TODO Auto-generated method stub
		return this.lessonDao.getPublicRoomFidToStudent(fid);
	}

	@Override
	public void saveUserToClassGrades(UserToClassGrades userToClassGrades) {
		// TODO Auto-generated method stub
		this.lessonDao.saveUserToClassGrades(userToClassGrades);
	}

	@Override
	public void savepublicRoomFidToStudent(
			PublicRoomFidToStudent publicRoomFidToStudent) {
		// TODO Auto-generated method stub
		this.lessonDao.savepublicRoomFidToStudent(publicRoomFidToStudent);
	}

	public Page getTeacherList(HashMap map) {
		// TODO 获取老师列表
		return this.lessonDao.getTeacherList(map);
	}

	@Override
	public Page searchClassRoom(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.searchClassRoom(map);
	}

	@Override
	public Page searchStudentList(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.searchStudentList(map);
	}

	@Override
	public Page getStudentListByClassGradesIdAndDate(HashMap<String, Object> map) {
		// 通过过班级ID和日期获取学过的学生列表
		return this.lessonDao.getStudentListByClassGradesIdAndDate(map);
	}

	@Override
	public Page<ClassCourseComment> getComByCIdAndSId(HashMap<String, String> map) {
		return this.lessonDao.getComByCIdAndSId(map);
	}
	@Override
	public Object getClassCourseCommentById(Integer id) {

		return classCourseCommentDao.getById(id);
	}

	@Override
	public JSONArray getClassCourseComment(Integer integer) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean findClassCourseRecord(String classCourseId) {
		// TODO Auto-generated method stub
		return this.lessonDao.findClassCourseRecord(classCourseId);
	}

	@Override
	public void saveStudyRule(StdDiyStudyDay stdDiyStudyDay) {
		// TODO Auto-generated method stub
		this.lessonDao.saveStudyRule(stdDiyStudyDay);
	}

	@Override
	public Page getStudyRule(HashMap map) {
		// TODO Auto-generated method stub
		return this.lessonDao.getStudyRule(map);
	}

	@Override
	public StdDiyStudyDay getStdDiyStudyDay(int id) {
		// TODO Auto-generated method stub
		return this.lessonDao.getStdDiyStudyDay(id);
	}

	@Override
	public ClassStudent getClassStudentByEpalId(String epalId) {
		// TODO Auto-generated method stub
		return this.lessonDao.getClassStudentByEpalId(epalId);
	}

	@Override
	public void deleteClassScriptNormal(HashMap map) {
		// TODO Auto-generated method stub
		this.lessonDao.deleteClassScriptNormal(map);
	}

	@Override
	public ClassStudent getStudentByStudentId(String studentId) {
		// TODO Auto-generated method stub
		return this.lessonDao.getStudentByStudentId(studentId);
	}

	@Override
	public void addAudioRelation(String audioIds, Integer classRoomId) {
		
		this.lessonDao.addAudioRelation(audioIds, classRoomId);
	}

	@Override
	public void batchSaveClassCourseComment(List<ClassCourseComment> classCourseComments,int batchSize) {
		// TODO Auto-generated method stub
		this.lessonDao.batchSaveClassCourseComment(classCourseComments, batchSize);
	}


}
