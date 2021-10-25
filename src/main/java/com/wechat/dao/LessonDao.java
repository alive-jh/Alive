package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.entity.dto.ClassRoomDto;
import com.wechat.entity.dto.ClassRoomIndexDto;
import com.wechat.entity.dto.ClassStudentDto;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface LessonDao {

	void saveClassRoom(ClassRoom classRoom);

	void saveClassScriptType(ClassScriptType classScriptType);

	ClassScriptType getClassScriptType(int id);

	ClassRoom getClassRoom(int id);

	void saveClassScriptNormal(ClassScriptNormal classScriptNormal);

	ClassScriptNormal getClassScriptNormal(int id);

	ClassScriptDone getClassScriptDone(int id);

	void saveClassScriptDone(ClassScriptDone classScriptDone);

	ClassTeacher getClassTeacher(int id);

	void saveClassTeacher(ClassTeacher classTeacher);

	ClassStudent getClassStudent(int id);

	void saveClassStudent(ClassStudent classStudent);

	ClassStudentRela getClassStudentRela(int id);

	void saveClassStudentRela(ClassStudentRela classStudentRela);

	void deleteTableRecord(String id, String table);

	ArrayList<ClassScriptType> findClassScriptTypes(HashMap<String, String> map);

	Page<ClassScriptNormal> findClassRoomNormalScript(
			HashMap<String, String> map);

	Page<ClassScriptDone> findClassRoomDoneScript(HashMap<String, String> map);

	Page<ClassRoomDto> findStudiedClassRooms(HashMap<String, String> map);

	Page<ClassRoomDto> findClassRooms(HashMap<String, String> map);

	ArrayList<ClassTeacher> findClassTeachers(HashMap<String, String> map);

	Page<ClassStudentDto> findClassStudents(HashMap<String, String> map);

	Page<ClassRoomIndexDto> findClassRoomsIndex(HashMap<String, String> map);

	Page<ClassStudent> findClassStudentByEpalId(HashMap<String, String> map);

	void signOutClassRoom(HashMap map);

	Page<ClassRoomIndexDto> findClassRoomCategorys(HashMap<String, String> map);

	Page<ClassGrades> findClassGrades(HashMap<String, String> map);

	ClassGrades getClassGrades(int id);

	void saveClassGrades(ClassGrades classGrades);

	void saveClassGradesRela(ClassGradesRela classGradesRela);

	ClassRoomGradesRela getClassRoomGradesRela(int id);

	void saveClassRoomGradesRela(ClassRoomGradesRela classRoomGradesRela);

	ClassGradesRela getClassGradesRela(int id);

	Page<ClassRoomDto> findClassRoomsByClassGradesId(
			HashMap<String, String> map);

	ClassRoomSign getClassRoomSign(int id);

	void saveClassRoomSign(ClassRoomSign classRoomSign);

	ClassRoomPush getClassRoomPush(int id);

	void saveClassRoomPush(ClassRoomPush classRoomPush,HashMap map);

	Page<ClassRoomPush> findClassRoomPushList(HashMap<String, String> map);

	Page getSchoolClassList(HashMap<String, String> map);

	void savePushStudentToClass(HashMap<String, String> map);

	Page<ClassRoomCategory> findClassRoomsIndexList(HashMap<String, String> map);

	ClassRoomCategory getClassRoomCategory(int id);

	void saveClassRoomCategory(ClassRoomCategory classRoomCategory);

	Page findClassRoomsByNameOrCate(HashMap map);

	Page<ClassStudentRela> findStudentByRoomIdAndStudentId(
			HashMap<String, String> map);

	Page<ClassGrades> findClassGradesByClassRoomId(HashMap<String, String> map);

	Page<ClassStudentDto> findClassStudentsByClassGradesId(
			HashMap<String, String> map);

	Page<ClassGrades> findClassGradesTreeByClassRoomId(
			HashMap<String, String> map);

	void saveBatchGradesToClassRoom(HashMap<String, String> map);

	ClassCourse getClassCourse(int id);

	void saveClassCourse(ClassCourse classCourse);

	Page findClassCourses(HashMap<String, String> map);

	void delClassRoomFromClassCourse(HashMap map);

	boolean saveBatchClassRoomToClassCourse(HashMap<String, String> map);

	ArrayList<ClassCourse> saveBatchClassCourse(HashMap<String, String> map);

	Page getClassGradesByStudentId(HashMap map);

	ClassRoomTemp getClassRoomTemp(int id);

	void deleteClassGrades(String id);

	void switchClassGradesStatus(String id, String status);

	Page getClassGradesByTeacherId(HashMap map);

	void saveClassRoomTemp(ClassRoomTemp classRoom);

	ClassScriptNormalTemp getClassScriptNormalTemp(int id);

	void saveClassScriptNormalTemp(ClassScriptNormalTemp classScriptNormal);

	Page<ClassRoomTemp> findClassRoomTemps(HashMap<String, String> map);

	Page<ClassScriptNormalTemp> findClassScriptNormalTemp(
			HashMap<String, String> map);

	ClassCourseSchedule getClassCourseSchedule(int id);

	void saveClassCourseSchedule(ClassCourseSchedule classCourseSchedule);


	Page<ClassCourseSchedule> findClassCourseSchedules(
			HashMap<String, String> map);

	ClassSlot getClassSlot(int id);

	void saveClassSlot(ClassSlot classSlot);

	Page<ClassSlot> findClassSlots(HashMap<String, String> map);

	void saveStudentToGrades(HashMap map);

	Page findClassRoomByClassRoomId(HashMap map);

	Page getClassGradesList(HashMap map);

	void saveClassGradesAuditing(HashMap map);

	void copyClassRoomToTearcher(HashMap<String, String> map);

	void delClassRoomTemp(HashMap map);

	void delClassScriptNormalTemp(HashMap map);

	void quitGradesByStudentId(HashMap<String, String> map);

	void saveCommentModel(OnlineClassCommentModel onlineClassCommentModel);

	Page getOnlineClassCommentModel(HashMap map);

	void saveStudentFromGradesByteacher(
			OnlineClassDeleteStudentRecord onlineClassDeleteStudentRecord);

	void saveOnlineClassComment(OnlineClassComment onlineClassComment);

	JSONObject getClassGradesInfo(String classGradesId);

	void saveClassCourseScoreRecord(
			ClassCourseScoreRecord classCourseScoreRecord);

	Page getSignOutClassGradesListBystudentId(HashMap<String, String> map);

	JSONObject getClassCourseByClassGradesId(HashMap map);

	Page searchClassGrades(HashMap map);

	ClassGrades updateClassGradesStatus(HashMap map);

	ClassGrades updateClassGradesOpenDate(HashMap map);

	void saveQuoteClassCourse(HashMap map);

	JSONArray findClassStudentsRecordByClassGradesId(HashMap<String, String> map);

	Page getClassRoomListByClassGradesId(HashMap map);

	Page getStudentListByClassGradesIdAndClassCourseId(HashMap map);

	void saveClassCourseComment(ClassCourseComment classCourseComment);

	void saveJoinClassDate(HashMap map);

	JSONArray getClassCourseComment(HashMap map);

	void updateClassGradesAuditingStatus(HashMap map);

	PublicRoomFidToStudent getPublicRoomFidToStudent(String fid);

	void saveUserToClassGrades(UserToClassGrades userToClassGrades);

	void savepublicRoomFidToStudent(
			PublicRoomFidToStudent publicRoomFidToStudent);

	Page getTeacherList(HashMap map);

	Page searchClassRoom(HashMap map);

	Page searchStudentList(HashMap map);

	Page getStudentListByClassGradesIdAndDate(HashMap<String, Object> map);

	Page<ClassCourseComment> getComByCIdAndSId(HashMap<String, String> map);

	boolean findClassCourseRecord(String classCourseId);

	void saveStudyRule(StdDiyStudyDay stdDiyStudyDay);

	Page getStudyRule(HashMap map);

	StdDiyStudyDay getStdDiyStudyDay(int id);

	ClassStudent getClassStudentByEpalId(String epalId);


	void deleteClassScriptNormal(HashMap map);

	ClassStudent getStudentByStudentId(String studentId);

	void addAudioRelation(String audioIds, Integer classRoomId);

	void batchSaveClassCourseComment(List<ClassCourseComment> classCourseComments, int batchSize);

}
