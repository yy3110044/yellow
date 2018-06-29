package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.AdminUserLoginLogMapper;
import com.yy.yellow.mapper.AdminUserMapper;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.po.AdminUserLoginLog;
import com.yy.yellow.service.AdminUserLoginLogService;
import com.yy.yellow.util.QueryCondition;

@Repository("adminUserLoginLogService")
@Transactional
public class AdminUserLoginLogServiceImpl implements AdminUserLoginLogService {
	@Autowired
	private AdminUserLoginLogMapper aullm;
	
	@Autowired
	private AdminUserMapper aum;

	@Override
	public void add(AdminUserLoginLog log) {
		aullm.add(log);
	}

	@Override
	public void delete(int id) {
		aullm.delete(id);
	}

	@Override
	public void update(AdminUserLoginLog log) {
		aullm.update(log);
	}

	@Override
	public AdminUserLoginLog find(QueryCondition qc) {
		return aullm.find(qc);
	}

	@Override
	public AdminUserLoginLog findById(int id) {
		return aullm.findById(id);
	}

	@Override
	public List<AdminUserLoginLog> query(QueryCondition qc) {
		return aullm.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return aullm.getCount(qc);
	}
	
	@Override
	public void addLog(AdminUserLoginLog log) {
		AdminUser au = aum.findById(log.getAdminUserId());
		au.setLastLoginIp(log.getLoginIp());
		au.setLastLoginTime(log.getLoginTime());
		aum.update(au);
		aullm.add(log);
	}
}