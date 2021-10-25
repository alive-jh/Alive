package com.wechat.util;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;


/**
 * 系统配置类,提供统一的系统配置入口.
 * 缺省从配置文件DEFAULT_CONFIG常量所指的文件gzdec-config.properties中读取配置；
 * 当不存在此文件时，则在指定名为 DB_CONFIG_PATH常量值的 系统变量中获取配置文件路径和名称；
 * 该类为commlib中的最基本包，log，jdbc等的处理都通过其读取配置信息；
 *
 */

public class AppConfig {
    /**
     * 缺省配置文件名
     */
    public static final String DEFAULT_CONFIG = "classpath:config.properties";

    /**
     * 不存在缺省的配置文件时，则查找是否该常量所指的系统变量System.properties中的值，作为配置文件；
     */
    public static final String DB_CONFIG_PATH = "db_config_path";

    /**
     * 读出的属性
     */
    private static Properties config;

    /**
     * 私有方法，单对象模式；
     */
    private AppConfig() {
    }
    /**
     * 初始化项目属性；
     */
    static {
        init();
    }

    /**
     * 当属性未初始化时，读取属性文件进行初始化 ，使用system.out输出调试信息；
     */
    private static void init() {
        try {
            config = new Properties();

            Class cfgClass = Class.forName("com.wechat.util.AppConfig");
            if (cfgClass == null) {
                //System.out.println("com.wechat.util.AppConfig: cfgClass is null");
            }
            //System.out.println("com.wechat.utilAppConfig: cfgClass loaded ");
            InputStream is = cfgClass.getResourceAsStream(DEFAULT_CONFIG);
            if (is == null) {
                try {
                    is = new FileInputStream(new File(System.getProperty(DB_CONFIG_PATH)));
                    try {
                        config.load(is);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("Cannot found config file, please check it out and try again.");
                }
            } else {
                //System.out.println("com.wechat.util.AppConfig:  Getted input stream of configuration file");
                try {
                    config.load(is);
                    //System.out.println("com.wechat.util.AppConfig:  successfully loaded gzdec-config.properties file");
                } catch (IOException e) {
                    //System.out.println("com.wechat.util.AppConfig:  Cannot configure system param, please check out the npulse-config.properties file");
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            //System.out.println("com.wechat.util.AppConfig:Cannot load class com.easou.common.utils.AppConfig");
            e.printStackTrace();
        }
    }

    /**
     * 取得所有配置，
     *
     * @return 返回所有配置
     */
    public static Properties getProperties() throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            return config;
        }
    }

    /**
     * 返回一个属性值
     *
     * @param propertyName 属性名
     * @return 返回指定属性名的值
     */
    public static String getProperty(String propertyName) throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            return config.getProperty(propertyName);
        }
    }

    /**
     * 返回指定的名称的属性值，
     *
     * @param propertyName
     * @param defaultValue
     * @return String类型的属性值；
     */
    public static String getProperty(String propertyName, String defaultValue) throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            return config.getProperty(propertyName, defaultValue);
        }
    }

    /**
     * 返回所有的属性名
     *
     * @return 返回指定属性名
     */
    public Enumeration getKeys() throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            return config.keys();
        }
    }

    /**
     * 返回一个布尔型的值
     *
     * @param propertyName
     * @return 返回属性名的bool值
     */
    public static boolean getBooleanProperty(String propertyName) throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            String value = config.getProperty(propertyName).toLowerCase();
            return Boolean.getBoolean(value);
        }
    }

    /**
     * 返回一个int型的值
     *
     * @param propertyName
     * @return 返回属性名的数字值；
     */
    public static int getIntegerProperty(String propertyName) throws AppConfigException {
        if (config == null) {
            //System.out.println("Can not find config properties ");
            throw new AppConfigException("Can not find config properties ");
        } else {
            String value = config.getProperty(propertyName);
            return Integer.parseInt(value);
        }
    }
     /*public static void main(String[] args) throws AppConfigException {
    	//System.out.println(AppConfig.getProperty("TRADEMARK_IMG_PATH")); 
     	}*/

}
