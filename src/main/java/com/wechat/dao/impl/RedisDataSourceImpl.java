package com.wechat.dao.impl;

import com.wechat.dao.RedisDataSource;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;


@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource{

	
	@Resource
    private ShardedJedisPool    shardedJedisPool;

    @Override
	public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            //System.out.println("getRedisClent error :"+e);
        }
        return null;
    }

    @Override
	public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    @Override
	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

}
