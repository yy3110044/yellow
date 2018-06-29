package com.yy.yellow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.CacheKeyPre;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

/**
 * 检查用户登陆的Interceptor
 * @author yy
 *
 */
public class CheckUserLoginInterceptor implements HandlerInterceptor {
	private Cache cache;
	public CheckUserLoginInterceptor(Cache cache) {
		this.cache = cache;
	}
	
	//进入handler方法之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		if(userId != null) {//先从session中读取userId
			request.setAttribute("userId", userId);
			return true;
		}
		
		//session中没有再从cache中读取userId
		String token = request.getParameter("token");
		if(token != null) {
			userId = (Integer)cache.get(CacheKeyPre.token, token);
			if(userId != null) {
				request.setAttribute("userId", userId);
				return true;
			} else {
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(Util.toJsonStr(new ResponseObject(200, "您还未登陆，或登陆已过期，请重新登陆")));
				return false;
			}
		} else {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(Util.toJsonStr(new ResponseObject(200, "您还未登陆，或登陆已过期，请重新登陆")));
			return false;
		}
	}
	
	//进入handler方法之后，返回modelAndView之前执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	//执行handler完成执行此方法
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}