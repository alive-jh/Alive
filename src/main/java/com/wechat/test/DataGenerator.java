package com.wechat.test;

import com.wechat.entity.ClassGrades;
import com.wechat.entity.ClassRoom;
import com.wechat.entity.dto.ClassRoomIndexDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataGenerator {
	
	private List classRoomIndexs;
	
	private List classRoomCategorys;
	
	private List classGradess;


	public List getClassRoomIndexs() {
		List dataList = new ArrayList();
		for (int i = 0; i < classRoomIndexs.size(); i++) {
			HashMap dataRecord = new HashMap();
			ClassRoomIndexDto classRoomIndexDto=(ClassRoomIndexDto) classRoomIndexs.get(i);
			HashMap<String, Object> propertis = new HashMap<String, Object>();
			propertis.put("summary", classRoomIndexDto.getSummary());
			propertis.put("createTime", new Timestamp(System.currentTimeMillis()));
			
			//解析数据集合
			String classRoomsStr=classRoomIndexDto.getClassRooms();
			List<ClassRoom> classRooms=new ArrayList<ClassRoom>();
			if(!"".equals(classRoomsStr)&&classRoomsStr!=null){
				String[] classRoomsStrArr=classRoomsStr.split("∪");
				for (int j = 0; j < classRoomsStrArr.length; j++) {
					ClassRoom classRoom=new ClassRoom();
					
					String[] classRoomStrArr=classRoomsStrArr[j].split("∫");
					
					if(classRoomStrArr.length != 9){
						//System.out.println(classRoomsStrArr[j]);
					}
					classRoom.setId(Integer.parseInt(classRoomStrArr[0]));
					classRoom.setTeacherId(Integer.parseInt(classRoomStrArr[1]));
					classRoom.setTeacherName(classRoomStrArr[2]);
					classRoom.setClassName(classRoomStrArr[3]);
					classRoom.setCover(classRoomStrArr[4]);
					classRoom.setSummary(classRoomStrArr[5]);
					try{
						classRoom.setSort(Integer.parseInt(classRoomStrArr[6]));
					}catch (Exception e){
						e.printStackTrace();
						
					}
					
					classRoom.setCreateTime(Timestamp.valueOf(classRoomStrArr[7]));
					classRoom.setGroupId(classRoomStrArr[8]);
					classRooms.add(classRoom);
				}
			}
			propertis.put("data", classRooms);
			dataRecord.put("id", classRoomIndexDto.getId());
			dataRecord.put("name", classRoomIndexDto.getCategoryName());
			dataRecord.put("propertis", propertis);
			dataRecord.put("parentId", classRoomIndexDto.getParentId());
			dataRecord.put("sort", classRoomIndexDto.getSort());
			dataList.add(dataRecord);
		}
		return dataList;
	}



	public void setClassRoomIndexs(List classRoomIndexs) {
		this.classRoomIndexs = classRoomIndexs;
	}



	public List getClassRoomCategorys() {
		List dataList = new ArrayList();
		for (int i = 0; i < classRoomCategorys.size(); i++) {
			HashMap dataRecord = new HashMap();
			ClassRoomIndexDto classRoomIndexDto=(ClassRoomIndexDto) classRoomCategorys.get(i);
			HashMap<String, Object> propertis = new HashMap<String, Object>();
			propertis.put("summary", classRoomIndexDto.getSummary());
			dataRecord.put("id", classRoomIndexDto.getId());
			dataRecord.put("name", classRoomIndexDto.getCategoryName());
			dataRecord.put("propertis", propertis);
			dataRecord.put("parentId", classRoomIndexDto.getParentId());
			dataRecord.put("sort", classRoomIndexDto.getSort());
			dataList.add(dataRecord);
		}
		return dataList;
	}



	public void setClassRoomCategorys(List classRoomCategorys) {
		this.classRoomCategorys = classRoomCategorys;
	}



	public List getClassGradess() {
		List dataList = new ArrayList();
		for (int i = 0; i < classGradess.size(); i++) {
			HashMap dataRecord = new HashMap();
			ClassGrades classGrades=(ClassGrades)classGradess.get(i);
			HashMap<String, Object> propertis = new HashMap<String, Object>();
			propertis.put("choose", classGrades.getChoose());
			propertis.put("cover", classGrades.getCover());
			propertis.put("summary", classGrades.getSummary());
			dataRecord.put("id", classGrades.getId());
			dataRecord.put("name", classGrades.getClassGradesName());
			dataRecord.put("propertis", propertis);
			dataRecord.put("parentId", classGrades.getParentId());
			dataRecord.put("sort", classGrades.getSort());
			dataList.add(dataRecord);
		}
		return dataList;
	}



	public void setClassGradess(List classGradess) {
		this.classGradess = classGradess;
	}
}
