package com.yy.yellow.controller.administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.User;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

/**
 * 用户管理controller
 * @author yy
 *
 */
@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class UserAdminController {
	@Autowired
	private UserService us;

	@RequestMapping("/userList")
	public ResponseObject userList(Integer id, String userName,
									@RequestParam(defaultValue="20") int pageSize,
							        @RequestParam(defaultValue="1") int pageNo,
							        @RequestParam(defaultValue="5") int showCount,
							        @RequestParam(defaultValue="id") String sortField,
							        @RequestParam(defaultValue="DESC") QueryCondition.SortType sortType) {
		QueryCondition qc = new QueryCondition();
		if(id != null) {
			qc.addCondition("id", "=", id);
		}
		if(!Util.empty(userName)) {
			qc.addCondition("userName", "=", userName);
		}
		qc.setPage(new Page(pageSize, pageNo, showCount));
		qc.addSort(sortField, sortType);
		List<User> list = us.query(qc);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", qc.getPage().setRowCount(us.getCount(qc)));
		return new ResponseObject(100, "success", result);
	}
	
	@RequestMapping("/userAdd")
	public ResponseObject userAdd(@RequestParam String userName,
			                       @RequestParam String passWord,
			                       String nickName,
			                       String phone,
			                       String email,
			                       @RequestParam(defaultValue="0") int level
			                       ) {
		if(Util.empty(userName, passWord)) {
			return new ResponseObject(101, "用户名或密码不能为空");
		}
		userName = userName.trim();
		User user = us.find(new QueryCondition().addCondition("userName", "=", userName));
		if(user != null) {
			return new ResponseObject(102, "此用户名已存在");
		}
		
		if(level < 0) {
			return new ResponseObject(103, "用户等级不能为负");
		}
		
		user = new User();
		user.setUserName(userName);
		user.setPassWord(DigestUtils.md5Hex(passWord));
		user.setNickName(nickName);
		user.setPhone(phone);
		user.setEmail(email);
		user.setLevel(level);
		us.add(user);
		return new ResponseObject(100, "添加用户成功");
	}
	
	@RequestMapping("/userDetail")
	public ResponseObject userDetail(@RequestParam int userId) {
		User user = us.findById(userId);
		if(user != null) {
			return new ResponseObject(100, "success", user);
		} else {
			return new ResponseObject(101, "用户不存在");
		}
	}
	
	@RequestMapping("/userDelete")
	public ResponseObject userDelete(@RequestParam int userId) {
		us.delete(userId);
		return new ResponseObject(100, "success");
	}
}