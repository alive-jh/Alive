package com.wechat.mp3;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wechat.util.Keys;

public class Main {
	public static void main(String[] args) {
		final String url = "https://wechat.fandoutech.com.cn/wechat/wechatImages/lesson/mediainfo/10001.xls";
		new Thread() {
			@Override
			public void run() {
				boolean success = HttpUtil.downLoadFromUrl(url,"mediainfo.xls", Keys.USER_PIC_PATH);
				if (success)
					System.out.println(ExcelInfo2Json(Keys.USER_PIC_PATH+"mediainfo.xls"));
				else
					System.out.println("文件下载失败！");
			};
		}.start();
	}

	/**
	 * 
	 * @param path
	 *            http:/.../��ѩ����/mediainfo.xls,/mediainfo.mls�̶����ļ���ַ
	 * @return
	 */
	public static String ExcelInfo2Json(String path) {
		MediaInfo info = ExcelParser.getInstance().getMediaInfo(path);

		if (info != null) {
			Gson gson = new GsonBuilder().setExclusionStrategies(
					new ExclusionStrategy() {

						@Override
						public boolean shouldSkipClass(Class<?> arg0) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean shouldSkipField(FieldAttributes f) {
							// TODO Auto-generated method stub
							return f.getName().contains("longSentenceList")
									| f.getName().contains("pageInfo")
									| f.getName().contains("isExpExist");
						}

					}).create();
			return gson.toJson(info);
		}
		return "";
	}
}
