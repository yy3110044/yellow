package com.yy.yellow.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		int port = request.getServerPort();
		String portStr = port == 80 ? "" : ":" + port;
		String basePath = request.getScheme() + "://" + request.getServerName() + portStr + request.getContextPath() + "/";
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

	/**
	 * 将对象转换成json字符串
	 * @param o
	 * @return
	 */
	private static ObjectMapper objectMapper = new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	public static String toJsonStr(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			logger.error(o.toString() + "：转换json错误：" + e.toString());
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 返回spring的bean
	 * @param name
	 * @param sc
	 * @return
	 */
	public static Object getBean(String name, ServletContext sc) {
		WebApplicationContext context = (WebApplicationContext)sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(name);
	}
	public static <T> T getBean(Class<T> requiredType, ServletContext sc) {
		WebApplicationContext context = (WebApplicationContext)sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(requiredType);
	}
	
	/**
	 * 得到url文件的后缀
	 * @param urlStr
	 * @return
	 */
	public static String getSuffix(String str) {
		str = str.substring(str.lastIndexOf('/'));

		int paramStrIndex = str.indexOf('?'); //参数字符串首字索引
		if(paramStrIndex >= 0) { //没有参数字符串
			str = str.substring(0, paramStrIndex);
		} 

		int index = str.lastIndexOf('.');
		if(index < 0) {
			return "";
		} else {
			return str.substring(index);
		}
	}
	
	/**
	 * 将对象转为map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> ObjectToMap(Object obj) {
		Field[] fields = obj.getClass().getFields();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for(Field field : fields) {
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj));
			}
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return map;
	}
	
	public static String requestPost(String urlStr, Map<String, Object> params) {
		BufferedWriter bw = null;
		BufferedReader br = null;
		StringBuilder result = new StringBuilder();

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("contentType", "application/x-www-form-urlencoded");
			
			if(params != null && params.size() > 0) {
				bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
				StringBuilder sb = new StringBuilder();
				int i = 0;
				for(Entry<String, Object> entry : params.entrySet()) {
					sb.append(entry.getKey()).append('=').append(entry.getValue());
					if(++i < params.size()) {
						sb.append('&');
					}
				}
				bw.write(sb.toString());
				bw.flush();
			}
			
			if(conn.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String str = null;
				while((str = br.readLine()) != null) {
					result.append(str);
				}
			} else {
				logger.warn("请求错误：" + url + "，代码：" + conn.getResponseCode());
			}
		} catch (IOException e) {
			logger.error(e.toString());
		} finally {
			try {
				if(bw != null) bw.close();
				if(br != null) br.close();
			} catch (IOException e) {
				logger.error(e.toString());
			}
		}
		return result.toString();
	}
}