package com.yy.yellow.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.yy.yellow.service.MovieService;

/**
 * 静态资源管理
 * @author yy
 *
 */
@Component("staticSourceAdmin")
public class StaticSourceAdmin {
	private static final Logger logger = LogManager.getLogger(StaticSourceAdmin.class);
	
	@Value("${web.config.download.connectionTimeout:60}")
	private int connectionTimeout;

	@Value("${web.config.download.readTimeout:60}")
	private int readTimeout;
	
	@Value("${web.config.staticSource.host}")
	private String staticSourceHost;
	
	@Value("${web.config.staticSource.rootPath}")
	private String staticSourceRootPath;
	
	@Autowired
	private MovieService ms;

	/**
	 * 保存文件
	 * @param file
	 * @return
	 */
	public ResponseObject saveFile(MultipartFile file) {
		if(file != null && file.getSize() > 0) {
			CreateFolderResult cfr = this.getBaseFolder();

			String originalFileName = file.getOriginalFilename(); //文件原始名称
			int index = originalFileName.lastIndexOf(".");
			String newFileName = UUID.randomUUID().toString() + (index < 0 ? "" : originalFileName.substring(index));
			File newFile = new File(cfr.baseFolder, newFileName);
			
			try {
				file.transferTo(newFile);
				return new ResponseObject(100, "文件上传成功", new MyMap().set("serverUrl", cfr.baseUrl + newFileName));
			} catch (IllegalStateException | IOException e) {
				logger.warn(e.toString());
				return new ResponseObject(102, "文件上传失败");
			}
		} else {
			return new ResponseObject(101, "上传文件为空");
		}
	}

	private Map<String, Future<DownloadResult>> downloadRecord = new ConcurrentHashMap<>();
	private ExecutorService service = Executors.newFixedThreadPool(5);
	/**
	 * 下载文件
	 */
	public void downloadFile(String movieId) {
		Future<DownloadResult> future = service.submit(new DownloadCallable(movieId, ms));
		f
	}
	
	private CreateFolderResult getBaseFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateStr = sdf.format(new Date());
		
		File baseFolder = new File(this.staticSourceRootPath, dateStr);
		if(!baseFolder.exists()) { //不存在，则创建目录
			baseFolder.mkdirs();
		}

		String baseUrl = (staticSourceHost.charAt(staticSourceHost.length() - 1) == '/') ? (staticSourceHost + dateStr + "/") : (staticSourceHost + "/" + dateStr + "/");
		return new CreateFolderResult(baseFolder, baseUrl);
	}
	private class CreateFolderResult {
		private File baseFolder;
		private String baseUrl;
		public CreateFolderResult(File baseFolder, String baseUrl) {
			this.baseFolder = baseFolder;
			this.baseUrl = baseUrl;
		}
	}
}