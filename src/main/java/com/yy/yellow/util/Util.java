package com.yy.yellow.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Util {
	private Util() {}
	
	private static final Logger logger = LogManager.getLogger(Util.class);
	
	public static boolean empty(String str) {
		return str == null || str.trim().isEmpty();
	}
	public static boolean empty(String ... strs) {
		for(String str : strs) {
			if(empty(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		int port = request.getServerPort();
		String portStr = port == 80 ? "" : ":" + port;
		String basePath = request.getScheme() + "://" + request.getServerName() + portStr + path + "/";
		return basePath;
	}
	
	//url编码
	public static String urlEncode(String str) {
		String retVal = "";
		if(str != null) {
			try {
				retVal = URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.toString());
				throw new RuntimeException(e);
			}
		}
		return retVal;
	}
	
	//url解码
	public static String urlDecode(String str) {
		String retVal = "";
		if(str != null) {
			try {
				retVal = URLDecoder.decode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.toString());
				throw new RuntimeException(e);
			}
		}
		return retVal;
	}
	
	//得到用户访问的完整URL
	public static String getRequestUrl(HttpServletRequest request) {
		String portStr = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String url = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath() + request.getServletPath();
		String queryString = request.getQueryString();
		return url + (empty(queryString) ? "" : "?" + queryString);
	}
	
	//得到每一天的开始时间和结束时间
	public static final long _24HoursMillis = 86400000L; //24小时的毫秒数
	public static final long _8HoursMillis = 28800000L; //8小时的毫秒数
	private static final long _jiange = 57600000L;
	public static long getEveryDayStartTime(long millis) {
		if(millis % _24HoursMillis >= _jiange) {
			return millis / _24HoursMillis * _24HoursMillis - _8HoursMillis + _24HoursMillis;
		} else {
			return millis / _24HoursMillis * _24HoursMillis - _8HoursMillis;
		}
	}
	//得到时间部分的毫秒数
	public static long getTimeMillis(long millis) {
		millis = millis % _24HoursMillis;
		if(millis >= _jiange) {
			return millis - _24HoursMillis;
		} else {
			return millis;
		}
	}
}