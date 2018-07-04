package com.yy.yellow.util;

/**
 * 缓存接口
 * @author yy
 *
 */
public interface Cache {
	//缓存一个数据，永久有效
	void set(CacheKeyPre pre, String key, String value);
	
	//缓存一个数据，并设置有效时间，单位：秒
	public void set(CacheKeyPre pre, String key, String value, int seconds);
	
	//取出一个数据
	public String getString(CacheKeyPre pre, String key);
	public Integer getInt(CacheKeyPre pre, String key);
	public Double getDouble(CacheKeyPre pre, String key);
	
	//删除一个缓存
	public String remove(CacheKeyPre pre, String key);
	
	//返回缓存数量
	public int size();
}