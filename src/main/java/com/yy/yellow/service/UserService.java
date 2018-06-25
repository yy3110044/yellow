package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.User;
import com.yy.yellow.util.QueryCondition;

public interface UserService {
	void add(User user);
	void delete(int id);
	void update(User user);
	User find(QueryCondition qc);
	User findById(int id);
	List<User> query(QueryCondition qc);
	int getCount(QueryCondition qc);
}