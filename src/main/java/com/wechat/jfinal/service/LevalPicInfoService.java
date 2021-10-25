package com.wechat.jfinal.service;

import com.wechat.jfinal.model.LevelPicInfo;

public class LevalPicInfoService {

    private static final LevelPicInfo dao = new LevelPicInfo().dao();

    public LevelPicInfo getById(int id){
        return dao.findById(id);
    }
}
