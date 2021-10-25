package com.wechat.util.script;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/*
[
	{"command":201,"resourceId":"","content":"http://xxxx.mp3","offset":0,"priority":1,"ext":{"speed":100}},
	{"command":202,"resourceId":"3506","content":"","offset":0,"priority":0,"ext":{"playIndex":[1,2,3],"speed":100}},
	{"command":206,"resourceId":"","content":"","offset":0,"priority":0,"ext":{"timeLast":10000,"bReplay":true}},
	{"command":207,"resourceId":"","content":"hello world","offset":0,"priority":0,"ext":{"bReplay":true,"bScore":true,"option":[{"action":"","condition":"0"},{"action":"","condition":"30"}]}},
	

	{"command":211,"resourceId":"","content":"http://xxxx.mp4","offset":0,"priority":0,"ext":{"speed":100}},
	{"command":212,"resourceId":"3506","content":"","offset":0,"priority":0,"ext":{"playIndex":[1,2,3],"speed":100}},
	{"command":216,"resourceId":"","content":"","offset":0,"priority":0,"ext":{"timeLast":10000,"bReplay":true}},

	{"command":221,"resourceId":"","content":"http://xxxx.jpg","offset":1000,"priority":0,"ext":{}},
	{"command":226,"resourceId":"","content":"","offset":1000,"priority":0,"ext":{}},

	{"command":231,"resourceId":"","content":"","offset":0,"priority":0,"ext":{}},
	
	{"command":301,"resourceId":"3506","content":"","offset":0,"priority":0,"ext":{"readIndex":[1,2,3],"speed":100,"bReplay":true,"bScore":true,"option":[{"action":"","condition":"0"},{"action":"","condition":"30"}]}},
	{"command":302,"resourceId":"3506","content":"","offset":0,"priority":0,"ext":{"playIndex":[1,3,5],"readIndex":[2,4,6],"speed":100,"bReplay":true,"bScore":true,"option":[{"action":"","condition":"0"},{"action":"","condition":"30"}]}}
]
*/
/*
{"cnUrl":"","command":102,"content":"","expUrl":"","id":23,"mcTag":"","mediaInfo":"","name":"录音","option":[],"picLib":"","picUrl":"http://xxx/xx.jpg","recordTimeStr":"00:32","resName":"","resourceId":-1,"srcUrl":"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_31_15_57_07.mp3","times":1,"wordList":[]},
{"cnUrl":"","command":104,"content":"118,101[10]<120>","expUrl":"","id":25,"mcTag":"1517385472208","mediaInfo":"http://source.fandoutech.com.cn/1516697326850.xls","name":"跟读(共1句,时长:6秒)资源:DSN14晚会时间","option":[],"picLib":"","picUrl":"http://xxx/xx.jpg","recordTimeStr":"05:32","resId":"4609","resName":"DSN14晚会时间","resourceId":-1,"srcUrl":"http://source.fandoutech.com.cn/1516545968737.mp3","times":1,"wordList":[]},
{"cnUrl":"","command":106,"content":"119,110,111<80>","expUrl":"","id":27,"mcTag":"","mediaInfo":"http://source.fandoutech.com.cn/1516697326850.xls","name":"播放(共1句,时长:1秒)资源:DSN14晚会时间","option":[],"picLib":"","picUrl":"http://xxx/xx.jpg","recordTimeStr":"05:59:32","resId":"4609","resName":"DSN14晚会时间","resourceId":-1,"srcUrl":"http://source.fandoutech.com.cn/1516545968737.mp4","times":1,"wordList":[]},
{"cnUrl":"","command":108,"content":"the ducklings.[01]","conumeTime":1112,"expUrl":"","id":37,"mcTag":"","mediaInfo":"","name":"语句(the ducklings.)","option":[{"action":"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_11_14_58_42.mp3","condition":"80"},{"action":"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_11_14_59_03.mp3","condition":"50"}],"picLib":"","picUrl":"http://xxx/xx.jpg","recordTimeStr":"08:01","resName":"","resourceId":-1,"srcUrl":"","times":1,"wordList":[]}	
 */

public class ScriptTransform {
//	public static void main(String[] args) {
//		String[] srcData = {"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":23,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://xxx/xx.jpg\",\"recordTimeStr\":\"00:32\",\"resName\":\"\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_31_15_57_07.mp3\",\"times\":1,\"wordList\":[]}",
//				"{\"cnUrl\":\"\",\"command\":104,\"content\":\"118,101[10]<120>\",\"expUrl\":\"\",\"id\":25,\"mcTag\":\"1517385472208\",\"mediaInfo\":\"http://source.fandoutech.com.cn/1516697326850.xls\",\"name\":\"跟读(共1句,时长:6秒)资源:DSN14晚会时间\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://xxx/xx.jpg\",\"recordTimeStr\":\"05:32\",\"resId\":\"4609\",\"resName\":\"DSN14晚会时间\",\"resourceId\":-1,\"srcUrl\":\"http://source.fandoutech.com.cn/1516545968737.mp3\",\"times\":1,\"wordList\":[]}",
//				"{\"cnUrl\":\"\",\"command\":106,\"content\":\"119,110,111<80>\",\"expUrl\":\"\",\"id\":27,\"mcTag\":\"\",\"mediaInfo\":\"http://source.fandoutech.com.cn/1516697326850.xls\",\"name\":\"播放(共1句,时长:1秒)资源:DSN14晚会时间\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://xxx/xx.jpg\",\"recordTimeStr\":\"05:59:32\",\"resId\":\"4609\",\"resName\":\"DSN14晚会时间\",\"resourceId\":-1,\"srcUrl\":\"http://source.fandoutech.com.cn/1516545968737.mp4\",\"times\":1,\"wordList\":[]}",
//				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"the ducklings.[01]\",\"conumeTime\":1112,\"expUrl\":\"\",\"id\":37,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(the ducklings.)\",\"option\":[{\"action\":\"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_11_14_58_42.mp3\",\"condition\":\"80\"},{\"action\":\"http://word.fandoutech.com.cn/FandouPalRecord-2018_01_11_14_59_03.mp3\",\"condition\":\"50\"}],\"picLib\":\"\",\"picUrl\":\"http://xxx/xx.jpg\",\"recordTimeStr\":\"08:01\",\"resName\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}"
//		};
//		
////		String[] srcData = {
////				"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":0,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/003d3a5949fbe28481ab4e9199d9afa6.jpg\",\"recordTimeStr\":\"00:03\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2017_12_04_17_46_07.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/cn/10004.mp3\",\"command\":106,\"content\":\"4<100>\",\"expUrl\":\"\",\"id\":1,\"mcTag\":\"\",\"mediaInfo\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/10004.xls\",\"name\":\"播放(共1句,时长:3秒) her hair is as black as ebony\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/11320344a82cf5a6d47254af154f4c57.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"resId\":\"10004\",\"srcUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/src/10004.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":2,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/00853ff6c1081c46d25a25bb978e0d84.jpg\",\"recordTimeStr\":\"00:02\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2017_12_04_17_46_22.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/cn/10004.mp3\",\"command\":104,\"content\":\"5,6,7,8[00]<100>\",\"expUrl\":\"\",\"id\":3,\"mcTag\":\"1512380792964\",\"mediaInfo\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/10004.xls\",\"name\":\"跟读(共1句,时长:3秒) her lips are as red as blood\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/11320344a82cf5a6d47254af154f4c57.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"resId\":\"10004\",\"srcUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/src/10004.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":4,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/008c5881c4fe21dd2ccfa2a0073f26b1.jpg\",\"recordTimeStr\":\"00:02\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2017_12_04_17_46_37.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"she has a magic mirror.\n[00]\",\"expUrl\":\"\",\"id\":5,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(she has a magic mirror.\n)\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/013c8c76e9036c83ede88deb0c400cdf.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":6,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/0132ecb5f3332a9adc1e50cad80643dd.jpg\",\"recordTimeStr\":\"00:02\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2017_12_04_17_46_59.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"run away princess.\n[00]\",\"expUrl\":\"\",\"id\":7,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(run away princess.\n)\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/07698d46648b8f07d2aa9c9357e11984.png\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/cn/10004.mp3\",\"command\":106,\"content\":\"6<100>\",\"expUrl\":\"\",\"id\":8,\"mcTag\":\"\",\"mediaInfo\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/10004.xls\",\"name\":\"播放(共1句,时长:3秒) her skin is as white as snow\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"resId\":\"10004\",\"srcUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/src/10004.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"snow white runs and runs.\n[00]\",\"expUrl\":\"\",\"id\":9,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(snow white runs and runs.\n)\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/0c515f2a92d2cf011bfdf7da6ba2a0ca.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/cn/10004.mp3\",\"command\":104,\"content\":\"7[00]<100>\",\"expUrl\":\"\",\"id\":10,\"mcTag\":\"1512380870474\",\"mediaInfo\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/10004.xls\",\"name\":\"跟读(共1句,时长:3秒) so she is called snow white\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"resId\":\"10004\",\"srcUrl\":\"http://source.fandoutech.com.cn/wechat/wechatImages/lesson/src/10004.mp3\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"she is lost in the forest.\n[00]\",\"expUrl\":\"\",\"id\":11,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(she is lost in the forest.\n)\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/0dff52d8f2920f3449d81cbea50b0cfb.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":108,\"content\":\"she goes into the house.\n[00]\",\"expUrl\":\"\",\"id\":12,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"语句(she goes into the house.\n)\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"http://words.fandoutech.com.cn/11320344a82cf5a6d47254af154f4c57.jpg\",\"recordTimeStr\":\"\",\"resourceId\":-1,\"srcUrl\":\"\",\"times\":1,\"wordList\":[]}",
////				"{\"cnUrl\":\"\",\"command\":102,\"content\":\"\",\"expUrl\":\"\",\"id\":0,\"mcTag\":\"\",\"mediaInfo\":\"\",\"name\":\"录音\",\"option\":[],\"picLib\":\"\",\"picUrl\":\"\",\"recordTimeStr\":\"00:02\",\"resourceId\":-1,\"srcUrl\":\"http://word.fandoutech.com.cn/FandouPalRecord-2017_12_04_17_48_17.mp3\",\"times\":1,\"wordList\":[]}",
////		};
//		List<String> newDatas = new ArrayList<String>();
//		for (String old : srcData) {
//			String newData = parseToNewScript(old);
//			//System.out.println(newData);
//			newDatas.add(newData);
//		}
//		//System.out.println("============================");
//		
//		List<String> oldDatas = new ArrayList<String>();
//		for (String newData : newDatas) {
//			String oldData = parseToOldScript(newData);
//			//System.out.println(oldData);
//			oldDatas.add(oldData);
//		}
//		
//		//System.out.println("============================");
//	}
	
	/**
	 * 把旧指令数据转成新的数据格式
	 * */
	public static String parseToNewScript(String oldScript) {
		String newScript = null;
		
		Gson gson = new Gson();
		KHMissionCmd oldMissionCmd = null;
		try {
			oldMissionCmd = gson.fromJson(oldScript, KHMissionCmd.class);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
		List<Command> commands = ScriptToNewParser.getInstance().parse(oldMissionCmd);//转换数据
		if (commands != null) {
			Type type = new TypeToken<List<Command>>() {}.getType();
			newScript = gson.toJson(commands, type);
			if (newScript != null) {
				newScript = CodeManager.getInstance().unicodeToString(newScript);
			}
		}
		
		return newScript;
	}
	
	/**
	 * 把新指令数据转成旧的数据格式
	 * */
	public static String parseToOldScript(String newScript) {
		String oldScript = null;
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<Command>>() {}.getType();
		
		List<Command> commands = null;
		try {
			commands = gson.fromJson(newScript, type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		
		KHMissionCmd oldMissionCmd = ScriptToOldParser.getInstance().parse(commands);//转换数据
		if (oldMissionCmd != null) {
			oldScript = gson.toJson(oldMissionCmd, KHMissionCmd.class);
			if (oldScript != null) {
				oldScript = CodeManager.getInstance().unicodeToString(oldScript);
			}
		}
		
		return oldScript;
	}

}
