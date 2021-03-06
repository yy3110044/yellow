package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.util.QueryCondition;

public interface AdminUserService {
    void add(AdminUser obj);
    void delete(int id);
    void update(AdminUser obj);
    AdminUser find(QueryCondition qc);
    AdminUser findById(int id);
    List<AdminUser> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
