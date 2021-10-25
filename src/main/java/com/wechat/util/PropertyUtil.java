package com.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件处理类
 * 
 */
public class PropertyUtil {

	public static String environment;
	
	static{
		environment = System.getProperty("spring.profiles.active");
		environment = environment == null ? "非正式" : environment;
		//System.out.println("当前环境为"+environment);
	}
	
	public static String getProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	
	public static String getDataBaseProperty(String key) {
		String jdbcPath="";
		if(environment == null || environment.equals("development")) {
			jdbcPath="/config_dev/jdbc.properties";
		}else{
			jdbcPath="/config_formal/jdbc.properties";
		}
		InputStream in = PropertyUtil.class.getResourceAsStream(jdbcPath);
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}

	public static boolean getBooleanProperty(String key) {
		boolean is = false;
		String str = getProperty(key);
		is = (new Boolean(str)).booleanValue();
		return is;
	}

	public static String getConfigDir() {
		String dir = "/test";
		if (PropertyUtil.getBooleanProperty("isProductDeploy")) {
			dir = "/product";
		}
		return dir;
	}
	
	
	
}
