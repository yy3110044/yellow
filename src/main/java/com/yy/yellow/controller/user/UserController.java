package com.yy.yellow.controller.user;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.User;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.ResponseObject;

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
}