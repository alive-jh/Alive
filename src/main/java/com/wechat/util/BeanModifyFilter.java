package com.wechat.util;

import java.lang.reflect.Field;

public class BeanModifyFilter {

	/**
	 * 
	 * @param objA 输入对象
	 * @param objB 对比对象
	 * @return
	 * @throws Exception
	 */
	public static Object modifyFilter(Object objA, Object objB)
			throws Exception {
		// 得到类对象
		Class objACla = objA.getClass();
		Class objBCla = objB.getClass();
		Field[] fsA = objACla.getDeclaredFields();
		Field[] fsB = objBCla.getDeclaredFields();
		for (int i = 0; i < fsA.length; i++) {
			Field fA = fsA[i];
			Field fB = fsB[i];
			fA.setAccessible(true); // 设置些属性是可以访问的
			fB.setAccessible(true); // 设置些属性是可以访问的
			Object valA = fA.get(objA);// 得到此属性的值
			String typeA = fA.getType().toString();// 得到此属性的类型
			Object valB = fB.get(objB);// 得到此属性的值
			String typeB = fB.getType().toString();// 得到此属性的类型
			////System.out.println("nameA:" + fA.getName() + "\t value = " + valA+"\t type = " + typeA);
			////System.out.println("nameB:" + fB.getName() + "\t value = " + valB+"\t type = " + typeB);
			//如果属性为null则表示保持原值
			if(valA==null){
				fA.set(objA, valB);
			}
		}
		return objA;
	}

}
