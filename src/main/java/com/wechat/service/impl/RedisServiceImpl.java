package com.wechat.service.impl;

import com.wechat.dao.RedisDataSource;
import com.wechat.service.RedisService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



@Service("redisService")
public  class RedisServiceImpl implements RedisService {
	
	
	
	 @Resource
	 private RedisDataSource     redisDataSource;
	 
	 
	 public RedisDataSource getRedisDataSource() {
		return redisDataSource;
	}


	public void setRedisDataSource(RedisDataSource redisDataSource) {
		this.redisDataSource = redisDataSource;
	}


	public void disconnect() {
	        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	        shardedJedis.disconnect();
	    }
	 
	 	
	    /**
	     * @param key
	     */
	    @Override
		public Long del(final String keys) {
	        
	    	Long result = null;  
	        ShardedJedis shardedJedis = redisDataSource.getRedisClient();  
	        if (shardedJedis == null) {  
	            return result;  
	        }  
	        boolean broken = false;  
	        try {  
	            result = shardedJedis.del(keys);  
	  
	        } catch (Exception e) {  
	           
	            broken = true;  
	        } finally {  
	        	redisDataSource.returnResource(shardedJedis, broken);  
	        }  
	        return result;  
	    
	    }

	    /**
	     * @param key
	     * @param value
	     * @param liveTime
	     */
	    @Override
		public String set(final String key, final String value, final Integer liveTime) {
	    	
	    	String result = null;
	    	boolean broken = false;
	    	ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	    	if (shardedJedis == null) {
	    		return result;
	        }
	    	try {
	    		
	    		result = shardedJedis.set(key, value);
	            if (liveTime > 0) {

	            	
	            	Long testResult = shardedJedis.expire(key,  liveTime); 
	            	return testResult+"";
		    	}
	        } catch (Exception e) {
	        	//System.out.println(" error = "+e.getMessage());
	            broken = true;
	        } finally {
	            redisDataSource.returnResource(shardedJedis, broken);
	        }
	    	return result;
	    	
	    	
	    	
	    	
	       
	    }



	    /**
	     * @param key
	     * @param value
	     */
	    @Override
		public void set(String key, String value) {
	    	//System.out.println(key+value);
	        this.set(key, value, 0);
	    }



	    /**
	     * @param key
	     * @return
	     */
	    @Override
		public String get(final String key) {
	    	
	    	 String result = "";
	         ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	         if (shardedJedis == null) {
	             return result;
	         }

	         boolean broken = false;
	         try {
	             result = shardedJedis.get(key);

	         } catch (Exception e) {
	             
	             broken = true;
	         } finally {
	             redisDataSource.returnResource(shardedJedis, broken);
	         }
	         return result;
	         
	    }



	    /**
	     * @param key
	     * @return
	     */
	    @Override
		public boolean exists(final String key) {

	    	
	    	boolean result = false;
	    	boolean broken = false;
	    	ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	    	 if (shardedJedis == null) {
		            return result;
		        }
	    	try {
	            result = shardedJedis.exists(key);
	           
	        } catch (Exception e) {
	           
	            broken = true;
	        } finally {
	            redisDataSource.returnResource(shardedJedis, broken);
	        }
	    	
	    	return result;
	    	
	    	
	    }

	    RedisServiceImpl() {

	    }

	    
	    @Override
		public void setObject(String key, Object value) {
			
			this.setObject(key, value, 0);
			
		}


		@Override
		public void setObject(String key, Object value,Integer liveTime) {
			
			JSONObject json = JSONObject.fromObject(value);//将java对象转换为json对象
			String str = json.toString();
			
			 if (liveTime > 0) {
                
                 this.set(key, JSONObject.fromObject(value).toString(),liveTime);
             }
			 else
			 {
				 this.set(key, JSONObject.fromObject(value).toString());
			 }
			
		}

		

		@Override
		public Object getObject(String key, Class clazz) {
			String result = this.get(key);
			if(!"".equals(result) && null != result){  //返回的结果不为空字符串
		
			}else{
				result = "{}";
			}
			new JSONObject();
			JSONObject obj = JSONObject.fromObject(result);//将json字符串转换为json对象  
			return JSONObject.toBean(obj,clazz);//将建json对象转换为Person对象  
		}



		@Override
		public void setList(String key, List value) {
			
			this.setList(key, value,0);
		}

		@Override
		public void setList(String key, List value, Integer liveTime) {
			
			JSONArray jsonarray = JSONArray.fromObject(value);  
			this.set(key, jsonarray.toString(), liveTime);
		}

		@Override
		public List<T> getList(String key, Class clazz) {
			
			JSONArray jsonArray = JSONArray.fromObject(this.get(key));  
			return (List<T>) JSONArray.toCollection(jsonArray,  clazz);
		}



		


		@Override
		public Long zadd(String key, double score, String member) {
	       
			Long result = null;
	        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
	        if (shardedJedis == null) {
	            return result;
	        }
	        boolean broken = false;
	        try {
	        	result = shardedJedis.zadd(key, score, member);
	        } catch (Exception e) {
	            
	            broken = true;
	        } finally {
	            redisDataSource.returnResource(shardedJedis, broken);
	        }
	        return result;
	       
	    }


		

	    
		  @Override
		public LinkedHashSet<String> zrevrangeByScore(String key, double max, double min) {
			  LinkedHashSet<String> result = null;
		        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		        if (shardedJedis == null) {
		            return result;
		        }
		        boolean broken = false;
		        try {

		            result = (LinkedHashSet<String>) shardedJedis.zrevrangeByScore(key, max, min);

		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
		        return result;
		    }

	    
		    @Override
			public List<String> hmget(String key, String... fields) {
		        List<String> result = null;
		        ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		        if (shardedJedis == null) {
		            return result;
		        }
		        boolean broken = false;
		        try {
		            result = shardedJedis.hmget(key, fields);
		            

		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
		        return result;
		    }


			@Override
			public Long hset(String key, String field, String value) {
				
				Long result = null;
				ShardedJedis shardedJedis = redisDataSource.getRedisClient();
				 if (shardedJedis == null) {
			            return result;
			        }
		        boolean broken = false;
		        try {
		              shardedJedis.hset(key, field, value);
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
		        return result;
				
			}

			@Override
			public void lpush(String key, String value) {

				ShardedJedis shardedJedis = redisDataSource.getRedisClient();

		        boolean broken = false;
		        try {
		              shardedJedis.lpush(key, value);
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
				
			}
			
			

			@Override
			public void pushTask(String key,String source) {

				ShardedJedis shardedJedis = redisDataSource.getRedisClient();

		        boolean broken = false;
		        try {
		              shardedJedis.lpush(key,source);
		              
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
				
			}


			@Override
			public String popTask(String key) {
				
				ShardedJedis shardedJedis = redisDataSource.getRedisClient();
				String task = "";
		        boolean broken = false;
		        try {
		             
		        	task = shardedJedis.rpop(key);
		        	
		        	
		              
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
		        return task;
				
			}


			@Override
			public void select(int db) {
				// TODO Auto-generated method stub
				ShardedJedis shardedJedis = redisDataSource.getRedisClient();

		        boolean broken = false;
		        try {
		              ((RedisService) shardedJedis).select(db);
		              
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
			}


			@Override
			public Set<String> keys(String paratemer) {
				// TODO Auto-generated method stub
				ShardedJedis shardedJedis = redisDataSource.getRedisClient();
				Set<String> result = null;
		        boolean broken = false;
		        //System.out.println(paratemer);
		        try {
		        	result = ((RedisService) shardedJedis).keys(paratemer);
		              
		        } catch (Exception e) {
		            
		            broken = true;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
		        return result;
			}


			@Override
			public boolean incr(String key) {
				// TODO Auto-generated method stub
				ShardedJedis shardedJedis = redisDataSource.getRedisClient();
		        boolean broken = false;
		        boolean result = true;
		        try {
		        	if(shardedJedis.get(key) != null){
		        		
		        	}else{
		        		shardedJedis.set(key,"2563");
		        	}
		        	shardedJedis.incr(key);
		              
		        } catch (Exception e) {
		            
		            broken = true;
		            result = false;
		        } finally {
		            redisDataSource.returnResource(shardedJedis, broken);
		        }
				return result;
			}

}
