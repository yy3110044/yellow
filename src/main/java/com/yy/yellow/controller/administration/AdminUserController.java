package com.yy.yellow.controller.administration;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.service.AdminUserService;
import com.yy.yellow.util.ResponseObject;

@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class AdminUserController {
	@Autowired
	private AdminUserService aus;

	/**
	 * 修改管理员密码
	 * @param oldPassWord
	 * @param newPassWord1
	 * @param newPassWord2
	 * @param req
	 * @return
	 */
	@RequestMapping("/modifyAdminUserPassWord")
	public ResponseObject modifyAdminUserPassWord(@RequestParam String oldPassWord, @RequestParam String newPassWord, HttpServletRequest req) {
		int adminUserId = (Integer)req.getAttribute("adminUserId");

		AdminUser au = aus.findById(adminUserId);
		if(!au.getPassWord().equals(DigestUtils.md5Hex(oldPassWord))) {
			return new ResponseObject(101, "原密码错误");
		}
		
		au.setPassWord(DigestUtils.md5Hex(newPassWord));
		aus.update(au);
		return new ResponseObject(100, "密码修改成功");
	}
}