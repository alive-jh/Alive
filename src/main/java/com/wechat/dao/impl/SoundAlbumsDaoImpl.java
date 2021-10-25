package com.wechat.dao.impl;


import com.wechat.dao.SoundAlbumsDao;
import com.wechat.entity.XMLYAlbum;
import com.wechat.util.Page;
import org.springframework.stereotype.Repository;

@Repository
public class SoundAlbumsDaoImpl extends BaseDaoImpl<XMLYAlbum> implements SoundAlbumsDao {

	@Override
	public Page<XMLYAlbum> fuzzySearchByName(String name, int page,
			int perPage) {
		StringBuffer hql = new StringBuffer(
				"from XMLYAlbum s where s.name like'%" + name + "%' and status > 0");

		return (Page<XMLYAlbum>) this.pageQueryByHql(hql.toString(), page,
				perPage);
	}

	@Override
	public Page<XMLYAlbum> SearchByAlbumId(Integer channelId, Integer pageSize,
			Integer page) {
		StringBuffer hql = new StringBuffer(
				"from XMLYAlbum s where s.channelId = " + channelId  + " and status > 0" );

		return (Page<XMLYAlbum>) this.pageQueryByHql(hql.toString(), page,
				pageSize);
	}

	
}
