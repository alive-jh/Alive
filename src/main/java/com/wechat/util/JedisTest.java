package com.wechat.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import com.sun.istack.internal.logging.Logger;
  
/** 
 * Created by IntelliJ IDEA. 
 * User: lifeng.xu 
 * Date: 12-6-11 
 * Time: 上午11:10 
 * To change this template use File | Settings | File Templates. 
 */  
public class JedisTest {  
  
    //private static Logger logger = LoggerFactory.getLogger(JedisTest.class);  
  
    /** 
     * Jedis Pool for Jedis Resource 
     * @return 
     */  
    public static JedisPool buildJedisPool(){  
        JedisPoolConfig config = new JedisPoolConfig();  
        //config.setMaxActive(1);  
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMinIdle(8);//设置最小空闲数
        config.setMaxWaitMillis(10000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        //Idle时进行连接扫描
        config.setTestWhileIdle(true);
        //表示idle object evitor两次扫描之间要sleep的毫秒数
        config.setTimeBetweenEvictionRunsMillis(30000);
        //表示idle object evitor每次扫描的最多的对象数
        config.setNumTestsPerEvictionRun(10);
        //表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        config.setMinEvictableIdleTimeMillis(60000);

        JedisPool pool = new JedisPool(config, "8.129.31.226");
        return pool;  
    }  
  
    /** 
     * Test Data 
     * @return 
     */  
    public static List<User> buildTestData(){  
        User a = new User();  
        a.setName("a");  
        User b = new User();  
        b.setName("b");  
        List<User> list = new ArrayList<User>();  
        list.add(a);  
        list.add(b);  
        return list;  
    }  
  
    /** 
     * Test for 
     */  
    public static void testSetElements(){  
        List<User> testData = buildTestData();  
        Jedis jedis = buildJedisPool().getResource();  
        String key = "testSetElements123";  
        jedis.set(key.getBytes(), ObjectsTranscoder.serialize(testData));  
  
        //验证  
        byte[] in = jedis.get(key.getBytes());  
        List<User> list = ObjectsTranscoder.deserialize(in);  
        for(User user : list){  
            //System.out.println("testSetElements user name is:" + user.getName());  
        }  
    }  
  
    public static void testSetEnsemble(){  
        List<User> testData = buildTestData();  
        Jedis jedis = buildJedisPool().getResource();  
        String key = "testSetEnsemble" + new Random(1000).nextInt();  
        jedis.set(key.getBytes(), ListTranscoder.serialize(testData));  
  
        //验证  
        byte[] in = jedis.get(key.getBytes());  
        List<User> list = (List<User>)ListTranscoder.deserialize(in);  
        for(User user : list){  
            //System.out.println("testSetEnsemble user name is:" + user.getName());  
        }  
    }  
  
    public static void main(String[] args) {  
        testSetElements();  
        testSetEnsemble();  
    }  
  
    public static void close(Closeable closeable) {  
        if (closeable != null) {  
            try {  
                closeable.close();  
            } catch (Exception e) { 
            	
                ////System.out.println("Unable to close "+closeable, e);  
            }  
        }  
    }  
  
    static class User implements Serializable{  
        String name;  
  
        public String getName() {  
            return name;  
        }  
  
        public void setName(String name) {  
            this.name = name;  
        }  
    }  
  
    static class ObjectsTranscoder{  
          
        public static byte[] serialize(List<User> value) {  
            if (value == null) {  
                throw new NullPointerException("Can't serialize null");  
            }  
            byte[] rv=null;  
            ByteArrayOutputStream bos = null;  
            ObjectOutputStream os = null;  
            try {  
                bos = new ByteArrayOutputStream();  
                os = new ObjectOutputStream(bos);  
                for(User user : value){  
                    os.writeObject(user);  
                }  
                os.writeObject(null);  
                os.close();  
                bos.close();  
                rv = bos.toByteArray();  
            } catch (IOException e) {  
                throw new IllegalArgumentException("Non-serializable object", e);  
            } finally {  
                close(os);  
                close(bos);  
            }  
            return rv;  
        }  
  
        public static List<User> deserialize(byte[] in) {  
            List<User> list = new ArrayList<User>();  
            ByteArrayInputStream bis = null;  
            ObjectInputStream is = null;  
            try {  
                if(in != null) {  
                    bis=new ByteArrayInputStream(in);  
                    is=new ObjectInputStream(bis);  
                    while (true) {  
                        User user = (User) is.readObject();  
                        if(user == null){  
                            break;  
                        }else{  
                            list.add(user);  
                        }  
                    }  
                    is.close();  
                    bis.close();  
                }  
            } catch (IOException e) {  
                ////System.out.println("Caught IOException decoding %d bytes of data",  in == null ? 0 : in.length, e);  
            } catch (ClassNotFoundException e) {  
               // //System.out.println("Caught CNFE decoding %d bytes of data",  in == null ? 0 : in.length, e);  
            } finally {  
                close(is);  
                close(bis);  
            }  
            return list;  
        }  
    }  
      
    static class ListTranscoder{  
        public static byte[] serialize(Object value) {  
            if (value == null) {  
                throw new NullPointerException("Can't serialize null");  
            }  
            byte[] rv=null;  
            ByteArrayOutputStream bos = null;  
            ObjectOutputStream os = null;  
            try {  
                bos = new ByteArrayOutputStream();  
                os = new ObjectOutputStream(bos);  
                os.writeObject(value);  
                os.close();  
                bos.close();  
                rv = bos.toByteArray();  
            } catch (IOException e) {  
                throw new IllegalArgumentException("Non-serializable object", e);  
            } finally {  
                close(os);  
                close(bos);  
            }  
            return rv;  
        }  
  
        public static Object deserialize(byte[] in) {  
            Object rv=null;  
            ByteArrayInputStream bis = null;  
            ObjectInputStream is = null;  
            try {  
                if(in != null) {  
                    bis=new ByteArrayInputStream(in);  
                    is=new ObjectInputStream(bis);  
                    rv=is.readObject();  
                    is.close();  
                    bis.close();  
                }  
            } catch (IOException e) {  
               // //System.out.println("Caught IOException decoding %d bytes of data",  in == null ? 0 : in.length, e);  
            } catch (ClassNotFoundException e) {  
                ////System.out.println("Caught CNFE decoding %d bytes of data",  in == null ? 0 : in.length, e);  
            } finally {  
                close(is);  
                close(bis);  
            }  
            return rv;  
        }  
        public static void close(Closeable closeable) {  
            if (closeable != null) {  
                try {  
                    closeable.close();  
                } catch (Exception e) {  
                    //logger.info("Unable to close %s", closeable, e);  
                }  
            }  
        }  
    }  
} 