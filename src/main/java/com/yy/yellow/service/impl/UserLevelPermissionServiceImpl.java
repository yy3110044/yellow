package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.UserLevelPermissionMapper;
import com.yy.yellow.po.UserLevelPermission;
import com.yy.yellow.service.UserLevelPermissionService;
import com.yy.yellow.util.QueryCondition;

@Repository("userLevelPermissionService")
@Transactional
public class UserLevelPermissionServiceImpl implements UserLevelPermissionService {
    @Autowired
    private UserLevelPermissionMapper mapper;

    @Override
    public void add(UserLevelPermission obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }

    @Override
    public void update(UserLevelPermission obj) {
        mapper.update(obj);
    }

    @Override
    public UserLevelPermission find(QueryCondition qc) {
        return mapper.find(qc);
    }

    @Override
    public UserLevelPermission findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public List<UserLevelPermission> query(QueryCondition qc) {
        return mapper.query(qc);
    }

    @Override
    public int getCount(QueryCondition qc) {
        return mapper.getCount(qc);
    }
}
