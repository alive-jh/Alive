package com.wechat.service;

import org.apache.poi.ss.formula.functions.T;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


//redis 的操作开放接口
public interface RedisService {
	 /**
     * 通过key删除
     * 
     * @param key
     */
    public abstract Long del(String keys);



    /**
     * 添加key value 并且设置存活时间
     * 
     * @param key
     * @param value
     * @param liveTime
     *            单位秒
     */
    public abstract String set(String key, String value, Integer liveTime);

    /**
     * 添加key value
     * 
     * @param key
     * @param value
     */
    public abstract void set(String key, String value);


    public abstract void select(int db);
    
    
    public abstract Set<String> keys(String paratemer);

    /**
     * 获取redis value (String)
     * 
     * @param key
     * @return
     */
    public abstract String get(String key);



    /**
     * 检查key是否已经存在
     * 
     * @param key
     * @return
     */
    public abstract boolean exists(String key);

   
    
    /**
     * 添加对象
     * @param key
     * @param value
     */
    public abstract void setObject(String key, Object value);
    
    /**
     * 添加对象,设置有效时间
     * @param key
     * @param value
     * @param liveTime
     */
    public abstract void setObject(String key, Object value, Integer liveTime);
    
   
    /**
     * 获取对象
     * @param key
     * @param clazz
     * @return
     */
    public abstract Object getObject(String key, Class clazz);
    
    

    
    /**
     * 添加集合
     * @param key
     * @param value
     */
    public abstract void setList(String key, List value);
    
    /**
     * 添加集合,设置有效时间
     * @param key
     * @param value
     * @param liveTime
     */
    public abstract void setList(String key, List value, Integer liveTime);
    
    /**
     * 
     * 获取redis集合
     * @param key
     * @param clazz
     * @return
     */
    public abstract List<T> getList(String key, Class clazz);
    
    
    
    public  Long zadd(String key, double score, String member); 
    
    public Long hset(String key, String field, String value);
    
    public LinkedHashSet<String> zrevrangeByScore(String key, double max, double min);
    
    public List<String> hmget(String key, String... fields);


	public abstract void lpush(String key, String soundName);
	
	
	
    /**\
     * 添加队列内容
     * @param key
     * @param soundName
     */
	public abstract void pushTask(String key, String source);
	
	
	
	/**
	 * 计数器
	 * @param key
	 * @param soundName
	 */
	public abstract boolean incr(String key);

	
	/**
	 * 获取队列内容
	 * @param key
	 * @param soundName
	 */
	public abstract String popTask(String key);
	

    
}
