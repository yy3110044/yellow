package com.yy.yellow.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 使用redis实现的缓存
 * @author yy
 *
 */
@Component("cacheToRedis")
public class CacheToRedis implements Cache {
	@Autowired
	private ShardedJedisPool shardedJedisPool;

	@Override
	public void set(CacheKeyPre pre, String key, String value) {
		key = pre + ":" + key;
		try(ShardedJedis jedis = this.shardedJedisPool.getResource()) {
			jedis.set(key, value);
		}
	}

	@Override
	public void set(CacheKeyPre pre, String key, String value, int seconds) {
		key = pre + ":" + key;
		try(ShardedJedis jedis = this.shardedJedisPool.getResource()) {
			jedis.set(key, value);
			jedis.expire(key, seconds);
		}
	}

	@Override
	public String getString(CacheKeyPre pre, String key) {
		key = pre + ":" + key;
		try(ShardedJedis jedis = this.shardedJedisPool.getResource()) {
			return jedis.get(key);
		}
	}

	@Override
	public Integer getInt(CacheKeyPre pre, String key) {
		String value = getString(pre, key);
		if(value != null) {
			return Integer.valueOf(value);
		} else {
			return null;
		}
	}

	@Override
	public Double getDouble(CacheKeyPre pre, String key) {
		String value = getString(pre, key);
		if(value != null) {
			return Double.valueOf(value);
		} else {
			return null;
		}
	}

	@Override
	public String remove(CacheKeyPre pre, String key) {
		key = pre + ":" + key;
		try(ShardedJedis jedis = this.shardedJedisPool.getResource()) {
			String retVal = jedis.get(key);
			jedis.del(key);
			return retVal;
		}
	}
}