package com.yy.yellow.util;

import java.net.URL;
import java.util.UUID;
import java.io.File;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;

/**
 * 下载工具类
 * @author yy
 *
 */
public class DownLoadUtil {
	private DownLoadUtil() {}
	private static DownLoadUtil downLoadUtil;
	
	public static DownLoadUtil getInstance() {
		if(downLoadUtil == null) {
			downLoadUtil = new DownLoadUtil();
		}
		return downLoadUtil;
	}

	public void downLoad(String urlStr, ServletContext context) throws Exception {
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + Util.getSuffix(urlStr);
		
		String movieUrl = "movies/" + fileName;
		String filePath = context.getRealPath("/movies") + File.separator + fileName;
		
		FileUtils.copyURLToFile(new URL(urlStr), new File(filePath));
	}
}