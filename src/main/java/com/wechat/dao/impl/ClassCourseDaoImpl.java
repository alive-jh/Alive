package com.wechat.dao.impl;

import com.wechat.dao.ClassCourseDao;
import com.wechat.entity.ClassCourse;
import com.wechat.entity.ClassCourseRecord;
import com.wechat.entity.ClassCourseReply;
import com.wechat.entity.dto.ClassCourseEvaluateDto;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ClassCourseDaoImpl extends BaseDaoImpl implements ClassCourseDao {

    @Override
    public ClassCourseRecord getClassCourseRecord(int id) {
        return (ClassCourseRecord) this.get(ClassCourseRecord.class, id);
    }

    @Override
    public void saveClassCourseRecord(ClassCourseRecord classCourseRecord) {
        this.saveOrUpdate(classCourseRecord);
    }

    @Override
    public Page<ClassCourseRecord> findToDayClassCourseRecordsByStudentId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassCourseRecord where 1 = 1 and studentId = " + map.get("studentId") + " and DATE_FORMAT(completeTime, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d')");
        return this.pageQueryByHql(sql.toString(),
                Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesByStudentId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	c_g.id," +
                "	c_g.class_grades_name," +
                "   c_g_r.gradesStatus" +
                " FROM" +
                "	class_grades_rela c_g_r" +
                " INNER JOIN class_grades c_g" +
                " ON" +
                "	c_g_r.class_student_id = " + map.get("studentId") +
                " AND c_g.id = c_g_r.class_grades_id");
        ArrayList<Object[]> classGradesRelas = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        HashMap<String, Object> classCourses = new HashMap<String, Object>();
        for (Object[] classGradesRela : classGradesRelas) {
            ArrayList<ClassCourse> ccs = (ArrayList<ClassCourse>) this.executeHQL("from ClassCourse where 1 = 1 and classGradesId = " + classGradesRela[0] + " order by doDay");
            classCourses.put(classGradesRela[1] + "", ccs);
        }
        return classCourses;
    }

    //通过学生ID获取所有班级的课程表 （列表结构）
    @Override
    public JSONArray findJoinedClassCoursesListByStudentId(
            HashMap<String, String> map) {
        JSONArray result = new JSONArray();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	c_g.id," +
                "	c_g.class_grades_name," +
                "   c_g_r.gradesStatus," +
                "   ifnull(c_g.classOpenDate,'')," +
                "   c_g.joinStatus," +
                "   c_g.grades_type," +
                " c_g_r.type " +
                " FROM" +
                "	class_grades_rela c_g_r" +
                " INNER JOIN class_grades c_g" +
                " ON" +
                "	c_g_r.class_student_id = " + map.get("studentId") +
                " AND c_g.id = c_g_r.class_grades_id");
        ArrayList<Object[]> classGradesRelas = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        for (Object[] classGradesRela : classGradesRelas) {
            ArrayList<ClassCourse> ccs = (ArrayList<ClassCourse>) this.executeHQL("from ClassCourse where 1 = 1 and classGradesId = " + classGradesRela[0] + " order by doDay");
            JSONObject data = new JSONObject();

            data.put("classGradesName", classGradesRela[1]);
            data.put("gradesId", classGradesRela[0]);
            data.put("gradesStatus", classGradesRela[2]);
            data.put("classOpenDate", classGradesRela[3]);
            data.put("joinStatus", classGradesRela[4]);
            data.put("gradesType", classGradesRela[5]);
            data.put("relaType", classGradesRela[6]);
            JSONArray classCoursesList = new JSONArray();
            for (int j = 0; j < ccs.size(); j++) {
                JSONObject classCourseTemp = new JSONObject();
                ClassCourse classCourse = ccs.get(j);
                classCourseTemp.put("classRoomId", classCourse.getClassRoomId());
                classCourseTemp.put("cover", classCourse.getCover());

                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (null != classCourse.getCreateTime() && !"".equals(classCourse.getCreateTime())) {
                    classCourseTemp.put("createTime", sdf.format(classCourse.getCreateTime()));
                } else {
                    classCourseTemp.put("createTime", "");
                }
                classCourseTemp.put("doDay", classCourse.getDoDay());
                classCourseTemp.put("doSlot", classCourse.getDoSlot());
                classCourseTemp.put("doTitle", classCourse.getDoTitle());
                classCourseTemp.put("id", classCourse.getId());
                classCourseTemp.put("classGradesId", classCourse.getClassGradesId());
                classCoursesList.add(classCourseTemp);
            }
            data.put("classCoursesList", classCoursesList);
            result.add(data);
        }
        return result;
    }

    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexByStudentId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	c_g.id," +
                "	c_g.class_grades_name," +
                "	c_g_r.gradesStatus" +
                " FROM" +
                "	class_grades_rela c_g_r" +
                " INNER JOIN class_grades c_g" +
                " ON" +
                "	c_g_r.class_student_id = " + map.get("studentId") +
                " AND c_g.id = c_g_r.class_grades_id");
        ArrayList<Object[]> classGradesRelas = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        HashMap<String, Object> classCourses = new HashMap<String, Object>();
        for (Object[] classGradesRela : classGradesRelas) {
            if ("-1".equals(classGradesRela[2].toString())) {
                continue;
            }
            StringBuffer sql1 = new StringBuffer();
            sql1.append(" SELECT" +
                    "	c_c_max.id," +
                    "	c_c_max.do_title," +
                    "	c_c_max.do_slot," +
                    "	c_c_max.do_day," +
                    "	c_c_max.class_room_id," +
                    "	c_c_max.class_grades_id," +
                    "	c_c_max.cover," +
                    "	c_c_max.schedule_start_time," +
                    "	c_c_max.schedule_last_time," +
                    "	c_c_r.score AS record_score," +
                    "	c_c_r.complete_time AS record_complete_time ," +
                    "	c_c_r.id AS record_id " +
                    " FROM" +
                    "	(" +
                    "		SELECT" +
                    "			c_c.*, DATE_FORMAT(" +
                    "				c_c_s.start_time," +
                    "				\'%Y-%m-%d\'" +
                    "			) AS schedule_start_time," +
                    "	IF (c_c_s.class_room_id = c_c.class_room_id,c_c_s.last_time,NULL) schedule_last_time " +
                    "		FROM" +
                    "			(" +
                    "				SELECT" +
                    "					*" +
                    "				FROM" +
                    "					class_course" +
                    "				WHERE" +
                    "					1 = 1" +
                    "				AND class_grades_id = " + classGradesRela[0] +
                    "			) c_c" +
                    "		LEFT JOIN ( select * from class_course_schedule ccs where ccs.student_id = " + map.get("studentId") + ")  c_c_s " +
                    "   ON c_c.class_grades_id = c_c_s.class_grades_id " +
                    "	) c_c_max" +
                    " LEFT JOIN class_course_record c_c_r ON c_c_r.student_id = " + map.get("studentId") +
                    " AND c_c_max.class_grades_id = c_c_r.class_grades_id" +
                    " AND c_c_max.class_room_id = c_c_r.class_room_id " +
                    " AND c_c_max.id = c_c_r.class_course_id ORDER BY c_c_max.do_day ");
            ArrayList<Object[]> ccs = (ArrayList<Object[]>) this.executeSQL(sql1.toString());
            ArrayList<HashMap<String, Object>> ccsMax = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < ccs.size(); i++) {
                Object[] objects = ccs.get(i);
                HashMap<String, Object> cc = new HashMap<String, Object>();
                cc.put("id", objects[0]);
                cc.put("doTitle", objects[1]);
                cc.put("doSlot", objects[2]);
                cc.put("doDay", objects[3]);
                cc.put("classRoomId", objects[4]);
                cc.put("classGradesId", objects[5]);
                cc.put("cover", objects[6]);
                cc.put("scheduleStartTime", objects[7]);
                cc.put("scheduleLastTime", objects[8]);
                cc.put("recordScore", objects[9]);
                cc.put("recordCompleteTime", objects[10]);
                cc.put("recordId", objects[11]);
                cc.put("gradesStatus", classGradesRela[2]);
                ccsMax.add(cc);
            }
            classCourses.put(classGradesRela[1] + "", ccsMax);
        }
        return classCourses;
    }


    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexByStudentIdOptimization(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	c_g.id," +
                "	c_g.class_grades_name," +
                "	c_g_r.gradesStatus," +
                "	ifnull(c_g.classOpenDate,'')," +
                "	c_g.joinStatus" +
                " FROM" +
                "	class_grades_rela c_g_r" +
                " INNER JOIN class_grades c_g" +
                " ON" +
                "	c_g_r.class_student_id = " + map.get("studentId") +
                " AND c_g.id = c_g_r.class_grades_id");
        ArrayList<Object[]> classGradesRelas = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        HashMap<String, Object> classCourses = new HashMap<String, Object>();

        ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        for (Object[] classGradesRela : classGradesRelas) {


            if ("-1".equals(classGradesRela[2].toString())) {
                continue;
            }
            StringBuffer sql1 = new StringBuffer();
            sql1.append(" SELECT" +
                    "	c_c_max.id," +
                    "	c_c_max.do_title," +
                    "	c_c_max.do_slot," +
                    "	c_c_max.do_day," +
                    "	c_c_max.class_room_id," +
                    "	c_c_max.class_grades_id," +
                    "	c_c_max.cover," +
                    "	c_c_max.schedule_start_time," +
                    "	c_c_max.schedule_last_time," +
                    "	c_c_r.score AS record_score," +
                    "	c_c_r.complete_time AS record_complete_time ," +
                    "	c_c_r.id AS record_id " +
                    " FROM" +
                    "	(" +
                    "		SELECT" +
                    "			c_c.*, DATE_FORMAT(" +
                    "				c_c_s.start_time," +
                    "				\'%Y-%m-%d\'" +
                    "			) AS schedule_start_time," +
                    "	IF (c_c_s.class_room_id = c_c.class_room_id,c_c_s.last_time,NULL) schedule_last_time " +
                    "		FROM" +
                    "			(" +
                    "				SELECT" +
                    "					*" +
                    "				FROM" +
                    "					class_course" +
                    "				WHERE" +
                    "					1 = 1" +
                    "				AND class_grades_id = " + classGradesRela[0] +
                    "			) c_c" +
                    "		LEFT JOIN ( select * from class_course_schedule ccs where ccs.student_id = " + map.get("studentId") + ")  c_c_s " +
                    "   ON c_c.class_grades_id = c_c_s.class_grades_id " +
                    "	) c_c_max" +
                    " LEFT JOIN class_course_record c_c_r ON c_c_r.student_id = " + map.get("studentId") +
                    " AND c_c_max.class_grades_id = c_c_r.class_grades_id" +
                    " AND c_c_max.class_room_id = c_c_r.class_room_id " +
                    " AND c_c_max.id = c_c_r.class_course_id ORDER BY c_c_max.do_day ");
            ArrayList<Object[]> ccs = (ArrayList<Object[]>) this.executeSQL(sql1.toString());
            ArrayList<HashMap<String, Object>> ccsMax = new ArrayList<HashMap<String, Object>>();
            for (int i = 0; i < ccs.size(); i++) {
                Object[] objects = ccs.get(i);
                HashMap<String, Object> cc = new HashMap<String, Object>();
                cc.put("id", objects[0]);
                cc.put("doTitle", objects[1]);
                cc.put("doSlot", objects[2]);
                cc.put("doDay", objects[3]);
                cc.put("classRoomId", objects[4]);
                cc.put("classGradesId", objects[5]);
                cc.put("cover", objects[6]);
                cc.put("scheduleStartTime", objects[7]);
                cc.put("scheduleLastTime", objects[8]);
                cc.put("recordScore", objects[9]);
                cc.put("recordCompleteTime", objects[10]);
                cc.put("recordId", objects[11]);
                cc.put("gradesStatus", classGradesRela[2]);
                ccsMax.add(cc);
            }

            HashMap<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("coursesName", classGradesRela[1]);
            infoMap.put("classOpenDate", classGradesRela[3]);
            infoMap.put("joinStatus", classGradesRela[4]);
            infoMap.put("coursesInfo", ccsMax);
            listMap.add(infoMap);
        }
        classCourses.put("coursesList", listMap);
        return classCourses;
    }


    @Override
    public HashMap<String, Object> findJoinedClassCoursesIndexInfo(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	c_g.id," +
                "	c_g.class_grades_name," +
                "	c_g_r.gradesStatus," +
                "	ifnull(c_g.classOpenDate,'')," +
                "	c_g.joinStatus" +
                " FROM" +
                "	class_grades_rela c_g_r" +
                " INNER JOIN class_grades c_g" +
                " ON" +
                "	c_g_r.class_student_id = " + map.get("studentId") +
                " AND c_g.id = c_g_r.class_grades_id");
        ArrayList<Object[]> classGradesRelas = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        HashMap<String, Object> classCourses = new HashMap<String, Object>();

        ArrayList<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        HashMap dateMap = new LinkedHashMap();
        HashMap dateListMap = new LinkedHashMap();

        String showStatus = "1";
        for (Object[] classGradesRela : classGradesRelas) {


            if ("-1".equals(classGradesRela[2].toString())) {
                continue;
            }
            StringBuffer sql1 = new StringBuffer();
            sql1.append(" SELECT" +
                    "	c_c_max.id," +
                    "	c_c_max.do_title," +
                    "	c_c_max.do_slot," +
                    "	c_c_max.do_day," +
                    "	c_c_max.class_room_id," +
                    "	c_c_max.class_grades_id," +
                    "	c_c_max.cover," +
                    "	c_c_max.schedule_start_time," +
                    "	c_c_max.schedule_last_time," +
                    "	c_c_r.score AS record_score," +
                    "	c_c_r.complete_time AS record_complete_time ," +
                    "	c_c_r.id AS record_id " +
                    " FROM" +
                    "	(" +
                    "		SELECT" +
                    "			c_c.*, DATE_FORMAT(" +
                    "				c_c_s.start_time," +
                    "				\'%Y-%m-%d\'" +
                    "			) AS schedule_start_time," +
                    "	IF (c_c_s.class_room_id = c_c.class_room_id,c_c_s.last_time,NULL) schedule_last_time " +
                    "		FROM" +
                    "			(" +
                    "				SELECT" +
                    "					*" +
                    "				FROM" +
                    "					class_course" +
                    "				WHERE" +
                    "					1 = 1" +
                    "				AND class_grades_id = " + classGradesRela[0] +
                    "			) c_c" +
                    "		LEFT JOIN ( select * from class_course_schedule ccs where ccs.student_id = " + map.get("studentId") + ")  c_c_s " +
                    "   ON c_c.class_grades_id = c_c_s.class_grades_id " +
                    "	) c_c_max" +
                    " LEFT JOIN class_course_record c_c_r ON c_c_r.student_id = " + map.get("studentId") +
                    " AND c_c_max.class_grades_id = c_c_r.class_grades_id" +
                    " AND c_c_max.class_room_id = c_c_r.class_room_id " +
                    " AND c_c_max.id = c_c_r.class_course_id ORDER BY c_c_max.do_day desc,c_c_max.do_slot asc");


            ArrayList<Object[]> ccs = (ArrayList<Object[]>) this.executeSQL(sql1.toString());

            ArrayList<HashMap<String, Object>> ccsMax = new ArrayList<HashMap<String, Object>>();

            ArrayList<HashMap<String, Object>> dateList = new ArrayList<HashMap<String, Object>>();

            Object[] objects = null;
            dateMap = new LinkedHashMap();
            Date infoDate = new Date();
            Date tempDate = new Date();
            Date toDate = new Date();
            Integer showNumber = 1;
            String s = sdf.format(toDate);
            try {

                toDate = sdf.parse(s);

            } catch (ParseException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            String tempStr = "";
            for (int i = 0; i < ccs.size(); i++) {//时间map分组


                objects = ccs.get(i);
                if (dateMap.get(objects[3].toString()) == null) {
                    dateMap.put(objects[3].toString(), objects[7].toString());

                }

            }

            Iterator<Map.Entry<String, String>> entries = dateMap.entrySet().iterator();

            HashMap<String, Object> dateCC = new HashMap<String, Object>();


            while (entries.hasNext()) {  //循环日期集合

                dateCC = new HashMap<String, Object>();

                Map.Entry<String, String> entry = entries.next();


                infoDate = new Date();

                try {

                    infoDate = new Date(sdf.parse(entry.getValue()).getTime() + (new Integer(entry.getKey()) - 1) * 24 * 60 * 60 * 1000L);


                } catch (Exception e) {

                    e.printStackTrace();
                }

                dateCC.put("infoDate", sdf.format(infoDate));//课程表日期
                ccsMax = new ArrayList<HashMap<String, Object>>();


                for (int i = 0; i < ccs.size(); i++) {

                    objects = ccs.get(i);
                    HashMap<String, Object> cc = new HashMap<String, Object>();

                    if (entry.getKey().toString().equals(objects[3].toString())) {

                        showNumber = 1;
                        cc.put("id", objects[0]);
                        cc.put("doTitle", objects[1]);
                        cc.put("doSlot", objects[2]);
                        cc.put("doDay", objects[3]);
                        cc.put("classRoomId", objects[4]);
                        cc.put("classGradesId", objects[5]);
                        cc.put("cover", objects[6]);
                        cc.put("scheduleStartTime", objects[7]);
                        cc.put("scheduleLastTime", objects[8]);
                        cc.put("recordScore", objects[9]);
                        cc.put("recordCompleteTime", objects[10]);
                        cc.put("recordId", objects[11]);
                        cc.put("gradesStatus", classGradesRela[2]);


                        if (dateMap.get(objects[3].toString()) != null) {

                            try {//算出课程表开始时间

                                //sdf.format(new Date(d.getTime() + 125 * 24 * 60 * 60 * 1000L))
                                tempDate = new Date(sdf.parse(dateMap.get(objects[3].toString()).toString()).getTime() + (new Integer(objects[3].toString()) - 1) * 24 * 60 * 60 * 1000L);

                                ////System.out.println(objects[1]+" ==== "+dateMap.get(objects[3].toString()).toString()+"  doday = " +objects[3].toString()+" tempDate = "+sdf.format(tempDate));

                                cc.put("classStartDate", sdf.format(tempDate));

                            } catch (Exception e) {

                                cc.put("classStartDate", "");
                            }
                        } else {
                            cc.put("classStartDate", "");
                        }

                        ////System.out.println("date  = "+sdf.format(tempDate)+"   showNumber = "+showNumber);

                        //计算
                        //1:过去完成,2落后未完成,3当前完成,4当前未完成,5超前完成,6超前未完成

                        if (toDate.getTime() > tempDate.getTime()) {
                            if (objects[9] != null) {
                                cc.put("classStatus", "1");
                            } else {
                                cc.put("classStatus", "2");
                            }
                            cc.put("showStatus", "0");


                            showNumber = 0;


                        } else if (toDate.getTime() == tempDate.getTime()) {
                            if (objects[9] != null) {
                                cc.put("classStatus", "3");


                            } else {
                                cc.put("classStatus", "4");
                            }
                            cc.put("showStatus", "0");


                            showNumber = 0;


                        } else {
                            if (objects[9] != null) {
                                cc.put("classStatus", "5");
                                cc.put("showStatus", "0");//超前完成,显示记录


                                showNumber = 0;


                            } else {
                                cc.put("classStatus", "6");
                                cc.put("showStatus", "1");


                            }


                        }


                        ccsMax.add(cc);
                    }

                }
                dateCC.put("dateList", ccsMax);
                dateCC.put("listStatus", showNumber);
                dateList.add(dateCC);

            }


            HashMap<String, Object> infoMap = new HashMap<String, Object>();
            infoMap.put("gradesId", classGradesRela[0]);
            infoMap.put("coursesName", classGradesRela[1]);
            infoMap.put("classOpenDate", classGradesRela[3]);
            infoMap.put("joinStatus", classGradesRela[4]);
            infoMap.put("coursesInfo", dateList);
            listMap.add(infoMap);
        }
        classCourses.put("coursesList", listMap);
        return classCourses;
    }

    @Override
    public List<ClassCourse> findJoinedCoursesByStudentId(Integer sid) {
        String sql = "SELECT " +
                "* " +
                "FROM " +
                "class_course c " +
                "WHERE " +
                "c.class_grades_id in " +
                "( " +
                "SELECT " +
                "gr.class_grades_id " +
                "FROM " +
                "class_grades_rela gr " +
                "WHERE " +
                "gr.class_student_id = 14 " +
                "AND " +
                "gr.gradesStatus = 1 " +
                ")";
        return (List<ClassCourse>) this.executeSQL(sql);
    }


    @Override
    public Page<ClassCourseRecord> findClassCourseRecordsByStudentIdAndGradesId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer("select a.id,a.student_id,a.class_grades_id,a.class_course_id,a.class_room_id,a.complete,a.score,ifnull(a.do_title,b.class_name),a.used_time,a.complete_time from class_course_record as a,class_room as b where a.class_room_id=b.id and a.class_grades_id=:classGradesId and a.student_id=:studentId");
        Query query = this.getQuery(sql.toString());
        if (null != map.get("classGradesId") && null != map.get("studentId")
                && !"".equals(map.get("classGradesId").toString()) && !"".equals(map.get("studentId").toString())) {
            query.setString("studentId", map.get("studentId").toString());
            query.setString("classGradesId", map.get("classGradesId").toString());

        }
        Page dataPage = this.pageQueryBySQL(query, 1, 1000);
        ArrayList<ClassCourseRecord> classCourseRecords = new ArrayList<ClassCourseRecord>();

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {

                Object[] obj = dataList.get(i);
                ClassCourseRecord classCourseRecord = new ClassCourseRecord();
                classCourseRecord.setId((Integer) obj[0]);
                classCourseRecord.setStudentId((Integer) obj[1]);
                classCourseRecord.setClassGradesId((Integer) obj[2]);
                classCourseRecord.setClassCourseId((Integer) obj[3]);
                classCourseRecord.setClassRoomId((Integer) obj[4]);
                classCourseRecord.setComplete((Integer) obj[5]);
                classCourseRecord.setScore((String) obj[6]);
                classCourseRecord.setDoTitle((String) obj[7]);
                classCourseRecord.setUsedTime((Integer) obj[8]);
                classCourseRecord.setCompleteTime((Timestamp) obj[9]);
                classCourseRecords.add(classCourseRecord);
            }

        }

        Page resultPage = new Page<ClassCourseRecord>(classCourseRecords, dataPage.getTotalCount(),
                dataPage.getIndexes(), dataPage.getStartIndex(),
                dataPage.getPageSize());
//		sql.append("from ClassCourseRecord where 1 = 1 and studentId = " +map.get("studentId") + " and classGradesId = " +map.get("classGradesId"));
//		return this.pageQueryByHql(sql.toString(),
//				Integer.parseInt(map.get("page")),
//				Integer.parseInt(map.get("pageSize")));
        return resultPage;
    }

    @Override
    public Page<ClassCourse> findClassCoursesByRoomId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassCourse where 1 = 1 and classRoomId = " + map.get("classRoomId"));
        return this.pageQueryByHql(sql.toString(),
                Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public void saveClassCourseReply(ClassCourseReply classCourseReply) {
        this.saveOrUpdate(classCourseReply);
    }

    @Override
    public ClassCourseReply getClassCourseReply(int id) {
        return (ClassCourseReply) this.get(ClassCourseReply.class, id);
    }

    @Override
    public Page<ClassCourseReply> findClassCourseReplysByRecordId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassCourseReply where 1 = 1 and recordId = " + map.get("recordId"));
        return this.pageQueryByHql(sql.toString(),
                Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public ClassCourseEvaluateDto findClassCourseEvaluateByStudentIdAndGradesId(
            HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" +
                "	COUNT(temp1.id)," +
                "	SUM(temp1.score)," +
                "	SUM(temp1.used_time)" +
                " FROM" +
                "	(" +
                "		SELECT" +
                "			*" +
                "		FROM" +
                "			class_course_record ccr" +
                "		WHERE" +
                "			ccr.class_grades_id = " + map.get("classGradesId") +
                "		AND ccr.student_id = " + map.get("studentId") +
                "	) temp1");
        ArrayList<Object[]> classCourseEvaluateArr = (ArrayList<Object[]>) this.executeSQL(sql.toString());
        ClassCourseEvaluateDto classCourseEvaluateDto = null;
        for (int i = 0; i < classCourseEvaluateArr.size(); i++) {
            Object[] objArr = classCourseEvaluateArr.get(i);
            Integer courseSum = ((BigInteger) objArr[0]).intValue();
            Integer scoreSum = ((Double) objArr[1]).intValue();
            Integer timeSum = ((BigDecimal) objArr[2]).intValue();
            classCourseEvaluateDto = new ClassCourseEvaluateDto(Integer.parseInt(map.get("studentId")), Integer.parseInt(map.get("classGradesId")), courseSum, scoreSum, timeSum);
        }
        return classCourseEvaluateDto;
    }



    public static void main(String[] args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String s = sdf.format(d);

        ////System.out.println(d.getTime());
        //System.out.println(s);


        d = sdf.parse("2017-05-03");


        //System.out.println(sdf.format(new Date(d.getTime() + 125 * 24 * 60 * 60 * 1000L)));

        Date tempDate = new Date(sdf.parse("2017-05-03").getTime() + (new Integer("125")) * 24 * 60 * 60 * 1000L);

        //System.out.println(sdf.format(tempDate));

    }

}
