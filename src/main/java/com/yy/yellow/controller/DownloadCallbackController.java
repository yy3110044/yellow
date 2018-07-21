package com.yy.yellow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.yy.yellow.service.MovieService;

@Controller
@RequestMapping(method=RequestMethod.POST)
public class DownloadCallbackController {
	@Autowired
	private MovieService ms;
	
	@RequestMapping("/downloadCallback")
	public void downloadCallback(String sourceUrl, String downloadStatus, String serverUrl) {
		ms.updateDownloadUrl(serverUrl, sourceUrl);
	}
}
