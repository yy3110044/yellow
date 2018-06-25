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
 * 缓存类
 * @author yy
 *
 */
@Component("cache")
public class Cache {
	public Cache(@Value("${web.config.cache.clearDelay}") final long clearDelay) {//从application.properties中读取清除时间间隔
		if(clearDelay < 1) {
			throw new RuntimeException("clearDelay必须大于零");
		}
		this.cacheMap = new ConcurrentHashMap<String, CacheObject>(); //一个线程安全的map
		this.service = Executors.newSingleThreadScheduledExecutor(); //一个单线程的任务池
		this.service.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
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
				System.out.println(clearDelay);
			}
		}, clearDelay, clearDelay, TimeUnit.MINUTES);
	}

	//定时任务线程池，用于清除已过期的缓存数据
	private ScheduledExecutorService service;
	
	//缓存map
	private Map<String, CacheObject> cacheMap;
	
	//缓存一个数据，永久有效
	public void set(String key, Object value) {
		this.cacheMap.put(key, new CacheObject(key, value));
	}
	
	//缓存一个数据，并设置有效时间，单位：秒
	public void set(String key, Object value, int seconds) {
		this.cacheMap.put(key, new CacheObject(key, value, System.currentTimeMillis() + seconds * 1000));
	}
	
	//取出一个数据
	public Object get(String key) {
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
	public Integer getInt(String key) {
		return (Integer)get(key);
	}
	public Double getDouble(String key) {
		return (Double)get(key);
	}
	public String getString(String key) {
		return (String)get(key);
	}
	
	//返回缓存数量
	public int size() {
		return this.cacheMap.size();
	}
	
	//缓存对象
	private class CacheObject implements Comparable<CacheObject> {
		private String key; //key
		private Object value; //缓存数据
		private long expirationTime; //到期时间

		CacheObject(String key, Object value) {
			this(key, value, 3471264000000L);//到期时间，默认为2080-01-01表示为永久有
		}
		CacheObject(String key, Object value, long expirationTime) {
			this.key = key;
			this.value = value;
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
}