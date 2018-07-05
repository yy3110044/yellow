package com.yy.yellow.controller.administration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public ResponseObject userList(String userName,
									 @RequestParam(defaultValue="20") int pageSize,
							         @RequestParam(defaultValue="1") int pageNo,
							         @RequestParam(defaultValue="5") int showCount,
							         @RequestParam(defaultValue="id") String sortField,
							         @RequestParam(defaultValue="DESC") QueryCondition.SortType sortType) {
		QueryCondition qc = new QueryCondition();
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