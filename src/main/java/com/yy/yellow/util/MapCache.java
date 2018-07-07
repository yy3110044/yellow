package com.yy.yellow.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 使用map实现的cache
 * @author yy
 *
 */
@Component("mapCache")
public class MapCache implements Cache {
	//定时任务线程池，用于清除已过期的缓存数据
	private ScheduledExecutorService service;
	
	//缓存map
	private Map<String, CacheObject> cacheMap;

	public MapCache(@Value("${web.config.cache.clearDelay:720}") final long clearDelay) {//从application.xml中读取清除时间间隔
		if(clearDelay < 1) {
			throw new RuntimeException("clearDelay必须大于零");
		}
		this.cacheMap = new ConcurrentHashMap<String, CacheObject>(); //一个线程安全的map
		this.service = Executors.newSingleThreadScheduledExecutor(); //一个单线程的任务池
		this.service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				cleanInvalidCache();
			}
		}, clearDelay, clearDelay, TimeUnit.MINUTES);
	}

	@Override
	public void set(CacheKeyPre pre, String key, String value) {
		key = pre + ":" + key;
		this.cacheMap.put(key, new CacheObject(key, value));
	}

	@Override
	public void set(CacheKeyPre pre, String key, String value, int seconds) {
		key = pre + ":" + key;
		this.cacheMap.put(key, new CacheObject(key, value, System.currentTimeMillis() + seconds * 1000));
	}

	@Override
	public String getString(CacheKeyPre pre, String key) {
		key = pre + ":" + key;
		CacheObject co = this.cacheMap.get(key);
		if(co != null) {
			if(co.expirationTime > System.currentTimeMillis()) { //未过期
				return co.value;
			} else { //已过期，移除对象，并返回null;
				this.cacheMap.remove(key);
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String delete(CacheKeyPre pre, String key) {
		key = pre + ":" + key;
		CacheObject co = this.cacheMap.remove(key);
		if(co != null) {
			return co.value;
		} else {
			return null;
		}
	}

	//清除无效缓存(已过期的)
	public void cleanInvalidCache() {
		List<CacheObject> coList = new ArrayList<CacheObject>(cacheMap.values());
		Collections.sort(coList);//排序
		long currentTimeMillis = System.currentTimeMillis();//当前时间
		for(CacheObject co : coList) {
			if(co.expirationTime <= currentTimeMillis) {
				cacheMap.remove(co.key); //已过期，移除缓数据
			} else {
				break;
			}
		}
	}
	
	private static final long foreverTime = 3471264000000L;
	
	//缓存对象
	public class CacheObject implements Comparable<CacheObject> {
		private String key; //key
		private String value; //缓存数据
		private long expirationTime; //到期时间

		CacheObject(String key, String value) {
			this(key, value, foreverTime);//到期时间，默认为2080-01-01表示为永久有
		}
		CacheObject(String key, String value, long expirationTime) {
			this.key = key;
			this.value = value;
			this.expirationTime = expirationTime;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public long getExpirationTime() {
			return expirationTime;
		}
		public void setExpirationTime(long expirationTime) {
			this.expirationTime = expirationTime;
		}
		@Override
		public int compareTo(CacheObject o) {
			if(this.expirationTime > o.expirationTime) {
				return 1;
			} else if(this.expirationTime < o.expirationTime) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	@Override
	public void clear() {
		cacheMap.clear();
	}
}