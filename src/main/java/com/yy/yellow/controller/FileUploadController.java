package com.yy.yellow.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.yy.yellow.util.Util;
import com.yy.yellow.util.Util.SaveFileResult;

/**
 * 文件上传controller
 * @author yy
 *
 */
@RestController
public class FileUploadController {
	@Value("${web.config.upload.path}")
	private String uploadPath;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public SaveFileResult upload(MultipartFile file, HttpServletRequest req) {
		return Util.saveFile(file, req.getServletContext(), uploadPath);
	}
}