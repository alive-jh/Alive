package com.wechat.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class CommonUtils {

	protected static final char[] charArray = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();

	/**
	 * 生成指定长度额随即字符串
	 * 
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static String getRandomString(int length) {
		// 随机字符串的随机字符库
		String KeyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	/**
	 * 计算两个时间相差几个月
	 * 
	 * @param endTimeMillis
	 *            结束时间
	 * @param startTimeMillis
	 *            开始时间
	 * @return int months 几个月
	 */
	public static int getMonths(long endTimeMillis, long startTimeMillis) {
		Calendar end = Calendar.getInstance();
		end.setTimeInMillis(endTimeMillis);
		Calendar start = Calendar.getInstance();
		start.setTimeInMillis(startTimeMillis);
		return ((end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12)
				+ (end.get(Calendar.MONTH) - start.get(Calendar.MONTH));
	}

	/**
	 * 计算两个时间相差几年
	 * 
	 * @param endTimeMillis
	 * @param startTimeMillis
	 * @return int year 几年
	 */
	public static int getYears(long endTimeMillis, long startTimeMillis) {
		return getMonths(endTimeMillis, startTimeMillis) / 12;
	}

	/**
	 * 计算两个时间相差几天
	 * 
	 * @param endTimeMillis
	 * @param startTimeMillis
	 * @return int days 几天
	 */
	public static int getDays(long endTimeMillis, long startTimeMillis) {
		long millisOfDay = 86400000L;// 一天的固定毫秒值
		return (int) ((endTimeMillis - startTimeMillis) / millisOfDay);
	}

	/**
	 * 计算当前日期是月份的第几天
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static int getDayOfMomth(long timeMillis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeMillis);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 检查是不是手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean checkMobile(String phone) {

		boolean result = false;

		if (phone == null) {
			return result;
		}

		String regex = "^[1][345897][0-9]{9}$";
		if (phone.length() != 11) {

		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(phone);
			result = m.matches();
		}

		return result;
	}

	public static void main(String[] args) {
		// System.out.println(checkMobile("15625732709"));
	}

	public static String getRandomNumber(int length) {
		// 随机字符串的随机字符库
		String KeyString = "123456789";
		StringBuffer sb = new StringBuffer();
		int len = KeyString.length();
		for (int i = 0; i < length; i++) {
			sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
		}
		return sb.toString();
	}

	public static String getRandomString() {
		char[] randomChars = new char[4];
		for (int i = 0; i < randomChars.length; i++) {
			randomChars[i] = charArray[new Random(System.nanoTime()).nextInt(charArray.length)];
		}
		return String.valueOf(randomChars);
	}

	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * 
	 * @return String
	 */
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandomNumber(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	public static JSONArray ClassScriptTypeSort(JSONArray jsonArr) {
		
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArr.size(); i++) {
			jsonValues.add(jsonArr.getJSONObject(i));
		}
		
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			// You can change "Name" with "ID" if you want to sort by ID
			private static final String KEY_NAME = "sort";

			@Override
			public int compare(JSONObject a, JSONObject b) {
				Integer valA = new Integer( a.getJSONArray("typeList").getJSONObject(0).getInt(KEY_NAME));
				Integer valB = new Integer( b.getJSONArray("typeList").getJSONObject(0).getInt(KEY_NAME));
				return valA.compareTo(valB);
				// if you want to change the sort order, simply use the following:
				// return -valA.compareTo(valB);
			}
		});
		
		return JSONArray.fromObject(jsonValues);
	}
	
	


}
