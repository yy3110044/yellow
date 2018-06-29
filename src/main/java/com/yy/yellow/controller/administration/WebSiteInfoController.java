package com.yy.yellow.controller.administration;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.service.AdminUserService;
import com.yy.yellow.util.ResponseObject;

@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class WebSiteInfoController {
	@Autowired
	private AdminUserService aus;
	
	/**
	 * 返回网站相关信息
	 * @return
	 */
	@RequestMapping("/getWebsiteInfo")
	public ResponseObject getWebsiteInfo(HttpServletRequest req) {
		int adminUserId = (Integer)req.getAttribute("adminUserId");
		AdminUser au = aus.findById(adminUserId);
		Map<String, Object> result = new HashMap<>();
		result.put("adminUserName", au.getUserName());
		result.put("adminUserLastLoginTime", au.getLastLoginTime());
		result.put("adminUserLastLoginIp", au.getLastLoginIp());
		result.put("os", System.getProperty("os.name", "") + " " + System.getProperty("os.arch", "") + " " + System.getProperty("os.version", ""));
		result.put("serverName", req.getServerName());
		result.put("container", req.getServletContext().getServerInfo());
		result.put("userAgent", req.getHeader("user-agent"));
		
		return new ResponseObject(100, "返回成功", result);
	}
}