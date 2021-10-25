package com.wechat.util;

import java.util.Random;
import java.util.UUID;

public class UUIDGenerator {  
	
	static String[] data = new String[]{"o","2","n","3","w","1","t","5","8","7","a","b","6","i","9","e"};  
	
    public UUIDGenerator() {  
    	
    }  
 
    public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }  
    //获得指定数量的UUID  
    public static String[] getUUID(int number) {  
        if (number < 1) {  
            return null;  
        }  
        String[] ss = new String[number];  
        for (int i = 0; i < number; i++) {  
            ss[i] = getUUID();  
        }  
        return ss;  
    }  
 
    public static String[] getDeviceInfo(String time,String pc ,String num) {  
    	
        
        String deviceNo=time+pc+num;
        String epalId="";
        for (int i = 2; i < deviceNo.length(); i++) {
        		String str=String.valueOf(deviceNo.charAt(i));
        		epalId+=data[Integer.parseInt(str)];
		}
        //System.out.println("deviceNo:"+deviceNo);
        //System.out.println("epalId:"+epalId);
        String epalPwd="";
        for (int i = 0; i < 8 ; i++) {
    		String str=String.valueOf(deviceNo.charAt(i));
    		if(i>1){
    			Random rand = new Random();
    			int randNum = rand.nextInt(10);
    			epalPwd+=data[randNum];
    		}else{
    			epalPwd+=data[Integer.parseInt(str)];
    		}
    		
        }
        //System.out.println("epalPwd:"+epalPwd);
        
        String[] deviceInfo=new String[]{deviceNo,epalId,epalPwd};
        
        return deviceInfo;
    }  
    
    public static String  getDeviceNo(String epalId,String epalPwd){
    	String deviceNo="";
    	String strEpalId="";
    	String strEpalPwd="";
    	for (int i = 0; i < epalId.length(); i++) {
    		String str=String.valueOf(epalId.charAt(i));
    		for (int j = 0; j < data.length; j++) {
				if(str.equals(data[j])){
					strEpalId=strEpalId+j;
				}
			}
		}
    	
    	for (int i = 0; i < epalPwd.length(); i++) {
    		String str=String.valueOf(epalPwd.charAt(i));
    		for (int j = 0; j < data.length; j++) {
				if(str.equals(data[j])){
					strEpalPwd=strEpalPwd+j;
				}
			}
		}
    	
    	deviceNo=strEpalPwd.substring(0, 2)+strEpalId;
    	
    	return deviceNo;
    }
    
    
    public static void main(String[] args) {
    	String time="16062916";
        String pc="1";
        String num="0001";
        getDeviceInfo(time, pc, num);
    	//1606291610001
    	//System.out.println(getDeviceNo("otn72t2ooo2", "2t2tt722"));
    	
	}
}  
