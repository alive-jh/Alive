package com.wechat.dao.impl;

import com.wechat.dao.GradeDao;
import com.wechat.entity.ClassGradesRela;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;
@Repository
public class GradeDaoImpl extends BaseDaoImpl<ClassGradesRela>  implements GradeDao{

	@Override
	public Page<ClassGradesRela> getBySidAndGId(int sid, int gid) {

		String hql = "from ClassGradesRela where  classGradesId = "+gid+" and classStudentID = " + sid+ " and gradesStatus = 1";
		
		return this.pageQueryByHql(hql, 1, 100);
	}

}
