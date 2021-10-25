package com.wechat.dao;

import redis.clients.jedis.ShardedJedis;

public interface RedisDataSource {

	/**
	 * 取得redis的客户端
	 * @return
	 */
	public abstract ShardedJedis getRedisClient();//取得redis的客户端
	/**
	 * 将资源返还给pool
	 * @param shardedJedis
	 */
    public void returnResource(ShardedJedis shardedJedis);//将资源返还给pool
    /**
     * 出现异常后，将资源返还给pool
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis, boolean broken);//出现异常后，将资源返还给pool
}
