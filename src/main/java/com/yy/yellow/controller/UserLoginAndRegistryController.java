package com.yy.yellow.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.CacheKeyPre;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;

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
	public ResponseObject userLogin(@RequestParam String loginType, @RequestParam String userName, @RequestParam String passWord, HttpSession session) {
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName).addCondition("passWord", "=", DigestUtils.md5Hex(passWord)));
		if(user == null) {
			return new ResponseObject(101, "用户名或密码错误");
		}

		if("web".equals(loginType)) { //web登陆
			session.setAttribute("userId", user.getId());
			return new ResponseObject(100, "web登陆成功");
		} else if("app".equals(loginType)) { //app登陆
			//先检查此用户有没有登陆过，如果有，则删除以前的token
			String token = cache.getString(CacheKeyPre.user_current_token, String.valueOf(user.getId()));
			if(token != null) { //删除以前的token缓存
				cache.remove(CacheKeyPre.token, token);
			}
			
			//创建新的token，使用uuid作为token
			token = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			
			//将新的当前用户token覆盖原来的token
			int seconds = tokenExpirationTime * 60 * 60;
			cache.set(CacheKeyPre.user_current_token, String.valueOf(user.getId()), token, seconds);
			cache.set(CacheKeyPre.token, token, user.getId(), seconds);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("token", token);
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
		session.removeAttribute("userId");
		if(token != null) {
			Integer userId = (Integer)cache.remove(CacheKeyPre.token, token);
			if(userId != null) {
				cache.remove(CacheKeyPre.user_current_token, userId.toString());
			}
		}
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
										@RequestParam String passWord1,
										@RequestParam String passWord2,
										String nickName,
										String email,
										@RequestParam String yzm,
										HttpSession session) {
		if(!passWord1.equals(passWord2)) {
			return new ResponseObject(101, "两次输入密码不一致");
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
		user.setUserName(userName);
		user.setPassWord(DigestUtils.md5Hex(passWord1));
		user.setNickName(nickName);
		user.setEmail(email);
		us.add(user);
		return new ResponseObject(100, "注册成功");
	}
}