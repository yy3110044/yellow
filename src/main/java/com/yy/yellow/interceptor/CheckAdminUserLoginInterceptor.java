package com.yy.yellow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

/**
 * 检查后台管理员用户是否登陆的Interceptor
 * @author yy
 *
 */
public class CheckAdminUserLoginInterceptor implements HandlerInterceptor {

	//进入handler方法之前执行
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer adminUserId = (Integer)request.getSession().getAttribute("adminUserId");
		if(adminUserId == null) {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(Util.toJsonStr(new ResponseObject(200, "您还未登陆，请先登陆")));
			return false;
		} else {
			request.setAttribute("adminUserId", adminUserId);
			return true;
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