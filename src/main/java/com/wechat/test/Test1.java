package com.wechat.test;

import com.wechat.entity.ClassScriptType;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;

public class Test1 {
	
	 public static void main(String[] args) throws Exception {  
		   
	        
	       /* 
	        * 实列化类 方法1 
	        */  
	       //String classPath = "com.whbs.bean.UserBean";  
	       //Class cla = Test1.class.getClassLoader().loadClass(classPath);  
	       //Object ob = cla.newInstance();  
	        
	       /* 
	        * 实列化类 方法2 
	        */  
	       ClassScriptType bean = new ClassScriptType();  
	       bean.setId(13);  
	       bean.setName("导读");  
	       bean.setCreateTime(new Timestamp(System.currentTimeMillis()));
	        
	       //得到类对象  
	       Class userCla = bean.getClass();  
	        
	       /* 
	        * 得到类中的所有属性集合 
	        */  
	       Field[] fs = userCla.getDeclaredFields();  
	       for(int i = 0 ; i < fs.length; i++){  
	           Field f = fs[i];  
	           f.setAccessible(true); //设置些属性是可以访问的  
	           Object val = f.get(bean);//得到此属性的值     
	           
	           //System.out.println("name:"+f.getName()+"\t value = "+val);  
	            
	           String type = f.getType().toString();//得到此属性的类型  
	           if (type.endsWith("String")) {  
	              //System.out.println(f.getType()+"\t是String");  
	              f.set(bean,"12") ;        //给属性设值  
	           }else if(type.endsWith("int") || type.endsWith("Integer")){  
	              //System.out.println(f.getType()+"\t是int");  
	              f.set(bean,12) ;       //给属性设值  
	           }else{  
	              //System.out.println(f.getType()+"\t");  
	           }  
	            
	       }  
	        
	        
	       /* 
	        * 得到类中的方法 
	        */  
	       Method[] methods = userCla.getMethods();  
	       for(int i = 0; i < methods.length; i++){  
	           Method method = methods[i];  
	           if(method.getName().startsWith("get")){  
	              //System.out.print("methodName:"+method.getName()+"\t");  
	              //System.out.println("value:"+method.invoke(bean));//得到get 方法的值  
	           }  
	       }  
	    }

	private static Class<T> testClass(Class<? extends T> class1) {
		
		return (Class<T>) class1;
	}  
}
