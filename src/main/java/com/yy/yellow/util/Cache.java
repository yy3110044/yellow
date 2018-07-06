package com.yy.yellow.util;

/**
 * 缓存接口
 * @author yy
 *
 */
public interface Cache {
	//缓存一个数据，永久有效
	void set(CacheKey key, String name, String value);
	
	//缓存一个数据，并设置有效时间，单位：秒
	void set(CacheKey key, String name, String value, int seconds);
	
	//返回一个String
	String getString(CacheKey key, String name);
	
	//返回一个Integer
	default Integer getInt(CacheKey key, String name) {
		String value = getString(key, name);
		if(value == null) {
			return null;
		} else {
			return Integer.valueOf(value);
		}
	}
	
	//返回一个Double
	default Double getDouble(CacheKey key, String name) {
		String value = getString(key, name);
		if(value == null) {
			return null;
		} else {
			return Double.valueOf(value);
		}
	}
	
	//删除一个值，并返回删除的值
	String delete(CacheKey key, String name);
	
	//清除所有缓存
	void clear();
}