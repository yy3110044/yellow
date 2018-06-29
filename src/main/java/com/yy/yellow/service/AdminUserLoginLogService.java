package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.AdminUserLoginLog;
import com.yy.yellow.util.QueryCondition;

public interface AdminUserLoginLogService {
	void add(AdminUserLoginLog log);
	void delete(int id);
	void update(AdminUserLoginLog log);
	AdminUserLoginLog find(QueryCondition qc);
	AdminUserLoginLog findById(int id);
	List<AdminUserLoginLog> query(QueryCondition qc);
	int getCount(QueryCondition qc);

	void addLog(AdminUserLoginLog log); //添加一个日志记录，此方法与add不同的是，会同时更新AdminUser的最近登陆记录
}