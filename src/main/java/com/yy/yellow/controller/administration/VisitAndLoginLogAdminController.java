package com.yy.yellow.controller.administration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.AdminUserLoginLog;
import com.yy.yellow.po.UserLoginLog;
import com.yy.yellow.po.VisitLog;
import com.yy.yellow.service.AdminUserLoginLogService;
import com.yy.yellow.service.UserLoginLogService;
import com.yy.yellow.service.VisitLogService;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;
import com.yy.yellow.util.QueryCondition.SortType;

/**
 * 访问记录以及登陆日志管理controller
 * @author yy
 *
 */
@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class VisitAndLoginLogAdminController {
	@Autowired
	private VisitLogService vls;
	
	@Autowired
	private AdminUserLoginLogService aulls;
	
	@Autowired
	private UserLoginLogService ulls;
	
	@RequestMapping("/visitLogList")
	public ResponseObject visitLogList(Integer userId,
										String ip,
										@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
		                                @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime,
										@RequestParam(defaultValue="20") int pageSize,
								        @RequestParam(defaultValue="1") int pageNo,
								        @RequestParam(defaultValue="5") int showCount) {
		QueryCondition qc = new QueryCondition();
		if(userId != null) {
			qc.addCondition("userId", "=", userId);
		}
		if(!Util.empty(ip)) {
			qc.addCondition("ip", "=", ip);
		}
		if(startTime != null) {
			qc.addCondition("createTime", ">=", startTime);
		}
		if(endTime != null) {
			qc.addCondition("createTime", "<=", endTime);
		}
		qc.setPage(new Page(pageSize, pageNo, showCount));
		qc.addSort("id", SortType.DESC);
		List<VisitLog> list = vls.query(qc);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", qc.getPage().setRowCount(vls.getCount(qc)));
		return new ResponseObject(100, "success", result);
	}
	
	@RequestMapping("/loginLogList")
	public ResponseObject loginLogList(@RequestParam String userType,//user、adminUser
										Integer userId,
										String userName,
										@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
		                                @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime,
		                                @RequestParam(defaultValue="20") int pageSize,
								        @RequestParam(defaultValue="1") int pageNo,
								        @RequestParam(defaultValue="5") int showCount) {
		if("user".equals(userType)) { //用户
			QueryCondition qc = new QueryCondition();
			if(userId != null) {
				qc.addCondition("userId", "=", userId);
			}
			if(!Util.empty(userName)) {
				qc.addCondition("userName", "=", userName);
			}
			if(startTime != null) {
				qc.addCondition("createTime", ">=", startTime);
			}
			if(endTime != null) {
				qc.addCondition("createTime", "<=", endTime);
			}
			qc.setPage(new Page(pageSize, pageNo, showCount));
			qc.addSort("id", SortType.DESC);
			List<UserLoginLog> list = ulls.query(qc);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("list", list);
			result.put("page", qc.getPage().setRowCount(ulls.getCount(qc)));
			return new ResponseObject(100, "success", result);
		} else if("adminUser".equals(userType)) { //管理员
			QueryCondition qc = new QueryCondition();
			if(userId != null) {
				qc.addCondition("adminUserId", "=", userId);
			}
			if(!Util.empty(userName)) {
				qc.addCondition("adminUserName", "=", userName);
			}
			if(startTime != null) {
				qc.addCondition("createTime", ">=", startTime);
			}
			if(endTime != null) {
				qc.addCondition("createTime", "<=", endTime);
			}
			qc.setPage(new Page(pageSize, pageNo, showCount));
			qc.addSort("id", SortType.DESC);
			List<AdminUserLoginLog> list = aulls.query(qc);
			
			Map<String, Object> result = new HashMap<>();
			result.put("list", list);
			result.put("page", qc.getPage().setRowCount(aulls.getCount(qc)));
			return new ResponseObject(100, "success", result);
		} else {
			return new ResponseObject(101, "未知的用户类型");
		}
	}
}