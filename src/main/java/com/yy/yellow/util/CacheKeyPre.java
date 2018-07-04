package com.yy.yellow.util;

/**
 * 缓存key的前缀
 * @author yy
 *
 */
public enum CacheKeyPre {
	token, //token
	user_current_token, //表示用户当前登陆的那个token
	
	user_level_permission; //用户权限级别
}