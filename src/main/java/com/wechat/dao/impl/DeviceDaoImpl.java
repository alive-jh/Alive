package com.wechat.dao.impl;

import com.mysql.jdbc.PreparedStatement;
import com.wechat.dao.DeviceDao;
import com.wechat.dao.LessonDao;
import com.wechat.entity.*;
import com.wechat.entity.dto.ChildrenCourseDto;
import com.wechat.entity.dto.CourseDto;
import com.wechat.entity.dto.CourseScheduleDto;
import com.wechat.entity.dto.CourseScheduleInfoDto;
import com.wechat.util.Page;
import com.wechat.util.PropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao {


	
	static final String JDBC_DRIVER = PropertyUtil
			.getDataBaseProperty("driverClass");
	static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

	static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
	static final String PASS = PropertyUtil.getDataBaseProperty("password");
	
	@Autowired
	LessonDao lessonDao;
	
	@Override
	public void saveDevice(Device device) {
		this.saveOrUpdate(device);
	}

	@Override
	public void deleteDevice(Integer id) {
		StringBuffer sql = new StringBuffer("delete from Device where id = "
				+ id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDevices(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT " + "dd.id," + // 0
				"dd.device_no," + // 1
				"dd.epal_id," + // 2
				"dd.epal_pwd," + // 3
				"dd.sn" + // 4
				" FROM " + "device dd");

		Query query = this.getQuery(sql.toString());

		Page resultPage = null;

		Device device = null;

		ArrayList<Device> devices = new ArrayList<Device>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				device.setSn((String) obj[4]);
				devices.add(device);
			}

		}

		resultPage = new Page<Device>(devices, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Device searchDevice(Integer id) {
		StringBuffer sql = new StringBuffer("from Device where id = " + id);
		ArrayList result = (ArrayList) this.executeHQL(sql.toString());
		Device device = new Device();
		if (result.size() > 0) {
			device = (Device) result.get(0);
		}
		return device;
	}

	@Override
	public Device searchDeviceByDeviceNo(String deviceNo) {
		StringBuffer sql = new StringBuffer("from Device where device_no ='"  + deviceNo + "'");
		List <Device> data = this.executeHQL(sql.toString());
		Device device = new Device();
		if (data.size() > 0) {
			device = (Device)data.get(0);
		}else{
			
		}
		return device;
	}
	
	@Override
	public Device searchDeviceByEpalId(String epalId) {
		StringBuffer sql = new StringBuffer("select id,device_no,epal_id,sn,epal_pwd,nickname,ifnull(device_type,'eggy'),device_used_type from device where epal_id =" + "\"" + epalId + "\"");
		List <Object[]> data = this.executeSQL(sql.toString());
		Device device = new Device();
		if (data.size() > 0) {

			device.setId(Integer.parseInt(data.get(0)[0].toString()));
			device.setEpalId(data.get(0)[2].toString());
			device.setDeviceNo(data.get(0)[1].toString());
			device.setEpalPwd(data.get(0)[4].toString());
			device.setSn(data.get(0)[3].toString());
			device.setNickName(data.get(0)[5].toString());
			device.setDeviceType(data.get(0)[6].toString());
			device.setDeviceUsedType(data.get(0)[7].toString());
		}
		return device;
	}

	@Override
	public Page searchDeviceStudys(HashMap map) {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT" + "	dd.id as device_id,"
				+ // 0
				"	dd.device_no,"
				+ // 1
				"	dd.epal_id,"
				+ // 2
				"	dd.epal_pwd,"
				+ // 3
				"	ds.id as device_study_id,"
				+ // 4
				"	ds.mission_id,"
				+ // 5
				"	ds.mission_name,"
				+ // 6
				"	ds.content,"
				+ // 7
				"	ds.isdone,"
				+ // 8
				"	ds.url"
				+ // 9
				"  FROM" + "  device dd"
				+ "  INNER JOIN device_study ds ON dd.device_no = ds.device_no");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and dd.device_no = ").append(" :deviceNo ");
		}
		sql.append(" ORDER BY " + "device_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<DeviceStudy> deviceStudys = new ArrayList<DeviceStudy>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DeviceStudy deviceStudy = new DeviceStudy();
				deviceStudy.setId((Integer) obj[4]);
				deviceStudy.setDeviceNo((String) obj[1]);
				deviceStudy.setEpalId((String) obj[2]);
				deviceStudy.setMissionId((String) obj[5]);
				deviceStudy.setMissionName((String) obj[6]);
				deviceStudy.setContent((String) obj[7]);
				deviceStudy.setIsdone((Integer) obj[8]);
				deviceStudy.setUrl((String) obj[9]);
				deviceStudy.setDevice(device);
				deviceStudys.add(deviceStudy);
			}

		}

		Page resultPage = new Page<DeviceStudy>(deviceStudys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDeviceStudy(DeviceStudy deviceStudy) {
		this.saveOrUpdate(deviceStudy);
	}

	@Override
	public void deleteDeviceStudy(DeviceStudy deviceStudy) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceStudy where id = " + deviceStudy.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceCollections(HashMap map) {

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"
				+ "	dd.id as device_id,"
				+ // 0
				"	dd.device_no,"
				+ // 1
				"	dd.epal_id,"
				+ // 2
				"	dd.epal_pwd,"
				+ // 3
				"	dc.id as device_collection_id,"
				+ // 4
				"	dc.audio_id,"
				+ // 5
				"	dc.audio_name,"
				+ // 6
				"	dc.create_time,"
				+ // 7
				"	dc.res_from,"
				+ // 8
				"	dc.url"
				+ // 9
				"  FROM"
				+ "  device dd"
				+ "  INNER JOIN device_collection dc ON dd.device_no = dc.device_no");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and dd.device_no = ").append(" :deviceNo ");
		}
		sql.append(" ORDER BY " + "device_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<DeviceCollection> deviceCollections = new ArrayList<DeviceCollection>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DeviceCollection deviceCollection = new DeviceCollection();
				deviceCollection.setId((Integer) obj[4]);
				deviceCollection.setDeviceNo((String) obj[1]);
				deviceCollection.setEpalId((String) obj[2]);
				deviceCollection.setAudioId((String) obj[5]);
				deviceCollection.setAudioName((String) obj[6]);
				deviceCollection.setCreateTime((Timestamp) obj[7]);
				deviceCollection.setResFrom((String) obj[8]);
				deviceCollection.setUrl((String) obj[9]);
				deviceCollection.setDevice(device);
				deviceCollections.add(deviceCollection);
			}

		}

		Page resultPage = new Page<DeviceCollection>(deviceCollections,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDeviceCollection(DeviceCollection deviceCollection) {
		this.saveOrUpdate(deviceCollection);
	}

	@Override
	public void deleteDeviceCollection(DeviceCollection deviceCollection) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceCollection where id = "
						+ deviceCollection.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceStorys(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT" + "	dd.id as device_id,"
				+ // 0
				"	dd.device_no,"
				+ // 1
				"	dd.epal_id,"
				+ // 2
				"	dd.epal_pwd,"
				+ // 3
				"	dc.id as device_story_id,"
				+ // 4
				"	dc.story_id,"
				+ // 5
				"	dc.story_name,"
				+ // 6
				"	dc.upload_time,"
				+ // 7
				"	dc.public_time,"
				+ // 8
				"	dc.url,"
				+ // 9
				"	dc.play_times,"
				+ // 10
				"	dc.positive_rate,"
				+ // 11
				"	dc.comment,"
				+ // 12
				"	dc.author_id "
				+ // 13
				"  FROM" + "  device dd"
				+ "  INNER JOIN device_story dc ON dd.device_no = dc.device_no");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and dd.device_no = ").append(" :deviceNo ");
		}
		sql.append(" ORDER BY " + "device_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<DeviceStory> deviceStorys = new ArrayList<DeviceStory>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DeviceStory deviceStory = new DeviceStory();
				deviceStory.setId((Integer) obj[4]);
				deviceStory.setDeviceNo((String) obj[1]);
				deviceStory.setEpalId((String) obj[2]);
				deviceStory.setStoryId((String) obj[5]);
				deviceStory.setStoryName((String) obj[6]);
				deviceStory.setUploadTime((Timestamp) obj[7]);
				deviceStory.setPublicTime((Timestamp) obj[8]);
				deviceStory.setUrl((String) obj[9]);
				deviceStory.setPlayTimes((Integer) obj[10]);
				deviceStory.setPositiveRate((Float) obj[11]);
				deviceStory.setComment((String) obj[12]);
				deviceStory.setAuthorId((String) obj[13]);
				deviceStory.setDevice(device);
				deviceStorys.add(deviceStory);
			}

		}

		Page resultPage = new Page<DeviceStory>(deviceStorys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDeviceStory(DeviceStory deviceStory) {
		this.saveOrUpdate(deviceStory);
	}

	@Override
	public void deleteDeviceStory(DeviceStory deviceStory) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceStory where id = " + deviceStory.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public DeviceTest searchDeviceTestByDeviceNo(String deviceNo) {
		StringBuffer sql = new StringBuffer("from DeviceTest where deviceNo = "
				+ deviceNo);
		ArrayList result = (ArrayList) this.executeHQL(sql.toString());
		DeviceTest deviceTest = new DeviceTest();
		if (result.size() > 0) {
			deviceTest = (DeviceTest) result.get(0);
		}
		return deviceTest;
	}

	@Override
	public Page searchDevicePlayRecords(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"
				+ " dd.id as device_id,"
				+ // 0
				" dd.device_no,"
				+ // 1
				" dd.epal_id,"
				+ // 2
				" dd.epal_pwd,"
				+ // 3
				" dc.id as device_playrecord_id,"
				+ // 4
				" dc.audio_id,"
				+ // 5
				" dc.audio_name,"
				+ // 6
				" dc.start_time,"
				+ // 7
				" dc.from,"
				+ // 8
				" dc.lastid,"
				+ // 9
				" dc.nextid"
				+ // 10
				" FROM"
				+ " device dd"
				+ " INNER JOIN device_playrecord dc ON dd.device_no = dc.device_no");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and dd.device_no = ").append(" :deviceNo ");
		}
		sql.append(" ORDER BY " + "device_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<DevicePlayRecord> devicePlayRecords = new ArrayList<DevicePlayRecord>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DevicePlayRecord devicePlayRecord = new DevicePlayRecord();
				devicePlayRecord.setId((Integer) obj[4]);
				devicePlayRecord.setDeviceNo((String) obj[1]);
				devicePlayRecord.setEpalId((String) obj[2]);
				devicePlayRecord.setAudioId((String) obj[5]);
				devicePlayRecord.setAudioName((String) obj[6]);
				devicePlayRecord.setStartTime((Timestamp) obj[7]);
				devicePlayRecord.setFrom((String) obj[8]);
				devicePlayRecord.setLastid((String) obj[9]);
				devicePlayRecord.setNextid((String) obj[10]);
				devicePlayRecord.setDevice(device);
				devicePlayRecords.add(devicePlayRecord);
			}

		}

		Page resultPage = new Page<DevicePlayRecord>(devicePlayRecords,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDevicePlayRecord(DevicePlayRecord devicePlayRecord) {
		this.saveOrUpdate(devicePlayRecord);
	}

	@Override
	public void deleteDevicePlayRecord(DevicePlayRecord devicePlayRecord) {
		StringBuffer sql = new StringBuffer(
				"delete from DevicePlayRecord where id = "
						+ devicePlayRecord.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDevicePropertys(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"
				+ " dd.id as device_id,"
				+ // 0
				" dd.device_no,"
				+ // 1
				" dd.epal_id,"
				+ // 2
				" dd.epal_pwd,"
				+ // 3
				" dc.id as device_property_id,"
				+ // 4
				" dc.property_key,"
				+ // 5
				" dc.property_value"
				+ // 6
				" FROM"
				+ " device dd"
				+ " INNER JOIN device_property dc ON dd.epal_id = dc.epal_id");
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" and dd.epal_id = ").append(" :epalId ");
		}
		sql.append(" ORDER BY " + "epal_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			query.setString("epalId", map.get("epalId").toString());
		}

		ArrayList<DeviceProperty> devicePropertys = new ArrayList<DeviceProperty>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DeviceProperty deviceProperty = new DeviceProperty();
				deviceProperty.setId((Integer) obj[4]);
				deviceProperty.setDeviceNo((String) obj[1]);
				deviceProperty.setEpalId((String) obj[2]);
				deviceProperty.setPropertyKey((String) obj[5]);
				deviceProperty.setPropertyValue((String) obj[6]);
				deviceProperty.setDevice(device);
				devicePropertys.add(deviceProperty);
			}

		}

		Page resultPage = new Page<DeviceProperty>(devicePropertys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDeviceProperty(DeviceProperty deviceProperty) {
		this.saveOrUpdate(deviceProperty);
	}

	@Override
	public void deleteDeviceProperty(DeviceProperty deviceProperty) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceProperty where id = "
						+ deviceProperty.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceRelations(HashMap map) {
		
		
		if (null == map.get("epalId")
				|| "".equals(map.get("epalId").toString())) {
			return null;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"
				+ " dd.id as device_id,"
				+ // 0
				" dd.device_no,"
				+ // 1
				" dd.epal_id,"
				+ // 2
				" dd.epal_pwd,"
				+ // 3
				" dc.id as device_relation_id,"
				+ // 4
				" dc.friend_id,"
				+ // 5
				" dc.friend_name,"
				+ // 6
				" dc.role,"
				+ // 7
				" dc.isbind "
				+ // 8
				" FROM"
				+ " device dd"
				+ " INNER JOIN device_relation dc ON dd.epal_id = dc.epal_id and dc.epal_id = :epalId");
		
		sql.append(" ORDER BY " + "epal_id DESC");

		Query query = this.getQuery(sql.toString());
		query.setString("epalId", map.get("epalId").toString());

		ArrayList<DeviceRelation> deviceRelations = new ArrayList<DeviceRelation>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				DeviceRelation deviceRelation = new DeviceRelation();
				deviceRelation.setId((Integer) obj[4]);
				deviceRelation.setDeviceNo((String) obj[1]);
				deviceRelation.setEpalId((String) obj[2]);
				deviceRelation.setFriendId((String) obj[5]);
				deviceRelation.setFriendName((String) obj[6]);
				deviceRelation.setRole((String) obj[7]);
				deviceRelation.setIsbind((Integer) obj[8]);
				deviceRelations.add(deviceRelation);
			}
		}

		Page resultPage = new Page<DeviceRelation>(deviceRelations,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}
	@Override
	public Page searchDevicebBindList(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select dr.id id,dr.device_no,dr.epal_id," +
				"dr.friend_id,dr.friend_name,dr.role,dr.isbind,ifnull(d.nickname,\"\"),ifnull(d.device_type,\"eggy\"),ifnull(d.device_used_type,\"qy_home\"),d.id deviceId" +
				" from device_relation as dr,device as d where isbind=1 and dr.epal_id=d.epal_id");

		if (null != map.get("friendId")
				&& !"".equals(map.get("friendId").toString())) {
			sql.append(" and friend_id = ").append(" :friendId ");
		}

		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" and d.epal_id = ").append(" :epalId ");
		}
		
		Query query = this.getQuery(sql.toString());
		if (null != map.get("friendId")
				&& !"".equals(map.get("friendId").toString())) {
			query.setString("friendId", map.get("friendId").toString());
		}
		
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			query.setString("epalId", map.get("epalId").toString());
		}
		
		ArrayList<DeviceRelation> deviceRelations = new ArrayList<DeviceRelation>();

		Page dataPage = this.pageQueryBySQL(query, 1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				DeviceRelation deviceRelation = new DeviceRelation();
				deviceRelation.setId((Integer) obj[0]);
				deviceRelation.setDeviceNo((String) obj[1]);
				deviceRelation.setEpalId((String) obj[2]);
				deviceRelation.setFriendId((String) obj[3]);
				deviceRelation.setFriendName((String) obj[4]);
				deviceRelation.setRole((String) obj[5]);
				deviceRelation.setIsbind((Integer) obj[6]);
				deviceRelation.setDeviceType((String) obj[8]);
				deviceRelation.setDeviceUsedType((String) obj[9]);
				deviceRelation.setDeviceId((Integer) obj[10]);
				if(null != obj[7].toString()&&!"".equals(obj[7].toString())){
					deviceRelation.setNickName((String) obj[7]);
				}else{
					deviceRelation.setNickName((String) obj[2]);
				}	
				deviceRelations.add(deviceRelation);
			}
		}

		Page resultPage = new Page<DeviceRelation>(deviceRelations,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}
	
	

	@Override
	public void deleteDeviceRelation(DeviceRelation deviceRelation) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceRelation where id = "
						+ deviceRelation.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveDeviceRelation(DeviceRelation deviceRelation) {
		this.saveOrUpdate(deviceRelation);
		
		try {
			updateStudentStatus(1, deviceRelation.getEpalId());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public Page searchDeviceSchedules(HashMap map) {
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT"
				+ " dd.id as device_id,"
				+ // 0
				" dd.device_no,"
				+ // 1
				" dd.epal_id,"
				+ // 2
				" dd.epal_pwd,"
				+ // 3
				" dc.id as device_schedule_id,"
				+ // 4
				" dc.event,"
				+ // 5
				" dc.note,"
				+ // 6
				" dc.content,"
				+ // 7
				" dc.period,"
				+ // 8
				" dc.type,"
				+ // 9
				" dc.do_time,"
				+ // 10
				" dc.state,"
				+ // 11
				" dc.is_def,"
				+ // 12
				" dc.catalog_file,"
				+ // 13
				" dc.event_cn,"
				+ // 14
				" dc.title,"
				+ // 15
				" dc.description,"
				+ // 16
				" dc.sid,"
				+ // 17
				" ifnull(dc.group_id,\"1\")"
				+ // 18
				" FROM"
				+ " device dd"
				+ " INNER JOIN device_schedule dc ON dd.epal_id = dc.epal_id");
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" and dd.epal_id = ").append("\"");
			sql.append(map.get("epalId").toString());
			sql.append("\"");
		}
		sql.append(" ORDER BY " + "dc.do_time DESC");
//		//System.out.println(sql);
		Query query = this.getQuery(sql.toString());

//		if (null != map.get("epalId")
//				&& !"".equals(map.get("epalId").toString())) {
//			query.setString("epalId", map.get("epalId").toString());
//		}

		ArrayList<DeviceSchedule> deviceSchedules = new ArrayList<DeviceSchedule>();
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				Device device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				DeviceSchedule deviceSchedule = new DeviceSchedule();
				deviceSchedule.setId((Integer) obj[4]);
				deviceSchedule.setDeviceNo((String) obj[1]);
				deviceSchedule.setEpalId((String) obj[2]);
				deviceSchedule.setEvent((String) obj[5]);
				deviceSchedule.setNote((String) obj[6]);
				deviceSchedule.setContent((String) obj[7]);
				deviceSchedule.setPeriod((String) obj[8]);
				deviceSchedule.setType((String) obj[9]);
				deviceSchedule.setDoTime((String) obj[10]);
				deviceSchedule.setState((Integer) obj[11]);
				deviceSchedule.setIsDef((Integer) obj[12]);
				deviceSchedule.setCatalogFile((String) obj[13]);
				deviceSchedule.setEventCN((String) obj[14]);
				deviceSchedule.setTitle((String) obj[15]);
				deviceSchedule.setDescription((String) obj[16]);
				deviceSchedule.setSid((String) obj[17]);
				deviceSchedule.setGroupId(Integer.parseInt((String) obj[18]));
				deviceSchedule.setDevice(device);
				deviceSchedules.add(deviceSchedule);
			}
		}
		Page resultPage = new Page<DeviceSchedule>(deviceSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void saveDeviceSchedule(DeviceSchedule deviceSchedule) {
		if(null != deviceSchedule.getId() && !"".equals(deviceSchedule.getId().toString()) ){
			StringBuffer sql = new StringBuffer();
			sql.append("update device_schedule set epal_id=:epalId ");
			if(null != deviceSchedule.getDoTime() && !"".equals(deviceSchedule.getDoTime()) ){
				sql.append(",do_time=:doTime ");
				
			}
			if(null != deviceSchedule.getEventCN() && !"".equals(deviceSchedule.getEventCN()) ){
				sql.append(",event_cn=:eventCN");
				
			}
			
			if(null != deviceSchedule.getEvent() && !"".equals(deviceSchedule.getEvent()) ){
				sql.append(",event=:event");
				
			}
			if(null != deviceSchedule.getNote() && !"".equals(deviceSchedule.getNote()) ){
				sql.append(",note=:note");
				
			}
			if(null != deviceSchedule.getContent() && !"".equals(deviceSchedule.getContent()) ){
				sql.append(",content=:content");
				
			}
			if(null != deviceSchedule.getPeriod() && !"".equals(deviceSchedule.getPeriod()) ){
				sql.append(",period=:period");
				
			}
			if(null != deviceSchedule.getType() && !"".equals(deviceSchedule.getType()) ){
				sql.append(",type=:type");
				
			}
			if(null != deviceSchedule.getState() && !"".equals(deviceSchedule.getState()) ){
				sql.append(",state=:state ");
				
			}
			if(null != deviceSchedule.getIsDef() && !"".equals(deviceSchedule.getIsDef()) ){
				sql.append(",is_def=:isDef");
				
			}
			if(null != deviceSchedule.getCatalogFile() && !"".equals(deviceSchedule.getCatalogFile()) ){
				sql.append(",catalog_file=:catalogFile");
				
			}
			if(null != deviceSchedule.getTitle() && !"".equals(deviceSchedule.getTitle()) ){
				sql.append(",title=:title");
				
			}
			if(null != deviceSchedule.getDescription() && !"".equals(deviceSchedule.getDescription()) ){
				sql.append(",description=:description");
				
			}
			if(null != deviceSchedule.getSid() && !"".equals(deviceSchedule.getSid()) ){
				sql.append(",sid=:sid");
				
			}			
			sql.append(" where id=:id");
			
			Query query = this.getQuery(sql.toString());
//			//System.out.println(deviceSchedule.getEpalId());
			
			query.setString("epalId", deviceSchedule.getEpalId().toString());
			
			if(null != deviceSchedule.getDoTime() && !"".equals(deviceSchedule.getDoTime()) ){
				query.setString("doTime", deviceSchedule.getDoTime().toString());
				
			}	
			if(null != deviceSchedule.getEventCN() && !"".equals(deviceSchedule.getEventCN()) ){
				query.setString("eventCN", deviceSchedule.getEventCN().toString());
				
			}	
			if(null != deviceSchedule.getEvent() && !"".equals(deviceSchedule.getEvent()) ){
				query.setString("event", deviceSchedule.getEvent().toString());
				
			}	
			if(null != deviceSchedule.getNote() && !"".equals(deviceSchedule.getNote()) ){
				query.setString("note", deviceSchedule.getNote().toString());
				
			}	
			if(null != deviceSchedule.getContent() && !"".equals(deviceSchedule.getContent()) ){
				query.setString("content", deviceSchedule.getContent().toString());
				
			}	
			if(null != deviceSchedule.getPeriod() && !"".equals(deviceSchedule.getPeriod()) ){
				query.setString("period", deviceSchedule.getPeriod().toString());
				
			}	
			if(null != deviceSchedule.getType() && !"".equals(deviceSchedule.getType()) ){
				query.setString("type", deviceSchedule.getType().toString());
				
			}	
			if(null != deviceSchedule.getState() && !"".equals(deviceSchedule.getState()) ){
				query.setInteger("state", deviceSchedule.getState());
				
			}	
			if(null != deviceSchedule.getIsDef() && !"".equals(deviceSchedule.getIsDef()) ){
				query.setInteger("isDef", deviceSchedule.getIsDef());
				
			}	
			if(null != deviceSchedule.getCatalogFile() && !"".equals(deviceSchedule.getCatalogFile()) ){
				query.setString("catalogFile", deviceSchedule.getCatalogFile().toString());
				
			}
			
			
			if(null != deviceSchedule.getTitle() && !"".equals(deviceSchedule.getTitle()) ){
				query.setString("title", deviceSchedule.getTitle().toString());
				
			}	
			if(null != deviceSchedule.getDescription() && !"".equals(deviceSchedule.getDescription()) ){
				query.setString("description", deviceSchedule.getDescription().toString());
				
			}	
			
			if(null != deviceSchedule.getSid() && !"".equals(deviceSchedule.getSid()) ){
				query.setString("sid", deviceSchedule.getSid().toString());
				
			}	
			
			
			query.setInteger("id", deviceSchedule.getId());
//			//System.out.println(query.toString());
			query.executeUpdate();
		}else{
			
			this.saveOrUpdate(deviceSchedule);
		}
	}

	@Override
	public void deleteDeviceSchedule(DeviceSchedule deviceSchedule) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceSchedule where id = "
						+ deviceSchedule.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchDeviceTests(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from ( ");
		sql.append(" SELECT " + "dd.id AS device_id,"
				+ // 0
				"dd.device_no,"
				+ // 1
				"dd.epal_id,"
				+ // 2
				"dd.epal_pwd,"
				+ // 3
				"dt.id AS device_test_id,"
				+ // 4
				"dt.`status`,"
				+ // 5
				"dt.led,"
				+ // 6
				"dt.usb_connect,"
				+ // 7
				"dt.burning_process,"
				+ // 8
				"dt.shake,"
				+ // 9
				"dt.left_mic,"
				+ // 10
				"dt.right_mic,"
				+ // 11
				"dt.camara,"
				+ // 12
				"dt.gesture_recognition,"
				+ // 13
				"dt.display,"
				+ // 14
				"dt.touch_key,"
				+ // 15
				"dt.rfid,"
				+ // 16
				"dt.rotation_function,"
				+ // 17
				"dt.wifi,"
				+ // 18
				"dt.back_result,"
				+ // 19
				"dt.regist_men,"
				+ // 20
				"dt.regist_time,"
				+ // 21
				"dt.review_men,"
				+ // 22
				"dt.review_time,"
				+ // 23
				"dt.power,"
				+ // 24
				"dt.test_process,"
				+ // 25
				"dt.left_horn,"
				+ // 26
				"dt.right_horn,"
				+ // 27
				"dt.left_ear,"
				+ // 28
				"dt.right_ear,"
				+ // 29
				"dt.ear_mic,"
				+ // 30
				"dt.top_touch,"
				+ // 31
				"dt.left_touch,"
				+ // 32
				"dt.right_touch,"
				+ // 33
				"dt.foot_charge,"
				+ // 34
				"dt.usb_charge"
				+ // 35
				" FROM " + "device dd"
				+ " INNER JOIN device_test dt ON dd.device_no = dt.device_no");

		sql.append(" ) as dt2 where 1=1  ");

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" and device_no  like ").append(" :deviceNo ");
		}
		if (null != map.get("status")
				&& !"".equals(map.get("status").toString())) {
			sql.append(" and status  = ").append(" :status ");
		}
		sql.append(" ORDER BY " + "device_test_id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", "%" + map.get("deviceNo").toString()
					+ "%");
		}
		if (null != map.get("status")
				&& !"".equals(map.get("status").toString())) {
			query.setString("status", map.get("status").toString());
		}

		Page resultPage = null;

		Device device = null;

		DeviceTest deviceTest = null;

		ArrayList<DeviceTest> deviceTests = new ArrayList<DeviceTest>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				device = new Device();
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setEpalPwd((String) obj[3]);
				deviceTest = new DeviceTest();
				deviceTest.setId((Integer) obj[4]);
				deviceTest.setEpalId((String) obj[2]);
				deviceTest.setDeviceNo((String) obj[1]);
				deviceTest.setStatus((Integer) obj[5]);
				deviceTest.setLed((Integer) obj[6]);
				deviceTest.setUsbConnect((Integer) obj[7]);
				deviceTest.setBurningProcess((Integer) obj[8]);
				deviceTest.setShake((Integer) obj[9]);
				deviceTest.setLeftMic((Integer) obj[10]);
				deviceTest.setRightMic((Integer) obj[11]);
				deviceTest.setCamara((Integer) obj[12]);
				deviceTest.setGestureRecognition((Integer) obj[13]);
				deviceTest.setDisplay((Integer) obj[14]);
				deviceTest.setTouchKey((Integer) obj[15]);
				deviceTest.setRfid((Integer) obj[16]);
				deviceTest.setRotationFunction((Integer) obj[17]);
				deviceTest.setWifi((Integer) obj[18]);
				deviceTest.setBackResult((String) obj[19]);
				String registMen = (String) obj[20];
				String newStr = "";
				if (null != registMen && !"".equals(registMen)) {
					newStr = registMen.replaceAll("\r|\n|\t", " ");
				}
				deviceTest.setRegistMen(newStr);
				deviceTest.setRegistTime((Timestamp) obj[21]);
				deviceTest.setReviewMen((String) obj[22]);
				deviceTest.setReviewTime((Timestamp) obj[23]);
				deviceTest.setPower((Integer) obj[24]);
				deviceTest.setTestProcess((Integer) obj[25]);
				deviceTest.setLeftHorn((Integer) obj[26]);
				deviceTest.setRightHorn((Integer) obj[27]);
				deviceTest.setLeftEar((Integer) obj[28]);
				deviceTest.setRightEar((Integer) obj[29]);
				deviceTest.setEarMic((Integer) obj[30]);
				deviceTest.setTopTouch((Integer) obj[31]);
				deviceTest.setLeftTouch((Integer) obj[32]);
				deviceTest.setRightTouch((Integer) obj[33]);
				deviceTest.setFootCharge((Integer) obj[34]);
				deviceTest.setUsbCharge((Integer) obj[35]);
				deviceTest.setDevice(device);
				deviceTests.add(deviceTest);
			}

		}

		resultPage = new Page<DeviceTest>(deviceTests,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public void deleteDeviceTestByDeviceNo(String deviceNo) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceTest where deviceNo = " + deviceNo);
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveCourseSchedule(CourseSchedule courseSchedule) {
		this.saveOrUpdate(courseSchedule);
	}

	@Override
	public void saveDeviceFile(DeviceFile deviceFile) {
		this.saveOrUpdate(deviceFile);
	}

	@Override
	public Page searchDeviceFiles(HashMap map) {
		StringBuffer sql = new StringBuffer("select * from device_file where 1 = 1 ");
		if (null != map.get("filePath")
				&& !"".equals(map.get("filePath").toString())) {
			sql.append(" and filePath  = ").append(map.get("filePath"));
		}
		if (null != map.get("name") && !"".equals(map.get("name").toString())) {
			sql.append(" and epal_id  like '%").append(map.get("name"))
					.append("%'");
		}
		
		if (null != map.get("source") && !"".equals(map.get("source").toString())) {
			sql.append(" and source = \"").append(map.get("source")).append("\"");
		}
		if (null != map.get("epalId") && !"".equals(map.get("epalId").toString())) {
			sql.append(" and epal_id  = \"").append(map.get("epalId")).append("\"");
			sql.append(" order by id asc");
			List<DeviceFile> data = this.executeSQL(sql.toString());
			Page resultPage = new Page<DeviceFile>(data,
					data.size(), 1,
					0, 1000);
			ArrayList<Object[]> dataList = (ArrayList<Object[]>) resultPage
					.getItems();
			ArrayList<DeviceFile> DeviceFiles = new ArrayList<DeviceFile>();
			if (dataList.size() > 0) {
				for (int i = 0; i < dataList.size(); i++) {
					Object[] obj = dataList.get(i);
					DeviceFile devicefile = new DeviceFile();
					devicefile.setId((Integer) obj[0]);
					devicefile.setFileUrl((String) obj[2]);
					devicefile.setEpalId((String) obj[1]);
					devicefile.setCreateTime((Timestamp) obj[3]);
					devicefile.setPath((String) obj[5]);
					devicefile.setFilePath((String) obj[4]);
					devicefile.setFileName((String) obj[6]);
					devicefile.setDuration((String) obj[7]);
					devicefile.setSource((String) obj[8]);
					DeviceFiles.add(devicefile);
				}
			}

			Page result = new Page<DeviceFile>(DeviceFiles,
					DeviceFiles.size(), 1,0, 10000);
			return result;

		}
		sql.append(" order by id desc ");
		Query query = this.getQuery(sql.toString());
		Page result = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));
		return result;
	}

	@Override
	public void deleteDeviceFile(DeviceFile deviceFile) {
		StringBuffer sql = new StringBuffer(
				"delete from DeviceFile where id = " + deviceFile.getId());
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchCoursesByProductId(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT"
				+ "	mallproduct.id AS product_id,"
				+ // 0
				"	mallproduct.`name` AS parent_name,"
				+ // 1
				"	course.id AS course_id,"
				+ // 2
				"	course.url AS chapter_res,"
				+ // 3
				"	course.`name` AS chapter_name ,"
				+ // 4
				"	course.`total_class` AS total_clas,"
				+ // 5
				"   product_category.cat_id AS courseCatId,"
				+ // 6
				"   product_category.cat_name AS courseCatName "
				+ // 7
				"	FROM "
				+ "	course ,"
				+ "	mallproduct,"
				+ "	product_category"
				+ "	WHERE"
				+ "	course.productid = mallproduct.id AND mallproduct.cat_id = product_category.cat_id ");
		if (null != map.get("productId")
				&& !"".equals(map.get("productId").toString())) {
			sql.append(" and course.productid = ").append(" :productId ");
		}
		sql.append(" ORDER BY " + "course.id ASC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("productId")
				&& !"".equals(map.get("productId").toString())) {
			query.setString("productId", map.get("productId").toString());
		}

		ArrayList<CourseDto> courses = new ArrayList<CourseDto>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				CourseDto courseDto = new CourseDto();
				Object[] obj = dataList.get(i);
				courseDto.setProductId((Integer) obj[0]);
				courseDto.setParentName((String) obj[1]);
				courseDto.setCourseId((Integer) obj[2]);
				courseDto.setChapterRes((String) obj[3]);
				courseDto.setChapterName((String) obj[4]);
				courseDto.setTotalClass((Integer) obj[5]);
				courseDto.setCourseCatId((Integer) obj[6]);
				courseDto.setCourseCatName((String) obj[7]);
				courses.add(courseDto);
			}

		}
		Page resultPage = new Page<CourseDto>(courses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page searchCourseSchedulesByDeviceNo(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT"
				+ "	temp.id,"
				+ // 0
				"	temp.device_no,"
				+ // 1
				"	temp.courseid,"
				+ // 2
				"	temp.productid,"
				+ // 3
				"	temp.schedule,"
				+ // 4
				"	temp.cur_class,"
				+ // 5
				"	temp.cus_file,"
				+ // 6
				"	temp.create_time"
				+ // 7
				"	FROM" + "		(" + "			SELECT" + "				*" + "			FROM"
				+ "				course_schedule" + "			ORDER BY"
				+ "				course_schedule.create_time DESC"
				+ "		) AS temp WHERE 1=1 ");
		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			sql.append(" AND temp.device_no = ").append(" :deviceNo ");
		}
		sql.append("	GROUP BY" + "	temp.courseid" + "	ORDER BY"
				+ "	temp.create_time DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("deviceNo")
				&& !"".equals(map.get("deviceNo").toString())) {
			query.setString("deviceNo", map.get("deviceNo").toString());
		}

		ArrayList<CourseSchedule> courseSchedules = new ArrayList<CourseSchedule>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				CourseSchedule courseSchedule = new CourseSchedule();
				Object[] obj = dataList.get(i);
				courseSchedule.setId((Integer) obj[0]);
				courseSchedule.setDeviceNo((String) obj[1]);
				courseSchedule.setCourseid((Integer) obj[2]);
				courseSchedule.setProductid((Integer) obj[3]);
				courseSchedule.setSchedule((String) obj[4]);
				courseSchedule.setCurClass((Integer) obj[5]);
				courseSchedule.setCusFile((String) obj[6]);
				courseSchedule.setCreateTime((Timestamp) obj[7]);
				courseSchedules.add(courseSchedule);
			}

		}
		Page resultPage = new Page<CourseSchedule>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	

	@Override
	public Page searchCourseSchedulesByEpalId2(HashMap map) {
		String epalId=" ";
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			epalId=map.get("epalId").toString();
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT"+
				"	temp_4.catId,"+
				"	temp_4.catName,"+
				"	GROUP_CONCAT("+
				"		temp_4.parentId,"+
				"		\'##\',"+
				"		temp_4.parentName,"+
				"		\'##\',"+
				"		temp_4.courseLogo,"+
				"		\'##\',"+
				"		temp_4.childCourse,"+
				"		\'\'"+
				"	ORDER BY"+
				"		parentId ASC SEPARATOR \'@@\'"+
				"	) AS mainCourse"+
				" FROM"+
				"	("+
				"		SELECT"+
				"			temp_3.catId,"+
				"			temp_3.catName,"+
				"			temp_3.parentId,"+
				"			temp_3.parentName,"+
				"			temp_3.courseLogo,"+
				"			GROUP_CONCAT("+
				"				temp_3.courseId,"+
				"				\'#\',"+
				"				temp_3.totalClass,"+
				"				\'#\',"+
				"				temp_3.chapterName,"+
				"				\'#\',"+
				"				temp_3.chapterRes,"+
				"				\'#\',"+
				"				temp_3.curClass,"+
				"				\'#\',"+
				"				temp_3.cusFile,"+
				"				\'\'"+
				"			ORDER BY"+
				"				courseId ASC SEPARATOR \'@\'"+
				"			) AS childCourse"+
				"		FROM"+
				"			("+
				"				SELECT"+
				"					temp_1.catId,"+
				"					temp_1.catName,"+
				"					temp_1.product_id AS parentId,"+
				"					temp_1.parent_name AS parentName,"+
				"					temp_1.courseLogo,"+
				"					temp_1.course_id AS courseId,"+
				"					temp_1.totalClass,"+
				"					temp_1.chapter_name AS chapterName,"+
				"					IF ("+
				"					temp_1.chapter_res = \'\' || temp_1.chapter_res = NULL,"+
				"					\'null\',"+
				"					temp_1.chapter_res"+
				"					) AS chapterRes,"+
				"					IFNULL(temp_2.cur_class, 0) AS curClass,"+
				"					IFNULL(temp_2.cus_file, \'null\') AS cusFile"+
				"				FROM"+
				"					("+
				"						SELECT"+
				"							mallproduct.id AS product_id,"+
				"							mallproduct.`name` AS parent_name,"+
				"							course.id AS course_id,"+
				"							course.url AS chapter_res,"+
				"							course.`name` AS chapter_name,"+
				"							IFNULL(course.total_class, 8) AS totalClass,"+
				"							product_category.cat_name AS catName,"+
				"							product_category.cat_id AS catId,"+
				"							mallproduct.logo1 AS courseLogo"+
				"						FROM"+
				"							course,"+
				"							mallproduct,"+
				"							product_category"+
				"						WHERE"+
				"							course.productid = mallproduct.id"+
				"						AND mallproduct.cat_id = product_category.cat_id"+
				"						AND mallproduct.id IN ("+
				"							SELECT"+
				"								course_schedule.productid"+
				"							FROM"+
				"								course_schedule"+
				"							WHERE"+
				"								course_schedule.epal_id = :epalId"+
				"							GROUP BY"+
				"								course_schedule.productid"+
				"						)"+
				"					) AS temp_1"+
				"				LEFT JOIN ("+
				"					SELECT"+
				"						a.epal_id,"+
				"						a.productid,"+
				"						a.courseid,"+
				"						a.cur_class,"+
				"						a.cus_file"+
				"					FROM"+
				"						("+
				"							SELECT"+
				"								temp.id,"+
				"								temp.device_no,"+
				"								temp.epal_id,"+
				"								temp.courseid,"+
				"								temp.productid,"+
				"								temp. SCHEDULE,"+
				"								temp.cur_class,"+
				"							IF ("+
				"								temp.cus_file = \'\' || temp.cus_file = NULL,"+
				"								\'null\',"+
				"								temp.cus_file"+
				"							) AS cus_file,"+
				"							temp.create_time"+
				"						FROM"+
				"							("+
				"								SELECT"+
				"									*"+
				"								FROM"+
				"									course_schedule"+
				"								ORDER BY"+
				"									course_schedule.create_time DESC"+
				"							) AS temp"+
				"						WHERE"+
				"							1 = 1"+
				"						AND temp.epal_id = :epalId"+
				"						GROUP BY"+
				"							temp.courseid"+
				"						ORDER BY"+
				"							temp.create_time DESC"+
				"						) AS a"+
				"					GROUP BY"+
				"						a.courseid"+
				"				) AS temp_2 ON temp_1.product_id = temp_2.productid"+
				"				AND temp_1.course_id = temp_2.courseid"+
				"			) AS temp_3"+
				"		GROUP BY"+
				"			temp_3.parentId"+
				"	) AS temp_4"+
				" GROUP BY"+
				"	temp_4.catId");

		Query query = this.getQuery(sql.toString());

		query.setString("epalId", epalId);

		ArrayList<CourseScheduleDto> courseSchedules = new ArrayList<CourseScheduleDto>();
		//System.out.println(System.currentTimeMillis());
		
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));
		//System.out.println(System.currentTimeMillis());
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int z = 0; z < dataList.size(); z++) {
				CourseScheduleDto courseSchedule = new CourseScheduleDto();
				Object[] obj = dataList.get(z);
				courseSchedule.setCatId((Integer) obj[0]);
				courseSchedule.setCatName((String) obj[1]);
				String catCourseStr=(String) obj[2];
				String[] catCourseStrArrs=catCourseStr.split("@@");
				
				ArrayList catCourseList=new ArrayList();
				
				for (int i = 0; i < catCourseStrArrs.length; i++) {
					String [] catCourseStrArr=catCourseStrArrs[i].split("##");
					HashMap mainCourseMap=new HashMap();
					mainCourseMap.put("productId", catCourseStrArr[0]);
					mainCourseMap.put("productName", catCourseStrArr[1]);
					
					mainCourseMap.put("productLogo", "http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/"
							+catCourseStrArr[2]);
					
					//子课程列表
					ArrayList childCourseList=new ArrayList();
					
					String[] childCourseStrArrs=catCourseStrArr[3].split("@");
					
					for (int j = 0; j < childCourseStrArrs.length; j++) {
						
						HashMap childCourseMap=new HashMap();
						
						String[] chileCourseStrArr=childCourseStrArrs[j].split("#");
						
						childCourseMap.put("courseId", chileCourseStrArr[0]);
						
						childCourseMap.put("totalClass", chileCourseStrArr[1]);
						
						childCourseMap.put("chapterName", chileCourseStrArr[2]);
						
						childCourseMap.put("chapterRes", chileCourseStrArr[3]);
						
						childCourseMap.put("curClass", chileCourseStrArr[4]);
						
						childCourseMap.put("cusFile", chileCourseStrArr[5]);
						//添加子课程到列表
						childCourseList.add(childCourseMap);
					}
					//添加主课程的子课程列表
					mainCourseMap.put("childCourseList", childCourseList);
					//添加主课程到列表
					catCourseList.add(mainCourseMap);
					
				}
				//添加类别下的主课程列表
				courseSchedule.setCatCourse(catCourseList);
				courseSchedules.add(courseSchedule);
			}
		}
		Page resultPage = new Page<CourseScheduleDto>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}
	

	@Override
	public void saveCourseScheduleNow(CourseScheduleNow courseSchedule) {
		this.saveOrUpdate(courseSchedule);
	}
	

	@Override
	public List<CourseScheduleNow> getCurrentCourseSchedule(HashMap map) {
		List list=this.executeHQL("from CourseScheduleNow where epalId='"+map.get("epalId")+"'");
		return list;
	}
	
	

	@Override
	public Integer findCourseScheduleNow(String epalId, Integer projectId) {
		List list=this.executeHQL("from CourseScheduleNow where epalId='"+epalId+"' and projectId = "+projectId);
		Integer id=null;
		if(list.size()>0){
			CourseScheduleNow courseScheduleNow= (CourseScheduleNow)list.get(0) ;
			id=courseScheduleNow.getId();
		}
		return id;
	}

	@Override
	public Page searchCourseSchedulesByEpalId(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT"
				+ "	a.epal_id,"
				+ // 0
				"	a.productid,"
				+ // 1
				"	GROUP_CONCAT(a.courseid) courseidlist,"
				+ // 2
				"	GROUP_CONCAT(a.cur_class) currentclasslist,"
				+ // 3
				"	GROUP_CONCAT(a.cus_file) cusfilelist"
				+ // 4
				" FROM" + "	(" + "		SELECT" + "			temp.id,"
				+ "			temp.device_no," + "			temp.epal_id,"
				+ "			temp.courseid," + "			temp.productid,"
				+ "			temp. SCHEDULE," + "			temp.cur_class,"
				+ "			temp.cus_file," + "			temp.create_time" + "		FROM"
				+ "			(" + "				SELECT" + "					*" + "				FROM"
				+ "					course_schedule" + "				ORDER BY"
				+ "					course_schedule.create_time DESC" + "			) AS temp"
				+ "		WHERE" + "			1 = 1");
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" AND temp.epal_id = ").append(" :epalId ");
		}
		sql.append("		GROUP BY" + "			temp.courseid" + "		ORDER BY"
				+ "			temp.create_time DESC" + "	) AS a" + " GROUP BY"
				+ "	a.productid");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			query.setString("epalId", map.get("epalId").toString());
		}

		ArrayList<CourseScheduleInfoDto> courseSchedules = new ArrayList<CourseScheduleInfoDto>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				CourseScheduleInfoDto courseSchedule = new CourseScheduleInfoDto();
				Object[] obj = dataList.get(i);
				courseSchedule.setEpalId((String) obj[0]);
				courseSchedule.setProductId((Integer) obj[1]);
				courseSchedule.setCourseIdList((String) obj[2]);
				courseSchedule.setCurrentClassList((String) obj[3]);
				courseSchedule.setCusFileList((String) obj[4]);
				courseSchedules.add(courseSchedule);
			}

		}
		Page resultPage = new Page<CourseScheduleInfoDto>(courseSchedules,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page searchCourseScheduleByProductId(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT"
				+ "	mallproduct.id AS productId,"
				+ // 0
				"	mallproduct.`name` AS parentName,"
				+ // 1
				"	course.id AS courseId,"
				+ // 2
				"	course.url AS chapterRes,"
				+ // 3
				"	course.`name` AS chapterName ,"
				+ // 4
				"	course.`total_class` AS totalClass,"
				+ // 5
				"   product_category.cat_id AS courseCatId,"
				+ // 6
				"   product_category.cat_name AS courseCatName, "
				+ // 7
				"   mallproduct.logo1 AS courseLogo "
				+ // 8
				"	FROM "
				+ "	course ,"
				+ "	mallproduct,"
				+ "	product_category"
				+ "	WHERE"
				+ "	course.productid = mallproduct.id AND mallproduct.cat_id = product_category.cat_id ");
		if (null != map.get("productId")
				&& !"".equals(map.get("productId").toString())) {
			sql.append(" and course.productid = ").append(" :productId ");
		}
		sql.append(" ORDER BY " + "course.id ASC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("productId")
				&& !"".equals(map.get("productId").toString())) {
			query.setString("productId", map.get("productId").toString());
		}

		ArrayList<ChildrenCourseDto> courses = new ArrayList<ChildrenCourseDto>();

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				ChildrenCourseDto courseDto = new ChildrenCourseDto();
				Object[] obj = dataList.get(i);
				courseDto.setProductId((Integer) obj[0]);
				courseDto.setParentName((String) obj[1]);
				courseDto.setCourseId((Integer) obj[2]);
				courseDto.setChapterRes((String) obj[3]);
				courseDto.setChapterName((String) obj[4]);
				courseDto.setTotalClass((Integer) obj[5]);
				courseDto.setCourseCatId((Integer) obj[6]);
				courseDto.setCourseCatName((String) obj[7]);
				courseDto
						.setCourseLogo("http://wechat.fandoutech.com.cn/wechat/wechatImages/mall/"
								+ (String) obj[8]);
				courses.add(courseDto);
			}
		}
		Page resultPage = new Page<ChildrenCourseDto>(courses,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public DeviceRelation getDeviceRelation(DeviceRelation deviceRelation) {
		int id = deviceRelation.getId();
		DeviceRelation source = (DeviceRelation) this.get(DeviceRelation.class, id);
		return source;
	}

	@Override
	public MemberChildren getChildrenInfoFromDevice(String epalId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.id,a.age,a.sex,a.interest from " +
				"memberchildren as a," +
				"device_relation as b," +
				"member as c " +
				"where a.memberid=c.id " +
				"and b.friend_id=c.mobile and b.isbind=1 " +
				"and b.epal_id=:epalId");
		Query query = this.getQuery(sql.toString());

		if (null != epalId
				&& !"".equals(epalId)) {
			query.setString("epalId", epalId);
		}
		
		Page dataPage = this.pageQueryBySQL(query, 1, 1);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		MemberChildren memberChildren = new MemberChildren();
		if(dataList.size() > 0){
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);

				memberChildren.setId((Integer) obj[0]);
				memberChildren.setAge((String) obj[1]);
				memberChildren.setSex((Integer) obj[2]);
				memberChildren.setInterest((String) obj[3]);
			}
		}
		return memberChildren;
	}

	@Override
	public void updateDeviceBindRelation(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("update device_relation set isbind=:isBind where epal_id=:epalId and friend_id=:friendId");
		Query query = this.getQuery(sql.toString());
		if (null != map.get("epalId").toString()
				&& !"".equals(map.get("epalId").toString())) {
//			//System.out.println( map.get("epalId").toString());
			query.setString("epalId", map.get("epalId").toString());
		}
		if (null != map.get("isBind").toString()
				&& !"".equals(map.get("isBind").toString())) {
//			//System.out.println( map.get("isBind").toString());
			query.setInteger("isBind", Integer.parseInt(map.get("isBind").toString()));
		}
		if (null != map.get("friendId").toString()
				&& !"".equals(map.get("friendId").toString())) {
//			//System.out.println( map.get("friendId").toString());
			query.setString("friendId", map.get("friendId").toString());
		}
		query.executeUpdate();
		
		try {
			
			if(map.get("isBind").toString().equals("0")){
				updateStudentStatus(-1, map.get("epalId").toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void updateStudentStatus(int status,String epal){
		
		String sql2 = "UPDATE class_student SET `member_id` = 0 WHERE epal_id = '"+epal+"'";
		this.executeUpdateSQL(sql2);
		
	}

	@Override
	public Page searchDeviceList(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from device");
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" WHERE epal_id=:epalId ");
		}
		sql.append(" ORDER BY " + "id ASC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			query.setString("epalId", map.get("epalId").toString());
		}

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<Device> devices = new ArrayList<Device>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Device device = new Device();
				Object[] obj = dataList.get(i);
				device.setId((Integer) obj[0]);
				device.setDeviceNo((String) obj[1]);
				device.setEpalId((String) obj[2]);
				device.setSn((String) obj[3]);
				device.setEpalPwd((String) obj[4]);
				device.setNickName((String) obj[5]);
				
				devices.add(device);
			}
		}
		Page resultPage = new Page<Device>(devices,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void saveSoundPlayHistory(DeviceSoundPlayHistory dsph) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(dsph);
	}

	@Override
	public Page getSoundPlayHistoryFromDevice(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.epal_id,a.sound_id,a.insert_date,b.name,b.image,b.playUrl from device_sound_play_history as a,xmly_sound as b where b.`status` = 1 and a.sound_id=b.soundId");
		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			sql.append(" and epal_id=:epalId ");
		}
		
		if (null != map.get("playType")
				&& !"".equals(map.get("playType").toString())) {
			sql.append(" and playType=:playType ");
		}
		sql.append(" ORDER BY " + "id DESC");

		Query query = this.getQuery(sql.toString());

		if (null != map.get("epalId")
				&& !"".equals(map.get("epalId").toString())) {
			query.setString("epalId", map.get("epalId").toString());
		}

		if (null != map.get("playType")
				&& !"".equals(map.get("playType").toString())) {
			query.setString("playType", map.get("playType").toString());
		}
		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> DeviceSoundPlayHistorys = new  ArrayList<HashMap>();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				HashMap deviceSoundPlayHistory=new HashMap();
				Object[] obj = dataList.get(i);
				deviceSoundPlayHistory.put("id",obj[0]);

				deviceSoundPlayHistory.put("epalId", obj[1]);
				deviceSoundPlayHistory.put("soundId", obj[2]);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				deviceSoundPlayHistory.put("insertDate",df.format(obj[3]));
				deviceSoundPlayHistory.put("name",obj[4]);
				deviceSoundPlayHistory.put("image",obj[5]);
				deviceSoundPlayHistory.put("playUrl",obj[6]);
				DeviceSoundPlayHistorys.add(deviceSoundPlayHistory);
			}
		}
		Page resultPage = new Page<HashMap>(DeviceSoundPlayHistorys,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void saveDeviceActivity(DeviceActivity deviceActivity) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceActivity);
		
	}

	@Override
	public Page getReplacement(HashMap map) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from replacement where status=0");

		sql.append(" ORDER BY " + "id DESC");

		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<Replacement> replacements = new ArrayList<Replacement>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Replacement replacement=new Replacement();
				Object[] obj = dataList.get(i);
				replacement.setId((Integer) obj[0]);
				replacement.setLastEpalId((String) obj[1]);
				replacement.setCurrentEpalId((String) obj[2]);
				replacement.setCreateTime((String) obj[3]);
				replacement.setRemark((String) obj[4]);
				replacements.add(replacement);
			}
		}
		Page resultPage = new Page<Replacement>(replacements,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void saveReplacement(Replacement replacement) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(replacement);
	}

	@Override
	public void saveSynchronousData(HashMap map) {
		// TODO Auto-generated method stub
		HashMap oldMap = new HashMap();
		oldMap.put("epalId", map.get("oldEpalId").toString());
		oldMap.put("page", "1");
		oldMap.put("pageSize","1");
		Page<ClassStudent> oldStudent = lessonDao.findClassStudentByEpalId(oldMap);
		ClassStudent oldClassStudent = oldStudent.getItems().get(0);
		int oldStudentId = oldClassStudent.getId();
		
		HashMap newMap = new HashMap();
		newMap.put("epalId", map.get("newEpalId").toString());
		newMap.put("page", "1");
		newMap.put("pageSize","1");
		Page<ClassStudent> newStudent = lessonDao.findClassStudentByEpalId(newMap);
		ClassStudent newClassStudent = newStudent.getItems().get(0);
		int newStudentId = newClassStudent.getId();


		if(!"".equals(map.get("online"))&& null!=map.get("online")){
			//新机器ID对应老学生ID
			StringBuffer sql1 = new StringBuffer();
			sql1.append("update class_student set epal_id='"+map.get("newEpalId").toString()+"' where id="+oldStudentId);
			this.executeUpdateSQL(sql1.toString());
			
			//删除机器人学生数据，重新生成
			StringBuffer sql2 = new StringBuffer("delete from ClassStudent where id = "
					+ newStudentId);
			this.executeHQL(sql2.toString());
		}
		if(!"".equals(map.get("deviceBookStack"))&& null!=map.get("deviceBookStack")){
			//System.out.println("同步机器人书库");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("update replacement set status=1 where id="+ map.get("id"));
		this.executeUpdateSQL(sql.toString());

	}

	@Override
	public String getNewEpalId(String epalId) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from replacement where last_epal_id ='"  + epalId + "'");
		List <Object[]> data = this.executeSQL(sql.toString());
		if(data.size()>0){
			String newEpalId = data.get(0)[2].toString();
			return newEpalId;
		}else{
			return null;
		}
	}

	@Override
	public Integer getDeviceCount() {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from Device");
		int deviceCount = this.countHQL(sql.toString());
		return deviceCount;
	}

	@Override
	public Page getOnlineDeviceRecord(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from DeviceOnlineRecordCount where 1=1");
		if(!"".equals(map.get("startDate"))&&!"".equals(map.get("endDate"))){
			sql.append(" and insertDate>='"+map.get("startDate").toString()+"' and insertDate<='"+map.get("endDate").toString()+"'");
		}
		sql.append(" order by id desc");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));
	}

	@Override
	public Page getCategoryList(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("from DeviceCategory where 1=1");
		sql.append(" order by id desc");
		return this.pageQueryByHql(sql.toString(), new Integer(map.get("page")
				.toString()), new Integer(map.get("pageSize").toString()));
	}

	@Override
	public JSONArray getDeviceListByCategory(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select a.device_no,b.epal_id,ifnull((select time from device_online_record where device_online_record.epal_id=b.epal_id order by id desc limit 1),'未开机') from device_category_to_device as a,device as b where a.device_no=b.device_no and a.category_id=:categoryId");

		Query query = this.getQuery(sql.toString());
		query.setInteger("categoryId", Integer.parseInt(map.get("categoryId").toString()));
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		
		
		StringBuffer sql4 = new StringBuffer();
		sql4.append("select epal_id,friend_id from device_relation where isbind=1");
		Query query4 = this.getQuery(sql4.toString());
		Page dataPage4 = this.pageQueryBySQL(query4, 1, 10000);
		ArrayList<Object[]> dataList4 = (ArrayList<Object[]>) dataPage4
				.getItems();
		JSONObject deviceRelation = new JSONObject();
		
		for(int i=0;i<dataList4.size();i++){
			Object[] obj = dataList4.get(i);
			deviceRelation.put(obj[0], obj[1]);
		}
		
		JSONArray epalIdList = new JSONArray();
		
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				JSONObject temp = new JSONObject();
				Object[] obj = dataList.get(i);
				temp.put("deviceNo", obj[0]);
				String epalId = (String)obj[1];
				temp.put("epalId", epalId);
				temp.put("onTime", obj[2]);
				if(deviceRelation.containsKey(epalId)){
					temp.put("bindMobile", deviceRelation.get(epalId).toString());
				}else{
					temp.put("bindMobile", "未绑定");
				}
				temp.put("classGrades", "");
				result.add(temp);
			}
		}
		
		
//		StringBuffer sql3 = new StringBuffer();
//		sql3.append("select c.class_grades_name,a.epal_id from class_student as a,class_grades_rela as b,class_grades as c where a.id=b.class_student_id and b.class_grades_id=c.id and a.epal_id in " + epalIdList);
//		Query query3 = this.getQuery(sql3.toString());
//		Page dataPage3 = this.pageQueryBySQL(query3, 1, 10000);
//		ArrayList<Object[]> dataList3 = (ArrayList<Object[]>) dataPage3
//				.getItems();
//		
//		for(int i=0;i<dataList3.size();i++){
//			Object[] obj = dataList3.get(i);
//			deviceRelation.put((String) obj[0], (String) obj[1]);
//		}
		return result;
		
	}

	@Override
	public void saveDeviceCategory(DeviceCategory deviceCategory) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceCategory);
	}

	@Override
	public void saveDeviceNoToCategory(HashMap map) {
		// TODO Auto-generated method stub
		String categoryId= map.get("categoryId").toString();
		String deviceNoList =  map.get("deviceNoList").toString();
		String [] temp = deviceNoList.split("\n");
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
				"insert into device_category_to_device (device_no,category_id) values (?,?)");
		try {
			pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
					.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i=0;i<temp.length;i++) {
			try {
				pst.setString(1, temp[0]);
				pst.setInt(2, Integer.parseInt(categoryId));
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
	public JSONObject getDeviceActive(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select activityTime,epalId from device_activity order by id asc) t group by epalId order by activityTime asc");

		Query query = this.getQuery(sql.toString());


		Page dataPage = this.pageQueryBySQL(query, 1, 1000000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONObject result = new JSONObject();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				String activityTime = (String) obj[0];
				String epalId = (String) obj[1];
				String activeDate = activityTime.split(" ")[0];
				JSONArray temp = new JSONArray();
				if(result.containsKey(activeDate)){
					temp = result.getJSONArray(activeDate);
					temp.add(epalId);
				}else{
					temp.add(epalId);
				}
				result.put(activeDate, temp);
			}
		}
		return result;
	}

	@Override
	public void saveHistorySchedules(HistorySchedules historySchedules) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(historySchedules);
	}

	@Override
	public JSONArray searchHistorySchedules(HashMap map) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		String epalId = map.get("epalId").toString();
		String startTime = map.get("startTime").toString();
		String endTime = map.get("endTime").toString();
		sql.append("select * from history_schedules where epalId='"+epalId+"' and exe_time>="+startTime+" and exe_time<=" + endTime);
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query,1,10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject temp = new JSONObject();
				temp.put("id", (Integer)obj[0]);
				temp.put("event", (String)obj[1]);
				temp.put("sid", (String)obj[2]);
				temp.put("do_time", (String)obj[3]);
				temp.put("picture", (String)obj[4]);
				temp.put("content", (String)obj[5]);
				temp.put("note", (String)obj[6]);
				temp.put("period", (String)obj[7]);
				temp.put("type", (String)obj[8]);
				temp.put("epalId", (String)obj[9]);
				temp.put("scheduId", (String)obj[10]);
				temp.put("title", (String)obj[11]);
				temp.put("exe_time", (BigInteger)obj[12]);
				temp.put("description", (String)obj[13]);
				result.add(temp);
			}
		}
		return result;
	}

	@Override
	public void saveDeviceLiber(DeviceLiber deviceLiber) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceLiber);
		
	}

	@Override
	public JSONArray getUploadFileList(HashMap map) {
		// TODO Auto-generated method stub

		StringBuffer sql = new StringBuffer();
		String userId = map.get("userId").toString();
		sql.append("select * from device_upload_file where user_id=:userId");
		Query query = this.getQuery(sql.toString());
		query.setString("userId", userId);
		Page dataPage = this.pageQueryBySQL(query,1,10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		JSONArray result = new JSONArray();
		
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject temp = new JSONObject();
				temp.put("id", (Integer)obj[0]);
				temp.put("musicUrl", (String)obj[1]);
				temp.put("filePath", (String)obj[2]);
				temp.put("fileName", (String)obj[3]);
				temp.put("userId", (String)obj[4]);
				temp.put("time", (String)obj[5]);
				temp.put("imageUrl", (String)obj[6]);
				temp.put("type", (String)obj[7]);
				result.add(temp);
			}
		}
		return result;
	}

	@Override
	public void deleteUploadFile(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("delete from DeviceUploadFile where id = "
				+ id);
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveUploadFileInfo(DeviceUploadFile deviceUploadFile) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(deviceUploadFile);
	}
}