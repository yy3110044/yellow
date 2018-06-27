package com.yy.yellow.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;

/**
 * 登陆管理器
 * @author yy
 *
 */
public class LoginManager {
	private LoginManager() {}
	
	//web在线用户的map
	private static final Map<Integer, HttpSession> webLoginUserMap = new ConcurrentHashMap<Integer, HttpSession>();

	//app登陆
	public static String appLogin(Integer userId, Cache cache, int tokenExpirationTime) {
		//先检查此用户有没有app登陆过，如果有，则删除以前的token
		String token = (String)cache.remove(CacheKeyPre.user_current_token, userId.toString());
		if(token != null) { //删除以前的token缓存
			cache.remove(CacheKeyPre.token, token);
		}
		
		//再检查有没有web登陆过，如果有，清除session中的userId
		HttpSession oldSession = webLoginUserMap.remove(userId);
		if(oldSession != null) {
			oldSession.removeAttribute("userId");
		}
		
		//创建新的token，使用uuid作为token
		token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		//将新的当前用户token覆盖原来的token
		int seconds = tokenExpirationTime * 60 * 60;
		cache.set(CacheKeyPre.user_current_token, userId.toString(), token, seconds);
		cache.set(CacheKeyPre.token, token, userId, seconds);
		return token;
	}

	//web登陆
	public static void webLogin(Integer userId, Cache cache, HttpSession session) {
		//先检查此用户有没有app登陆过，如果有，则删除以前的token
		String token = (String)cache.remove(CacheKeyPre.user_current_token, userId.toString());
		if(token != null) { //删除以前的token缓存
			cache.remove(CacheKeyPre.token, token);
		}
		
		//先检查此用户有没有web登陆过，如果有，清除session中的userId
		HttpSession oldSession = webLoginUserMap.remove(userId);
		if(oldSession != null) {
			oldSession.removeAttribute("userId");
		}
		
		session.setAttribute("userId", userId);
		webLoginUserMap.put(userId, session);
	}
	
	//app退出登陆
	public static void appLogout(String token, Cache cache) {
		if(token != null) {
			Integer userId = (Integer)cache.remove(CacheKeyPre.token, token);
			if(userId != null) {
				cache.remove(CacheKeyPre.user_current_token, userId.toString());
			}
		}
	}
	
	//web退出登陆
	public static void webLogout(HttpSession session) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId != null) {
			session.removeAttribute("userId");
			webLoginUserMap.remove(userId).removeAttribute("userId");
		}
	}
}