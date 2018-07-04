package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.UserLevelPermission;
import com.yy.yellow.util.QueryCondition;

public interface UserLevelPermissionService {
    void add(UserLevelPermission obj);
    void delete(int id);
    void update(UserLevelPermission obj);
    UserLevelPermission find(QueryCondition qc);
    UserLevelPermission findById(int id);
    List<UserLevelPermission> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
