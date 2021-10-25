package com.wechat.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wechat.dao.impl.BaseDaoImpl;
import com.wechat.entity.ClassTeacher;
import com.wechat.service.ClassTeacherService;

@Service
public class ClassTeacherServiceImpl extends BaseDaoImpl<ClassTeacher> implements ClassTeacherService{

	@Override
	public List<ClassTeacher> getTeachersByStudent(Integer studentId) {
		
		String hql = "SELECT c.id,c.name,c.memberId,c.createTime,c.agentId,c.level FROM ClassGradesRela a,ClassGrades b,ClassTeacher c"
				+ " WHERE a.classStudentId = :studentId AND a.classGradesId = b.id AND b.teacherId = c.id group by c.id";
		
		List<Object[]> list = this.getSession().createQuery(hql).setInteger("studentId", studentId).list();
		
		List<ClassTeacher> classTeachers = new ArrayList<ClassTeacher>();
		
		for(Object[] obj : list){
			
			ClassTeacher classTeacher = new ClassTeacher();
			classTeacher.setId((Integer) obj[0]);
			classTeacher.setName((String) obj[1]);
			classTeacher.setMemberId((String) obj[2]);
			classTeacher.setCreateTime((Timestamp) obj[3]);
			classTeacher.setAgentId((Integer) obj[4]);
			classTeacher.setLevel((Integer) obj[5]);
			
			classTeachers.add(classTeacher);
		}
		
		return classTeachers;
	}

}
