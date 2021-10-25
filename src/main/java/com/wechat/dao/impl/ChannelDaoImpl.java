package com.wechat.dao.impl;

import com.mysql.jdbc.PreparedStatement;
import com.wechat.dao.ChannelDao;
import com.wechat.entity.*;
import com.wechat.util.Page;
import com.wechat.util.PropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class ChannelDaoImpl extends BaseDaoImpl implements ChannelDao {

	static final String JDBC_DRIVER = PropertyUtil
			.getDataBaseProperty("driverClass");
	static final String DB_URL = PropertyUtil.getDataBaseProperty("jdbcUrl");

	static final String USER = PropertyUtil.getDataBaseProperty("jdbcName");
	static final String PASS = PropertyUtil.getDataBaseProperty("password");

	/*
	 * 根据机器人ID获取所属频道
	 */
	@Override
	public JSONArray getChannelList(String userId) {
		// TODO Auto-generated method stub
		JSONArray channelList = new JSONArray();
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.user_id,a.channel_id,b.name,b.intro,ifnull(b.image,''),b.fans from user_channel as a,xmly_channel as b where  a.channel_id=b.id and user_id=:userId");
		Query query = this.getQuery(sql.toString());
		query.setString("userId", userId);
		Page dataPage = this.pageQueryBySQL(query, 1, 1000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> answers = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject temp = new JSONObject();
				temp.put("id", obj[0]);
				temp.put("userId", obj[1]);
				temp.put("channelId", obj[2]);
				temp.put("name", obj[3]);
				temp.put("intro", obj[4]);
				temp.put("image", obj[5]);
				temp.put("fans", obj[6]);
				channelList.add(temp);
			}
		} else {

		}
		return channelList;
	}

	/*
	 * 根据频道ID获取频道的音频列表
	 */
	@Override
	public Page getSoundFromChannel(HashMap map) {
		// TODO Auto-generated method stub
		String channelId = (String) map.get("channelId");
		StringBuffer sql = new StringBuffer();
		sql.append("select c.id,c.soundId,c.name,c.image,c.intro,c.playTimes,c.playUrl,c.albumName,b.albumId from xmly_channel_album as a,xmly_album_sound as b,xmly_sound as c where c.status=1 and a.albumId=b.albumId and b.soundId=c.id and a.channelId=:channelId");
		sql.append(" order by b.id desc");
		Query query = this.getQuery(sql.toString());

		query.setInteger("channelId", Integer.parseInt(channelId));
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> sounds = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap sound = new HashMap();
				sound.put("id", obj[0]);
				sound.put("soundId", obj[1]);
				sound.put("name", obj[2]);
				sound.put("image", obj[3]);

				sound.put("intro", obj[4]);
				sound.put("playTimes", obj[5]);
				sound.put("playUrl", obj[6]);
				sound.put("albumName", obj[7]);
				sound.put("albumId", obj[8]);
				sounds.add(sound);
			}
		}
		Page resultPage = new Page<HashMap>(sounds, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	/*
	 * 
	 * 
	 * 根据专辑ID获取频道的音频列表
	 */
	@Override
	public Page getSoundFromAlbum(HashMap map) {
		// TODO Auto-generated method stub

		String albumId = (String) map.get("albumId");
		StringBuffer sql = new StringBuffer();
		if (null != map.get("tagId") && !"".equals(map.get("tagId"))) {
			sql.append("select a.id,a.soundId,a.name,a.image,a.intro,a.playTimes,a.playUrl,a.albumName,b.albumId from xmly_sound as a,xmly_album_sound as b,sound_tag_source as c where a.status=1 and a.id=b.soundId and b.albumId=:albumId and c.sourceId=a.id and c.sourceType='sound' and c.tagId=:tagId");
		} else {
			sql.append("select a.id,a.soundId,a.name,a.image,a.intro,a.playTimes,a.playUrl,a.albumName,b.albumId from xmly_sound as a,xmly_album_sound as b where a.status=1 and a.id=b.soundId and b.albumId=:albumId");
		}
		Query query = this.getQuery(sql.toString());

		if (null != map.get("tagId") && !"".equals(map.get("tagId"))) {
			query.setInteger("tagId",
					Integer.parseInt(map.get("tagId").toString()));
		}
		query.setInteger("albumId", Integer.parseInt(albumId));
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> sounds = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap sound = new HashMap();
				sound.put("id", obj[0]);
				sound.put("soundId", obj[1]);
				sound.put("name", obj[2]);
				sound.put("image", obj[3]);

				sound.put("intro", obj[4]);
				sound.put("playTimes", obj[5]);
				sound.put("playUrl", obj[6]);
				sound.put("albumName", obj[7]);
				sound.put("albumId", obj[8]);
				sounds.add(sound);
			}

		}
		Page resultPage = new Page<HashMap>(sounds, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void saveChannel(XMLYChannel xmlyChannel) {
		// 保存或者修改频道
		this.saveOrUpdate(xmlyChannel);
	}

	@Override
	public void deleteChannel(HashMap map) {
		// 删除频道
		String id = map.get("id").toString();
		StringBuffer sql = new StringBuffer(
				"delete from XMLYChannel where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public void deleteRecommend(HashMap map) {
		// 删除推荐
		String id = map.get("id").toString();
		StringBuffer sql = new StringBuffer(
				"delete from SoundRecommendGroupToSound where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveAlbum(XMLYAlbum xmlyAlbum) {
		// 保存或者修改专辑
		this.saveOrUpdate(xmlyAlbum);

	}

	@Override
	public void deleteAlbum(HashMap map) {
		// 删除专辑
		String id = map.get("albumId").toString();
		StringBuffer sql = new StringBuffer(
				"delete from xmly_album where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page searchChannels(HashMap map) {
		// 分页查询频道列表
		StringBuffer sql = new StringBuffer("select * from xmly_channel");
		String searchStr = map.get("searchStr").toString();
		if (!"".equals(searchStr) && null != searchStr) {
			sql.append(" where name like  '%" + map.get("searchStr")).append(
					"%'");
		}
		sql.append(" ORDER BY updateCycle ASC,level DESC,id DESC");
		Query query = this.getQuery(sql.toString());
		String countSql = "from XMLYChannel";
		Page dataPage = this.pageQueryBySQL(query, countSql,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
		ArrayList<XMLYChannel> xmlyChannels = new ArrayList<XMLYChannel>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				XMLYChannel xmlyChannel = new XMLYChannel();
				xmlyChannel.setId((Integer) obj[0]);
				xmlyChannel.setName((String) obj[1]);
				xmlyChannel.setFans((Integer) obj[2]);
				xmlyChannel.setIntro((String) obj[3]);
				xmlyChannel.setImage((String) obj[4]);
				xmlyChannel.setChannelId((Integer) obj[5]);
				xmlyChannel.setStatus((Integer) obj[6]);
				xmlyChannel.setLevel((Integer) obj[7]);
				xmlyChannel.setLastUpdateTime((String) obj[8]);
				xmlyChannel.setNextUpdateTime((String) obj[9]);
				xmlyChannel.setUpdateCycle((Integer) obj[10]);
				xmlyChannels.add(xmlyChannel);
			}

		}

		Page resultPage = new Page<XMLYChannel>(xmlyChannels,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Page searchAlbums(HashMap map) {
		// 查找专辑列表
		String searchStr = "";
		if (map.containsKey("searchStr")) {
			searchStr = map.get("searchStr").toString();
		} else {
			searchStr = "";
		}

		StringBuffer sql = new StringBuffer("select * from xmly_album");
		if (!"".equals(searchStr) && null != searchStr) {
			sql.append(" where name like  '%" + map.get("searchStr")).append(
					"%'");
		}
		sql.append(" order by id desc");
		String countSql = "from XMLYAlbum";

		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
		ArrayList<XMLYAlbum> xmlyAlbums = new ArrayList<XMLYAlbum>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				XMLYAlbum xmlyAlbum = new XMLYAlbum();
				xmlyAlbum.setId((Integer) obj[0]);
				xmlyAlbum.setAlbumId((Integer) obj[1]);
				xmlyAlbum.setChannelId((Integer) obj[2]);
				xmlyAlbum.setName((String) obj[3]);
				xmlyAlbum.setImage((String) obj[4]);
				xmlyAlbum.setIntro((String) obj[5]);
				xmlyAlbum.setStatus((Integer) obj[6]);
				xmlyAlbum.setSortId((Integer) obj[7]);
				xmlyAlbums.add(xmlyAlbum);
			}

		}

		Page resultPage = new Page<XMLYAlbum>(xmlyAlbums,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	@Override
	public Page searchSounds(HashMap map) {
		// 分页查找音频列表
		String soundNameStr = "";
		if (map.containsKey("soundNameStr")) {
			soundNameStr = map.get("soundNameStr").toString();
		} else {
			soundNameStr = "";
		}
		StringBuffer sql = new StringBuffer("select * from xmly_sound where status = 1 ");
		if (!"".equals(soundNameStr) && null != soundNameStr) {
			sql.append("and name like  '%" + map.get("soundNameStr"))
					.append("%'");
		}
		sql.append(" order by score desc");
		Query query = this.getQuery(sql.toString());
		String countSql = "from XMLYSound";
		if (!"".equals(soundNameStr) && null != soundNameStr) {
			countSql = countSql + " where name like  '%"
					+ map.get("soundNameStr") + "%'";
		}
		Page dataPage = this.pageQueryBySQL(query, countSql,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
		ArrayList<XMLYSound> xmlySounds = new ArrayList<XMLYSound>();
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		if (dataList.size() > 0) {

			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				XMLYSound xmlySound = new XMLYSound();
				xmlySound.setId((Integer) obj[0]);
				xmlySound.setSoundId((Integer) obj[1]);
				xmlySound.setName((String) obj[2]);
				xmlySound.setImage((String) obj[3]);
				xmlySound.setIntro((String) obj[4]);
				xmlySound.setPlayTimes((Integer) obj[5]);
				xmlySound.setPlayUrl((String) obj[6]);
				xmlySound.setUpdateTime((String) obj[7]);
				xmlySound.setAlbumName((String) obj[8]);
				xmlySound.setChannelId((Integer) obj[9]);
				xmlySound.setAlbumId((Integer) obj[10]);
				xmlySound.setStatus((Integer) obj[11]);
				xmlySound.setSortId((Integer) obj[12]);

				xmlySound.setCateId((String) obj[13]);
				xmlySound.setScore((Integer) obj[14]);
				xmlySound.setFDPlayCount((Integer) obj[15]);
				xmlySounds.add(xmlySound);
			}

		}

		Page resultPage = new Page<XMLYSound>(xmlySounds,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());

		return resultPage;
	}

	public void deleteSound(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"delete from xmly_sound where id = " + id);
		this.executeHQL(sql.toString());
	}

	@Override
	public Page getAlbumsFromChannel(HashMap map) {
		String id = (String) map.get("id");
		StringBuffer sql = new StringBuffer();
		if (null != map.get("tagId") && !"".equals(map.get("tagId"))) {
			sql.append("select a.id,a.album_id,b.channelId,a.name,a.image,a.intro,a.status,a.sort_id from xmly_album as a,xmly_channel_album as b,sound_tag_source as c where a.id=b.albumId and a.status=1 and b.channelId=:id and c.sourceId=a.id and c.sourceType='album' and c.tagId=:tagId order by a.sort_id desc");
		} else {
			sql.append("select a.id,a.album_id,b.channelId,a.name,a.image,a.intro,a.status,a.sort_id from xmly_album as a,xmly_channel_album as b where a.id=b.albumId and a.status=1 and b.channelId=:id order by a.sort_id desc");
		}
		Query query = this.getQuery(sql.toString());
		if (null != map.get("tagId") && !"".equals(map.get("tagId"))) {
			query.setInteger("tagId",
					Integer.parseInt(map.get("tagId").toString()));
		}
		query.setInteger("id", Integer.parseInt(id));
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> albums = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap album = new HashMap();
				album.put("id", obj[0]);
				album.put("albumId", obj[0]);
				album.put("channelId", obj[2]);
				album.put("name", obj[3]);

				album.put("image", obj[4]);
				album.put("intro", obj[5]);
				album.put("status", obj[6]);
				album.put("sortId", obj[7]);
				albums.add(album);
			}

		}
		Page resultPage = new Page<HashMap>(albums, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public JSONObject getRecommend(HashMap map) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		JSONArray sourceList = new JSONArray();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.id,b.intro,b.name,b.image,b.playUrl,b.albumName from sound_recommend_group as a,xmly_sound as b,sound_recommend_group_to_sound as c where b.`status` = 1 and b.soundId=c.soundId and a.id=c.groupId and tagCN=:tag order by c.sortId DESC");
		Query query = this.getQuery(sql.toString());
		query.setString("tag", map.get("tag").toString());
		Page dataPage = this.pageQueryBySQL(query, 1, 200);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> albums = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject sourceDetail = new JSONObject();
				sourceDetail.put("id", obj[0]);
				sourceDetail.put("intro", obj[1]);
				sourceDetail.put("duration", "");
				sourceDetail.put("SoundName", obj[2]);
				sourceDetail.put("coverUrl", obj[3]);
				sourceDetail.put("playCount", "");
				sourceDetail.put("palyPath", obj[4]);
				sourceDetail.put("albumTitle", obj[5]);
				sourceList.add(sourceDetail);
			}
			result.put("sourceList", sourceList);
			result.put("total", 200);
			result.put("nextPage", false);
			return result;
		} else {
			return result;
		}
	}

	@Override
	public void saveDeviceChannel(UserChannel userChannel) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(userChannel);
	}

	@Override
	public void deleteDeviceChannel(String id) {
		// 删除机器人和频道的关系
		StringBuffer sql = new StringBuffer(
				"delete from UserChannel where id = " + id);
		this.executeHQL(sql.toString());

	}

	@Override
	public Page searchSoundRecommend(HashMap map) {
		// 按照标签查询推荐音频
		String tag = (String) map.get("tag");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.tagEN,a.tagCN,b.id,c.soundId,c.image,c.name,c.playUrl,c.albumId,c.albumName from sound_recommend_group as a,sound_recommend_group_to_sound as b,xmly_sound as c where c.`status` = 1 and a.id=b.groupId and b.soundId=c.soundId");
		if (null != tag && !"".equals(tag)) {
			sql.append(" and a.tagCN=:tag");
		}
		sql.append(" order by b.sortId DESC");
		Query query = this.getQuery(sql.toString());
		if (null != tag && !"".equals(tag)) {
			query.setString("tag", tag);
		}

		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> recommends = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap recommend = new HashMap();
				recommend.put("tagEN", obj[0]);
				recommend.put("tagCN", obj[1]);
				recommend.put("id", obj[2]);
				recommend.put("soundId", obj[3]);

				recommend.put("image", obj[4]);
				recommend.put("name", obj[5]);
				recommend.put("playUrl", obj[6]);
				recommend.put("albumId", obj[7]);
				recommend.put("albumName", obj[8]);
				recommends.add(recommend);
			}

		}
		Page resultPage = new Page<HashMap>(recommends,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void switchSoundStatus(String soundId) {
		// 改变音频的状态
		String sql = "update xmly_sound set status=case when status=1 then 0 else 1 end where soundId="
				+ soundId;
		this.executeUpdateSQL(sql);

	}

	@Override
	public void saveRecommendAlbums(HashMap map) {
		// 通过专辑ID保存为推荐列表
		String tag = map.get("tag").toString();
		String albums = map.get("albums").toString();
		String[] albumList = albums.split(",");
		int groupId = getRecommendGroupId(tag);

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			StringBuffer insertMainCourseSql = new StringBuffer(
					"insert into sound_recommend_group_to_sound (groupId,soundId,albumId,sortId) values (?,?,?,?)");
			pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
					.toString());
			for (int i = 0; i < albumList.length; i++) {
				String albumId = albumList[i].toString();
				HashMap map2 = new HashMap();
				map2.put("albumId", albumId);
				map2.put("page", 1);
				map2.put("pageSize", 10000);
				Page result = getSoundFromAlbum(map2);
				List<HashMap> dataList = result.getItems();
				for (int j = 0; j < dataList.size(); j++) {
					HashMap temp = dataList.get(j);
					Integer soundId = Integer.parseInt(temp.get("soundId")
							.toString());
					pst.setInt(1, groupId);
					pst.setInt(2, soundId);
					pst.setInt(3, Integer.parseInt(albumId));
					pst.setInt(4, 1);
					pst.addBatch();
				}

			}
			pst.executeBatch(); // 执行批量处理
			conn.commit(); // 提交
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getRecommendGroupId(String tag) {
		// 通过推荐分组名称获取推荐分组ID
		int groupId = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("select id from sound_recommend_group where tagCN=:tag");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(tag) && null != tag) {
			query.setString("tag", tag);
		}
		groupId = (Integer) query.list().get(0);
		return groupId;
	}

	@Override
	public void updateAlbumById(XMLYAlbum xmlyAlbum) {
		// 保存专辑
		this.saveOrUpdate(xmlyAlbum);

	}

	@Override
	public Page searchSoundSearch(HashMap map) {
		// 查询搜索记录，分组查询
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		StringBuffer sql = new StringBuffer();
		sql.append("select soundName,COUNT(soundName) from sound_search where 1=1");
		if (!"".equals(startDate) && null != startDate) {
			startDate = startDate + " 00:00:00";
			sql.append(" and insertDate>=:startDate");
		}
		if (!"".equals(endDate) && null != endDate) {
			endDate = endDate + " 24:00:00";
			sql.append(" and insertDate<=:endDate");
		}

		sql.append(" group by soundName order by count(soundName) DESC");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(startDate) && null != startDate) {
			query.setString("startDate", startDate);
		}
		if (!"".equals(endDate) && null != endDate) {
			query.setString("endDate", endDate);
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> soundSearchs = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap soundSearch = new HashMap();
				soundSearch.put("soundName", obj[0]);
				soundSearch.put("count", obj[1]);

				soundSearchs.add(soundSearch);
			}

		}
		Page resultPage = new Page<HashMap>(soundSearchs,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void switchChannelStatus(String id) {
		// 改变频道的状态
		String sql = "update xmly_channel set status=case when status=1 then 0 else 1 end where id="
				+ id;
		this.executeUpdateSQL(sql);
	}

	@Override
	public void saveAlbumsToChannel(XMLYChannelAlbum xmlyChannelAlbum) {
		// 保存专辑和频道的对应关系
		this.saveOrUpdate(xmlyChannelAlbum);
	}

	@Override
	public void saveSoundToAlbum(XMLYAlbumSound xmlyAlbumSound) {
		// 保存音频和专辑的对应关系
		this.saveOrUpdate(xmlyAlbumSound);
	}

	@Override
	public void deleteAlbumFromChannel(HashMap map) {
		// 删除专辑和频道的对应关系

		StringBuffer sql = new StringBuffer(
				"delete from XMLYChannelAlbum where albumId = "
						+ Integer.parseInt(map.get("albumId").toString())
						+ " and channelId="
						+ Integer.parseInt(map.get("channelId").toString()));
		this.executeHQL(sql.toString());
	}

	@Override
	public void deleteSoundFromAlbum(HashMap map) {
		// 删除专辑和音频的对应关系
		StringBuffer sql = new StringBuffer(
				"delete from XMLYAlbumSound where albumId = "
						+ Integer.parseInt(map.get("albumId").toString())
						+ " and soundId="
						+ Integer.parseInt(map.get("soundId").toString()));
		this.executeHQL(sql.toString());
	}

	@Override
	public void saveSound(XMLYSound xmlySound) {
		// 保存音频
		this.saveOrUpdate(xmlySound);

	}

	@Override
	public void deleteSound(HashMap map) {
		// 删除音频
		StringBuffer sql = new StringBuffer("delete from XMLYSound where id= "
				+ Integer.parseInt(map.get("id").toString()));
		this.executeHQL(sql.toString());
	}

	@Override
	public Page soundPlayManage(HashMap map) {
		// TODO Auto-generated method stub
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		StringBuffer sql = new StringBuffer();
		sql.append("select b.id,b.name,COUNT(sound_id) from device_sound_play_history as a,xmly_sound as b where b.`status` = 1 and a.sound_id=b.soundId");
		if (!"".equals(startDate) && null != startDate) {
			startDate = startDate + " 00:00:00";
			sql.append(" and insert_date>=:startDate");
		}
		if (!"".equals(endDate) && null != endDate) {
			endDate = endDate + " 24:00:00";
			sql.append(" and insert_date<=:endDate");
		}

		sql.append(" group by a.sound_id order by count(a.sound_id) DESC");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(startDate) && null != startDate) {
			query.setString("startDate", startDate);
		}
		if (!"".equals(endDate) && null != endDate) {
			query.setString("endDate", endDate);
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> soundSearchs = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap soundSearch = new HashMap();
				soundSearch.put("soundId", obj[0]);
				soundSearch.put("soundName", obj[1]);
				soundSearch.put("count", obj[2]);

				soundSearchs.add(soundSearch);
			}

		}
		Page resultPage = new Page<HashMap>(soundSearchs,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getSearchDetail(HashMap map) {
		// TODO Auto-generated method stub
		String searchName = (String) map.get("searchName");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.soundName,a.userName,a.page,a.ISBN,a.insertDate,a.remoteIP from sound_search as a");
		if (!"".equals(searchName) && null != searchName) {
			sql.append(" where a.soundName=:soundName");
		}
		sql.append(" order by a.id desc");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> searchrecords = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap searchrecord = new HashMap();
				searchrecord.put("id", obj[0]);
				searchrecord.put("soundName", obj[1]);
				searchrecord.put("userName", obj[2]);
				searchrecord.put("page", obj[3]);

				searchrecord.put("ISBN", obj[4]);
				searchrecord.put("insertDate", obj[5]);
				searchrecord.put("remoteIP", obj[6]);
				searchrecords.add(searchrecord);
			}

		}
		Page resultPage = new Page<HashMap>(searchrecords,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void updateCycle(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("update xmly_channel set updateCycle=" + map.get("num")
				+ " where id=" + map.get("id"));
		this.executeUpdateSQL(sql.toString());
	}

	@Override
	public void updateChannelInfo(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = df.format(new Date());
		sql.append("update xmly_channel set nextUpdateTime='" + currentTime
				+ "' where id=" + map.get("id"));
		this.executeUpdateSQL(sql.toString());
	}

	@Override
	public Page getTagListFromGroup(HashMap map) {
		// TODO 通过分组ID，获取当前分组下所有的标签列表,当状态为-1时候，取全部标签
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.tagName,a.sortId,a.channelId,a.image,a.intro,a.groupId,a.status from sound_tag as a where 1=1");
		if (!"".equals(map.get("groupId")) && null != map.get("groupId")) {
			sql.append(" and a.groupId=:groupId");
		}

		if (!"".equals(map.get("tagStatus")) && null != map.get("tagStatus")
				&& !"-1".equals(map.get("tagStatus"))) {
			sql.append(" and a.status=:tagStatus");
		}

		sql.append(" order by a.sortId desc");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(map.get("groupId")) && null != map.get("groupId")) {
			query.setInteger("groupId",
					Integer.parseInt(map.get("groupId").toString()));
		}

		if (!"".equals(map.get("tagStatus")) && null != map.get("tagStatus")
				&& !"-1".equals(map.get("tagStatus"))) {
			query.setInteger("tagStatus",
					Integer.parseInt(map.get("tagStatus").toString()));
		}

		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> soundTags = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap soundTag = new HashMap();
				soundTag.put("id", obj[0]);
				soundTag.put("tagName", obj[1]);
				soundTag.put("sortId", obj[2]);
				soundTag.put("channelId", obj[3]);

				soundTag.put("image", obj[4]);
				soundTag.put("intro", obj[5]);
				soundTag.put("groupId", obj[6]);
				soundTag.put("status", obj[7]);
				soundTags.add(soundTag);
			}

		}
		Page resultPage = new Page<HashMap>(soundTags,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page getSoundTagGroupList(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.groupName,a.status,a.sortId,a.image,a.intro from sound_tag_group as a");
		sql.append(" order by a.sortId desc");
		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> soundTagGroups = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap soundTagGroup = new HashMap();
				soundTagGroup.put("id", obj[0]);
				soundTagGroup.put("groupName", obj[1]);
				soundTagGroup.put("status", obj[2]);
				soundTagGroup.put("sortId", obj[3]);
				soundTagGroup.put("image", obj[4]);
				soundTagGroup.put("intro", obj[5]);
				soundTagGroups.add(soundTagGroup);
			}

		}
		Page resultPage = new Page<HashMap>(soundTagGroups,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page searchLabel(HashMap map) {

		StringBuffer sql = new StringBuffer(
				"select a.id,a.tagName,b.groupName from sound_tag as a,sound_tag_group as b where a.groupId=b.id ");
		sql.append(" order by a.id desc ");
		Query query = this.getQuery(sql.toString());
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("rowsPerPage").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		ArrayList<HashMap> soundTags = new ArrayList<HashMap>();
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap soundTag = new HashMap();
				soundTag.put("id", obj[0]);
				soundTag.put("tagName", obj[1]);
				soundTag.put("groupName", obj[2]);
				soundTags.add(soundTag);
			}
		}
		Page resultPage = new Page<HashMap>(soundTags,
				dataPage.getTotalCount(), dataPage.getIndexes(),
				dataPage.getStartIndex(), dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public JSONObject getSourceFromTag(HashMap map) {
		// TODO Auto-generated method stub
		String tagId = map.get("tagId").toString();
		JSONObject data = new JSONObject();
		map.put("page", "1");
		map.put("pageSize", "20");

		data.put("channel", getChannelFromTag(map).getItems());
		data.put("album", getAlbumFromTag(map).getItems());
		data.put("sound", getSoundFromTag(map).getItems());
		return data;
	}

	/*
	 * 通过标签ID获取标签下频道
	 */

	@Override
	public Page getChannelFromTag(HashMap map) {
		JSONArray result = new JSONArray();

		StringBuffer sql = new StringBuffer(
				"select b.id,b.name,b.image,b.intro,b.fans from sound_tag_source as a,xmly_channel as b where a.sourceId=b.id and sourceType='channel'");
		String tagId = map.get("tagId").toString();
		if (!"".equals(tagId) && null != tagId) {
			sql.append(" and a.tagId=:tagId");
		}
		sql.append(" order by a.sortId desc ");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(tagId) && null != tagId) {
			query.setInteger("tagId", Integer.parseInt(tagId));
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap channel = new HashMap();
				channel.put("id", obj[0]);
				channel.put("name", obj[1]);
				channel.put("image", obj[2]);
				channel.put("intro", obj[3]);
				channel.put("fans", obj[4]);
				result.add(channel);
			}
		}
		Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	/*
	 * 通过标签ID获取标签下专辑列表
	 */

	@Override
	public Page getAlbumFromTag(HashMap map) {
		JSONArray result = new JSONArray();
		StringBuffer sql = new StringBuffer(
				"select b.id,b.name,b.image,b.intro from sound_tag_source as a,xmly_album as b where a.sourceId=b.id and sourceType='album'");
		String tagId = map.get("tagId").toString();
		if (!"".equals(tagId) && null != tagId) {
			sql.append(" and a.tagId=:tagId");
		}
		sql.append(" order by a.sortId desc ");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(tagId) && null != tagId) {
			query.setInteger("tagId", Integer.parseInt(tagId));
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap album = new HashMap();
				album.put("id", obj[0]);
				album.put("name", obj[1]);
				album.put("image", obj[2]);
				album.put("intro", obj[3]);
				result.add(album);
			}
		}
		Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	/*
	 * 通过标签ID获取标签下音频列表
	 */
	@Override
	public Page getSoundFromTag(HashMap map) {
		JSONArray result = new JSONArray();

		StringBuffer sql = new StringBuffer(
				"select b.id,b.name,b.image,b.intro,b.playUrl,b.playTimes from sound_tag_source as a,xmly_sound as b where b.status = 1 and a.sourceId=b.id and sourceType='sound'");
		String tagId = map.get("tagId").toString();
		if (!"".equals(tagId) && null != tagId) {
			sql.append(" and a.tagId=:tagId");
		}
		sql.append(" order by a.sortId desc ");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(tagId) && null != tagId) {
			query.setInteger("tagId", Integer.parseInt(tagId));
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap sound = new HashMap();
				sound.put("id", obj[0]);
				sound.put("name", obj[1]);
				sound.put("image", obj[2]);
				sound.put("intro", obj[3]);
				sound.put("playUrl", obj[4]);
				sound.put("playTimes", obj[5]);
				result.add(sound);
			}
		}
		Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public Page searchTag(HashMap map) {
		// TODO Auto-generated method stub

		JSONArray result = new JSONArray();
		String tagName = map.get("tagName").toString();
		StringBuffer sql = new StringBuffer(
				"select a.id,a.tagName,b.groupName from sound_tag as a,sound_tag_group as b where a.groupId=b.id");

		if (!"".equals(tagName) && null != tagName) {
			sql.append(" and a.tagName like  '%" + tagName).append("%'");
		}
		sql.append(" order by a.id desc ");
		Query query = this.getQuery(sql.toString());

		Page dataPage = this.pageQueryBySQL(query, 1, 10000);

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap tag = new HashMap();
				tag.put("id", obj[0]);
				tag.put("tagName", obj[1]);
				tag.put("groupName", obj[2]);
				result.add(tag);
			}
		}
		Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	public int getChannelIdFromAlbumId(String albumId) {
		int channelId = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("from XMLYChannelAlbum where albumId=" + albumId);
		List<XMLYChannelAlbum> dataList = this.executeHQL(sql.toString());
		if (dataList.size() > 0) {
			XMLYChannelAlbum xmlyChannelAlbum = dataList.get(0);
			channelId = xmlyChannelAlbum.getChannelId();
		}
		return channelId;

	}

	@Override
	public void saveTagToSource(HashMap map) {
		// TODO Auto-generated method stub
		String albumId = map.get("albumId").toString();
		String tagCheckedId = map.get("tagCheckedId").toString();
		String[] tagCheckedIds = tagCheckedId.split(",");

		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			StringBuffer insertMainCourseSql = new StringBuffer(
					"insert into sound_tag_source (tagId,sourceId,sourceType,sortId) values (?,?,?,?)");
			pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
					.toString());

			pst2 = (PreparedStatement) conn
					.prepareStatement(insertMainCourseSql.toString());

			for (int i = 0; i < tagCheckedIds.length; i++) {

				String tagId = tagCheckedIds[i].toString();
				pst.setInt(1, Integer.parseInt(tagId));
				pst.setInt(2, Integer.parseInt(albumId));
				pst.setString(3, "album");
				pst.setInt(4, 1);
				pst.addBatch();

				int channelId = getChannelIdFromAlbumId(albumId);
				pst2.setInt(1, Integer.parseInt(tagId));
				pst2.setInt(2, channelId);
				pst2.setString(3, "channel");
				pst2.setInt(4, 1);
				pst2.addBatch();

				StringBuffer sql = new StringBuffer();
				sql.append("from XMLYAlbumSound where albumId=" + albumId);
				List<XMLYAlbumSound> dataList = this.executeHQL(sql.toString());
				if (dataList.size() > 0) {
					for (int j = 0; j < dataList.size(); j++) {
						XMLYAlbumSound xmlyAlbumSound = dataList.get(j);
						pst.setInt(1, Integer.parseInt(tagId));
						pst.setInt(2, xmlyAlbumSound.getSoundId());
						pst.setString(3, "sound");
						pst.setInt(4, 1);
						pst.addBatch();
					}
				}
			}
			try {
				pst2.executeBatch();
			} catch (SQLException e) {

			}
			pst.executeBatch(); // 执行批量处理
			conn.commit(); // 提交
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Page getAlbumTagFromAlbumId(HashMap map) {
		// TODO Auto-generated method stub
		JSONArray result = new JSONArray();
		String albumId = map.get("albumId").toString();
		String channelId = map.get("channelId").toString();
		String soundId = map.get("soundId").toString();
		StringBuffer sql = new StringBuffer(
				"select a.id,b.tagName,b.image,b.intro from sound_tag_source as a,sound_tag as b where a.tagId=b.id");
		if (!"".equals(channelId) && null != channelId) {
			sql.append(" and a.sourceId=:sourceId and a.sourceType='channel'");
		}
		if (!"".equals(albumId) && null != albumId) {
			sql.append(" and a.sourceId=:sourceId and a.sourceType='album'");
		}
		if (!"".equals(soundId) && null != soundId) {
			sql.append(" and a.sourceId=:sourceId and a.sourceType='sound'");
		}
		sql.append(" order by a.id asc ");
		Query query = this.getQuery(sql.toString());
		if (!"".equals(channelId) && null != channelId) {
			query.setInteger("sourceId", Integer.parseInt(channelId));
		}
		if (!"".equals(albumId) && null != albumId) {
			query.setInteger("sourceId", Integer.parseInt(albumId));
		}
		if (!"".equals(soundId) && null != soundId) {
			query.setInteger("sourceId", Integer.parseInt(soundId));
		}
		Page dataPage = this.pageQueryBySQL(query,
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));

		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();

		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				HashMap tag = new HashMap();
				tag.put("id", obj[0]);
				tag.put("tagName", obj[1]);
				tag.put("image", obj[2]);
				tag.put("intro", obj[2]);
				result.add(tag);
			}
		}
		Page resultPage = new Page<HashMap>(result, dataPage.getTotalCount(),
				dataPage.getIndexes(), dataPage.getStartIndex(),
				dataPage.getPageSize());
		return resultPage;
	}

	@Override
	public void deleteTag(String id) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"delete from SoundTagSource where id= " + Integer.parseInt(id));
		this.executeHQL(sql.toString());
	}

	public Integer getChannelIdBySoundId(String soundId) {
		int channelId = 1;
		StringBuffer sql = new StringBuffer(
				"select a.channelId from xmly_channel_album as a,xmly_album_sound as b,xmly_channel as c where a.albumId=b.albumId and a.channelId=c.id and c.channel_id>1 and b.soundId=:soundId");
		Query query = this.getQuery(sql.toString());
		query.setInteger("soundId", Integer.parseInt(soundId));
		channelId = (Integer) query.list().get(0);
		return channelId;
	}

	public Integer getAlbumIdBySoundId(String soundId) {
		int albumId = 1;
		StringBuffer sql = new StringBuffer(
				"select a.albumId from xmly_album_sound as a where a.soundId=:soundId");
		Query query = this.getQuery(sql.toString());
		query.setInteger("soundId", Integer.parseInt(soundId));
		albumId = (Integer) query.list().get(0);
		return albumId;
	}

	@Override
	public void saveAlias(HashMap map) {

		Connection conn = null;
		PreparedStatement pst = null;
		String type = map.get("type").toString();
		String aliasName = map.get("aliasName").toString();
		String soundId = map.get("soundId").toString();
		Integer sourceId = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer();
			if ("sound".equals(type)) {
				sql.append("delete from sound_alias where soundId= "
						+ Integer.parseInt(soundId));
				sourceId = Integer.parseInt(soundId);
			} else if ("album".equals(type)) {
				Integer albumId = getAlbumIdBySoundId(soundId);
				sql.append("delete from album_alias where albumId= " + albumId);
				sourceId = albumId;
			} else if ("zhubo".equals(type)) {
				Integer zhuboId = getChannelIdBySoundId(soundId);
				sql.append("delete from zhubo_alias where zhuboId= " + zhuboId);
				sourceId = zhuboId;
			}

			pst = (PreparedStatement) conn.prepareStatement(sql.toString());

			pst.executeUpdate();

			StringBuffer insertMainCourseSql = null;

			if ("sound".equals(type)) {

				insertMainCourseSql = new StringBuffer(
						"insert into sound_alias (aliasName,soundId) values (?,?)");

			} else if ("album".equals(type)) {
				insertMainCourseSql = new StringBuffer(
						"insert into album_alias (aliasName,albumId) values (?,?)");

			} else if ("zhubo".equals(type)) {
				insertMainCourseSql = new StringBuffer(
						"insert into zhubo_alias (aliasName,zhuboId) values (?,?)");
			}
			String aliasList[] = aliasName.split(",");
			pst = (PreparedStatement) conn.prepareStatement(insertMainCourseSql
					.toString());
			for (int i = 0; i < aliasList.length; i++) {
				pst.setString(1, aliasList[i]);
				pst.setInt(2, sourceId);
				pst.addBatch();
			}
			pst.executeBatch(); // 执行批量处理
			conn.commit(); // 提交
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Page getAliasList(HashMap map) {
		// TODO Auto-generated method stub
		String type = map.get("type").toString();
		StringBuffer sql = new StringBuffer();
		if ("zhubo".equals(type)) {
			sql.append(" from ZhuboAlias where 1=1");
		} else if ("album".equals(type)) {
			sql.append(" from AlbumAlias where 1=1");
		} else if ("sound".equals(type)) {
			sql.append(" from SoundAlias where 1=1");
		} else {
			sql.append(" from SoundAlias where 1=2");
		}
		Page dataPage = this.pageQueryByHql(sql.toString(),
				Integer.parseInt(map.get("page").toString()),
				Integer.parseInt(map.get("pageSize").toString()));
		return dataPage;
	}

	@Override
	public Page getSoundFromAlbumByZhuboAlbumAlias(HashMap map) {
		// 通过主播和专辑名字获取专辑下音频列表
		Integer albumId = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.albumId from zhubo_alias as a,album_alias as b,xmly_channel_album as c where c.albumId=b.albumId and c.channelId=a.zhuboId and a.aliasName=:zhuboAlias and b.aliasName=:albumAlias");
		Query query = this.getQuery(sql.toString());
		query.setString("zhuboAlias", map.get("zhuboName").toString());
		query.setString("albumAlias", map.get("albumName").toString());

		albumId = (Integer) query.list().get(0);

		map.put("albumId", albumId.toString());
		Page result = getSoundFromAlbum(map);

		return result;
	}

	@Override
	public JSONObject getRecommendSound(HashMap map) {
		// 通过标签获取推荐音频
		StringBuffer sql = new StringBuffer();
		sql.append("select xs.id,xs.name,xs.intro,xs.image,xs.playUrl,xs.playTimes,xs.albumName from xmly_sound as xs,daily_recommend_tag as drt,daily_recommend_source as drs where xs.`status` = 1 and  drt.id=drs.tagId and drs.sourceId=xs.id and drt.tagName=:tagName ORDER BY drs.id");
		Query query = this.getQuery(sql.toString());
		query.setString("tagName", map.get("tag").toString());
		JSONObject result = new JSONObject();
		JSONArray sourceList = new JSONArray();
		Page dataPage = this.pageQueryBySQL(query, 1, 50);
		ArrayList<Object[]> dataList = (ArrayList<Object[]>) dataPage
				.getItems();
		;
		if (dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				Object[] obj = dataList.get(i);
				JSONObject sourceDetail = new JSONObject();
				sourceDetail.put("id", (Integer) obj[0]);
				sourceDetail.put("intro", (String) obj[2]);
				sourceDetail.put("duration", 0);
				sourceDetail.put("SoundName", (String) obj[1]);
				sourceDetail.put("coverUrl", (String) obj[3]);
				sourceDetail.put("playCount", (Integer) obj[5]);
				sourceDetail.put("palyPath", (String) obj[4]);
				sourceDetail.put("albumTitle", (String) obj[6]);
				sourceDetail.put("favoritesCount", 0);
				sourceList.add(sourceDetail);
			}

		}
		result.put("sourceList", sourceList);
		result.put("total", dataList.size());
		result.put("nextPage", false);
		return result;
	}

	@Override
	public Page<XMLYChannel> fuzzySearchByName(int page, int pageSize,
			String name) {
		//TODO 为了速度，先查id小于2000000的数据，共668327条数据
		StringBuffer hql = new StringBuffer(
				"from XMLYChannel s where s.id <1000000 and s.name like'%" + name + "%' and status > 0");

		return (Page<XMLYChannel>) this.pageQueryByHql(hql.toString(), page,
				pageSize);
	}

	@Override
	public void deleteSourceFromTag(HashMap map) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(
				"delete from SoundTagSource where sourceId = " + map.get("sourceId").toString());
		sql.append(" and sourceType='" + map.get("type").toString() + "'");
		this.executeHQL(sql.toString());
	}
}
