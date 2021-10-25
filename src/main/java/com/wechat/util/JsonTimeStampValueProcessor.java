package com.wechat.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class JsonTimeStampValueProcessor implements JsonValueProcessor {  
    private String format ="yyyy-MM-dd HH:mm:ss";  
      
    public JsonTimeStampValueProcessor() {  
        super();  
    }  
      
    public JsonTimeStampValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override
	public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
  
    @Override
	public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
      
      
    private Object process(Object value){  
        if(value instanceof Timestamp){    
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }  
  
}  
