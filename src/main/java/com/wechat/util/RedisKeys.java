package com.wechat.util;

public class RedisKeys {
	
	
	//全局系统参数
	public static final Integer ADMIN_TIME =new Integer(3600);	//用户登录状态有效期 
	
	
	//管理后台
	public static final String ADMIN_USER ="adminUser";	//登录用户
	
	public static final String REDIS_USER ="adminUser:";	//redis登录用户

	
	//用户数据
	public static final String REDIS_MODULA  ="adminModula:";	//用户权限菜单
	
	public static final String REDIS_LABEL  ="adminLabel:";	//商城标签
	
	public static final String REDIS_MALLORDER  ="mallOrder:";	//用户商城订单
	
	
	//APP接口
	public static final String APP_BOOK_INDEX = "appBookIndex";	//书院首页
	
	public static final String APP_BOOK_LABEL = "appBookLabel";	//书院分类
	
	public static final String APP_CURRICULUM_INDEX = "appCurriculumIndex";	//课程首页
	
	public static final String APP_CURRICULUM_INFO = "appCurriculumInfo";	//课程分类
	
	
	public static final String REDIS_SOUND_LABEL = "soundLabelList";//音频标签列表
	
	
	
}
