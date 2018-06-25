package com.yy.yellow.controller;

import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.service.AdminUserService;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;

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
	public ResponseObject adminUserLogin(@RequestParam String userName, @RequestParam String passWord, HttpSession session) {
		ResponseObject ro = new ResponseObject();
		AdminUser au = aus.find(new QueryCondition().addCondition("userName", "=", userName).addCondition("passWord", "=", DigestUtils.md5Hex(passWord)));
		if(au == null) {
			ro.setCode(101);
			ro.setMsg("用户名或密码错误");
		} else {
			ro.setCode(100);
			ro.setMsg("登陆成功");
			session.setAttribute("adminUserId", au.getId());//在session中存入adminUser的Id
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
		if(!this.addAdminUser) {
			return new ResponseObject(101, "不允许添加管理员");
		}
		
		AdminUser au = aus.find(new QueryCondition().addCondition("userName", "=", userName));
		if(au != null) {
			return new ResponseObject(102, userName + "已存在");
		}
		
		au = new AdminUser();
		au.setUserName(userName);
		au.setPassWord(DigestUtils.md5Hex(passWord));
		aus.add(au);
		return new ResponseObject(100, "添加管理员成功");
	}
}