package com.wechat.dao;

import com.wechat.entity.*;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface DeviceDao {
	
	
	void saveDevice(Device device);
	
	void deleteDevice(Integer id);
	
	Page searchDevices(HashMap map);
	
	Device searchDevice(Integer id);

	Device searchDeviceByDeviceNo(String deviceNo);
	
	Device searchDeviceByEpalId(String epalId);
	
	Page searchDeviceStudys(HashMap map);

	void saveDeviceStudy(DeviceStudy deviceStudy);

	void deleteDeviceStudy(DeviceStudy deviceStudy);

	Page searchDeviceCollections(HashMap map);

	void saveDeviceCollection(DeviceCollection deviceCollection);

	void deleteDeviceCollection(DeviceCollection deviceCollection);

	Page searchDeviceStorys(HashMap map);

	void saveDeviceStory(DeviceStory deviceStory);

	void deleteDeviceStory(DeviceStory deviceStory);

	DeviceTest searchDeviceTestByDeviceNo(String deviceNo);
	
	

	
	Page searchDevicePlayRecords(HashMap map);

	void saveDevicePlayRecord(DevicePlayRecord devicePlayRecord);

	void deleteDevicePlayRecord(DevicePlayRecord devicePlayRecord);

	Page searchDevicePropertys(HashMap map);

	void saveDeviceProperty(DeviceProperty deviceProperty);

	void deleteDeviceProperty(DeviceProperty deviceProperty);

	Page searchDeviceRelations(HashMap map);
	
	Page searchDevicebBindList(HashMap map);
	
	

	void deleteDeviceRelation(DeviceRelation deviceRelation);

	void saveDeviceRelation(DeviceRelation deviceRelation);

	Page searchDeviceSchedules(HashMap map);

	void saveDeviceSchedule(DeviceSchedule deviceSchedule);

	void deleteDeviceSchedule(DeviceSchedule deviceSchedule);

	Page searchDeviceTests(HashMap map);

	void deleteDeviceTestByDeviceNo(String deviceNo);

	void saveCourseSchedule(CourseSchedule courseSchedule);

	Page searchCoursesByProductId(HashMap map);

	void saveDeviceFile(DeviceFile deviceFile);

	Page searchDeviceFiles(HashMap map);

	void deleteDeviceFile(DeviceFile deviceFile);

	Page searchCourseSchedulesByDeviceNo(HashMap map);

	Page searchCourseScheduleByProductId(HashMap map);

	Page searchCourseSchedulesByEpalId(HashMap map);

	Page searchCourseSchedulesByEpalId2(HashMap map);

	void saveCourseScheduleNow(CourseScheduleNow courseSchedule);

	List<CourseScheduleNow> getCurrentCourseSchedule(HashMap map);

	Integer findCourseScheduleNow(String epalId, Integer projectId);

	DeviceRelation getDeviceRelation(DeviceRelation deviceRelation);

	MemberChildren getChildrenInfoFromDevice(String epalId);

	void updateDeviceBindRelation(HashMap map);

	Page searchDeviceList(HashMap map);

	void saveSoundPlayHistory(DeviceSoundPlayHistory dsph);


	Page getSoundPlayHistoryFromDevice(HashMap map);

	void saveDeviceActivity(DeviceActivity deviceActivity);

	Page getReplacement(HashMap map);

	void saveReplacement(Replacement replacement);

	void saveSynchronousData(HashMap map);

	String getNewEpalId(String epalId);

	Integer getDeviceCount();

	Page getOnlineDeviceRecord(HashMap map);

	Page getCategoryList(HashMap map);

	JSONArray getDeviceListByCategory(HashMap map);

	void saveDeviceCategory(DeviceCategory deviceCategory);

	void saveDeviceNoToCategory(HashMap map);

	JSONObject getDeviceActive(HashMap map);

	void saveHistorySchedules(HistorySchedules historySchedules);

	JSONArray searchHistorySchedules(HashMap map);

	void saveDeviceLiber(DeviceLiber deviceLiber);

	JSONArray getUploadFileList(HashMap map);

	void deleteUploadFile(String id);

	void saveUploadFileInfo(DeviceUploadFile deviceUploadFile);


}
