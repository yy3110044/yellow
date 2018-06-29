package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.UserLoginLogMapper;
import com.yy.yellow.mapper.UserMapper;
import com.yy.yellow.po.User;
import com.yy.yellow.po.UserLoginLog;
import com.yy.yellow.service.UserLoginLogService;
import com.yy.yellow.util.QueryCondition;

@Repository("userLoginLogService")
@Transactional
public class UserLoginLogServiceImpl implements UserLoginLogService {
	@Autowired
	private UserLoginLogMapper ullm;
	
	@Autowired
	private UserMapper um;

	@Override
	public void add(UserLoginLog log) {
		ullm.add(log);
	}

	@Override
	public void delete(int id) {
		ullm.delete(id);
	}

	@Override
	public void update(UserLoginLog log) {
		ullm.update(log);
	}

	@Override
	public UserLoginLog find(QueryCondition qc) {
		return ullm.find(qc);
	}

	@Override
	public UserLoginLog findById(int id) {
		return ullm.findById(id);
	}

	@Override
	public List<UserLoginLog> query(QueryCondition qc) {
		return ullm.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return ullm.getCount(qc);
	}

	@Override
	public void addLog(UserLoginLog log) {
		User user = um.findById(log.getUserId());
		user.setLastLoginIp(log.getLoginIp());
		user.setLastLoginTime(log.getLoginTime());
		user.setLastLoginType(log.getLoginType());
		um.update(user);
		ullm.add(log);
	}
}