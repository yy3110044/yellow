package com.yy.yellow.util;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 登陆管理器
 * @author yy
 *
 */
@Component("loginManager")
public class LoginManager {
	private final Map<Integer, HttpSession> webLoginUserMap = new ConcurrentHashMap<Integer, HttpSession>();
	
	@Autowired
	private Cache cache;
	
	@Value("${web.config.token.expirationTime:1296000000}")
	private long tokenExpirationTime;//token过期时间，单位：毫秒
	
	//app登陆
	public String appLogin(Integer userId) {
		//先检查此用户有没有app登陆过，如果有，则删除以前的token
		String token = cache.delete(CacheKeyPre.yellow_userId_to_token, userId.toString());
		if(token != null) { //删除以前的token缓存
			cache.delete(CacheKeyPre.yellow_token_to_userId, token);
		}
		
		//再检查有没有web登陆过，如果有，清除session中的userId
		HttpSession oldSession = webLoginUserMap.remove(userId);
		if(oldSession != null) {
			oldSession.removeAttribute("userId");
		}
		
		//创建新的token，使用uuid作为token
		token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		//将新的当前用户token覆盖原来的token
		cache.set(CacheKeyPre.yellow_userId_to_token, userId.toString(), token, tokenExpirationTime);
		cache.set(CacheKeyPre.yellow_token_to_userId, token, userId.toString(), tokenExpirationTime);
		return token;
	}
	
	//web登陆
	public void webLogin(Integer userId, HttpSession session) {
		//先检查此用户有没有app登陆过，如果有，则删除以前的token
		String token = cache.delete(CacheKeyPre.yellow_userId_to_token, userId.toString());
		if(token != null) { //删除以前的token缓存
			cache.delete(CacheKeyPre.yellow_token_to_userId, token);
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
	public void appLogout(String token) {
		if(token != null) {
			String userId = cache.delete(CacheKeyPre.yellow_token_to_userId, token);
			if(userId != null) {
				cache.delete(CacheKeyPre.yellow_userId_to_token, userId);
			}
		}
	}
	
	
	//web退出登陆
	public void webLogout(HttpSession session) {
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId != null) {
			session.removeAttribute("userId");
			session = webLoginUserMap.remove(userId);
			if(session != null) session.removeAttribute("userId");
		}
	}
	
	//返回entrySet
	public Set<Entry<Integer, HttpSession>> getEntrySet() {
		return webLoginUserMap.entrySet();
	}
}