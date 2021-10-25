package com.wechat.dao.impl;


import com.wechat.dao.SoundCollectionDao;
import com.wechat.entity.SoundCollection;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SoundCollectionDaoImpl extends BaseDaoImpl<SoundCollection> implements SoundCollectionDao {

	@Override
	public SoundCollection getBySoundId(int soundId) {
		String hql = "from SoundCollection c where c.soundId = "+soundId  + " and status > 0";
		@SuppressWarnings("unchecked")
		List<SoundCollection> list = this.executeHQL(hql);
		if(list.size() > 0 )
			return list.get(0);
		else
			return null;
	}

	@Override
	public Page<Object[] > getByISBN(String ISBN, String userId, String name, int currentPage,int pageSize) {
		List<Object> result = new ArrayList<>();
		String sql = "";
		if(ISBN != null){
			sql += "SELECT  " +
					"id,  " +
					"sound_id,  " +
					"image,  " +
					"sound_url,  " +
					"sound_name,  " +
					"serier_name,  " +
					"isbn  " +
					"FROM  " +
					"sound_collection  " +
					"WHERE  " +
					"isbn =  " +ISBN+
					" AND  " +
					"user_id =  '"	+name+"' "+
					" UNION  " +
					"SELECT  " +
					"id,  " +
					" sound_id,  " +
					"image,  " +
					"sound_url,  " +
					"sound_name,  " +
					"serier_name,  " +
					" isbn  " +
					"FROM  " +
					"sound_best  " +
					"WHERE  " +
					"isbn = "+ISBN+
					"  UNION  ";
		}
		sql +=	"SELECT  " +
				"id,  " +
				" soundId sound_id,  " +
				" image,  " +
				" playUrl sound_url,  " +
				" name sound_name,  " +
				" albumName serier_name,  " +
				" '123456' as isbn  " +
				" FROM  " +
				" xmly_sound  " +
				" WHERE  id < 700000 and " +
				" name like '%"	+name+"%' and "+
				"status=1";

		sql = "SELECT * FROM  ( " + sql +" )a GROUP BY a.sound_id";

		Page<Object[]> data = this.pageQueryBySQL(sql,currentPage,pageSize);


		return data;
	}
	
	
}
