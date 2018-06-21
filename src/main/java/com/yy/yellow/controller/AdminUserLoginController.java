package com.yy.yellow.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method=RequestMethod.POST)
public class AdminUserLoginController {
	
	@RequestMapping("/adminUserLogin")
	public Map<String, Object> adminUserLogin(String userName, String passWord) {
		return null;
	}
	
	@RequestMapping("/adminUserLogout")
	public Map<String, Object> adminUserLogout() {
		return null;
	}
}