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
    private UserMapper mapper;

    @Override
    public void add(User obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }

    @Override
    public void update(User obj) {
        mapper.update(obj);
    }

    @Override
    public User find(QueryCondition qc) {
        return mapper.find(qc);
    }

    @Override
    public User findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public List<User> query(QueryCondition qc) {
        return mapper.query(qc);
    }

    @Override
    public int getCount(QueryCondition qc) {
        return mapper.getCount(qc);
    }
}
