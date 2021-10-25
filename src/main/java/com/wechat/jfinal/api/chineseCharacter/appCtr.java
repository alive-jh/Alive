package com.wechat.jfinal.api.chineseCharacter;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.wechat.jfinal.annotation.EmptyParaValidate;
import com.wechat.jfinal.common.utils.web.Result;
import com.wechat.jfinal.model.ClassCourse;
import com.wechat.jfinal.model.ClassGrades;
import com.wechat.jfinal.model.ClassProductOrder;
import com.wechat.jfinal.model.ClassRoom;
import com.wechat.jfinal.model.ClassRoomPractice;
import com.wechat.jfinal.model.ClassRoomPracticePic;
import com.wechat.jfinal.model.ClassStudent;
import com.wechat.jfinal.model.GradeHomework;
import com.wechat.jfinal.model.Label;
import com.wechat.jfinal.model.MallProductCollector;
import com.wechat.jfinal.model.Mallproduct;
import com.wechat.util.CommonUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class appCtr extends Controller {

	/*public void indexLabel() {
		List<Label> labels = Label.dao.findAll();
		Result.ok(labels, this);
	}

	public void syncLesson() {

		int label = getParaToInt("label");
		int student = getParaToInt("student");
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);

		Page<Mallproduct> page = Mallproduct.dao.findGradeProductsByLabel(label, pageNumber, pageSize);

		List<ClassProductOrder> classProductOrders = ClassProductOrder.dao.findByStudent(student);

		for (Mallproduct mallproduct : page.getList()) {

			mallproduct.put("hasBouth", false);

			for (ClassProductOrder classProductOrder : classProductOrders) {
				if (mallproduct.getId().equals(classProductOrder.getProductId())
						&& classProductOrder.getStatus() == 1) {
					mallproduct.put("hasBought", true);
				}
			}
		}

		Result.ok(page, this);

	}

	public void lessonPack() {

		int label = getParaToInt("label");
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);

		Page<Mallproduct> page = Mallproduct.dao.findLessonPackByLabel(label, pageNumber, pageSize);

		Result.ok(page, this);

	}

	public void lessonCourse() {

		Mallproduct mallproduct = Mallproduct.dao.findById(getParaToInt("id"));

		List<ClassRoom> classRooms = null;

		if (mallproduct == null) {
			Result.error(20501, "找不到课程商品", this);
			return;
		} else if (mallproduct.getClassGradeId() > 0) {

			List<ClassCourse> courses = ClassGrades.dao.findCourse(mallproduct.getClassGradeId());

			classRooms = new ArrayList<ClassRoom>(courses.size());

			for (ClassCourse classCourse : courses) {
				ClassRoom classRoom = new ClassRoom();
				classRoom.setId(classCourse.getClassRoomId());
				classRoom.setClassName(classCourse.getDoTitle());
				classRooms.add(classRoom);
			}

		} else if (mallproduct.getClassRoomPackage() > 0) {

			classRooms = ClassRoom.dao.findByLessonPack(mallproduct.getClassRoomPackage());

		} else {
			Result.error(20502, "不是课程类商品", this);
			return;
		}

		JSONObject json = new JSONObject();
		json.put("classRooms", Result.makeupList(classRooms));
		json.put("mallproduct", mallproduct);
		json.put("hasBought", ClassProductOrder.dao.hasBought(getParaToInt("student"), mallproduct.getId()));

		Result.ok(json, this);

	}

	public void productCollectList() {
		Result.ok(MallProductCollector.dao.findByStudent(getParaToInt("student")), this);
	}

	public void saveProductCollect() {

		MallProductCollector.dao.update(getParaToInt("student"), getParaToInt("product"));
		Result.ok(MallProductCollector.dao.findByStudent(getParaToInt("student")), this);

	}

	public void saveLessonPractice() {

		JSONObject data = JSONObject.fromObject(getPara("data"));

		JSONArray picArray = data.getJSONArray("pics");
		List<ClassRoomPracticePic> classRoomPracticesPics = new ArrayList<ClassRoomPracticePic>(picArray.size());

		ClassRoomPractice classRoomPractice = new ClassRoomPractice();
		classRoomPractice.setClassRoomId(data.getInt("classRoom"));
		classRoomPractice.setStudentId(data.getInt("student"));
		classRoomPractice.setText(data.getString("text")).save();

		for (int i = 0; i < picArray.size(); i++) {

			ClassRoomPracticePic classRoomPracticePic = new ClassRoomPracticePic();
			classRoomPracticePic.setClassRoomPracticeId(classRoomPractice.getId());
			classRoomPracticePic.setPic(picArray.getString(i));
			classRoomPracticesPics.add(classRoomPracticePic);

		}

		Db.batchSave(classRoomPracticesPics, 200);

	}

	@EmptyParaValidate(params = { "student", "classroom" })
	public void lessonPractice() {

		int student = getParaToInt("student");
		int classroom = getParaToInt("classroom");

		List<ClassRoomPractice> classRoomPractices = ClassRoomPractice.dao.findPractice(student, classroom);

		List<ClassRoomPracticePic> classRoomPracticePics = ClassRoomPracticePic.dao.findPracticePic(student, classroom);

		JSONObject json = new JSONObject();
		json.put("classRoomPractices", classRoomPractices);
		json.put("classRoomPracticePics", classRoomPracticePics);

		Result.ok(json, this);

	}

	public void getLessonPractice() {
		int classroom = getParaToInt("classroom");
		
		int pageNumber = getParaToInt("pageNumber");
		int pageSize = getParaToInt("pageSize");
		
		Page<ClassRoomPractice> page = ClassRoomPractice.dao.paginateByClassRoom(pageNumber, pageSize, classroom);
		
		List<ClassStudent> classStudent = ClassStudent.dao.findbyIds(CommonUtils.get);
		
	}

	public void gradeCourses() {

		List<ClassCourse> classCourses = ClassCourse.dao.findByGrade(getParaToInt("grade"));
		Result.ok(classCourses, this);

	}

	@EmptyParaValidate(params = "grade")
	public void homeWork() {

		int grade = getParaToInt("grade");
		int pageNumber = getParaToInt("pageNumber", 1);
		int pageSize = getParaToInt("pageSize", 10);

		Page<GradeHomework> page = GradeHomework.dao.findByGrade(grade, pageNumber, pageSize);
		Result.ok(page, this);

	}

	@EmptyParaValidate(params = { "student", "practice" })
	public void lessonPracticeLike() {
		int student = getParaToInt("student");
		int practice = getParaToInt("practice");
		ClassRoomPractice.dao.likeOrUnliek(student, practice);
		Result.ok(this);
	}
*/
}
