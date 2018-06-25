package com.yy.yellow.controller;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.User;
import com.yy.yellow.service.UserService;
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
	
	/**
	 * 用户登陆接口
	 * @param loginType 登陆类型：web、app
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@RequestMapping("/userLogin")
	public ResponseObject userLogin(@RequestParam String loginType, @RequestParam String userName, @RequestParam String passWord) {
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName).addCondition("passWord", "=", DigestUtils.md5Hex(passWord)));
		if(user == null) {
			return new ResponseObject(101, "用户名或密码错误");
		}
		
		
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
										String email) {
		if(!passWord1.equals(passWord2)) {
			return new ResponseObject(101, "两次输入密码不一致");
		}
		
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName));
		if(user != null) {
			return new ResponseObject(102, "此用户名已被注册");
		}
		
		user = new User();
		user.setUserName(userName);
		user.setPassWord(DigestUtils.md5Hex(passWord1));
		user.setNickName(nickName);
		user.setEmail(email);
		us.add(user);
		return new ResponseObject(100, "注册成功");
	}
}