package com.yy.yellow.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 使用redis实现的缓存
 * @author yy
 *
 */
@Component("redisCache")
public class RedisCache implements Cache {
	@Autowired
	private JedisPool jedisPool;

	@Override
	public void set(CacheKeyPre pre, String key, String value) {
		key = pre.name() + ":" + key;
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, value);
		}
	}

	@Override
	public void set(CacheKeyPre pre, String key, String value, int seconds) {
		key = pre.name() + ":" + key;
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.set(key, value);
			jedis.expire(key, seconds);
		}
	}

	@Override
	public String getString(CacheKeyPre pre, String key) {
		key = pre.name() + ":" + key;
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.get(key);
		}
	}

	@Override
	public String delete(CacheKeyPre pre, String key) {
		key = pre.name() + ":" + key;
		try(Jedis jedis = jedisPool.getResource()) {
			String value = jedis.get(key);
			jedis.del(key);
			return value;
		}
	}

	@Override
	public void clear() {
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.flushAll();
		}
	}
}