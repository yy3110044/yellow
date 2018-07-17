package com.yy.yellow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.StaticSourceAdmin;

/**
 * 文件上传controller
 * @author yy
 *
 */
@RestController
public class FileUploadController {
	@Autowired
	private StaticSourceAdmin ssa;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public ResponseObject upload(@RequestParam MultipartFile file) {
		return ssa.saveFile(file);
	}
}