package com.wechat.dao;

import com.wechat.entity.XMLYAlbum;
import com.wechat.util.Page;


public interface SoundAlbumsDao{

	Page<XMLYAlbum> fuzzySearchByName(String soundName, int page, int perPage);

	Page<XMLYAlbum> SearchByAlbumId(Integer channelId, Integer pageSize,
                                    Integer page);

	
}
