package com.yy.yellow.controller.user;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.User;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

@RestController
@RequestMapping(value="/user", method=RequestMethod.POST)
public class UserController {
	@Autowired
	private UserService us;
	
	/**
	 * 返回用户信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	public ResponseObject getUserInfo(HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		User user = us.findById(userId);
		return new ResponseObject(100, "返回成功", user);
	}
	
	@RequestMapping("/modifyPassWord")
	public ResponseObject modifyPassWord( @RequestParam String oldPassWord, @RequestParam String newPassWord, HttpServletRequest req) {
		int userId = (Integer)req.getAttribute("userId");
		
		if(Util.empty(newPassWord)) {
			return new ResponseObject(101, "新密码不能为空");
		}
		
		User user = us.findById(userId);
		if(!user.getPassWord().equals(DigestUtils.md5Hex(oldPassWord))) {
			return new ResponseObject(102, "原密码错误");
		}

		user.setPassWord(DigestUtils.md5Hex(newPassWord));
		us.update(user);
		return new ResponseObject(100, "密码修改成功");
	}
}