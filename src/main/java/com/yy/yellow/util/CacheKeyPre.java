package com.yy.yellow.util;

/**
 * 缓存key前缀
 * @author yy
 *
 */
public enum CacheKeyPre {
	yellow_token_to_userId, //用户登陆后，缓存 token 对应的 userId
	yellow_userId_to_token, //用户登陆后，缓存 userId 对应的 token，与yellow_token_to_userId联合起来保证了token，userId单对单的关系，用来实现一个帐号同时只能登陆一个用户
	yellow_user_level_permission; //缓存用户级别对应的观影权限
}