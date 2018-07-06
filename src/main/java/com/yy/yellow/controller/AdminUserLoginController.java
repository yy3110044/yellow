package com.yy.yellow.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.po.AdminUserLoginLog;
import com.yy.yellow.service.AdminUserLoginLogService;
import com.yy.yellow.service.AdminUserService;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

/**
 * 管理员登陆接口
 * @author yy
 *
 */
@RestController
@RequestMapping(method=RequestMethod.POST)
public class AdminUserLoginController {
	@Autowired
	private AdminUserService aus;
	
	@Autowired
	private AdminUserLoginLogService aulls;
	
	@Value("${web.config.addAdminUser}")
	private boolean addAdminUser;
	
	/**
	 * 管理员登陆
	 * @param userName
	 * @param passWord
	 * @param session
	 * @return
	 */
	@RequestMapping("/adminUserLogin")
	public ResponseObject adminUserLogin(@RequestParam String userName, @RequestParam String passWord, HttpServletRequest req) {
		ResponseObject ro = new ResponseObject();
		AdminUser au = aus.find(new QueryCondition().addCondition("userName", "=", userName).addCondition("passWord", "=", DigestUtils.md5Hex(passWord)));
		if(au == null) {
			ro.setCode(101);
			ro.setMsg("用户名或密码错误");
		} else {
			ro.setCode(100);
			ro.setMsg("登陆成功");
			req.getSession().setAttribute("adminUserId", au.getId());//在session中存入adminUser的Id
			
			//添加登陆日志
			AdminUserLoginLog log = new AdminUserLoginLog();
			log.setAdminUserId(au.getId());
			log.setAdminUserName(au.getUserName());
			log.setLoginIp(req.getRemoteAddr());
			log.setLoginTime(new Date());
			log.setUserAgent(req.getHeader("user-agent"));
			aulls.addLog(log);
		}
		return ro;
	}

	/**
	 * 管理员退出登陆
	 * @param session
	 * @return
	 */
	@RequestMapping("/adminUserLogout")
	public ResponseObject adminUserLogout(HttpSession session) {
		session.removeAttribute("adminUserId");//清除session中的adminUserId
		return new ResponseObject(100, "退出登陆成功");
	}
	
	/**
	 * 添加一个管理员，必须要在web设置里把web.config.upload.addAdminUser设为true
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping("/addAdminUser")
	public ResponseObject addAdminUser(@RequestParam String userName, @RequestParam String passWord) {
		if(Util.empty(userName, passWord)) {
			return new ResponseObject(101, "用户名或密码不能为空");
		}
		
		if(!this.addAdminUser) {
			return new ResponseObject(102, "不允许添加管理员");
		}
		userName = userName.trim();
		AdminUser au = aus.find(new QueryCondition().addCondition("userName", "=", userName));
		if(au != null) {
			return new ResponseObject(103, userName + "已存在");
		}
		
		au = new AdminUser();
		au.setUserName(userName);
		au.setPassWord(DigestUtils.md5Hex(passWord));
		aus.add(au);
		return new ResponseObject(100, "添加管理员成功");
	}
}