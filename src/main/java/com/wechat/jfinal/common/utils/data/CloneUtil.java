package com.wechat.jfinal.common.utils.data;

import java.io.*;

public class CloneUtil {

	/**
	 * 序列化深度克隆数据
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T model) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(model);
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Object obj = ois.readObject();
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}