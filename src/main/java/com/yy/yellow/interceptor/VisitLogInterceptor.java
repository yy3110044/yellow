package com.yy.yellow.interceptor;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.yy.yellow.po.VisitLog;
import com.yy.yellow.service.VisitLogService;
import com.yy.yellow.util.Util;

/**
 * 接口访问记录interceptor
 * @author yy
 *
 */
public class VisitLogInterceptor implements HandlerInterceptor {
	private VisitLogService vls;
	private ExecutorService service; //线程池
	
	public VisitLogInterceptor(VisitLogService vls) {
		this.vls = vls;
		service = Executors.newCachedThreadPool();//一个灵活的缓冲线程池
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		String ip = request.getRemoteAddr();
		String userAgent = request.getHeader("user-agent");
		String requestUrl = Util.getRequestUrl(request);
		String params = Util.toJsonStr(request.getParameterMap());
		service.execute(new AddVisitLogRunnable(userId, ip, userAgent, requestUrl, params));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	//不要保存记录的访问文件
	private static final String[] doNotSave = {".js", ".png", ".jpg", ".css", "jpeg", ".mp4"};
	static {
		Arrays.sort(doNotSave); //二分查找必须先排序
	}
	
	//添加访问日志的执行线程操作
	private class AddVisitLogRunnable implements Runnable {
		private Integer userId;
		private String ip;
		private String userAgent;
		private String requestUrl;
		private String params;
		
		public AddVisitLogRunnable(Integer userId, String ip, String userAgent, String requestUrl, String params) {
			this.userId = userId;
			this.ip = ip;
			this.userAgent = userAgent;
			this.requestUrl = requestUrl;
			this.params = params;
		}

		@Override
		public void run() {
			if(Arrays.binarySearch(doNotSave, Util.getSuffix(requestUrl)) < 0) {//没在排除数组里才保存
				VisitLog log = new VisitLog();
				log.setUserId(userId);
				log.setIp(ip);
				log.setUserAgent(userAgent);
				log.setRequestUrl(requestUrl);
				log.setParams(params);
				vls.add(log);
			}
		}
	}
}