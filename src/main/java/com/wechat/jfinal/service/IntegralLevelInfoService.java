package com.wechat.jfinal.service;

import com.wechat.jfinal.model.IntegralLevelInfo;

/**
 * 与integral（贡献值）相关的service
 * 包括表：integral_level_info 、level_pic_info
 */
public class IntegralLevelInfoService {

    private static final IntegralLevelInfo dao = new IntegralLevelInfo().dao();

    public IntegralLevelInfo getByIntegral(int score){
        return dao.findFirst("SELECT * FROM integral_level_info WHERE integral_start <= ? AND integral_end >= ?",score,score);
        
    }
}
