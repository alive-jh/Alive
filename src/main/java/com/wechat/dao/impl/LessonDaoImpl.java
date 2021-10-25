package com.wechat.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mysql.jdbc.PreparedStatement;
import com.wechat.dao.LessonDao;
import com.wechat.easemob.EasemobUtil;
import com.wechat.entity.*;
import com.wechat.entity.dto.ClassRoomDto;
import com.wechat.entity.dto.ClassRoomIndexDto;
import com.wechat.entity.dto.ClassStudentDto;
import com.wechat.jfinal.model.ClassRoomAudioRel;
import com.wechat.jfinal.model.ClassRoomLabelRela;
import com.wechat.service.RedisService;
import com.wechat.util.BeanModifyFilter;
import com.wechat.util.Page;
import com.wechat.util.PropertyUtil;
import com.wechat.util.TimestampMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class LessonDaoImpl extends BaseDaoImpl implements LessonDao {

    static final String JDBC_DRIVER = PropertyUtil.getDataBaseProperty("driverClass");
    static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

    static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
    static final String PASS = PropertyUtil.getDataBaseProperty("password");

    @Resource
    private RedisService redisService;

    @Override
    public void saveClassRoom(ClassRoom classRoom) {
        // 保存课程
        this.saveOrUpdate(classRoom);
    }

    @Override
    public void saveClassScriptType(ClassScriptType classScriptType) {
        this.saveOrUpdate(classScriptType);
    }

    @Override
    public ClassScriptType getClassScriptType(int id) {
        return (ClassScriptType) this.get(ClassScriptType.class, id);
    }

    @Override
    public ClassRoom getClassRoom(int id) {
        // 通过课程主键获取课程信息
        return (ClassRoom) this.get(ClassRoom.class, id);
    }

    @Override
    public void saveClassScriptNormal(ClassScriptNormal classScriptNormal) {
        if (null == classScriptNormal.getId()) {// 如果不带ID为新增，需要判断是否会重复
            // 通过classRoomId和sortId查询数据库是否已经包含该指令
            StringBuffer sql = new StringBuffer();
            sql.append("select * from class_script_normal where class_room_id=:classRoomId and sort=:sort");
            Query query = this.getQuery(sql.toString());
            query.setInteger("sort", classScriptNormal.getSort());
            query.setInteger("classRoomId", classScriptNormal.getClassRoomId());

            List<?> classScriptNormalList = query.list();
            if (classScriptNormalList.size() == 0) {// 如果没有，插入新的

            } else {
                return;
            }

        } else {

        }
        this.saveOrUpdate(classScriptNormal);
    }

    @Override
    public ClassScriptNormal getClassScriptNormal(int id) {
        return (ClassScriptNormal) this.get(ClassScriptNormal.class, id);
    }

    @Override
    public ClassScriptDone getClassScriptDone(int id) {
        return (ClassScriptDone) this.get(ClassScriptDone.class, id);
    }

    @Override
    public void saveClassScriptDone(ClassScriptDone classScriptDone) {
        this.saveOrUpdate(classScriptDone);
    }

    @Override
    public ClassTeacher getClassTeacher(int id) {
        return (ClassTeacher) this.get(ClassTeacher.class, id);
    }

    @Override
    public void saveClassTeacher(ClassTeacher classTeacher) {
        this.saveOrUpdate(classTeacher);
    }

    @Override
    public ClassStudent getClassStudent(int id) {
        return (ClassStudent) this.get(ClassStudent.class, id);
    }

    @Override
    public void saveClassStudent(ClassStudent classStudent) {
        this.saveOrUpdate(classStudent);
    }

    @Override
    public ClassStudentRela getClassStudentRela(int id) {
        return (ClassStudentRela) this.get(ClassStudentRela.class, id);
    }

    @Override
    public void saveClassStudentRela(ClassStudentRela classStudentRela) {
        this.saveOrUpdate(classStudentRela);
    }

    @Override
    public void deleteTableRecord(String id, String table) {
        this.executeUpdateSQL("delete from " + table + " where id = " + id);
    }

    @Override
    public ArrayList<ClassScriptType> findClassScriptTypes(HashMap<String, String> map) {
        // 老的app版本取状态为0的
        return (ArrayList<ClassScriptType>) this.executeHQL("from ClassScriptType where status=3");
    }

    @Override
    public Page<ClassScriptNormal> findClassRoomNormalScript(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassScriptNormal where 1 = 1 ");
        if (!"".equals(map.get("classRoomId"))) {
            sql.append(" and classRoomId = " + map.get("classRoomId"));

            // 临时添加---只取排序小于10000的数据
            sql.append(" and sort < 10000");
        }
        sql.append("order by sort");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public Page<ClassScriptDone> findClassRoomDoneScript(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassScriptDone where 1 = 1 ");
        if (!"".equals(map.get("studentId"))) {
            sql.append(" and studentId = " + map.get("studentId"));
        } else {
            if (!"".equals(map.get("epalId"))) {
                sql.append(" and epalId = '" + map.get("epalId") + "'");
            }
        }
        if (!"".equals(map.get("classRoomId"))) {
            sql.append(" and classRoomId = " + map.get("classRoomId"));
        }
        if (!"".equals(map.get("classCourseId"))) {
            sql.append(" and classCourseId = " + map.get("classCourseId"));
        }
        if (!"".equals(map.get("classScriptTypeId"))) {
            sql.append(" and classScriptTypeId = " + map.get("classScriptTypeId"));
        }
        sql.append("order by createTime");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public Page<ClassRoomDto> findStudiedClassRooms(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	tab_2.id," + "	tab_2.class_name," + "	tab_2.teacher_name," + "	tab_2.summary,"
                + "	tab_2.cover," + "	tab_2.create_time," + "	tab_2.studied_stu_count," + "	tab_2.book_res_id ,"
                + "	tab_2.group_id ," + "	tab_2.teacher_id " + " FROM" + "	(" + "		SELECT"
                + "			tab_1.id," + "			tab_1.class_name," + "			tab_1.teacher_name,"
                + "			tab_1.summary," + "			tab_1.cover," + "			tab_1.create_time,"
                + "			tab_1.book_res_id," + "			tab_1.group_id," + "			tab_1.teacher_id,"
                + "			count(" + "				class_student_rela.class_student_id"
                + "			) studied_stu_count" + "		FROM" + "			(" + "				SELECT"
                + "					*" + "				FROM" + "					class_room"
                + "				WHERE 1 = 1 ");
        if (!"".equals(map.get("className"))) {
            sql.append(" AND class_room.class_name like :className");
        }
        if (!"".equals(map.get("classRoomId"))) {
            sql.append(" AND class_room.id = :classRoomId");
        }
        if (!"".equals(map.get("classStudentId"))) {
            sql.append(
                    " AND class_room.id IN ( SELECT class_student_rela.class_room_id FROM class_student_rela WHERE class_student_rela.class_student_id = :classStudentId )");
        }
        sql.append("			) AS tab_1"
                + "		LEFT JOIN class_student_rela ON tab_1.id = class_student_rela.class_room_id"
                + "		GROUP BY" + "			tab_1.id" + "	) AS tab_2" + " GROUP BY" + "	tab_2.id");

        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("className"))) {
            query.setString("className", "%" + map.get("className") + "%");
        }
        if (!"".equals(map.get("classRoomId"))) {
            query.setInteger("classRoomId", Integer.parseInt(map.get("classRoomId")));
        }
        if (!"".equals(map.get("classStudentId"))) {
            query.setInteger("classStudentId", Integer.parseInt(map.get("classStudentId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // Studied在线课堂数组
        ArrayList<ClassRoomDto> classRoomDtos = new ArrayList<ClassRoomDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassRoomDto classRoomDto = new ClassRoomDto();
                Object[] obj = dataList.get(z);
                classRoomDto.setId((Integer) obj[0]);
                classRoomDto.setClassName((String) obj[1]);
                classRoomDto.setTeacherName((String) obj[2]);
                classRoomDto.setSummary((String) obj[3]);
                classRoomDto.setCover((String) obj[4]);
                classRoomDto.setCreateTime((Timestamp) obj[5]);
                classRoomDto.setStudiedStuCount((BigInteger) obj[6]);
                classRoomDto.setBookResId((Integer) obj[7]);
                classRoomDto.setGroupId((String) obj[8]);
                classRoomDto.setTeacherId((Integer) obj[9]);
                classRoomDtos.add(classRoomDto);
            }
        }
        // 绑定的班级里面的在线课堂数组
        ArrayList<ClassGradesRela> students = (ArrayList<ClassGradesRela>) this
                .executeHQL("from ClassGradesRela where classStudentId = " + map.get("classStudentId"));
        if (students.size() > 0) {
            map.put("classGradesId", students.get(0).getClassGradesId() + "");
            Page<ClassRoomDto> gradesClassRooms = this.findClassRoomsByClassGradesId(map);
            classRoomDtos.addAll(gradesClassRooms.getItems());
        }

        Page resultPage = new Page<ClassRoomDto>(classRoomDtos, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassRoomDto> findClassRooms(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select id,class_name,teacher_name,summary,cover,create_time,book_res_id,group_id,status,category_id,teacher_id,sort,book_res_ids,class_room_type,video_url,ifnull((select sum(total_time) from class_script_normal where class_room_id=class_room.id),0) from class_room where status>=0");
        if (!"".equals(map.get("className"))) {
            sql.append("				AND	class_room.class_name LIKE :className");
        }
        if (!"".equals(map.get("teacherName"))) {
            sql.append("				AND class_room.teacher_name LIKE :teacherName");
        }
        if (!"".equals(map.get("teacherId"))) {
            sql.append("				AND class_room.teacher_id = :teacherId");
        }

        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("className"))) {
            query.setString("className", "%" + map.get("className") + "%");
        }
        if (!"".equals(map.get("teacherName"))) {
            query.setString("teacherName", "%" + map.get("teacherName") + "%");
        }
        if (!"".equals(map.get("teacherId"))) {
            query.setInteger("teacherId", Integer.parseInt(map.get("teacherId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassRoomDto> classRoomDtos = new ArrayList<ClassRoomDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassRoomDto classRoomDto = new ClassRoomDto();
                Object[] obj = dataList.get(z);
                classRoomDto.setId((Integer) obj[0]);
                classRoomDto.setClassName((String) obj[1]);
                classRoomDto.setTeacherName((String) obj[2]);
                classRoomDto.setSummary((String) obj[3]);
                classRoomDto.setCover((String) obj[4]);
                classRoomDto.setCreateTime((Timestamp) obj[5]);
                classRoomDto.setBookResId((Integer) obj[6]);
                classRoomDto.setGroupId((String) obj[7]);
                classRoomDto.setStatus((Integer) obj[8]);
                classRoomDto.setCategoryId((Integer) obj[9]);
                classRoomDto.setTeacherId((Integer) obj[10]);
                classRoomDto.setSort((Integer) obj[11]);
                String bookResIds = (String) obj[12];
                if (null != bookResIds && !"".equals(bookResIds)) {
                    classRoomDto.setBookResIds(bookResIds);
                } else if (obj[6] != null) {
                    classRoomDto.setBookResIds(obj[6].toString());
                } else {
                    classRoomDto.setBookResIds("");
                }
                classRoomDto.setClassRoomType((String) obj[13]);
                classRoomDto.setVideoUrl((String) obj[14]);
                classRoomDto.setTotalTime(Integer.parseInt(obj[15].toString()));
                classRoomDtos.add(classRoomDto);
            }
        }

        Page resultPage = new Page<ClassRoomDto>(classRoomDtos, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public ArrayList<ClassTeacher> findClassTeachers(HashMap<String, String> map) {
        return (ArrayList<ClassTeacher>) this.executeHQL("from ClassTeacher where memberId = " + map.get("memberId"));
    }

    @Override
    public Page<ClassStudentDto> findClassStudents(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" + "	tab1.id," + "	tab1.className," + "	IFNULL(tab2. NAME, tab1.epal_id) stuName,"
                + "	tab1.epal_id" + " FROM" + "	(" + "		SELECT" + "			csr.id,"
                + "			csr. NAME className," + "			cs.`name` stuName," + "			cs.epal_id"
                + "		FROM" + "			class_student_rela csr"
                + "		INNER JOIN class_student cs ON csr.class_room_id = :classRoomId "
                + "		AND csr.class_student_id = cs.id" + "	) tab1"
                + " LEFT JOIN device_student_school tab2 ON tab1.epal_id = tab2.epalId ");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classRoomId"))) {
            query.setInteger("classRoomId", Integer.parseInt(map.get("classRoomId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassStudentDto> classStudentDtos = new ArrayList<ClassStudentDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassStudentDto classStudentDto = new ClassStudentDto();
                Object[] obj = dataList.get(z);
                classStudentDto.setId((Integer) obj[0]);
                classStudentDto.setClassName((String) obj[1]);
                classStudentDto.setStudentName((String) obj[2]);
                classStudentDto.setEpalId((String) obj[3]);
                classStudentDtos.add(classStudentDto);
            }
        }

        Page resultPage = new Page<ClassStudentDto>(classStudentDtos, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassRoomIndexDto> findClassRoomsIndex(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	crc.id," + "	crc.category_name," + "	crc.parent_id," + "	ifnull(crc.summary,'暂无简介'),"
                + "	crc.cover," + "	crc.sort," + "	GROUP_CONCAT(" + "		cr.id," + "		\'∫\',"
                + "		IFNULL(cr.teacher_id, 0)," + "		\'∫\'," + "		IFNULL(cr.teacher_name, \'null\'),"
                + "		\'∫\'," + "		IFNULL(cr.class_name, \'null\')," + "		\'∫\',"
                + "		IFNULL(cr.cover, \'\')," + "		\'∫\'," + "		IFNULL(cr.summary, \'暂无简介\'),"
                + "		\'∫\'," + "		IFNULL(cr.sort, 0)," + "		\'∫\'," + "		cr.create_time,"
                + "		\'∫\'," + "		IFNULL(cr.group_id, \'null\')," + "		\'∫\'" + "	ORDER BY"
                + "		cr.create_time DESC SEPARATOR \'∪\'" + "	) class_rooms" + " FROM"
                + "	class_room_category crc" + " LEFT JOIN class_room cr ON crc.id = cr.category_id"
                + " AND cr. STATUS=1" + " GROUP BY" + "	crc.id");
        Query query = this.getQuery(sql.toString());
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));
        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassRoomIndexDto> classRoomIndexDtos = new ArrayList<ClassRoomIndexDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassRoomIndexDto classRoomIndexDto = new ClassRoomIndexDto();
                Object[] obj = dataList.get(z);
                classRoomIndexDto.setId((Integer) obj[0]);
                classRoomIndexDto.setCategoryName((String) obj[1]);
                classRoomIndexDto.setParentId((Integer) obj[2]);
                classRoomIndexDto.setSummary((String) obj[3]);
                classRoomIndexDto.setCover((String) obj[4]);
                classRoomIndexDto.setSort((Integer) obj[5]);
                classRoomIndexDto.setClassRooms((String) obj[6]);
                classRoomIndexDtos.add(classRoomIndexDto);
            }
        }

        Page resultPage = new Page<ClassRoomIndexDto>(classRoomIndexDtos, dataPage.getTotalCount(),
                dataPage.getIndexes(), dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;

    }

    @Override
    public Page<ClassStudent> findClassStudentByEpalId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassStudent where 1 = 1 ");
        if (!"".equals(map.get("epalId"))) {
            sql.append(" and epalId = '" + map.get("epalId") + "'");
        }
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));

    }

    @Override
    public void signOutClassRoom(HashMap map) {
        this.executeUpdateSQL("delete from class_student_rela where class_student_rela.class_room_id = "
                + map.get("classRoomId") + " and class_student_rela.class_student_id = " + map.get("studentId"));
    }

    @Override
    public Page<ClassRoomIndexDto> findClassRoomCategorys(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	crc.id," + "	crc.category_name," + "	crc.parent_id," + "	crc.summary,"
                + "	crc.cover," + "	crc.sort " + " FROM" + "	class_room_category crc where 1 = 1" + " GROUP BY "
                + " crc.id");
        Query query = this.getQuery(sql.toString());
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));
        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
        // 在线课堂数组
        ArrayList<ClassRoomIndexDto> classRoomIndexDtos = new ArrayList<ClassRoomIndexDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassRoomIndexDto classRoomIndexDto = new ClassRoomIndexDto();
                Object[] obj = dataList.get(z);
                classRoomIndexDto.setId((Integer) obj[0]);
                classRoomIndexDto.setCategoryName((String) obj[1]);
                classRoomIndexDto.setParentId((Integer) obj[2]);
                classRoomIndexDto.setSummary((String) obj[3]);
                classRoomIndexDto.setCover((String) obj[4]);
                classRoomIndexDto.setSort((Integer) obj[5]);
                classRoomIndexDtos.add(classRoomIndexDto);
            }
        }

        Page resultPage = new Page<ClassRoomIndexDto>(classRoomIndexDtos, dataPage.getTotalCount(),
                dataPage.getIndexes(), dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassGrades> findClassGrades(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassGrades where 1 = 1 and gradesType='virtualClass'");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));

    }

    @Override
    public ClassGrades getClassGrades(int id) {
        return (ClassGrades) this.get(ClassGrades.class, id);
    }

    @Override
    public void saveClassGrades(ClassGrades classGrades) {
        this.saveOrUpdate(classGrades);
    }

    @Override
    public void saveClassGradesRela(ClassGradesRela classGradesRela) {
        this.saveOrUpdate(classGradesRela);
    }

    @Override
    public ClassRoomGradesRela getClassRoomGradesRela(int id) {
        return (ClassRoomGradesRela) this.get(ClassRoomGradesRela.class, id);
    }

    @Override
    public void saveClassRoomGradesRela(ClassRoomGradesRela classRoomGradesRela) {
        this.saveOrUpdate(classRoomGradesRela);
    }

    @Override
    public ClassGradesRela getClassGradesRela(int id) {
        return (ClassGradesRela) this.get(ClassGradesRela.class, id);
    }

    @Override
    public Page<ClassRoomDto> findClassRoomsByClassGradesId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	temp2.id," + "	temp2.teacher_id," + "	temp2.teacher_name," + "	temp2.class_name,"
                + "	temp2.cover," + "	temp2.summary," + "	temp2. STATUS," + "	temp2.sort," + "	temp2.book_res_id,"
                + "	temp2.group_id," + "	temp2.create_time" + " FROM" + "	class_room_grades_rela temp1"
                + " INNER JOIN class_room temp2 ON temp1.class_grades_id = :classGradesId"
                + " AND temp1.class_room_id = temp2.id");
        if (!"".equals(map.get("classRoomId"))) {
            sql.append(" AND temp2.id = :classRoomId");
        }
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classGradesId"))) {
            query.setInteger("classGradesId", Integer.parseInt(map.get("classGradesId")));
        }
        if (!"".equals(map.get("classRoomId"))) {
            query.setInteger("classRoomId", Integer.parseInt(map.get("classRoomId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassRoomDto> classRoomDtos = new ArrayList<ClassRoomDto>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassRoomDto classRoomDto = new ClassRoomDto();
                Object[] obj = dataList.get(z);
                classRoomDto.setId((Integer) obj[0]);
                classRoomDto.setTeacherId((Integer) obj[1]);
                classRoomDto.setTeacherName((String) obj[2]);
                classRoomDto.setClassName((String) obj[3]);
                classRoomDto.setCover((String) obj[4]);
                classRoomDto.setSummary((String) obj[5]);
                classRoomDto.setStatus((Integer) obj[6]);
                classRoomDto.setSort((Integer) obj[7]);
                classRoomDto.setBookResId((Integer) obj[8]);
                classRoomDto.setGroupId((String) obj[9]);
                classRoomDto.setCreateTime((Timestamp) obj[10]);
                classRoomDtos.add(classRoomDto);
            }
        }

        Page resultPage = new Page<ClassRoomDto>(classRoomDtos, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public ClassRoomSign getClassRoomSign(int id) {
        return (ClassRoomSign) this.get(ClassRoomSign.class, id);
    }

    @Override
    public void saveClassRoomSign(ClassRoomSign classRoomSign) {
        this.saveOrUpdate(classRoomSign);
    }

    @Override
    public ClassRoomPush getClassRoomPush(int id) {
        return (ClassRoomPush) this.get(ClassRoomPush.class, id);
    }

    @Override
    public void saveClassRoomPush(ClassRoomPush classRoomPush, HashMap map) {
        this.saveOrUpdate(classRoomPush);
        int pushId = classRoomPush.getId();
        Integer classRoomId = classRoomPush.getClassRoomId();
        String classScript = classRoomPush.getClassRoomScript();
        EasemobUtil easemobUtil = new EasemobUtil();
        if ("person".equals(map.get("type"))) {
            String[] users = new String[]{map.get("epalIds").toString()};
            try {
                easemobUtil.sendMessageSign(users, classScript, pushId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("personBatch".equals(map.get("type"))) {
            String[] users = map.get("epalIds").toString().split(",");
            try {
                easemobUtil.sendMessageSign(users, classScript, pushId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("classBatch".equals(map.get("type"))) {
            String classIds = map.get("classIds").toString();
            StringBuffer sql = new StringBuffer();
            sql.append(
                    "select b.epal_id from class_grades_rela as a,class_student as b where a.class_student_id=b.id and a.class_grades_id in ("
                            + classIds + ")");
            Query query = this.getQuery(sql.toString());
            List epalIdList = query.list();
            String[] users = new String[epalIdList.size()];
            for (int i = 0; i < epalIdList.size(); i++) {
                users[i] = (String) epalIdList.get(i);
            }
            try {
                easemobUtil.sendMessageSign(users, classScript, pushId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("all".equals(map.get("type"))) {
            String[] users1 = map.get("epalIds").toString().split(",");
            try {
                easemobUtil.sendMessageSign(users1, classScript, pushId);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String classIds = map.get("classIds").toString();
            StringBuffer sql = new StringBuffer();

            sql.append(
                    "select b.epal_id from class_grades_rela as a,class_student as b where a.class_student_id=b.id and a.class_grades_id in (select class_grades_id from class_room_grades_rela where class_room_id=");
            sql.append(classRoomId.toString());
            sql.append(")");
            Query query = this.getQuery(sql.toString());
            List epalIdList = query.list();
            String[] users = new String[epalIdList.size()];
            for (int i = 0; i < epalIdList.size(); i++) {
                users[i] = (String) epalIdList.get(i);
            }
            try {
                easemobUtil.sendMessageSign(users, classScript, pushId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // String classRoonId_str = classRoomId.toString();
            // StringBuffer sql = new StringBuffer();
            // sql.append("SELECT epal_id from class_student_rela as
            // a,class_student as b where a.class_student_id=b.id and
            // a.class_room_id=:classRoomId");
            // Query query = this.getQuery(sql.toString());
            // if (!"".equals(classRoonId_str) && null != classRoonId_str) {
            // query.setInteger("classRoomId", classRoomId);
            // }
            // List epalIdList = query.list();
            //
            // JSONArray temp = new JSONArray();
            // String[] users = new String[epalIdList.size() + 1000];
            // for (int i = 0; i < epalIdList.size(); i++) {
            // users[i] = (String) epalIdList.get(i);
            // temp.add(epalIdList.get(i));
            // }
            // try {
            // easemobUtil.sendMessage(users, classScript);
            // } catch (Exception e) {
            // e.printStackTrace();
            // }
        }

    }

    @Override
    public Page<ClassRoomPush> findClassRoomPushList(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassRoomPush where 1 = 1 ");
        if (!"-1".equals(map.get("epalId"))) {
            sql.append(" and epalId = '" + map.get("epalId") + "'");
        }
        if (!"-1".equals(map.get("classRoomId"))) {
            sql.append(" and classRoomId = '" + map.get("classRoomId") + "'");
        }
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));

    }

    // 废弃
    @Override
    public Page getSchoolClassList(HashMap<String, String> map) {

        JSONArray result = new JSONArray();
        StringBuffer sql = new StringBuffer();
        sql.append("select id,schoolName from school where status=1");
        Query query = this.getQuery(sql.toString());
        Page dataPage = this.pageQueryBySQL(query, 1, 1000);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);
                JSONObject schoolInfo = new JSONObject();
                int schoolId = (Integer) obj[0];

                schoolInfo.put("schoolId", obj[0]);
                schoolInfo.put("schoolName", obj[1]);
                schoolInfo.put("classList", getClassList(schoolId));
                result.add(schoolInfo);
            }
        }

        Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    // 废弃
    public JSONArray getClassList(int schoolId) {
        JSONArray classList = new JSONArray();
        StringBuffer sql = new StringBuffer();
        sql.append("select id,className,schoolId from school_class where status=1 and schoolId=:schoolId");
        Query query = this.getQuery(sql.toString());
        query.setInteger("schoolId", schoolId);
        Page dataPage = this.pageQueryBySQL(query, 1, 1000);
        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
        if (dataList.size() > 0) {
            for (int j = 0; j < dataList.size(); j++) {
                Object[] obj = dataList.get(j);
                JSONObject temp = new JSONObject();
                temp.put("classId", obj[0]);
                temp.put("className", obj[1]);
                temp.put("schoolId", obj[2]);
                classList.add(temp);
            }
        }
        return classList;
    }

    @Override
    public void savePushStudentToClass(HashMap<String, String> map) {
        String classIds = map.get("classIds");
        String classRoomId = map.get("classRoomId");
        String classRoomName = map.get("classRoomName");
        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT id FROM class_student cs WHERE cs.epal_id IN (SELECT b.epalId FROM(SELECT * FROM school_class_to_student AS a WHERE a.classId IN ("
                        + classIds
                        + ")) c INNER JOIN device_student_school AS b ON c.studentId = b.id AND b.epalId IS NOT NULL)");
        Query query = this.getQuery(sql.toString());
        List ret = query.list();

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_student_rela (name,class_room_id,class_student_id,create_time) values (?,?,?,?)");
        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式

        for (int i = 0; i < ret.size(); i++) {
            try {
                pst.setString(1, classRoomName);
                pst.setInt(2, Integer.parseInt(classRoomId));
                pst.setInt(3, Integer.parseInt(ret.get(i).toString()));
                pst.setString(4, df.format(new Date()));
                pst.addBatch();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            pst.executeBatch(); // 执行批量处理
            conn.commit(); // 提交
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Page<ClassRoomCategory> findClassRoomsIndexList(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassRoomCategory where 1 = 1 ");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public ClassRoomCategory getClassRoomCategory(int id) {
        return (ClassRoomCategory) this.get(ClassRoomCategory.class, id);
    }

    @Override
    public void saveClassRoomCategory(ClassRoomCategory classRoomCategory) {
        this.saveOrUpdate(classRoomCategory);
    }

    @Override
    public Page findClassRoomsByNameOrCate(HashMap map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassRoom where 1 = 1 ");
        if (!"".equals(map.get("categoryId")) && null != map.get("categoryId")) {
            sql.append(" and categoryId = " + map.get("categoryId"));
        }
        if (!"".equals(map.get("className")) && null != map.get("className")) {
            sql.append(" and className like '%" + map.get("className") + "%'");
        }
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));
    }

    @Override
    public Page<ClassStudentRela> findStudentByRoomIdAndStudentId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassStudentRela where 1 = 1 ");
        if (!"".equals(map.get("classRoomId")) && null != map.get("classRoomId")) {
            sql.append(" and classRoomId = " + map.get("classRoomId"));
        }
        if (!"".equals(map.get("classStudentId")) && null != map.get("classStudentId")) {
            sql.append(" and classStudentId = " + map.get("classStudentId"));
        }
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));
    }

    @Override
    public Page<ClassGrades> findClassGradesByClassRoomId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" + "	cg.id," + "	cg.class_grades_name," + "	cg.parent_id," + "	cg.summary,"
                + "	cg.cover," + "	cg.sort," + "	cg.create_time" + " FROM" + "	class_grades cg"
                + " INNER JOIN class_room_grades_rela crgr ON cg.id = crgr.class_grades_id"
                + " AND crgr.class_room_id = :classRoomId ");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classRoomId"))) {
            query.setInteger("classRoomId", Integer.parseInt(map.get("classRoomId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassGrades> classGradess = new ArrayList<ClassGrades>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassGrades classGrades = new ClassGrades();
                Object[] obj = dataList.get(z);
                classGrades.setId((Integer) obj[0]);
                classGrades.setClassGradesName((String) obj[1]);
                classGrades.setParentId((Integer) obj[2]);
                classGrades.setSummary((String) obj[3]);
                classGrades.setCover((String) obj[4]);
                classGrades.setSort((Integer) obj[5]);
                classGrades.setCreateTime((Timestamp) obj[6]);
                classGradess.add(classGrades);
            }
        }

        Page resultPage = new Page<ClassGrades>(classGradess, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassStudentDto> findClassStudentsByClassGradesId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	cs.id," + "cs.`name` stuName," + "cs.epal_id," + "cgr.gradesStatus,"+"ccs.do_day,"+"cs.avatar,"+"cs.sex"
                + " FROM" + " class_grades_rela cgr ,`class_course_schedule` ccs,class_student cs"
                + " WHERE ccs.student_id = cs.id "
                + " AND cgr.class_grades_id = ccs.class_grades_id"
                + " AND ccs.student_id= cgr.class_student_id"
                + " AND ccs.class_grades_id = :classGradesId"
                + " ORDER BY cgr.gradesStatus desc");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classGradesId"))) {
            query.setInteger("classGradesId", Integer.parseInt(map.get("classGradesId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        JSONObject data = new JSONObject();

        JSONArray classStudentDtos = new JSONArray();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                JSONObject classStudentDto = new JSONObject();
                Object[] obj = dataList.get(z);
                classStudentDto.put("id", obj[0]);
                classStudentDto.put("studentName", obj[1]);
                classStudentDto.put("epalId", obj[2]);
                classStudentDto.put("gradesStatus", obj[3]);
                classStudentDto.put("doDay",obj[4]);
                classStudentDto.put("avatar",obj[5]);
                classStudentDto.put("sex",obj[6]);
                classStudentDtos.add(classStudentDto);
            }
        }

        Page resultPage = new Page<ClassStudentDto>(classStudentDtos, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassGrades> findClassGradesTreeByClassRoomId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT" + "	cg.id," + "	cg.class_grades_name," + "	cg.parent_id," + "	cg.summary,"
                + "	cg.cover," + "	cg.sort," + "	cg.create_time, " + "	IFNULL(crgr.id,-1) choose " + " FROM"
                + "	class_grades cg" + " LEFT JOIN class_room_grades_rela crgr ON cg.id = crgr.class_grades_id"
                + " AND crgr.class_room_id = :classRoomId ");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classRoomId"))) {
            query.setInteger("classRoomId", Integer.parseInt(map.get("classRoomId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        // 在线课堂数组
        ArrayList<ClassGrades> classGradess = new ArrayList<ClassGrades>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassGrades classGrades = new ClassGrades();
                Object[] obj = dataList.get(z);
                classGrades.setId((Integer) obj[0]);
                classGrades.setClassGradesName((String) obj[1]);
                classGrades.setParentId((Integer) obj[2]);
                classGrades.setSummary((String) obj[3]);
                classGrades.setCover((String) obj[4]);
                classGrades.setSort((Integer) obj[5]);
                classGrades.setCreateTime((Timestamp) obj[6]);
                classGrades.setChoose(obj[7].toString());
                classGradess.add(classGrades);
            }
        }

        Page resultPage = new Page<ClassGrades>(classGradess, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public void saveBatchGradesToClassRoom(HashMap<String, String> map) {
        String classGradesIds = map.get("classGradesIds");
        String classRoomId = map.get("classRoomId");
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_room_grades_rela (class_room_id,class_grades_id,create_time) values (?,?,?)");
        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        if (classGradesIds != null) {
            String[] classGradesIdsArr = classGradesIds.split(",");
            for (int i = 0; i < classGradesIdsArr.length; i++) {
                try {
                    pst.setInt(1, Integer.parseInt(classRoomId));
                    pst.setInt(2, Integer.parseInt(classGradesIdsArr[i]));
                    pst.setString(3, df.format(new Date()));
                    pst.addBatch();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (classGradesIds != null) {
                pst.executeBatch(); // 执行批量处理
                conn.commit(); // 提交
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClassCourse getClassCourse(int id) {
        return (ClassCourse) this.get(ClassCourse.class, id);
    }

    @Override
    public void saveClassCourse(ClassCourse classCourse) {
        this.saveOrUpdate(classCourse);
    }

    @Override
    public Page findClassCourses(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "SELECT c.id, r.class_name do_title, c.do_slot, c.do_day, c.class_room_id, c.class_grades_id, r.cover, c.create_time, c.do_date FROM class_course c, class_room r WHERE 1 = 1 ");
        if (!"-1".equals(map.get("classGradesId"))) {
            sql.append(" and  c.class_grades_id = '" + map.get("classGradesId") + "' AND c.class_room_id = r.id");
        } else {
            return null;
        }
        return this.pageQueryBySQL(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));

    }

    @Override
    public void delClassRoomFromClassCourse(HashMap map) {
        this.executeUpdateSQL("delete from class_course where class_course.id = " + map.get("classCourseId"));
    }

    @Override
    public boolean saveBatchClassRoomToClassCourse(HashMap<String, String> map) {
        String doTitles = map.get("doTitles");
        String doSlots = map.get("doSlots");
        String doDays = map.get("doDays");
        String classRoomIds = map.get("classRoomIds");
        String classGradesIds = map.get("classGradesIds");
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_course (do_title,do_slot,do_day,class_room_id,class_grades_id,create_time) values (?,?,?,?,?,?)");
        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        try {
            if (doTitles != null && doSlots != null && doDays != null && classRoomIds != null
                    && classGradesIds != null) {
                String[] doTitlesArr = doTitles.split(",");
                String[] doSlotsArr = doSlots.split(",");
                String[] doDaysArr = doDays.split(",");
                String[] classRoomIdsArr = classRoomIds.split(",");
                String[] classGradesIdsArr = classGradesIds.split(",");
                for (int i = 0; i < classRoomIdsArr.length; i++) {
                    try {
                        pst.setString(1, doTitlesArr[i]);
                        pst.setInt(2, Integer.parseInt(doSlotsArr[i]));
                        pst.setInt(3, Integer.parseInt(doDaysArr[i]));
                        pst.setInt(4, Integer.parseInt(classRoomIdsArr[i]));
                        pst.setInt(5, Integer.parseInt(classGradesIdsArr[i]));
                        pst.setString(6, df.format(new Date()));
                        pst.addBatch();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return false;
            }
            pst.executeBatch(); // 执行批量处理
            conn.commit(); // 提交
            conn.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<ClassCourse> saveBatchClassCourse(HashMap<String, String> map) {
        String classCoursesJson = map.get("classCoursesJson");
        ArrayList<ClassCourse> classCourses = new ArrayList<ClassCourse>();
        if (null != classCoursesJson) {
            JSONArray classCoursesJSONArray = JSONArray.fromObject(classCoursesJson);
            for (int i = 0; i < classCoursesJSONArray.size(); i++) {
                String[] formats = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};
                JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));
                ClassCourse classCourse = (ClassCourse) JSONObject.toBean(classCoursesJSONArray.getJSONObject(i),
                        ClassCourse.class);
                this.saveOrUpdate(classCourse);
                classCourses.add(classCourse);
            }
        }
        return classCourses;
    }

    @Override
    public Page getClassGradesByStudentId(HashMap map) {
        // 通过学生ID和班级类型取班级列表，状态为1的
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select b.id,b.class_grades_name,b.parent_id,b.summary,b.cover,b.sort,b.create_time from class_grades_rela as a,class_grades as b where a.class_grades_id=b.id and b.status=1 and a.class_student_id=:studentId");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("studentId"))) {
            query.setInteger("studentId", Integer.parseInt(map.get("studentId").toString()));
        }
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        ArrayList<ClassGrades> classGradess = new ArrayList<ClassGrades>();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassGrades classGrades = new ClassGrades();
                Object[] obj = dataList.get(z);
                classGrades.setId((Integer) obj[0]);
                classGrades.setClassGradesName((String) obj[1]);
                classGrades.setParentId((Integer) obj[2]);
                classGrades.setSummary((String) obj[3]);
                classGrades.setCover((String) obj[4]);
                classGrades.setSort((Integer) obj[5]);
                classGrades.setCreateTime((Timestamp) obj[6]);
                classGradess.add(classGrades);
            }
        }

        Page resultPage = new Page<ClassGrades>(classGradess, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public void switchClassGradesStatus(String id, String status) {
        // 修改班级状态，可见或者不可见 （0为不可见，1为可见）
        this.executeUpdateSQL("update class_grades set status=" + status + " where id = " + id);
    }

    @Override
    public void deleteClassGrades(String id) {
        // 删除课堂，暂时先屏蔽，不做任何操作

    }

    @Override
    public Page getClassGradesByTeacherId(HashMap map) {
        // 老师获取自己所有的班级列表
        StringBuffer sql = new StringBuffer();
        String teacherId = map.get("teacherId").toString();
        JSONArray allowTeacherList = new JSONArray();
        allowTeacherList.add("2");
        allowTeacherList.add("17");
        allowTeacherList.add("71");
        allowTeacherList.add("111");
        allowTeacherList.add("32");
        if (allowTeacherList.contains(teacherId)) {

            sql.append(
                    "select id,class_grades_name,parent_id,summary,cover,sort,create_time,status,teacher_id,grades_type,auditing_status,price,ifnull((select id from teacher_important_class_grades as a where a.class_grades_id=b.id and a.teacher_id=b.teacher_id),0),ifnull(classOpenDate,''),joinStatus from class_grades as b where grades_type in ('virtualClass','eleClass','basicClass') order by id desc");
        } else {

            sql.append(
                    "select id,class_grades_name,parent_id,summary,cover,sort,create_time,status,teacher_id,grades_type,auditing_status,price,ifnull((select id from teacher_important_class_grades as a where a.class_grades_id=b.id and a.teacher_id=b.teacher_id),0),ifnull(classOpenDate,''),joinStatus from class_grades as b where teacher_id=:teacherId and grades_type in ('virtualClass','eleClass','basicClass') order by id desc");

        }
        Query query = this.getQuery(sql.toString());
        if (allowTeacherList.contains(teacherId)) {

        } else {
            if (!"".equals(map.get("teacherId"))) {
                query.setInteger("teacherId", Integer.parseInt(map.get("teacherId").toString()));
            }

        }
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        ArrayList<ClassGrades> classGradess = new ArrayList<ClassGrades>();

        JSONArray result = new JSONArray();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                ClassGrades classGrades = new ClassGrades();
                JSONObject temp = new JSONObject();
                Object[] obj = dataList.get(z);
                temp.put("id", obj[0]);
                temp.put("classGradesName", obj[1]);
                temp.put("parentId", obj[2]);
                temp.put("summary", obj[3]);
                temp.put("cover", obj[4]);
                temp.put("sort", obj[5]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(obj[6]);
                temp.put("createTime", dateString);
                temp.put("status", obj[7]);
                temp.put("teacherId", obj[8]);
                temp.put("gradesType", obj[9]);
                temp.put("auditingStatus", obj[10]);
                temp.put("price", obj[11]);
                if (Integer.parseInt(obj[12].toString()) > 0) {
                    temp.put("isImportant", 1);
                } else {
                    temp.put("isImportant", 0);
                }
                temp.put("classOpenDate", obj[13]);
                temp.put("joinStatus", obj[14]);
                // classGrades.setId((Integer) obj[0]);
                // classGrades.setClassGradesName((String) obj[1]);
                // classGrades.setParentId((Integer) obj[2]);
                // classGrades.setSummary((String) obj[3]);
                // classGrades.setCover((String) obj[4]);
                // classGrades.setSort((Integer) obj[5]);
                // classGrades.setCreateTime((Timestamp) obj[6]);
                // classGrades.setStatus((Integer) obj[7]);
                // classGrades.setTeacherId((Integer) obj[8]);
                // classGrades.setGradesType((String) obj[9]);
                // classGrades.setAuditingStatus((Integer) obj[10]);
                // classGrades.setPrice((Integer) obj[11]);
                // //System.out.println(obj[12]);
                // classGradess.add(classGrades);
                result.add(temp);
            }
        }

        Page resultPage = new Page<ClassGrades>(result, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public ClassRoomTemp getClassRoomTemp(int id) {
        return (ClassRoomTemp) this.get(ClassRoomTemp.class, id);
    }

    @Override
    public void saveClassRoomTemp(ClassRoomTemp classRoom) {
        this.saveOrUpdate(classRoom);
    }

    @Override
    public ClassScriptNormalTemp getClassScriptNormalTemp(int id) {
        return (ClassScriptNormalTemp) this.get(ClassScriptNormalTemp.class, id);
    }

    @Override
    public void saveClassScriptNormalTemp(ClassScriptNormalTemp classScriptNormal) {
        this.saveOrUpdate(classScriptNormal);
    }

    @Override
    public Page<ClassRoomTemp> findClassRoomTemps(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassRoomTemp where 1 = 1 ");
        if (!map.get("teacherId").equals("-1")) {
            sql.append(" and teacherId = " + map.get("teacherId"));
        }
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public Page<ClassScriptNormalTemp> findClassScriptNormalTemp(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassScriptNormalTemp where 1 = 1 and classRoomId = " + map.get("classRoomId"));
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));

    }

    @Override
    public ClassCourseSchedule getClassCourseSchedule(int id) {
        return (ClassCourseSchedule) this.get(ClassCourseSchedule.class, id);
    }

    @Override
    public void saveClassCourseSchedule(ClassCourseSchedule classCourseSchedule) {
        this.saveOrUpdate(classCourseSchedule);
    }

    @Override
    public Page<ClassCourseSchedule> findClassCourseSchedules(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassCourseSchedule where 1 = 1 and studentId = " + map.get("studentId"));
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    @Override
    public ClassSlot getClassSlot(int id) {
        return (ClassSlot) this.get(ClassSlot.class, id);
    }

    @Override
    public void saveClassSlot(ClassSlot classSlot) {
        this.saveOrUpdate(classSlot);
    }

    @Override
    public Page<ClassSlot> findClassSlots(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassSlot where 1 = 1 and epalId = '" + map.get("epalId") + "'");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page")),
                Integer.parseInt(map.get("pageSize")));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.wechat.dao.LessonDao#saveStudentToGrades(java.util.HashMap)
     */
    /*
     * (non-Javadoc)
     *
     * @see com.wechat.dao.LessonDao#saveStudentToGrades(java.util.HashMap)
     */
    @Override
    public void saveStudentToGrades(HashMap map) {
        // 添加学生到虚拟班级
        String studentIds = map.get("studentIds").toString();
        String entityClassIds = map.get("entityClassIds").toString();
        String classGradesId = map.get("classGradesId").toString();
        // 批量添加学生到虚拟班级
        // 查询当前虚拟班级里现有的学生
        StringBuffer sql1 = new StringBuffer();
        sql1.append("select class_student_id from class_grades_rela where class_grades_id in (" + classGradesId
                + ") group by class_student_id");
        Query query1 = this.getQuery(sql1.toString());
        List gradesStudentList = query1.list();

        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pst2 = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_grades_rela (class_grades_id,class_student_id,create_time) values (?,?,?)");
        StringBuffer insertMainCourseSql2 = new StringBuffer(
                "insert into class_course_schedule (student_id,class_grades_id,class_room_id,start_time,last_time,do_day) values (?,?,?,?,?,?)");

        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
            pst2 = (PreparedStatement) conn.prepareStatement(insertMainCourseSql2.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != studentIds && !"".equals(studentIds)) {
            String[] students = studentIds.split(",");
            for (int i = 0; i < students.length; i++) {
                try {
                    if (gradesStudentList.contains(Integer.parseInt(students[i]))) {
                        continue;
                    }
                    pst.setInt(1, Integer.parseInt(classGradesId));
                    pst.setInt(2, Integer.parseInt(students[i]));
                    pst.setString(3, df.format(new Date()));
                    pst.addBatch();

                    pst2.setInt(1, Integer.parseInt(students[i]));
                    pst2.setInt(2, Integer.parseInt(classGradesId));
                    pst2.setInt(3, -1);

                    Timestamp createTime = new Timestamp(System.currentTimeMillis());

                    pst2.setTimestamp(4, createTime);
                    pst2.setTimestamp(5, createTime);

                    pst2.setInt(6, -1);
                    pst2.addBatch();

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // 批量添加实体班级到虚拟班级
        if (null != entityClassIds && !"".equals(entityClassIds)) {
            StringBuffer sql = new StringBuffer();
            sql.append("select class_student_id from class_grades_rela where class_grades_id in (" + entityClassIds
                    + ") group by class_student_id");
            Query query = this.getQuery(sql.toString());
            List studentIdList = query.list();
            for (int i = 0; i < studentIdList.size(); i++) {
                try {
                    if (gradesStudentList.contains(Integer.parseInt(studentIdList.get(i).toString()))) {
                        continue;
                    }
                    pst.setInt(1, Integer.parseInt(classGradesId));
                    pst.setInt(2, Integer.parseInt(studentIdList.get(i).toString()));
                    pst.setString(3, df.format(new Date()));
                    pst.addBatch();

                    pst2.setInt(1, Integer.parseInt(studentIdList.get(i).toString()));
                    pst2.setInt(2, Integer.parseInt(classGradesId));
                    pst2.setInt(3, -1);

                    Timestamp createTime = new Timestamp(System.currentTimeMillis());

                    pst2.setTimestamp(4, createTime);
                    pst2.setTimestamp(5, createTime);

                    pst2.setInt(6, -1);
                    pst2.addBatch();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

        try {
            pst.executeBatch();
            pst2.executeBatch();
            conn.commit(); // 提交
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 执行批量处理

    }

    @Override
    public Page findClassRoomByClassRoomId(HashMap map) {
        StringBuffer sql = new StringBuffer();
        sql.append("from ClassRoom where 1 = 1 and id = " + map.get("classRoomId"));
        return this.pageQueryByHql(sql.toString(), Integer.parseInt((String) map.get("page")),
                Integer.parseInt((String) map.get("pageSize")));
    }

    @Override
    public Page getClassGradesList(HashMap map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select a.id,a.class_grades_name,a.parent_id,a.summary,a.cover,a.sort,a.create_time,a.grades_type,a.teacher_id,a.auditing_status,a.price,b.name from class_grades as a,class_teacher as b where a.teacher_id=b.id and status=1 and grades_type='virtualClass'");
        if (null != map.get("name") && !"".equals(map.get("name").toString())) {
            sql.append(" and class_grades_name like '%" + map.get("name") + "%'");
        } else {

        }
        sql.append(" order by a.sort DESC");
        Query query = this.getQuery(sql.toString());
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        ArrayList<HashMap> classGradess = new ArrayList<HashMap>();

        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                HashMap data = new HashMap();
                Object[] obj = dataList.get(z);
                data.put("id", obj[0]);
                data.put("classGradesName", obj[1]);
                data.put("parentId", obj[2]);
                data.put("summary", obj[3]);
                data.put("cover", obj[4]);
                data.put("sort", obj[5]);
                data.put("createTime", obj[6]);
                data.put("gradesType", obj[7]);
                data.put("teacherId", obj[8]);
                data.put("auditingStatus", obj[9]);
                data.put("price", obj[10]);
                data.put("teacherName", obj[11]);
                classGradess.add(data);
            }
        }

        Page resultPage = new Page<HashMap>(classGradess, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public void saveClassGradesAuditing(HashMap map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("update class_grades set auditing_status=" + map.get("auditingStatus") + " where id="
                + map.get("classGradesId"));
        this.executeUpdateSQL(sql.toString());
    }

    @Override
    public void copyClassRoomToTearcher(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        String teacherId = map.get("teacherId");
        String classRoomId = map.get("classRoomId");
        String teacherName = map.get("teacherName");
        // 复制课堂到老师名下

        ClassRoom classRoom = new ClassRoom();
        try {
            classRoom = (ClassRoom) BeanModifyFilter.modifyFilter(classRoom,
                    this.getClassRoom(Integer.parseInt(classRoomId)));
        } catch (NumberFormatException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        classRoom.setId(null);
        classRoom.setStatus(0);
        classRoom.setTeacherName(teacherName);
        classRoom.setTeacherId(Integer.parseInt(teacherId));
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        classRoom.setCreateTime(createTime);
        classRoom.setGroupId("0");
        classRoom.setSort(0);

        this.saveOrUpdate(classRoom);

        int newClassRoomId = classRoom.getId();

        //复制标签
        List<ClassRoomLabelRela> classRoomLabelRelaList = ClassRoomLabelRela.dao.find("select * from class_room_label_rela where class_room_id = ? and status = 1", classRoomId);
        if (classRoomLabelRelaList.size() > 0) {
            List<ClassRoomLabelRela> newClassRoomLabelRelaList = new ArrayList<>();
            for (ClassRoomLabelRela crl : classRoomLabelRelaList) {
                ClassRoomLabelRela classRoomLabelRela = new ClassRoomLabelRela();
                classRoomLabelRela.setLabelId(crl.getLabelId());
                classRoomLabelRela.setClassRoomId(newClassRoomId);
                classRoomLabelRela.setStatus(1);
                newClassRoomLabelRelaList.add(classRoomLabelRela);
            }

            Db.batchSave(newClassRoomLabelRelaList, newClassRoomLabelRelaList.size());
        }

        // 复制课堂指令到老师名下
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_script_normal (class_room_id,class_script_type_id,class_script_content,sort,create_time,total_time) values (?,?,?,?,?,?)");
        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuffer sql = new StringBuffer();
        sql.append("select class_script_type_id,class_script_content,sort,total_time from class_script_normal where class_room_id="
                + classRoomId);
        Query query = this.getQuery(sql.toString());
        Page dataPage = this.pageQueryBySQL(query, 1, 10000);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        ArrayList<HashMap> classGradess = new ArrayList<HashMap>();

        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                HashMap data = new HashMap();
                Object[] obj = dataList.get(z);

                try {
                    pst.setInt(1, newClassRoomId);
                    if (obj[0] != null) {
                        pst.setInt(2, (Integer) obj[0]);
                    } else {
                        pst.setObject(2, null);
                    }
                    pst.setString(3, (String) obj[1]);
                    pst.setInt(4, (Integer) obj[2]);
                    pst.setTimestamp(5, createTime);
                    pst.setInt(6, (Integer) obj[3]);
                    pst.addBatch();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            pst.executeBatch(); // 执行批量处理
            conn.commit(); // 提交
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pst = null;
            conn = null;
        }


        PreparedStatement pst2 = null;
        // 复制课堂资源到新课堂
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuffer insertClassRoomAudioRel = new StringBuffer(
                "insert into class_room_audio_rel (class_room_id,audio_id,material_file_id) values (?,?,?)");
        try {
            pst2 = (PreparedStatement) conn.prepareStatement(insertClassRoomAudioRel.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuffer selectClassRoomAudioRel = new StringBuffer(
                "select class_room_id,audio_id,material_file_id from class_room_audio_rel where class_room_id = " + classRoomId);
        List<Object[]> classRoomAudioRels = this.createSQLQuery(selectClassRoomAudioRel.toString()).list();

        if (classRoomAudioRels.size() > 0 && classRoomAudioRels.get(0)[0] != null) {
            for (int z = 0; z < classRoomAudioRels.size(); z++) {

                Object[] obj = classRoomAudioRels.get(z);

                try {

                    pst2.setInt(1, newClassRoomId);
                    pst2.setInt(2, (Integer) obj[1]);
                    pst2.setInt(3, (Integer) obj[2]);
                    pst2.addBatch();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {

        }

        try {
            pst2.executeBatch(); // 执行批量处理
            conn.commit(); // 提交
            conn.close();
            pst2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void delClassRoomTemp(HashMap map) {
        this.executeUpdateSQL("delete from class_room_temp where class_room_temp.id = " + map.get("tempId"));
    }

    @Override
    public void delClassScriptNormalTemp(HashMap map) {
        this.executeUpdateSQL(
                "delete from class_script_normal_temp where class_script_normal_temp.id = " + map.get("tempScriptId"));
        if (!"".equals(map.get("classRoomId").toString()) && null != map.get("classRoomId").toString()) {
            StringBuffer sql = new StringBuffer();
            sql.append("from ClassScriptNormalTemp where classRoomId=" + map.get("classRoomId"));
            sql.append(" order by sort asc");
            Page dataPage = this.pageQueryByHql(sql.toString(), 1, 10000);

            ArrayList<ClassScriptNormalTemp> dataList = (ArrayList<ClassScriptNormalTemp>) dataPage.getItems();
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    ClassScriptNormalTemp classScriptNormalTemp = dataList.get(i);
                    classScriptNormalTemp.setSort(i);
                    this.saveOrUpdate(classScriptNormalTemp);
                }

            }
        }

    }

    @Override
    public void quitGradesByStudentId(HashMap<String, String> map) {
        Db.update("update class_grades_rela set gradesStatus=" + map.get("gradesStatus") + " where class_grades_id="
                + map.get("gradesId") + " and class_student_id=" + map.get("studentId"));

        // this.executeUpdateSQL("update class_grades_rela set gradesStatus="
        // + map.get("gradesStatus") + " where class_grades_id="
        // + map.get("gradesId") + " and class_student_id="
        // + map.get("studentId"));
    }

    @Override
    public void saveCommentModel(OnlineClassCommentModel onlineClassCommentModel) {
        // 保存或者修改评价模板
        this.saveOrUpdate(onlineClassCommentModel);

    }

    @Override
    public Page getOnlineClassCommentModel(HashMap map) {
        // 通过老师ID获取评价模板
        StringBuffer sql = new StringBuffer();
        sql.append("from OnlineClassCommentModel where 1 = 1 ");
        if (!"".equals(map.get("teacherId"))) {
            sql.append(" and teacherId = " + map.get("teacherId"));
        }
        sql.append("order by id desc");
        return this.pageQueryByHql(sql.toString(), Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));
    }

    @Override
    public void saveStudentFromGradesByteacher(OnlineClassDeleteStudentRecord onlineClassDeleteStudentRecord) {
        // 保存老师从班级中删除学生记录
        this.saveOrUpdate(onlineClassDeleteStudentRecord);
    }

    @Override
    public void saveOnlineClassComment(OnlineClassComment onlineClassComment) {
        // 保存老师评价
        this.saveOrUpdate(onlineClassComment);
    }

    @Override
    public JSONObject getClassGradesInfo(String classGradesId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append("select " + "a.id gradesId," + "a.parent_id," + "a.class_grades_name," + "a.summary," + "a.cover,"
                + "a.sort," + "a.create_time classGradesCreateTime," + "a.grades_type," + "a.teacher_id," + "a.status,"
                + "a.auditing_status," + "a.price," + "b.name," + "b.member_id," + "b.create_time teacherCreateTime,"
                + "b.level from class_grades as a,class_teacher as b where a.teacher_id=b.id and a.id=:classGradesId");
        Query query = this.getQuery(sql.toString());
        query.setString("classGradesId", classGradesId);
        Page dataPage = this.pageQueryBySQL(query, 1, 1);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
        JSONObject data = new JSONObject();
        ArrayList<HashMap> classGradess = new ArrayList<HashMap>();
        if (dataList.size() > 0) {
            Object[] obj = dataList.get(0);
            data.put("gradesId", obj[0]);
            data.put("parentId", obj[1]);
            data.put("classGradesName", obj[2]);
            data.put("summary", obj[3]);
            data.put("cover", obj[4]);
            data.put("sort", obj[5]);
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String gradesCreateTime = sdf.format((Timestamp) obj[6]);
            data.put("gradesCreateTime", gradesCreateTime);
            data.put("gradesType", obj[7]);
            data.put("teacherId", obj[8]);
            data.put("status", obj[9]);
            data.put("auditingStatus", obj[10]);
            data.put("price", obj[11]);
            data.put("teacherName", obj[12]);
            data.put("teacherMemberId", Integer.parseInt((String) obj[13]));
            String teacherCreateTime = sdf.format((Timestamp) obj[14]);
            data.put("teacherCreateTime", teacherCreateTime);
            data.put("teacherLevel", obj[15]);
        }
        return data;
    }

    @Override
    public void saveClassCourseScoreRecord(ClassCourseScoreRecord classCourseScoreRecord) {
        // TODO Auto-generated method stub
        this.saveOrUpdate(classCourseScoreRecord);
        Object[] obj = (Object[]) this.executeSQL(
                "SELECT a.student_id,b.do_slot from class_course_record as a,class_course as b where a.class_course_id=b.id and a.id="
                        + classCourseScoreRecord.getClassCourseRecordId())
                .get(0);
        String stuId = obj[0].toString();
        String doSlot = obj[1].toString();
        int score = 0;
        if (doSlot.equals("100"))
            score = 3;
        if (doSlot.equals("300"))
            score = 5;
        if (doSlot.equals("500"))
            score = 2;
        String sql = "update class_student set integral=integral+" + score + " where id=" + stuId;
        this.executeUpdateSQL(sql);
    }

    @Override
    public Page getSignOutClassGradesListBystudentId(HashMap<String, String> map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select a.id,b.id gradesId,b.class_grades_name,b.summary,b.cover,b.teacher_id,b.status,a.gradesStatus ,a.type from class_grades_rela as a,class_grades as b where a.class_grades_id=b.id and a.class_student_id=:studentId");
        Query query = this.getQuery(sql.toString());
        query.setString("studentId", map.get("studentId"));
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classGradess = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);
                data.put("id", obj[0]);
                data.put("classGradesId", obj[1]);
                data.put("classGradesName", obj[2]);
                data.put("summary", obj[3]);
                data.put("cover", obj[4]);
                data.put("teacherId", obj[5]);
                data.put("status", obj[6]);
                data.put("gradesStatus", obj[7]);
                data.put("relaType", obj[8]);
                classGradess.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classGradess, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public JSONObject getClassCourseByClassGradesId(HashMap map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select a.id classCourseId,a.class_room_id,b.class_name,a.do_slot,a.do_day,ifnull(a.do_date,'null') from class_course as a,class_room as b where a.class_room_id=b.id and a.class_grades_id=:classGradesId");
        Query query = this.getQuery(sql.toString());
        query.setString("classGradesId", map.get("classGradesId").toString());
        Page dataPage = this.pageQueryBySQL(query, 1, 1000);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();
        JSONObject result = new JSONObject();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);
                data.put("classCourseId", obj[0]);
                data.put("classRoomId", obj[1]);
                data.put("className", obj[2]);
                data.put("doSlot", obj[3]);
                data.put("doDay", obj[4]);
                String doDate = "";
                if ("null".equals(obj[5]) || null == (String) obj[5]) {
                    Date currdate = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar ca = Calendar.getInstance();
                    ca.add(Calendar.DATE, (Integer) obj[4] - 1);// num为增加的天数，可以改变的
                    currdate = ca.getTime();
                    doDate = format.format(currdate);
                } else {
                    doDate = (String) obj[5];
                }
                data.put("doDate", doDate);

                if (result.containsKey(doDate)) {
                    JSONArray temp2 = new JSONArray();
                    temp2 = result.getJSONArray(doDate);
                    temp2.add(data);
                    result.put(doDate, temp2);
                } else {
                    JSONArray dateData = new JSONArray();
                    dateData.add(data);
                    result.put(doDate, dateData);
                }

            }
        }

        return result;
    }

    public JSONArray getTeacherClassGradesListByAgent(Integer agentId, Integer page, Integer pageSize) {
        // 通过加盟商下所有的的老师ID，查询所有班级列表
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select a.id,a.class_grades_name,a.summary,a.cover,a.teacher_id,a.status,a.auditing_status,a.price,a.sort,(select count(class_student_id) from class_grades_rela where class_grades_rela.class_grades_id=a.id),b.name,a.classOpenDate,a.grades_type from class_grades as a,class_teacher as b where a.teacher_id=b.id and a.status>=0 and a.grades_type in ('virtualClass','eleClass') and b.agent_id=:agentId");
        Query query = this.getQuery(sql.toString());
        query.setInteger("agentId", agentId);
        Page dataPage = this.pageQueryBySQL(query, page, pageSize);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classGradess = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("classGradesId", obj[0]);
                data.put("classGradesName", obj[1]);
                data.put("summary", obj[2]);
                data.put("cover", obj[3]);
                data.put("teacherId", obj[4]);
                data.put("teacherName", obj[10]);
                data.put("status", obj[5]);
                data.put("auditingStatus", obj[6]);
                data.put("price", obj[7]);
                data.put("sort", obj[8]);
                data.put("studentCount", Integer.parseInt(obj[9].toString()));
                data.put("classOpenDate", obj[11]);
                data.put("gradesType", obj[12]);
                classGradess.add(data);
            }
        }

        return classGradess;

    }

    @Override
    public Page searchClassGrades(HashMap map) {
        // 查询所有虚拟班级
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select a.id,a.class_grades_name,a.summary,a.cover,a.teacher_id,a.status,a.auditing_status,a.price,a.sort,(select count(class_student_id) from class_grades_rela where class_grades_rela.class_grades_id=a.id) studentCount,b.name,a.classOpenDate,a.grades_type from class_grades as a,class_teacher as b where a.teacher_id=b.id and a.status>=0 and a.grades_type in ('virtualClass','eleClass')");
        if (!"".equals(map.get("searchStr").toString()) && null != map.get("searchStr")) {

            sql.append(" and a.class_grades_name like '%" + map.get("searchStr")).append("%'");
        }
        if (!"".equals(map.get("teacherId").toString()) && null != map.get("teacherId")) {

            sql.append(" and a.teacher_id=:teacherId");
        }

        if (Integer.parseInt(map.get("userId").toString()) > 1000) {
            sql.append(" and a.id in (select class_grades_id from user_to_class_grades where user_id="
                    + map.get("userId").toString() + ")");
        }
        sql.append(" order by studentCount desc");
        Query query = this.getQuery(sql.toString());

        if (!"".equals(map.get("teacherId").toString()) && null != map.get("teacherId")) {

            query.setInteger("teacherId", Integer.parseInt(map.get("teacherId").toString()));
        }

        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classGradess = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("classGradesId", obj[0]);
                data.put("classGradesName", obj[1]);
                data.put("summary", obj[2]);
                data.put("cover", obj[3]);
                data.put("teacherId", obj[4]);
                data.put("teacherName", obj[10]);
                data.put("status", obj[5]);
                data.put("auditingStatus", obj[6]);
                data.put("price", obj[7]);
                data.put("sort", obj[8]);
                data.put("studentCount", Integer.parseInt(obj[9].toString()));
                data.put("classOpenDate", obj[11]);
                data.put("gradesType", obj[12]);
                classGradess.add(data);
            }
        }
        Integer pageSize = Integer.parseInt(map.get("pageSize").toString());
        int totalCount = dataPage.getTotalCount();
        // 还差一个判断页码的
        if (classGradess.size() < pageSize) {
            JSONArray tempClassGradesList = getTeacherClassGradesListByAgent(
                    Integer.parseInt(map.get("userId").toString()), 1, pageSize - classGradess.size());
            totalCount = totalCount + tempClassGradesList.size();
            if (tempClassGradesList.size() > 0) {
                int maxIndex = 0;
                if (pageSize - classGradess.size() > tempClassGradesList.size()) {
                    maxIndex = tempClassGradesList.size();

                } else {
                    maxIndex = pageSize - classGradess.size();
                }
                for (int i = 0; i < maxIndex; i++) {
                    classGradess.add(tempClassGradesList.getJSONObject(i));
                }
            }
        }

        Page resultPage = new Page<HashMap>(classGradess, totalCount, dataPage.getIndexes(), dataPage.getStartIndex(),
                dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public ClassGrades updateClassGradesStatus(HashMap map) {
        // 修改班级状态

        StringBuffer sql = new StringBuffer("from ClassGrades where id=:id");
        Query query = this.getQuery(sql.toString());
        int id = Integer.parseInt(map.get("id").toString());
        query.setInteger("id", Integer.parseInt(map.get("id").toString()));
        ClassGrades classGrades = (ClassGrades) this.get(ClassGrades.class, id);
        classGrades.setStatus(Integer.parseInt(map.get("status").toString()));
        this.saveOrUpdate(classGrades);
        return classGrades;
    }

    @Override
    public ClassGrades updateClassGradesOpenDate(HashMap map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("from ClassGrades where id=:id");
        Query query = this.getQuery(sql.toString());
        int id = Integer.parseInt(map.get("id").toString());
        query.setInteger("id", Integer.parseInt(map.get("id").toString()));
        ClassGrades classGrades = (ClassGrades) this.get(ClassGrades.class, id);
        classGrades.setClassOpenDate(map.get("classOpenDate").toString());
        this.saveOrUpdate(classGrades);
        return classGrades;
    }

    @Override
    public void saveQuoteClassCourse(HashMap map) {
        // TODO Auto-generated method stub
        String sourceClassGradesId = map.get("sourceClassGradesId").toString();
        Integer targetClassGradesId = Integer.parseInt(map.get("targetClassGradesId").toString());
        StringBuffer sql = new StringBuffer(
                "select id,do_title,do_slot,do_day,class_room_id,class_grades_id,cover,create_time,do_date from class_course where class_grades_id=:sourceClassGradesId");
        Query query = this.getQuery(sql.toString());
        query.setInteger("sourceClassGradesId", Integer.parseInt(map.get("sourceClassGradesId").toString()));

        Connection conn = null;
        PreparedStatement pst = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuffer insertMainCourseSql = new StringBuffer(
                "insert into class_course (do_title,do_slot,do_day,class_room_id,class_grades_id,cover,create_time,do_date) values (?,?,?,?,?,?,?,?)");
        try {
            pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Page dataPage = this.pageQueryBySQL(query, 1, 1000);

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        for (int i = 0; i < dataList.size(); i++) {
            try {
                Object[] obj = dataList.get(i);
                pst.setString(1, (String) obj[1]);
                pst.setInt(2, (Integer) obj[2]);
                pst.setInt(3, (Integer) obj[3]);
                pst.setInt(4, (Integer) obj[4]);
                pst.setInt(5, targetClassGradesId);
                pst.setString(6, (String) obj[6]);
                pst.setDate(7, new java.sql.Date(((Timestamp) obj[7]).getTime()));
                pst.setString(8, (String) obj[8]);
                pst.addBatch();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            pst.executeBatch();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 执行批量处理
        try {
            conn.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // 提交
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public JSONArray findClassStudentsRecordByClassGradesId(HashMap<String, String> map) {
        // 通过班级ID查询班级下所有学生的学习情况
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT" + "	cgr.class_student_id," + "	cs.`name` stuName," + "	cs.epal_id," + "cgr.gradesStatus"
                + " FROM" + "	class_grades_rela cgr"
                + " INNER JOIN class_student cs ON cgr.class_grades_id = :classGradesId"
                + " AND cgr.class_student_id = cs.id ");
        Query query = this.getQuery(sql.toString());
        if (!"".equals(map.get("classGradesId"))) {
            query.setInteger("classGradesId", Integer.parseInt(map.get("classGradesId")));
        }
        Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page").toString()),
                new Integer(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        StringBuffer sql2 = new StringBuffer();

        sql2.append(
                "select a.class_room_id,b.id,a.complete_time from class_course_record as a,class_student as b,class_course as c where a.student_id=b.id and a.class_room_id=c.class_room_id and c.do_slot=300 and a.class_grades_id=:classGradesId");
        Query query2 = this.getQuery(sql2.toString());
        if (!"".equals(map.get("classGradesId"))) {
            query2.setInteger("classGradesId", Integer.parseInt(map.get("classGradesId")));
        }
        Page dataPage2 = this.pageQueryBySQL(query2, 1, 10000000);

        ArrayList<Object[]> dataList2 = (ArrayList<Object[]>) dataPage2.getItems();
        JSONObject epalToClassRoomHash = new JSONObject();

        JSONObject epalLastTimeHash = new JSONObject();

        for (int j = 0; j < dataList2.size(); j++) {
            Object[] obj = dataList2.get(j);
            Integer classRoomId = (Integer) obj[0];
            Integer studentId = (Integer) obj[1];
            Date createTime = (Date) obj[2];
            Long createTimeLong = createTime.getTime();

            JSONArray classRoomList = new JSONArray();
            if (epalToClassRoomHash.containsKey(studentId.toString())) {
                classRoomList = epalToClassRoomHash.getJSONArray(studentId.toString());
                if (classRoomList.contains(classRoomId)) {

                } else {
                    classRoomList.add(classRoomId);
                    epalToClassRoomHash.put(studentId.toString(), classRoomList);
                }

            } else {
                classRoomList.add(classRoomId);
                epalToClassRoomHash.put(studentId.toString(), classRoomList);
            }

            if (epalLastTimeHash.containsKey(studentId.toString())) {
                if (Long.parseLong(epalLastTimeHash.get(studentId.toString()).toString()) <= createTimeLong) {
                    epalLastTimeHash.put(studentId.toString(), createTimeLong);
                }
            } else {
                epalLastTimeHash.put(studentId.toString(), createTimeLong);
            }
        }

        StringBuffer sql3 = new StringBuffer();
        sql3.append("select * from class_course where do_slot=300 and class_grades_id=:classGradesId");
        Query query3 = this.getQuery(sql3.toString());
        query3.setInteger("classGradesId", Integer.parseInt(map.get("classGradesId")));
        List temp3 = query3.list();

        StringBuffer sql4 = new StringBuffer();
        sql4.append("select epal_id,friend_id from device_relation where isbind=1");
        Query query4 = this.getQuery(sql4.toString());
        Page dataPage4 = this.pageQueryBySQL(query4, 1, 10000);
        ArrayList<Object[]> dataList4 = (ArrayList<Object[]>) dataPage4.getItems();
        JSONObject deviceRelation = new JSONObject();

        for (int i = 0; i < dataList4.size(); i++) {
            Object[] obj = dataList4.get(i);
            deviceRelation.put(obj[0], obj[1]);
        }

        Integer classRoomCount = temp3.size();
        // 在线课堂数组
        JSONArray classStudentDtos = new JSONArray();
        if (dataList.size() > 0 && dataList.get(0)[0] != null) {
            for (int z = 0; z < dataList.size(); z++) {
                // 主课程
                JSONObject classStudentDto = new JSONObject();
                Object[] obj = dataList.get(z);
                Integer studentId = (Integer) obj[0];
                classStudentDto.put("id", obj[0]);
                classStudentDto.put("studentName", obj[1]);
                String epalId = (String) obj[2];
                classStudentDto.put("epalId", epalId);
                classStudentDto.put("gradesStatus", obj[3]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (epalToClassRoomHash.containsKey(studentId.toString())) {
                    String lastStudyTime = sdf
                            .format(new Date(Long.parseLong(epalLastTimeHash.get(studentId.toString()).toString())));
                    classStudentDto.put("lastStudyTime", lastStudyTime);
                    classStudentDto.put("studyCount", epalToClassRoomHash.getJSONArray(studentId.toString()).size());
                } else {
                    classStudentDto.put("lastStudyTime", "");
                    classStudentDto.put("studyCount", 0);
                }
                classStudentDto.put("classRoomCount", classRoomCount);
                if (deviceRelation.containsKey(epalId)) {
                    classStudentDto.put("bindMobile", deviceRelation.get(epalId).toString());

                } else {
                    classStudentDto.put("bindMobile", "");
                }
                classStudentDtos.add(classStudentDto);
            }
        }
        return classStudentDtos;
    }

    @Override
    public Page getClassRoomListByClassGradesId(HashMap map) {
        // 通过班级ID获取班级课程列表
        StringBuffer sql = new StringBuffer();
        String classGradesId = map.get("classGradesId").toString();
        sql.append("SELECT b.id AS classRoomId, b.class_name, b.cover, b.summary, a.do_day , a.do_title, a.id AS classCourseId,");
        sql.append("IFNULL(c.today_lesson, 0) AS today_lesson FROM class_course a LEFT JOIN ( SELECT a.id AS course_id, 1 AS today_lesson, b.id AS");
        sql.append(" grade_id FROM class_course a, class_grades b WHERE a.class_grades_id = b.id AND a.do_day = DATEDIFF(CURDATE(),");
        sql.append("IFNULL(b.classOpenDate, b.create_time)) ) c ON a.id = c.course_id, class_room b WHERE (a.class_room_id = b.id AND a.do_slot = 300");
        sql.append(" AND a.class_grades_id = :classGradesId)");

        sql.append(" order by a.do_day desc");
        Query query = this.getQuery(sql.toString());
        query.setInteger("classGradesId", Integer.parseInt(classGradesId));

        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classRoomList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("classRoomId", obj[0]);
                data.put("classRoomName", obj[1]);
                data.put("cover", obj[2]);
                data.put("summary", obj[3]);

                data.put("doDay", obj[4]);
                data.put("doTitle", obj[5]);
                data.put("classCourseId", obj[6]);
                data.put("today_lesson", obj[7]);
                classRoomList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classRoomList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page getStudentListByClassGradesIdAndClassCourseId(HashMap map) {
        // 通过过班级ID和课程ID获取学过的学生列表
        StringBuffer sql = new StringBuffer();
        String classGradesId = map.get("classGradesId").toString();
        String classCourseId = map.get("classCourseId").toString();
        sql.append("select c.id studentId,c.`name`,a.score,a.audioUrl,a.recordDate,a.fluency,"
                + "a.accuracy,a.integrity,a.timeCost,"
                + "ifnull((select id  from class_course_comment where class_course_comment.class_course_id=b.class_course_id and class_course_comment.student_id=c.id),0) classCourseCommendId,"
                + "ifnull((select teacher_sound from class_course_comment where class_course_comment.class_course_id=b.class_course_id and class_course_comment.student_id=c.id),null) teacherSound,"
                + " ifnull( (SELECT teacher_score FROM class_course_comment WHERE	class_course_comment.class_course_id = b.class_course_id AND class_course_comment.student_id = c.id ), NULL ) teacherScore,	ifnull (date_format(( SELECT update_date FROM class_course_comment WHERE class_course_comment.class_course_id = b.class_course_id AND class_course_comment.student_id = c.id),'%Y-%c-%d %h:%i:%s'),a.recordDate ) teacherRecordDate,b.do_title,ifnull(c.avatar,'') as avatar "
                + "from "
                + "( SELECT classCourseRecord_id, max(score) score, audioUrl, recordDate, fluency, accuracy, integrity, timeCost, id FROM class_course_score_record GROUP BY classCourseRecord_id ) AS a,"
                + "( SELECT max(score) score, class_course_id, do_title, id, student_id, class_grades_id FROM class_course_record WHERE class_course_id = :classCourseId AND class_grades_id = :classGradesId  GROUP BY class_grades_id, class_course_id, student_id ) AS  b,class_student AS c "
                + "where " + "a.classCourseRecord_id=b.id " + "and b.student_id=c.id "
                + "and b.class_grades_id=:classGradesId ");
        if (!"".equals(classCourseId) && null != classCourseId) {
            sql.append(" and b.class_course_id=:classCourseId");
        }

        sql.append(" order by a.score desc");
        Query query = this.getQuery(sql.toString());
        query.setInteger("classGradesId", Integer.parseInt(classGradesId));
        query.setInteger("classCourseId", Integer.parseInt(classCourseId));

        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classRoomList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("studentId", obj[0]);
                data.put("studentName", obj[1]);
                data.put("score", obj[2]);
                data.put("audioUrl", obj[3]);

                data.put("recordDate", obj[4]);
                data.put("fluency", obj[5]);
                data.put("accuracy", obj[6]);
                data.put("integrity", obj[7]);
                data.put("timeCost", obj[8]);
                if (null == obj[9]) {
                    data.put("classCourseCommendId", 0);
                } else {

                    data.put("classCourseCommendId", Integer.parseInt(obj[9].toString()));
                }
                if (null == obj[10]) {
                    data.put("teacherSound", "");
                } else {

                    data.put("teacherSound", obj[10]);
                }
                if (null == obj[11]) {
                    data.put("teacherScore", "");
                } else {

                    data.put("teacherScore", obj[11]);
                }
                data.put("teacherRecordDate", obj[12]);
                data.put("doTitle", obj[13]);
                data.put("avatar", obj[14]);

                classRoomList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classRoomList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public void saveClassCourseComment(ClassCourseComment classCourseComment) {
        // TODO 保存评价
        this.saveOrUpdate(classCourseComment);
    }

    @Override
    public void saveJoinClassDate(HashMap map) {
        // TODO 保存加班时间
        String classGradesId = map.get("classGradesId").toString();
        String joinClassDate = map.get("joinClassDate").toString();
        String tempTime = joinClassDate + " 00:00:00";
        StringBuffer sql = new StringBuffer();
        sql.append("update class_course_schedule set start_time='" + tempTime + "',last_time='" + tempTime
                + "',do_day=1 where class_grades_id=" + classGradesId);
        this.executeUpdateSQL(sql.toString());
    }

    @Override
    public JSONArray getClassCourseComment(HashMap map) {
        // TODO 获取课程评论
        StringBuffer sql = new StringBuffer();
        String studentId = map.get("studentId").toString();
        String classCourseId = map.get("classCourseId").toString();
        sql.append(
                "select id,teacher_sound,teacher_score,insert_date,effective_date from class_course_comment where class_course_id=:classCourseId and student_id=:studentId");
        sql.append(" order by id desc");
        Query query = this.getQuery(sql.toString());
        query.setInteger("studentId", Integer.parseInt(studentId));
        query.setInteger("classCourseId", Integer.parseInt(classCourseId));

        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray result = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("studentId", Integer.parseInt(studentId));
                data.put("classCourseId", Integer.parseInt(classCourseId));
                data.put("classCourseCommentId", obj[0]);
                data.put("teacherSound", obj[1]);
                data.put("teacherScore", obj[2]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String commentDate = sdf.format((Date) obj[3]);
                String effectiveDate = "";
                try {
                    effectiveDate = sdf.format((Date) obj[4]);
                } catch (NullPointerException e) {
                }
                data.put("commentDate", commentDate);
                data.put("effectiveDate", effectiveDate);
                result.add(data);
            }
        }
        return result;
    }

    @Override
    public void updateClassGradesAuditingStatus(HashMap map) {
        // TODO 修改班级审核状态
        String classGradesId = map.get("classGradesId").toString();
        String auditingStatus = map.get("auditingStatus").toString();
        StringBuffer sql = new StringBuffer();
        sql.append("update class_grades set auditing_status=" + auditingStatus + " where id=" + classGradesId);
        this.executeUpdateSQL(sql.toString());
    }

    @Override
    public PublicRoomFidToStudent getPublicRoomFidToStudent(String fid) {
        // 通过RFID卡号，获取学生ID
        StringBuffer sql = new StringBuffer();
        sql.append("from PublicRoomFidToStudent where cardFid=" + fid);
        List<PublicRoomFidToStudent> result = this.executeHQL(sql.toString());
        PublicRoomFidToStudent publicRoomFidToStudent = new PublicRoomFidToStudent();
        if (result.size() > 0) {
            publicRoomFidToStudent = result.get(0);
        }
        return publicRoomFidToStudent;
    }

    @Override
    public void saveUserToClassGrades(UserToClassGrades userToClassGrades) {
        // 废弃
        this.saveOrUpdate(userToClassGrades);
    }

    @Override
    public void savepublicRoomFidToStudent(PublicRoomFidToStudent publicRoomFidToStudent) {
        // 保存RFID卡和学生的绑定关系
        this.saveOrUpdate(publicRoomFidToStudent);
    }

    public Page getTeacherList(HashMap map) {
        // TODO 获取老师列表（如果agentId大于10000，则限制为当前用户的老师列表）
        StringBuffer sql = new StringBuffer();
        String agentId = map.get("userId").toString();
        if (Integer.parseInt(agentId) > 10000) {
            sql.append("select * from class_teacher where agent_id=:agentId");
        } else {

            sql.append("select * from class_teacher");
        }

        Query query = this.getQuery(sql.toString());

        if (Integer.parseInt(agentId) > 10000) {
            query.setInteger("agentId", Integer.parseInt(agentId));
        } else {

        }
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray teacherList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("teacherId", (Integer) obj[0]);
                data.put("name", (String) obj[1]);
                data.put("memberId", (String) obj[2]);
                // data.put("createTime",(String) obj[3]);

                data.put("level", (Integer) obj[4]);
                data.put("agentId", (Integer) obj[5]);
                teacherList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(teacherList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page searchClassRoom(HashMap map) {
        // 获取课程列表
        StringBuffer sql = new StringBuffer();
        String agentId = map.get("userId").toString();
        if (Integer.parseInt(agentId) > 10000) {
            sql.append("select cr.id,cr.teacher_id,cr.teacher_name,cr.class_name,cr.cover,"
                    + "ifnull(cr.summary,'暂无简介'),cr.status,cr.create_time from class_room as cr,class_teacher as ct where cr.teacher_id=ct.id and ct.agent_id=:agentId");
        } else {

            sql.append("select  cr.id,cr.teacher_id,cr.teacher_name,cr.class_name,cr.cover,"
                    + "ifnull(cr.summary,'暂无简介'),cr.status,cr.create_time from class_room as cr");
        }

        Query query = this.getQuery(sql.toString());
        if (Integer.parseInt(agentId) > 10000) {
            query.setInteger("agentId", Integer.parseInt(agentId));
        } else {

        }
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classRoomList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("classRoomId", (Integer) obj[0]);
                data.put("teacherId", (Integer) obj[1]);
                data.put("teacherName", (String) obj[2]);
                data.put("classRoomName", (String) obj[3]);
                data.put("cover", (String) obj[4]);
                data.put("summary", (String) obj[5]);
                data.put("status", (Integer) obj[6]);
                data.put("createTime", (Date) obj[7]);
                classRoomList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classRoomList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page searchStudentList(HashMap map) {
        // 获取学生列表，如果登录的账号为加盟商账号，则只取加盟商的学生
        StringBuffer sql = new StringBuffer();
        String agentId = map.get("userId").toString();
        if (Integer.parseInt(agentId) > 10000) {
            sql.append("");
        } else {
            sql.append("select id,name,epal_id,create_time,student_type from class_student order by sort_id desc");

        }
        Query query = this.getQuery(sql.toString());
        if (Integer.parseInt(agentId) > 10000) {
            query.setInteger("agentId", Integer.parseInt(agentId));
        } else {

        }
        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classRoomList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("classStudentId", (Integer) obj[0]);
                data.put("studentName", (String) obj[1]);
                data.put("epalId", (String) obj[2]);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String str = sdf.format((Date) obj[3]);
                data.put("createTime", str);
                data.put("studentType", (Integer) obj[4]);
                classRoomList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classRoomList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page getStudentListByClassGradesIdAndDate(HashMap<String, Object> map) {
        // 通过过班级ID和日期获取学过的学生列表
        StringBuffer sql = new StringBuffer();
        String classGradesId = map.get("classGradesId").toString();
        String date = map.get("date").toString();
        sql.append(" SELECT " + " c.student_id, " + " c. NAME, " + "  IF(c.score < 0,0,c.score) AS score, " + " c.audioUrl, " + " date_format(  "
                + "  c.recordDate,   " + "  '%Y-%c-%d %h:%i:%s'   " + "   ) recordDate,   " + " c.fluency, "
                + " c.accuracy, " + " c.integrity, " + " c.timeCost, " + " ifnull( " + "  cc.id, " + "  0 "
                + " ) classCourseCommendId, " + " cc.teacher_sound, " + " cc.teacher_score, " + " c.class_course_id, "
                + " ifnull( " + "  date_format( " + " cc.update_date, " + " '%Y-%c-%d %h:%i:%s' " + "  ), "
                + "  c.recordDate " + " ) teacherRecordDate, " + " c.do_title " + " FROM " + " ( " + "  SELECT "
                + "   cr.student_id, " + "   s. NAME, " + "   cr.score, " + "   csr.audioUrl, "
                + "   MAX(cr.complete_time) recordDate, " + "   csr.fluency, " + "   csr.accuracy, "
                + "   csr.integrity, " + "   csr.timeCost, " + "   cr.id class_course_id,"
                + "   r.class_name do_title " + "  FROM " + "   class_course_record cr "
                + "  LEFT JOIN class_course_score_record csr ON cr.id = csr.classCourseRecord_id "
                + "  AND cr.score = csr.score, " + "  class_student s, " + "  class_room r , " + "  class_course cc "
                + "  WHERE " + "  cr.class_grades_id = :classGradesId " + " AND cr.complete_time >  :startTime  "
                + " AND cr.complete_time <  :endTime  " + " AND cr.student_id = s.id "
                + " AND r.id = cr.class_room_id  AND cr.class_course_id = cc.id " + " AND cc.do_slot = 300 "
                + " GROUP BY " + "  student_id " + " ) c "
                + "LEFT JOIN class_course_comment cc ON c.class_course_id = cc.class_course_id "
                + "AND c.student_id = cc.student_id");
        Query query = this.getQuery(sql.toString());
        query.setInteger("classGradesId", Integer.parseInt(classGradesId));
        query.setString("startTime", date + " 00:00:00");
        query.setString("endTime", date + " 23:59:59");

        Page dataPage = this.pageQueryBySQL(query, Integer.parseInt(map.get("page").toString()),
                Integer.parseInt(map.get("pageSize").toString()));

        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray classRoomList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("studentId", obj[0]);
                data.put("studentName", obj[1]);
                data.put("score", obj[2]);
                data.put("audioUrl", obj[3]);

                data.put("recordDate", obj[4]);
                data.put("fluency", obj[5]);
                data.put("accuracy", obj[6]);
                data.put("integrity", obj[7]);
                data.put("timeCost", obj[8]);
                if (null == obj[9]) {
                    data.put("classCourseCommendId", 0);
                } else {

                    data.put("classCourseCommendId", Integer.parseInt(obj[9].toString()));
                }
                if (null == obj[10]) {
                    data.put("teacherSound", "");
                } else {

                    data.put("teacherSound", obj[10]);
                }
                if (null == obj[11]) {
                    data.put("teacherScore", "");
                } else {

                    data.put("teacherScore", obj[11]);
                }
                if (null == obj[12]) {
                    data.put("classCourseId", "");
                } else {
                    data.put("classCourseId", obj[12]);
                }
                data.put("teacherRecordDate", obj[13]);
                data.put("doTitle", obj[14]);
                classRoomList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(classRoomList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    @Override
    public Page<ClassCourseComment> getComByCIdAndSId(HashMap<String, String> map) {
        StringBuffer sql = new StringBuffer("Select ccc FROM ClassCourseComment ccc, ClassCourse cc "
                + "WHERE cc.classGradesId = " + map.get("classGradesId")
                + " AND ccc.classCourseId = cc.id AND ccc.studentId =" + map.get("studentId"));
        return this.pageQueryByHql(sql.toString(), 1, 10000);
    }

    // 老师删除课堂时候，判断是否该课有人上过
    @Override
    public boolean findClassCourseRecord(String classCourseId) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("Select * FROM class_course_record where class_course_id=" + classCourseId);
        Query query = this.getQuery(sql.toString());
        if (query.list().size() > 0) {
            return true;

        } else {
            return false;

        }

    }

    @Override
    public void saveStudyRule(StdDiyStudyDay stdDiyStudyDay) {
        // TODO Auto-generated method stub
        this.saveOrUpdate(stdDiyStudyDay);
    }

    @Override
    public Page getStudyRule(HashMap map) {
        // TODO Auto-generated method stub
        StringBuffer sql = new StringBuffer("SELECT " + "ifnull(b.id, 0)," + "ifnull(b.status, 1),"
                + "cgr.class_student_id," + "cgr.class_grades_id," + "ifnull(" + "b.rule," + "(" + "SELECT " + "rule "
                + "FROM " + "std_diy_study_day " + "WHERE " + "grade_id = cgr.class_grades_id " + "AND rule_type=0 "
                + "ORDER BY " + "id DESC " + "LIMIT 1" + ")" + ")," + "ifnull(b.rule_type, 0)," + "ifnull(" + "b.week,"
                + "(" + "SELECT " + "week " + "FROM " + "std_diy_study_day " + "WHERE "
                + "grade_id=cgr.class_grades_id " + "AND rule_type=0 " + "ORDER BY " + "id DESC " + "LIMIT 1" + ")"
                + ")," + "a.class_grades_name," + "ifnull((" + "SELECT " + "GROUP_CONCAT(" + "CONCAT_WS(" + "'#',"
                + "e.do_slot," + "e. name," + "e.do_time" + ")" + "ORDER BY " + "e.do_time ASC SEPARATOR '_'"
                + ") AS temp " + "FROM " + "class_slot AS e," + "class_student AS f " + "WHERE "
                + "e.epal_id=f.epal_id " + "and f.id =:stdId" + "),'100#早读#07:00:00_300#课堂#19:00:00_500#晚听#20:00:00'), "
                + "a.grades_type, " + "ifnull(" + "b.is_teacher_default," + "(" + "SELECT " + "is_teacher_default "
                + "FROM " + "std_diy_study_day " + "WHERE " + "grade_id=cgr.class_grades_id " + "AND rule_type=0 "
                + "ORDER BY " + "id DESC " + "LIMIT 1" + ")" + ")," + "a.cover" +" from " + "class_grades AS a,"
                + "class_grades_rela AS cgr " + "LEFT JOIN (" + "SELECT " + "* " + "from " + "std_diy_study_day AS a "
                + "WHERE " + "a.std_id=:stdId " + ") AS b ON b.grade_id = cgr.class_grades_id " + "WHERE " + "a.id"
                + "= cgr.class_grades_id " + "AND cgr.class_student_id =:stdId " + "AND cgr.gradesStatus = 1");

        Query query = this.getQuery(sql.toString());
        query.setInteger("stdId", Integer.parseInt(map.get("stdId").toString()));
        // query.setInteger("StdId2",
        // Integer.parseInt(map.get("stdId").toString()));
        Page dataPage = this.pageQueryBySQL(query, 1, 10000);
        ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage.getItems();

        JSONArray roleList = new JSONArray();
        if (dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                JSONObject data = new JSONObject();
                Object[] obj = dataList.get(i);

                data.put("id", obj[0]);
                data.put("status", obj[1]);
                data.put("gradeName", obj[7]);
                data.put("gradesType", obj[9]);
                data.put("cover", obj[11]);
                JSONArray define = new JSONArray();
                String temp = (String) obj[8];
                for (int j = 0; j < temp.split("_").length; j++) {
                    String temp1 = temp.split("_")[j];
                    String[] temp2 = temp1.split("#");
                    JSONObject temp3 = new JSONObject();
                    temp3.put("id", Integer.parseInt(temp2[0].toString()));
                    temp3.put("name", temp2[1]);
                    temp3.put("time", temp2[2]);
                    define.add(temp3);
                }
                data.put("define", define);
                if (null != obj[4] && !"".equals(obj[4].toString())) {
                    data.put("rule", obj[4]);
                    data.put("week", obj[6]);
                    data.put("ruleType", obj[5]);
                    data.put("isTeacherDefault", obj[10]);
                } else {
                    StdDiyStudyDay stdDiyStudyDay = getDefaultStudyRule(Integer.parseInt(obj[3].toString()));
                    data.put("rule", stdDiyStudyDay.getRule());
                    data.put("week", stdDiyStudyDay.getWeek());
                    data.put("ruleType", stdDiyStudyDay.getRuleType());
                    data.put("isTeacherDefault", stdDiyStudyDay.getIsTeacherDefault());
                }
                data.put("studentId", obj[2]);

                data.put("classGradesId", obj[3]);
                roleList.add(data);
            }
        }
        Page resultPage = new Page<HashMap>(roleList, dataPage.getTotalCount(), dataPage.getIndexes(),
                dataPage.getStartIndex(), dataPage.getPageSize());
        return resultPage;
    }

    public StdDiyStudyDay getDefaultStudyRule(Integer classGradesId) {
        StdDiyStudyDay stdDiyStudyDay = new StdDiyStudyDay();
        StringBuffer sql = new StringBuffer("from StdDiyStudyDay where ruleType=-1");
        Page dataPage = this.pageQueryByHql(sql.toString(), 1, 10);
        ArrayList<StdDiyStudyDay> dataList = (ArrayList<StdDiyStudyDay>) dataPage.getItems();
        if (dataList.size() > 0) {
            stdDiyStudyDay = dataList.get(0);
        }

        return stdDiyStudyDay;
    }

    @Override
    public StdDiyStudyDay getStdDiyStudyDay(int id) {
        // TODO Auto-generated method stub
        return (StdDiyStudyDay) this.getEntity(StdDiyStudyDay.class, id);
    }

    @Override
    public ClassStudent getClassStudentByEpalId(String epalId) {
        // TODO Auto-generated method stub
        ClassStudent classStudent = new ClassStudent();
        StringBuffer sql = new StringBuffer("from ClassStudent where epal_id='" + epalId + "'");
        Page dataPage = this.pageQueryByHql(sql.toString(), 1, 1);
        ArrayList<ClassStudent> dataList = (ArrayList<ClassStudent>) dataPage.getItems();
        if (dataList.size() > 0) {
            classStudent = dataList.get(0);
        } else {

        }
        return classStudent;
    }

    @Override
    public void deleteClassScriptNormal(HashMap map) {
        // TODO Auto-generated method stub
        this.executeUpdateSQL("delete from class_script_normal where class_script_normal.id in ( " + map.get("id") + ")");
        if (!"".equals(map.get("classRoomId").toString()) && null != map.get("classRoomId").toString()) {
            StringBuffer sql = new StringBuffer();
            sql.append("from ClassScriptNormal where classRoomId=" + map.get("classRoomId"));
            sql.append(" order by sort asc");
            Page dataPage = this.pageQueryByHql(sql.toString(), 1, 10000);

            ArrayList<ClassScriptNormal> dataList = (ArrayList<ClassScriptNormal>) dataPage.getItems();
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    ClassScriptNormal classScriptNormal = dataList.get(i);
                    classScriptNormal.setSort(i);
                    this.saveOrUpdate(classScriptNormal);
                }

            }
        }
    }

    @Override
    public ClassStudent getStudentByStudentId(String studentId) {
        // 通过学生ID获取学生对象
        return (ClassStudent) this.getEntity(ClassStudent.class, Integer.parseInt(studentId));
    }

    @Override
    public void addAudioRelation(String audioIds, Integer classRoomId) {

        Session session = this.getSession();

        String[] _audioIds = audioIds.split(",");

        session.beginTransaction();

        String sql = "";

        for (String audioId : _audioIds) {
            sql = "insert into class_room_audio_rel (class_room_id,audio_id) values (?,?)";
            session.createSQLQuery(sql).setInteger(0, classRoomId).setInteger(1, Integer.parseInt(audioId))
                    .executeUpdate();
        }

        session.getTransaction().commit();

    }

    @Override
    public void batchSaveClassCourseComment(List<ClassCourseComment> classCourseComments, int batchSize) {
        this.batchSaveOrUpdate(classCourseComments, batchSize);
    }

}
