package com.wechat.service.impl;

import com.wechat.dao.DeviceDao;
import com.wechat.entity.*;
import com.wechat.service.DeviceService;
import com.wechat.util.Page;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

	@Resource
	private
	DeviceDao deviceDao;
	
	@Override
	public void saveDevice(Device device) {
		this.deviceDao.saveDevice(device);
	}

	@Override
	public void deleteDevice(Integer id) {
		this.deviceDao.deleteDevice(id);
	}

	@Override
	public Page searchDevices(HashMap map) {
		return this.deviceDao.searchDevices(map);
	}

	public DeviceDao getDeviceDao() {
		return deviceDao;
	}

	public void setDeviceDao(DeviceDao deviceDao) {
		this.deviceDao = deviceDao;
	}

	@Override
	public Device searchDevice(Integer id) {
		return this.deviceDao.searchDevice(id);
	}

	@Override
	public Device searchDeviceByDeviceNo(String deviceNo) {
		return this.deviceDao.searchDeviceByDeviceNo(deviceNo);
	}
	
	@Override
	public Device searchDeviceByEpalId(String EpalId) {
		return this.deviceDao.searchDeviceByEpalId(EpalId);
	}


	@Override
	public Page searchDeviceStudys(HashMap map) {
		return this.deviceDao.searchDeviceStudys(map);
	}

	@Override
	public void saveDeviceStudy(DeviceStudy deviceStudy) {

		this.deviceDao.saveDeviceStudy(deviceStudy);
	}

	@Override
	public void deleteDeviceStudy(DeviceStudy deviceStudy) {
		this.deviceDao.deleteDeviceStudy(deviceStudy);
	}

	@Override
	public Page searchDeviceCollections(HashMap map) {
		return this.deviceDao.searchDeviceCollections(map);
	}

	@Override
	public void saveDeviceCollection(DeviceCollection deviceCollection) {
		this.deviceDao.saveDeviceCollection(deviceCollection);
	}

	@Override
	public void deleteDeviceCollection(DeviceCollection deviceCollection) {
		this.deviceDao.deleteDeviceCollection(deviceCollection);
	}

	@Override
	public Page searchDeviceStorys(HashMap map) {
		return this.deviceDao.searchDeviceStorys(map);
	}

	@Override
	public void saveDeviceStory(DeviceStory deviceStory) {
		this.deviceDao.saveDeviceStory(deviceStory);
	}

	@Override
	public void deleteDeviceStory(DeviceStory deviceStory) {
		this.deviceDao.deleteDeviceStory(deviceStory);
	}

	@Override
	public DeviceTest searchDeviceTestByDeviceNo(String deviceNo) {
		return this.deviceDao.searchDeviceTestByDeviceNo(deviceNo);
	}

	@Override
	public Page searchDevicePlayRecords(HashMap map) {
		return this.deviceDao.searchDevicePlayRecords(map);
	}

	@Override
	public void saveDevicePlayRecord(DevicePlayRecord devicePlayRecord) {
		this.deviceDao.saveDevicePlayRecord(devicePlayRecord);
	}

	@Override
	public void deleteDevicePlayRecord(DevicePlayRecord devicePlayRecord) {
		this.deviceDao.deleteDevicePlayRecord(devicePlayRecord);
	}

	@Override
	public Page searchDevicePropertys(HashMap map) {
		return this.deviceDao.searchDevicePropertys(map);
	}

	@Override
	public void saveDeviceProperty(DeviceProperty deviceProperty) {
		this.deviceDao.saveDeviceProperty(deviceProperty);
	}

	@Override
	public void deleteDeviceProperty(DeviceProperty deviceProperty) {
		this.deviceDao.deleteDeviceProperty(deviceProperty);
	}

	@Override
	public Page searchDeviceRelations(HashMap map) {
		return this.deviceDao.searchDeviceRelations(map);
	}

	@Override
	public Page searchDevicebBindList(HashMap map) {
		return this.deviceDao.searchDevicebBindList(map);
	}
	
	@Override
	public void deleteDeviceRelation(DeviceRelation deviceRelation) {
		this.deviceDao.deleteDeviceRelation(deviceRelation);
	}

	@Override
	public void saveDeviceRelation(DeviceRelation deviceRelation) {
		this.deviceDao.saveDeviceRelation(deviceRelation);
	}

	@Override
	public Page searchDeviceSchedules(HashMap map) {
		return this.deviceDao.searchDeviceSchedules(map);
	}

	@Override
	public void saveDeviceSchedule(DeviceSchedule deviceSchedule) {
		this.deviceDao.saveDeviceSchedule(deviceSchedule);
	}

	@Override
	public void deleteDeviceSchedule(DeviceSchedule deviceSchedule) {
		this.deviceDao.deleteDeviceSchedule(deviceSchedule);
	}

	@Override
	public Page searchDeviceTests(HashMap map) {
		return this.deviceDao.searchDeviceTests(map);
	}

	@Override
	public void deleteDeviceTestByDeviceNo(String deviceNo) {
		this.deviceDao.deleteDeviceTestByDeviceNo(deviceNo);
	}

	@Override
	public void saveCourseSchedule(CourseSchedule courseSchedule) {
		this.deviceDao.saveCourseSchedule(courseSchedule);
	}

	@Override
	public Page searchCoursesByProductId(HashMap map) {
		return this.deviceDao.searchCoursesByProductId(map) ;
	}

	@Override
	public void saveDeviceFile(DeviceFile deviceFile) {
		this.deviceDao.saveDeviceFile(deviceFile);
	}

	@Override
	public Page searchDeviceFiles(HashMap map) {
		return this.deviceDao.searchDeviceFiles(map);
	}

	@Override
	public void deleteDeviceFile(DeviceFile deviceFile) {
		this.deviceDao.deleteDeviceFile(deviceFile);
	}

	@Override
	public Page searchCourseSchedulesByDeviceNo(HashMap map) {
		return this.deviceDao.searchCourseSchedulesByDeviceNo(map);
	}

	@Override
	public Page searchCourseScheduleByProductId(HashMap map) {
		return this.deviceDao.searchCourseScheduleByProductId(map);
	}

	@Override
	public Page searchCourseSchedulesByEpalId(HashMap map) {
		return this.deviceDao.searchCourseSchedulesByEpalId(map);
	}

	@Override
	public Page searchCourseSchedulesByEpalId2(HashMap map) {
		return this.deviceDao.searchCourseSchedulesByEpalId2(map);
	}
	
	

	@Override
	public List<CourseScheduleNow> getCurrentCourseSchedule(HashMap map) {
		return this.deviceDao.getCurrentCourseSchedule(map);
	}

	@Override
	public void saveCourseScheduleNow(CourseScheduleNow courseSchedule) {
		
		this.deviceDao.saveCourseScheduleNow(courseSchedule);
	}

	@Override
	public Integer findCourseScheduleNow(String epalId, Integer projectId) {
		return this.deviceDao.findCourseScheduleNow(epalId,projectId);
	}

	@Override
	public DeviceRelation getDeviceRelation(DeviceRelation deviceRelation) {
		return  this.deviceDao.getDeviceRelation(deviceRelation);
	}

	@Override
	public MemberChildren getChildrenInfoFromDevice(String epalId) {
		// TODO Auto-generated method stub
		return this.deviceDao.getChildrenInfoFromDevice(epalId);
	}

	@Override
	public void updateDeviceBindRelation(HashMap map) {
		// TODO Auto-generated method stub
		this.deviceDao.updateDeviceBindRelation(map);
		
	}

	@Override
	public Page searchDeviceList(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.searchDeviceList(map);
	}

	@Override
	public void saveSoundPlayHistory(DeviceSoundPlayHistory dsph) {
		// TODO Auto-generated method stub
		this.deviceDao.saveSoundPlayHistory(dsph);
	}

	@Override
	public Page getSoundPlayHistoryFromDevice(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getSoundPlayHistoryFromDevice(map);
	}

	@Override
	public void saveDeviceActivity(DeviceActivity deviceActivity) {
		// TODO Auto-generated method stub
		this.deviceDao.saveDeviceActivity(deviceActivity);
	}

	@Override
	public Page getReplacement(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getReplacement(map);
	}

	@Override
	public void saveReplacement(Replacement replacement) {
		// TODO Auto-generated method stub
		this.deviceDao.saveReplacement(replacement);
	}

	@Override
	public void saveSynchronousData(HashMap map) {
		// TODO Auto-generated method stub
		this.deviceDao.saveSynchronousData(map);
		
	}

	@Override
	public String getNewEpalId(String epalId) {
		// TODO Auto-generated method stub
		return this.deviceDao.getNewEpalId(epalId);
		
	}

	@Override
	public Integer getDeviceCount() {
		// 获取机器人人总数
		return this.deviceDao.getDeviceCount();
	}

	@Override
	public Page getOnlineDeviceRecord(HashMap map) {
		// 获取机器人在线记录
		return this.deviceDao.getOnlineDeviceRecord(map);
	}

	@Override
	public Page getCategoryList(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getCategoryList(map);
	}

	@Override
	public JSONArray getDeviceListByCategory(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getDeviceListByCategory(map);
	}

	@Override
	public void saveDeviceCategory(DeviceCategory deviceCategory) {
		// TODO Auto-generated method stub
		this.deviceDao.saveDeviceCategory(deviceCategory);
	}

	@Override
	public void saveDeviceNoToCategory(HashMap map) {
		// TODO Auto-generated method stub
		this.deviceDao.saveDeviceNoToCategory(map);
		
	}

	@Override
	public JSONObject getDeviceActive(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getDeviceActive(map);
	}

	@Override
	public void saveHistorySchedules(HistorySchedules historySchedules) {
		// TODO Auto-generated method stub
		this.deviceDao.saveHistorySchedules(historySchedules);
	}

	@Override
	public JSONArray searchHistorySchedules(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.searchHistorySchedules(map);
	}

	@Override
	public void saveBookToMyLib(DeviceLiber deviceLiber) {
		// TODO Auto-generated method stub
		this.deviceDao.saveDeviceLiber(deviceLiber);
	}

	@Override
	public JSONArray getUploadFileList(HashMap map) {
		// TODO Auto-generated method stub
		return this.deviceDao.getUploadFileList(map);
	}

	@Override
	public void deleteUploadFile(String id) {
		// TODO Auto-generated method stub
		this.deviceDao.deleteUploadFile(id);
	}

	@Override
	public void saveUploadFileInfo(DeviceUploadFile deviceUploadFile) {
		// TODO Auto-generated method stub
		this.deviceDao.saveUploadFileInfo(deviceUploadFile);
	}

}
