package com.wechat.dao;

import com.wechat.entity.ClassGradesRela;
import com.wechat.util.Page;

public interface GradeDao {
	
	Page<ClassGradesRela> getBySidAndGId(int sid, int gid);
}
