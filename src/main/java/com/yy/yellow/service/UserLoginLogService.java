package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.UserLoginLog;
import com.yy.yellow.util.QueryCondition;

public interface UserLoginLogService {
	void add(UserLoginLog log);
	void delete(int id);
	void update(UserLoginLog log);
	UserLoginLog find(QueryCondition qc);
	UserLoginLog findById(int id);
	List<UserLoginLog> query(QueryCondition qc);
	int getCount(QueryCondition qc);
	
	void addLog(UserLoginLog log);
}