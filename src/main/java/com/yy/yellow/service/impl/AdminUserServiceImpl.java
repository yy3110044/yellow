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
    private AdminUserMapper mapper;

    @Override
    public void add(AdminUser obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(int id) {
        mapper.delete(id);
    }

    @Override
    public void update(AdminUser obj) {
        mapper.update(obj);
    }

    @Override
    public AdminUser find(QueryCondition qc) {
        return mapper.find(qc);
    }

    @Override
    public AdminUser findById(int id) {
        return mapper.findById(id);
    }

    @Override
    public List<AdminUser> query(QueryCondition qc) {
        return mapper.query(qc);
    }

    @Override
    public int getCount(QueryCondition qc) {
        return mapper.getCount(qc);
    }
}
