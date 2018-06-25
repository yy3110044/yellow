package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.UserMapper;
import com.yy.yellow.po.User;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.QueryCondition;

@Repository("userService")
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper um;

	@Override
	public void add(User user) {
		um.add(user);
	}

	@Override
	public void delete(int id) {
		um.delete(id);
	}

	@Override
	public void update(User user) {
		um.update(user);
	}

	@Override
	public User find(QueryCondition qc) {
		return um.find(qc);
	}

	@Override
	public User findById(int id) {
		return um.findById(id);
	}

	@Override
	public List<User> query(QueryCondition qc) {
		return um.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return um.getCount(qc);
	}
}