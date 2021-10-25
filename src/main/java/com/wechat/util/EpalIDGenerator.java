package com.wechat.util;

import java.util.Random;

public class EpalIDGenerator {

	static String[] data = new String[] { "o", "2", "n", "3", "w", "1", "t",
			"5", "8", "7", "a", "b", "6", "i", "9", "e" };

	public EpalIDGenerator() {

	}

	
	public static String[] getDeviceInfo(String deviceNo) {

		String epalId = "";
		for (int i = 2; i < deviceNo.length(); i++) {
			String str = String.valueOf(deviceNo.charAt(i));
			epalId += data[Integer.parseInt(str)];
		}
		//System.out.println("deviceNo:" + deviceNo);
		//System.out.println("epalId:" + epalId);
		String epalPwd = "";
		for (int i = 0; i < 8; i++) {
			String str = String.valueOf(deviceNo.charAt(i));
			if (i > 1) {
				Random rand = new Random();
				int randNum = rand.nextInt(10);
				epalPwd += data[randNum];
			} else {
				epalPwd += data[Integer.parseInt(str)];
			}

		}
		//System.out.println("epalPwd:" + epalPwd);

		String[] deviceInfo = new String[] { deviceNo, epalId, epalPwd };

		return deviceInfo;
	}

	public static String getEpalId(String deviceNo) {

		String epalId = "";
		for (int i = 2; i < deviceNo.length(); i++) {
			String str = String.valueOf(deviceNo.charAt(i));
			epalId += data[Integer.parseInt(str)];
		}
		//System.out.println("deviceNo:" + deviceNo);
		//System.out.println("epalId:" + epalId);

		return epalId;
	}

	/**
	 * 
	 * @param time 时间 8
	 * @param pc   批次 2
	 * @param num  序列号 3
	 * @return
	 */
	public static String getDeviceNo(String epalId, String epalPwd) {
		String deviceNo = "";
		String strEpalId = "";
		String strEpalPwd = "";
		for (int i = 0; i < epalId.length(); i++) {
			String str = String.valueOf(epalId.charAt(i));
			for (int j = 0; j < data.length; j++) {
				if (str.equals(data[j])) {
					strEpalId = strEpalId + j;
				}
			}
		}

		for (int i = 0; i < epalPwd.length(); i++) {
			String str = String.valueOf(epalPwd.charAt(i));
			for (int j = 0; j < data.length; j++) {
				if (str.equals(data[j])) {
					strEpalPwd = strEpalPwd + j;
				}
			}
		}

		deviceNo = strEpalPwd.substring(0, 2)
				+ strEpalId;

		return deviceNo;
	}

	public static void main(String[] args) {
		getDeviceInfo("16081117001001");
		getDeviceInfo("16081117001002");
		getDeviceInfo("16081117001003");
		//System.out.println(getDeviceNo("o5222t2ooo2", "2t2t8w5n"));
	}
}
