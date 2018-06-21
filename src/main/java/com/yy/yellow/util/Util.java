package com.yy.yellow.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

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
	
	//保存上传文件方法
	public static SaveFileResult saveFile(MultipartFile file, ServletContext context, String uploadPath) {
		SaveFileResult saveFileResult = new SaveFileResult();
		
		if(file != null && file.getSize() > 0) {
			CreateFolderResult result = getBaseFolder(context, uploadPath);
			String originalFileName = file.getOriginalFilename(); //文件原始名称
			int index = originalFileName.lastIndexOf(".");
			String newFileName = UUID.randomUUID().toString() + (index < 0 ? "" : originalFileName.substring(index));
			File newFile = new File(result.baseFolder, newFileName);
			
			try {
				file.transferTo(newFile);
				saveFileResult.setCode(100);
				saveFileResult.setMsg("文件上传成功");
				saveFileResult.setServerUrl(result.baseUrl + newFileName);
				logger.debug(newFile.getPath());
			} catch (IllegalStateException | IOException e) {
				saveFileResult.setCode(102);
				saveFileResult.setMsg("文件上传失败");
				logger.warn(e.toString());
			}
		} else {
			saveFileResult.setCode(101);
			saveFileResult.setMsg("上传文件为空");
		}
		return saveFileResult;
	}
	private static CreateFolderResult getBaseFolder(ServletContext context, String uploadPath) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(new Date());
		
		String baseFolder = context.getRealPath(uploadPath + "/" + dateStr);
		File file = new File(baseFolder);
		if(!file.exists()) { //不存在目录则先创建
			file.mkdirs();
		}
		
		String baseUrl = uploadPath + "/" + dateStr + "/";
		if(baseUrl.charAt(0) == '/') {
			baseUrl = baseUrl.substring(1);
		}
		return new CreateFolderResult(file, baseUrl);
	}
	private static class CreateFolderResult {
		private File baseFolder;
		private String baseUrl;
		public CreateFolderResult(File baseFolder, String baseUrl) {
			this.baseFolder = baseFolder;
			this.baseUrl = baseUrl;
		}
	}
	//文件保存结果类
	public static class SaveFileResult {
		private int code; //结果代码 100：文件上传成功，101：上传文件为空，102：文件上传失败
		private String msg; //结果信息
		private String serverUrl; //保存成功后，在服务器上的url
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getServerUrl() {
			return serverUrl;
		}
		public void setServerUrl(String serverUrl) {
			this.serverUrl = serverUrl;
		}
	}
}