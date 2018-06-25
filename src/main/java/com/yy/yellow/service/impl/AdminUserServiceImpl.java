package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.AdminUserMapper;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.service.AdminUserService;
import com.yy.yellow.util.QueryCondition;

@Repository("adminUserService")
@Transactional
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserMapper aum;

	@Override
	public void add(AdminUser au) {
		aum.add(au);
	}

	@Override
	public void delete(int id) {
		aum.delete(id);
	}

	@Override
	public void update(AdminUser au) {
		aum.update(au);
	}

	@Override
	public AdminUser find(QueryCondition qc) {
		return aum.find(qc);
	}

	@Override
	public AdminUser findById(int id) {
		return aum.findById(id);
	}

	@Override
	public List<AdminUser> query(QueryCondition qc) {
		return aum.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return aum.getCount(qc);
	}
}