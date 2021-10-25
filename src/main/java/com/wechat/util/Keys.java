package com.wechat.util;

public class Keys {

	public static final  String ACCOUNT_ID = "";//公众账号ID


	public static final  String SMS_ID = "";//短信接口账号


	public static final  String SMS_PWD = "";//短接接口密码

	//public static final  String USER_PIC_PATH = "d:/data/wechatImages/";//图片上传路径
	public static String USER_PIC_PATH = "/data/wechatImages/";//图片上传路径

	public static String QINIU_LOG_PATH;//七牛log路径

	public static final String STAT_NAME = Config.getProperty("NORMAL_SERVER_SITE");//域名

	public static final String APP_ID_EGG = "";//蛋蛋机器人
	public static final String APP_SECRET_EGG = "";//蛋蛋机器人

	public static final String APP_ID = "";//机器人加教育
	public static final String APP_SECRET = "";//机器人加教育

	/*public static final String APP_ID = "";//蛋蛋机器人
	public static final String APP_SECRET = "";//蛋蛋机器人
*/
	public static final String  LIBRARY_APP_ID = "";//凡豆未来书院

	public static final String  LIBRARY_APP_SECRET = "";//凡豆未来书院

	public static final String MINIAPP_GET_OPPEN_ID_API = "";

	public static final String QQ_APP_ID = "";
	public static final String QQ_APP_KEY = "";

	/*
	 * 凡豆小程序
	 */
	public static final String FANDDOU_MINIAPP_APP_ID = "";
	public static final String FANDDOU_MINIAPP_APP_SECRET = "";


	/*
	 *凡豆伴小程序
	 */
	public static final String FANDDOUBAN_MINIAPP_APP_ID = "";
	public static final String FANDDOUBAN_MINIAPP_APP_SECRET = "";


	/*
	 * 凡豆图书馆小程序
	 */
	public static final String FANDDOU_LIBRARY_APP_ID = "";
	public static final String FANDDOU_LIBRARY_APP_SECRET = "";


	public static String SERVER_SITE = "";

	//
	//


	//public static final String MCH_ID = "";
	public static final String MCH_ID = "";


	//public static final String API_KEY = "";
	public static final String API_KEY = "";


	public static final String CUSTOMER_MOBLIE = "";


	public static final String PROJECT_NAME = "凡豆科技";
	public static final String QY_PROJECT_NAME = "";
	public static final String SNP_PROJECT_NAME = "少年派智能教育";


	public static final String  SENDORDERNOTICE = "";//订单提交成功通知

	public static final String  SENDORDEREXPRESS = "";  //订单发货提醒

	public static final String  SERVICEORDEROTICE = "";//预约订单通知

	public static final String  ACCEPTANCESERVICE = "";//受理服务订单通知

	public static final String  UPDATEPRICE = ""; // 订单价格修改

	public static final String CLOSEORDER = "";//取消订单

	public static final String SENDLESSONINGETRAL = "";//积分

	public static final String SENDFREElESSONMESSAGE = "";

	public static final String SENDLESSONMESSAGE = "";

	public static final String APP_KEY = "";//淘宝短信接口appkey

	public static final String SECRET = "";//淘宝短信接口SECRET

	public static final String TAOBAO_MOBILE = "";

	/*public static final  String ACCESS_KEY = "";	//七牛access_key
	public static final  String SECRET_KEY= "";		//七牛secret_key */

	public static final  String ACCESS_KEY = "";	//七牛access_key
	public static final  String SECRET_KEY= "";		//七牛secret_key

	public static final  String URL_ADDRESS = "";			//七牛图片空间域名

	public static final  String QINIU_IMAGE = "";			//七牛图片空间域名-image

	public static final  String QINIU_SRC = "";			//七牛图片空间域名-source

	public static final  String QINIU_IMAGE_BUCKet = "";			//七牛图片空间域名-image


	public static final  String QYJY_APP_ID = "";			//公众号appid
	public static final  String QYJY_APP_SECRET = "";			//公众号SECRET

	public static final int SDKAPPID = ;
	public static final String SECRETKEY = "";

	//特殊符号https://zhidao.baidu.com/question/920354809774970419.html
	static {

		String env = System.getProperty("spring.profiles.active");

		//判断是不是开发模式
	    if(env == null || env.equals("development")) {

	    	QINIU_LOG_PATH="d:/data/qiniuLog/";

	    } else {

	    	QINIU_LOG_PATH="/data/qiniuLog/";

	    	SERVER_SITE = "";

	    }
	}
}
