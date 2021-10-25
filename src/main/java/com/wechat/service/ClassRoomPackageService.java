package com.wechat.service;


import com.wechat.util.Page;

import java.util.HashMap;

//import com.wechat.entity.ClassRoomPackage;

public interface ClassRoomPackageService {
//	void saveClassRoomPackage(ClassRoomPackage classRoomPackage);
	void deleteClassRoomPackage(HashMap map);
	Page searchClassRoomPackage(HashMap map);
}
