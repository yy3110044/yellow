package com.yy.yellow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.code.kaptcha.Constants;
import com.yy.yellow.po.User;
import com.yy.yellow.po.UserLoginLog;
import com.yy.yellow.service.UserLoginLogService;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.LoginManager;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

/**
 * 用户登陆以及注册接口
 * @author yy
 *
 */
@RestController
@RequestMapping(method=RequestMethod.POST)
public class UserLoginAndRegistryController {
	@Autowired
	private UserService us;
	
	@Autowired
	private UserLoginLogService ulls;
	
	@Autowired
	private Cache cache;
	
	@Value("${web.config.token.expirationTime}")
	private int tokenExpirationTime;//token过期时间，单位：小时
	
	/**
	 * 用户登陆接口
	 * @param loginType 登陆类型：web、app
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping("/userLogin")
	public ResponseObject userLogin(@RequestParam String loginType, @RequestParam String userName, @RequestParam String passWord, HttpServletRequest req) {
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName).addCondition("passWord", "=", DigestUtils.md5Hex(passWord)));
		if(user == null) {
			return new ResponseObject(101, "用户名或密码错误");
		}
		
		//登陆日志
		UserLoginLog log = new UserLoginLog();
		log.setUserId(user.getId());
		log.setUserName(user.getUserName());
		log.setLoginIp(req.getRemoteAddr());
		log.setLoginTime(new Date());
		log.setUserAgent(req.getHeader("user-agent"));
		log.setLoginType(loginType);
		
		if("web".equals(loginType)) {
			LoginManager.webLogin(user.getId(), cache, req.getSession());
			ulls.addLog(log); //添加日志
			return new ResponseObject(100, "web登陆成功");
		} else if("app".equals(loginType)) {
			String token = LoginManager.appLogin(user.getId(), cache, tokenExpirationTime);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("token", token);
			ulls.addLog(log); //添加日志
			return new ResponseObject(100, "app登陆成功", result);
		} else {
			return new ResponseObject(102, "未知的登陆类型");
		}
	}
	
	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping("/userLogout")
	public ResponseObject logout(String token, HttpSession session) {
		LoginManager.appLogout(token, cache);
		LoginManager.webLogout(session);
		return new ResponseObject(100, "退出登陆成功");
	}
	
	/**
	 * 用户注册
	 * @param userName
	 * @param passWord1
	 * @param passWord2
	 * @param nickName
	 * @param email
	 * @return
	 */
	@RequestMapping("/userRegistry")
	public ResponseObject userRegistry(@RequestParam String userName,
										@RequestParam String passWord,
										String nickName,
										String email,
										@RequestParam String yzm,
										HttpSession session) {
		if(Util.empty(userName) || Util.empty(passWord)) {
			return new ResponseObject(101, "用户名或密码不能为空");
		}
		
		String yzmCode = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(yzmCode == null || !yzmCode.toUpperCase().equals(yzm.toUpperCase())) {
			return new ResponseObject(102, "验证码错误");
		}
		
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName));
		if(user != null) {
			return new ResponseObject(103, "此用户名已被注册");
		}
		
		session.removeAttribute(Constants.KAPTCHA_SESSION_KEY); //成功使用后，删除验证码
		
		user = new User();
		user.setUserName(userName.trim());
		user.setPassWord(DigestUtils.md5Hex(passWord));
		user.setNickName(nickName);
		user.setEmail(email);
		us.add(user);
		return new ResponseObject(100, "注册成功");
	}
}