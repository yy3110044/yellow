package com.yy.yellow.util;

import java.util.HashMap;
import java.util.Map;
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
	public void set(CacheKey key, String name, String value) {
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.hmset(key.name(), getMap(name, value));
		}
	}

	@Override
	public void set(CacheKey key, String name, String value, int seconds) {
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.hmset(key.name(), getMap(name, value));
			jedis.expire(key.name(), seconds);
			有效时间有问题
		}
	}

	@Override
	public String getString(CacheKey key, String name) {
		try(Jedis jedis = jedisPool.getResource()) {
			return jedis.hget(key.name(), name);
		}
	}

	@Override
	public String delete(CacheKey key, String name) {
		try(Jedis jedis = jedisPool.getResource()) {
			String value = jedis.hget(key.name(), name);
			jedis.hdel(key.name(), name);
			return value;
		}
	}

	@Override
	public void clear() {
		try(Jedis jedis = jedisPool.getResource()) {
			jedis.flushAll();
		}
	}
	
	//根据一个key和一个value生成一个map
	private Map<String, String> getMap(String name, String value) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(name, value);
		return map;
	}
}